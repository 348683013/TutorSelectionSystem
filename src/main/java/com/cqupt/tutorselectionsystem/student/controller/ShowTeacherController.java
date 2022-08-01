package com.cqupt.tutorselectionsystem.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.dto.ShowAllTeacherDTO;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.service.TeacherService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Student/ShowTeacher")
public class ShowTeacherController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(path = "/findAllTeachers")
    @ResponseBody
    public ResultMsg findAllTeachers(@RequestParam(value = "limit", defaultValue = "8") Integer limit,
                                     @RequestParam(value = "page", defaultValue = "1") Integer pc,
                                     @RequestParam(value = "nameKeyWord", defaultValue = "") String nameKeyWord) {

        Page<Teacher> page = new Page<>(pc, limit);
        //条件
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Teacher::getRealname, nameKeyWord);
        Page<Teacher> teachers = teacherService.page(page, lambdaQueryWrapper);

        //取出teachers中的List<T> records
        List<Teacher> records = teachers.getRecords();
        for (Teacher record : records) {
            record.setAccountNumber("");
            record.setPassword("");
        }

        return ResultMsg.success().add("teachers", teachers);
    }
}
