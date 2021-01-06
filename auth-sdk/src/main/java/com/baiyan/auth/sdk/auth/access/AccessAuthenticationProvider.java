package com.baiyan.auth.sdk.auth.access;

import com.baiyan.auth.api.api.AccessApi;
import com.baiyan.auth.api.model.user.UserDTO;
import com.baiyan.auth.sdk.auth.token.UserDetailDelegates;
import com.baiyan.auth.sdk.exception.AccessAuthenticationException;
import com.baiyan.auth.sdk.extractor.access.AccessAuthentication;
import com.baiyan.auth.sdk.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Objects;

/**
 * 重写token认证方式
 *
 * @author baiyan
 * @date 2020/11/14
 */
public class AccessAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AccessApi accessApi;

    @Override
    public Authentication authenticate(Authentication authentication){
        try{
            AccessAuthentication accessAuthentication = (AccessAuthentication) authentication;
            String accessKey = accessAuthentication.getAccessKey();
            UserDTO userDTO = accessApi.getUserDetail(accessKey);
            if (Objects.isNull(userDTO)) {
                throw new AccessAuthenticationException("access " + accessKey + " 失效");
            }
            ThreadLocalUtil.setUser(userDTO);
            ThreadLocalUtil.setAccessKey(accessKey);
            List<GrantedAuthority> authorities = UserDetailDelegates.mapAuthorities(userDTO);
            AccessAuthentication authResult = new AccessAuthentication(accessKey,authorities);
            authResult.setDetails(userDTO);
            authResult.setAuthenticated(true);
            return authResult;
        }catch (Exception e){
            throw new AccessAuthenticationException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AccessAuthentication.class);
    }
}
