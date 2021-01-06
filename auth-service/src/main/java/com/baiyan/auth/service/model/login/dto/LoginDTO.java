package com.baiyan.auth.service.model.login.dto;

import com.baiyan.auth.common.utils.HttpRequestUtil;
import com.baiyan.auth.service.model.login.vo.LoginVO;
import com.baiyan.auth.service.utils.PasswordUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录
 *
 * @author baiyan
 * @date 2020/11/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否强制登录
     */
    private Boolean force;

    /**
     * 客户端登录ip
     */
    private String ip;

    /**
     * 密码解析时间格式: yyyy-MM-dd HH:mm:ss
     */
    private String times;

    public LoginDTO(LoginVO vo, HttpServletRequest request,String times){
        BeanUtils.copyProperties(vo,this);
        this.ip = HttpRequestUtil.getIpAddr(request);
        this.times = times;
        //返回用户解密前端后的密码
        this.password = PasswordUtil.decode(vo.getPassword(),times);
    }

}
