package com.satellite.studentmanagement.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @TableName user
 * @author Satellite
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 用户角色 (0-管理员; 1-学生)
     */
    private Integer userRole;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 用户账号(学号/教师编号)
     */
    private String userAccount;

    /**
     * 用户密码(加密存储)
     */
    private String userPassword;

    /**
     * 联系电话
     */
    private String userPhone;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 性别(男; 女)
     */
    private String gender;

    /**
     * 用户头像URL
     */
    private String userAvatar;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删除,1-已删除)
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}