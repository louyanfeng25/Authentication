package com.baiyan.auth.service.enums;

import com.baiyan.common.base.exception.ValidationException;
import lombok.Getter;

import java.util.Objects;

/**
 * 登录方式枚举
 *
 * @author baiyan
 * @date 2020/11/18
 */
public enum AuthLoginTypeEnum {

    FORM("form", "表单登录"),
    ;

    @Getter
    private String key;
    @Getter
    private String value;

    AuthLoginTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static AuthLoginTypeEnum getByKey(String key) {
        for (AuthLoginTypeEnum e : values()) {
            if (Objects.equals(key, e.key)) {
                return e;
            }
        }
        throw new ValidationException("没有找到相应登录模式的key：" + key);
    }
}
