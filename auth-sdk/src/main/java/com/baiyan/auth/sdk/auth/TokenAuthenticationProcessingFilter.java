package com.baiyan.auth.sdk.auth;

import com.baiyan.auth.sdk.auth.access.AccessAuthenticationParser;
import com.baiyan.auth.sdk.auth.token.TokenAuthenticationParser;
import com.baiyan.auth.sdk.model.AuthHandle;
import com.baiyan.auth.sdk.util.ThreadLocalUtil;
import lombok.Setter;

import javax.servlet.*;
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
public class TokenAuthenticationProcessingFilter implements Filter {

    private AuthHandle authHandle;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //通行证解析
        AccessAuthenticationParser.parser(authHandle,httpServletRequest,httpServletResponse);
        //token解析
        TokenAuthenticationParser.parser(authHandle,httpServletRequest,httpServletResponse);

        this.removeThreadLocalValue(request,response,chain);
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
