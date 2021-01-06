package com.baiyan.auth.sdk.extractor.access;

import javax.servlet.http.HttpServletRequest;

/**
 * 三方鉴权码认证执行
 *
 * @author baiyan
 * @time 2020/11/14
 */
public interface AccessAuthExtractor {

    /**
     * 根据不同的鉴权方式进行解析
     *
     * @param httpServletRequest
     * @return
     */
    AccessAuthentication extract(HttpServletRequest httpServletRequest);

}
