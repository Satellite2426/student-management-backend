package com.satellite.studentmanagement.model.dto.user;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 用户更新请求
 *
 * @author Satellite
 */
@Data
public class UserUpdateRequest implements Serializable {

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String username;


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


    private static final long serialVersionUID = 1L;
}