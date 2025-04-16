package com.satellite.studentmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satellite.studentmanagement.exception.BusinessException;
import com.satellite.studentmanagement.mapper.CourseMapper;
import com.satellite.studentmanagement.model.dto.course.CourseAddRequest;
import com.satellite.studentmanagement.model.dto.course.CourseListRequest;
import com.satellite.studentmanagement.model.dto.course.CourseSearchRequest;
import com.satellite.studentmanagement.model.dto.course.CourseUpdateRequest;
import com.satellite.studentmanagement.model.entity.Course;
import com.satellite.studentmanagement.model.enums.ErrorCode;
import com.satellite.studentmanagement.model.vo.CourseVO;
import com.satellite.studentmanagement.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Satellite
 * @description 针对表【course(课程表)】的数据库操作Service实现
 */
@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Resource
    private CourseMapper courseMapper;

    /**
     * 查询所有课程列表(分页)
     *
     * @param courseListRequest
     * @return
     */
    @Override
    public Page<CourseVO> courseList(CourseListRequest courseListRequest) {
        // 转换 Course 对象为 CourseVO 对象
        Page<Course> coursePage = getCoursePage(courseListRequest);
        List<CourseVO> courseVOList = coursePage.getRecords().stream().map(this::domain2VO).collect(Collectors.toList());

        // 将转换后的结果放入新的 Page<CourseVO> 中
        Page<CourseVO> courseVOPage = new Page<>();
        courseVOPage.setRecords(courseVOList);
        courseVOPage.setTotal(coursePage.getTotal());
        courseVOPage.setCurrent(coursePage.getCurrent());
        courseVOPage.setSize(coursePage.getSize());

        // 获取课程分页对象
        return courseVOPage;
    }

    /**
     * 添加课程 (管理员)
     *
     * @param courseAddRequest
     * @return
     */
    @Override
    public Long courseAdd(CourseAddRequest courseAddRequest) {
        String courseName = courseAddRequest.getCourseName();
        String courseNum = courseAddRequest.getCourseNum();
        String courseDescription = courseAddRequest.getCourseDescription();
        String courseTimes = courseAddRequest.getCourseTimes();
        String courseTeacher = courseAddRequest.getCourseTeacher();

        // 参数校验
        // 课程不能重复
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Course::getCourseName, courseName)
                .eq(Course::getCourseNum, courseNum);
        long count = count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该课程已存在");
        }

        Course course = new Course();
        course.setCourseName(courseName);
        course.setCourseNum(courseNum);
        course.setCourseDescription(courseDescription);
        course.setCourseTimes(courseTimes);
        course.setCourseTeacher(courseTeacher);

        save(course);

        return course.getCourseId();
    }

    /**
     * 删除课程 (管理员)
     *
     * @param deleteCourseId
     * @return
     */
    @Override
    public boolean questionDelete(Long deleteCourseId) {
        Course course = getById(deleteCourseId);
        // 要删除的课程不存在
        if (course == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 删除课程
        removeById(course);
        return true;
    }

    /**
     * 题目更新 (管理员)
     *
     * @param courseUpdateRequest
     * @return
     */
    @Override
    public boolean courseUpdate(CourseUpdateRequest courseUpdateRequest) {
        Long courseId = courseUpdateRequest.getCourseId();
        String courseName = courseUpdateRequest.getCourseName();
        String courseNum = courseUpdateRequest.getCourseNum();
        String courseDescription = courseUpdateRequest.getCourseDescription();
        String courseTimes = courseUpdateRequest.getCourseTimes();
        String courseTeacher = courseUpdateRequest.getCourseTeacher();

        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Course::getCourseNum, courseNum);
        Course course = getOne(queryWrapper);
        if (course != null && !course.getCourseId().equals(courseUpdateRequest.getCourseId())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该课程已存在");
        }

        course = new Course();
        course.setCourseId(courseId);
        course.setCourseName(courseName);
        course.setCourseNum(courseNum);
        course.setCourseDescription(courseDescription);
        course.setCourseTimes(courseTimes);
        course.setCourseTeacher(courseTeacher);

        updateById(course);

        return true;
    }

    /**
     * 课程搜索
     *
     * @param courseSearchRequest
     * @return
     */
    @Override
    public Page<CourseVO> courseSearch(CourseSearchRequest courseSearchRequest) {
        String courseName = courseSearchRequest.getCourseName();
        String courseNum = courseSearchRequest.getCourseNum();
        String courseTeacher = courseSearchRequest.getCourseTeacher();
        Integer currentPage = courseSearchRequest.getCurrentPage();
        Integer pageSize = courseSearchRequest.getPageSize();

        // 如果当前搜索字为空, 直接返回课程列表
        if (StringUtils.isBlank(courseName) && StringUtils.isBlank(courseNum) && StringUtils.isBlank(courseTeacher)) {
            CourseListRequest courseListRequest = new CourseListRequest();
            courseListRequest.setCurrentPage(courseListRequest.getCurrentPage());
            courseListRequest.setPageSize(courseListRequest.getPageSize());
            return courseList(courseListRequest);
        }

        // 构造分页对象
        Page<Course> coursePage = new Page<>(currentPage, pageSize);

        // 构造查询参数
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.or(q -> q.like(Course::getCourseName, courseName))
                .or(q -> q.like(Course::getCourseNum, courseNum))
                .or(q -> q.like(Course::getCourseTeacher, courseTeacher));

        // 查询符合的课程名称 课程编号 课程任课教师的课程
        coursePage = page(coursePage, queryWrapper);

        // 转换 Course 对象为 CourseVO 对象
        List<CourseVO> courseVOList = coursePage.getRecords().stream().map(this::domain2VO).collect(Collectors.toList());

        // 将转换后的结果放入新的 Page<CourseVO> 中
        Page<CourseVO> courseVOPage = new Page<>();
        courseVOPage.setRecords(courseVOList);
        courseVOPage.setTotal(coursePage.getTotal());
        courseVOPage.setCurrent(coursePage.getCurrent());
        courseVOPage.setSize(coursePage.getSize());

        // 获取课程分页对象
        return courseVOPage;
    }

    /**
     * 获取课程分页对象
     *
     * @param courseListRequest
     * @return
     */
    private Page<Course> getCoursePage(CourseListRequest courseListRequest) {
        Integer currentPage = courseListRequest.getCurrentPage();
        Integer pageSize = courseListRequest.getPageSize();

        // 创建分页对象
        Page<Course> page = new Page<>(currentPage, pageSize);

        // 按照 id 升序排序
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Course::getCourseId);
        // 执行分页查询
        return page(page, queryWrapper);
    }

    @Override
    public CourseVO domain2VO(Course course) {
        Long courseId = course.getCourseId();
        String courseName = course.getCourseName();
        String courseNum = course.getCourseNum();
        String courseDescription = course.getCourseDescription();
        String courseTimes = course.getCourseTimes();
        String courseTeacher = course.getCourseTeacher();
        Date createTime = course.getCreateTime();

        CourseVO courseVO = new CourseVO();
        courseVO.setCourseId(courseId);
        courseVO.setCourseName(courseName);
        courseVO.setCourseNum(courseNum);
        courseVO.setCourseDescription(courseDescription);
        courseVO.setCourseTimes(courseTimes);
        courseVO.setCourseTeacher(courseTeacher);
        courseVO.setCreateTime(createTime);

        return courseVO;
    }
}




