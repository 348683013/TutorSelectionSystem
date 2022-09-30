package com.cqupt.tutorselectionsystem.teacher.dto;

import lombok.Data;

import java.util.Date;

/**
 * 老师接收到的所有请求DTO
 */
@Data
public class AllReceiveRequestsDTO {
    private String studentNumber;//学生学号
    private String studentName;//学生姓名
    private String telephone;//学生电话
    private String className;//学生班级
    private String receiveTime;//收到请求时间
    private Integer status;//请求处理状态，0未处理，1已同意，2为已拒绝
}
