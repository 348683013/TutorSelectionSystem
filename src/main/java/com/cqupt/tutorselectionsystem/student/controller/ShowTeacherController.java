package com.cqupt.tutorselectionsystem.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.dto.ShowAllTeacherDTO;
import com.cqupt.tutorselectionsystem.student.dto.ShowTeacherSearchDTO;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.service.TeacherService;
import com.cqupt.tutorselectionsystem.student.utils.CalculateAge;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Student/ShowTeacher")
public class ShowTeacherController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(path = "/findAllTeachers")
    @ResponseBody
    public ResultMsg findAllTeachers(/*@RequestParam(value = "limit", defaultValue = "8") Integer limit,
                                     @RequestParam(value = "page", defaultValue = "1") Integer pc,
                                     @RequestParam(value = "nameKeyWord", defaultValue = "") String nameKeyWord*/
            @RequestBody ShowTeacherSearchDTO showTeacherSearchDTO
    ) {

        Page<Teacher> page = new Page<>(showTeacherSearchDTO.getPage(), showTeacherSearchDTO.getLimit());
        //条件
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Teacher::getRealname, showTeacherSearchDTO.getNameKeyWord());
        Page<Teacher> teachersPage = teacherService.page(page, lambdaQueryWrapper);

        //取出teachers中的List<T> records
        List<Teacher> records = teachersPage.getRecords();
        /*for (Teacher record : records) {
            record.setAccountNumber("");
            record.setPassword("");
        }
        teachersPage.setRecords(records);*/

        //想把ShowAllTeacherDTO的list传过去，而不是传Teacher的list
        List<ShowAllTeacherDTO> showAllTeacherDTOList = new ArrayList<>();
        for (Teacher teacher : records) {
            ShowAllTeacherDTO showAllTeacherDTO = new ShowAllTeacherDTO();
            BeanUtils.copyProperties(teacher, showAllTeacherDTO);
            //根据生日计算每个老师的年龄
            Integer age = CalculateAge.getAge(teacher.getBirthday());
            showAllTeacherDTO.setAge(age.toString());
            showAllTeacherDTOList.add(showAllTeacherDTO);
        }
        Page<ShowAllTeacherDTO> showAllTeacherDTOPage = new Page<>();
        BeanUtils.copyProperties(teachersPage, showAllTeacherDTOPage);
        showAllTeacherDTOPage.setRecords(showAllTeacherDTOList);

        return ResultMsg.success().add("teachersInfo", showAllTeacherDTOPage);
    }

    //根据性别查找
    @RequestMapping(path = "/findAllTeachersBySex")
    @ResponseBody
    public ResultMsg findAllTeachersBySex(@RequestBody ShowTeacherSearchDTO showTeacherSearchDTO) {
        if (!(showTeacherSearchDTO.getSexKeyWord().equals("男") || showTeacherSearchDTO.getSexKeyWord().equals("女"))) {
            return ResultMsg.fail().add("errorMsg", "关键字输入有误！");
        }

        Page<Teacher> page = new Page<>(showTeacherSearchDTO.getPage(), showTeacherSearchDTO.getLimit());
        //条件
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //接收过来的性别
        String sex = showTeacherSearchDTO.getSexKeyWord();
        lambdaQueryWrapper.eq(Teacher::getSex, sex.equals("男") ? 0 : 1);
        Page<Teacher> teachersPage = teacherService.page(page, lambdaQueryWrapper);

        //取出teachers中的List<T> records
        List<Teacher> records = teachersPage.getRecords();

        //想把ShowAllTeacherDTO的list传过去，而不是传Teacher的list
        List<ShowAllTeacherDTO> showAllTeacherDTOList = new ArrayList<>();
        for (Teacher teacher : records) {
            ShowAllTeacherDTO showAllTeacherDTO = new ShowAllTeacherDTO();
            BeanUtils.copyProperties(teacher, showAllTeacherDTO);
            //根据生日计算每个老师的年龄
            Integer age = CalculateAge.getAge(teacher.getBirthday());
            showAllTeacherDTO.setAge(age.toString());
            showAllTeacherDTOList.add(showAllTeacherDTO);
        }
        Page<ShowAllTeacherDTO> showAllTeacherDTOPage = new Page<>();
        BeanUtils.copyProperties(teachersPage, showAllTeacherDTOPage);
        showAllTeacherDTOPage.setRecords(showAllTeacherDTOList);

        return ResultMsg.success().add("teachersInfo", showAllTeacherDTOPage);
    }
}
