package com.baiyan.auth.rpc.config;

import com.baiyan.auth.api.constant.AuthConstant;
import com.baiyan.auth.common.utils.StringUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * feign接口请求增加统一token参数
 *
 * @author baiyan
 * @time 2020/11/26 12:27
 */
@ConditionalOnProperty(name = "auth.feign.enable", havingValue = "true",matchIfMissing = true)
public class AuthFeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Optional.ofNullable(attributes)
                .map(ServletRequestAttributes::getRequest)
                .orElse(null);
        String headerToken = Optional.ofNullable(request).map(e->e.getHeader(AuthConstant.HEADER_TOKEN_KEY))
                .orElse(null);
        String paramToken = Optional.ofNullable(request).map(e->e.getParameter(AuthConstant.PARAM_TOKEN_KEY))
                .orElse(null);
        String headerAccessKey = Optional.ofNullable(request).map(e->e.getHeader(AuthConstant.HEADER_ACCESS_KEY))
                .orElse(null);
        String paramAccessKey = Optional.ofNullable(request).map(e->e.getParameter(AuthConstant.PARAM_ACCESS_KEY))
                .orElse(null);
        String token  = StringUtil.isNotBlank(headerToken) ? headerToken : paramToken;
        String accessKey  = StringUtil.isNotBlank(headerAccessKey) ? headerAccessKey : paramAccessKey;
        requestTemplate.header(AuthConstant.HEADER_TOKEN_KEY, token);
        requestTemplate.header(AuthConstant.HEADER_ACCESS_KEY, accessKey);
    }

}
