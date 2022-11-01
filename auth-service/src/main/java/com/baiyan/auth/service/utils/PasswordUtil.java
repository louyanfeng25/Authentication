package com.baiyan.auth.service.utils;

import com.baiyan.common.base.utils.ValidationUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

/**
 * 密码生成工具
 *
 * @author baiyan
 * @time 2020/11/18 10:03
 */
public class PasswordUtil {

    /**
     * 默认密码[10位长度随机字符串]
     *
     * @return
     */
    public static String defaultPassword(){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<10;i++){
            int number=random.nextInt(70);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 默认加密密码
     *
     * @return 数据库存储密码
     */
    public static String encodePassword(String password){
        return DigestUtils.sha1Hex(password);
    }

    /**
     * 密码解密
     *
     * @param password 前端传递的密码
     * @param times aes时间参数用于加解密
     * @return 解密后的密码
     */
    public static String decode(String password, String times) {
        return AuthAesUtil.decrypt(password, times);
    }

    /**
     * 校验密码
     * @param frontPassword 用户的原始密码
     * @param dbPassword 数据库中存储的密码
     */
    public static void checkPassword(String frontPassword,String dbPassword){
        String password = PasswordUtil.encodePassword(frontPassword) ;
        ValidationUtil.isTrue(Objects.equals(password,dbPassword),"login.password.error");
    }

    /**
     * 校验密码长度
     *
     * @param password 密码数组
     */
    public static void checkPasswordLength(String... password){
        if(Objects.isNull(password)){
            return;
        }
        Stream.of(password).forEach(pwd-> ValidationUtil.isTrue(pwd.length()>=8 && pwd.length()<=16,
                "login.password.is.too.lang"));
    }


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(defaultPassword());
        }
    }

}
