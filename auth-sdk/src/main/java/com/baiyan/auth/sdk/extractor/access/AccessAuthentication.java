package com.baiyan.auth.sdk.extractor.access;

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
public class AccessAuthentication extends AbstractAuthenticationToken {

    @Getter
    private String accessKey;

    public AccessAuthentication(String accessKey) {
        this(accessKey, null);
    }
    public AccessAuthentication(String accessKey, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.accessKey = accessKey;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return accessKey;
    }
}
