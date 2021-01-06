package com.baiyan.auth.service.model.user.dto;

import com.baiyan.auth.api.model.role.RoleDTO;
import com.baiyan.auth.service.model.menu.dto.MenuDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户新增
 *
 * @author baiyan
 * @time 2020/11/17 16:23
 */
@Data
public class UserAuthDTO {

    @ApiModelProperty("用户id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("显示名称")
    private String realName;

    @ApiModelProperty("角色信息")
    private List<RoleDTO> roles;

    @ApiModelProperty("菜单权限")
    private List<MenuDTO> menus;

    @ApiModelProperty("按钮权限")
    private List<String> buttons;

}
