package com.baiyan.auth.service.service.impl;

import com.baiyan.auth.service.service.TokenStoreService;
import com.baiyan.auth.service.utils.TokenUtil;
import com.baiyan.common.base.utils.GsonUtil;
import com.baiyan.common.base.utils.StringUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author baiyan
 * @date 2020/11/14
 */
@Service
public class TokenStoreServiceImpl implements TokenStoreService {
    /**
     * token前缀
     */
    private final String PREFIX_AUTH_TOKEN = "auth:token:";

    /**
     * 用户id前缀
     */
    private final String PREFIX_AUTH_USER_ID = "auth:user_id:";

    @Override
    public void store(String token, Long userId) {

        TokenUtil.TOKEN_CACHE.put(PREFIX_AUTH_TOKEN + token,userId.toString());
        TokenUtil.USER_ID_CACHE.put(PREFIX_AUTH_USER_ID + userId,token);
    }

    @Override
    public Long getUserId(String token) {
        return Optional.ofNullable(token)
                .map(e->e.replace(TokenUtil.PREFIX, Strings.EMPTY))
                .map(e->PREFIX_AUTH_TOKEN + e)
                .map(TokenUtil.TOKEN_CACHE::getIfPresent)
                .map(e-> GsonUtil.gsonToBean(GsonUtil.gsonToString(e),Long.class))
                .orElse(null);
    }

    @Override
    public String getToken(Long userId) {
        return Optional.ofNullable(userId)
                .map(e->PREFIX_AUTH_USER_ID + e)
                .map(TokenUtil.USER_ID_CACHE::getIfPresent)
                .map(String::valueOf)
                .orElse(null);
    }

    @Override
    public void delete(String token) {
        if(StringUtil.isBlank(token)){
            return;
        }
        token = token.replace(TokenUtil.PREFIX, Strings.EMPTY);
        Object userId =  TokenUtil.TOKEN_CACHE.getIfPresent(PREFIX_AUTH_TOKEN + token);
        TokenUtil.TOKEN_CACHE.invalidate(PREFIX_AUTH_TOKEN + token);
        if(Objects.nonNull(userId)){
            TokenUtil.USER_ID_CACHE.invalidate(PREFIX_AUTH_USER_ID + userId);
        }
    }

    @Override
    public void delete(Long userId){
        if(Objects.isNull(userId)){
            return;
        }
        Object token = TokenUtil.USER_ID_CACHE.getIfPresent(PREFIX_AUTH_USER_ID + userId);
        TokenUtil.USER_ID_CACHE.invalidate(PREFIX_AUTH_USER_ID + userId);
        if(Objects.nonNull(token)){
            TokenUtil.TOKEN_CACHE.invalidate(PREFIX_AUTH_TOKEN + token);
        }
    }

}
