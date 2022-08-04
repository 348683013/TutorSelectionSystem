package com.cqupt.tutorselectionsystem.student.dto;

import lombok.Data;

@Data
public class ShowTeacherSearchDTO {
    private Integer limit = 8;
    private Integer page = 1;
    private String nameKeyWord; //根据姓名查找
    private String sexKeyWord; //根据性别查找
}
