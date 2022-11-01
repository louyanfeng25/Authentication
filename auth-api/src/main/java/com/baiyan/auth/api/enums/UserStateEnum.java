package com.baiyan.auth.api.enums;

import com.baiyan.common.base.exception.ValidationException;
import lombok.Getter;

import java.util.Objects;

/**
 * 用户状态枚举
 *
 * @author baiyan
 * @date 2020/11/17
 */
public enum UserStateEnum {

    AVAILABLE(1, "可用"),
    DISABLE(0, "禁用"),
    DELETED(-1, "删除"),
    ;

    @Getter
    private Integer key;
    @Getter
    private String value;

    UserStateEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static UserStateEnum getByKey(Integer key) {
        for (UserStateEnum e : values()) {
            if (Objects.equals(key, e.key)) {
                return e;
            }
        }
        throw new ValidationException("没有找到相应的用户状态key：" + key);
    }
}
