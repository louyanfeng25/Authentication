package com.baiyan.auth.web;

import com.baiyan.auth.config.VersionConfig;
import com.baiyan.auth.service.model.role.dto.RoleCommandDTO;
import com.baiyan.auth.service.model.role.dto.RoleDetailDTO;
import com.baiyan.auth.service.model.role.dto.RoleListDTO;
import com.baiyan.auth.service.service.RoleService;
import com.baiyan.common.base.model.query.KeywordQuery;
import com.baiyan.common.base.result.Page;
import com.baiyan.common.base.result.PageResult;
import com.baiyan.common.base.result.Result;
import com.baiyan.common.interaction.annotation.WebRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author baiyan
 * @time 2020/11/17
 */
@Api(tags = "角色管理web接口")
@WebRestController
@RequestMapping(VersionConfig.COMMON_WEB_VERSION_URL+"role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取角色列表")
    @GetMapping
    @PreAuthorize("hasAuthority('role')")
    public PageResult<RoleListDTO> page(KeywordQuery query) {
        Page<RoleListDTO> page = roleService.list(query);
        return PageResult.ok(page);
    }

    @ApiOperation(value = "角色新建")
    @PostMapping
    @PreAuthorize("hasAuthority('role_add')")
    public Result<Object> add(@RequestBody @Valid RoleCommandDTO dto) {
        roleService.add(dto);
        return Result.ok("新增成功");
    }

    @ApiOperation(value = "角色修改")
    @PutMapping
    @PreAuthorize("hasAuthority('role_update')")
    public Result<Object> update(@RequestBody @Valid RoleCommandDTO dto) {
        roleService.update(dto);
        return Result.ok("修改成功");
    }

    @ApiOperation(value = "角色删除")
    @DeleteMapping
    @PreAuthorize("hasAuthority('role_delete')")
    public Result<Object> delete(@RequestBody List<Long> ids) {
        roleService.delete(ids);
        return Result.ok("删除成功");
    }

    @ApiOperation(value = "角色详情")
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('role_detail')")
    public Result<RoleDetailDTO> detail(@PathVariable Long id) {
        return Result.success(roleService.detail(id));
    }

}
