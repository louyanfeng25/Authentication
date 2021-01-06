package com.baiyan.auth.service.service;

import com.baiyan.auth.service.model.menu.dto.MenuDTO;
import com.baiyan.auth.service.model.menu.po.MenuPO;
import com.baiyan.auth.service.model.menu.po.RoleMenuPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 权限服务
 *
 * @author baiyan
 * @time 2020/11/19
 */
public interface MenuService extends IService<MenuPO> {

    /**
     * 获取作为资源数据转换成一个树返回
     *
     * @param userId
     * @return
     */
    List<MenuDTO> tree(Long userId);

    /**
     * 根据角色id列表获取资源信息
     * @param roleIds
     * @return
     */
    List<String> listByRoleIds(List<Long> roleIds);

    /**
     * 根据角色id列表获取资源ID信息
     *
     * @param roleIds
     * @return
     */
    List<Long> listMenuIdsByRoleIds(List<Long> roleIds);

    /**
     * 批量插入菜单角色关联关系
     *
     * @param pos
     * @return
     */
    void insertRoleMenus(List<RoleMenuPO> pos);

    /**
     * 删除角色关联配置
     *
     * @param roleIds
     * @return
     */
    void deleteRoleMenuByRoleIds(List<Long> roleIds);

}
