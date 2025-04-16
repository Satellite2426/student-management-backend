package com.satellite.studentmanagement.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.satellite.studentmanagement.annotation.CheckLogin;
import com.satellite.studentmanagement.common.BaseResponse;
import com.satellite.studentmanagement.common.ResultUtils;
import com.satellite.studentmanagement.constant.UserRole;
import com.satellite.studentmanagement.exception.BusinessException;
import com.satellite.studentmanagement.model.dto.course.*;
import com.satellite.studentmanagement.model.enums.ErrorCode;
import com.satellite.studentmanagement.model.vo.CourseVO;
import com.satellite.studentmanagement.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 课程接口
 *
 * @author Satellite
 */
@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController {

    @Resource
    private CourseService courseService;

    // 查询所有课程列表
    @PostMapping("/list")
    public BaseResponse<Page<CourseVO>> courseList(@RequestBody CourseListRequest courseListRequest) {
        // 参数校验
        Integer currentPage = courseListRequest.getCurrentPage();
        Integer pageSize = courseListRequest.getPageSize();
        if (currentPage < 0 || pageSize < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(courseService.courseList(courseListRequest));

    }

    /**
     * 添加课程 (管理员)
     * @param courseAddRequest
     * @return
     */
    @PostMapping("/add")
    @CheckLogin(UserRole.ADMIN)
    public BaseResponse<Long> courseAdd(@RequestBody CourseAddRequest courseAddRequest) {
        // 参数检验
        String courseName = courseAddRequest.getCourseName();
        String courseNum = courseAddRequest.getCourseNum();
        String courseDescription = courseAddRequest.getCourseDescription();
        String courseTimes = courseAddRequest.getCourseTimes();
        String courseTeacher = courseAddRequest.getCourseTeacher();
        if (StringUtils.isAnyBlank(courseName, courseNum, courseDescription, courseTimes, courseTeacher)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "必填信息不能为空");
        }

        return ResultUtils.success(courseService.courseAdd(courseAddRequest));
    }

    /**
     * 删除课程 (管理员)
     * @param courseDeleteRequest
     * @return
     */
    @PostMapping("/delete")
    @CheckLogin(UserRole.ADMIN)
    public BaseResponse<Boolean> courseDelete(@RequestBody CourseDeleteRequest courseDeleteRequest) {
        // 参数检验
        Long deleteCourseId = courseDeleteRequest.getCourseId();
        if (deleteCourseId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "必填参数不能为空");
        }
        return ResultUtils.success(courseService.questionDelete(deleteCourseId));
    }

    /**
     * 题目更新 (管理员)
     * @param courseUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @CheckLogin(UserRole.ADMIN)
    public BaseResponse<Boolean> courseUpdate(@RequestBody CourseUpdateRequest courseUpdateRequest) {
        // 参数检验
        Long courseId = courseUpdateRequest.getCourseId();
        if (courseId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(courseService.courseUpdate(courseUpdateRequest));
    }

    /**
     * 课程搜索
     * @param courseSearchRequest
     * @return
     */
    @PostMapping("/search")
    public BaseResponse<Page<CourseVO>> searchCourse(@RequestBody CourseSearchRequest courseSearchRequest) {
        // 参数检验
        Integer currentPage = courseSearchRequest.getCurrentPage();
        Integer pageSize = courseSearchRequest.getPageSize();
        if (currentPage < 0 || pageSize < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(courseService.courseSearch(courseSearchRequest));
    }






}
