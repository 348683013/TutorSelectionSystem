package com.cqupt.tutorselectionsystem.student.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_requests
 */
@TableName(value ="t_requests")
@Data
public class Requests implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long requestId;

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 老师id
     */
    private Integer teacherId;

    /**
     * 老师姓名
     */
    private String teacherName;

    /**
     * 请求发送时间
     */
    private Date sendTime;

    /**
     * 请求状态，0未处理，1已同意，2为已拒绝
     */
    private Integer status;

    /**
     * 第几轮id
     */
    private Long roundId;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}