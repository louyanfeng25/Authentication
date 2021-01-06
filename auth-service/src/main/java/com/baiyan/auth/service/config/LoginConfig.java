package com.baiyan.auth.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 登录配置
 *
 * @author baiyan
 * @date 2020/11/20
 */
@Data
@Configuration
@ConfigurationProperties("auth.login.config")
public class LoginConfig {

    /**
     * 是否校验验证
     */
    private Boolean captcha;

    /**
     * 是否校验密码
     */
    private Boolean password;

    /**
     * token过期时间
     */
    private Long tokenTimeout;
}
