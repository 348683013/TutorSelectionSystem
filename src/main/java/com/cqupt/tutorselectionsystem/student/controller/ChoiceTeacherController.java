package com.cqupt.tutorselectionsystem.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqupt.tutorselectionsystem.student.domain.Requests;
import com.cqupt.tutorselectionsystem.student.domain.Round;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.dto.RequestDTO;
import com.cqupt.tutorselectionsystem.student.dto.HasSpareTeacherDTO;
import com.cqupt.tutorselectionsystem.student.service.RequestsService;
import com.cqupt.tutorselectionsystem.student.service.RoundService;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.service.TeacherService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import com.cqupt.tutorselectionsystem.student.utils.TimeUtil;
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
import java.util.Date;
import java.util.List;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Student/ChoiceTeacher")
public class ChoiceTeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private RequestsService requestsService;
    @Autowired
    private StudentService studentService;

    //选择还有剩余名额导师的教师姓名，同时查询这一轮选择什么时候结束
    @RequestMapping(path = "/hasSpareTeachers")
    @ResponseBody
    public ResultMsg hasSpareTeachers() {
        //查询有剩余名额老师姓名
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.ne(Teacher::getTotalStudent, 0);
        List<Teacher> teacherList = teacherService.list(lambdaQueryWrapper);

        List<HasSpareTeacherDTO> hasSpareTeacherDTOS = new ArrayList<>();
        for (Teacher teacher : teacherList) {
            HasSpareTeacherDTO hasSpareTeacherDTO = new HasSpareTeacherDTO();
            BeanUtils.copyProperties(teacher, hasSpareTeacherDTO);
            hasSpareTeacherDTOS.add(hasSpareTeacherDTO);
        }
        //查询这一轮结束的时间
        LambdaQueryWrapper<Round> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Round::getIsStart, 1);
        Round round = roundService.getOne(lambdaQueryWrapper1);
        String stopTimeStr = "";
        if (round != null) {
            Date stopTimeDate = round.getStopTime();
            stopTimeStr = TimeUtil.formatDateByPattern(stopTimeDate, "yyyy-MM-dd HH:mm:ss");
        } else {
            stopTimeStr = "系统尚未开启！";
        }

        return ResultMsg.success().add("teachers", hasSpareTeacherDTOS).add("stopTime", stopTimeStr);
    }

    //接收前端传来的请求导师列表
    @RequestMapping(path = "/sendRequest")
    @ResponseBody
    public ResultMsg sendRequest(@RequestBody RequestDTO requestDTO,
                                 HttpServletRequest request,
                                 HttpSession session) {
        //得到登录状态的学生信息
        String token = request.getHeader("token"); //从请求头中获取这个token的值
        Student student = (Student) session.getAttribute(token);
        if (student == null) {
            //当session中没有这个用户的时候再进行查询
            LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Student::getToken, token);
            student = studentService.getOne(queryWrapper);
            //查询出来之后再次放入session
            session.setAttribute(token, student);
        }
        //得到第几轮id
        Long roundId = requestDTO.getRoundId();

        //得到老师id的int数组
        Integer[] teacherIds = requestDTO.getCheckId();
        List<Requests> requestsList = new ArrayList<>();
        //把所有的请求进行封装
        for (Integer teacherId : teacherIds) {
            //根据teacherId查询老师名
            LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
            teacherLambdaQueryWrapper.eq(Teacher::getTeacherId, teacherId);
            Teacher teacher = teacherService.getOne(teacherLambdaQueryWrapper);
            Requests requests = new Requests();
            //开始封装信息
            requests.setTeacherId(teacherId);
            requests.setTeacherName(teacher.getRealname());
            requests.setStudentId(student.getStudentId());
            requests.setStudentName(student.getRealname());
            requests.setSendTime(new Date());
            requests.setRoundId(roundId);
            requestsList.add(requests);
        }
        //写入数据库
        requestsService.saveBatch(requestsList);

        return ResultMsg.success();
    }

}
