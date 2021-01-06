package com.baiyan.auth.sdk.auth.token;

import com.baiyan.auth.api.model.permission.PermissionDTO;
import com.baiyan.auth.api.model.role.RoleDTO;
import com.baiyan.auth.api.model.user.UserDTO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 用户权限信息
 *
 * @author baiyan
 * @date 2020/11/14
 */
@Data
public class UserDetailDelegates {

    public static List<GrantedAuthority> mapAuthorities(UserDTO userDTO) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        //角色权限
        List<RoleDTO> roles = userDTO.getRoles();
        Optional.ofNullable(roles).ifPresent(e->e.stream()
                .map(RoleDTO::getCode)
                .map(code -> "ROLE_" + code)
                .map(SimpleGrantedAuthority::new)
                .forEach(authorities::add));

        //菜单按钮权限
        List<PermissionDTO> permissions = userDTO.getPermissions();
        Optional.ofNullable(permissions).ifPresent(e->e.stream()
                .map(PermissionDTO::getCode)
                .map(SimpleGrantedAuthority::new)
                .forEach(authorities::add));
        return authorities;
    }

}
