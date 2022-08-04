package com.cqupt.tutorselectionsystem.student.dto;

import lombok.Data;

@Data
public class ShowTeacherSearchDTO {
    private Integer limit = 8;
    private Integer page = 1;
    private String nameKeyWord;
}
