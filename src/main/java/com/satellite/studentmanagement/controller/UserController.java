package com.satellite.studentmanagement.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.satellite.studentmanagement.annotation.CheckLogin;
import com.satellite.studentmanagement.common.BaseResponse;
import com.satellite.studentmanagement.constant.UserConstant;
import com.satellite.studentmanagement.constant.UserRole;
import com.satellite.studentmanagement.model.enums.ErrorCode;
import com.satellite.studentmanagement.common.ResultUtils;
import com.satellite.studentmanagement.exception.BusinessException;
import com.satellite.studentmanagement.model.dto.user.*;
import com.satellite.studentmanagement.model.vo.UserVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.satellite.studentmanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 用户接口
 *
 * @author Satellite
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param userRegisterRequest 账号为 8 - 16 位不允许带特殊字符；密码为 8 - 16 位不允许带特殊字符
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        // 参数校验
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String userConfirmedPassword = userRegisterRequest.getUserConfirmedPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, userConfirmedPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "必填信息不能为空格");
        }
        return ResultUtils.success(userService.userRegister(userRegisterRequest));
    }

    /**
     * 用户登录
     *
     */
    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpSession session) {
        // 参数校验
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或密码未输入");
        }

        return ResultUtils.success(userService.userLogin(userLoginRequest, session));
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpSession session) {
        return ResultUtils.success(userService.userLogout(session));
    }

    /**
     *  查询所有学生列表(分页) - 仅管理员
     */
    @PostMapping("/list")
    @CheckLogin(UserRole.ADMIN)
    public BaseResponse<Page<UserVO>> userList(@RequestBody UserListRequest userListRequest) {
        // 参数校验
        Integer currentPage = userListRequest.getCurrentPage();
        Integer pageSize = userListRequest.getPageSize();
        if (currentPage < 0 || pageSize < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(userService.userList(userListRequest));

    }


    /**
     * 学生更新个人信息
     * @param userUpdateRequest
     * @param session
     * @return
     */
    @PostMapping("/update")
    @CheckLogin
    public BaseResponse<UserVO> userUpdate(@RequestBody UserUpdateRequest userUpdateRequest, HttpSession session) {
        return ResultUtils.success(userService.userUpdate(userUpdateRequest, session));
    }


    /**
     * 删除学生用户
     * 该功能只有管理员才能删除学生用户
     * @param userDeleteRequest
     * @param session
     * @return
     */
    @PostMapping("/delete")
    @CheckLogin(UserRole.ADMIN)
    public BaseResponse<Boolean> userDelete(@RequestBody UserDeleteRequest userDeleteRequest, HttpSession session) {
        // 参数校验
        UserVO loginUserVO = (UserVO) session.getAttribute(UserConstant.USER_LOGIN_STATE);
        Long deleteUserId = userDeleteRequest.getUserId();
        if (deleteUserId == null || deleteUserId < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(userService.userDelete(loginUserVO, deleteUserId));
    }


}
