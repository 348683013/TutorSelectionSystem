package com.cqupt.tutorselectionsystem.teacher.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.service.TeacherService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import com.cqupt.tutorselectionsystem.student.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    //教师用户登录
    @RequestMapping(path = "/TeacherLogin")
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
}
