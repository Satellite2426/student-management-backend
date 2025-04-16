package com.satellite.studentmanagement.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Satellite
 */
@Data
public class StudentCourseVO implements Serializable {
    /**
     * 学生选课 ID
     */
    private Long id;

    /**
     * 学生选课关联的 userVO
     */
    private UserVO userVO;

    /**
     * 学生选课关联的 courseVO
     */
    private CourseVO courseVO;



    private static final long serialVersionUID = 1L;
}
