package com.cqupt.tutorselectionsystem.admin.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_admin
 */
@TableName(value ="t_admin")
@Data
public class Admin implements Serializable {
    /**
     * 管理员主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer adminId;

    /**
     * 账号
     */
    private String accountNumber;

    /**
     * 密码
     */
    private String password;

    /**
     * 管理员名称
     */
    private String adminName;

    /**
     * 登录凭证
     */
    private String token;

    /**
     * 是否上锁，0是上锁，默认是1
     */
    private Integer isLock;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}