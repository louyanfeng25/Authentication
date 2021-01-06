package com.baiyan.auth.service.mapper;

import com.baiyan.auth.service.model.role.po.UserRolePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户角色关联关系mapper
 *
 * @author baiyan
 * @time 2020/11/13 14:34
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRolePO> {

    /**
     * 批量插入
     * @param pos
     */
    void insertBatches(List<UserRolePO> pos);
}
