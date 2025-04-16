package com.satellite.studentmanagement.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户删除请求类
 *
 * @author Satellite
 */
@Data
public class UserDeleteRequest implements Serializable {

    /**
     * 用户 ID
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}
