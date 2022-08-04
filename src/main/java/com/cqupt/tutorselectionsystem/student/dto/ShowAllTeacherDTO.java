package com.cqupt.tutorselectionsystem.student.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ShowAllTeacherDTO {

    private Integer teacherId;

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
    private Date birthday;

    //年龄
    private String age;

}