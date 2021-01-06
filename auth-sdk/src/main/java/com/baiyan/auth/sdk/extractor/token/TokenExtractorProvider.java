package com.baiyan.auth.sdk.extractor.token;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求合法性解析
 *
 * @author baiyan
 * @time 2020/11/14
 */
public interface TokenExtractorProvider {

    /**
     * 解析请求体获取token
     *
     * @param httpServletRequest
     * @return
     */
    String extract(HttpServletRequest httpServletRequest);

}
