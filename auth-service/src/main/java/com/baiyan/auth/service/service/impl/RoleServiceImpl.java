package com.baiyan.auth.service.service.impl;

import com.baiyan.auth.common.model.query.KeywordQuery;
import com.baiyan.auth.common.result.Page;
import com.baiyan.auth.common.utils.CollectionUtil;
import com.baiyan.auth.common.utils.JavaUtil;
import com.baiyan.auth.common.utils.ValidationUtil;
import com.baiyan.auth.service.enums.YesNoEnum;
import com.baiyan.auth.service.mapper.RoleMapper;
import com.baiyan.auth.service.mapper.UserRoleMapper;
import com.baiyan.auth.service.model.menu.po.RoleMenuPO;
import com.baiyan.auth.service.model.role.bo.RoleBO;
import com.baiyan.auth.service.model.role.dto.RoleCommandDTO;
import com.baiyan.auth.service.model.role.dto.RoleDetailDTO;
import com.baiyan.auth.service.model.role.dto.RoleListDTO;
import com.baiyan.auth.service.model.role.po.RolePO;
import com.baiyan.auth.service.model.role.po.UserRolePO;
import com.baiyan.auth.service.service.MenuService;
import com.baiyan.auth.service.service.RoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author baiyan
 * @time 2020/11/13 14:31
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RolePO> implements RoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private MenuService menuService;

    @Override
    public List<Long> getRoleIdByUserId(Long userId){
        if(Objects.isNull(userId)){
            return null;
        }
        List<UserRolePO> pos = userRoleMapper.selectList(Wrappers.<UserRolePO>lambdaQuery().eq(UserRolePO::getUserId, userId));
        if(CollectionUtil.isEmpty(pos)){
            return null;
        }
        return pos.stream().map(UserRolePO::getRoleId).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRoleByUserId(List<Long> userIds){
        userRoleMapper.delete(Wrappers.<UserRolePO>lambdaQuery().in(UserRolePO::getUserId,userIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRoles(Long userId,List<Long> roleIds){
        ValidationUtil.isTrue(Objects.nonNull(userId),"role.userId.is.blank");
        ValidationUtil.isTrue(CollectionUtil.isNotEmpty(roleIds),"role.roleIds.is.blank");
        List<Long> allRoleIds = getBaseMapper().selectList(Wrappers.<RolePO>lambdaQuery().select(RolePO::getId))
                .stream().map(RolePO::getId).collect(Collectors.toList());
        List<UserRolePO> pos = roleIds.stream().map(roleId -> {
            ValidationUtil.isTrue(allRoleIds.contains(roleId),"role.roleId.is.not.exist");
            return new UserRolePO(userId, roleId);
        }).collect(Collectors.toList());
        userRoleMapper.insertBatches(pos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRoles(Long userId,List<Long> roleIds){
        ValidationUtil.isTrue(Objects.nonNull(userId),"role.userId.is.blank");
        ValidationUtil.isTrue(CollectionUtil.isNotEmpty(roleIds),"role.roleIds.is.blank");
        userRoleMapper.delete(Wrappers.<UserRolePO>lambdaQuery()
                .eq(UserRolePO::getUserId,userId)
                .in(UserRolePO::getRoleId,roleIds)
                .eq(UserRolePO::getDeleted, YesNoEnum.NO.getKey())
        );
    }

    @Override
    public RoleDetailDTO detail(Long id){
        RolePO byId = getById(id);
        ValidationUtil.notNull(byId,"role.is.not.exist");

        RoleDetailDTO detail = new RoleDetailDTO();
        detail.setId(id);
        detail.setName(byId.getName());
        detail.setMenus(menuService.listByRoleIds(Lists.newArrayList(id)));
        return detail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(RoleCommandDTO dto){
        RolePO one = getOne(Wrappers.<RolePO>lambdaQuery().eq(RolePO::getName, dto.getName()));
        ValidationUtil.isTrue(Objects.isNull(one),"role.name.is.existed");
        //角色新增
        RolePO rolePO = new RolePO(dto);
        rolePO.setCode(UUID.randomUUID().toString().replace("-", ""));
        getBaseMapper().insert(rolePO);
        //插入关联关系
        List<RoleMenuPO> roleMenuPOS = dto.getMenuIds().stream()
                .map(menuId -> new RoleMenuPO(rolePO.getId(), menuId))
                .collect(Collectors.toList());
        menuService.insertRoleMenus(roleMenuPOS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RoleCommandDTO dto){
        ValidationUtil.notNull(dto.getId(),"role.update.id.is.null");
        RolePO byId = getById(dto.getId());
        ValidationUtil.isTrue(Objects.nonNull(byId),"role.is.not.exist");
        ValidationUtil.isTrue(Objects.equals(byId.getCanEdit(),YesNoEnum.YES.getKey()),"role.system.can.not.update");
        //修改角色
        RolePO rolePO = new RolePO(dto);
        rolePO.setId(dto.getId());
        updateById(rolePO);
        //修改角色资源关联关系
        this.updateMenus(dto.getId(),dto.getMenuIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids){
        //系统配置角色不可删除
        List<Long> systemRoleIds = getBaseMapper().selectList(Wrappers.<RolePO>lambdaQuery()
                .eq(RolePO::getCanDelete,YesNoEnum.NO.getKey())
                .select(RolePO::getId)
        ).stream().map(RolePO::getId).collect(Collectors.toList());
        ids.forEach(id->ValidationUtil.isFalse(systemRoleIds.contains(id),"role.system.can.not.delete"));
        //删除角色
        getBaseMapper().deleteBatchIds(ids);
        //删除角色菜单关联关系
        menuService.deleteRoleMenuByRoleIds(Lists.newArrayList(ids));
        //删除用户角色关联关系
        userRoleMapper.delete(Wrappers.<UserRolePO>lambdaQuery().in(UserRolePO::getRoleId,ids));
    }

    @Override
    public Page<RoleListDTO> list(KeywordQuery query){
        Page<RoleBO> page = getBaseMapper().page(query);
        return page.convert(RoleListDTO::new);
    }

    /**
     * 修改角色关联的菜单资源信息
     *
     * @param roleId 角色id
     * @param menus 菜单资源列表
     */
    private void updateMenus(Long roleId,List<Long> menus){
        //修改角色资源关联关系
        List<Long> nowMenus = menuService.listMenuIdsByRoleIds(Lists.newArrayList(roleId));
        //待删除新增
        List<Long> add = menus.stream().filter(menuId->!nowMenus.contains(menuId))
                .collect(Collectors.toList());
        //待新增资源
        List<Long> remove = nowMenus.stream().filter(menuId->!menus.contains(menuId))
                .collect(Collectors.toList());
        JavaUtil.getJavaUtil()
                .acceptIfCondition(CollectionUtil.isNotEmpty(remove),remove,menuService::deleteRoleMenuByRoleIds)
                .acceptIfCondition(CollectionUtil.isNotEmpty(add),add,value->{
                            List<RoleMenuPO> pos = value.stream().map(e ->
                                    new RoleMenuPO(roleId, e)).collect(Collectors.toList()
                            );
                            menuService.insertRoleMenus(pos);
                        }
                );
    }

}
