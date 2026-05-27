CREATE TABLE IF NOT EXISTS auth_dept (
    id varchar(64) NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL COMMENT '部门名称',
    parent_id varchar(64) DEFAULT NULL COMMENT '父级部门ID',
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
    UNIQUE KEY uk_auth_dept_tenant_code (tenant_id, code),
    KEY idx_auth_dept_tenant_parent (tenant_id, parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证部门表';

CREATE TABLE IF NOT EXISTS auth_role_data_scope (
    id varchar(64) NOT NULL PRIMARY KEY,
    role_id varchar(64) NOT NULL COMMENT '角色ID',
    dept_id varchar(64) NOT NULL COMMENT '部门ID',
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
    UNIQUE KEY uk_auth_role_data_scope_tenant_role_dept (tenant_id, role_id, dept_id),
    KEY idx_auth_role_data_scope_tenant_id (tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色自定义数据范围表';

SET @auth_user_dept_id_exists := (
    SELECT COUNT(1)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'auth_user'
      AND COLUMN_NAME = 'dept_id'
);

SET @auth_user_dept_id_sql := IF(
    @auth_user_dept_id_exists = 0,
    'ALTER TABLE auth_user ADD COLUMN dept_id varchar(64) DEFAULT NULL COMMENT ''所属部门ID''',
    'SELECT 1'
);

PREPARE auth_user_dept_id_stmt FROM @auth_user_dept_id_sql;
EXECUTE auth_user_dept_id_stmt;
DEALLOCATE PREPARE auth_user_dept_id_stmt;

SET @auth_role_data_scope_exists := (
    SELECT COUNT(1)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'auth_role'
      AND COLUMN_NAME = 'data_scope'
);

SET @auth_role_data_scope_sql := IF(
    @auth_role_data_scope_exists = 0,
    'ALTER TABLE auth_role ADD COLUMN data_scope varchar(32) NOT NULL DEFAULT ''SELF'' COMMENT ''数据权限范围：ALL/SELF/DEPT/DEPT_TREE/CUSTOM''',
    'SELECT 1'
);

PREPARE auth_role_data_scope_stmt FROM @auth_role_data_scope_sql;
EXECUTE auth_role_data_scope_stmt;
DEALLOCATE PREPARE auth_role_data_scope_stmt;

INSERT IGNORE INTO auth_dept
(id, name, parent_id, code, description, create_name, modify_name, sorting, state, version, tenant_id)
VALUES
('dept_root_100', '默认部门', NULL, 'default', '系统默认部门', 'system', 'system', 1, 1, 1, '100');

UPDATE auth_user
SET dept_id = 'dept_root_100'
WHERE tenant_id = '100'
  AND id = 'u_admin_100'
  AND (dept_id IS NULL OR dept_id = '');

UPDATE auth_role
SET data_scope = 'ALL'
WHERE tenant_id = '100'
  AND id = 'r_admin_100';

INSERT IGNORE INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description, create_name, modify_name, sorting, state, version, tenant_id)
VALUES
('res_menu_dept_100', '部门管理', 'FRONTEND', '/system/dept', NULL, 'res_root_system_100', 'menu:dept', '前端部门菜单', 'system', 'system', 15, 1, 1, '100'),
('res_menu_role_data_scope_100', '角色数据范围', 'FRONTEND', '/system/role#data-scope', NULL, 'res_menu_role_100', 'menu:role:data-scope', '前端角色数据范围授权按钮示例', 'system', 'system', 32, 1, 1, '100');

INSERT IGNORE INTO auth_role_resource
(id, role_id, resource_id, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('rr_admin_menu_dept_100', 'r_admin_100', 'res_menu_dept_100', 'r_admin_100:res_menu_dept_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_role_data_scope_100', 'r_admin_100', 'res_menu_role_data_scope_100', 'r_admin_100:res_menu_role_data_scope_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100');
