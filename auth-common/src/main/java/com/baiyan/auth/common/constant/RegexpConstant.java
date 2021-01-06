package com.baiyan.auth.common.constant;

/**
 * 正则常量
 * 
 * @author baiyan
 * @time 2020/11/23 19:14
 */
public class RegexpConstant {

    /**
     * 用户名
     */
    public final static String REGEXP_USERNAME = "^[a-zA-Z][a-zA-Z0-9_]{3,19}$";

    /**
     * 密码
     */
    public final static String USER_PWD = "^(?![\\d]+$)(?![\\da-z]+$)(?![\\dA-Z]+$)(?![\\d~!@#$%^&*()_+=]+$)(?![a-z]+$)(?![a-zA-Z]+$)(?![a-z~!@#$%^&*()_+=]+$)(?![A-Z]+$)(?![A-Z~!@#$%^&*()_+=]+$)(?![~!@#$%^&*()_+=]+$)[\\da-zA-Z~!@#$%^&*()_+=]{8,16}$";

}

