/**初始化SOURCE表**/
DROP TABLE IF EXISTS `source`;
CREATE TABLE `source` (
  `id` varchar(255) NOT NULL COMMENT '序列',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `description` varchar(255) DEFAULT NULL COMMENT '说明',
  `create_date_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_name` varchar(255) DEFAULT NULL COMMENT '创建人',
  `modify_date_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_name` varchar(255) DEFAULT NULL COMMENT '修改人',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '删除状态',
  `type` int(20) DEFAULT '0' COMMENT '类型（0：默认）',
  `state` int(20) DEFAULT '0' COMMENT '状态（0：默认）',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `sorting` int(20) DEFAULT '0' COMMENT '排序',
  `version` int(20) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='来源';
insert into `source` (`id`,`name`,`code`,`create_date_time`,`create_name`) values ('1','APP','APP',now(),'孙馨普通后台用户-138901042');
insert into `source` (`id`,`name`,`code`,`create_date_time`,`create_name`) values ('2','小程序','APPLET',now(),'孙馨普通后台用户-138901042');
insert into `source` (`id`,`name`,`code`,`create_date_time`,`create_name`) values ('3','工保通','GONG_BAO_TON',now(),'孙馨普通后台用户-138901042');
insert into `source` (`id`,`name`,`code`,`create_date_time`,`create_name`) values ('4','工保网','GONG_BAO_NET',now(),'孙馨普通后台用户-138901042');
insert into `source` (`id`,`name`,`code`,`create_date_time`,`create_name`) values ('5','SCRM','SCRM',now(),'孙馨普通后台用户-138901042');
insert into `source` (`id`,`name`,`code`,`create_date_time`,`create_name`) values ('6','工保金','GONG_BAO_JIN',now(),'孙馨普通后台用户-138901042');
insert into `source` (`id`,`name`,`code`,`create_date_time`,`create_name`) values ('7','一体化','GONG_BAO_UNIFY',now(),'孙馨普通后台用户-138901042');

/**初始化SOURCE_VALUE表**/
DROP TABLE IF EXISTS `source_value`;
CREATE TABLE `source_value` (
  `id` varchar(255) NOT NULL COMMENT '序列',
  `source_id` varchar(255) DEFAULT NULL COMMENT '来源键id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `description` varchar(255) DEFAULT NULL COMMENT '说明',
  `create_date_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_name` varchar(255) DEFAULT NULL COMMENT '创建人',
  `modify_date_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_name` varchar(255) DEFAULT NULL COMMENT '修改人',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '删除状态',
  `type` int(20) DEFAULT '0' COMMENT '类型（0：默认）',
  `state` int(20) DEFAULT '0' COMMENT '状态（0：默认）',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `sorting` int(20) DEFAULT '0' COMMENT '排序',
  `version` int(20) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='来源值';
insert into `source_value` (`id`,`source_id`,`name`,`code`,`create_date_time`,`create_name`) values ('1','4','工保网前台','GBW_FRONT',now(),'孙馨普通后台用户-138901042');
insert into `source_value` (`id`,`source_id`,`name`,`code`,`create_date_time`,`create_name`) values ('2','4','工保网后台','GBW_BAC',now(),'孙馨普通后台用户-138901042');
insert into `source_value` (`id`,`source_id`,`name`,`code`,`create_date_time`,`create_name`) values ('3','5','工保网SCRM后台','CRM_BAC',now(),'孙馨普通后台用户-138901042');
insert into `source_value` (`id`,`source_id`,`name`,`code`,`create_date_time`,`create_name`) values ('4','3','工保通后台','GBT_BAC',now(),'孙馨普通后台用户-138901042');

/**初始化system表**/
DROP TABLE IF EXISTS `system`;
CREATE TABLE `system` (
  `id` varchar(255) NOT NULL COMMENT '序列',
  `url` varchar(255) DEFAULT NULL COMMENT '系统url地址',
  `name` varchar(255) DEFAULT NULL COMMENT '系统名字',
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `description` varchar(255) DEFAULT NULL COMMENT '说明',
  `create_date_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_name` varchar(255) DEFAULT NULL COMMENT '创建人',
  `modify_date_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_name` varchar(255) DEFAULT NULL COMMENT '修改人',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '删除状态',
  `type` int(20) DEFAULT '0' COMMENT '类型（0：默认）',
  `state` int(20) DEFAULT '0' COMMENT '状态（0：默认）',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `sorting` int(20) DEFAULT '0' COMMENT '排序',
  `version` int(20) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统表';
insert into `system` (`id`,`url`,`name`,`code`,`create_date_time`,`create_name`) values ('1','https://www.gongbao.cn/login','工保网前台普通用户','net-user',now(),'孙馨普通后台用户-138901042');
insert into `system` (`id`,`url`,`name`,`code`,`create_date_time`,`create_name`) values ('2','https://www.gongbao.cn/login','工保网前台经纪人','net-agent',now(),'孙馨普通后台用户-138901042');
insert into `system` (`id`,`url`,`name`,`code`,`create_date_time`,`create_name`) values ('3','https://www.gongbao.cn/login','工保网前台保险公司','net-ins',now(),'孙馨普通后台用户-138901042');
insert into `system` (`id`,`url`,`name`,`code`,`create_date_time`,`create_name`) values ('4','http://gbwbackstage.gongbao.cn/','工保网后台管理端','net-backend',now(),'孙馨普通后台用户-138901042');
insert into `system` (`id`,`url`,`name`,`code`,`create_date_time`,`create_name`) values ('5','http://crm.gongbao.cn/','工保网SCRM','CRM',now(),'孙馨普通后台用户-138901042');
