package com.baiyan.auth.api.model.permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资源信息
 *
 * @author baiyan
 * @date 2020/11/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDTO {

    /**
     * 资源id
     */
    private Long id;

    /**
     * 资源code
     */
    private String code;

    /**
     * 资源类型
     * @see com.baiyan.auth.api.enums.MenuTypeEnum
     */
    private String type;

    /**
     * 资源名称
     */
    private String name;

}
