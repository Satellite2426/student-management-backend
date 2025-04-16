package com.satellite.studentmanagement.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程表
 *
 * @TableName course
 * @author Satellite
 */
@TableName(value ="course")
@Data
public class Course implements Serializable {
    /**
     * 课程ID
     */
    @TableId(type = IdType.AUTO)
    private Long courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程编号
     */
    private String courseNum;

    /**
     * 课程简介
     */
    private String courseDescription;

    /**
     * 课程学时
     */
    private String courseTimes;

    /**
     * 任课教师
     */
    private String courseTeacher;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删除,1-已删除)
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}