/**初始化USER_ROLE表**/
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` varchar(255) NOT NULL COMMENT '序列',
  `user_id` varchar(255) NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_id` varchar(255) NOT NULL DEFAULT '0' COMMENT '角色id',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';


/**1、用户中心，孙馨普通用户账号：18252354064-118841303**/
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('1', '507342486', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('2', '786719101', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('3', '253244521', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('4', '119376964', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('5', '140089737', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('6', '358232893', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('7', '184090083', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('8', '505364234', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('9', '120615577', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('10', '321083171', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('11', '313451483', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('12', '736555388', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('13', '768816097', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('14', '143597037', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('15', '572163072', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('16', '145196638', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('17', '848369352', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('18', '159315644', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('19', '747518455', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('20', '617574051', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('21', '132232724', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('22', '157742168', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('23', '716514893', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('24', '139952831', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('25', '116869075', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('26', '131904592', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('27', '156207511', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('28', '218765201', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('29', '996969513', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('30', '667659303', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('31', '455349797', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('32', '449886595', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('33', '931522595', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('34', '115097497', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('35', '538981397', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('36', '165029730', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('37', '126092199', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('38', '148608876', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('39', '588621119', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('40', '107596022', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('41', '141505113', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('42', '114926636', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('43', '328380119', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('44', '162108715', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('45', '461667798', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('46', '854939724', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('47', '120210316', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('48', '581264483', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('49', '317516374', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('50', '522538280', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('51', '348572523', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('52', '118667266', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('53', '183026613', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('54', '325007531', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('55', '480932820', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('56', '115805552', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('57', '899649781', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('58', '137162579', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('59', '165051439', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('60', '391248762', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('61', '387859077', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('62', '577540478', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('63', '140636097', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('64', '379763054', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('65', '109537890', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('66', '708892496', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('67', '117826332', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('68', '348822441', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('69', '255247040', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('70', '205138765', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('71', '310564386', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('72', '131202473', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('73', '140003027', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('74', '110014484', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('75', '402856621', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('76', '146516768', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('77', '116121533', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('78', '146739299', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('79', '950953502', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('80', '136325391', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('81', '606201636', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('82', '152459055', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('83', '804355251', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('84', '959258273', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('85', '682912344', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('86', '110442868', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('87', '879897068', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('88', '718117024', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('89', '403223804', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('90', '133203607', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('91', '630156009', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('92', '149096841', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('93', '162039184', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('94', '119362870', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('95', '155660187', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('96', '107359059', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('97', '140630342', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('98', '749698960', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('99', '129954788', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('100', '106645730', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('101', '141693675', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('102', '128063693', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('103', '730840636', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('104', '489934364', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('105', '536630440', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('106', '738981675', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('107', '882639620', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('108', '676775908', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('109', '852505894', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('110', '157484033', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('111', '120310950', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('112', '125491385', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('113', '899959530', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('114', '662227468', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('115', '284508387', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('116', '858966599', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('117', '100549104', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('118', '121252739', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('119', '443947919', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('120', '885851566', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('121', '329799304', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('122', '484204256', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('123', '162506495', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('124', '151606300', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('125', '109881346', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('126', '301820709', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('127', '139923797', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('128', '899781291', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('129', '358008425', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('130', '949630328', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('131', '137459343', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('132', '741847981', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('133', '148153143', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('134', '322689681', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('135', '290379303', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('136', '462365967', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('137', '919837385', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('138', '771615748', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('139', '108920202', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('140', '140280258', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('141', '883695447', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('142', '162173406', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('143', '551564945', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('144', '139477590', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('145', '467577205', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('146', '159959793', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('147', '164930523', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('148', '139431489', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('149', '275283096', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('150', '118250222', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('151', '303815353', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('152', '695772116', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('153', '583453781', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('154', '125184447', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('155', '144967127', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('156', '160538741', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('157', '144194698', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('158', '509507794', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('159', '103468087', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('160', '695636286', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('161', '110890564', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('162', '986822583', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('163', '111879455', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('164', '189010204', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('165', '523701186', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('166', '112996477', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('167', '111647451', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('168', '126897859', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('169', '276521350', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('170', '298563535', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('171', '945838895', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('172', '144729609', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('173', '154202572', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('174', '590754156', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('175', '163526156', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('176', '710647619', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('177', '268321728', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('178', '113046320', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('179', '141289963', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('180', '113582917', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('181', '529546936', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('182', '256865812', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('183', '159305635', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('184', '111834948', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('185', '131014517', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('186', '832538150', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('187', '116212824', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('188', '373101576', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('189', '161478236', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('190', '118841303', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('191', '144479523', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('192', '104802942', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('193', '470487671', '53', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('194', '779812866', '53', NULL, now(), '孙馨普通后台用户-138901042');


/**2、用户中心，孙馨正式经纪人账号：18452354064-107038734**/
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('195', '107038734', '52', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('196', '614874582', '52', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('197', '153705906', '52', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('198', '129699200', '52', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('199', '865733692', '52', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('200', '588971231', '52', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('201', '158324157', '52', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('202', '708977195', '52', NULL, now(), '孙馨普通后台用户-138901042');


/**3、用户中心，非正式经纪人账号**/
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('203', '113976777', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('204', '728002161', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('205', '328706842', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('206', '665362197', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('207', '145638082', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('208', '149383634', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('209', '130795036', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('210', '585520557', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('211', '125325126', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('212', '113382273', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('213', '145434371', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('214', '712202478', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('215', '946697549', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('216', '133005563', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('217', '417171908', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('218', '695475527', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('219', '386366249', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('220', '341447319', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('221', '159962966', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('222', '115580086', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('223', '107881467', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('224', '113680063', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('225', '115508828', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('226', '812369708', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('227', '103694870', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('228', '104128626', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('229', '100181663', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('230', '143976174', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('231', '922123174', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('232', '818484579', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('233', '156724026', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('234', '640069026', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('235', '556925865', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('236', '132226127', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('237', '949545287', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('238', '942556366', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('239', '143276335', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('240', '118552507', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('241', '387682038', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('242', '101271995', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('243', '147612941', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('244', '445421603', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('245', '266109924', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('246', '123767519', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('247', '514871304', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('248', '897553380', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('249', '558692758', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('250', '811036167', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('251', '132800026', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('252', '164930523', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('253', '754454620', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('254', '452515409', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('255', '319471708', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('256', '577678640', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('257', '495815303', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('258', '268479847', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('259', '103882068', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('260', '143741835', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('261', '129699200', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('262', '117747073', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('263', '110890564', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('264', '118872308', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('265', '931683228', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('266', '671991254', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('267', '942504820', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('268', '119318844', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('269', '864043816', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('270', '140257411', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('271', '126587410', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('272', '800962768', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('273', '674106122', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('274', '104753309', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('275', '136710764', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('276', '373101576', '62', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('277', '111919871', '62', NULL, now(), '孙馨普通后台用户-138901042');


/**4、用户中心，孙馨保险用户账号：18352354064-833073575**/
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('278', '161406140', '54', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('279', '754454620', '54', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('280', '123712690', '54', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('281', '965545548', '54', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('282', '132675541', '54', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('283', '833073575', '54', NULL, now(), '孙馨普通后台用户-138901042');


/**5、用户中心，工保网后台超管-孙馨管理员账号：18552354064-138901042**/
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('284', '138901042', '51', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('285', '104802942', '51', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('286', '839196701', '51', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('287', '125866639', '51', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('288', '119341270', '51', NULL, now(), '孙馨普通后台用户-138901042');


/**6、用户中心，工保网管理端角色2**/
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('289', '117430623', '55', NULL, now(), '孙馨普通后台用户-138901042');


/**6、用户中心，工保网后台管理端业务管理员**/
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('290', '922337203', '84', NULL, now(), '孙馨普通后台用户-138901042');


/**7、用户中心，工保网后台管理端业务支持**/
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('291', '110890564', '88', NULL, now(), '孙馨普通后台用户-138901042');


/**8、用户中心，工保网后台管理端渠道**/
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('292', '278774093', '89', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('293', '304346570', '89', NULL, now(), '孙馨普通后台用户-138901042');


/**9、用户中心，工保网后台管理端业务助理**/
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('294', '695475527', '90', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('295', '129699200', '90', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('296', '158324157', '90', NULL, now(), '孙馨普通后台用户-138901042');


/**10、用户中心，CRM业务管家**/
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('297', '149383634', '79', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('298', '156724026', '79', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('299', '132226127', '79', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('300', '949545287', '79', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('301', '445421603', '79', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('302', '588971231', '79', NULL, now(), '孙馨普通后台用户-138901042');


/**11、用户中心，CRM业务助理**/
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('303', '129699200', '78', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('304', '158324157', '78', NULL, now(), '孙馨普通后台用户-138901042');


/**12、用户中心，CRM互联网管家主管**/
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('305', '865733692', '75', NULL, now(), '孙馨普通后台用户-138901042');


/**12、用户中心，CRM管理员**/
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('306', '104802942', '72', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('307', '922337203', '72', NULL, now(), '孙馨普通后台用户-138901042');


/**12、用户中心，CRM业务管家主管**/
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('308', '865733692', '71', NULL, now(), '孙馨普通后台用户-138901042');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `description`, `create_date_time`, `create_name`) VALUES ('309', '104802942', '71', NULL, now(), '孙馨普通后台用户-138901042');
