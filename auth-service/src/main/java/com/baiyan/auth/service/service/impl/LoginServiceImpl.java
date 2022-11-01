package com.baiyan.auth.service.service.impl;

import com.baiyan.auth.api.enums.UserStateEnum;
import com.baiyan.auth.sdk.exception.LoginResetPasswordException;
import com.baiyan.auth.sdk.exception.LoginUserOnlineException;
import com.baiyan.auth.service.enums.YesNoEnum;
import com.baiyan.auth.service.mapper.UserAuthLogMapper;
import com.baiyan.auth.service.mapper.UserMapper;
import com.baiyan.auth.service.model.login.dto.LoginDTO;
import com.baiyan.auth.service.model.login.dto.LoginResetPasswordDTO;
import com.baiyan.auth.service.model.login.dto.LoginResultDTO;
import com.baiyan.auth.service.model.login.po.UserAuthLogPO;
import com.baiyan.auth.service.model.user.po.UserPO;
import com.baiyan.auth.service.service.LoginService;
import com.baiyan.auth.service.service.TokenStoreService;
import com.baiyan.auth.service.utils.PasswordUtil;
import com.baiyan.auth.service.utils.TokenUtil;
import com.baiyan.common.base.constant.RegexpConstant;
import com.baiyan.common.base.utils.StringUtil;
import com.baiyan.common.base.utils.ValidationUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author baiyan
 * @time 2020/11/18
 */
@Service
@Slf4j
public class LoginServiceImpl  implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenStoreService tokenStoreService;

    @Autowired
    private UserAuthLogMapper userAuthLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResultDTO login(LoginDTO loginDTO){
        UserPO userPO = userMapper.selectOne(Wrappers.<UserPO>lambdaQuery()
                .eq(UserPO::getUserName, loginDTO.getUsername()));
        //校验登录信息
        this.checkLoginMessage(loginDTO,userPO);
        //设置token信息
        return this.createResult(loginDTO,userPO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void loginOut(String token){
        tokenStoreService.delete(token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResultDTO login(LoginResetPasswordDTO loginDTO){
        ValidationUtil.isFalse(Objects.equals(loginDTO.getNewPassword(),loginDTO.getPassword()),
                "user.update.password.is.same");
        UserPO userPO = userMapper.selectOne(Wrappers.<UserPO>lambdaQuery()
                .eq(UserPO::getUserName, loginDTO.getUsername()));
        ValidationUtil.notNull(userPO,"login.username.is.not.exist");
        //校验密码准确性
        PasswordUtil.checkPassword(loginDTO.getPassword(),userPO.getPassword());
        //校验密码强度
        ValidationUtil.isTrue(loginDTO.getNewPassword().matches(RegexpConstant.USER_PWD),"user.update.password.format.error");
        userPO.updatePassword(loginDTO.getNewPassword());
        //设置token信息
        return this.createResult(loginDTO,userPO);
    }

    /**
     * 创建token
     *
     * @param loginDTO 登录信息
     * @param userPO 用户信息
     * @return
     */
    private LoginResultDTO createResult(LoginDTO loginDTO,UserPO userPO){
        //修改登录时间
        userPO.setLastLoginTime(LocalDateTime.now());
        userPO.setIp(loginDTO.getIp());
        userMapper.updateById(userPO);
        //记录登录时间
        userAuthLogMapper.insert(new UserAuthLogPO(loginDTO,userPO));
        //请求登录接口先删除token
        tokenStoreService.delete(userPO.getId());
        String token = TokenUtil.createToken();
        //存储token
        tokenStoreService.store(token,userPO.getId());
        LoginResultDTO loginResultDTO = new LoginResultDTO();
        loginResultDTO.setToken(TokenUtil.PREFIX+token);
        return loginResultDTO;
    }

    /**
     * 校验登录信息
     * @param loginDTO 登录信息
     * @param userPO 用户信息
     */
    private void checkLoginMessage(LoginDTO loginDTO,UserPO userPO){
        ValidationUtil.notNull(userPO,"login.username.is.not.exist");
        //校验用户是否禁用
        ValidationUtil.isTrue(Objects.equals(userPO.getState(), UserStateEnum.AVAILABLE.getKey()),"login.user.state.forbidden");
        //校验密码
        PasswordUtil.checkPassword(loginDTO.getPassword(),userPO.getPassword());
        //校验首次登录
        if(Objects.equals(userPO.getNeedResetPassword(), YesNoEnum.YES.getKey())){
            throw new LoginResetPasswordException("用户首次登录或者重置密码后需要修改密码！");
        }
        //校验是否已经登录
        if(StringUtil.isNotBlank(tokenStoreService.getToken(userPO.getId())) ){
            if(!Objects.equals(Boolean.TRUE,loginDTO.getForce()) && !Objects.equals(userPO.getIp(),loginDTO.getIp())){
                String loginIp = StringUtil.isNotBlank(userPO.getIp())? userPO.getIp() : Strings.EMPTY;
                throw new LoginUserOnlineException(String.format("用户在IP:%s已经登录！",loginIp));
            }else {
                //强制登录先删除已经登录的token
                tokenStoreService.delete(userPO.getId());
            }
        }
    }

}
