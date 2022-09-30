package com.cqupt.tutorselectionsystem.teacher.dto;

import lombok.Data;

@Data
public class ShowAllRequestsPageDTO {
    private Integer limit = 12;
    private Integer page = 1;
    private String nameKeyWord; //根据姓名查找
    private String sexKeyWord; //根据性别查找
}
