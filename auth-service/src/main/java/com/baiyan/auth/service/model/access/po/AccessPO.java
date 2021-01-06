package com.baiyan.auth.service.model.access.po;

import com.baiyan.auth.common.model.po.BaseUuidEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 授权实体
 *
 * @author baiyan
 * @time 2020/12/04
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_access")
public class AccessPO extends BaseUuidEntity {

    /** 关联用户id */
    private Long userId;

    /** 通行证 */
    private String accessKey;

    /** 备注信息 */
    private String remark;

    /** 创建者用户id */
    private Long creator;

}
