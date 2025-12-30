/**初始化ROLE表**/
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(255) NOT NULL COMMENT '序列',
  `system_id` varchar(255) DEFAULT NULL COMMENT '系统id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `value` varchar(255) DEFAULT NULL COMMENT '角色值',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('90', '4', '工保网后台管理端业务助理', 'gbwBackendBussAssisantRole', null, now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('89', '4', '工保网后台管理端渠道	', 'gbwBackendCanalRole', null, now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('88', '4', '工保网后台管理端业务支持', 'gbwBackendBussSupportRole', null, now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('84', '4', '工保网后台管理端业务管理员', 'gbwBackendBussAdminRole', null, now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('55', '4', '工保网管理端角色2', 'gbwBackendRole2', null, now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('51', '4', '工保网后台超管', 'gbwBackendRole', '默认角色', now(), '孙馨普通后台用户-138901042');


INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('33', '2', '工保网经纪人角色', 'gbwBrokerRole', '默认角色', now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('52', '2', '正式经纪人', 'officialBroker', '正式经纪人角色', now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('62', '2', '非正式经纪人', 'informalBroker', '非正式经纪人角色', now(), '孙馨普通后台用户-138901042');

INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('54', '3', '工保网保险公司角色', 'gbwInsurerRole', '默认角色', now(), '孙馨普通后台用户-138901042');

INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('53', '1', '工保网普通用户角色', 'gbwOrdinaryRole', '默认角色', now(), '孙馨普通后台用户-138901042');

INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('56', '5', '工保网SCRM角色', 'scrmRole', '工保网SCRM默认角色', now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('79', '5', '业务管家', 'crmBulterRole', 'crm业务管家', now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('78', '5', '业务助理', 'crmBussAssisantRole', 'crm服务管家', now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('77', '5', '服务管家主管', 'crmServiceBulterMainRole', 'crm服务管家主管', now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('76', '5', '互联网管家', 'crmInternetBulterRole', 'crm互联网管家', now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('75', '5', '互联网管家主管', 'crmInternetBulterMainRole', 'crm互联网管家主管', now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('74', '5', '商务管家', 'crmBussBulterRole', 'crm商务管家', now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('73', '5', '商务管家主管', 'crmBussBulterMainRole', 'crm商务管家主管', now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('72', '5', '管理员', 'crmAdminRole', 'crm管理员角色', now(), '孙馨普通后台用户-138901042');
INSERT INTO `role` (`id`, `system_id`, `name`, `value`, `description`, `create_date_time`, `create_name`) VALUES ('71', '5', '业务管家主管', 'crmBulterMainRole', '业务管家主管角色', now(), '孙馨普通后台用户-138901042');
