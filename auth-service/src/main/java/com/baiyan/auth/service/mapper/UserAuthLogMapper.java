package com.baiyan.auth.service.mapper;

import com.baiyan.auth.service.model.login.po.UserAuthLogPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志mapper
 *
 * @author baiyan
 * @time 2020/11/18 14:34
 */
@Mapper
public interface UserAuthLogMapper extends BaseMapper<UserAuthLogPO> {

}
