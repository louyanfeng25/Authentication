package com.baiyan.auth.service.model.menu.po;

import com.baiyan.auth.common.model.po.BaseUuidEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 菜单实体
 *
 * @author baiyan
 * @time 2020/11/14 14:29
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_menu")
public class MenuPO extends BaseUuidEntity {

    /** 父级菜单id */
    private Long parentId;

    /** 模块 */
    private Integer module;

    /** 菜单名称 */
    private String name;

    /** 菜单code */
    private String code;

    /** 跳转路径 */
    private String path;

    /** 菜单级别 */
    private Integer level;

    /** 菜单类型 */
    private String type;

    /** 菜单排序 */
    private Integer sort;

    /** 是否可见 */
    private Integer disable;

}
