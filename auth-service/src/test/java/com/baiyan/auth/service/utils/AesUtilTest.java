package com.baiyan.auth.service.utils;

import com.baiyan.common.base.utils.DateUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class AesUtilTest {

    @Test
    @SneakyThrows
    public void getEncodePassword(){
        String adminDefaultPassword = "2wsxVFR_";
        String encodeTime = DateUtil.formatTimeString(LocalDateTime.now());
        String key = AuthAesUtil.encodeTime(DateUtil.formatTimeString(LocalDateTime.now()));
        String encode = new AesUtil(key).encryptData(adminDefaultPassword);
        System.out.println("用户输入框中输入的密码:"+adminDefaultPassword);
        System.out.println("登陆请求头时间:"+encodeTime);
        System.out.println("前后端AES堆成加密的key::"+key);
        System.out.println("数据库中存储的key："+PasswordUtil.encodePassword("2wsxVFR_"));
        System.out.println("前端请求传递的key："+encode);
    }

}