package com.baiyan.auth.api.model.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色信息
 * @author baiyan
 * @date 2020/11/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色code
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

}
