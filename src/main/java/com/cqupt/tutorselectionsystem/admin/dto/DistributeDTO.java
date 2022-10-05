package com.cqupt.tutorselectionsystem.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class DistributeDTO {
    private Integer teacherId;
    private String teacherName;
    private String studentIdsStr;
}
