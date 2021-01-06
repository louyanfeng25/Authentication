package com.baiyan.auth.service.model.access.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * accessKey用户新增实体
 *
 * @author baiyan
 * @time 2020/12/04
 */
@Data
public class AccessIndividualAddDTO {

    @ApiModelProperty("备注信息")
    @Length(max = 200,message = "{access.add.remark.is.too.lang}")
    private String remark;

}
