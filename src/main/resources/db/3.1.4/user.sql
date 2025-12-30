DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL COMMENT '序列',
  `source_id` varchar(255) DEFAULT NULL COMMENT '来源id',
  `source_value_id` varchar(255) DEFAULT NULL COMMENT '来源值id',
  `user_name` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '用户头像地址',
  `ip` varchar(255) DEFAULT NULL COMMENT '登录ip',
  `ip_address` varchar(255) DEFAULT NULL COMMENT '登录ip地址',
  `login_date_time` datetime DEFAULT NULL COMMENT '登录时间',
  `before_ip` varchar(255) DEFAULT NULL COMMENT '之前登录ip',
  `before_login_date_time` datetime DEFAULT NULL COMMENT '之前登录时间',
  `today_login_count` int(10) DEFAULT NULL COMMENT '今天登录次数',
  `is_account_locked` bit(1) DEFAULT b'0' COMMENT '账户是否锁定',
  `attribute` int(10) DEFAULT NULL COMMENT '属性',
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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

DROP TABLE IF EXISTS `user_oauths`;
CREATE TABLE `user_oauths` (
   `id` varchar(255) NOT NULL COMMENT '序列',
   `user_id` varchar(255) DEFAULT NULL COMMENT '用户id',
   `oauth_id` varchar(255) DEFAULT NULL COMMENT '授权编码',
   `union_id` varchar(255) DEFAULT NULL COMMENT '统一授权编码',
   `app_url` varchar(255) DEFAULT NULL COMMENT 'app跳转地址',
   `app_package` varchar(255) DEFAULT NULL COMMENT 'app包名',
   `app_class` varchar(255) DEFAULT NULL COMMENT 'app类名',
   `description` varchar(255) DEFAULT NULL COMMENT '说明',
   `create_date_time` datetime DEFAULT NULL COMMENT '创建时间',
   `create_name` varchar(255) DEFAULT NULL COMMENT '创建人',
   `modify_date_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   `modify_name` varchar(255) DEFAULT NULL COMMENT '修改人',
   `is_delete` bit(1) DEFAULT b'0' COMMENT '删除状态',
   `type` int(20) DEFAULT '0' COMMENT '授权类型（0：微信，1：qq，2：微博，3：IOS，4：android）',
   `state` int(20) DEFAULT '0' COMMENT '状态（0：默认）',
   `label` varchar(255) DEFAULT NULL COMMENT '标签',
   `sorting` int(20) DEFAULT '0' COMMENT '排序',
   `version` int(20) DEFAULT '1' COMMENT '版本号',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户授权表';

DROP TABLE IF EXISTS `user_extends`;
CREATE TABLE `user_extends` (
  `id` varchar(255) NOT NULL COMMENT '序列',
  `user_id` varchar(255) NOT NULL COMMENT '用户id',
  `distribution_user_id` varchar(255) DEFAULT NULL COMMENT '分销上层用户id',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `alias` varchar(255) DEFAULT NULL COMMENT '别名',
  `coding` varchar(255) DEFAULT NULL COMMENT '员工编码',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` int(20) DEFAULT '0' COMMENT '性别（0：男，1：女）',
  `id_card` varchar(255) DEFAULT NULL COMMENT '身份证号码',
  `native_place` varchar(255) DEFAULT NULL COMMENT '籍贯',
  `province_code` varchar(255) DEFAULT NULL COMMENT '省编码',
  `province_name` varchar(255) DEFAULT NULL COMMENT '省名称',
  `city_code` varchar(255) DEFAULT NULL COMMENT '市编码',
  `city_name` varchar(255) DEFAULT NULL COMMENT '市名称',
  `area_code` varchar(255) DEFAULT NULL COMMENT '区编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '区名称',
  `address` varchar(255) DEFAULT NULL COMMENT '住址',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ',
  `wechat` varchar(255) DEFAULT '' COMMENT '微信',
  `emergency_contact_name` varchar(255) DEFAULT NULL COMMENT '紧急联系人',
  `emergency_contact_mobile` varchar(255) DEFAULT NULL COMMENT '紧急联系人手机',
  `onboarding_date_time` datetime DEFAULT NULL COMMENT '入职时间',
  `bank_card_number` varchar(255) DEFAULT NULL COMMENT '银行卡号',
  `bank` varchar(255) DEFAULT NULL COMMENT '所属银行',
  `open_account_address` varchar(255) DEFAULT NULL COMMENT '银行卡开户地址',
  `open_account_name` varchar(255) DEFAULT NULL COMMENT '银行卡开户名',
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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户扩展表';

/**1、初始化用户表信息--经纪人**/
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('113976777', '4', '1', '15928556969', NULL, '2021-12-03 21:52:34', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('728002161', '4', '1', '15606936706', NULL, '2021-12-01 13:10:51', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('328706842', '4', '1', '15190055557', NULL, '2021-12-01 11:51:27', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('665362197', '4', '1', '13333478089', NULL, '2021-12-01 11:17:52', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('145638082', '4', '1', '18068035568', NULL, '2021-12-01 10:43:04', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('149383634', '4', '1', '15858049855', NULL, '2021-12-01 10:23:26', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('130795036', '4', '1', '18788567803', NULL, '2021-12-01 09:03:55', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('585520557', '4', '1', '15962196299', NULL, '2021-11-30 21:32:04', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('125325126', '4', '1', '18554033808', NULL, '2021-11-18 15:06:15', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('113382273', '4', '1', '17309995333', NULL, '2021-11-15 23:48:08', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('145434371', '4', '1', '18608039995', NULL, '2021-11-11 17:42:01', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('712202478', '4', '1', '17390919914', NULL, '2021-11-10 15:49:44', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('133005563', '4', '1', '13857753275', NULL, '2021-11-01 09:44:05', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('417171908', '4', '1', '15077916060', NULL, '2021-10-26 09:27:53', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('386366249', '4', '1', '18907542880', NULL, '2021-09-29 15:17:16', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('341447319', '4', '1', '15989429224', NULL, '2021-09-29 10:14:28', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('159962966', '4', '1', '18820158050	', NULL, '2021-09-29 09:53:41', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('115580086', '4', '1', '13065731607', NULL, '2021-09-28 19:53:39', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('107881467', '4', '1', '15238509591', NULL, '2021-09-28 19:47:40', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('113680063', '4', '1', '18855139728', NULL, '2021-09-28 17:44:58', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('115508828', '4', '1', '13819291360', NULL, '2021-09-28 17:10:23', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('812369708', '4', '1', '15617676875', NULL, '2021-09-28 16:55:07', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('103694870', '4', '1', '15356458585', NULL, '2021-09-28 16:46:44', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('104128626', '4', '1', '18971399949', NULL, '2021-09-28 16:22:33', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('100181663', '4', '1', '18279371814', NULL, '2021-09-28 16:04:36', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('143976174', '4', '1', '15003714781', NULL, '2021-09-28 15:49:56', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('818484579', '4', '1', '15058102685', NULL, '2021-09-26 10:25:25', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('156724026', '4', '1', '13666680653', NULL, '2021-09-26 10:21:59', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('640069026', '4', '1', '15557886207', NULL, '2021-09-17 22:42:56', '孙馨普通后台用户-138901042');
-- INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('556925865', '4', '1', '18566227539', NULL, '2021-09-17 14:17:55', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('266109924', '4', '1', '17680000001', NULL, '2021-09-16 14:59:28', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('123767519', '4', '1', '13083958633', NULL, '2021-09-16 14:46:27', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('514871304', '4', '1', '18657127925', NULL, '2021-09-13 17:29:10', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('558692758', '4', '1', '18088185368', NULL, '2021-08-30 17:11:46', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('811036167', '4', '1', '15669020853', NULL, '2021-08-29 22:34:19', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('132800026', '4', '1', '13757718531', NULL, '2021-08-24 11:28:33', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('452515409', '4', '1', '18640087641', NULL, '2021-08-10 17:05:42', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('319471708', '4', '1', '13858887859', NULL, '2021-08-10 16:55:48', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('577678640', '4', '1', '13968849505', NULL, '2021-08-10 14:30:32', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('495815303', '4', '1', '15867768787', NULL, '2021-08-10 14:16:45', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('268479847', '4', '1', '19967418695', NULL, '2021-08-03 14:21:23', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('103882068', '4', '1', '18617134354', NULL, '2021-08-02 17:31:56', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('143741835', '4', '1', '18505672505', NULL, '2021-07-28 17:16:37', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('117747073', '4', '1', '18668062763', NULL, '2021-07-10 18:48:24', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('118872308', '4', '1', '17603807975', NULL, '2021-07-02 17:59:34', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('931683228', '4', '1', '13594158021', NULL, '2021-07-02 10:11:21', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('671991254', '4', '1', '17811112223', NULL, '2021-07-01 17:22:23', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('942504820', '4', '1', '17811112222', NULL, '2021-07-01 17:21:52', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('119318844', '4', '1', '13858569874', NULL, '2021-07-01 16:46:19', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('864043816', '4', '1', '13999999999', NULL, '2021-07-01 09:09:26', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('126587410', '4', '1', '15640344628', NULL, '2021-06-21 15:43:54', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('800962768', '4', '1', '13666630162', NULL, '2021-06-21 09:24:19', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('674106122', '4', '1', '18898981111', NULL, '2021-06-16 19:41:23', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('104753309', '4', '1', '18269731311', NULL, '2021-06-16 19:35:44', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('136710764', '4', '1', '18399012221', NULL, '2021-06-16 19:28:00', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('588971231', '4', '1', '13396889527', NULL, '2021-06-03 10:17:49', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('158324157', '4', '1', '13655811577', NULL, '2021-06-03 10:17:21', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('373101576', '4', '1', '13625814421', NULL, '2021-06-01 18:35:04', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('107038734', '4', '1', '18452354064', NULL, '2021-05-31 15:09:09', '孙馨普通后台用户-138901042');

INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '113976777', NULL, NULL, '15928556969', '2021-12-03 21:52:34', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '728002161', '于美灵	', NULL, '15606936706', '2021-12-01 13:10:51', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '328706842', '陈志遐	', NULL, '15190055557', '2021-12-01 11:51:27', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '665362197', '赵匡	', NULL, '13333478089', '2021-12-01 11:17:52', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '145638082', '徐嘉铭	', NULL, '18068035568', '2021-12-01 10:43:04', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '149383634', '夏陈斌	', NULL, '15858049855', '2021-12-01 10:23:26', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '130795036', '梁通	', NULL, '18788567803', '2021-12-01 09:03:55', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '585520557', '杨洋	', NULL, '15962196299', '2021-11-30 21:32:04', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '125325126', '周颜佳	', NULL, '18554033808', '2021-11-18 15:06:15', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '113382273', NULL, NULL, '17309995333', '2021-11-15 23:48:08', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '145434371', NULL, NULL, '18608039995', '2021-11-11 17:42:01', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '712202478', '王恩鹏	', NULL, '17390919914', '2021-11-10 15:49:44', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '133005563', NULL, NULL, '13857753275', '2021-11-01 09:44:05', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '417171908', '张鹏	', NULL, '15077916060', '2021-10-26 09:27:53', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '386366249', '吉训泽	', NULL, '18907542880', '2021-09-29 15:17:16', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '341447319', '吴佳鑫	', NULL, '15989429224', '2021-09-29 10:14:28', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '159962966', '庄海荣	', NULL, '18820158050', '2021-09-29 09:53:41', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '115580086', '江凤丽呀	', NULL, '13065731607', '2021-09-28 19:53:39', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '107881467', '彭若男呀', NULL, '15238509591', '2021-09-28 19:47:40', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '113680063', '陈国栋	', NULL, '18855139728', '2021-09-28 17:44:58', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '115508828', '孔德婧', NULL, '13819291360', '2021-09-28 17:10:23', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '812369708', '高才	', NULL, '15617676875', '2021-09-28 16:55:07', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '103694870', '王冬	', NULL, '15356458585', '2021-09-28 16:46:44', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '104128626', '陈春燕	', NULL, '18971399949', '2021-09-28 16:22:33', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '100181663', '周晓	', NULL, '18279371814', '2021-09-28 16:04:36', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '143976174', NULL, NULL, '15003714781', '2021-09-28 15:49:56', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '818484579', '余晓华	', NULL, '15058102685', '2021-09-26 10:25:25', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '156724026', '余晓丽	', NULL, '13666680653', '2021-09-26 10:21:59', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '640069026', NULL, NULL, '15557886207', '2021-09-17 22:42:56', '孙馨普通后台用户-138901042');
-- INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '556925865', NULL, NULL, '15989897967', '2021-09-17 14:17:55', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '266109924', NULL, NULL, '17680000001', '2021-09-16 14:59:28', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '123767519', '尼古拉斯赵四', NULL, '13083958633', '2021-09-16 14:46:27', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '514871304', NULL, NULL, '18657127925', '2021-09-13 17:29:10', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '558692758', '朗岩全	', NULL, '18088185368', '2021-08-30 17:11:46', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '811036167', NULL, NULL, '15669020853', '2021-08-29 22:34:19', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '132800026', '陈佩佩	', NULL, '13757718531', '2021-08-24 11:28:33', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '452515409', '陈小明	', NULL, '18640087641', '2021-08-10 17:05:42', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '319471708', '叶擒鹏	', NULL, '13858887859', '2021-08-10 16:55:48', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '577678640', '章菁菁	', NULL, '13968849505', '2021-08-10 14:30:32', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '495815303', '李旭', NULL, '15867768787', '2021-08-10 14:16:45', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '268479847', NULL, NULL, '19967418695', '2021-08-03 14:21:23', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '103882068', NULL, NULL, '18617134354', '2021-08-02 17:31:56', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '143741835', '刘伦文	', NULL, '18505672505', '2021-07-28 17:16:37', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '117747073', '于露', NULL, '18668062763', '2021-07-10 18:48:24', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '118872308', '测试4', NULL, '17603807975', '2021-07-02 17:59:34', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '931683228', '测试3', NULL, '13594158021', '2021-07-02 10:11:21', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '671991254', NULL, NULL, '17811112223', '2021-07-01 17:22:23', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '942504820', 'ff', NULL, '17811112222', '2021-07-01 17:21:52', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '119318844', NULL, NULL, '13858569874', '2021-07-01 16:46:19', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '864043816', NULL, NULL, '13999999999', '2021-07-01 09:09:26', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '126587410', '测试2', NULL, '15640344628', '2021-06-21 15:43:54', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '800962768', '测试1', NULL, '13666630162', '2021-06-21 09:24:19', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '674106122', NULL, NULL, '18898981111', '2021-06-16 19:41:23', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '104753309', '1', NULL, '18269731111', '2021-06-16 19:35:44', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '136710764', '1', NULL, '18399012221', '2021-06-16 19:28:00', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '588971231', '卫平红	', NULL, '13396889527', '2021-06-03 10:17:49', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '158324157', '汪梦瑶	', NULL, '13655811577', '2021-06-03 10:17:21', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '373101576', NULL, NULL, '13625814421', '2021-06-01 18:35:04', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '107038734', '测试', NULL, '18452354064', '2021-05-31 15:09:09', '孙馨普通后台用户-138901042');

/**2、初始化用户表信息--正式经纪人**/
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('708977195', '3', '4', '18673210372', NULL, '2021-09-23 16:38:35', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('614874582', '3', '4', '13873206923', NULL, '2021-09-23 16:37:48', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('153705906', '3', '4', '15624711039', NULL, '2021-09-08 10:58:10', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('865733692', '3', '4', '13162547448', NULL, '2021-07-16 14:03:56', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('129699200', '3', '4', '15356143036', NULL, '2021-07-16 14:16:19', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '708977195', '文豪	', NULL, '18673210372', '2021-09-23 16:38:35', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '614874582', '赵能	', NULL, '13873206923', '2021-09-23 16:37:48', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '153705906', '周恩藏	', NULL, '15624711039', '2021-09-08 10:58:10', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '865733692', '龚保儿	', NULL, '13162547448', '2021-07-16 14:03:56', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '129699200', '汪梦瑶	', NULL, '15356143036', '2021-07-16 14:16:19', '孙馨普通后台用户-138901042');

/**2、初始化用户表信息--保险公司**/
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('833073575', '4', '1', '18352354064', NULL, '2021-05-31 15:18:59', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('754454620', '3', '4', '15372049082', NULL, '2021-08-12 11:20:33', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('161406140', '3', '4', '18300835921', NULL, '2021-09-09 16:10:07', '孙馨普通后台用户-138901042');

INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '833073575', '孙馨保险用户', NULL, '18352354064', '2021-05-31 15:18:59', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '754454620', '谭书雅	', NULL, '15372049082', '2021-08-12 11:20:33', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '161406140', '工保渠道		', NULL, '18300835921', '2021-09-09 16:10:07', '孙馨普通后台用户-138901042');

/**3、初始化用户表信息--管理端角色2**/
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('117430623', '4', '2', '19817857355', NULL, '2021-06-03 10:24:44', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '117430623', '赵静	', NULL, '19817857355', '2021-06-03 10:24:44', '孙馨普通后台用户-138901042');

/**3、初始化用户表信息--管理端业务管理员**/
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('922337203', '4', '2', '18930099997', NULL, '2021-03-23 09:51:40', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '922337203', '笈米	', NULL, '18930099997', '2021-03-23 09:51:40', '孙馨普通后台用户-138901042');

/**3、初始化用户表信息--管理端业务助理**/
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('695475527', '4', '2', '18437928938', NULL, '2021-10-15 16:06:30', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '695475527', '何海燕	', NULL, '18437928938', '2021-10-15 16:06:30', '孙馨普通后台用户-138901042');

/**3、初始化用户表信息--管理端渠道**/
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('278774093', '4', '2', '18668466357', NULL, '2021-06-08 14:06:26', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '278774093', '黄康	', NULL, '18668466357', '2021-06-08 14:06:26', '孙馨普通后台用户-138901042');

/**3、初始化用户表信息--后台管理端**/
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('304346570', '4', '2', '18652354064', NULL, '2021-05-31 15:53:41', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('839196701', '3', '4', '18001107767', NULL, '2021-06-15 17:26:57', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('125866639', '3', '4', '19999999999', NULL, '2021-07-29 15:42:00', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('119341270', '3', '4', 'Sadmin', NULL, '2021-08-06 10:03:25', '孙馨普通后台用户-138901042');

INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '304346570', NULL, NULL, '18652354064', '2021-05-31 15:53:41', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '839196701', '蔡志航	', NULL, '18001107767', '2021-05-31 15:53:41', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '125866639', '核心系统管理员', NULL, '19999999999', '2021-07-29 15:42:00', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '119341270', '核心系统管理员', NULL, '16666666666', '2021-08-06 10:03:25', '孙馨普通后台用户-138901042');

/**3、初始化用户表信息--普通用户**/
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('507342486', '4', '1', '13100732777', NULL, '2021-12-04 09:21:27', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('786719101', '4', '1', '13900001234', NULL, '2021-12-03 14:15:21', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('253244521', '4', '1', '18674507194', NULL, '2021-12-03 13:41:40', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('119376964', '4', '1', '18208872914', NULL, '2021-12-03 09:29:15', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('140089737', '4', '1', '17681818224', NULL, '2021-12-02 14:45:33', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('358232893', '4', '1', '17398335995', NULL, '2021-12-02 10:41:03', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('184090083', '4', '1', '18899997777', NULL, '2021-12-01 18:47:22', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('505364234', '4', '1', '18399998888', NULL, '2021-12-01 15:39:43', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('120615577', '4', '1', '18299998888', NULL, '2021-12-01 15:31:44', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('321083171', '4', '1', '18288889999', NULL, '2021-12-01 11:19:52', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('313451483', '4', '1', '18655554444', NULL, '2021-12-01 10:20:01', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('736555388', '4', '1', '18988889999', NULL, '2021-12-01 10:08:55', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('768816097', '4', '1', '18988887777', NULL, '2021-12-01 09:43:20', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('143597037', '4', '1', '17733332222', NULL, '2021-11-30 15:58:35', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('572163072', '4', '1', '18222223333', NULL, '2021-11-30 14:03:00', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('145196638', '4', '1', '13513514710', NULL, '2021-11-30 10:30:05', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('848369352', '4', '1', '15988192152', NULL, '2021-11-30 09:04:18', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('159315644', '4', '1', '13454345434', NULL, '2021-11-29 14:27:47', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('747518455', '4', '1', '15788889999', NULL, '2021-11-29 10:06:01', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('617574051', '4', '1', '13881600747', NULL, '2021-11-29 10:01:51', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('132232724', '4', '1', '18858206461', NULL, '2021-11-29 09:25:53', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('157742168', '4', '1', '18799998888', NULL, '2021-11-27 18:03:33', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('716514893', '4', '1', '13855556666', NULL, '2021-11-27 14:47:40', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('139952831', '4', '1', '17681818222', NULL, '2021-11-26 14:44:54', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('116869075', '4', '1', '18606529703', NULL, '2021-11-26 10:56:10', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('131904592', '4', '1', '19122223333', NULL, '2021-11-26 09:20:17', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('156207511', '4', '1', '18758206461', NULL, '2021-11-26 08:45:27', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('218765201', '4', '1', '18758206465', NULL, '2021-11-25 19:57:48', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('996969513', '4', '1', '19022223333', NULL, '2021-11-25 18:58:23', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('667659303', '4', '1', '15268593781', NULL, '2021-11-25 17:47:18', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('455349797', '4', '1', '18758206462', NULL, '2021-11-25 17:42:44', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('449886595', '4', '1', '17794568526', NULL, '2021-11-25 17:38:46', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('931522595', '4', '1', '13755556666', NULL, '2021-11-25 17:03:30', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('115097497', '4', '1', '18451734702', NULL, '2021-11-25 13:52:43', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('538981397', '4', '1', '18408219725', NULL, '2021-11-24 15:30:38', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('165029730', '4', '1', '18612429996', NULL, '2021-11-23 18:40:33', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('126092199', '4', '1', '18326168535', NULL, '2021-11-23 15:08:34', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('148608876', '4', '1', '18326168535', NULL, '2021-11-23 09:17:25', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('588621119', '4', '1', '18215521150', NULL, '2021-11-23 00:39:09', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('107596022', '4', '1', '17702526948', NULL, '2021-11-19 09:27:28', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('141505113', '4', '1', '19825046299', NULL, '2021-11-17 14:26:34', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('114926636', '4', '1', '18052219786', NULL, '2021-11-17 11:53:09', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('328380119', '4', '1', '18101880157', NULL, '2021-11-17 11:14:24', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('162108715', '4', '1', '13096386621', NULL, '2021-11-16 16:58:11', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('854939724', '4', '1', '15658581039', NULL, '2021-11-15 14:49:16', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('120210316', '4', '1', '15645153337', NULL, '2021-11-15 14:38:49', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('581264483', '4', '1', '18104509300', NULL, '2021-11-15 14:27:25', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('317516374', '4', '1', '15681360898', NULL, '2021-11-13 19:02:40', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('522538280', '4', '1', '13155171519', NULL, '2021-11-12 11:08:07', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('348572523', '4', '1', '13678018910', NULL, '2021-11-11 10:50:13', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('118667266', '4', '1', '17364459110', NULL, '2021-11-10 16:33:05', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('183026613', '4', '1', '15123933554', NULL, '2021-11-09 15:08:16', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('325007531', '4', '1', '13460607738', NULL, '2021-11-08 14:56:16', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('480932820', '4', '1', '18633037011', NULL, '2021-11-05 17:42:47', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('115805552', '4', '1', '18633016827', NULL, '2021-11-05 17:30:53', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('899649781', '4', '1', '13649652044', NULL, '2021-11-05 14:32:35', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('137162579', '4', '1', '18647014740', NULL, '2021-11-03 15:26:01', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('165051439', '4', '1', '18081539722', NULL, '2021-11-03 12:06:45', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('391248762', '4', '1', '18608459990', NULL, '2021-11-03 11:14:32', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('387859077', '4', '1', '18583983729', NULL, '2021-11-02 17:41:55', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('577540478', '4', '1', '13408626486', NULL, '2021-11-01 16:45:36', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('140636097', '4', '1', '17802712105', NULL, '2021-10-30 18:59:07', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('379763054', '4', '1', '15395882093', NULL, '2021-10-30 17:38:27', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('109537890', '4', '1', '13888678242', NULL, '2021-10-28 10:24:12', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('708892496', '4', '1', '17621859313', NULL, '2021-10-27 15:57:00', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('117826332', '4', '1', '17721341447', NULL, '2021-10-25 09:09:23', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('348822441', '4', '1', '18912510698', NULL, '2021-10-22 17:13:06', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('255247040', '4', '1', '13634708338', NULL, '2021-10-21 16:54:53', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('205138765', '4', '1', '13384888626', NULL, '2021-10-21 15:47:19', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('310564386', '4', '1', '18256509098', NULL, '2021-10-20 16:40:24', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('131202473', '4', '1', '18207327310', NULL, '2021-10-20 10:01:28', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('140003027', '4', '1', '15268618832', NULL, '2021-10-19 16:42:26', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('110014484', '4', '1', '13866799145', NULL, '2021-10-19 14:51:40', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('402856621', '4', '1', '19156568803', NULL, '2021-10-14 16:50:45', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('146516768', '4', '1', '18667135669', NULL, '2021-10-13 11:15:55', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('116121533', '4', '1', '15009914596', NULL, '2021-10-12 10:51:15', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('146739299', '4', '1', '18667135669', NULL, '2021-10-11 17:09:26', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('950953502', '4', '1', '17847123844', NULL, '2021-10-11 16:46:38', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('136325391', '4', '1', '15548797668', NULL, '2021-10-11 16:08:37', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('606201636', '4', '1', '19111850094', NULL, '2021-10-05 20:34:36', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('152459055', '4', '1', '16655160529', NULL, '2021-09-30 10:17:52', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('804355251', '4', '1', '17306443661', NULL, '2021-09-30 09:44:58', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('959258273', '4', '1', '18334250271', NULL, '2021-09-28 10:19:34', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('394369029', '4', '1', '18566227539', NULL, '2021-09-27 11:43:49', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('682912344', '4', '1', '17730369196', NULL, '2021-09-27 11:36:15', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('110442868', '4', '1', '18861825157', NULL, '2021-09-27 10:15:26', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('879897068', '4', '1', '13456720836', NULL, '2021-09-26 11:15:27', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('718117024', '4', '1', '18317492645', NULL, '2021-09-24 15:43:53', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('403223804', '4', '1', '18684943700', NULL, '2021-09-24 15:38:47', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('133203607', '4', '1', '15088663476', NULL, '2021-09-24 15:26:28', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('630156009', '4', '1', '15262235732', NULL, '2021-09-24 15:26:23', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('149096841', '4', '1', '15729091121', NULL, '2021-09-24 15:19:44', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('162039184', '4', '1', '13282199694', NULL, '2021-09-24 15:14:35', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('119362870', '4', '1', '18768124236', NULL, '2021-09-24 15:11:07', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('155660187', '4', '1', '18374116385', NULL, '2021-09-24 15:07:12', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('107359059', '4', '1', '13393829351', NULL, '2021-09-24 15:06:35', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('140630342', '4', '1', '13646888135', NULL, '2021-09-24 15:02:36', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('749698960', '4', '1', '17606508601', NULL, '2021-09-24 14:39:08', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('141693675', '4', '1', '18659466250', NULL, '2021-09-22 10:32:07', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('128063693', '4', '1', '13325816070', NULL, '2021-09-18 15:40:09', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('730840636', '4', '1', '13069316391', NULL, '2021-09-18 14:18:01', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('489934364', '4', '1', '13588895598', NULL, '2021-09-18 10:22:53', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('536630440', '4', '1', '15957870732', NULL, '2021-09-18 09:22:53', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('738981675', '4', '1', '18668320616', NULL, '2021-09-17 15:54:33', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('882639620', '4', '1', '15800353233', NULL, '2021-09-15 17:59:51', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('676775908', '4', '1', '18136623988', NULL, '2021-09-15 17:35:24', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('852505894', '4', '1', '13031047901', NULL, '2021-09-14 13:46:40', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('157484033', '4', '1', '18956679661', NULL, '2021-09-14 11:59:31', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('125491385', '4', '1', '13866751881', NULL, '2021-09-14 10:09:49', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('899959530', '4', '1', '18771390663', NULL, '2021-09-14 09:08:14', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('662227468', '4', '1', '15247987315', NULL, '2021-09-14 07:02:03', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('284508387', '4', '1', '18756278832', NULL, '2021-09-13 14:41:03', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('858966599', '4', '1', '15847379004', NULL, '2021-09-13 11:18:54', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('100549104', '4', '1', '13755677081', NULL, '2021-09-10 15:43:46', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('121252739', '4', '1', '18764000807', NULL, '2021-09-10 15:18:00', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('443947919', '4', '1', '18678348880', NULL, '2021-09-10 15:10:59', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('885851566', '4', '1', '17681818223', NULL, '2021-09-10 13:54:59', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('329799304', '4', '1', '15912483905', NULL, '2021-09-10 10:48:49', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('484204256', '4', '1', '18157501161', NULL, '2021-09-10 09:51:25', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('162506495', '4', '1', '13066905089', NULL, '2021-09-09 11:29:38', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('151606300', '4', '1', '13064828730', NULL, '2021-09-08 22:36:02', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('109881346', '4', '1', '15988806785', NULL, '2021-09-08 16:01:59', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('301820709', '4', '1', '18817280664', NULL, '2021-09-08 13:45:44', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('139923797', '4', '1', '17712850604', NULL, '2021-09-08 09:45:10', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('899781291', '4', '1', '18805517227', NULL, '2021-09-07 09:19:06', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('358008425', '4', '1', '13472772691', NULL, '2021-09-03 17:17:45', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('949630328', '4', '1', '19143173592', NULL, '2021-09-03 16:17:51', '孙馨普通后台用户-138901042');
-- 来超手动修改userCode为18729506575,用户中心之前是137459343
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('18729506575', '4', '1', '18729506575', NULL, '2021-09-03 14:26:39', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('741847981', '4', '1', '15669011532', NULL, '2021-09-03 08:46:23', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('148153143', '4', '1', '18524479235', NULL, '2021-09-02 16:03:43', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('322689681', '4', '1', '13206562261', NULL, '2021-09-02 15:25:43', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('290379303', '4', '1', '18845897848', NULL, '2021-09-02 15:07:54', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('462365967', '4', '1', '18945664986', NULL, '2021-09-02 10:56:08', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('919837385', '4', '1', '13810376458', NULL, '2021-09-01 11:59:40', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('771615748', '4', '1', '13966646206', NULL, '2021-09-01 09:07:23', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('108920202', '4', '1', '18172955787', NULL, '2021-08-30 17:32:07', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('140280258', '4', '1', '18635493607', NULL, '2021-08-28 10:41:35', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('883695447', '4', '1', '13100788862', NULL, '2021-08-26 16:50:50', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('162173406', '4', '1', '18582627069', NULL, '2021-08-22 19:02:54', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('551564945', '4', '1', '13906003741', NULL, '2021-08-22 16:31:27', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('139477590', '4', '1', '18616178513', NULL, '2021-08-17 16:20:27', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('467577205', '4', '1', '15136285435', NULL, '2021-08-17 14:09:51', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('159959793', '4', '1', '13865644888', NULL, '2021-08-17 09:58:13', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('164930523', '4', '1', '17367089795', NULL, '2021-08-14 18:59:10', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('139431489', '4', '1', '13917518781', NULL, '2021-08-11 10:35:31', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('275283096', '4', '1', '18700009999', NULL, '2021-08-10 11:15:10', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('118250222', '4', '1', '18455556666', NULL, '2021-08-10 11:10:51', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('303815353', '4', '1', '18999998881', NULL, '2021-08-10 10:32:40', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('695772116', '4', '1', '18119686420', NULL, '2021-08-10 09:30:40', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('583453781', '4', '1', '17185712363', NULL, '2021-08-08 20:16:17', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('125184447', '4', '1', '15221000509', NULL, '2021-08-07 14:43:14', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('144967127', '4', '1', '15184867027', NULL, '2021-08-06 15:28:28', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('160538741', '4', '1', '18924640708', NULL, '2021-08-05 14:45:13', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('144194698', '4', '1', '17708462153', NULL, '2021-08-03 22:14:01', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('509507794', '4', '1', '15920998969', NULL, '2021-08-03 08:35:12', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('103468087', '4', '1', '18758111080', NULL, '2021-07-29 08:23:14', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('695636286', '4', '1', '18767155360', NULL, '2021-07-28 11:36:05', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('110890564', '4', '1', '15067193017', NULL, '2021-07-09 15:45:23', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('986822583', '4', '1', '13084720329', NULL, '2021-07-05 15:42:02', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('111879455', '4', '1', '15055482211', NULL, '2021-07-02 14:54:33', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('189010204', '4', '1', '18899091101', NULL, '2021-07-02 14:02:22', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('523701186', '4', '1', '13122221328', NULL, '2021-07-01 12:54:53', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('112996477', '4', '1', '15768117401', NULL, '2021-06-29 14:19:04', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('111647451', '4', '1', '18787717729', NULL, '2021-06-28 10:53:54', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('126897859', '4', '1', '15500606966', NULL, '2021-06-24 16:27:00', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('276521350', '4', '1', '13759550883', NULL, '2021-06-23 12:40:48', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('298563535', '4', '1', '15355069255', NULL, '2021-06-21 15:53:35', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('945838895', '4', '1', '13699110127', NULL, '2021-06-18 10:01:00', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('144729609', '4', '1', '18325648012', NULL, '2021-06-16 19:45:05', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('154202572', '4', '1', '18898989988', NULL, '2021-06-16 19:38:18', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('590754156', '4', '1', '18399018881', NULL, '2021-06-16 19:13:15', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('163526156', '4', '1', '17756595878', NULL, '2021-06-16 09:03:35', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('710647619', '4', '1', '13679693011', NULL, '2021-06-13 09:24:12', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('268321728', '4', '1', '18512172730', NULL, '2021-06-09 17:32:35', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('113046320', '4', '1', '13516752949', NULL, '2021-06-09 17:08:30', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('529546936', '4', '1', '18501369770', NULL, '2021-06-07 13:53:30', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('256865812', '4', '1', '18139772885', NULL, '2021-06-04 15:07:05', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('159305635', '4', '1', '17612051414', NULL, '2021-06-04 11:28:54', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('111834948', '4', '1', '18850367706', NULL, '2021-06-03 16:26:46', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('131014517', '4', '1', '18616997869', NULL, '2021-06-03 09:50:44', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('832538150', '4', '1', '18768154662', NULL, '2021-06-03 09:12:08', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('116212824', '4', '1', '17605271477', NULL, '2021-06-02 09:27:53', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('161478236', '4', '1', '18358580869', NULL, '2021-06-01 18:34:14', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('118841303', '4', '1', '18252354064', NULL, '2021-05-31 15:03:20', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('144479523', '3', '4', 'lvleya', NULL, '2021-05-28 15:24:25', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('779812866', '3', '4', 'kailun', NULL, '2021-05-07 12:57:57', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('104802942', '3', '4', '17767259525', NULL, '2021-05-27 15:36:13', '孙馨普通后台用户-138901042');
INSERT INTO `user` (`id`, `source_id`, `source_value_id`, `user_name`, `ip`, `create_date_time`, `create_name`) VALUES ('470487671', '3', '4', '18573137894', NULL, '2021-05-16 09:04:16', '孙馨普通后台用户-138901042');



INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '507342486', NULL, NULL, '13100732777', '2021-12-04 09:21:27', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '786719101', NULL, NULL, '13900001234', '2021-12-03 14:15:21', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '253244521', NULL, NULL, '18674507194', '2021-12-03 13:41:40', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '119376964', NULL, NULL, '18208872914', '2021-12-03 09:29:15', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '140089737', NULL, NULL, '17681818224', '2021-12-02 14:45:33', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '358232893', NULL, NULL, '17398335995', '2021-12-02 10:41:03', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '184090083', NULL, NULL, '18899997777', '2021-12-01 18:47:22', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '505364234', NULL, NULL, '18399998888', '2021-12-01 15:39:43', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '120615577', NULL, NULL, '18299998888', '2021-12-01 15:31:44', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '321083171', NULL, NULL, '18288889999', '2021-12-01 11:19:52', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '313451483', NULL, NULL, '18655554444', '2021-12-01 10:20:01', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '736555388', NULL, NULL, '18988889999', '2021-12-01 10:08:55', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '768816097', NULL, NULL, '18988887777', '2021-12-01 09:43:20', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '143597037', NULL, NULL, '17733332222', '2021-11-30 15:58:35', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '572163072', NULL, NULL, '18222223333', '2021-11-30 14:03:00', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '145196638', NULL, NULL, '13513514710', '2021-11-30 10:30:05', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '848369352', NULL, NULL, '15988192152', '2021-11-30 09:04:18', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '159315644', NULL, NULL, '13454345434', '2021-11-29 14:27:47', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '747518455', NULL, NULL, '15788889999', '2021-11-29 10:06:01', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '617574051', NULL, NULL, '13881600747', '2021-11-29 10:01:51', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '132232724', NULL, NULL, '18858206461', '2021-11-29 09:25:53', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '157742168', NULL, NULL, '18799998888', '2021-11-27 18:03:33', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '716514893', NULL, NULL, '13855556666', '2021-11-27 14:47:40', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '139952831', NULL, NULL, '17681818222', '2021-11-26 14:44:54', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '116869075', NULL, NULL, '18606529703', '2021-11-26 10:56:10', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '131904592', NULL, NULL, '19122223333', '2021-11-26 09:20:17', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '156207511', NULL, NULL, '18758206461', '2021-11-26 08:45:27', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '218765201', NULL, NULL, '18758206465', '2021-11-25 19:57:48', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '996969513', NULL, NULL, '19022223333', '2021-11-25 18:58:23', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '667659303', NULL, NULL, '15268593781', '2021-11-25 17:47:18', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '455349797', NULL, NULL, '18758206462', '2021-11-25 17:42:44', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '449886595', NULL, NULL, '17794568526', '2021-11-25 17:38:46', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '931522595', NULL, NULL, '13755556666', '2021-11-25 17:03:30', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '115097497', NULL, NULL, '18451734702', '2021-11-25 13:52:43', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '538981397', NULL, NULL, '18408219725', '2021-11-24 15:30:38', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '165029730', NULL, NULL, '18612429996', '2021-11-23 18:40:33', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '126092199', NULL, NULL, '18326168535', '2021-11-23 15:08:34', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '148608876', NULL, NULL, '18736039078', '2021-11-23 09:17:25', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '588621119', NULL, NULL, '18215521150', '2021-11-23 00:39:09', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '107596022', NULL, NULL, '17702526948', '2021-11-19 09:27:28', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '141505113', NULL, NULL, '19825046299', '2021-11-17 14:26:34', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '114926636', NULL, NULL, '18052219786', '2021-11-17 11:53:09', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '328380119', NULL, NULL, '18101880157', '2021-11-17 11:14:24', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '162108715', NULL, NULL, '13096386621', '2021-11-16 16:58:11', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '854939724', NULL, NULL, '15658581039', '2021-11-15 14:49:16', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '120210316', NULL, NULL, '15645153337', '2021-11-15 14:38:49', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '581264483', NULL, NULL, '18104509300', '2021-11-15 14:27:25', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '317516374', NULL, NULL, '15681360898', '2021-11-13 19:02:40', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '522538280', NULL, NULL, '13155171519', '2021-11-12 11:08:07', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '348572523', NULL, NULL, '13678018910', '2021-11-11 10:50:13', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '118667266', NULL, NULL, '17364459110', '2021-11-10 16:33:05', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '183026613', NULL, NULL, '15123933554', '2021-11-09 15:08:16', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '325007531', NULL, NULL, '13460607738', '2021-11-08 14:56:16', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '480932820', NULL, NULL, '18633037011', '2021-11-05 17:42:47', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '115805552', NULL, NULL, '18633016827', '2021-11-05 17:30:53', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '899649781', NULL, NULL, '13649652044', '2021-11-05 14:32:35', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '137162579', NULL, NULL, '18647014740', '2021-11-03 15:26:01', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '165051439', NULL, NULL, '18081539722', '2021-11-03 12:06:45', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '391248762', NULL, NULL, '18608459990', '2021-11-03 11:14:32', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '387859077', NULL, NULL, '18583983729', '2021-11-02 17:41:55', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '577540478', NULL, NULL, '13408626486', '2021-11-01 16:45:36', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '140636097', NULL, NULL, '17802712105', '2021-10-30 18:59:07', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '379763054', NULL, NULL, '15395882093', '2021-10-30 17:38:27', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '109537890', NULL, NULL, '13888678242', '2021-10-28 10:24:12', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '708892496', NULL, NULL, '17621859313', '2021-10-27 15:57:00', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '117826332', NULL, NULL, '17721341447', '2021-10-25 09:09:23', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '348822441', NULL, NULL, '18912510698', '2021-10-22 17:13:06', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '255247040', NULL, NULL, '13634708338', '2021-10-21 16:54:53', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '205138765', NULL, NULL, '13384888626', '2021-10-21 15:47:19', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '310564386', NULL, NULL, '18256509098', '2021-10-20 16:40:24', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '131202473', NULL, NULL, '18207327310', '2021-10-20 10:01:28', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '140003027', NULL, NULL, '15268618832', '2021-10-19 16:42:26', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '110014484', NULL, NULL, '13866799145', '2021-10-19 14:51:40', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '402856621', NULL, NULL, '19156568803', '2021-10-14 16:50:45', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '146516768', NULL, NULL, '18667135669', '2021-10-13 11:15:55', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '116121533', NULL, NULL, '15009914596', '2021-10-12 10:51:15', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '146739299', NULL, NULL, '18667135669', '2021-10-11 17:09:26', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '950953502', NULL, NULL, '17847123844', '2021-10-11 16:46:38', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '136325391', NULL, NULL, '15548797668', '2021-10-11 16:08:37', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '606201636', NULL, NULL, '19111850094', '2021-10-05 20:34:36', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '152459055', NULL, NULL, '16655160529', '2021-09-30 10:17:52', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '804355251', NULL, NULL, '17306443661', '2021-09-30 09:44:58', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '959258273', NULL, NULL, '18334250271', '2021-09-28 10:19:34', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '394369029', NULL, NULL, '18566227539', '2021-09-27 11:43:49', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '682912344', NULL, NULL, '17730369196', '2021-09-27 11:36:15', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '110442868', NULL, NULL, '18861825157', '2021-09-27 10:15:26', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '879897068', NULL, NULL, '13456720836', '2021-09-26 11:15:27', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '718117024', NULL, NULL, '18317492645', '2021-09-24 15:43:53', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '403223804', NULL, NULL, '18684943700', '2021-09-24 15:38:47', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '133203607', NULL, NULL, '15088663476', '2021-09-24 15:26:28', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '630156009', NULL, NULL, '15262235732', '2021-09-24 15:26:23', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '149096841', NULL, NULL, '15729091121', '2021-09-24 15:19:44', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '162039184', NULL, NULL, '13282199694', '2021-09-24 15:14:35', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '119362870', NULL, NULL, '18768124236', '2021-09-24 15:11:07', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '155660187', NULL, NULL, '18374116385', '2021-09-24 15:07:12', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '107359059', NULL, NULL, '13393829351', '2021-09-24 15:06:35', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '140630342', NULL, NULL, '13646888135', '2021-09-24 15:02:36', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '749698960', NULL, NULL, '17606508601', '2021-09-24 14:39:08', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '141693675', NULL, NULL, '18659466250', '2021-09-22 10:32:07', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '128063693', NULL, NULL, '13325816070', '2021-09-18 15:40:09', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '730840636', NULL, NULL, '13069316391', '2021-09-18 14:18:01', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '489934364', NULL, NULL, '13588895598', '2021-09-18 10:22:53', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '536630440', NULL, NULL, '15957870732', '2021-09-18 09:22:53', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '738981675', NULL, NULL, '18668320616', '2021-09-17 15:54:33', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '882639620', NULL, NULL, '15800353233', '2021-09-15 17:59:51', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '676775908', NULL, NULL, '18136623988', '2021-09-15 17:35:24', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '852505894', NULL, NULL, '13031047901', '2021-09-14 13:46:40', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '157484033', NULL, NULL, '18956679661', '2021-09-14 11:59:31', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '125491385', NULL, NULL, '13866751881', '2021-09-14 10:09:49', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '899959530', NULL, NULL, '18771390663', '2021-09-14 09:08:14', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '662227468', NULL, NULL, '15247987315', '2021-09-14 07:02:03', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '284508387', NULL, NULL, '18756278832', '2021-09-13 14:41:03', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '858966599', NULL, NULL, '15847379004', '2021-09-13 11:18:54', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '100549104', NULL, NULL, '13755677081', '2021-09-10 15:43:46', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '121252739', NULL, NULL, '18764000807', '2021-09-10 15:18:00', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '443947919', NULL, NULL, '18678348880', '2021-09-10 15:10:59', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '885851566', '彭若男', NULL, '17681818223', '2021-09-10 13:54:59', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '329799304', NULL, NULL, '15912483905', '2021-09-10 10:48:49', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '484204256', NULL, NULL, '18157501161', '2021-09-10 09:51:25', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '162506495', NULL, NULL, '13066905089', '2021-09-09 11:29:38', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '151606300', NULL, NULL, '13064828730', '2021-09-08 22:36:02', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '109881346', NULL, NULL, '15988806785', '2021-09-08 16:01:59', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '301820709', NULL, NULL, '18817280664', '2021-09-08 13:45:44', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '139923797', NULL, NULL, '17712850604', '2021-09-08 09:45:10', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '899781291', NULL, NULL, '18805517227', '2021-09-07 09:19:06', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '358008425', NULL, NULL, '13472772691', '2021-09-03 17:17:45', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '949630328', NULL, NULL, '19143173592', '2021-09-03 16:17:51', '孙馨普通后台用户-138901042');
-- 来超手动修改userCode为18729506575,用户中心之前是137459343
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '18729506575', NULL, NULL, '18729506575', '2021-09-03 14:26:39', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '741847981', NULL, NULL, '15669011532', '2021-09-03 08:46:23', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '148153143', NULL, NULL, '18524479235', '2021-09-02 16:03:43', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '322689681', NULL, NULL, '13206562261', '2021-09-02 15:25:43', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '290379303', NULL, NULL, '18845897848', '2021-09-02 15:07:54', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '462365967', '张伟东', NULL, '18945664986', '2021-09-02 10:56:08', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '919837385', NULL, NULL, '13810376458', '2021-09-01 11:59:40', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '771615748', NULL, NULL, '13966646206', '2021-09-01 09:07:23', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '108920202', NULL, NULL, '18172955787', '2021-08-30 17:32:07', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '140280258', NULL, NULL, '18635493607', '2021-08-28 10:41:35', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '883695447', NULL, NULL, '13100788862', '2021-08-26 16:50:50', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '162173406', NULL, NULL, '18582627069', '2021-08-22 19:02:54', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '551564945', NULL, NULL, '13906003741', '2021-08-22 16:31:27', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '139477590', NULL, NULL, '18616178513', '2021-08-17 16:20:27', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '467577205', NULL, NULL, '15136285435', '2021-08-17 14:09:51', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '159959793', '李同宾', NULL, '13865644888', '2021-08-17 09:58:13', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '164930523', '翁文翔', NULL, '17367089795', '2021-08-14 18:59:10', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '139431489', NULL, NULL, '13917518781', '2021-08-11 10:35:31', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '275283096', NULL, NULL, '18700009999', '2021-08-10 11:15:10', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '118250222', NULL, NULL, '18455556666', '2021-08-10 11:10:51', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '303815353', NULL, NULL, '18999998881', '2021-08-10 10:32:40', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '695772116', NULL, NULL, '18119686420', '2021-08-10 09:30:40', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '583453781', NULL, NULL, '17185712363', '2021-08-08 20:16:17', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '125184447', NULL, NULL, '15221000509', '2021-08-07 14:43:14', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '144967127', NULL, NULL, '15184867027', '2021-08-06 15:28:28', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '160538741', NULL, NULL, '18924640708', '2021-08-05 14:45:13', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '144194698', NULL, NULL, '17708462153', '2021-08-03 22:14:01', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '509507794', NULL, NULL, '15920998969', '2021-08-03 08:35:12', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '103468087', NULL, NULL, '18758111080', '2021-07-29 08:23:14', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '695636286', NULL, NULL, '18767155360', '2021-07-28 11:36:05', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '110890564', '姚斌', NULL, '15067193017', '2021-07-09 15:45:23', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '986822583', NULL, NULL, '13084720329', '2021-07-05 15:42:02', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '111879455', NULL, NULL, '15055482211', '2021-07-02 14:54:33', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '189010204', NULL, NULL, '18899091101', '2021-07-02 14:02:22', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '523701186', NULL, NULL, '13122221328', '2021-07-01 12:54:53', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '112996477', NULL, NULL, '15768117401', '2021-06-29 14:19:04', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '111647451', NULL, NULL, '18787717729', '2021-06-28 10:53:54', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '126897859', NULL, NULL, '15500606966', '2021-06-24 16:27:00', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '276521350', NULL, NULL, '13759550883', '2021-06-23 12:40:48', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '298563535', NULL, NULL, '15355069255', '2021-06-21 15:53:35', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '945838895', NULL, NULL, '13699110127', '2021-06-18 10:01:00', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '144729609', '1', NULL, '18325648012', '2021-06-16 19:45:05', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '154202572', NULL, NULL, '18898989988', '2021-06-16 19:38:18', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '590754156', NULL, NULL, '18399018881', '2021-06-16 19:13:15', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '163526156', NULL, NULL, '17756595878', '2021-06-16 09:03:35', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '710647619', NULL, NULL, '13679693011', '2021-06-13 09:24:12', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '268321728', NULL, NULL, '18512172730', '2021-06-09 17:32:35', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '113046320', NULL, NULL, '13516752949', '2021-06-09 17:08:30', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '529546936', NULL, NULL, '18501369770', '2021-06-07 13:53:30', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '256865812', NULL, NULL, '18139772885', '2021-06-04 15:07:05', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '159305635', NULL, NULL, '17612051414', '2021-06-04 11:28:54', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '111834948', NULL, NULL, '18850367706', '2021-06-03 16:26:46', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '131014517', NULL, NULL, '18616997869', '2021-06-03 09:50:44', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '832538150', NULL, NULL, '18768154662', '2021-06-03 09:12:08', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '116212824', NULL, NULL, '17605271477', '2021-06-02 09:27:53', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '161478236', NULL, NULL, '18358580869', '2021-06-01 18:34:14', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '118841303', '孙馨普通用户', NULL, '18252354064', '2021-05-31 15:03:20', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '144479523', '吕乐亚', NULL, '13989539743', '2021-05-28 15:24:25', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '779812866', '郑凯伦', NULL, '13777579028', '2021-05-07 12:57:57', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '104802942', '于露', NULL, '17767259525', '2021-05-27 15:36:13', '孙馨普通后台用户-138901042');
INSERT INTO `user_extends` (`id`, `user_id`, `name`, `alias`, `mobile`, `create_date_time`, `create_name`) VALUES (uuid_short(), '470487671', NULL, NULL, '18573137894', '2021-05-16 09:04:16', '孙馨普通后台用户-138901042');