# 数据库初始化

-- 创建库
create database if not exists student;

-- 切换库
use student;

-- 用户表
CREATE TABLE `user`
(
    `userId`       bigint       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `userRole`     tinyint               DEFAULT 1 NOT NULL COMMENT '用户角色 (0-管理员; 1-学生)',
    `username`     varchar(128) NULL COMMENT '用户姓名',
    `userAccount`  varchar(128) NOT NULL COMMENT '用户账号(学号/教师编号)',
    `userPassword` varchar(128) NOT NULL COMMENT '用户密码(加密存储)',
    `userPhone`    varchar(20)           DEFAULT NULL COMMENT '联系电话',
    `userEmail`    varchar(128)          DEFAULT NULL comment '用户邮箱',
    `gender`       varchar(20)  NULL COMMENT '性别(男; 女)',
    `userAvatar`   varchar(255) NULL COMMENT '用户头像URL',
    `createTime`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete`     tinyint      NOT NULL DEFAULT '0' COMMENT '是否删除(0-未删除,1-已删除)',
    PRIMARY KEY (`userId`)
) COMMENT ='用户表' collate = utf8mb4_unicode_ci;

-- 课程表
CREATE TABLE `course`
(
    `courseId`          bigint       NOT NULL AUTO_INCREMENT COMMENT '课程ID',
    `courseName`        varchar(128) NOT NULL COMMENT '课程名称',
    `courseNum`         varchar(128) NOT NULL COMMENT '课程编号',
    `courseDescription` text COMMENT '课程简介',
    `courseTimes`       varchar(128)          DEFAULT NULL COMMENT '课程学时',
    `courseTeacher`     varchar(128) NULL COMMENT '任课教师',
    `createTime`        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime`        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete`          tinyint      NOT NULL DEFAULT '0' COMMENT '是否删除(0-未删除,1-已删除)',
    PRIMARY KEY (`courseId`)
) COMMENT ='课程表' collate = utf8mb4_unicode_ci;

-- 学生选课表
CREATE TABLE `student_course`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT COMMENT '学生选课ID',
    `courseName` varchar(128) NOT NULL COMMENT '课程名称',
    `courseNum`  varchar(128) NOT NULL COMMENT '课程编号',
    `courseId`   bigint       NOT NULL COMMENT '课程ID',
    `userId`     bigint       NOT NULL COMMENT '学生ID',
    `isDelete`   tinyint      NOT NULL DEFAULT '0' COMMENT '是否删除(0-未删除,1-已删除)',
    PRIMARY KEY (`id`),
    index idx_questionId (courseId),
    index idx_userId (userId)
) COMMENT ='学生选课表' collate = utf8mb4_unicode_ci;

-- 成绩表
CREATE TABLE `grade`
(
    `id`       bigint        NOT NULL AUTO_INCREMENT COMMENT '学生成绩ID',
    `courseId` bigint        NOT NULL COMMENT '课程ID',
    `userId`   bigint        NOT NULL COMMENT '学生ID',
    `score`    double(10, 1) NOT NULL COMMENT '成绩',
    `comment`  varchar(255)  NULL COMMENT '管理员/教师评语',
    `feedback` varchar(255)  NULL COMMENT '学生反馈/评价',
    `isDelete` tinyint       NOT NULL DEFAULT '0' COMMENT '是否删除(0-未删除,1-已删除)',
    PRIMARY KEY (`id`),
    index idx_questionId (courseId),
    index idx_userId (userId)
) COMMENT ='学生选课表' collate = utf8mb4_unicode_ci;

