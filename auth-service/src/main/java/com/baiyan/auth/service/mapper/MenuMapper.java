package com.baiyan.auth.service.mapper;

import com.baiyan.auth.service.model.menu.po.MenuPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单mapper
 *
 * @author baiyan
 * @time 2020/11/13 14:34
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuPO> {
}
