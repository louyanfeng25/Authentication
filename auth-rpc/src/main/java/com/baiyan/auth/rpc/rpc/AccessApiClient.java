package com.baiyan.auth.rpc.rpc;

import com.baiyan.auth.api.api.AccessApi;
import com.baiyan.auth.api.model.user.UserDTO;
import com.baiyan.auth.rpc.config.ClientConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author baiyan
 * @time 2020/12/09
 */
@FeignClient(
        name = ClientConstants.Access.SERVICE_NAME,
        contextId = ClientConstants.Access.NAME,
        fallback = AccessApiClient.AccessFallback.class,
        configuration = AccessApiClient.AccessFallback.class
)
public interface AccessApiClient extends AccessApi {

    @Component
    class AccessFallback implements AccessApi {

        @Override
        public UserDTO getUserDetail(String accessKey){return null;}

    }

}
