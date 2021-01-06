package com.baiyan.auth.sdk.extractor.access;

import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author baiyan
 * @time 2020/12/4 13:50
 */
public class AccessAuthProviderConfiguration {

    /**
     * access参数解析管理
     *
     * @param providers
     * @return
     */
    @Bean
    public AccessAuthExtractorProviderManager accessAuthExtractorProviderManager(List<AccessExtractorProvider> providers) {
        return new AccessAuthExtractorProviderManager(providers);
    }

    /**
     * 链接参数access解析
     *
     * @return
     */
    @Bean
    public ParamAccessAuthExtractorProvider paramAccessAuthExtractorProvider() {
        return new ParamAccessAuthExtractorProvider();
    }

    /**
     * 链接参数access解析
     *
     * @return
     */
    @Bean
    public HeaderAccessAuthExtractorProvider headerAccessAuthExtractorProvider() {
        return new HeaderAccessAuthExtractorProvider();
    }
}
