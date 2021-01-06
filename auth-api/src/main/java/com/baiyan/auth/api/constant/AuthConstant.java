package com.baiyan.auth.api.constant;

/**
 * 鉴权常量
 *
 * @author baiyan
 * @date 2020/11/14
 */
public class AuthConstant {

    /**
     * 注入spring参数的key
     */
    public final static String USER_DETAIL = AuthConstant.class.getName() + ".user_detail";

    /**
     * 请求头token的key
     */
    public final static String HEADER_TOKEN_KEY = "Authorization";

    /**
     * 请求头access的key
     */
    public final static String HEADER_ACCESS_KEY = "access";

    /**
     * 请求参数token的key
     */
    public final static String PARAM_TOKEN_KEY = "token";

    /**
     * 请求参数access的key
     */
    public final static String PARAM_ACCESS_KEY = "access";

}
