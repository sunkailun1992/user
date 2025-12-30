CREATE TABLE `user_attachment`
(
    `id`               varchar(255) NOT NULL COMMENT '序列',
    `user_id`          varchar(255) NULL DEFAULT NULL COMMENT '用户id',
    `name`             varchar(255) NULL COMMENT '文件名称',
    `address`          varchar(255) NULL COMMENT '文件地址',
    `code`             varchar(255) NULL COMMENT '编码',
    `description`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
    `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_name`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_date_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '修改时间',
    `modify_name`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `is_delete`        bit(1) NULL DEFAULT b'0' COMMENT '删除',
    `type`             int(20) NULL DEFAULT 0 COMMENT '类型（0：默认）',
    `state`            int(20) NULL DEFAULT 0 COMMENT '状态（0：默认）',
    `label`            varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签',
    `sorting`          int(20) NULL DEFAULT 0 COMMENT '排序',
    `version`          int(20) NULL DEFAULT 1 COMMENT '版本号',
    PRIMARY KEY (`id`)
) COMMENT = '用户附件';
