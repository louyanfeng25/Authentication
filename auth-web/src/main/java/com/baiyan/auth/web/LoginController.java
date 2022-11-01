package com.baiyan.auth.web;

import com.baiyan.auth.config.VersionConfig;
import com.baiyan.auth.sdk.exception.LoginResetPasswordException;
import com.baiyan.auth.sdk.exception.LoginUserOnlineException;
import com.baiyan.auth.sdk.extractor.token.BearerTokenAuthExtractorProvider;
import com.baiyan.auth.service.model.login.dto.LoginDTO;
import com.baiyan.auth.service.model.login.dto.LoginResetPasswordDTO;
import com.baiyan.auth.service.model.login.dto.LoginResultDTO;
import com.baiyan.auth.service.model.login.vo.LoginResetPasswordVO;
import com.baiyan.auth.service.model.login.vo.LoginVO;
import com.baiyan.auth.service.service.LoginService;
import com.baiyan.auth.service.utils.AuthAesUtil;
import com.baiyan.common.base.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author baiyan
 * @time 2020/11/13 11:27
 */
@Api(tags = "登录管理web接口")
@RestController
@RequestMapping(VersionConfig.COMMON_WEB_VERSION_URL+"login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "登录",response = LoginResultDTO.class)
    @PostMapping("/auth/tokens")
    public Result login(@RequestBody @Valid LoginVO vo,
                        @RequestHeader(AuthAesUtil.HEADER_TIMES) String times,
                        HttpServletRequest request) {
        try{
            LoginResultDTO login = loginService.login(new LoginDTO(vo, request,times));
            return Result.success(login,"登录成功");
        }catch (LoginUserOnlineException e){
            return Result.error(20001,null,e.getMessage(),null);
        }catch (LoginResetPasswordException  e){
            return Result.error(20002,null,e.getMessage(),null);
        }
    }

    @ApiOperation(value = "登出")
    @DeleteMapping("/auth/tokens")
    public Result<Object> logout(HttpServletRequest request) {
        String token = new BearerTokenAuthExtractorProvider().extract(request);
        loginService.loginOut(token);
        return Result.ok("用户登出成功");
    }

    @ApiOperation(value = "修改密码登录")
    @PostMapping("/auth/resetPassword")
    public Result<LoginResultDTO> loginResetPassword(@RequestBody @Valid LoginResetPasswordVO vo,
                                                     @RequestHeader(AuthAesUtil.HEADER_TIMES) String times,
                                                     HttpServletRequest request) {
        LoginResultDTO login = loginService.login(new LoginResetPasswordDTO(vo, request,times));
        return Result.success(login,"密码修改成功");
    }

}
