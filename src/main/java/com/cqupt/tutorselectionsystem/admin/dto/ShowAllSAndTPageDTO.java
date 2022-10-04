package com.cqupt.tutorselectionsystem.admin.dto;

import lombok.Data;

@Data
public class ShowAllSAndTPageDTO {
    private Integer limit = 12;
    private Integer page = 1;
}
