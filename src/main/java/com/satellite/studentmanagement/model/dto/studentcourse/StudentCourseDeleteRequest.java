package com.satellite.studentmanagement.model.dto.studentcourse;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Satellite
 */
@Data
public class StudentCourseDeleteRequest implements Serializable {

    /**
     * 学生选课 ID
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
