package com.baiyan.auth.sdk.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * token认证异常
 *
 * @author baiyan
 * @date 2020/11/14
 */
public class TokenAuthenticationException extends AuthenticationException {

    public TokenAuthenticationException(String msg) {
        super(msg);
    }

}
