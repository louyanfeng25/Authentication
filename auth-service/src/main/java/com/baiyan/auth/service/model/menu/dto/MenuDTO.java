package com.baiyan.auth.service.model.menu.dto;

import com.baiyan.auth.common.utils.tree.IDTree;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 菜单实体
 *
 * @author baiyan
 * @time 2020/11/14 14:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MenuDTO extends IDTree<MenuDTO> {

    @ApiModelProperty(value = "资源id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(value = "上级资源id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;

    @ApiModelProperty(value = "类型：menu-菜单，button—按钮")
    private String type;

    @ApiModelProperty(value = "菜单级别")
    private Integer level;

    @ApiModelProperty(value = "菜单code")
    private String code;

    @ApiModelProperty(value = "菜单跳转的url，为空由前端控制跳转路径")
    private String path;

    @ApiModelProperty(value = "菜单排序")
    private Integer sort;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "true没有子节点")
    private Boolean nonChildren;

}
