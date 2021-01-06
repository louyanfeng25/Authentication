package com.baiyan.auth.service.model.access.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * accessKey用户新增实体
 *
 * @author baiyan
 * @time 2020/12/04
 */
@Data
public class AccessIndividualDeleteDTO {

    @ApiModelProperty("关联用户id")
    @NotNull(message = "{access.delete.access.key.id.is.null}")
    @Size(min=1,message = "{access.delete.access.key.id.is.null}")
    List<Long> accessKeyIds;

}
