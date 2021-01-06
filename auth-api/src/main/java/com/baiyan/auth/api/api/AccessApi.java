package com.baiyan.auth.api.api;

import com.baiyan.auth.api.config.VersionConfig;
import com.baiyan.auth.api.model.user.UserDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author baiyan
 * @time 2020/12/09
 */
@RequestMapping(VersionConfig.COMMON_RPC_VERSION_URL+"access")
@Validated
public interface AccessApi {

    /**
     * 根据鉴权码获取授权信息
     *
     * @param accessKey
     * @return
     */
    @PostMapping("by_accessKey")
    UserDTO getUserDetail(@RequestParam("accessKey") String accessKey);

}
