package com.cqupt.tutorselectionsystem.teacher.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqupt.tutorselectionsystem.student.domain.Requests;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.service.RequestsService;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.service.TeacherService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import com.cqupt.tutorselectionsystem.student.utils.TimeUtil;
import com.cqupt.tutorselectionsystem.teacher.dto.AllReceiveRequestsDTO;
import com.cqupt.tutorselectionsystem.teacher.dto.ShowAllRequestsPageDTO;
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
@RequestMapping(path = "/Teacher/ReceiveAllRequests")
public class ReceiveAllRequestsController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private RequestsService requestsService;

    @Autowired
    private StudentService studentService;

    @ResponseBody
    @RequestMapping(path = "/searchRequestsByPage")
    public ResultMsg searchRequestsByPage(@RequestBody ShowAllRequestsPageDTO showAllRequestsPageDTO,
                                          HttpServletRequest request,
                                          HttpSession session) {
        //先判断登录状态
        String token = request.getHeader("token");
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
        if (teacher == null) {
            return ResultMsg.fail().add("msg", "用户未登录");
        }
        //用户登录后分页查询该老师收到的所有请求
        Integer teacherId = teacher.getTeacherId();
        Page<Requests> page = new Page<>(showAllRequestsPageDTO.getPage(), showAllRequestsPageDTO.getLimit());
        LambdaQueryWrapper<Requests> requestsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        requestsLambdaQueryWrapper.eq(Requests::getTeacherId, teacherId).orderByDesc(Requests::getSendTime);
        Page<Requests> requestsByPage = requestsService.page(page, requestsLambdaQueryWrapper);
        //获取所有的请求list
        List<Requests> requestsList = requestsByPage.getRecords();
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
        Page<AllReceiveRequestsDTO> allReceiveRequestsDTOPage = new Page<>();
        BeanUtils.copyProperties(requestsByPage, allReceiveRequestsDTOPage);
        allReceiveRequestsDTOPage.setRecords(allReceiveRequestsDTOList);
        return ResultMsg.success().add("allReceiveRequestsDTOPage", allReceiveRequestsDTOPage);
    }
}
