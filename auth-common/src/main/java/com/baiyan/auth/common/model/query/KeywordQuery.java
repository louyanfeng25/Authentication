package com.baiyan.auth.common.model.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 关键字查询条件
 *
 * @author baiyan
 * @date 2020/11/17
 */
@Data
public class KeywordQuery extends PageQuery {

    @ApiModelProperty("关键字查询")
    private String keyword;
}
