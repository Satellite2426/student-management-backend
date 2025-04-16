package com.satellite.studentmanagement.model.dto.user;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户注册请求体
 *
 * @author Satellite
 */
@Data
public class UserRegisterRequest implements Serializable {

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户确认密码
     */
    private String userConfirmedPassword;

    private static final long serialVersionUID = 1L;

}
