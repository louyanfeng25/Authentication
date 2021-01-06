package com.baiyan.auth.service.model.role.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 角色新增修改实体
 *
 * @author baiyan
 * @time 2020/11/17 14:32
 */
@Data
public class RoleCommandDTO {

    @ApiModelProperty("角色id：新增时不需要，修改时必传")
    private Long id;

    @ApiModelProperty("角色名称")
    @NotBlank(message = "{role.add.name.is.blank}")
    @Length(max = 20,message = "{role.add.name.is.too.long}")
    private String name;

    @ApiModelProperty("角色描述")
    @Length(max = 1000,message = "{role.add.description.is.too.long}")
    private String description;

    @ApiModelProperty("资源id列表")
    @NotNull(message = "{user.add.menuIds.is.blank}")
    @Size(min = 1,message = "{user.add.menuIds.is.blank}")
    private List<Long> menuIds;

}
