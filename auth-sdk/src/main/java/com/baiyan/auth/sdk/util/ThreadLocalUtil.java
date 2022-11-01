package com.baiyan.auth.sdk.util;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.baiyan.auth.api.model.user.UserDTO;
import com.baiyan.common.base.utils.ValidationUtil;

/**
 * @author baiyan
 * @date 2020/11/14
 */
public class ThreadLocalUtil {

    /**
     * 用户信息线程变量
     */
    private static TransmittableThreadLocal<UserDTO> user = new TransmittableThreadLocal<>();

    /**
     * token线程变量
     */
    private static TransmittableThreadLocal<String> token = new TransmittableThreadLocal<>();

    /**
     * accessKey线程变量
     */
    private static TransmittableThreadLocal<String> accessKey = new TransmittableThreadLocal<>();

    public static void setUser(UserDTO userDTO) {
        user.set(userDTO);
    }

    public static void removeUser() {
        user.remove();
    }

    public static UserDTO getUser() {
        UserDTO userDTO = user.get();
        ValidationUtil.notNull(userDTO, "user.message.is.null");
        return userDTO;
    }

    public static void setToken(String token) {
        ThreadLocalUtil.token.set(token);
    }

    public static void setAccessKey(String accessKey) {
        ThreadLocalUtil.accessKey.set(accessKey);
    }

    public static void removeToken() {
        token.remove();
    }

    public static String getToken() {
        return token.get();
    }

    public static void removeAccessKey() {
        accessKey.remove();
    }

    public static String getAccessKey() {
        return accessKey.get();
    }
}