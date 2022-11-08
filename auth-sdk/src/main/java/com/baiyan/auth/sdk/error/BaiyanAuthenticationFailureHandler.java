package com.baiyan.auth.sdk.error;

import com.baiyan.common.base.result.Result;
import com.baiyan.common.base.utils.GsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Component("baiyanAuthFailHandler")
@RequiredArgsConstructor
@Slf4j
public class BaiyanAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper mapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.error("鉴权失败,错误信息:",exception);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //请求成功，由业务判断失败的code
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().print(GsonUtil.gsonToString(Result.authError()));
//        mapper.writeValue(response.getWriter(), ((ResponseEntity<?>) result).getBody());
    }
}
