package com.baiyan.auth.service.model.role.bo;

import lombok.Data;

/**
 * @author baiyan
 * @time 2020/11/17 21:12
 */
@Data
public class RoleBO {

    /** 角色Id */
    private Long id;

    /** 角色名称 */
    private String name;

    /** 描述 */
    private String description;

    /** 角色下用户列表 */
    private String users;

    /** 能否编辑 */
    private Integer canEdit;

    /** 能否删除 */
    private Integer canDelete;

}
