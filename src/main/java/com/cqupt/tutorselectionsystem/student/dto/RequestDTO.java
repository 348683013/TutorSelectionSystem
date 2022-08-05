package com.cqupt.tutorselectionsystem.student.dto;

import lombok.Data;

@Data
public class RequestDTO {
    private Integer[] checkId; //这个变量就是teachersIds，发过来的老师id数组
    private Long roundId;
}
