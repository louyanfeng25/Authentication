package com.baiyan.auth.service.model.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户修改
 *
 * @author baiyan
 * @time 2020/11/17 16:23
 */
@Data
public class UserUpdatePasswordDTO {

    @ApiModelProperty("旧密码")
    @NotBlank(message = "{user.update.password.old.is.blank}")
    private String oldPassword;

    @ApiModelProperty("新密码")
    @NotBlank(message = "{user.update.password.new.is.blank}")
    private String newPassword;

}
