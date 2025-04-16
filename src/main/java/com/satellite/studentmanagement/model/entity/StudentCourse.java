package com.satellite.studentmanagement.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 学生选课表
 *
 * @TableName student_course
 * @author Satellite
 */
@TableName(value ="student_course")
@Data
public class StudentCourse implements Serializable {
    /**
     * 学生选课ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程编号
     */
    private String courseNum;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 学生ID
     */
    private Long userId;

    /**
     * 是否删除(0-未删除,1-已删除)
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}