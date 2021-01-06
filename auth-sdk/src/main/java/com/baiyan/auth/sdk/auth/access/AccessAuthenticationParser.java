package com.baiyan.auth.sdk.auth.access;

import com.baiyan.auth.api.constant.AuthConstant;
import com.baiyan.auth.sdk.extractor.access.AccessAuthentication;
import com.baiyan.auth.sdk.model.AuthHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * access认证解析器
 *
 * @author baiyan
 * @date 2020/11/14
 */
@Slf4j
public class AccessAuthenticationParser {

    public static void parser(AuthHandle authHandle,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        AccessAuthentication access = authHandle.getAccessAuthExtractor().extract(httpServletRequest);
        if (Objects.nonNull(access)) {
            Authentication authenticate;
            try {
                authenticate = authHandle.getAuthenticationManager().authenticate(access);
            } catch (AuthenticationException e) {
                log.error("通行证鉴权失败：",e);
                authHandle.getAuthenticationEventPublisher().publishAuthenticationFailure(e, access);
                return;
            }
            httpServletRequest.setAttribute(AuthConstant.USER_DETAIL, authenticate.getDetails());
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            authHandle.getAuthenticationEventPublisher().publishAuthenticationSuccess(authenticate);
        }
    }

}
