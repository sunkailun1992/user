/*
 Navicat Premium Data Transfer

 Source Server         : 工保网测试
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 172.16.200.212:3306
 Source Schema         : user

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 20/01/2022 16:54:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_token
-- ----------------------------
DROP TABLE IF EXISTS `user_token`;
CREATE TABLE `user_token` (
  `id` varchar(255) NOT NULL COMMENT '序列',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户id',
  `token_type` int(1) DEFAULT NULL COMMENT '设备类型（0：IOS，1：Android）',
  `cid` varchar(255) DEFAULT NULL COMMENT '个推唯一标识',
  `description` varchar(255) DEFAULT NULL COMMENT '说明',
  `create_date_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_name` varchar(255) DEFAULT NULL COMMENT '创建人',
  `modify_date_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_name` varchar(255) DEFAULT NULL COMMENT '修改人',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '删除状态（0：未删除，1：删除）',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `sorting` int(20) DEFAULT '0' COMMENT '排序',
  `version` int(20) DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户设备信息表';

SET FOREIGN_KEY_CHECKS = 1;
