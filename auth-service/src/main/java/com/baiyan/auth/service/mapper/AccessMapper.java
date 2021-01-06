package com.baiyan.auth.service.mapper;

import com.baiyan.auth.service.model.access.po.AccessPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 授权mapper
 *
 * @author baiyan
 * @time 2020/12/04
 */
@Mapper
public interface AccessMapper extends BaseMapper<AccessPO> {

    /**
     * 查询access列表
     * @param userId 用户id
     * @param keyword 备注关键字
     * @return
     */
    List<AccessPO> accessList(@Param("userId") Long userId,@Param("keyword") String keyword);
}
