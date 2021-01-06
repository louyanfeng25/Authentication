package com.baiyan.auth.service.model.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 用户修改
 *
 * @author baiyan
 * @time 2020/11/17 16:23
 */
@Data
public class UserUpdateDTO {

    @ApiModelProperty("用户id")
    @NotNull(message = "{user.update.id.is.null}")
    private Long id;

    @ApiModelProperty("显示名称")
    @NotBlank(message = "{user.update.realName.is.blank}")
    @Length(max = 30,message = "{user.update.realName.is.too.long}")
    private String realName;

    @ApiModelProperty("角色id列表")
    @NotNull(message = "{user.update.roleIds.is.blank}")
    @Size(min=1,message = "{user.update.roleIds.is.blank}")
    private List<Long> roleIds;

}
