package com.baiyan.auth.api.enums;

import lombok.Getter;

/**
 * 菜单类型枚举
 *
 * @author baiyan
 * @date 2020/11/23
 */
public enum MenuTypeEnum {

    MENU("menu", "菜单"),
    BUTTON("button", "按钮"),
    ;

    @Getter
    private String key;
    @Getter
    private String value;

    MenuTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
