/**初始化USER_TYPE表**/
DROP TABLE IF EXISTS `user_type`;
CREATE TABLE `user_type` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户类型表';
insert into `user_type` (`id`,`name`,`code`,`create_date_time`,`create_name`) values ('1','经纪人','1',now(),'孙馨普通后台用户-138901042');
insert into `user_type` (`id`,`name`,`code`,`create_date_time`,`create_name`) values ('2','用户','2',now(),'孙馨普通后台用户-138901042');
insert into `user_type` (`id`,`name`,`code`,`create_date_time`,`create_name`) values ('3','保险公司','3',now(),'孙馨普通后台用户-138901042');


/**初始化user_type_value表**/
DROP TABLE IF EXISTS `user_type_value`;
CREATE TABLE `user_type_value` (
  `id` varchar(255) NOT NULL COMMENT '序列',
  `user_type_id` varchar(255) DEFAULT NULL COMMENT '用户类型id',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户类型值表';
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('1', '1', '经纪人', '213', '默认', now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('10', '1', '服务管家主管', '231', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('11', '1', '互联网管家主管', '229', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('12', '1', '商务管家主管', '230', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('13', '1', '特别业务管家', '234', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('14', '1', '特殊经纪人', '237', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('1463807938694615041', '1', '渠道经纪人', '238', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('2', '1', '业务管家', '214', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('20', '2', '前端用户', 'average_user', '默认', now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('21', '2', '管理用户', 'management_user', '默认', now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('3', '1', '业务助理', '215', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('30', '3', '保险公司', 'insurance_company', '默认', now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('31', '1', '用户咨询', '240', '用户线索分配的管家', now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('32', '1', '经纪人咨询', '241', '经纪人线索分配的管家', now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('33', '1', '管理员', '235', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('4', '1', '互联网管家', '216', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('8', '1', '商务管家', '227', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('9', '1', '业务管家主管', '228', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('5', '1', '预发布测试服务管家组（工保盾）', '219', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('6', '1', '业务助理主管组', '242', '工保网前台经纪人标签', now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value` (`id`, `user_type_id`, `name`, `code`, `description`, `create_date_time`, `create_name`) VALUES ('7', '1', '线索池成员管家组', '260', 'SCRM标签', now(),'孙馨普通后台用户-138901042');


/**初始化user_type_value_relationship表**/
DROP TABLE IF EXISTS `user_type_value_relationship`;
CREATE TABLE `user_type_value_relationship` (
  `id` varchar(255) NOT NULL COMMENT '序列',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户id',
  `user_type_value_id` varchar(255) DEFAULT NULL COMMENT '用户类型值id',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户类型值关联';
/**1、渠道经纪人组238**/
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('1', '129699200', '1463807938694615041', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('2', '158324157', '1463807938694615041', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('3', '153705906', '1463807938694615041', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('4', '754454620', '1463807938694615041', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('5', '107038734', '1463807938694615041', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('6', '922123174', '1463807938694615041', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('7', '946697549', '1463807938694615041', NULL, now(),'孙馨普通后台用户-138901042');

/**2、业务管家214**/
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('8', '588971231', '2', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('9', '949545287', '2', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('10', '132226127', '2', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('11', '156724026', '2', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('12', '115508828', '2', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('13', '113680063', '2', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('14', '452515409', '2', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('15', '115580086', '2', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('16', '445421603', '2', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('17', '149383634', '2', NULL, now(),'孙馨普通后台用户-138901042');

/**3、预发布测试服务管家组（工保盾）219**/
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('18', '127570407', '5', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('19', '122222737', '5', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('20', '841016589', '5', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('21', '146054364', '5', NULL, now(),'孙馨普通后台用户-138901042');

/**4、业务管家主管228**/
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('22', '865733692', '9', NULL, now(),'孙馨普通后台用户-138901042');

/**5、特别经纪人237**/
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('23', '161478236', '14', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('24', '303815353', '14', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('25', '118250222', '14', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('26', '275283096', '14', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('27', '495815303', '14', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('28', '577678640', '14', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('29', '132800026', '14', NULL, now(),'孙馨普通后台用户-138901042');

/**6、业务助理215**/
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('30', '129699200', '3', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('31', '158324157', '3', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('32', '153705906', '3', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('33', '754454620', '3', NULL, now(),'孙馨普通后台用户-138901042');

/**7、业务助理主管组242**/
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('34', '138901042', '6', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('35', '158324157', '6', NULL, now(),'孙馨普通后台用户-138901042');

/**7、保险公司insurance_company**/
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('36', '754454620', '30', NULL, now(),'孙馨普通后台用户-138901042');
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('37', '161406140', '30', NULL, now(),'孙馨普通后台用户-138901042');

/**8、经纪人咨询241**/
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('38', '695475527', '32', NULL, now(),'孙馨普通后台用户-138901042');

/**9、用户咨询241**/
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('39', '588971231', '31', NULL, now(),'孙馨普通后台用户-138901042');

/**10、线索池成员管家组260**/
INSERT INTO `user_type_value_relationship` (`id`, `user_id`, `user_type_value_id`, `description`, `create_date_time`, `create_name`) VALUES ('40', '949545287', '7', NULL, now(),'孙馨普通后台用户-138901042');

