package com.baiyan.auth.service.model.login.po;

import com.baiyan.auth.service.enums.AuthLoginTypeEnum;
import com.baiyan.auth.service.model.login.dto.LoginDTO;
import com.baiyan.auth.service.model.user.po.UserPO;
import com.baiyan.common.base.model.po.BaseUuidEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 登录日志
 *
 * @author baiyan
 * @time 2020/11/18 15:48
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_auth_log")
public class UserAuthLogPO extends BaseUuidEntity {

    /**
     * 认证方式
     */
    private String authType;

    /**
     * 登录ip
     */
    private String ip;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 展示名称
     */
    private String realName;

    /**
     * 用户id
     */
    private Long userId;

    public UserAuthLogPO(LoginDTO dto, UserPO po){
        this.authType = AuthLoginTypeEnum.FORM.getKey();
        this.ip = dto.getIp();
        this.userName = po.getUserName();
        this.realName = po.getRealName();
        this.userId = po.getId();
    }
}
