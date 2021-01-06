package com.baiyan.auth.sdk.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * accessKey认证异常
 *
 * @author baiyan
 * @date 2020/12/04
 */
public class AccessAuthenticationException extends AuthenticationException {

    public AccessAuthenticationException(String msg) {
        super(msg);
    }

}
