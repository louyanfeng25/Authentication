package com.baiyan.auth.sdk.configuration;

import com.baiyan.auth.sdk.auth.TokenAuthenticationProcessingFilter;
import com.baiyan.auth.sdk.auth.access.AccessAuthenticationProvider;
import com.baiyan.auth.sdk.auth.token.TokenAuthenticationProvider;
import com.baiyan.auth.sdk.error.CustomAccessDenyHandler;
import com.baiyan.auth.sdk.extractor.access.AccessAuthExtractor;
import com.baiyan.auth.sdk.extractor.access.AccessAuthProviderConfiguration;
import com.baiyan.auth.sdk.extractor.token.TokenAuthExtractor;
import com.baiyan.auth.sdk.extractor.token.TokenAuthProviderConfiguration;
import com.baiyan.auth.sdk.model.AuthHandle;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * security配置
 *
 * @author baiyan
 * @date 2020/11/14
 */
@Configuration
@Import({TokenAuthProviderConfiguration.class, AccessAuthProviderConfiguration.class})
public class TokenAuthenticationResourceConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    private AuthenticationEventPublisher authenticationEventPublisher;

    @Autowired
    private TokenAuthExtractor tokenAuthExtractor;

    @Autowired
    private AccessAuthExtractor accessAuthExtractor;

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private AuthenticationFailureHandler baiyanAuthFailHandler;

    /**
     * 鉴权方式配置
     *
     * @param auth
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(tokenAuthenticationProvider())
            .authenticationProvider(accessAuthenticationProvider());
    }

    /**
     * 鉴权资源配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .requestMatcher(authMatcher())
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
            .csrf().disable()
            .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDenyHandler())
                .and()
            .addFilterBefore(buildDefaultFilter(), BasicAuthenticationFilter.class);
    }

    @SneakyThrows
    private TokenAuthenticationProcessingFilter buildDefaultFilter(){
        AuthHandle authHandle = new AuthHandle();
        authHandle.setAuthenticationEventPublisher(authenticationEventPublisher);
        authHandle.setAuthenticationManager(authenticationManager());
        authHandle.setTokenAuthExtractor(tokenAuthExtractor);
        authHandle.setAccessAuthExtractor(accessAuthExtractor);
        authHandle.setFailureHandler(baiyanAuthFailHandler);
        return new TokenAuthenticationProcessingFilter(authMatcher(),authHandle);
    }

    /**
     * 自定义token鉴权执行器
     *
     * @return
     */
    @Bean
    public TokenAuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider();
    }

    /**
     * 自定义access鉴权执行器
     *
     * @return
     */
    @Bean
    public AccessAuthenticationProvider accessAuthenticationProvider() {
        return new AccessAuthenticationProvider();
    }

    /**
     * 资源过滤配置
     * @return
     */
    @Bean
    public AuthMatcher authMatcher() {
        AuthMatcher authMatcher = new AuthMatcher();
        authMatcher.addExcludeMatcher(new AntPathRequestMatcher("/api/**/auth/tokens", HttpMethod.POST.name()));
        authMatcher.addExcludeMatcher(new AntPathRequestMatcher("/api/**/auth/resetPassword", HttpMethod.POST.name()));
        authMatcher.addExcludeMatcher(new AntPathRequestMatcher("/api/auth/web/user/auth", HttpMethod.GET.name()));
        return authMatcher;
    }

    public class AuthMatcher implements RequestMatcher {

        private List<AntPathRequestMatcher> excludeMatchers = new ArrayList<>();

        private List<AntPathRequestMatcher> includeMatchers = new ArrayList<>();

        public AuthMatcher() {
            authProperties.getExclude()
                    .stream()
                    .map(AntPathRequestMatcher::new)
                    .forEach(excludeMatchers::add);
            Arrays.stream(authProperties.getResourceApi().split(","))
                    .map(AntPathRequestMatcher::new)
                    .forEach(includeMatchers::add);
        }

        public void addExcludeMatcher(AntPathRequestMatcher antPathRequestMatcher) {
            excludeMatchers.add(antPathRequestMatcher);
        }

        @Override
        public boolean matches(HttpServletRequest request) {
            boolean findExclude = excludeMatchers.stream()
                    .filter(excludeMatcher -> excludeMatcher.matches(request))
                    .findAny()
                    .map(t -> Boolean.TRUE)
                    .orElse(Boolean.FALSE);
            return !findExclude && includeMatchers.stream().anyMatch(includeMatchers -> includeMatchers.matches(request));
        }

    }

}
