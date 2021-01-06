package com.baiyan.auth.sdk.extractor.token;

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
public class TokenAuthExtractorProviderManager implements TokenAuthExtractor {

    private List<TokenExtractorProvider> providers = new ArrayList<>();

    @Override
    public TokenAuthentication extract(HttpServletRequest httpServletRequest) {
        return providers.stream()
                .map(provider -> provider.extract(httpServletRequest))
                .filter(Objects::nonNull)
                .findAny()
                .map(TokenAuthentication::new)
                .orElse(null);
    }

}
