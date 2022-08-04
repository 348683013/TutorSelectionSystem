package com.cqupt.tutorselectionsystem.student.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.mapper.StudentMapper;
import org.springframework.stereotype.Service;

/**
* @author pc
* @description 针对表【t_student】的数据库操作Service实现
* @createDate 2022-08-01 15:31:14
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

}




