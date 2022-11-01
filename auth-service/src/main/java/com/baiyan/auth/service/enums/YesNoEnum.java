package com.baiyan.auth.service.enums;

import com.baiyan.common.base.exception.ValidationException;
import lombok.Getter;

import java.util.Objects;

/**
 * 是否判断枚举
 *
 * @author baiyan
 * @date 2020/11/14
 */
public enum YesNoEnum {

    YES(1, "是"),
    NO(0, "否"),
    ;

    @Getter
    private Integer key;
    @Getter
    private String value;

    YesNoEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static YesNoEnum getByKey(Integer key) {
        for (YesNoEnum e : values()) {
            if (Objects.equals(key, e.key)) {
                return e;
            }
        }
        throw new ValidationException("没有找到相应的key：" + key);
    }
}
