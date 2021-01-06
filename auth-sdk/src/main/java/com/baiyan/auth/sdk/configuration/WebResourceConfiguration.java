package com.baiyan.auth.sdk.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author baiyan
 * @date 2020/11/14
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(AuthProperties.class)
public class WebResourceConfiguration implements WebMvcConfigurer {

    @Bean
    public InjectUserDetailArgumentResolver injectUserDetailArgumentResovler() {
        return new InjectUserDetailArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(injectUserDetailArgumentResovler());
    }

}
