package com.baiyan.auth.service.model.role.po;

import com.baiyan.auth.common.model.po.BaseUuidEntity;
import com.baiyan.auth.service.model.role.dto.RoleCommandDTO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.beans.BeanUtils;

/**
 * 角色实体
 *
 * @author baiyan
 * @time 2020/11/14 14:29
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_role")
public class RolePO extends BaseUuidEntity  {

    /** 角色名称 */
    private String name;

    /** 角色code */
    private String code;

    /** 描述 */
    private String description;

    /** 能否编辑 */
    private Integer canEdit;

    /** 能否删除 */
    private Integer canDelete;

    public RolePO(RoleCommandDTO dto){
        BeanUtils.copyProperties(dto,this);
    }

}
