package com.baiyan.auth.sdk.error;

import com.baiyan.common.base.result.Result;
import com.baiyan.common.base.utils.GsonUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权认证错误
 *
 * @author baiyan
 * @time 2020/11/14
 */
public class CustomAccessDenyHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/javascript;charset=utf-8");
        response.getWriter().print(GsonUtil.gsonToString(Result.error(403,"access error","没有访问权限!",null)));
    }
}
