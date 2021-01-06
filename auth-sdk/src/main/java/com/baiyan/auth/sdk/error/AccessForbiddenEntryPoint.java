package com.baiyan.auth.sdk.error;

import org.springframework.http.HttpStatus;

/**
 * 授权认证错误
 *
 * @author baiyan
 * @time 2020/11/14
 */
public class AccessForbiddenEntryPoint extends ErrorCodeHttpStatusEntryPoint {

    /**
     * 自定义错误信息
     */
    public AccessForbiddenEntryPoint() {
        super("access.forbidden", HttpStatus.UNAUTHORIZED,"身份认证失败");
    }

}
