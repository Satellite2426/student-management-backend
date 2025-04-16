package com.satellite.studentmanagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.satellite.studentmanagement.model.dto.studentcourse.StudentCourseAddRequest;
import com.satellite.studentmanagement.model.dto.studentcourse.StudentCourseListRequest;
import com.satellite.studentmanagement.model.entity.StudentCourse;
import com.satellite.studentmanagement.model.vo.StudentCourseVO;


import javax.servlet.http.HttpSession;

/**
* @author Satellite
* @description 针对表【student_course(学生选课表)】的数据库操作Service
*/
public interface StudentCourseService extends IService<StudentCourse> {

    /**
     * 学生选课
     * @param studentCourseAddRequest
     * @param session
     * @return
     */
    boolean doStudentCourse(StudentCourseAddRequest studentCourseAddRequest, HttpSession session);

    /**
     * 查询所有选课列表 (管理员)
     * @param studentCourseListRequest
     * @return
     */
    Page<StudentCourseVO> studentCourseListAdmin(StudentCourseListRequest studentCourseListRequest);

    /**
     * 查询当前登录用户(学生)的选课列表
     * @param studentCourseListRequest
     * @param session
     * @return
     */
    Page<StudentCourseVO> studentCourseList(StudentCourseListRequest studentCourseListRequest, HttpSession session);

    /**
     * 删除学生选课信息 (管理员)
     * @param deleteStudentCourseId
     * @return
     */
    boolean studentCourseDelete(Long deleteStudentCourseId);
}
