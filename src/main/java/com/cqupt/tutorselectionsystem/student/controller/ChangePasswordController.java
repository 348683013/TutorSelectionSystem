package com.cqupt.tutorselectionsystem.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.dto.ChangePasswordDTO;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Student/ChangePassword")
public class ChangePasswordController {
    @Autowired
    private StudentService studentService;

    @ResponseBody
    @RequestMapping(path = "/changePassword")
    public ResultMsg changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, HttpServletRequest request){
        //先判断是否是登录状态
        String token = request.getHeader("token"); //从请求头中获取这个token的值
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getToken, token);
        Student student = studentService.getOne(queryWrapper);
        if(student == null){
            return ResultMsg.fail().add("msg","未登录用户！");
        }

        //先校验两次新密码是否相同
        if(!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getReNewPassword())){
            return ResultMsg.fail().add("msg","两次输入的新密码不同！");
        }
        //长度校验10-20位
        if(changePasswordDTO.getNewPassword().length() < 10 || changePasswordDTO.getNewPassword().length() > 20){
            return ResultMsg.fail().add("msg","新密码长度在10-20位之间！");
        }
        //检验旧密码是否正确
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(Student::getPassword, changePasswordDTO.getOldPassword())
                .eq(Student::getToken, token);
        Student student1 = studentService.getOne(studentLambdaQueryWrapper);
        if(student1 == null){//密码错误
            return ResultMsg.fail().add("msg","旧密码输入错误！");
        }
        student1.setPassword(changePasswordDTO.getNewPassword());
        LambdaUpdateWrapper<Student> newPasswordUpdateWrapper = new LambdaUpdateWrapper<>();
        newPasswordUpdateWrapper.eq(Student::getToken, token);
        studentService.update(student1, newPasswordUpdateWrapper);
        return ResultMsg.success();
    }
}
