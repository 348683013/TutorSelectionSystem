package com.cqupt.tutorselectionsystem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqupt.tutorselectionsystem.admin.domain.Admin;
import com.cqupt.tutorselectionsystem.admin.dto.SelectedResultDTO;
import com.cqupt.tutorselectionsystem.admin.dto.ShowAllSAndTPageDTO;
import com.cqupt.tutorselectionsystem.admin.service.AdminService;
import com.cqupt.tutorselectionsystem.student.domain.Requests;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.service.RequestsService;
import com.cqupt.tutorselectionsystem.student.service.TeacherService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Admin/SelectedResult")
public class SelectedResultController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private RequestsService requestsService;

    @ResponseBody
    @RequestMapping(path = "/showSelectedResultByPage")
    public ResultMsg showSelectedResultByPage(@RequestBody ShowAllSAndTPageDTO showAllSAndTPageDTO
            , HttpSession session, HttpServletRequest request) {
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

        //获得所有老师id
        LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teacherLambdaQueryWrapper.select(Teacher::getTeacherId, Teacher::getRealname);
        Page<Teacher> page = new Page<>(showAllSAndTPageDTO.getPage(), 11);
        Page<Teacher> teachersByPage = teacherService.page(page, teacherLambdaQueryWrapper);
        List<Teacher> teacherList = teachersByPage.getRecords();
        List<SelectedResultDTO> selectedResultDTOList = new ArrayList<>();
        //查找这个老师的所有学生，根据teacherId和status进行查找
        for (Teacher teacher : teacherList) {
            Integer teacherId = teacher.getTeacherId();
            LambdaQueryWrapper<Requests> requestsLambdaQueryWrapper = new LambdaQueryWrapper<>();
            requestsLambdaQueryWrapper.eq(Requests::getTeacherId, teacherId).eq(Requests::getStatus, 1);
            List<Requests> requestsList = requestsService.list(requestsLambdaQueryWrapper);
            if (requestsList.size() == 0) {
                SelectedResultDTO selectedResultDTO = new SelectedResultDTO();
                selectedResultDTO.setTeacherName(teacher.getRealname());
                selectedResultDTOList.add(selectedResultDTO);
                continue;
            }
            SelectedResultDTO selectedResultDTO = new SelectedResultDTO();
            selectedResultDTO.setTeacherName(teacher.getRealname());
            List<String> strings = new ArrayList<>();
            for (Requests requests : requestsList) {
                strings.add(requests.getStudentName());
            }
            selectedResultDTO.setStudentNameList(strings);
            selectedResultDTOList.add(selectedResultDTO);
        }
        Page<SelectedResultDTO> selectedResultDTOPage = new Page<>();
        BeanUtils.copyProperties(teachersByPage, selectedResultDTOPage);
        selectedResultDTOPage.setRecords(selectedResultDTOList);
        return ResultMsg.success().add("selectedResultDTOPage", selectedResultDTOPage);
    }
}
