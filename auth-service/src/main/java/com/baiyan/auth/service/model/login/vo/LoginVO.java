package com.baiyan.auth.service.model.login.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录vo
 *
 * @author baiyan
 * @date 2020/11/18
 */
@Data
public class LoginVO {

    @ApiModelProperty("用户名")
    @NotBlank(message = "{login.username.is.blank}")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "{login.password.is.blank}")
    private String password;

    @ApiModelProperty("是否强制登录")
    private Boolean force;

}
