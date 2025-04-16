package com.satellite.studentmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satellite.studentmanagement.constant.UserConstant;
import com.satellite.studentmanagement.constant.UserRole;
import com.satellite.studentmanagement.exception.BusinessException;
import com.satellite.studentmanagement.mapper.UserMapper;
import com.satellite.studentmanagement.model.dto.user.UserListRequest;
import com.satellite.studentmanagement.model.dto.user.UserLoginRequest;
import com.satellite.studentmanagement.model.dto.user.UserRegisterRequest;
import com.satellite.studentmanagement.model.dto.user.UserUpdateRequest;
import com.satellite.studentmanagement.model.entity.User;
import com.satellite.studentmanagement.model.enums.ErrorCode;
import com.satellite.studentmanagement.model.vo.UserVO;
import com.satellite.studentmanagement.service.UserService;
import com.satellite.studentmanagement.utils.RegUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Satellite
 * @description 针对表【user(用户表)】的数据库操作Service实现
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    @Value("${password.salt}")
    private String salt;

    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @Override
    public Long userRegister(UserRegisterRequest userRegisterRequest) {
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String userConfirmedPassword = userRegisterRequest.getUserConfirmedPassword();

        // 参数校验
        // 账号为 8 - 16 位不允许带特殊字符
        if (!RegUtil.isLegalUserAccount(userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或密码格式错误");
        }
        // 密码为 8-16 位除空格外可带其他特殊字符
        if (!RegUtil.isLegalUserPassword(userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或密码格式错误");
        }
        // 密码与确认密码一致
        if (!userPassword.equalsIgnoreCase(userConfirmedPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }

        // 查询数据库是否有相同账号的用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, userAccount);
        User user = getOne(queryWrapper);
        if (user != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号已存在");
        }

        // 密码加密处理
        String encryptUserPassword = encryptUserPassword(userPassword);

        // 插入用户
        user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptUserPassword);
        user.setUsername("学生" + System.currentTimeMillis());
        return user.getUserId();
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param session
     * @return
     */
    @Override
    public UserVO userLogin(UserLoginRequest userLoginRequest, HttpSession session) {
        // 参数校验
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (!RegUtil.isLegalUserAccount(userAccount) || !RegUtil.isLegalUserPassword(userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或密码格式错误");
        }

        // 查询数据库账号是否存在或密码输入是否错误
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, userAccount)
                .eq(User::getUserPassword, encryptUserPassword(userPassword));
        User user = this.getOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不存在或密码错误");
        }

        // 是否为封禁用户
        if (user.getIsDelete().equals(UserConstant.USER_BANED_CODE)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号已被封禁");
        }

        // 将用户转为 userVO, 进行脱敏
        UserVO userVO = domain2VO(user);

        // 记录用户的登录态
        session.setAttribute(UserConstant.USER_LOGIN_STATE, userVO);

        return userVO;
    }



    @Override
    public boolean userLogout(HttpSession session) {
        // 删除用户登录态
        session.removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }

    /**
     * 获取学生用户列表(分页) - 管理员
     * @param userListRequest
     * @return
     */
    @Override
    public Page<UserVO> userList(UserListRequest userListRequest) {
        // 转换 User 对象为 UserVO 对象
        Page<User> userPage = getUserPage(userListRequest);
        List<UserVO> userVOList = userPage.getRecords().stream().map(this::domain2VO).collect(Collectors.toList());

        // 将转换后的结果放入新的 Page<UserVO> 中
        Page<UserVO> userVOPage = new Page<>();
        userVOPage.setRecords(userVOList);
        userVOPage.setTotal(userPage.getTotal());
        userVOPage.setCurrent(userPage.getCurrent());
        userVOPage.setSize(userPage.getSize());
        return userVOPage;
    }

    /**
     * 管理员删除学生用户
     * @param loginUserVO
     * @param deleteUserId
     * @return
     */
    @Override
    public boolean userDelete(UserVO loginUserVO, Long deleteUserId) {
        // 是否为管理员
        Integer loginUserRole = loginUserVO.getUserRole();
        if (!loginUserRole.equals(UserRole.ADMIN)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
        }

        // 当前用户是否存在
        User deleteUser = getById(deleteUserId);
        if (deleteUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }

        // 删除用户
        return removeById(deleteUserId);
    }

    /**
     * 学生更新个人信息
     * @param userUpdateRequest
     * @param session
     * @return
     */
    @Override
    public UserVO userUpdate(UserUpdateRequest userUpdateRequest, HttpSession session) {
        // 参数校验
        Long userId = userUpdateRequest.getUserId();
        String username = userUpdateRequest.getUsername();
        String userPhone = userUpdateRequest.getUserPhone();
        String userEmail = userUpdateRequest.getUserEmail();
        String gender = userUpdateRequest.getGender();
        String userAvatar = userUpdateRequest.getUserAvatar();

        // 姓名不能过长
        if (username != null && username.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "姓名过长");
        }

        User updateUser = new User();
        updateUser.setUserId(userId);
        updateUser.setUsername(username);
        updateUser.setUserPhone(userPhone);
        updateUser.setUserEmail(userEmail);
        updateUser.setGender(gender);
        updateUser.setUserAvatar(userAvatar);
        updateById(updateUser);

        // 将更新后的用户存入 session 中
        UserVO userVO = domain2VO(getById(userId));
        session.setAttribute(UserConstant.USER_LOGIN_STATE, userVO);

        return userVO;
    }


    /**
     * 用户密码加密处理
     */
    private String encryptUserPassword(String userPassword) {
        return DigestUtils.md5DigestAsHex((salt + userPassword).getBytes());
    }

    /**
     * 获取学生用户分页的对象
     * @param userListRequest
     * @return
     */
    private Page<User> getUserPage(UserListRequest userListRequest) {
        Integer currentPage = userListRequest.getCurrentPage();
        Integer pageSize = userListRequest.getPageSize();

        // 创建分页对象
        Page<User> page = new Page<>(currentPage, pageSize);

        // 鉴别学生用户, 并按照 id 升序排序
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserRole, UserRole.USER)
                .orderByAsc(User::getUserId);

        // 执行分页查询
        return page(page, queryWrapper);
    }

    @Override
    public UserVO domain2VO(User user) {
        Long userId = user.getUserId();
        Integer userRole = user.getUserRole();
        String username = user.getUsername();
        String userAccount = user.getUserAccount();
        String userPhone = user.getUserPhone();
        String userEmail = user.getUserEmail();
        String gender = user.getGender();
        String userAvatar = user.getUserAvatar();
        Date createTime = user.getCreateTime();

        UserVO userVO = new UserVO();
        userVO.setUserId(userId);
        userVO.setUserRole(userRole);
        userVO.setUsername(username);
        userVO.setUserAccount(userAccount);
        userVO.setUserPhone(userPhone);
        userVO.setUserEmail(userEmail);
        userVO.setGender(gender);
        userVO.setUserAvatar(userAvatar);
        userVO.setCreateTime(createTime);

        return userVO;
    }
}




