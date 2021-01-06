package com.baiyan.auth.service.utils;

import com.baiyan.auth.common.exception.ValidationException;
import com.baiyan.auth.common.utils.DateUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 鉴权中心AES工具类
 *
 * @author baiyan
 * @date 2020/11/22
 */
public class AuthAesUtil {

    private static final Logger log = LoggerFactory.getLogger(AuthAesUtil.class);

    /**
     * 请求头获取 Times 的key
     */
    public static final String HEADER_TIMES = "Times";

    /**
     * 解密
     *
     * @param str     字符串
     * @param request request
     * @return 解密后字符串
     */
    public static String decrypt(String str, HttpServletRequest request) {
        String headerTimes = request.getHeader(HEADER_TIMES);
        if (StringUtils.isEmpty(headerTimes)) {
            return "";
        }
        return AuthAesUtil.decrypt(str,headerTimes);
    }

    /**
     * 解密
     *
     * @param str   字符串
     * @param times request header Times
     * @return 解密后字符串
     */
    public static String decrypt(String str, String times) {
        try {
            String key = encodeTime(times);
            return new AesUtil(key).decryptData(str);
        } catch (ValidationException ve){
            throw ve;
        } catch (Exception e) {
            log.error("字符串:{}解密错误，错误信息为:{}",str,e.getMessage());
        }
        return "";
    }

    /**
     * AES加密key生成
     *
     * @param time 前端传递的时间格式: yyyy-MM-dd HH:mm:ss
     * @return AES加密key
     */
    public static String encodeTime(String time){
        //解析前端的时间格式
        LocalDateTime localDateTime = DateUtil.stringToLocalDateTime(time);
        //生成打乱的时间字符串
        String key = DateUtil.formatTimeString(localDateTime,DateUtil.YYYYMMDDHHMMSS);
        return DigestUtils.md5Hex(key);
    }

}
