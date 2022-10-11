package com.cqupt.tutorselectionsystem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqupt.tutorselectionsystem.admin.domain.Admin;
import com.cqupt.tutorselectionsystem.admin.dto.AddTeacherDTO;
import com.cqupt.tutorselectionsystem.admin.dto.LockOrNotDTO;
import com.cqupt.tutorselectionsystem.admin.dto.ShowAllSAndTPageDTO;
import com.cqupt.tutorselectionsystem.admin.dto.ShowTeachersDTO;
import com.cqupt.tutorselectionsystem.admin.service.AdminService;
import com.cqupt.tutorselectionsystem.student.domain.Requests;
import com.cqupt.tutorselectionsystem.student.domain.Round;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.service.RequestsService;
import com.cqupt.tutorselectionsystem.student.service.RoundService;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.service.TeacherService;
import com.cqupt.tutorselectionsystem.student.utils.CalculateAge;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import com.cqupt.tutorselectionsystem.student.utils.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Admin/TeacherManager")
public class TeacherManagerController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private RequestsService requestsService;

    @ResponseBody
    @RequestMapping(path = "/showAllTeacherByPage")
    public ResultMsg showAllTeacherByPage(@RequestBody ShowAllSAndTPageDTO showAllSAndTPageDTO
            , HttpSession session, HttpServletRequest request) throws ParseException {
        String token = request.getHeader("token"); //从请求头中获取这个token的值
        //先从session中取这个用户信息
        Admin admin = (Admin) session.getAttribute(token);
        if (admin == null) {
            //当session中没有这个用户的时候再进行查询
            LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Admin::getToken, token);
            admin = adminService.getOne(queryWrapper);
            //查询出来之后再次放入session
            if (admin != null) {
                session.setAttribute(token, admin);
            }
        }
        //如果admin为null则表示没登录
        if (admin == null) {
            return ResultMsg.fail().add("msg", "管理员未登录！");
        }
        Page<Teacher> page = new Page<>(showAllSAndTPageDTO.getPage(), showAllSAndTPageDTO.getLimit());
        Page<Teacher> teachersByPage = teacherService.page(page);
        Page<ShowTeachersDTO> showTeachersDTOPage = new Page<>();
        BeanUtils.copyProperties(teachersByPage, showTeachersDTOPage);
        List<ShowTeachersDTO> showTeachersDTOList = new ArrayList<>();
        //获得当前轮次，is_start不等于0就是当前的轮次
        LambdaQueryWrapper<Round> roundLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roundLambdaQueryWrapper.ne(Round::getIsStart, 0);
        Round round = roundService.getOne(roundLambdaQueryWrapper);
        //如果这个时候未开启轮次，则round为空，我们直接设置请求数都为0
        Long roundId = round != null ? round.getRoundId() : 0;
        //格式化里面老师的年龄
        for (Teacher teacher : teachersByPage.getRecords()) {
            ShowTeachersDTO showTeachersDTO = new ShowTeachersDTO();
            BeanUtils.copyProperties(teacher, showTeachersDTO);
            showTeachersDTO.setBirthday(TimeUtil.dateToYMD(teacher.getBirthday()));
            //得到这个老师收到的所有未处理的请求数，根据teacherId、status、roundId
            LambdaQueryWrapper<Requests> requestsLambdaQueryWrapper = new LambdaQueryWrapper<>();
            requestsLambdaQueryWrapper.eq(Requests::getTeacherId, teacher.getTeacherId())
                    .eq(Requests::getRoundId, roundId)
                    .eq(Requests::getStatus, 0);
            long untreatedRequestsCount = requestsService.count(requestsLambdaQueryWrapper);
            showTeachersDTO.setUntreatedRequestsCount(untreatedRequestsCount);
            showTeachersDTOList.add(showTeachersDTO);
        }
        showTeachersDTOPage.setRecords(showTeachersDTOList);
        return ResultMsg.success().add("studentsByPage", showTeachersDTOPage);
    }

    //修改或者添加老师信息
    @ResponseBody
    @RequestMapping(path = "/modifyTeacherInfo")
    public ResultMsg modifyTeacherInfo(@RequestBody AddTeacherDTO teacherDTO, HttpSession session, HttpServletRequest request) throws ParseException {
        String token = request.getHeader("token"); //从请求头中获取这个token的值
        //先从session中取这个用户信息
        Admin admin = (Admin) session.getAttribute(token);
        if (admin == null) {
            //当session中没有这个用户的时候再进行查询
            LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Admin::getToken, token);
            admin = adminService.getOne(queryWrapper);
            //查询出来之后再次放入session
            if (admin != null) {
                session.setAttribute(token, admin);
            }
        }
        //如果admin为null则表示没登录
        if (admin == null) {
            return ResultMsg.fail().add("msg", "管理员未登录！");
        }
        //日期解析
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherDTO, teacher);
        teacher.setBirthday(CalculateAge.str2Date(teacherDTO.getBirthday()));
        boolean b = teacherService.saveOrUpdate(teacher);
        if (b) {
            return ResultMsg.success().add("msg", "修改成功！");
        } else {
            return ResultMsg.fail().add("msg", "修改失败！");
        }
    }

    //删除老师信息
    @ResponseBody
    @RequestMapping(path = "/deleteTeacherInfo")
    public ResultMsg deleteTeacherInfo(@RequestBody Teacher teacher, HttpSession session, HttpServletRequest request) {
        String token = request.getHeader("token"); //从请求头中获取这个token的值
        //先从session中取这个用户信息
        Admin admin = (Admin) session.getAttribute(token);
        if (admin == null) {
            //当session中没有这个用户的时候再进行查询
            LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Admin::getToken, token);
            admin = adminService.getOne(queryWrapper);
            //查询出来之后再次放入session
            if (admin != null) {
                session.setAttribute(token, admin);
            }
        }
        //如果admin为null则表示没登录
        if (admin == null) {
            return ResultMsg.fail().add("msg", "管理员未登录！");
        }
        boolean b = teacherService.removeById(teacher);
        if (b) {
            return ResultMsg.success().add("msg", "删除成功！");
        } else {
            return ResultMsg.fail().add("msg", "删除失败！");
        }
    }

    //给老师上锁和解锁
    @ResponseBody
    @RequestMapping(path = "/lockOrNotTeacher")
    public ResultMsg lockOrNotTeacher(@RequestBody LockOrNotDTO lockOrNotDTO, HttpSession session, HttpServletRequest request) {
        String token = request.getHeader("token"); //从请求头中获取这个token的值
        //先从session中取这个用户信息
        Admin admin = (Admin) session.getAttribute(token);
        if (admin == null) {
            //当session中没有这个用户的时候再进行查询
            LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Admin::getToken, token);
            admin = adminService.getOne(queryWrapper);
            //查询出来之后再次放入session
            if (admin != null) {
                session.setAttribute(token, admin);
            }
        }
        //如果admin为null则表示没登录
        if (admin == null) {
            return ResultMsg.fail().add("msg", "管理员未登录！");
        }
        //如果现在已上锁则进行解锁，否则加锁
        Teacher teacher = new Teacher();
        teacher.setTeacherId(lockOrNotDTO.getTeacherId());
        if (lockOrNotDTO.getIsLock() == 0) {
            teacher.setIsLock(1);//解锁
        } else {
            teacher.setIsLock(0);//加锁
        }
        boolean b = teacherService.updateById(teacher);
        if (b) {
            return ResultMsg.success().add("msg", "修改成功！");
        } else {
            return ResultMsg.fail().add("msg", "修改失败！");
        }
    }
}
