package com.baiyan.auth.sdk.extractor.token;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * token认证执行
 *
 * @author baiyan
 * @time 2020/11/14
 */
@EqualsAndHashCode(callSuper = true)
public class TokenAuthentication extends AbstractAuthenticationToken {

    @Getter
    private String token;

    public TokenAuthentication(String token) {
        this(token, null);
    }

    public TokenAuthentication(String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
}
