package com.baiyan.auth.service.service;

import com.baiyan.auth.api.model.user.UserDTO;

/**
 * 用户信息
 *
 * @author baiyan
 * @date 2020/11/14
 */
public interface AuthService {

    /**
     * 根据用户id获取用户信息
     *
     * @param id 用户id
     * @param userState 用户状态
     * @see com.baiyan.auth.api.enums.UserStateEnum
     * @return
     */
    UserDTO getUserDetail(Long id,Integer userState);

    /**
     * 根据token获取用户信息
     *
     * @param token 用户token
     * @param userState 用户状态
     * @see com.baiyan.auth.api.enums.UserStateEnum
     * @return
     */
    UserDTO getUserDetail(String token,Integer userState);

}
