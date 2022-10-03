package com.cqupt.tutorselectionsystem.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqupt.tutorselectionsystem.student.domain.Requests;
import com.cqupt.tutorselectionsystem.student.domain.Round;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.dto.RequestedInfoDTO;
import com.cqupt.tutorselectionsystem.student.dto.RoundInfoDTO;
import com.cqupt.tutorselectionsystem.student.service.RequestsService;
import com.cqupt.tutorselectionsystem.student.service.RoundService;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.service.TeacherService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import com.cqupt.tutorselectionsystem.student.utils.TimeUtil;
import com.cqupt.tutorselectionsystem.student.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private RequestsService requestsService;

    @Autowired
    private TeacherService teacherService;

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
    @PostMapping(path = "/findStudentByToken")
    @ResponseBody
    public ResultMsg getStudentByToken(HttpSession session, HttpServletRequest request) throws ParseException {
        String token = request.getHeader("token"); //从请求头中获取这个token的值
        //System.out.println("============"+token);
        //先从session中取这个用户信息
        Student student = (Student) session.getAttribute(token);
        if (student == null) {
            //当session中没有这个用户的时候再进行查询
            LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Student::getToken, token);
            student = studentService.getOne(queryWrapper);
            //查询出来之后再次放入session
            if (student != null) {
                session.setAttribute(token, student);
            }
        }

        if(student == null){
            return ResultMsg.fail().add("msg", "用户未登录！");
        }

        //得到现在是第几轮选择导师
        LambdaQueryWrapper<Round> roundLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roundLambdaQueryWrapper.ne(Round::getIsStart, 0);
        Round round = roundService.getOne(roundLambdaQueryWrapper);

        String roundName;
        if (round != null) {
            roundName = round.getName();
            roundName = roundName.substring(0, 4) + "年第" + roundName.substring(5) + "轮";
        } else {
            roundName = "系统尚未开启！";
            RoundInfoDTO roundInfoDTO = new RoundInfoDTO();
            roundInfoDTO.setRoundId(0L);//id
            roundInfoDTO.setName(roundName);//第几轮
            roundInfoDTO.setStopTime("");
            roundInfoDTO.setIsStatus(0);
            return ResultMsg.success()
                    .add("studentInfo", student)
                    .add("roundInfo", roundInfoDTO);
        }

        //得到这个学生在本轮给哪些老师发送过请求
        LambdaQueryWrapper<Requests> requestsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        requestsLambdaQueryWrapper.eq(Requests::getStudentId, student.getStudentId()).eq(Requests::getRoundId, round.getRoundId());
        List<Requests> requestsList = requestsService.list(requestsLambdaQueryWrapper);
        //我们只需要里面的teacherIds
        Integer[] teacherIds = new Integer[requestsList.size()];
        for (int i = 0; i < requestsList.size(); i++) {
            Integer teacherId = requestsList.get(i).getTeacherId();
            teacherIds[i] = teacherId;
        }

        //写入到数据库后，因为学生前端页面需要展示发送请求的信息，所以需要查询出来返回给前端
        List<RequestedInfoDTO> showStudentSendRequests = new ArrayList<>();
        //根据学生id查找他在本轮中发送的所有请求
        /*LambdaQueryWrapper<Requests> stuIdAndRoundIdLambdaQueryWrapper = new LambdaQueryWrapper<>();
        stuIdAndRoundIdLambdaQueryWrapper.eq(Requests::getStudentId, student.getStudentId());//改成了显示这个学生的所有请求信息，不用roundId作为查询条件了
        List<Requests> requestsByStuIdAndRoundId = requestsService.list(stuIdAndRoundIdLambdaQueryWrapper);*/


        RoundInfoDTO roundInfoDTO = new RoundInfoDTO();
        roundInfoDTO.setRoundId(round == null ? 0 : round.getRoundId());//id
        roundInfoDTO.setName(roundName);//第几轮
        String stopTime = round == null ? "" : TimeUtil.formatDateByPattern(round.getStopTime(), "yyyy-MM-dd HH:mm:ss");
        roundInfoDTO.setStopTime(stopTime);
        roundInfoDTO.setIsStatus(round.getIsStart());

        //把这个查询到的信息封装到DTO中
        for (Requests request1 : requestsList) {
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

        return ResultMsg.success()
                .add("studentInfo", student)
                .add("roundInfo", roundInfoDTO)
                .add("teacherIds", teacherIds)
                .add("showStudentSendRequests", showStudentSendRequests);
    }
}
