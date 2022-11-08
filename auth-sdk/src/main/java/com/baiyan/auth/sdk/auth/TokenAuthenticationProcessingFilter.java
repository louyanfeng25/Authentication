package com.baiyan.auth.sdk.auth;

import com.baiyan.auth.sdk.auth.access.AccessAuthenticationParser;
import com.baiyan.auth.sdk.auth.token.TokenAuthenticationParser;
import com.baiyan.auth.sdk.model.AuthHandle;
import com.baiyan.auth.sdk.util.ThreadLocalUtil;
import lombok.Setter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token认证过滤器
 *
 * @author baiyan
 * @date 2020/11/14
 */
@Setter
public class TokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private AuthHandle authHandle;

    public TokenAuthenticationProcessingFilter(RequestMatcher matcher, AuthHandle authHandle) {
        super(matcher);
        this.authHandle = authHandle;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //通行证解析
        Authentication accessAuthentication = AccessAuthenticationParser.parser(authHandle,httpServletRequest,httpServletResponse);
        if (accessAuthentication != null && accessAuthentication.isAuthenticated()) {
            return accessAuthentication;
        }
        //token解析
        Authentication tokenAuthentication = TokenAuthenticationParser.parser(authHandle,httpServletRequest,httpServletResponse);
        if (tokenAuthentication != null && tokenAuthentication.isAuthenticated()) {
            return tokenAuthentication;
        }
        throw new BadCredentialsException("无效身份信息");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        this.removeThreadLocalValue(request,response,chain);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        authHandle.getAuthenticationEventPublisher().publishAuthenticationFailure(failed,null);
        authHandle.getFailureHandler().onAuthenticationFailure(request,response,failed);
    }

    /**
     * 在进行后续链式调用结束后移除变量
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    private void removeThreadLocalValue(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            chain.doFilter(request, response);
        } finally {
            ThreadLocalUtil.removeToken();
            ThreadLocalUtil.removeUser();
            ThreadLocalUtil.removeAccessKey();
        }
    }

}
