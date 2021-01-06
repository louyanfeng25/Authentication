package com.baiyan.auth.sdk.configuration;

import com.baiyan.auth.api.constant.AuthConstant;
import com.baiyan.auth.api.model.user.UserDTO;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 请求体中注入用户信息
 *
 * @author baiyan
 * @date 2020/11/14
 */
public class InjectUserDetailArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserDTO.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return webRequest.getAttribute(AuthConstant.USER_DETAIL, RequestAttributes.SCOPE_REQUEST);
    }

}
