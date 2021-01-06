package com.baiyan.auth.api.model.user;

import com.baiyan.auth.api.enums.UserStateEnum;
import com.baiyan.auth.api.model.permission.PermissionDTO;
import com.baiyan.auth.api.model.role.RoleDTO;
import lombok.Data;

import java.util.List;

/**
 * 用户信息
 *
 * @author baiyan
 * @date 2020/11/19
 */
@Data
public class UserDTO {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户展示名称
     */
    private String realName;

    /**
     * 用户状态
     * @see UserStateEnum
     */
    private Integer state;

    /**
     * 角色信息
     */
    private List<RoleDTO> roles;

    /**
     * 权限信息
     */
    private List<PermissionDTO> permissions;
}
