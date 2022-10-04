package com.cqupt.tutorselectionsystem.admin.dto;

import com.cqupt.tutorselectionsystem.student.domain.Student;
import lombok.Data;

import java.util.List;

@Data
public class TokenStudentDTO {
    private List<Student> records;
    private Long total;
    private Integer size = 12; //每页默认12条数据
}
