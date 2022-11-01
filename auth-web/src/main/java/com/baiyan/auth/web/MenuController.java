package com.baiyan.auth.web;

import com.baiyan.auth.config.VersionConfig;
import com.baiyan.auth.service.model.menu.dto.MenuDTO;
import com.baiyan.auth.service.service.MenuService;
import com.baiyan.common.base.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author baiyan
 * @time 2020/11/23
 */
@Api(tags = "资源管理web接口")
@RestController
@RequestMapping(VersionConfig.COMMON_WEB_VERSION_URL+"menu")
public class MenuController {

    @Autowired
    private MenuService  menuService;

    @ApiOperation(value = "获取整体菜单树")
    @GetMapping
    public Result<List<MenuDTO>> tree() {
        List<MenuDTO> tree = menuService.tree(null);
        return Result.success(tree);
    }
}
