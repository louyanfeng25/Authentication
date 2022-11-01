package com.baiyan.auth.service.model.user.po;

import com.baiyan.auth.api.enums.UserStateEnum;
import com.baiyan.auth.service.enums.YesNoEnum;
import com.baiyan.auth.service.model.user.dto.UserAddDTO;
import com.baiyan.auth.service.model.user.dto.UserUpdateDTO;
import com.baiyan.auth.service.utils.PasswordUtil;
import com.baiyan.common.base.model.po.BaseUuidEntity;
import com.baiyan.common.base.utils.ValidationUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * 用户实体
 * @author baiyan
 * @date 2020/11/13
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
public class UserPO extends BaseUuidEntity {

    /** 用户名 */
    private String userName;

    /** 真是姓名 */
    private String realName;

    /** 密码 */
    private String password;

    /** 密码 */
    private String ip;

    /**
     * 状态
     * @see UserStateEnum
     * */
    private Integer state;

    /** 上次登录时间 */
    private LocalDateTime lastLoginTime;

    /** 能否编辑 */
    private Integer canEdit;

    /** 能否删除 */
    private Integer canDelete;

    /** 是否需要重置密码 */
    private Integer needResetPassword;

    public UserPO(UserAddDTO dto){
        BeanUtils.copyProperties(dto,this);
    }

    public UserPO(UserUpdateDTO dto){
        ValidationUtil.notNull(dto,"user.update.dto.is.null");
        BeanUtils.copyProperties(dto,this);
    }

    /**
     * 修改密码
     * @param newPassword 用户真实密码
     */
    public void updatePassword(String newPassword){
        this.setNeedResetPassword(YesNoEnum.NO.getKey());
        this.setPassword(PasswordUtil.encodePassword(newPassword));
    }

    /**
     * 重置密码
     *
     * @return 新密码
     */
    public String resetPassword(){
        this.setNeedResetPassword(YesNoEnum.YES.getKey());
        String newPassword = PasswordUtil.defaultPassword();
        this.setPassword(PasswordUtil.encodePassword(newPassword));
        return newPassword;
    }

}
