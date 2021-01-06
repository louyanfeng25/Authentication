package com.baiyan.auth.service.model.user.dto;

import com.baiyan.auth.service.model.user.po.UserPO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author baiyan
 * @time 2020/11/17 13:44
 */
@Data
@NoArgsConstructor
public class UserDetailDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("角色Id")
    private List<Long> roleIds;

    @ApiModelProperty("上次登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("用户状态：1可用，0禁用")
    private Integer state;

    public UserDetailDTO(UserPO po){
        BeanUtils.copyProperties(po,this);
    }

}
