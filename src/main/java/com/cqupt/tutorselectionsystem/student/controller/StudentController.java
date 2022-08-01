package com.cqupt.tutorselectionsystem.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import com.cqupt.tutorselectionsystem.student.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    //学生用户登录
    @RequestMapping(path = "/StudentLogin")
    @ResponseBody
    public ResultMsg studentLogin(@RequestBody Student student, HttpSession session) { //@RequestBody可以解决获取不到前端封装好的json格式对象

        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Student::getAccountNumber, student.getAccountNumber())
                .eq(Student::getPassword, student.getPassword());
        List<Student> studentList = studentService.list(queryWrapper);
        long count = studentList.size();
        if (count == 0) {
            return ResultMsg.fail().add("errorMsg", "账号或密码错误！");
        } else if (count > 1) {
            return ResultMsg.fail().add("errorMsg", "账号信息有误，请联系管理员进行调整！");
        }
        Student student1 = studentList.get(0);
        //登录成功后生成token，然后赋值给用户这个属性，同时写入数据库中
        String token = UUIDUtil.getUUID();
        student1.setToken(token);
        studentService.updateById(student1);
        //把token存入到session中，用来校验用户是否登录使用
        session.setAttribute(token, student1);
        return ResultMsg.success().add("studentInfo", student1);
    }

    //根据token查询学生用户信息，这个token是从请求头中进行传过来的
    @PostMapping(path = "/getStudentByToken")
    @ResponseBody
    public ResultMsg getStudentByToken(HttpSession session, HttpServletRequest request) {
        String token = request.getHeader("token"); //从请求头中获取这个token的值
        System.out.println(token);
        //先从session中取这个用户信息
        Student student = (Student) session.getAttribute(token);
        if (student != null) {
            return ResultMsg.success().add("studentInfo", student);
        } else {
            //当session中没有这个用户的时候再进行查询
            LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Student::getToken, token);
            student = studentService.getOne(queryWrapper);
            return ResultMsg.success().add("studentInfo", student);
        }
    }
}
