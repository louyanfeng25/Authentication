package com.baiyan.auth.sdk.model;

import com.baiyan.auth.sdk.extractor.access.AccessAuthExtractor;
import com.baiyan.auth.sdk.extractor.token.TokenAuthExtractor;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 鉴权处理
 *
 * @author baiyan
 * @time 2020/12/4 15:40
 */
@Data
public class AuthHandle {

    /**
     * 事件发布
     */
    private AuthenticationEventPublisher authenticationEventPublisher;

    /**
     * 鉴权信息解析
     */
    private TokenAuthExtractor tokenAuthExtractor;

    /**
     * 鉴权信息解析
     */
    private AccessAuthExtractor accessAuthExtractor;

    /**
     * 解析链路管理
     */
    private AuthenticationManager authenticationManager;

    /**
     * 异常处理
     */
    private AuthenticationFailureHandler failureHandler;

}
