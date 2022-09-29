package com.cqupt.tutorselectionsystem.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqupt.tutorselectionsystem.student.domain.Requests;
import com.cqupt.tutorselectionsystem.student.domain.Round;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.dto.RequestDTO;
import com.cqupt.tutorselectionsystem.student.dto.HasSpareTeacherDTO;
import com.cqupt.tutorselectionsystem.student.dto.RequestedInfoDTO;
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
import java.text.ParseException;
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

    //第一、二轮选择导师
    //在第二轮中，第一轮导师没有同意的，除了已经拒绝的，要把未处理的（即状态为0）的设置成拒绝（即设置成状态为2），这一步设置是管理员在关闭第一轮选择的时候自动进行设置
    //接收前端传来的请求导师列表
    @RequestMapping(path = "/sendRequest")
    @ResponseBody
    public ResultMsg sendRequest(@RequestBody RequestDTO requestDTO,
                                 HttpServletRequest request,
                                 HttpSession session) throws ParseException {
        //得到登录状态的学生信息
        String token = request.getHeader("token"); //从请求头中获取这个token的值
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getToken, token);
        Student student = studentService.getOne(queryWrapper);
        //查询出来之后再次放入session
        session.setAttribute(token, student);
        //得到第几轮id
        Long roundId = requestDTO.getRoundId();
        //得到发送过来老师id的int数组
        Integer[] teacherIds = requestDTO.getCheckId();

        //判断这个人是否已经有导师了
        if (!student.getHasTutor().equals("0")) {
            return ResultMsg.fail().add("errorMsg", "该学生已有导师，禁止申请！");
        }

        //判断发送过来的id数组是否大于5
        if (teacherIds.length > 5 || teacherIds.length <= 0) {
            return ResultMsg.fail().add("errorMsg", "请求数量异常！");
        }

        //判断是否是重复发送请求，同时判断是否还剩名额，
        //先获得这个学生发送过得所有请求list——仅限在本轮中
        LambdaQueryWrapper<Requests> requestsLambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        requestsLambdaQueryWrapper1.eq(Requests::getStudentId, student.getStudentId()).eq(Requests::getRoundId, roundId);
        List<Requests> requestsList1 = requestsService.list(requestsLambdaQueryWrapper1);
        //如果总请求数大于5则抛错
        if (requestsList1.size() + teacherIds.length > 5) {
            return ResultMsg.fail().add("errorMsg", "本轮你还剩" + (5 - requestsList1.size()) + "次发送请求机会！");
        }
        //判断是否有重复发送
        //得到发送过的老师的id的list
        List<Integer> teacherIdList = new ArrayList<>();
        for (Requests requests : requestsList1) {
            teacherIdList.add(requests.getTeacherId());
        }
        for (Integer teacherId : teacherIds) {
            if (teacherIdList.contains(teacherId)) {
                return ResultMsg.fail().add("errorMsg", "存在给同一导师发送多次请求！");
            }
        }

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

        //写入到数据库后，因为学生前端页面需要展示发送请求的信息，所以需要查询出来返回给前端
        List<RequestedInfoDTO> showStudentSendRequests = new ArrayList<>();
        //根据学生id查找他在本轮中发送的所有请求
        LambdaQueryWrapper<Requests> stuIdAndRoundIdLambdaQueryWrapper = new LambdaQueryWrapper<>();
        stuIdAndRoundIdLambdaQueryWrapper.eq(Requests::getStudentId, student.getStudentId()).eq(Requests::getRoundId, roundId);
        List<Requests> requestsByStuIdAndRoundId = requestsService.list(stuIdAndRoundIdLambdaQueryWrapper);
        //查询轮次
        LambdaQueryWrapper<Round> roundLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roundLambdaQueryWrapper.eq(Round::getRoundId, roundId);
        Round round = roundService.getOne(roundLambdaQueryWrapper);
        String roundName = round.getName();
        roundName = roundName.substring(0, 4) + "年第" + roundName.substring(5) + "轮";
        //把这个查询到的信息封装到DTO中
        for (Requests request1 : requestsByStuIdAndRoundId) {
            RequestedInfoDTO requestedInfoDTO = new RequestedInfoDTO();
            requestedInfoDTO.setStudentName(request1.getStudentName());
            requestedInfoDTO.setTeacherName(request1.getTeacherName());
            requestedInfoDTO.setRequestTime(TimeUtil.dateToYMD(request1.getSendTime()));
            requestedInfoDTO.setStatus(request1.getStatus());
            requestedInfoDTO.setRoundName(roundName);
            //根据老师id查询老师剩余名额和地址
            LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
            teacherLambdaQueryWrapper.eq(Teacher::getTeacherId, request1.getTeacherId());
            Teacher teacher = teacherService.getOne(teacherLambdaQueryWrapper);
            requestedInfoDTO.setTeacherAddress(teacher.getAddress());
            requestedInfoDTO.setTeacherRestCount(teacher.getTotalStudent());
            showStudentSendRequests.add(requestedInfoDTO);
        }

        return ResultMsg.success().add("showStudentSendRequests", showStudentSendRequests);
    }

}
