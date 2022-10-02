package com.cqupt.tutorselectionsystem.student.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.service.TeacherService;
import com.cqupt.tutorselectionsystem.student.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

/**
* @author pc
* @description 针对表【t_teacher】的数据库操作Service实现
* @createDate 2022-10-02 19:47:00
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{

}




