package com.satellite.studentmanagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.satellite.studentmanagement.model.dto.user.UserListRequest;
import com.satellite.studentmanagement.model.dto.user.UserLoginRequest;
import com.satellite.studentmanagement.model.dto.user.UserRegisterRequest;
import com.satellite.studentmanagement.model.dto.user.UserUpdateRequest;
import com.satellite.studentmanagement.model.entity.User;
import com.satellite.studentmanagement.model.vo.UserVO;

import javax.servlet.http.HttpSession;


/**
* @author Satellite
* @description 针对表【user(用户表)】的数据库操作Service
*/
public interface UserService extends IService<User> {

    Long userRegister(UserRegisterRequest userRegisterRequest);

    UserVO userLogin(UserLoginRequest userLoginRequest, HttpSession session);

    UserVO domain2VO(User user);

    boolean userLogout(HttpSession session);

    Page<UserVO> userList(UserListRequest userListRequest);

    boolean userDelete(UserVO loginUserVO, Long deleteUserId);

    UserVO userUpdate(UserUpdateRequest userUpdateRequest, HttpSession session);
}
