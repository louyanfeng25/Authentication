package com.baiyan.auth.rpc.rpc;

import com.baiyan.auth.api.api.UserApi;
import com.baiyan.auth.api.model.user.UserDTO;
import com.baiyan.auth.rpc.config.ClientConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author baiyan
 * @time 2020/11/13 11:27
 */
@FeignClient(
        name = ClientConstants.User.SERVICE_NAME,
        contextId = ClientConstants.User.NAME,
        fallback = UserApiClient.UserFallback.class,
        configuration = UserApiClient.UserFallback.class
)
public interface UserApiClient extends UserApi {

    @Component
    class UserFallback implements UserApi {

        @Override
        public UserDTO getUserDetail(String token,Integer userState){return null;}
   }

}
