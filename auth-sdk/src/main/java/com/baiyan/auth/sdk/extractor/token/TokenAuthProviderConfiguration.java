package com.baiyan.auth.sdk.extractor.token;

import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author baiyan
 * @time 2020/12/4 13:50
 */
public class TokenAuthProviderConfiguration {

    /**
     * token参数解析管理
     *
     * @param providers
     * @return
     */
    @Bean
    public TokenAuthExtractorProviderManager tokenAuthExtractorProviderManager(List<TokenExtractorProvider> providers) {
        return new TokenAuthExtractorProviderManager(providers);
    }

    /**
     * 请求头参数token解析
     *
     * @return
     */
    @Bean
    public BearerTokenAuthExtractorProvider headerAuthTokenExtractorProvider() {
        return new BearerTokenAuthExtractorProvider();
    }

    /**
     * 链接参数token解析
     *
     * @return
     */
    @Bean
    public ParamTokenAuthExtractorProvider paramAuthTokenExtractorProvider() {
        return new ParamTokenAuthExtractorProvider();
    }

}
