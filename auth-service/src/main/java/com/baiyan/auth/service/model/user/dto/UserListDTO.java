package com.baiyan.auth.service.model.user.dto;

import com.baiyan.auth.service.model.user.bo.UserBO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author baiyan
 * @time 2020/11/17 13:44
 */
@Data
public class UserListDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("角色名称")
    private String roleNames;

    @ApiModelProperty("上次登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("用户状态：1可用，0禁用")
    private Integer state;

    @ApiModelProperty("能否编辑，1：能，0：否")
    private Integer canEdit;

    @ApiModelProperty("能否删除，1：能，0：否")
    private Integer canDelete;

    public UserListDTO(UserBO bo){
        BeanUtils.copyProperties(bo,this);
    }

}
