package com.baiyan.auth.service.service.impl;

import com.baiyan.auth.api.enums.UserStateEnum;
import com.baiyan.auth.api.model.permission.PermissionDTO;
import com.baiyan.auth.api.model.role.RoleDTO;
import com.baiyan.auth.api.model.user.UserDTO;
import com.baiyan.auth.service.model.menu.po.MenuPO;
import com.baiyan.auth.service.model.role.po.RolePO;
import com.baiyan.auth.service.model.user.po.UserPO;
import com.baiyan.auth.service.service.*;
import com.baiyan.common.base.utils.CollectionUtils;
import com.baiyan.common.base.utils.ValidationUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author baiyan
 * @date 2020/11/14
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private TokenStoreService tokenStoreService;

    @Override
    public UserDTO getUserDetail(Long id,Integer userState){
        ValidationUtil.isTrue(Objects.nonNull(id),"user.query.id.is.not.null");
        UserDTO userDTO = new UserDTO();
        //设置用户信息
        UserPO user = this.setUserInfo(id, userDTO, userState);
        if(this.checkUserState(userState,user.getState())){
            return null;
        }
        //设置角色信息
        List<Long> roleIds = roleService.getRoleIdByUserId(id);
        List<RolePO> roles= this.getRoleInfo(roleIds);
        this.setRoleInfo(roles, userDTO);
        //设置资源信息
        this.setPermissionInfo(roleIds, userDTO);
        return userDTO;
    }

    @Override
    public UserDTO getUserDetail(String token,Integer userState) {
        Long userId = tokenStoreService.getUserId(token);
        ValidationUtil.notNull(userId,"auth.token.is.useless");
        return this.getUserDetail(userId,userState);
    }

    /**
     * 设置用户信息
     *
     * @param userId
     * @param userDTO
     */
    public UserPO setUserInfo(Long userId, UserDTO userDTO,Integer userState){
        UserPO user = userService.getById(userId);
        ValidationUtil.notNull(user,"user.query.is.not.exist");
        if(this.checkUserState(userState,user.getState())){
            return user;
        }
        BeanUtils.copyProperties(user, userDTO);
        return user;
    }

    /**
     * 判断用户状态信息
     *
     * @param userState 期望的用户状态
     * @param dbState 数据库中用户状态
     * @return
     */
    private Boolean checkUserState(Integer userState,Integer dbState){
        return Objects.nonNull(userState) && !Objects.equals(dbState,UserStateEnum.getByKey(userState).getKey());
    }

    /**
     * 设置角色信息
     *
     * @param roles 用户角色信息
     * @param userDTO
     */
    public void setRoleInfo(List<RolePO> roles, UserDTO userDTO){
        if(CollectionUtils.isEmpty(roles)){
            return;
        }
        List<RoleDTO> details = roles.stream().map(role -> new RoleDTO(role.getId(), role.getCode(), role.getName()))
                .collect(Collectors.toList());
        userDTO.setRoles(details);
    }

    /**
     * 获取角色信息
     *
     * @return
     */
    public List<RolePO> getRoleInfo(List<Long> roleIds){
        if(CollectionUtils.isEmpty(roleIds)){
            return null;
        }
        return roleService.listByIds(roleIds);
    }

    /**
     * 设置用户资源信息
     *
     * @param roleIds 角色id
     * @param userDTO
     */
    public void setPermissionInfo(List<Long> roleIds, UserDTO userDTO){
        List<Long> menuIds = menuService.listMenuIdsByRoleIds(roleIds);
        List<MenuPO> menuPos = Optional.ofNullable(menuIds).map(menuService::listByIds)
                .orElse(null);
        if(CollectionUtils.isEmpty(menuPos)){
            return;
        }
        Optional.ofNullable(menuService.listByIds(menuIds)).ifPresent(menuPOS -> {
             List<PermissionDTO> permissions = menuPOS.stream().map(menuPO -> {
                         PermissionDTO permissionDTO = new PermissionDTO();
                         BeanUtils.copyProperties(menuPO,permissionDTO);
                         return permissionDTO;
                     }).collect(Collectors.toList());
             userDTO.setPermissions(permissions);
         });
    }
}
