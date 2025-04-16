package com.satellite.studentmanagement.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.satellite.studentmanagement.annotation.CheckLogin;
import com.satellite.studentmanagement.common.BaseResponse;
import com.satellite.studentmanagement.common.ResultUtils;
import com.satellite.studentmanagement.constant.UserRole;
import com.satellite.studentmanagement.exception.BusinessException;
import com.satellite.studentmanagement.model.dto.studentcourse.StudentCourseAddRequest;
import com.satellite.studentmanagement.model.dto.studentcourse.StudentCourseDeleteRequest;
import com.satellite.studentmanagement.model.dto.studentcourse.StudentCourseListRequest;
import com.satellite.studentmanagement.model.enums.ErrorCode;
import com.satellite.studentmanagement.model.vo.StudentCourseVO;
import com.satellite.studentmanagement.service.StudentCourseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


/**
 * 学生选课接口
 *
 * @author Satellite
 */
@RestController
@RequestMapping("/student_course")
@Slf4j
public class StudentCourseController {

    @Resource
    private StudentCourseService studentCourseService;

    /**
     * 学生选课 (需要登录)
     * @param studentCourseAddRequest
     * @return
     */
    @PostMapping("/do")
    @CheckLogin
    public BaseResponse<Boolean> doStudentCourse(@RequestBody StudentCourseAddRequest studentCourseAddRequest, HttpSession session) {
        // 参数检验
        String courseName = studentCourseAddRequest.getCourseName();
        String courseNum = studentCourseAddRequest.getCourseNum();
        Long courseId = studentCourseAddRequest.getCourseId();
        Long userId = studentCourseAddRequest.getUserId();
        if (userId == null || userId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (courseId == null || courseId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isAnyBlank(courseName, courseNum)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return ResultUtils.success(studentCourseService.doStudentCourse(studentCourseAddRequest, session));
    }

    /**
     * 管理员: 查询所有选课课程列表
     * @param studentCourseListRequest
     * @return
     */
    @PostMapping("/list/admin")
    @CheckLogin(UserRole.ADMIN)
    public BaseResponse<Page<StudentCourseVO>> studentCourseListAdmin(@RequestBody StudentCourseListRequest studentCourseListRequest) {
        // 参数校验
        Integer currentPage = studentCourseListRequest.getCurrentPage();
        Integer pageSize = studentCourseListRequest.getPageSize();

        if (currentPage < 0 || pageSize < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(studentCourseService.studentCourseListAdmin(studentCourseListRequest));

    }

    /**
     * 学生: 查询自己的选课列表
     * @param studentCourseListRequest
     * @param session
     * @return
     */
    @PostMapping("/list")
    @CheckLogin
    public BaseResponse<Page<StudentCourseVO>> studentCourseList(@RequestBody StudentCourseListRequest studentCourseListRequest, HttpSession session) {
        // 参数校验
        Integer currentPage = studentCourseListRequest.getCurrentPage();
        Integer pageSize = studentCourseListRequest.getPageSize();
        if (currentPage < 0 || pageSize < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(studentCourseService.studentCourseList(studentCourseListRequest, session));

    }


    /**
     * 删除学生课程信息 (管理员)
     * @param studentCourseDeleteRequest
     * @return
     */
    @PostMapping("/delete")
    @CheckLogin(UserRole.ADMIN)
    public BaseResponse<Boolean> courseDelete(@RequestBody StudentCourseDeleteRequest studentCourseDeleteRequest) {
        // 参数检验
        Long deleteStudentCourseId = studentCourseDeleteRequest.getId();
        if (deleteStudentCourseId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "必填参数不能为空");
        }
        return ResultUtils.success(studentCourseService.studentCourseDelete(deleteStudentCourseId));
    }


}
