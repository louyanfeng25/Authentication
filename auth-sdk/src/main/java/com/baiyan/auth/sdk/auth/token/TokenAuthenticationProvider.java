package com.baiyan.auth.sdk.auth.token;

import com.baiyan.auth.api.api.UserApi;
import com.baiyan.auth.api.enums.UserStateEnum;
import com.baiyan.auth.api.model.user.UserDTO;
import com.baiyan.auth.sdk.exception.TokenAuthenticationException;
import com.baiyan.auth.sdk.extractor.token.TokenAuthentication;
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
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserApi userApi;

    @Override
    public Authentication authenticate(Authentication authentication){
        try{
            TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
            String token = tokenAuthentication.getToken();
            UserDTO userDTO = userApi.getUserDetail(token, UserStateEnum.AVAILABLE.getKey());
            if (Objects.isNull(userDTO)) {
                throw new TokenAuthenticationException("token " + token + " 失效");
            }
            ThreadLocalUtil.setToken(token);
            ThreadLocalUtil.setUser(userDTO);
            List<GrantedAuthority> authorities = UserDetailDelegates.mapAuthorities(userDTO);
            TokenAuthentication authResult = new TokenAuthentication(token, authorities);
            authResult.setDetails(userDTO);
            authResult.setAuthenticated(true);
            return authResult;
        }catch (Exception e){
            throw new TokenAuthenticationException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(TokenAuthentication.class);
    }
}
