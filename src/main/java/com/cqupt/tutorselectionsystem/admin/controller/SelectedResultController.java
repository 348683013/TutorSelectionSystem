package com.cqupt.tutorselectionsystem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqupt.tutorselectionsystem.admin.domain.Admin;
import com.cqupt.tutorselectionsystem.admin.dto.*;
import com.cqupt.tutorselectionsystem.admin.service.AdminService;
import com.cqupt.tutorselectionsystem.student.domain.Requests;
import com.cqupt.tutorselectionsystem.student.domain.Round;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.service.RequestsService;
import com.cqupt.tutorselectionsystem.student.service.RoundService;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.service.TeacherService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Admin/SelectedResult")
public class SelectedResultController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private RequestsService requestsService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RoundService roundService;

    //展示选择结果，同时传输没有导师的学生list
    @ResponseBody
    @RequestMapping(path = "/showSelectedResultByPage")
    public ResultMsg showSelectedResultByPage(@RequestBody ShowAllSAndTPageDTO showAllSAndTPageDTO
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

        //获得所有老师id
        LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teacherLambdaQueryWrapper.select(Teacher::getTeacherId, Teacher::getRealname, Teacher::getTotalStudent, Teacher::getAgreeCount);
        Page<Teacher> page = new Page<>(showAllSAndTPageDTO.getPage(), 11);
        Page<Teacher> teachersByPage = teacherService.page(page, teacherLambdaQueryWrapper);
        List<Teacher> teacherList = teachersByPage.getRecords();
        List<SelectedResultDTO> selectedResultDTOList = new ArrayList<>();
        //查找这个老师的所有学生，根据teacherId和status进行查找
        for (Teacher teacher : teacherList) {
            Integer teacherId = teacher.getTeacherId();
            LambdaQueryWrapper<Requests> requestsLambdaQueryWrapper = new LambdaQueryWrapper<>();
            requestsLambdaQueryWrapper.eq(Requests::getTeacherId, teacherId).eq(Requests::getStatus, 1);
            List<Requests> requestsList = requestsService.list(requestsLambdaQueryWrapper);
            if (requestsList.size() == 0) {
                SelectedResultDTO selectedResultDTO = new SelectedResultDTO();
                selectedResultDTO.setTeacherId(teacher.getTeacherId());
                selectedResultDTO.setTotalStudent(teacher.getTotalStudent());
                selectedResultDTO.setAgreeCount(teacher.getAgreeCount());
                selectedResultDTO.setTeacherName(teacher.getRealname());
                selectedResultDTOList.add(selectedResultDTO);
                continue;
            }
            SelectedResultDTO selectedResultDTO = new SelectedResultDTO();
            selectedResultDTO.setTeacherId(teacher.getTeacherId());
            selectedResultDTO.setTotalStudent(teacher.getTotalStudent());
            selectedResultDTO.setAgreeCount(teacher.getAgreeCount());
            selectedResultDTO.setTeacherName(teacher.getRealname());
            List<String> strings = new ArrayList<>();
            for (Requests requests : requestsList) {
                strings.add(requests.getStudentName());
            }
            selectedResultDTO.setStudentNameList(strings);
            selectedResultDTOList.add(selectedResultDTO);
        }
        Page<SelectedResultDTO> selectedResultDTOPage = new Page<>();
        BeanUtils.copyProperties(teachersByPage, selectedResultDTOPage);
        selectedResultDTOPage.setRecords(selectedResultDTOList);

        //查找没有导师的学生
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(Student::getHasTutor, "0");
        List<Student> noTeacherStudentList = studentService.list(studentLambdaQueryWrapper);
        List<NoTeacherStudentDTO> noTeacherStudentDTOList = new ArrayList<>();
        for (Student student : noTeacherStudentList) {
            NoTeacherStudentDTO noTeacherStudentDTO = new NoTeacherStudentDTO();
            BeanUtils.copyProperties(student, noTeacherStudentDTO);
            noTeacherStudentDTOList.add(noTeacherStudentDTO);
        }

        return ResultMsg.success()
                .add("selectedResultDTOPage", selectedResultDTOPage)
                .add("noTeacherStudentList", noTeacherStudentDTOList);
    }

    //管理员分配学生给老师
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    @RequestMapping(path = "/distribute")
    public ResultMsg distribute(@RequestBody DistributeDTO distributeDTO
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

        try {
            //修改老师的agreeCount，修改之前先判断已经选中的加上这次提交的是否大于老师总名额
            String studentIdsStr = distributeDTO.getStudentIdsStr();
            String[] studentIdsStrArr = studentIdsStr.split(",");
            Integer[] studentIdsIntArr = new Integer[studentIdsStrArr.length];
            for (int i = 0; i < studentIdsStrArr.length; i++) {
                studentIdsIntArr[i] = Integer.parseInt(studentIdsStrArr[i]);
            }
            //给studentIdsIntArr去重
            Set<Integer> studentIdsIntSet = new HashSet<>();
            for (int i = 0; i < studentIdsIntArr.length; i++) {
                studentIdsIntSet.add(studentIdsIntArr[i]);
            }
            Integer[] studentIdsIntArrNoRep = new Integer[studentIdsIntSet.size()];
            Iterator<Integer> iterator = studentIdsIntSet.iterator();
            int k = 0;
            while (iterator.hasNext()) {
                studentIdsIntArrNoRep[k] = iterator.next();
                k++;
            }

            List<DistributeStudentDTO> distributeStudentDTOList = new ArrayList<>();
            for (int i = 0; i < studentIdsIntArrNoRep.length; i++) {
                LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
                studentLambdaQueryWrapper.eq(Student::getStudentId, studentIdsIntArrNoRep[i]);
                Student student = studentService.getOne(studentLambdaQueryWrapper);
                //先看看这个学生是否已经有老师了
                if (!student.getHasTutor().equals("0")) {
                    throw new RuntimeException("其中学生：" + student.getRealname() + "有老师了！");
                }
                if (student == null) {
                    throw new RuntimeException("学生不存在，请按照规定分配！");
                }
                DistributeStudentDTO distributeStudentDTO = new DistributeStudentDTO();
                distributeStudentDTO.setStudentId(student.getStudentId());
                distributeStudentDTO.setStudentName(student.getRealname());
                distributeStudentDTOList.add(distributeStudentDTO);
            }

            Integer teacherId = distributeDTO.getTeacherId();
            String teacherName = distributeDTO.getTeacherName();
            LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
            teacherLambdaQueryWrapper.eq(Teacher::getTeacherId, teacherId);
            Teacher oneTeacher = teacherService.getOne(teacherLambdaQueryWrapper);
            if (oneTeacher.getAgreeCount() + distributeStudentDTOList.size() > oneTeacher.getTotalStudent()) {
                return ResultMsg.fail().add("msg", "分配名额大于总剩余名额！");
            }
            oneTeacher.setAgreeCount(oneTeacher.getAgreeCount() + distributeStudentDTOList.size());
            teacherService.updateById(oneTeacher);

            //修改学生的hasTutor
            //添加新的requests，其中roundId为round表中最后一条数据的id
            //先得到roundId
            LambdaQueryWrapper<Round> roundLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roundLambdaQueryWrapper.orderByDesc(Round::getStartTime).last("limit 1");
            Round oneRound = roundService.getOne(roundLambdaQueryWrapper);
            Long roundId = oneRound.getRoundId();
            for (DistributeStudentDTO distributeStudentDTO : distributeStudentDTOList) {
                LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
                studentLambdaQueryWrapper.eq(Student::getStudentId, distributeStudentDTO.getStudentId());
                Student oneStudent = studentService.getOne(studentLambdaQueryWrapper);
                oneStudent.setHasTutor(teacherName);
                studentService.updateById(oneStudent);//更新学生的导师信息
                //向requests表中写入数据
                Requests requests = new Requests();
                requests.setStudentId(distributeStudentDTO.getStudentId());
                requests.setStudentName(distributeStudentDTO.getStudentName());
                requests.setTeacherId(teacherId);
                requests.setTeacherName(teacherName);
                requests.setStatus(1);
                requests.setRoundId(roundId);
                requests.setSendTime(new Date());
                requestsService.save(requests);
            }

        } catch (Exception e) {
            throw new RuntimeException("分配失败！");
        }
        return ResultMsg.fail().add("msg", "分配成功！");
    }
}
