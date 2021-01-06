package com.baiyan.auth.service.service;

/**
 * token管理
 *
 * @author baiyan
 * @time 2020/11/14
 */
public interface TokenStoreService {

    /**
     * token存储
     * @param token
     * @param userId
     */
    void store(String token, Long userId);

    /**
     * 根据token获取用户id
     * @param token
     * @return
     */
    Long getUserId(String token);

    /**
     * 根据用户id获取token
     * @param userId
     * @return
     */
    String getToken(Long userId);

    /**
     * 删除token
     *
     * @param token
     */
    void delete(String token);

    /**
     * 删除token
     *
     * @param userId
     */
    void delete(Long userId);

}
