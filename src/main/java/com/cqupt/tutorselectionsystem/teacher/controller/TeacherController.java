package com.cqupt.tutorselectionsystem.teacher.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqupt.tutorselectionsystem.student.domain.Requests;
import com.cqupt.tutorselectionsystem.student.domain.Round;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.dto.RoundInfoDTO;
import com.cqupt.tutorselectionsystem.student.service.RequestsService;
import com.cqupt.tutorselectionsystem.student.service.RoundService;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.service.TeacherService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import com.cqupt.tutorselectionsystem.student.utils.TimeUtil;
import com.cqupt.tutorselectionsystem.student.utils.UUIDUtil;
import com.cqupt.tutorselectionsystem.teacher.dto.AllReceiveRequestsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private RequestsService requestsService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RoundService roundService;

    //教师用户登录
    @PostMapping(path = "/TeacherLogin")
    @ResponseBody
    public ResultMsg studentLogin(@RequestBody Teacher teacher, HttpSession session) { //@RequestBody可以解决获取不到前端封装好的json格式对象

        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Teacher::getAccountNumber, teacher.getAccountNumber())
                .eq(Teacher::getPassword, teacher.getPassword());
        List<Teacher> teacherList = teacherService.list(queryWrapper);
        long count = teacherList.size();
        if (count == 0) {
            return ResultMsg.fail().add("errorMsg", "账号或密码错误！");
        } else if (count > 1) {
            return ResultMsg.fail().add("errorMsg", "账号信息有误，请联系管理员进行调整！");
        }
        Teacher teacher1 = teacherList.get(0);
        //登录成功后生成token，然后赋值给用户这个属性，同时写入数据库中
        String token = UUIDUtil.getUUID();
        teacher1.setToken(token);
        teacherService.updateById(teacher1);
        //把token存入到session中，用来校验用户是否登录使用
        session.setAttribute(token, teacher1);
        return ResultMsg.success().add("teacherInfo", teacher1);
    }

    //根据token查询老师信息
    @PostMapping(path = "/findTeacherByToken")
    @ResponseBody
    public ResultMsg findTeacherByToken(HttpSession session, HttpServletRequest request) throws ParseException {
        String token = request.getHeader("token"); //从请求头中获取这个token的值
        //System.out.println("============"+token);
        //先从session中取这个用户信息
        Teacher teacher = (Teacher) session.getAttribute(token);
        if (teacher == null) {
            //当session中没有这个用户的时候再进行查询
            LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Teacher::getToken, token);
            teacher = teacherService.getOne(queryWrapper);
            //查询出来之后再次放入session
            if (teacher != null) {
                session.setAttribute(token, teacher);
            }
        }
        if(teacher == null){
            return ResultMsg.fail().add("msg", "用户未登录");
        }

        //需要查询出这个老师收到的所有请求，分页，一次查询13条记录，这个只是查询第一页，后面分页功能由其他方法来实现
        LambdaQueryWrapper<Requests> requestsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        requestsLambdaQueryWrapper
                .eq(Requests::getTeacherId, teacher.getTeacherId())
                .orderByDesc(Requests::getSendTime)
                .last("limit 0,12");
        List<Requests> requestsList = requestsService.list(requestsLambdaQueryWrapper);
        List<AllReceiveRequestsDTO> allReceiveRequestsDTOList = new ArrayList<>();
        for (Requests request1 : requestsList) {
            AllReceiveRequestsDTO allReceiveRequestsDTO = new AllReceiveRequestsDTO();
            allReceiveRequestsDTO.setStudentName(request1.getStudentName());
            allReceiveRequestsDTO.setReceiveTime(TimeUtil.dateToYMD(request1.getSendTime()));
            allReceiveRequestsDTO.setStatus(request1.getStatus());
            //获得学生其他信息
            LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
            studentLambdaQueryWrapper.eq(Student::getStudentId, request1.getStudentId());
            Student oneStudent = studentService.getOne(studentLambdaQueryWrapper);
            allReceiveRequestsDTO.setClassName(oneStudent.getClassName());
            allReceiveRequestsDTO.setStudentNumber(oneStudent.getAccountNumber());
            allReceiveRequestsDTO.setTelephone(oneStudent.getTelephone());
            allReceiveRequestsDTO.setEmail(oneStudent.getEmail());
            allReceiveRequestsDTO.setHasScienceClass(oneStudent.getHasScienceClass());
            allReceiveRequestsDTO.setAddress(oneStudent.getAddress());
            allReceiveRequestsDTOList.add(allReceiveRequestsDTO);
        }

        //查找自己的学生
        //根据老师id和status等于1来进行查询
        requestsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        requestsLambdaQueryWrapper.eq(Requests::getTeacherId, teacher.getTeacherId())
                .eq(Requests::getStatus, 1);
        requestsList = requestsService.list(requestsLambdaQueryWrapper);
        List<AllReceiveRequestsDTO> myStudentList = new ArrayList<>();
        for (Requests request1 : requestsList) {
            AllReceiveRequestsDTO allReceiveRequestsDTO = new AllReceiveRequestsDTO();
            allReceiveRequestsDTO.setStudentName(request1.getStudentName());
            allReceiveRequestsDTO.setReceiveTime(TimeUtil.dateToYMD(request1.getSendTime()));
            allReceiveRequestsDTO.setStatus(request1.getStatus());
            //获得学生其他信息
            LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
            studentLambdaQueryWrapper.eq(Student::getStudentId, request1.getStudentId());
            Student oneStudent = studentService.getOne(studentLambdaQueryWrapper);
            allReceiveRequestsDTO.setClassName(oneStudent.getClassName());
            allReceiveRequestsDTO.setStudentNumber(oneStudent.getAccountNumber());
            allReceiveRequestsDTO.setTelephone(oneStudent.getTelephone());
            allReceiveRequestsDTO.setEmail(oneStudent.getEmail());
            allReceiveRequestsDTO.setHasScienceClass(oneStudent.getHasScienceClass());
            allReceiveRequestsDTO.setAddress(oneStudent.getAddress());
            myStudentList.add(allReceiveRequestsDTO);
        }

        //再传一个roundId给前端
        LambdaQueryWrapper<Round> roundLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roundLambdaQueryWrapper.eq(Round::getIsStart, 2);
        Round round = roundService.getOne(roundLambdaQueryWrapper);
        RoundInfoDTO roundInfoDTO = new RoundInfoDTO();
        //如果选择尚未开始
        if(round == null){
            roundInfoDTO.setRoundId(0L);
            roundInfoDTO.setName("系统尚未开启！");
            roundInfoDTO.setStopTime("");
            return ResultMsg.success()
                    .add("teacherInfo", teacher)
                    .add("myStudentList", myStudentList)
                    .add("allReceiveRequestsDTOList", allReceiveRequestsDTOList)
                    .add("roundInfo", roundInfoDTO);
        }
        roundInfoDTO.setRoundId(round.getRoundId());
        roundInfoDTO.setName(round.getName());
        roundInfoDTO.setStopTime("本轮结束时间：" + TimeUtil.dateToYMD(round.getStopTime()));

        return ResultMsg.success()
                .add("teacherInfo", teacher)
                .add("allReceiveRequestsDTOList", allReceiveRequestsDTOList)
                .add("myStudentList", myStudentList)
                .add("roundInfoDTO", roundInfoDTO);
    }
}
