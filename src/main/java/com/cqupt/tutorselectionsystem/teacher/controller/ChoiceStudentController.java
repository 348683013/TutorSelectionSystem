package com.cqupt.tutorselectionsystem.teacher.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cqupt.tutorselectionsystem.student.domain.Requests;
import com.cqupt.tutorselectionsystem.student.domain.Round;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.service.RequestsService;
import com.cqupt.tutorselectionsystem.student.service.RoundService;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.service.TeacherService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import com.cqupt.tutorselectionsystem.teacher.dto.AgreeRequestsVO;
import com.cqupt.tutorselectionsystem.teacher.dto.OriginRoundIdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping(path = "/Teacher/ChoiceStudentController")
public class ChoiceStudentController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private RequestsService requestsService;

    @Autowired
    private StudentService studentService;

    //展示可以同意的请求列表
    @ResponseBody
    @RequestMapping(path = "/showStudentRequests")
    public ResultMsg showStudentRequests(@RequestBody OriginRoundIdVO originRoundIdVO, HttpServletRequest request) {
        //先判断登录状态
        String token = request.getHeader("token");
        //当session中没有这个用户的时候再进行查询
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getToken, token);
        Teacher teacher = teacherService.getOne(queryWrapper);

        if (teacher == null) {
            return ResultMsg.fail().add("msg", "用户未登录");
        }
        //查看这个老师接收到本轮的所有请求
        LambdaQueryWrapper<Requests> requestsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        requestsLambdaQueryWrapper.eq(Requests::getRoundId, originRoundIdVO.getOriginRoundId()).eq(Requests::getTeacherId, teacher.getTeacherId());
        List<Requests> requestsList = requestsService.list(requestsLambdaQueryWrapper);
        List<Requests> requestsList2 = new ArrayList<>();
        //如果其中某个学生已经有导师了，就不用在这里进行展示了
        for (int i = 0; i < requestsList.size(); i++) {
            Requests requests = requestsList.get(i);
            Long studentId = requests.getStudentId();
            LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
            studentLambdaQueryWrapper.eq(Student::getStudentId, studentId);
            Student student = studentService.getOne(studentLambdaQueryWrapper);
            if (student.getHasTutor().equals("0")) {
                //没有导师就进行展示
                requestsList2.add(requests);
            }
        }
        return ResultMsg.success().add("showRequestsList", requestsList2);
    }

    //第一轮同意请求
    //同意请求，根据学生的id，老师id，轮次id进行查询同意
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    @RequestMapping(path = "/agreeStudentRequests")
    public ResultMsg agreeStudentRequests(@RequestBody AgreeRequestsVO agreeRequestsVO, HttpServletRequest request) {
        //先判断登录状态
        String token = request.getHeader("token");
        //当session中没有这个用户的时候再进行查询
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getToken, token);
        Teacher teacher = teacherService.getOne(queryWrapper);

        if (teacher == null) {
            return ResultMsg.fail().add("msg", "用户未登录");
        }
        //判断老师是否获得选择学生的权利，是否解锁
        if (teacher.getIsLock() == 0) {
            //已上锁
            return ResultMsg.fail().add("msg", "还未轮到您来操作！请耐心等待！");
        }

        //获得学生ids
        Integer[] studentIds = agreeRequestsVO.getStudentIds();
        //获得轮次id
        Long roundId = agreeRequestsVO.getRoundId();

        //当选择的学生大于本轮的可选的次数就返回错误
        Integer totalStudent = teacher.getTotalStudent();//总剩余数
        Integer choiceCount = totalStudent % 2 == 0 ? totalStudent / 2 : totalStudent / 2 + 1;
        if (studentIds.length > choiceCount) {
            return ResultMsg.fail().add("msg", "提交失败，您目前选中的学生数大于您的可选数！");
        }
        //已有的学生数大于本轮可选人数
        if (teacher.getAgreeCount() >= choiceCount) {
            return ResultMsg.fail().add("msg", "提交失败，您的本轮选择人数已满！");
        }
        //现在选中的人数加上已有的人数大于本轮可选人数
        if (studentIds.length + teacher.getAgreeCount() > choiceCount) {
            return ResultMsg.fail().add("msg", "提交失败，选择超额，您目前只可选择" + (choiceCount - teacher.getAgreeCount()) + "个！");
        }

        //同意修改数据库
        for (Integer studentId : studentIds) {
            //老师选择学生的时候已经做过判断了，但是保险起见判断这个学生有没有导师
            LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
            studentLambdaQueryWrapper.eq(Student::getStudentId, studentId);
            Student student = studentService.getOne(studentLambdaQueryWrapper);
            if (student == null) {
                return ResultMsg.fail().add("msg", "操作失败！该生不存在！");
            }
            if (!student.getHasTutor().equals("0")) {
                //学生已经有导师了
                return ResultMsg.fail().add("msg", "学生[" + student.getRealname() + "]已经有导师了！");
            }
            //此学生没有导师，同意该请求，进行修改数据库
            LambdaUpdateWrapper<Requests> requestsLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            requestsLambdaUpdateWrapper
                    .eq(Requests::getStudentId, student.getStudentId())
                    .eq(Requests::getTeacherId, teacher.getTeacherId())
                    .eq(Requests::getRoundId, roundId)
                    .set(Requests::getStatus, 1);
            boolean b = requestsService.update(requestsLambdaUpdateWrapper);
            //保险起见校验是否是同意的本轮请求
            if (!b) {
                throw new RuntimeException("请求错误，包含有非本轮请求内容！");
            }
            //老师同意名额+1
            teacher.setAgreeCount(teacher.getAgreeCount() + 1);
            //同时修改这个学生的数据库信息
            student.setHasTutor(teacher.getRealname());
            studentService.updateById(student);
        }
        //更新老师剩余名额
        teacherService.updateById(teacher);

        return ResultMsg.success();
    }

    //第二轮同意请求
    //还是根据学生id，老师id，轮次id 进行同意请求
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    @RequestMapping(path = "/agreeStudentRequestsAgain")
    public ResultMsg agreeStudentRequestsAgain(@RequestBody AgreeRequestsVO agreeRequestsVO, HttpServletRequest request) {
        //这一次老师的剩余名额就是总名额-已选名额
        //先判断登录状态
        String token = request.getHeader("token");
        //当session中没有这个用户的时候再进行查询
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getToken, token);
        Teacher teacher = teacherService.getOne(queryWrapper);

        if (teacher == null) {
            return ResultMsg.fail().add("msg", "用户未登录");
        }
        //判断老师是否获得选择学生的权利，是否解锁
        if (teacher.getIsLock() == 0) {
            //已上锁
            return ResultMsg.fail().add("msg", "还未轮到您来操作！请耐心等待！");
        }

        //获得学生ids
        Integer[] studentIds = agreeRequestsVO.getStudentIds();
        //获得轮次id
        Long roundId = agreeRequestsVO.getRoundId();

        //当选择的学生大于本轮的可选的次数就返回错误
        Integer totalStudent = teacher.getTotalStudent();//总可选数
        //本轮可选学生数
        Integer choiceCount = totalStudent - teacher.getAgreeCount();
        if (studentIds.length > choiceCount) {
            return ResultMsg.fail().add("msg", "提交失败，您目前选中的学生数大于您的可选数！");
        }
        //已有的学生数大于本轮可选人数
        if (teacher.getAgreeCount() >= totalStudent) {
            return ResultMsg.fail().add("msg", "提交失败，您的总选择名额已满！");
        }
        //现在选中的人数加上已有的人数大于本轮可选人数
        if (studentIds.length + teacher.getAgreeCount() > totalStudent) {
            return ResultMsg.fail().add("msg", "提交失败，选择超额，您目前只可选择" + (totalStudent - teacher.getAgreeCount()) + "个！");
        }

        //同意修改数据库
        for (Integer studentId : studentIds) {
            //老师选择学生的时候已经做过判断了，但是保险起见判断这个学生有没有导师
            LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
            studentLambdaQueryWrapper.eq(Student::getStudentId, studentId);
            Student student = studentService.getOne(studentLambdaQueryWrapper);
            if (student == null) {
                return ResultMsg.fail().add("msg", "操作失败！该生不存在！");
            }
            if (!student.getHasTutor().equals("0")) {
                //学生已经有导师了
                return ResultMsg.fail().add("msg", "学生[" + student.getRealname() + "]已经有导师了！");
            }
            //此学生没有导师，同意该请求，进行修改数据库
            LambdaUpdateWrapper<Requests> requestsLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            requestsLambdaUpdateWrapper
                    .eq(Requests::getStudentId, student.getStudentId())
                    .eq(Requests::getTeacherId, teacher.getTeacherId())
                    .eq(Requests::getRoundId, roundId)
                    .set(Requests::getStatus, 1);
            boolean b = requestsService.update(requestsLambdaUpdateWrapper);
            //保险起见校验是否是同意的本轮请求
            if (!b) {
                throw new RuntimeException("请求错误，包含有非本轮请求内容！");
            }
            //老师同意名额+1
            teacher.setAgreeCount(teacher.getAgreeCount() + 1);
            //同时修改这个学生的数据库信息
            student.setHasTutor(teacher.getRealname());
            studentService.updateById(student);
        }
        //更新老师剩余名额
        teacherService.updateById(teacher);

        return ResultMsg.success();
    }
}
