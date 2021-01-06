package com.baiyan.auth.sdk.extractor.access;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * token解析
 *
 * @author baiyan
 * @time 2020/11/14
 */
@NoArgsConstructor
@AllArgsConstructor
public class AccessAuthExtractorProviderManager implements AccessAuthExtractor {

    private List<AccessExtractorProvider> providers = new ArrayList<>();

    @Override
    public AccessAuthentication extract(HttpServletRequest httpServletRequest){
        return providers.stream()
                .map(provider -> provider.extract(httpServletRequest))
                .filter(Objects::nonNull)
                .findAny()
                .map(AccessAuthentication::new)
                .orElse(null);
    }

}
