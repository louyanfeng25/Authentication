package com.baiyan.auth.rpc.config;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

/**
 * auth服务RPC自动配置类
 *
 * @author baiyan
 * @date 2020/11/26
 */
@Configuration
@Import(AuthFeignConfig.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableFeignClients(basePackages = "com.baiyan.auth.rpc")
public class AuthFeignClientAutoConfiguration {

}
