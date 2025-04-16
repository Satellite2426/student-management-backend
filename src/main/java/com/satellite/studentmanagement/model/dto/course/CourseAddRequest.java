package com.satellite.studentmanagement.model.dto.course;


import lombok.Data;

import java.io.Serializable;

/**
 * 课程添加请求类
 *
 * @author Satellite
 */
@Data
public class CourseAddRequest implements Serializable {


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

    private static final long serialVersionUID = 1L;
}
