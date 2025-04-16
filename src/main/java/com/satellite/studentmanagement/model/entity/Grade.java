package com.satellite.studentmanagement.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 学生成绩表
 *
 * @TableName grade
 * @author Satellite
 */
@TableName(value ="grade")
@Data
public class Grade implements Serializable {
    /**
     * 学生成绩ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 学生ID
     */
    private Long userId;

    /**
     * 成绩
     */
    private Double score;

    /**
     * 管理员/教师评语
     */
    private String comment;

    /**
     * 学生反馈/评价
     */
    private String feedback;

    /**
     * 是否删除(0-未删除,1-已删除)
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}