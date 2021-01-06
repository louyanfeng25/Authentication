package com.baiyan.auth.service.model.access.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * accessKey新增实体
 *
 * @author baiyan
 * @time 2020/12/04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccessAddDTO extends AccessIndividualAddDTO{

    @ApiModelProperty("关联用户id")
    @NotNull(message = "{access.add.user.id.is.null}")
    private Long userId;

}
