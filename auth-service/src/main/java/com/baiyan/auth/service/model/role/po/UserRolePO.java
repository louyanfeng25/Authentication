package com.baiyan.auth.service.model.role.po;

import com.baiyan.common.base.model.po.BaseUuidEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 用户角色关联实体
 *
 * @author baiyan
 * @time 2020/11/14 14:29
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_role")
public class UserRolePO extends BaseUuidEntity {

    /** 用户id */
    private Long userId;

    /** 角色id */
    private Long roleId;

}
