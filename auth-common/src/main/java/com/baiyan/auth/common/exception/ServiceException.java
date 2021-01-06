package com.baiyan.auth.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author baiyan
 * @time 2020/11/13 13:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 430933593095358673L;

    private String errorMessage;

    private String errorCode;

    /**
     * 用给定的异常信息构造新实例。
     * @param errorMessage 异常信息。
     */
    public ServiceException(String errorMessage) {
        super((String)null);
        this.errorMessage = errorMessage;
    }

    /**
     * 返回异常信息。
     * @return 异常信息。
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 返回错误代码的字符串表示。
     * @return 错误代码的字符串表示。
     */
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return getErrorMessage();
    }

    public ServiceException(String code, String message) {
        super(message);
        this.errorCode = code;
    }

}
