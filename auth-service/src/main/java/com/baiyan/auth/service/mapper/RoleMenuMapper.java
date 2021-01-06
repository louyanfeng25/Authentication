package com.baiyan.auth.service.mapper;

import com.baiyan.auth.service.model.menu.po.RoleMenuPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色菜单关联关系apper
 *
 * @author baiyan
 * @time 2020/11/13 14:34
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenuPO> {

    /**
     * 批量插入
     * @param pos
     */
    void insertBatches(List<RoleMenuPO> pos);
}
