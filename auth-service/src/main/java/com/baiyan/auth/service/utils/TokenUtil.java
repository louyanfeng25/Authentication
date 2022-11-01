package com.baiyan.auth.service.utils;

import com.baiyan.auth.service.config.LoginConfig;
import com.baiyan.common.base.utils.cache.CacheFactory;
import com.baiyan.common.service.util.SpringContextUtil;
import com.google.common.cache.Cache;

import java.util.UUID;

/**
 * token管理工具类
 *
 * @author baiyan
 * @time 2020/11/14 10:21
 */
public class TokenUtil {
    /**
     * token前缀
     */
    public final static String PREFIX = "Bearer ";

    /**
     * 缓存过期时间
     */
    public final static Long TOKEN_EXPIRE_TIME = SpringContextUtil.getBean(LoginConfig.class)
            .getTokenTimeout();

    /**
     * token缓存
     */
    public final static Cache<String, Object> TOKEN_CACHE =  CacheFactory.instance.getCache(TOKEN_EXPIRE_TIME);

    /**
     * 用户id缓存
     */
    public final static Cache<String, Object> USER_ID_CACHE =  CacheFactory.instance.getCache(TOKEN_EXPIRE_TIME);

    /**
     * 创建token
     *
     * @return token值
     */
    public static String createToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }


}
