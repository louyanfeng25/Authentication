package com.baiyan.auth.service.model.user.bo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体
 * @author baiyan
 * @date 2020/11/13
 */
@Data
public class UserBO {

    /** 用户id */
    private Long id;

    /** 用户名 */
    private String userName;

    /** 真实姓名 */
    private String realName;

    /** 角色名称 */
    private String roleNames;

    /** 上次登录时间 */
    private LocalDateTime lastLoginTime;

    /** 用户状态：1可用，0禁用 */
    private Integer state;

    /** 能否编辑，1：能，0：否 */
    private Integer canEdit;

    /** 能否删除，1：能，0：否 */
    private Integer canDelete;

}
