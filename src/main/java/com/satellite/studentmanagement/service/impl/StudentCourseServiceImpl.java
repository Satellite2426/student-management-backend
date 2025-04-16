package com.satellite.studentmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satellite.studentmanagement.constant.UserConstant;
import com.satellite.studentmanagement.exception.BusinessException;
import com.satellite.studentmanagement.mapper.StudentCourseMapper;
import com.satellite.studentmanagement.model.dto.studentcourse.StudentCourseAddRequest;
import com.satellite.studentmanagement.model.dto.studentcourse.StudentCourseListRequest;
import com.satellite.studentmanagement.model.entity.StudentCourse;
import com.satellite.studentmanagement.model.enums.ErrorCode;
import com.satellite.studentmanagement.model.vo.StudentCourseVO;
import com.satellite.studentmanagement.model.vo.UserVO;
import com.satellite.studentmanagement.service.CourseService;
import com.satellite.studentmanagement.service.StudentCourseService;
import com.satellite.studentmanagement.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Satellite
* @description 针对表【student_course(学生选课表)】的数据库操作Service实现
*/
@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse>
    implements StudentCourseService {

    @Resource
    private UserService userService;

    @Resource
    private CourseService courseService;

    /**
     * 学生选课
     * @param studentCourseAddRequest
     * @param session
     * @return
     */
    @Override
    public boolean doStudentCourse(StudentCourseAddRequest studentCourseAddRequest, HttpSession session) {
        // 当前登录用户
        UserVO loginUserVO = (UserVO) session.getAttribute(UserConstant.USER_LOGIN_STATE);

        String courseName = studentCourseAddRequest.getCourseName();
        String courseNum = studentCourseAddRequest.getCourseNum();
        Long courseId = studentCourseAddRequest.getCourseId();

        // 判断是否已经选过这门课程
        LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentCourse::getUserId, loginUserVO.getUserId())
                .eq(StudentCourse::getCourseId, courseId)
                .eq(StudentCourse::getIsDelete, 0);
        StudentCourse existing = this.getOne(queryWrapper);
        if (existing != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "您已经选过这门课程了");
        }
        StudentCourse sc = new StudentCourse();
        sc.setCourseName(courseName);
        sc.setCourseNum(courseNum);
        sc.setCourseId(courseId);
        sc.setUserId(loginUserVO.getUserId());

        return this.save(sc);
    }

    /**
     * 查询所有选课列表 (管理员)
     * @param studentCourseListRequest
     * @return
     */
    @Override
    public Page<StudentCourseVO> studentCourseListAdmin(StudentCourseListRequest studentCourseListRequest) {
        Integer currentPage = studentCourseListRequest.getCurrentPage();
        Integer pageSize = studentCourseListRequest.getPageSize();

        // 创建分页对象
        Page<StudentCourse> studentCoursePage = new Page<>(currentPage, pageSize);

        // 按照 学生选课 id 升序排序
        LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(StudentCourse::getId);

        // 执行分页查询
        studentCoursePage = page(studentCoursePage, queryWrapper);

        // 转换为 StudentCourseVO 对象
        return studentCoursePage2StudentCourseVOPage(studentCoursePage);
    }

    /**
     * 查询当前登录用户(学生)的选课列表
     * @param studentCourseListRequest
     * @param session
     * @return
     */
    @Override
    public Page<StudentCourseVO> studentCourseList(StudentCourseListRequest studentCourseListRequest, HttpSession session) {
        // 当前登录用户
        UserVO loginUserVO = (UserVO) session.getAttribute(UserConstant.USER_LOGIN_STATE);

        Integer currentPage = studentCourseListRequest.getCurrentPage();
        Integer pageSize = studentCourseListRequest.getPageSize();

        // 创建分页对象
        Page<StudentCourse> studentCoursePage = new Page<>(currentPage, pageSize);

        // 按照 学生选课 id 升序排序
        LambdaQueryWrapper<StudentCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentCourse::getUserId, loginUserVO.getUserId())
                .orderByAsc(StudentCourse::getId);

        // 执行分页查询
        studentCoursePage = page(studentCoursePage, queryWrapper);

        // 转换为 StudentCourseVO 对象
        return studentCoursePage2StudentCourseVOPage(studentCoursePage);
    }

    @Override
    public boolean studentCourseDelete(Long deleteStudentCourseId) {
        StudentCourse studentCourse = getById(deleteStudentCourseId);
        // 要删除的选课信息不存在
        if (studentCourse == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 删除学生选课信息
        return removeById(deleteStudentCourseId);
    }

    /**
     * 将 StudentCoursePage 转换为 StudentCourseVOPage
     * @param studentCoursePage
     * @return
     */
    private Page<StudentCourseVO> studentCoursePage2StudentCourseVOPage(Page<StudentCourse> studentCoursePage) {
        List<StudentCourseVO> studentCourseVOList = studentCoursePage.getRecords().stream().map(this::domain2VO).collect(Collectors.toList());

        // 将转换后的结果放入新的 Page<StudentCourseVO> 中
        Page<StudentCourseVO> studentCourseVOPage = new Page<>();
        studentCourseVOPage.setRecords(studentCourseVOList);
        studentCourseVOPage.setTotal(studentCoursePage.getTotal());
        studentCourseVOPage.setCurrent(studentCoursePage.getCurrent());
        studentCourseVOPage.setSize(studentCoursePage.getSize());

        return studentCourseVOPage;
    }

    private StudentCourseVO domain2VO(StudentCourse studentCourse) {
        String courseName = studentCourse.getCourseName();
        String courseNum = studentCourse.getCourseNum();
        Long courseId = studentCourse.getCourseId();
        Long userId = studentCourse.getUserId();
        Integer isDelete = studentCourse.getIsDelete();

        StudentCourseVO studentCourseVO = new StudentCourseVO();
        studentCourseVO.setUserVO(userService.domain2VO(userService.getById(userId)));
        studentCourseVO.setCourseVO(courseService.domain2VO(courseService.getById(courseId)));
        return studentCourseVO;

    }
}




