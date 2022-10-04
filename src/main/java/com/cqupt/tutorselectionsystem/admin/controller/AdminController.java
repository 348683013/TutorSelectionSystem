package com.cqupt.tutorselectionsystem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqupt.tutorselectionsystem.admin.domain.Admin;
import com.cqupt.tutorselectionsystem.admin.dto.TokenStudentDTO;
import com.cqupt.tutorselectionsystem.admin.service.AdminService;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import com.cqupt.tutorselectionsystem.student.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    //管理员登录
    @ResponseBody
    @RequestMapping(path = "/adminLogin")
    public ResultMsg adminLogin(@RequestBody Admin admin, HttpSession session) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Admin::getAccountNumber, admin.getAccountNumber())
                .eq(Admin::getPassword, admin.getPassword());
        List<Admin> adminList = adminService.list(queryWrapper);
        long count = adminList.size();
        if (count == 0) {
            return ResultMsg.fail().add("errorMsg", "账号或密码错误！");
        } else if (count > 1) {
            return ResultMsg.fail().add("errorMsg", "账号信息有误，请检查数据库进行调整！");
        }
        Admin admin1 = adminList.get(0);
        //判断这个管理员是否被锁住
        if (admin1.getIsLock() == 0) {
            return ResultMsg.fail().add("errorMsg", "该账号被上锁，请联系超级管理员进行解锁！");
        }
        //登录成功后生成token，然后赋值给用户这个属性，同时写入数据库中
        String token = UUIDUtil.getUUID();
        admin1.setToken(token);
        adminService.updateById(admin1);
        //把token存入到session中，用来校验用户是否登录使用
        session.setAttribute(token, admin1);
        return ResultMsg.success().add("adminInfo", admin1);
    }

    //根据token查询需要的信息
    @ResponseBody
    @RequestMapping(path = "/findAdminByToken")
    public ResultMsg findAdminByToken(HttpSession session, HttpServletRequest request) throws ParseException {
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
        //查询学生信息用于刚登陆时候展示
        /*LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.last("limit 0,12");
        List<Student> studentList = studentService.list(studentLambdaQueryWrapper);//第一页的学生list
        long studentCount = studentService.count();//学生总数
        TokenStudentDTO tokenStudentDTO = new TokenStudentDTO();
        tokenStudentDTO.setTotal(studentCount);
        tokenStudentDTO.setRecords(studentList);*/

        return ResultMsg.success().add("adminInfo", admin)
                /*.add("studentsByPage", tokenStudentDTO)*/;
    }
}
