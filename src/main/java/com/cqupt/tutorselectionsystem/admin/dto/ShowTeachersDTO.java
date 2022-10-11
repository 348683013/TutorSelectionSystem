package com.cqupt.tutorselectionsystem.admin.dto;

import lombok.Data;

@Data
public class ShowTeachersDTO {
    private Integer teacherId;

    /**
     * 教师账号
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
     * 所属学院
     */
    private Integer collegeId;

    /**
     * 所属学院名称
     */
    private String collegeName;

    /**
     * 职称
     */
    private String teacherType;

    /**
     * 老师详细信息
     */
    private String description;

    /**
     * 剩余能够带领的学生数量
     */
    private Integer totalStudent;

    /**
     * 已同意学生数，默认是0
     */
    private Integer agreeCount;

    /**
     * 照片路径
     */
    private String headImage;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 性别，默认是男0，女士1
     */
    private Integer sex;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 登录验证token
     */
    private String token;

    /**
     * 是否上锁，0是上锁，1是未上锁，默认上锁，上锁时候不能进行选择学生操作
     */
    private Integer isLock;

    /**
     * 本轮未处理请求数
     */
    private Long untreatedRequestsCount;

}
