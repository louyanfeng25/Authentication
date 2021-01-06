package com.baiyan.auth.service.service.impl;

import com.baiyan.auth.common.utils.CollectionUtil;
import com.baiyan.auth.service.enums.YesNoEnum;
import com.baiyan.auth.service.mapper.MenuMapper;
import com.baiyan.auth.service.mapper.RoleMenuMapper;
import com.baiyan.auth.service.model.menu.dto.MenuDTO;
import com.baiyan.auth.service.model.menu.po.MenuPO;
import com.baiyan.auth.service.model.menu.po.RoleMenuPO;
import com.baiyan.auth.service.service.MenuService;
import com.baiyan.auth.service.service.RoleService;
import com.baiyan.auth.service.utils.MenuDelegates;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author baiyan
 * @time 2020/11/19
 */
@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuPO> implements MenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public List<MenuDTO> tree(Long userId){
        List<Long> menuIds = null;
        if(Objects.nonNull(userId)){
            List<Long> roleIds = roleService.getRoleIdByUserId(userId);
            menuIds = this.listMenuIdsByRoleIds(roleIds);
        }
        List<MenuPO> menuPOS = getBaseMapper().selectList(Wrappers.<MenuPO>lambdaQuery()
                .in(CollectionUtil.isNotEmpty(menuIds),MenuPO::getId,menuIds)
                .eq(MenuPO::getDisable, YesNoEnum.NO.getKey())
                .orderByAsc(MenuPO::getId));
        return MenuDelegates.buildTree(menuPOS, null);
    }

    @Override
    public List<String> listByRoleIds(List<Long> roleIds){
        List<Long> menuIds = this.listMenuIdsByRoleIds(roleIds);
        if(CollectionUtil.isEmpty(menuIds)){
            return null;
        }
        List<MenuPO> menuPos = getBaseMapper().selectList(Wrappers.<MenuPO>lambdaQuery()
                .in(MenuPO::getId, menuIds)
                .eq(MenuPO::getDisable, YesNoEnum.NO.getKey())
                .select(MenuPO::getCode, MenuPO::getCode)
        );
        return Optional.of(menuPos).map(menus-> menus.stream().map(MenuPO::getCode)
                    .collect(Collectors.toList())).orElse(null);
    }

    @Override
    public List<Long> listMenuIdsByRoleIds(List<Long> roleIds){
        if(CollectionUtil.isEmpty(roleIds)){
            return null;
        }
        List<RoleMenuPO> roleMenus = roleMenuMapper.selectList(Wrappers.<RoleMenuPO>lambdaQuery()
                .in(RoleMenuPO::getRoleId, roleIds)
                .select(RoleMenuPO::getMenuId)
        );
        if (CollectionUtil.isEmpty(roleMenus)){
            return null;
        }
        return roleMenus.stream().map(RoleMenuPO::getMenuId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRoleMenus(List<RoleMenuPO> pos){
        roleMenuMapper.insertBatches(pos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenuByRoleIds(List<Long> roleIds){
        if(CollectionUtil.isEmpty(roleIds)){
            return;
        }
        roleMenuMapper.delete(Wrappers.<RoleMenuPO>lambdaQuery().in(RoleMenuPO::getRoleId,roleIds));
    }
}
