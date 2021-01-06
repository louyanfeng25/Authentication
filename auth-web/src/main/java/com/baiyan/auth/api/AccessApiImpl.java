package com.baiyan.auth.api;

import com.baiyan.auth.api.api.AccessApi;
import com.baiyan.auth.api.enums.UserStateEnum;
import com.baiyan.auth.api.model.user.UserDTO;
import com.baiyan.auth.service.service.AccessService;
import com.baiyan.auth.service.service.AuthService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author baiyan
 * @time 2020/12/09
 */
@Api(tags = "通行证管理rpc接口")
@RestController
public class AccessApiImpl implements AccessApi {

    @Autowired
    private AccessService accessService;

    @Autowired
    private AuthService authService;

    @Override
    public UserDTO getUserDetail(String accessKey){
        Long userId = accessService.accessAuth(accessKey);
        return authService.getUserDetail(userId, UserStateEnum.AVAILABLE.getKey());
    }

}
