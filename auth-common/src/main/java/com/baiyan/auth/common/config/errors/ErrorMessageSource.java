package com.baiyan.auth.common.config.errors;

public interface ErrorMessageSource {

    String getMessage(String code, Object... params);

    String getMessage(String code, String defaultMessage, Object... params);

}
