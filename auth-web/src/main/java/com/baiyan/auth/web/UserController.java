package com.baiyan.auth.web;

import com.baiyan.auth.common.model.query.KeywordQuery;
import com.baiyan.auth.common.result.Page;
import com.baiyan.auth.common.result.PageResult;
import com.baiyan.auth.common.result.Result;
import com.baiyan.auth.config.VersionConfig;
import com.baiyan.auth.service.model.user.dto.*;
import com.baiyan.auth.service.service.UserService;
import com.baiyan.auth.service.utils.AuthAesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author baiyan
 * @time 2020/11/13 11:27
 */
@Api(tags = "用户管理web接口")
@RestController
@RequestMapping(VersionConfig.COMMON_WEB_VERSION_URL+"user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户列表")
    @GetMapping
    @PreAuthorize("hasAuthority('user')")
    public PageResult<UserListDTO> list(KeywordQuery query) {
        Page<UserListDTO> page = userService.list(query);
        return PageResult.ok(page);
    }

    @ApiOperation(value = "用户新建")
    @PostMapping
    @PreAuthorize("hasAuthority('user_add')")
    public Result<String> add(@RequestBody @Valid UserAddDTO dto) {
        String password = userService.add(dto);
        return Result.success(password,"用户新增成功");
    }

    @ApiOperation(value = "用户修改")
    @PutMapping
    @PreAuthorize("hasAuthority('user_update')")
    public Result<Object> update(@RequestBody @Valid UserUpdateDTO dto) {
        userService.update(dto);
        return Result.ok("用户信息修改成功");
    }

    @ApiOperation(value = "用户本人修改显示名称")
    @PutMapping("individual")
    public Result<Object> update(@RequestParam String realName) {
        userService.update(realName);
        return Result.ok("用户信息修改成功");
    }

    @ApiOperation(value = "用户密码修改")
    @PutMapping("password")
    public Result<Object> updatePassword(@RequestBody @Valid UserUpdatePasswordDTO dto,
                                         @RequestHeader(AuthAesUtil.HEADER_TIMES) String times) {
        userService.updatePassword(dto,times);
        return Result.ok("密码修改成功,请重新登录");
    }

    @ApiOperation(value = "用户状态修改")
    @PutMapping("/state/{id}")
    @PreAuthorize("hasAuthority('user_state_update')")
    public Result<Object> updateState(@PathVariable Long id,@RequestParam Integer state) {
        userService.updateState(id,state);
        return Result.ok("状态修改成功");
    }

    @ApiOperation(value = "重置用户密码")
    @PutMapping("/resetPassword/{id}")
    @PreAuthorize("hasAuthority('user_reset_password')")
    public Result<String> resetPassword(@PathVariable Long id) {
        String password = userService.resetPassword(id);
        return Result.success(password,"重置用户密码成功");
    }

    @ApiOperation(value = "用户删除")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('user_delete')")
    public Result<Object> delete(@PathVariable Long id) {
        userService.delete(id);
        return Result.ok("删除成功");
    }

    @ApiOperation(value = "用户详情")
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('user_detail')")
    public Result<UserDetailDTO> detail(@PathVariable Long id) {
        return Result.success(userService.detail(id));
    }

    @ApiOperation(value = "获取用户权限信息")
    @GetMapping("/auth")
    public Result<UserAuthDTO> authInfo(HttpServletRequest request) {
        UserAuthDTO auth = userService.getAuth(request);
        return Result.success(auth);
    }

}
