package com.satellite.studentmanagement.model.dto.course;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Satellite
 */
@Data
public class CourseListRequest implements Serializable {
    /**
     * 当前页码, 默认为 1
     */
    private Integer currentPage = 1;

    /**
     * 页面尺寸, 默认为 10
     */
    private Integer pageSize = 10;

    private static final long serialVersionUID = 1L;

}
