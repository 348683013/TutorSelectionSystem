package com.cqupt.tutorselectionsystem.student.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_round
 */
@TableName(value ="t_round")
@Data
public class Round implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long roundId;

    /**
     * 轮次名称，指明哪一轮
     */
    private String name;

    /**
     * 是否开启，0未开启，1开启，所有记录中只能有一个是开启状态
     */
    private Integer isStart;

    /**
     * 发起时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date stopTime;

    /**
     * 逻辑删除
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}