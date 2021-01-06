package com.baiyan.auth.service.model.access.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * accessKey删除实体
 *
 * @author baiyan
 * @time 2020/12/09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccessDeleteDTO extends AccessIndividualDeleteDTO{

    @ApiModelProperty("关联用户id")
    @NotNull(message = "{access.delete.user.id.is.null}")
    private Long userId;

}
