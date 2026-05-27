CREATE TABLE IF NOT EXISTS auth_tenant (
    id varchar(64) NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL COMMENT '租户名称',
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
    UNIQUE KEY uk_auth_tenant_code (code),
    KEY idx_auth_tenant_state (state)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证租户表';

CREATE TABLE IF NOT EXISTS auth_user (
    id varchar(64) NOT NULL PRIMARY KEY,
    username varchar(255) NOT NULL COMMENT '用户名',
    password varchar(255) NOT NULL COMMENT '密码',
    nickname varchar(255) DEFAULT NULL COMMENT '昵称',
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
    UNIQUE KEY uk_auth_user_tenant_username (tenant_id, username),
    KEY idx_auth_user_tenant_id (tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证用户表';

CREATE TABLE IF NOT EXISTS auth_role (
    id varchar(64) NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL COMMENT '角色名称',
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
    UNIQUE KEY uk_auth_role_tenant_code (tenant_id, code),
    KEY idx_auth_role_tenant_id (tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证角色表';

CREATE TABLE IF NOT EXISTS auth_resource (
    id varchar(64) NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL COMMENT '资源名称',
    resource_category varchar(32) NOT NULL COMMENT '资源分类：FRONTEND/BACKEND',
    path varchar(255) DEFAULT NULL COMMENT '资源路径',
    method varchar(32) DEFAULT NULL COMMENT '请求方法',
    parent_id varchar(64) DEFAULT NULL COMMENT '父级资源ID',
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
    UNIQUE KEY uk_auth_resource_tenant_code (tenant_id, code),
    KEY idx_auth_resource_tenant_id (tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证资源表';

CREATE TABLE IF NOT EXISTS auth_user_role (
    id varchar(64) NOT NULL PRIMARY KEY,
    user_id varchar(64) NOT NULL COMMENT '用户ID',
    role_id varchar(64) NOT NULL COMMENT '角色ID',
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
    UNIQUE KEY uk_auth_user_role_tenant_user_role (tenant_id, user_id, role_id),
    KEY idx_auth_user_role_tenant_id (tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证用户角色关系表';

CREATE TABLE IF NOT EXISTS auth_role_resource (
    id varchar(64) NOT NULL PRIMARY KEY,
    role_id varchar(64) NOT NULL COMMENT '角色ID',
    resource_id varchar(64) NOT NULL COMMENT '资源ID',
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
    UNIQUE KEY uk_auth_role_resource_tenant_role_resource (tenant_id, role_id, resource_id),
    KEY idx_auth_role_resource_tenant_id (tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证角色资源关系表';

INSERT IGNORE INTO auth_tenant
(id, name, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('100', '默认租户', 'default', '系统默认租户', 'system', 'system', 1, 1, '100');

INSERT IGNORE INTO auth_user
(id, username, password, nickname, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('u_admin_100', 'admin', '$2a$10$WzvrHmwWViUXVzyNaez6ROLEEg49ATB68RCjSWFai5MlyWojAbraG', '管理员', 'admin', '默认管理员账号', 'system', 'system', 1, 1, '100');

INSERT IGNORE INTO auth_role
(id, name, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('r_admin_100', '管理员', 'admin', '默认管理员角色', 'system', 'system', 1, 1, '100');

INSERT IGNORE INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description, create_name, modify_name, sorting, state, version, tenant_id)
VALUES
('res_root_system_100', '系统管理', 'FRONTEND', '/system', NULL, NULL, 'menu:system', '前端系统管理根菜单', 'system', 'system', 1, 1, 1, '100'),
('res_menu_tenant_100', '租户管理', 'FRONTEND', '/system/tenant', NULL, 'res_root_system_100', 'menu:tenant', '前端租户菜单', 'system', 'system', 10, 1, 1, '100'),
('res_menu_user_100', '用户管理', 'FRONTEND', '/system/user', NULL, 'res_root_system_100', 'menu:user', '前端用户菜单', 'system', 'system', 20, 1, 1, '100'),
('res_menu_role_100', '角色管理', 'FRONTEND', '/system/role', NULL, 'res_root_system_100', 'menu:role', '前端角色菜单', 'system', 'system', 30, 1, 1, '100'),
('res_menu_role_bind_100', '角色资源授权', 'FRONTEND', '/system/role#bind-resource', NULL, 'res_menu_role_100', 'menu:role:bind-resource', '前端角色资源授权按钮示例', 'system', 'system', 31, 1, 1, '100'),
('res_menu_resource_100', '权限资源', 'FRONTEND', '/system/resource', NULL, 'res_root_system_100', 'menu:resource', '前端权限资源菜单', 'system', 'system', 40, 1, 1, '100'),
('res_menu_resource_tree_100', '资源树维护', 'FRONTEND', '/system/resource#tree', NULL, 'res_menu_resource_100', 'menu:resource:tree', '前端资源树维护按钮示例', 'system', 'system', 41, 1, 1, '100'),
('res_api_auth_root_100', '认证接口', 'BACKEND', '/auth', '*', NULL, 'api:auth', '认证授权接口根节点', 'system', 'system', 1, 1, 1, '100'),
('res_api_auth_resource_100', '当前资源列表', 'BACKEND', '/auth/resources', 'GET', 'res_api_auth_root_100', 'user:auth:resources', '当前用户资源接口权限', 'system', 'system', 10, 1, 1, '100'),
('res_api_auth_manage_100', '认证体系维护', 'BACKEND', '/auth/manage/**', '*', 'res_api_auth_root_100', 'user:auth:manage', '认证体系维护接口权限', 'system', 'system', 20, 1, 1, '100'),
('res_api_code_generate_100', '编码自动生成', 'BACKEND', '/auth/manage/codes/generate', 'GET', 'res_api_auth_manage_100', 'user:auth:code-generate', '业务编码自动生成接口权限', 'system', 'system', 21, 1, 1, '100'),
('res_api_resource_tree_100', '资源树查询', 'BACKEND', '/auth/manage/resources', 'GET', 'res_api_auth_manage_100', 'user:auth:resource-tree', '资源树查询接口权限示例', 'system', 'system', 22, 1, 1, '100');

UPDATE auth_resource SET parent_id = 'res_root_system_100' WHERE tenant_id = '100' AND id IN ('res_menu_tenant_100', 'res_menu_user_100', 'res_menu_role_100', 'res_menu_resource_100');
UPDATE auth_resource SET parent_id = 'res_api_auth_root_100' WHERE tenant_id = '100' AND id IN ('res_api_auth_resource_100', 'res_api_auth_manage_100');

INSERT IGNORE INTO auth_user_role
(id, user_id, role_id, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('ur_admin_100', 'u_admin_100', 'r_admin_100', 'u_admin_100:r_admin_100', '默认管理员用户角色关系', 'system', 'system', 1, 1, '100');

INSERT IGNORE INTO auth_role_resource
(id, role_id, resource_id, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('rr_admin_root_system_100', 'r_admin_100', 'res_root_system_100', 'r_admin_100:res_root_system_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_tenant_100', 'r_admin_100', 'res_menu_tenant_100', 'r_admin_100:res_menu_tenant_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_user_100', 'r_admin_100', 'res_menu_user_100', 'r_admin_100:res_menu_user_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_role_100', 'r_admin_100', 'res_menu_role_100', 'r_admin_100:res_menu_role_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_role_bind_100', 'r_admin_100', 'res_menu_role_bind_100', 'r_admin_100:res_menu_role_bind_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_resource_100', 'r_admin_100', 'res_menu_resource_100', 'r_admin_100:res_menu_resource_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_resource_tree_100', 'r_admin_100', 'res_menu_resource_tree_100', 'r_admin_100:res_menu_resource_tree_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_auth_root_100', 'r_admin_100', 'res_api_auth_root_100', 'r_admin_100:res_api_auth_root_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_auth_resource_100', 'r_admin_100', 'res_api_auth_resource_100', 'r_admin_100:res_api_auth_resource_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_auth_manage_100', 'r_admin_100', 'res_api_auth_manage_100', 'r_admin_100:res_api_auth_manage_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_code_generate_100', 'r_admin_100', 'res_api_code_generate_100', 'r_admin_100:res_api_code_generate_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_resource_tree_100', 'r_admin_100', 'res_api_resource_tree_100', 'r_admin_100:res_api_resource_tree_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100');
