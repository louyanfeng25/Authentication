package com.baiyan.auth.common.config.errors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SpringMessageSourceErrorMessageSource implements ErrorMessageSource {

    @Autowired
    private MessageSource messageSource;

    @Override
    public String getMessage(String code, Object... params) {
        return messageSource.getMessage(code, params, LocaleContextHolder.getLocale());
    }

    @Override
    public String getMessage(String code, String defaultMessage, Object... params) {
        return messageSource.getMessage(code, params, defaultMessage, LocaleContextHolder.getLocale());
    }
}
