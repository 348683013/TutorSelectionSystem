package com.cqupt.tutorselectionsystem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqupt.tutorselectionsystem.admin.domain.Admin;
import com.cqupt.tutorselectionsystem.admin.dto.ShowAllSAndTPageDTO;
import com.cqupt.tutorselectionsystem.admin.service.AdminService;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Admin/StudentManager")
public class StudentManagerController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminService adminService;

    //分页展示所有学生信息
    @ResponseBody
    @RequestMapping(path = "/showAllStudentByPage")
    public ResultMsg showAllStudentByPage(@RequestBody ShowAllSAndTPageDTO showAllSAndTPageDTO
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
        Page<Student> page = new Page<>(showAllSAndTPageDTO.getPage(), showAllSAndTPageDTO.getLimit());
        Page<Student> studentsByPage = studentService.page(page);
        return ResultMsg.success().add("studentsByPage", studentsByPage);
    }

    //修改或者添加学生信息
    @ResponseBody
    @RequestMapping(path = "/modifyStudentInfo")
    public ResultMsg modifyStudentInfo(@RequestBody Student student, HttpSession session, HttpServletRequest request) {
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
        boolean b = studentService.saveOrUpdate(student);
        if (b) {
            return ResultMsg.success().add("msg", "修改成功！");
        } else {
            return ResultMsg.fail().add("msg", "修改失败！");
        }
    }

    //删除学生信息
    @ResponseBody
    @RequestMapping(path = "/deleteStudentInfo")
    public ResultMsg deleteStudentInfo(@RequestBody Student student, HttpSession session, HttpServletRequest request) {
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
        boolean b = studentService.removeById(student);
        if (b) {
            return ResultMsg.success().add("msg", "删除成功！");
        } else {
            return ResultMsg.fail().add("msg", "删除失败！");
        }
    }

    //添加学生信息
    /*@ResponseBody
    @RequestMapping(path = "/addStudentInfo")
    public ResultMsg addStudentInfo(@RequestBody Student student, HttpSession session, HttpServletRequest request) {
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
        boolean b = studentService.save(student);
        if (b) {
            return ResultMsg.success().add("msg", "添加成功！");
        } else {
            return ResultMsg.fail().add("msg", "添加失败！");
        }
    }*/
}
