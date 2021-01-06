package com.baiyan.auth.service.model.login.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录结果
 *
 * @author baiyan
 * @date 2020/11/18
 */
@Data
public class LoginResultDTO {

    @ApiModelProperty("token")
    private String token;

}
