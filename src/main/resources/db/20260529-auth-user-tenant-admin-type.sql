SET @auth_user_admin_type_exists := (
    SELECT COUNT(1)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'auth_user'
      AND COLUMN_NAME = 'admin_type'
);

SET @auth_user_admin_type_sql := IF(
    @auth_user_admin_type_exists = 0,
    'ALTER TABLE auth_user ADD COLUMN admin_type varchar(32) NOT NULL DEFAULT ''TENANT_ADMIN'' COMMENT ''管理员分类：PLATFORM_SUPER_ADMIN/TENANT_ADMIN'' AFTER nickname',
    'SELECT 1'
);

PREPARE auth_user_admin_type_stmt FROM @auth_user_admin_type_sql;
EXECUTE auth_user_admin_type_stmt;
DEALLOCATE PREPARE auth_user_admin_type_stmt;

CREATE TABLE IF NOT EXISTS auth_user_tenant (
    id varchar(64) NOT NULL PRIMARY KEY,
    user_id varchar(64) NOT NULL COMMENT '用户ID',
    relation_tenant_id varchar(64) NOT NULL COMMENT '关联租户ID',
    dept_id varchar(64) DEFAULT NULL COMMENT '当前租户内部门ID',
    default_tenant bit(1) DEFAULT b'0' COMMENT '是否默认租户',
    code varchar(255) DEFAULT NULL COMMENT '编码',
    description varchar(255) DEFAULT NULL COMMENT '说明',
    create_date_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_name varchar(255) DEFAULT NULL COMMENT '创建人',
    modify_date_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    modify_name varchar(255) DEFAULT NULL COMMENT '修改人',
    is_delete bit(1) DEFAULT b'0' COMMENT '删除状态',
    type int DEFAULT '0' COMMENT '类型（0：默认）',
    state int DEFAULT '1' COMMENT '状态（1：启用，2：禁用）',
    label varchar(255) DEFAULT NULL COMMENT '标签',
    sorting int DEFAULT '0' COMMENT '排序',
    version int DEFAULT '1' COMMENT '版本号',
    tenant_id varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '租户id',
    UNIQUE KEY uk_auth_user_tenant_user_relation (user_id, relation_tenant_id),
    KEY idx_auth_user_tenant_relation (relation_tenant_id),
    KEY idx_auth_user_tenant_tenant_id (tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证用户租户关联表';

UPDATE auth_user
SET admin_type = 'PLATFORM_SUPER_ADMIN'
WHERE id = 'u_admin_100';

UPDATE auth_user
SET admin_type = 'TENANT_ADMIN'
WHERE admin_type IS NULL
   OR admin_type = '';

INSERT IGNORE INTO auth_user_tenant
(id, user_id, relation_tenant_id, dept_id, default_tenant, code, description, create_name, modify_name, state, version, tenant_id)
SELECT
    CONCAT('ut_', SUBSTRING(MD5(CONCAT(id, ':', tenant_id)), 1, 32)),
    id,
    tenant_id,
    dept_id,
    b'1',
    CONCAT(id, ':', tenant_id),
    '历史用户默认租户关联',
    'system',
    'system',
    1,
    1,
    tenant_id
FROM auth_user
WHERE is_delete = b'0';
