package com.cqupt.tutorselectionsystem.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqupt.tutorselectionsystem.student.domain.Round;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.dto.HasSpareTeacherDTO;
import com.cqupt.tutorselectionsystem.student.service.RoundService;
import com.cqupt.tutorselectionsystem.student.service.TeacherService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import com.cqupt.tutorselectionsystem.student.utils.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
