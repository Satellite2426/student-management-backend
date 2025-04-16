package com.satellite.studentmanagement.model.dto.studentcourse;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Satellite
 */
@Data
public class StudentCourseAddRequest implements Serializable {

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

    private static final long serialVersionUID = 1L;
}
