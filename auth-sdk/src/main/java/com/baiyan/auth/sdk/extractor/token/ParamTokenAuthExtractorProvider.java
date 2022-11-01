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
public class ParamTokenAuthExtractorProvider implements TokenExtractorProvider {

    /**
     * 根据请求链接参数token进行解析
     *
     * @param httpServletRequest
     * @return
     */
    @Override
    public String extract(HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getParameter(AuthConstant.PARAM_TOKEN_KEY);
        return StringUtil.isEmpty(auth) ? null : auth;
    }

}
