CREATE TABLE `t_access` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '关联用户id',
  `access_key` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '通行证key',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `creator` bigint(20) NOT NULL COMMENT '创建者用户id',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint(20) NOT NULL DEFAULT '0' COMMENT '是否删除，非0为已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_access` (`access_key`) USING BTREE COMMENT '通行证索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='三方授权信息表';

CREATE TABLE `t_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单id',
  `module` int(11) DEFAULT NULL COMMENT '模块标识',
  `name` varchar(256) DEFAULT NULL COMMENT '菜单名称',
  `code` varchar(64) DEFAULT NULL COMMENT '菜单编码',
  `path` varchar(9192) DEFAULT NULL COMMENT '菜单层级',
  `level` int(11) DEFAULT NULL COMMENT '菜单等级',
  `type` varchar(64) DEFAULT NULL COMMENT '菜单类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `disable` int(11) DEFAULT '0' COMMENT '是否显示  1不显示  0显示',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint(20) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单表';

CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) NOT NULL COMMENT '名称',
  `code` varchar(64) DEFAULT NULL COMMENT '角色code',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  `can_edit` tinyint(4) DEFAULT '1' COMMENT '是否可编辑',
  `can_delete` tinyint(4) DEFAULT '1' COMMENT '是否可删除',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint(20) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

CREATE TABLE `t_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint(20) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `union_idx` (`role_id`,`menu_id`,`deleted`) USING BTREE COMMENT '角色与菜单联合索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色菜单关联表';

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `real_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '用户状态：1可用，0禁用',
  `ip` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '最近一次登录ip',
  `can_edit` tinyint(4) DEFAULT '1' COMMENT '能否编辑1是 0不是',
  `can_delete` tinyint(4) DEFAULT '1' COMMENT '能否删除1是 0不是',
  `need_reset_password` tinyint(4) DEFAULT '1' COMMENT '是否需要重置密码1是 0不是',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint(20) NOT NULL DEFAULT '0' COMMENT '是否删除，非0为已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `union_idx` (`user_name`,`real_name`) USING BTREE COMMENT '用户名与真实姓名联合索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户表';

CREATE TABLE `t_user_auth_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `auth_type` varchar(64) NOT NULL COMMENT '认证方式',
  `ip` varchar(256) DEFAULT NULL COMMENT '登录ip地址',
  `user_name` varchar(256) NOT NULL COMMENT '登录账号',
  `real_name` varchar(256) NOT NULL COMMENT '登录展示名称',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint(20) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='登录日志';

CREATE TABLE `t_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint(20) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `union_idx` (`user_id`,`role_id`) USING BTREE COMMENT '用户与角色联合索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';
