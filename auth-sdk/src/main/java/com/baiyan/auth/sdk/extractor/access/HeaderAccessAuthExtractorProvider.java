package com.baiyan.auth.sdk.extractor.access;

import com.baiyan.auth.api.constant.AuthConstant;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 通行证解析
 *
 * @author baiyan
 * @time 2020/12/04
 */
public class HeaderAccessAuthExtractorProvider implements AccessExtractorProvider {

    /**
     * 根据请求头中参数进行解析
     *
     * @param httpServletRequest
     * @return
     */
    @Override
    public String extract(HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getHeader(AuthConstant.HEADER_ACCESS_KEY);
        if (StringUtils.isEmpty(auth)) {
            return null;
        }
        return auth;
    }

}
