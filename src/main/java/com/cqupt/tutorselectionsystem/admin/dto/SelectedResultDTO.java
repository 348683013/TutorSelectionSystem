package com.cqupt.tutorselectionsystem.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class SelectedResultDTO {
    private Integer teacherId;
    private String teacherName;
    private Integer totalStudent;
    private Integer agreeCount;
    private List<String> studentNameList;
}
