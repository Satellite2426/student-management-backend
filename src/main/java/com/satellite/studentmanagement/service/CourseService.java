package com.satellite.studentmanagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.satellite.studentmanagement.model.dto.course.CourseAddRequest;
import com.satellite.studentmanagement.model.dto.course.CourseListRequest;
import com.satellite.studentmanagement.model.dto.course.CourseSearchRequest;
import com.satellite.studentmanagement.model.dto.course.CourseUpdateRequest;
import com.satellite.studentmanagement.model.entity.Course;
import com.satellite.studentmanagement.model.vo.CourseVO;


/**
* @author Satellite
* @description 针对表【course(课程表)】的数据库操作Service
*/
public interface CourseService extends IService<Course> {

    /**
     * 查询所有课程列表(分页) - 管理员
     * @param courseListRequest
     * @return
     */
    Page<CourseVO> courseList(CourseListRequest courseListRequest);

    /**
     * 添加课程 (管理员)
     * @param courseAddRequest
     * @return
     */
    Long courseAdd(CourseAddRequest courseAddRequest);

    /**
     * 删除课程 (管理员)
     * @param deleteCourseId
     * @return
     */
    boolean questionDelete(Long deleteCourseId);

    /**
     * 题目更新 (管理员)
     * @param courseUpdateRequest
     * @return
     */
    boolean courseUpdate(CourseUpdateRequest courseUpdateRequest);

    /**
     * 课程搜索
     * @param courseSearchRequest
     * @return
     */
    Page<CourseVO> courseSearch(CourseSearchRequest courseSearchRequest);

    CourseVO domain2VO(Course course);
}
