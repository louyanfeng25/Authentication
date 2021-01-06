package com.baiyan.auth.sdk.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;

/**
 * 拦截与过滤资源定义
 *
 * @author baiyan
 * @date 2020/11/14
 */
@Data
@ConfigurationProperties("auth")
public class AuthProperties {

    /**
     * 鉴权拦截的请求
     */
    private String resourceApi = "/api/**";

    /**
     * 不拦截的请求
     */
    private List<String> exclude = Arrays.asList("/api/**/api-docs");

}
