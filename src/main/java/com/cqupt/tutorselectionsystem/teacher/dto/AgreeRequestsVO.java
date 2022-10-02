package com.cqupt.tutorselectionsystem.teacher.dto;

import lombok.Data;

@Data
public class AgreeRequestsVO {
    private Integer[] studentIds;
    private Long roundId;
}
