package com.baiyan.auth.sdk.extractor.token;

import javax.servlet.http.HttpServletRequest;

/**
 * token认证执行
 *
 * @author baiyan
 * @time 2020/11/14
 */
public interface TokenAuthExtractor{
    /**
     * 根据不同的鉴权方式进行解析
     *
     * @param httpServletRequest
     * @return
     */
    TokenAuthentication extract(HttpServletRequest httpServletRequest);
}
