package com.satellite.studentmanagement.model.vo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
 * 用户视图（脱敏）
 *
 * @author Satellite
 */
@Data
public class UserVO implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户角色（0-管理员; 1-学生）
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


    private static final long serialVersionUID = 1L;
}