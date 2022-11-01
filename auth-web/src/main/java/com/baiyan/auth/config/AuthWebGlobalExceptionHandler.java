package com.baiyan.auth.config;

import com.baiyan.common.base.result.Result;
import com.baiyan.common.interaction.annotation.Web;
import com.baiyan.common.interaction.handler.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局默认异常处理
 *
 * @author baiyan
 * @date 2020/11/13
 */
@Slf4j
@RestControllerAdvice(annotations = Web.class)
@ResponseStatus(HttpStatus.OK)
public class AuthWebGlobalExceptionHandler extends GlobalExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public Object handle(AccessDeniedException ex) {
        return Result.authError();
    }

}
