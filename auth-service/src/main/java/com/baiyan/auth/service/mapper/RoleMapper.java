package com.baiyan.auth.service.mapper;

import com.baiyan.auth.common.model.query.KeywordQuery;
import com.baiyan.auth.common.result.Page;
import com.baiyan.auth.service.model.role.bo.RoleBO;
import com.baiyan.auth.service.model.role.po.RolePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色mapper
 *
 * @author baiyan
 * @time 2020/11/13 14:34
 */
@Mapper
public interface RoleMapper extends BaseMapper<RolePO> {

    /**
     * 列表数据查询
     * @param query
     * @return
     */
    Page<RoleBO> page(KeywordQuery query);

}
