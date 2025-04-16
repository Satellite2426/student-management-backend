package com.satellite.studentmanagement.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Satellite
 */
@Data
public class CourseVO implements Serializable {
    /**
     * 课程ID
     */
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

    private static final long serialVersionUID = 1L;
}