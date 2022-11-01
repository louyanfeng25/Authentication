package com.baiyan.auth.sdk.extractor.token;

import com.baiyan.auth.api.constant.AuthConstant;
import com.baiyan.common.base.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * token解析
 *
 * @author baiyan
 * @time 2020/11/14
 */
public class BearerTokenAuthExtractorProvider implements TokenExtractorProvider {

    /**
     * 根据请求头中参数进行解析
     *
     * @param httpServletRequest
     * @return
     */
    @Override
    public String extract(HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getHeader(AuthConstant.HEADER_TOKEN_KEY);
        if (StringUtil.isEmpty(auth)) {
            return null;
        }
        String[] strings = auth.split("\\s+");
        if (strings.length == 2) {
            return strings[1];
        }
        return null;
    }

}
