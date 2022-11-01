package com.baiyan.auth.service.model.role.dto;

import com.baiyan.auth.service.model.role.bo.RoleBO;
import com.baiyan.common.base.utils.StringUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 角色列表
 *
 * @author baiyan
 * @time 2020/11/17 14:32
 */
@Data
@NoArgsConstructor
public class RoleListDTO {

    @ApiModelProperty("角色id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色描述")
    private String description;

    @ApiModelProperty("角色下用户")
    private List<String> users;

    @ApiModelProperty("能否编辑，1：能，0：否")
    private Integer canEdit;

    @ApiModelProperty("能否删除，1：能，0：否")
    private Integer canDelete;

    public RoleListDTO(RoleBO bo){
        BeanUtils.copyProperties(bo,this);
        this.users = StringUtil.isNotBlank(bo.getUsers()) ?
                Stream.of(bo.getUsers().split(",")).collect(Collectors.toList()) : null;
    }
}
