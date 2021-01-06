package com.baiyan.auth.api;

import com.baiyan.auth.api.api.UserApi;
import com.baiyan.auth.api.model.user.UserDTO;
import com.baiyan.auth.service.service.AuthService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author baiyan
 * @time 2020/11/20
 */
@Api(tags = "用户管理rpc接口")
@RestController
public class UserApiImpl implements UserApi {

    @Autowired
    private AuthService authService;

    @Override
    public UserDTO getUserDetail(String token,Integer userState){
        return authService.getUserDetail(token,userState);
    }

}
