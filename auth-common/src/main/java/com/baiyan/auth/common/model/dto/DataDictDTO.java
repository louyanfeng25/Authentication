package com.baiyan.auth.common.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author baiyan
 * @time 2020/11/17 20:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataDictDTO {

    @ApiModelProperty("key值")
    private String key;

    @ApiModelProperty("value值")
    private String value;

}
