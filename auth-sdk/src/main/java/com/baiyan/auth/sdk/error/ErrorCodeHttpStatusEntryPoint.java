package com.baiyan.auth.sdk.error;

import com.baiyan.auth.common.result.Result;
import com.baiyan.auth.common.utils.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 授权认证错误
 *
 * @author baiyan
 * @time 2020/11/14
 */
public class ErrorCodeHttpStatusEntryPoint implements AuthenticationEntryPoint {

	private static final Logger log = LoggerFactory.getLogger(ErrorCodeHttpStatusEntryPoint.class);

	/**
	 * 业务自定义错误code
	 */
	private String errorCode;

	/**
	 * 业务自定义错误信息
	 */
	private String message;

	/**
	 * http错误码
	 */
	private HttpStatus httpStatus;

	public ErrorCodeHttpStatusEntryPoint(String errorCode, HttpStatus httpStatus,String message) {
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
		this.message = message;
	}


	/**
	 * 认证失败执行逻辑
	 *
	 * @param request
	 * @param response
	 * @param e
	 * @throws IOException
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException {
		log.error("鉴权失败,错误信息:",e);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		//请求成功，由业务判断失败的code
		response.setStatus(HttpServletResponse.SC_OK);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.getWriter().print(GsonUtil.gsonToString(Result.authError(httpStatus.value(),errorCode,message)));
	}

}