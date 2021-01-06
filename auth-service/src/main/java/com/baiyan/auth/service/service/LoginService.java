package com.baiyan.auth.service.service;

import com.baiyan.auth.service.model.login.dto.LoginDTO;
import com.baiyan.auth.service.model.login.dto.LoginResetPasswordDTO;
import com.baiyan.auth.service.model.login.dto.LoginResultDTO;

/**
 * @author baiyan
 * @time 2020/11/13 14:31
 */
public interface LoginService {

    /**
     * 用户名密码登录
     * @param loginDTO
     * @return
     */
    LoginResultDTO login(LoginDTO loginDTO);

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    void loginOut(String token);

    /**
     * 重置密码登录
     * @param loginDTO
     * @return
     */
    LoginResultDTO login(LoginResetPasswordDTO loginDTO);

}
