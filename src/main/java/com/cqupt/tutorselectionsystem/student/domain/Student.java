package com.cqupt.tutorselectionsystem.student.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_student
 */
@TableName(value ="t_student")
@Data
public class Student implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long studentId;

    /**
     * 账号，一般是学号
     */
    private String accountNumber;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 班级id
     */
    private Long classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 是否有导师，没有导师为0，有导师就是导师姓名
     */
    private String hasTutor;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 是否加入科研办，0表示没有，1表示有
     */
    private Integer hasScienceClass;

    /**
     * 用户token身份校验
     */
    private String token;

    /**
     * 逻辑删除，默认是0没删除
     */
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}