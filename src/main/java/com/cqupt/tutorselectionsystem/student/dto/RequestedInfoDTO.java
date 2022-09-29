package com.cqupt.tutorselectionsystem.student.dto;

import lombok.Data;

import java.util.Date;

/**
 * 这个类是学生查看自己的选择结果内容
 */
@Data
public class RequestedInfoDTO {
    private String studentName;//学生姓名
    private String roundName; //选择轮次
    private String teacherName;//老师姓名
    private Integer teacherRestCount;//老师剩余名额
    private String teacherAddress;//老师联系地址
    private String requestTime;//请求发送时间
    private int status;//请求的处理状态，0未处理，1已同意，2为已拒绝
}
