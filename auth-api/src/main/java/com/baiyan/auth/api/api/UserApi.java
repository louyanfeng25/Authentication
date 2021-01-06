package com.baiyan.auth.api.api;

import com.baiyan.auth.api.config.VersionConfig;
import com.baiyan.auth.api.model.user.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author baiyan
 * @time 2020/11/13 11:27
 */
@RequestMapping(VersionConfig.COMMON_RPC_VERSION_URL+"user")
public interface UserApi {

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @param userState 用户状态
     * @see com.baiyan.auth.api.enums.UserStateEnum
     * @return
     */
    @PostMapping("/by_token")
    UserDTO getUserDetail(@RequestParam("token") String token,@RequestParam(value = "userState",required = false) Integer userState);

}
