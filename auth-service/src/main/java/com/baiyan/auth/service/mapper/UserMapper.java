package com.baiyan.auth.service.mapper;

import com.baiyan.auth.service.model.user.bo.UserBO;
import com.baiyan.auth.service.model.user.po.UserPO;
import com.baiyan.common.base.model.query.KeywordQuery;
import com.baiyan.common.base.result.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息mapper
 *
 * @author baiyan
 * @time 2020/11/13 14:34
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {

    /**
     * 列表数据查询
     * @param query
     * @return
     */
    Page<UserBO> page(KeywordQuery query);

}
