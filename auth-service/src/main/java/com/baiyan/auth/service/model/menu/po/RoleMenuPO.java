package com.baiyan.auth.service.model.menu.po;

import com.baiyan.auth.common.model.po.BaseUuidEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 角色菜单关联实体
 *
 * @author baiyan
 * @time 2020/11/14 14:30
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_role_menu")
public class RoleMenuPO extends BaseUuidEntity {

    /** 角色id */
    private Long roleId;

    /** 菜单id */
    private Long menuId;

}
