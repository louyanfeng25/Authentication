package com.baiyan.auth.service.model.access.dto;

import com.baiyan.auth.service.model.access.po.AccessPO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * access列表数据
 *
 * @author baiyan
 * @time 2020/12/9 13:55
 */
@Data
@NoArgsConstructor
public class AccessDTO {

    @ApiModelProperty("通行证id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty("通行证")
    private String accessKey;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    public AccessDTO(AccessPO po){
        BeanUtils.copyProperties(po,this);
    }

}
