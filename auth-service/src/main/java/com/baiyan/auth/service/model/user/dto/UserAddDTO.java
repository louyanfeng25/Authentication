package com.baiyan.auth.service.model.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 用户新增
 *
 * @author baiyan
 * @time 2020/11/17 16:23
 */
@Data
public class UserAddDTO {

    @ApiModelProperty("用户名称")
    @NotBlank(message = "{user.add.username.is.blank}")
    @Length(max = 20,message = "{user.add.username.is.too.long}")
    private String userName;

    @ApiModelProperty("显示名称")
    @NotBlank(message = "{user.add.realName.is.blank}")
    @Length(max = 30,message = "{user.add.realName.is.too.long}")
    private String realName;

    @ApiModelProperty("角色id列表")
    @NotNull(message = "{user.add.roleIds.is.blank}")
    @Size(min=1,message = "{user.add.roleIds.is.blank}")
    private List<Long> roleIds;

}
