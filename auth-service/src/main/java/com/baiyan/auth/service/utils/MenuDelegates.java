package com.baiyan.auth.service.utils;


import com.baiyan.auth.api.enums.MenuTypeEnum;
import com.baiyan.auth.common.utils.tree.TreeFactory;
import com.baiyan.auth.common.utils.tree.TreeUtils;
import com.baiyan.auth.service.model.menu.dto.MenuDTO;
import com.baiyan.auth.service.model.menu.po.MenuPO;

import java.util.List;
import java.util.Objects;

/**
 * 菜单树工具类
 *
 * @author baiyan
 * @date 2020/11/23
 */
public class MenuDelegates {

    public static List<MenuDTO> buildTree(List<MenuPO> menus, MenuPO root) {
        return TreeUtils.buildTree(menus, root,new MenuDTOFactory());
    }

    public static class MenuDTOFactory implements TreeFactory<MenuPO, MenuDTO> {

        @Override
        public MenuDTO convert(MenuPO dataType) {
            return MenuDTO.builder()
                    .id(dataType.getId())
                    .name(dataType.getName())
                    .parentId(dataType.getParentId())
                    .type(dataType.getType())
                    .code(dataType.getCode())
                    .level(dataType.getLevel())
                    .path(dataType.getPath())
                    .sort(dataType.getSort())
                    .nonChildren(Objects.equals(dataType.getType(), MenuTypeEnum.BUTTON.getKey()))
                    .build();
        }

        @Override
        public MenuDTO buildDefaultRoot() {
            return MenuDTO.builder().id(0L).parentId(0L).build();
        }
    }

}
