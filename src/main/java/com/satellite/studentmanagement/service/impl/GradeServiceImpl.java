package com.satellite.studentmanagement.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satellite.studentmanagement.mapper.GradeMapper;
import com.satellite.studentmanagement.model.entity.Grade;
import com.satellite.studentmanagement.service.GradeService;
import org.springframework.stereotype.Service;

/**
* @author Satellite
* @description 针对表【grade(学生选课表)】的数据库操作Service实现
*/
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade>
    implements GradeService {

}




