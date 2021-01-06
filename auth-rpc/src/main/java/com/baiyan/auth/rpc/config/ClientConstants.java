package com.baiyan.auth.rpc.config;

/**
 * client配置
 *
 * @author baiyan
 * @time 2020/11/18
 */
public class ClientConstants {

    public static final String DEFAULT_SERVICE_NAME = "${auth.client.name:auth}";

    public static class User {
        public static final String NAME = "auth-user";
        public static final String SERVICE_NAME = "${" + NAME + ".client.name:" + DEFAULT_SERVICE_NAME + "}";
    }

    public static class Access {
        public static final String NAME = "auth-access";
        public static final String SERVICE_NAME = "${" + NAME + ".client.name:" + DEFAULT_SERVICE_NAME + "}";
    }

}
