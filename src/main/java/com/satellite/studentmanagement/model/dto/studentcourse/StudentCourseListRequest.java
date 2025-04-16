package com.satellite.studentmanagement.model.dto.studentcourse;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Satellite
 */
@Data
public class StudentCourseListRequest implements Serializable {

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 学生ID
     */
    private Long userId;

    /**
     * 当前页面, 默认为1
     */
    private Integer currentPage = 1;

    /**
     * 页面每页显示数量, 默认为10
     */
    private Integer pageSize = 10;

    private static final long serialVersionUID = 1L;

}
