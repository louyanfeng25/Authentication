package com.baiyan.auth.web;

import com.baiyan.auth.common.result.Result;
import com.baiyan.auth.config.VersionConfig;
import com.baiyan.auth.sdk.util.ThreadLocalUtil;
import com.baiyan.auth.service.model.access.dto.*;
import com.baiyan.auth.service.service.AccessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author baiyan
 * @time 2020/12/09
 */
@Api(tags = "通行证管理web接口")
@RestController
@RequestMapping(VersionConfig.COMMON_WEB_VERSION_URL+"access")
public class AccessController {

    @Autowired
    private AccessService accessService;

    @ApiOperation(value = "个人查询通行证")
    @GetMapping("individual")
    public Result<List<AccessDTO>> query(String keyword) {
        List<AccessDTO> dto = accessService.queryAccess(ThreadLocalUtil.getUser().getId(), keyword);
        return Result.success(dto);
    }

    @ApiOperation(value = "个人增加通行证")
    @PostMapping("individual")
    public Result<String> add(@RequestBody @Valid AccessIndividualAddDTO dto) {
        AccessAddDTO addDTO = new AccessAddDTO();
        addDTO.setRemark(dto.getRemark());
        addDTO.setUserId(ThreadLocalUtil.getUser().getId());
        String accessKey = accessService.addAccess(addDTO);
        return Result.success(accessKey,"新增成功");
    }

    @ApiOperation(value = "个人销毁通行证")
    @DeleteMapping("/individual")
    public Result<Object> delete(@RequestBody @Valid AccessIndividualDeleteDTO dto) {
        accessService.deleteAccess(ThreadLocalUtil.getUser().getId(), dto.getAccessKeyIds());
        return Result.ok("销毁成功");
    }

    @ApiOperation(value = "管理员查询通行证")
    @GetMapping("/manage")
    @PreAuthorize("hasAuthority('user_access')")
    public Result<List<AccessDTO>> query(@RequestParam Long userId, String keyword) {
        List<AccessDTO> dto = accessService.queryAccess(userId, keyword);
        return Result.success(dto);
    }

    @ApiOperation(value = "管理员增加通行证")
    @PostMapping("/manage")
    @PreAuthorize("hasAuthority('user_access_add')")
    public Result<String> add(@RequestBody @Valid AccessAddDTO dto) {
        String accessKey = accessService.addAccess(dto);
        return Result.success(accessKey,"新增成功");
    }

    @ApiOperation(value = "管理员销毁通行证")
    @DeleteMapping("/manage")
    @PreAuthorize("hasAuthority('user_access_delete')")
    public Result<Object> delete(@RequestBody @Valid AccessDeleteDTO dto) {
        accessService.deleteAccess(dto.getUserId(), dto.getAccessKeyIds());
        return Result.ok("销毁成功");
    }

}
