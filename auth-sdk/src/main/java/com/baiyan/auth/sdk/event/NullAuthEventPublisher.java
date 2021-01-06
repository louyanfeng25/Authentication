package com.baiyan.auth.sdk.event;

import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 口子先留出来，暂时未对鉴权通过与否做操作，后续可进行流量检测
 *
 * @author baiyan
 * @time 2020/12/4 14:13
 */
public class NullAuthEventPublisher implements AuthenticationEventPublisher {

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
    }

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
    }

}
