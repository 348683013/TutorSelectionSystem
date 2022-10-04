package com.cqupt.tutorselectionsystem.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class SelectedResultDTO {
    private String teacherName;
    private List<String> studentNameList;
}
