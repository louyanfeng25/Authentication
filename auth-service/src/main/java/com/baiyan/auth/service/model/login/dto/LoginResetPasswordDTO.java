package com.baiyan.auth.service.model.login.dto;

import com.baiyan.auth.common.utils.HttpRequestUtil;
import com.baiyan.auth.service.model.login.vo.LoginResetPasswordVO;
import com.baiyan.auth.service.utils.PasswordUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 重置密码登录
 *
 * @author baiyan
 * @date 2020/11/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResetPasswordDTO extends LoginDTO{

    /**
     * 新密码
     */
    private String newPassword;

    public LoginResetPasswordDTO(LoginResetPasswordVO vo, HttpServletRequest request,String times){
        BeanUtils.copyProperties(vo,this);
        this.setIp(HttpRequestUtil.getIpAddr(request));
        this.setTimes(times);
        //返回用户解密前端后的密码
        String oldPwd = PasswordUtil.decode(vo.getPassword(),times);
        String newPwd = PasswordUtil.decode(vo.getNewPassword(),times);
        //校验密码长度
        PasswordUtil.checkPasswordLength(newPwd);
        this.setPassword(oldPwd);
        this.setNewPassword(newPwd);
    }

}
