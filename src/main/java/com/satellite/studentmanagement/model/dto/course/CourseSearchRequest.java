package com.satellite.studentmanagement.model.dto.course;

import lombok.Data;

import java.io.Serializable;

/**
 * 课程搜索请求类
 *
 * @author Satellite
 */
@Data
public class CourseSearchRequest implements Serializable {

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程编号
     */
    private String courseNum;

    /**
     * 课程任课教师
     */
    private String courseTeacher;

    /**
     * 当前页码 (默认为 1)
     */
    private Integer currentPage = 1;

    /**
     * 每页显示的记录数 (默认为 10)
     */
    private Integer pageSize = 10;

    private static final long serialVersionUID = 1L;
}
