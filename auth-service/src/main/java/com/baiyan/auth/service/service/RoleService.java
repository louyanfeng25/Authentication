package com.baiyan.auth.service.service;

import com.baiyan.auth.common.model.query.KeywordQuery;
import com.baiyan.auth.common.result.Page;
import com.baiyan.auth.service.model.role.dto.RoleCommandDTO;
import com.baiyan.auth.service.model.role.dto.RoleDetailDTO;
import com.baiyan.auth.service.model.role.dto.RoleListDTO;
import com.baiyan.auth.service.model.role.po.RolePO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author baiyan
 * @time 2020/11/13 14:31
 */
public interface RoleService extends IService<RolePO> {

    /**
     * 根据用户id查询角色id
     * @param userId
     * @return
     */
    List<Long> getRoleIdByUserId(Long userId);

    /**
     * 根据用户id删除关联关系
     *
     * @param userIds
     * @return
     */
    void deleteUserRoleByUserId(List<Long> userIds);

    /**
     * 增加用户角色
     * @param userId
     * @return
     */
    void addRoles(Long userId,List<Long> roleIds);

    /**
     * 删除用户相关角色
     * @param userId
     * @return
     */
    void removeRoles(Long userId,List<Long> roleIds);

    /**
     * 角色详情
     *
     * @param id
     */
    RoleDetailDTO detail(Long id);

    /**
     * 角色新增
     *
     * @param dto
     */
    void add(RoleCommandDTO dto);

    /**
     * 角色修改
     *
     * @param dto
     */
    void update(RoleCommandDTO dto);

    /**
     * 角色删除
     *
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 角色分页数据
     *
     * @param query
     * @return
     */
    Page<RoleListDTO> list(KeywordQuery query);

}
