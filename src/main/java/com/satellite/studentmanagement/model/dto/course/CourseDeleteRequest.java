package com.satellite.studentmanagement.model.dto.course;

import lombok.Data;

import java.io.Serializable;

/**
 * 课程删除请求类
 *
 * @author Satellite
 */
@Data
public class CourseDeleteRequest implements Serializable {

    /**
     * 课程 ID
     */
    private Long courseId;

    private static final long serialVersionUID = 1L;
}
