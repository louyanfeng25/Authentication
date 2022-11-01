package com.baiyan.auth.sdk.extractor.access;

import com.baiyan.auth.api.constant.AuthConstant;
import com.baiyan.common.base.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 通行证解析
 *
 * @author baiyan
 * @time 2020/12/04
 */
public class ParamAccessAuthExtractorProvider implements AccessExtractorProvider {

    /**
     * 根据请求头中参数进行解析
     *
     * @param httpServletRequest
     * @return
     */
    @Override
    public String extract(HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getParameter(AuthConstant.PARAM_ACCESS_KEY);
        if (StringUtil.isEmpty(auth)) {
            return null;
        }
        return auth;
    }

}
