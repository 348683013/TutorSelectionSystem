package com.cqupt.tutorselectionsystem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cqupt.tutorselectionsystem.admin.domain.Admin;
import com.cqupt.tutorselectionsystem.admin.service.AdminService;
import com.cqupt.tutorselectionsystem.student.domain.Requests;
import com.cqupt.tutorselectionsystem.student.domain.Round;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.service.RequestsService;
import com.cqupt.tutorselectionsystem.student.service.RoundService;
import com.cqupt.tutorselectionsystem.student.service.TeacherService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import com.mysql.jdbc.ResultSetRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Admin/LockUnlockInSeq")
public class LockUnlockInSeqController {
    @Autowired
    private RoundService roundService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private RequestsService requestsService;

    //有开启的就关闭正在开的那一个然后开启没开启过请求最多的那个老师；没有开启的就直接开启最多的那个
    @ResponseBody
    @RequestMapping(path = "/lockAndUnlockInSeq")
    public ResultMsg lockAndUnlockInSeq() {
        //找到接收请求数目最多的老师

        return ResultMsg.success();
    }

    //学生一轮
    @ResponseBody
    @RequestMapping(path = "/studentOneRound")
    public ResultMsg studentOneRound(HttpSession session, HttpServletRequest request) {
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

        //倒序查找round表的最新两轮
        LambdaQueryWrapper<Round> roundLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roundLambdaQueryWrapper.orderByDesc(Round::getStartTime).last("limit 2");
        List<Round> roundList = roundService.list(roundLambdaQueryWrapper);
        //roundList中的第二个元素是第一轮的记录
        Round round = roundList.get(1);
        round.setIsStart(1);
        roundService.updateById(round);

        return ResultMsg.success().add("msg","成功开启学生一轮！");
    }

    //学生二轮
    @ResponseBody
    @RequestMapping(path = "/studentTwoRound")
    public ResultMsg studentTwoRound(HttpSession session, HttpServletRequest request) {
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

        //倒序查找round表的最新两轮
        LambdaQueryWrapper<Round> roundLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roundLambdaQueryWrapper.orderByDesc(Round::getStartTime).last("limit 2");
        List<Round> roundList = roundService.list(roundLambdaQueryWrapper);


        //开启学生二轮，先关闭老师一轮
        Round round1 = roundList.get(1);
        round1.setIsStart(0);
        roundService.updateById(round1);

        //roundList中的第一个元素是第二轮的记录
        Round round = roundList.get(0);
        round.setIsStart(1);
        roundService.updateById(round);


        return ResultMsg.success().add("msg","成功开启学生二轮！");
    }

    //老师一轮
    @ResponseBody
    @RequestMapping(path = "/teacherOneRound")
    public ResultMsg teacherOneRound(HttpSession session, HttpServletRequest request) {
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

        //倒序查找round表的最新两轮
        LambdaQueryWrapper<Round> roundLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roundLambdaQueryWrapper.orderByDesc(Round::getStartTime).last("limit 2");
        List<Round> roundList = roundService.list(roundLambdaQueryWrapper);
        //roundList中的第二个元素是第一轮的记录
        Round round = roundList.get(1);
        round.setIsStart(2);
        roundService.updateById(round);

        return ResultMsg.success().add("msg","成功开启老师一轮！");
    }

    //老师二轮
    @ResponseBody
    @RequestMapping(path = "/teacherTwoRound")
    public ResultMsg teacherTwoRound(HttpSession session, HttpServletRequest request) {
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

        //倒序查找round表的最新两轮
        LambdaQueryWrapper<Round> roundLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roundLambdaQueryWrapper.orderByDesc(Round::getStartTime).last("limit 2");
        List<Round> roundList = roundService.list(roundLambdaQueryWrapper);

        //开启老师二轮，避免存在未关闭一轮
        Round round1 = roundList.get(1);
        round1.setIsStart(0);
        roundService.updateById(round1);

        //roundList中的第一个元素是第二轮的记录
        Round round = roundList.get(0);
        round.setIsStart(2);
        roundService.updateById(round);

        return ResultMsg.success().add("msg","成功开启老师二轮！");
    }

    //关闭整个系统
    //设置所有的round的isStart为0，teacher的isLock都设置成0，把所有request中的未处理的请求都设置成拒绝2
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    @RequestMapping(path = "/closeSystem")
    public ResultMsg closeSystem(HttpSession session, HttpServletRequest request) {
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
        try {
            //给所有老师上锁
            LambdaUpdateWrapper<Teacher> teacherLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            teacherLambdaUpdateWrapper.set(Teacher::getIsLock, 0);
            teacherService.update(teacherLambdaUpdateWrapper);
            //给所有round的isStart设置为0
            LambdaUpdateWrapper<Round> roundLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            roundLambdaUpdateWrapper.set(Round::getIsStart, 0);
            roundService.update(roundLambdaUpdateWrapper);
            //把所有request中的未处理的请求都设置成拒绝2
            LambdaUpdateWrapper<Requests> requestsLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            requestsLambdaUpdateWrapper.set(Requests::getStatus, 2).eq(Requests::getStatus, 0);
            requestsService.update(requestsLambdaUpdateWrapper);
        } catch (Exception e) {
            throw new RuntimeException("关闭失败！");
        }
        return ResultMsg.success().add("msg", "关闭成功！");
    }
}
