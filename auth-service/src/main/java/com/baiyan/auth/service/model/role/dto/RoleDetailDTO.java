package com.baiyan.auth.service.model.role.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 角色列表
 *
 * @author baiyan
 * @time 2020/11/17 14:32
 */
@Data
@NoArgsConstructor
public class RoleDetailDTO {

    @ApiModelProperty("角色id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("菜单资源")
    private List<String> menus;

}
