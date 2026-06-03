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

CREATE TABLE IF NOT EXISTS auth_dept (
    id varchar(64) NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL COMMENT '部门名称',
    parent_id varchar(64) DEFAULT NULL COMMENT '父级部门ID',
    owner_user_id varchar(64) DEFAULT NULL COMMENT '负责人用户ID',
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
    KEY idx_auth_dept_tenant_parent (tenant_id, parent_id),
    KEY idx_auth_dept_owner_user_id (owner_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证部门表';

CREATE TABLE IF NOT EXISTS auth_user (
    id varchar(64) NOT NULL PRIMARY KEY,
    username varchar(255) NOT NULL COMMENT '用户名',
    password varchar(255) NOT NULL COMMENT '密码',
    nickname varchar(255) DEFAULT NULL COMMENT '昵称',
    admin_type varchar(32) NOT NULL DEFAULT 'TENANT_ADMIN' COMMENT '管理员分类：PLATFORM_SUPER_ADMIN/TENANT_ADMIN',
    dept_id varchar(64) DEFAULT NULL COMMENT '所属部门ID',
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
    KEY idx_auth_user_tenant_id (tenant_id),
    KEY idx_auth_user_dept_id (dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证用户表';

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
    KEY idx_auth_user_tenant_dept_id (dept_id),
    KEY idx_auth_user_tenant_tenant_id (tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证用户租户关联表';

CREATE TABLE IF NOT EXISTS auth_role (
    id varchar(64) NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL COMMENT '角色名称',
    data_scope varchar(32) NOT NULL DEFAULT 'SELF' COMMENT '数据权限范围：ALL/SELF/DEPT/DEPT_TREE/CUSTOM',
    owner_user_id varchar(64) DEFAULT NULL COMMENT '负责人用户ID',
    dept_id varchar(64) DEFAULT NULL COMMENT '归属部门ID',
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
    KEY idx_auth_role_tenant_id (tenant_id),
    KEY idx_auth_role_owner_user_id (owner_user_id),
    KEY idx_auth_role_dept_id (dept_id)
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
    KEY idx_auth_resource_tenant_id (tenant_id),
    KEY idx_auth_resource_parent_id (parent_id)
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
    KEY idx_auth_user_role_tenant_id (tenant_id),
    KEY idx_auth_user_role_user_id (user_id),
    KEY idx_auth_user_role_role_id (role_id)
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
    KEY idx_auth_role_resource_tenant_id (tenant_id),
    KEY idx_auth_role_resource_role_id (role_id),
    KEY idx_auth_role_resource_resource_id (resource_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='认证角色资源关系表';

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
    KEY idx_auth_role_data_scope_tenant_id (tenant_id),
    KEY idx_auth_role_data_scope_role_id (role_id),
    KEY idx_auth_role_data_scope_dept_id (dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色自定义数据范围表';

INSERT IGNORE INTO auth_tenant
(id, name, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('100', '默认租户', 'default', '系统默认租户', 'system', 'system', 1, 1, '100'),
('200', '测试租户', 'test-org', '租户隔离测试租户', 'system', 'system', 1, 1, '200');

INSERT IGNORE INTO auth_dept
(id, name, parent_id, owner_user_id, code, description, create_name, modify_name, sorting, state, version, tenant_id)
VALUES
('dept_root_100', '默认租户总部', NULL, 'u_admin_100', 'root', '默认租户根部门', 'system', 'system', 1, 1, 1, '100'),
('dept_tech_100', '技术部', 'dept_root_100', 'u_admin_100', 'tech', '数据权限测试部门', 'system', 'system', 10, 1, 1, '100'),
('dept_backend_100', '后端组', 'dept_tech_100', 'u_admin_100', 'backend', '数据权限测试部门', 'system', 'system', 11, 1, 1, '100'),
('dept_frontend_100', '前端组', 'dept_tech_100', 'u_admin_100', 'frontend', '数据权限测试部门', 'system', 'system', 12, 1, 1, '100'),
('dept_finance_100', '财务部', 'dept_root_100', 'u_admin_100', 'finance', '数据权限测试部门', 'system', 'system', 20, 1, 1, '100'),
('dept_hr_100', '人事部', 'dept_root_100', 'u_admin_100', 'hr', '数据权限测试部门', 'system', 'system', 30, 1, 1, '100'),
('dept_root_200', '测试租户总部', NULL, 'u_scope_all_200', 'root', '租户隔离测试根部门', 'system', 'system', 1, 1, 1, '200'),
('dept_tech_200', '测试租户技术部', 'dept_root_200', 'u_scope_all_200', 'tech', '租户隔离测试部门', 'system', 'system', 10, 1, 1, '200'),
('dept_finance_200', '测试租户财务部', 'dept_root_200', 'u_scope_all_200', 'finance', '租户隔离测试部门', 'system', 'system', 20, 1, 1, '200');

INSERT IGNORE INTO auth_user
(id, username, password, nickname, admin_type, dept_id, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('u_admin_100', 'admin', '$2a$10$WzvrHmwWViUXVzyNaez6ROLEEg49ATB68RCjSWFai5MlyWojAbraG', '管理员', 'PLATFORM_SUPER_ADMIN', 'dept_root_100', 'admin', '默认管理员账号，密码123456', 'system', 'system', 1, 1, '100'),
('u_scope_all_100', 'org_all', '$2a$10$WzvrHmwWViUXVzyNaez6ROLEEg49ATB68RCjSWFai5MlyWojAbraG', '机构测试-全部数据', 'TENANT_ADMIN', 'dept_root_100', 'org_all', '数据权限测试账号，密码123456', 'system', 'system', 1, 1, '100'),
('u_scope_self_100', 'org_self', '$2a$10$WzvrHmwWViUXVzyNaez6ROLEEg49ATB68RCjSWFai5MlyWojAbraG', '机构测试-仅本人数据', 'TENANT_ADMIN', 'dept_tech_100', 'org_self', '数据权限测试账号，密码123456', 'system', 'system', 1, 1, '100'),
('u_scope_dept_100', 'org_dept', '$2a$10$WzvrHmwWViUXVzyNaez6ROLEEg49ATB68RCjSWFai5MlyWojAbraG', '机构测试-本部门数据', 'TENANT_ADMIN', 'dept_tech_100', 'org_dept', '数据权限测试账号，密码123456', 'system', 'system', 1, 1, '100'),
('u_scope_tree_100', 'org_tree', '$2a$10$WzvrHmwWViUXVzyNaez6ROLEEg49ATB68RCjSWFai5MlyWojAbraG', '机构测试-部门树数据', 'TENANT_ADMIN', 'dept_tech_100', 'org_tree', '数据权限测试账号，密码123456', 'system', 'system', 1, 1, '100'),
('u_scope_custom_100', 'org_custom', '$2a$10$WzvrHmwWViUXVzyNaez6ROLEEg49ATB68RCjSWFai5MlyWojAbraG', '机构测试-自定义部门', 'TENANT_ADMIN', 'dept_finance_100', 'org_custom', '数据权限测试账号，密码123456', 'system', 'system', 1, 1, '100'),
('u_scope_all_200', 'org200_all', '$2a$10$WzvrHmwWViUXVzyNaez6ROLEEg49ATB68RCjSWFai5MlyWojAbraG', '测试租户-全部数据', 'TENANT_ADMIN', 'dept_root_200', 'org200_all', '租户隔离测试账号，密码123456', 'system', 'system', 1, 1, '200');

INSERT IGNORE INTO auth_user_tenant
(id, user_id, relation_tenant_id, dept_id, default_tenant, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('ut_admin_100', 'u_admin_100', '100', 'dept_root_100', b'1', 'u_admin_100:100', '默认管理员租户关系', 'system', 'system', 1, 1, '100'),
('ut_scope_all_100', 'u_scope_all_100', '100', 'dept_root_100', b'1', 'u_scope_all_100:100', '数据权限测试用户租户关系', 'system', 'system', 1, 1, '100'),
('ut_scope_self_100', 'u_scope_self_100', '100', 'dept_tech_100', b'1', 'u_scope_self_100:100', '数据权限测试用户租户关系', 'system', 'system', 1, 1, '100'),
('ut_scope_dept_100', 'u_scope_dept_100', '100', 'dept_tech_100', b'1', 'u_scope_dept_100:100', '数据权限测试用户租户关系', 'system', 'system', 1, 1, '100'),
('ut_scope_tree_100', 'u_scope_tree_100', '100', 'dept_tech_100', b'1', 'u_scope_tree_100:100', '数据权限测试用户租户关系', 'system', 'system', 1, 1, '100'),
('ut_scope_custom_100', 'u_scope_custom_100', '100', 'dept_finance_100', b'1', 'u_scope_custom_100:100', '数据权限测试用户租户关系', 'system', 'system', 1, 1, '100'),
('ut_scope_all_200', 'u_scope_all_200', '200', 'dept_root_200', b'1', 'u_scope_all_200:200', '租户隔离测试用户租户关系', 'system', 'system', 1, 1, '200');

INSERT IGNORE INTO auth_role
(id, name, data_scope, owner_user_id, dept_id, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('r_admin_100', '管理员', 'ALL', 'u_admin_100', 'dept_root_100', 'admin', '默认管理员角色', 'system', 'system', 1, 1, '100'),
('r_scope_all_100', '测试-全部数据', 'ALL', 'u_admin_100', 'dept_root_100', 'scope_all', '数据权限测试角色：全部数据', 'system', 'system', 1, 1, '100'),
('r_scope_self_100', '测试-仅本人数据', 'SELF', 'u_scope_self_100', 'dept_tech_100', 'scope_self', '数据权限测试角色：仅本人数据', 'system', 'system', 1, 1, '100'),
('r_scope_dept_100', '测试-本部门数据', 'DEPT', 'u_scope_dept_100', 'dept_tech_100', 'scope_dept', '数据权限测试角色：本部门数据', 'system', 'system', 1, 1, '100'),
('r_scope_dept_tree_100', '测试-本部门及下级', 'DEPT_TREE', 'u_scope_tree_100', 'dept_tech_100', 'scope_dept_tree', '数据权限测试角色：本部门及下级部门数据', 'system', 'system', 1, 1, '100'),
('r_scope_custom_100', '测试-自定义部门', 'CUSTOM', 'u_scope_custom_100', 'dept_finance_100', 'scope_custom', '数据权限测试角色：自定义部门数据', 'system', 'system', 1, 1, '100'),
('r_scope_all_200', '测试租户-全部数据', 'ALL', 'u_scope_all_200', 'dept_root_200', 'scope_all', '租户隔离测试角色：全部数据', 'system', 'system', 1, 1, '200');

INSERT IGNORE INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description, create_name, modify_name, sorting, state, version, tenant_id)
VALUES
('res_root_system_100', '系统管理', 'FRONTEND', '/system', NULL, NULL, 'menu:system', '前端系统管理根菜单', 'system', 'system', 1, 1, 1, '100'),
('res_menu_tenant_100', '租户管理', 'FRONTEND', '/system/tenant', NULL, 'res_root_system_100', 'menu:tenant', '前端租户菜单', 'system', 'system', 10, 1, 1, '100'),
('res_menu_dept_100', '部门管理', 'FRONTEND', '/system/dept', NULL, 'res_root_system_100', 'menu:dept', '前端部门菜单', 'system', 'system', 15, 1, 1, '100'),
('res_menu_user_100', '用户管理', 'FRONTEND', '/system/user', NULL, 'res_root_system_100', 'menu:user', '前端用户菜单', 'system', 'system', 20, 1, 1, '100'),
('res_menu_role_100', '角色管理', 'FRONTEND', '/system/role', NULL, 'res_root_system_100', 'menu:role', '前端角色菜单', 'system', 'system', 30, 1, 1, '100'),
('res_menu_role_bind_100', '角色资源授权', 'FRONTEND', '/system/role#bind-resource', NULL, 'res_menu_role_100', 'menu:role:bind-resource', '前端角色资源授权按钮示例', 'system', 'system', 31, 1, 1, '100'),
('res_menu_role_data_scope_100', '角色数据范围', 'FRONTEND', '/system/role#data-scope', NULL, 'res_menu_role_100', 'menu:role:data-scope', '前端角色数据范围授权按钮示例', 'system', 'system', 32, 1, 1, '100'),
('res_menu_resource_100', '权限资源', 'FRONTEND', '/system/resource', NULL, 'res_root_system_100', 'menu:resource', '前端权限资源菜单', 'system', 'system', 40, 1, 1, '100'),
('res_menu_resource_tree_100', '资源树维护', 'FRONTEND', '/system/resource#tree', NULL, 'res_menu_resource_100', 'menu:resource:tree', '前端资源树维护按钮示例', 'system', 'system', 41, 1, 1, '100'),
('res_api_auth_root_100', '认证接口', 'BACKEND', '/auth', '*', NULL, 'api:auth', '认证授权接口根节点', 'system', 'system', 1, 1, 1, '100'),
('res_api_auth_resource_100', '当前资源列表', 'BACKEND', '/auth/current/resources', 'GET', 'res_api_auth_root_100', 'user:auth:resources', '当前用户资源接口权限', 'system', 'system', 10, 1, 1, '100'),
('res_api_auth_manage_100', '认证体系维护', 'BACKEND', '/auth/manage/**', '*', 'res_api_auth_root_100', 'user:auth:manage', '认证体系维护接口权限', 'system', 'system', 20, 1, 1, '100'),
('res_api_code_generate_100', '编码自动生成', 'BACKEND', '/auth/manage/codes', 'POST', 'res_api_auth_manage_100', 'user:auth:code-generate', '业务编码自动生成接口权限', 'system', 'system', 21, 1, 1, '100'),
('res_api_resource_tree_100', '资源树查询', 'BACKEND', '/auth/manage/resources', 'GET', 'res_api_auth_manage_100', 'user:auth:resource-tree', '资源树查询接口权限示例', 'system', 'system', 22, 1, 1, '100'),
('res_root_system_200', '系统管理', 'FRONTEND', '/system', NULL, NULL, 'menu:system', '测试租户前端系统管理根菜单', 'system', 'system', 1, 1, 1, '200'),
('res_menu_dept_200', '部门管理', 'FRONTEND', '/system/dept', NULL, 'res_root_system_200', 'menu:dept', '测试租户前端部门菜单', 'system', 'system', 15, 1, 1, '200'),
('res_menu_user_200', '用户管理', 'FRONTEND', '/system/user', NULL, 'res_root_system_200', 'menu:user', '测试租户前端用户菜单', 'system', 'system', 20, 1, 1, '200'),
('res_menu_role_200', '角色管理', 'FRONTEND', '/system/role', NULL, 'res_root_system_200', 'menu:role', '测试租户前端角色菜单', 'system', 'system', 30, 1, 1, '200'),
('res_menu_resource_200', '权限资源', 'FRONTEND', '/system/resource', NULL, 'res_root_system_200', 'menu:resource', '测试租户前端权限资源菜单', 'system', 'system', 40, 1, 1, '200'),
('res_api_auth_resource_200', '当前资源列表', 'BACKEND', '/auth/current/resources', 'GET', NULL, 'user:auth:resources', '测试租户当前用户资源接口权限', 'system', 'system', 10, 1, 1, '200'),
('res_api_auth_manage_200', '认证体系维护', 'BACKEND', '/auth/manage/**', '*', NULL, 'user:auth:manage', '测试租户认证体系维护接口权限', 'system', 'system', 20, 1, 1, '200');

INSERT IGNORE INTO auth_user_role
(id, user_id, role_id, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('ur_admin_100', 'u_admin_100', 'r_admin_100', 'u_admin_100:r_admin_100', '默认管理员用户角色关系', 'system', 'system', 1, 1, '100'),
('ur_scope_all_100', 'u_scope_all_100', 'r_scope_all_100', 'u_scope_all_100:r_scope_all_100', '数据权限测试用户角色关系', 'system', 'system', 1, 1, '100'),
('ur_scope_self_100', 'u_scope_self_100', 'r_scope_self_100', 'u_scope_self_100:r_scope_self_100', '数据权限测试用户角色关系', 'system', 'system', 1, 1, '100'),
('ur_scope_dept_100', 'u_scope_dept_100', 'r_scope_dept_100', 'u_scope_dept_100:r_scope_dept_100', '数据权限测试用户角色关系', 'system', 'system', 1, 1, '100'),
('ur_scope_tree_100', 'u_scope_tree_100', 'r_scope_dept_tree_100', 'u_scope_tree_100:r_scope_dept_tree_100', '数据权限测试用户角色关系', 'system', 'system', 1, 1, '100'),
('ur_scope_custom_100', 'u_scope_custom_100', 'r_scope_custom_100', 'u_scope_custom_100:r_scope_custom_100', '数据权限测试用户角色关系', 'system', 'system', 1, 1, '100'),
('ur_scope_all_200', 'u_scope_all_200', 'r_scope_all_200', 'u_scope_all_200:r_scope_all_200', '租户隔离测试用户角色关系', 'system', 'system', 1, 1, '200');

INSERT IGNORE INTO auth_role_data_scope
(id, role_id, dept_id, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('rds_scope_custom_backend_100', 'r_scope_custom_100', 'dept_backend_100', 'r_scope_custom_100:dept_backend_100', '自定义数据范围测试关系', 'system', 'system', 1, 1, '100'),
('rds_scope_custom_finance_100', 'r_scope_custom_100', 'dept_finance_100', 'r_scope_custom_100:dept_finance_100', '自定义数据范围测试关系', 'system', 'system', 1, 1, '100');

INSERT IGNORE INTO auth_role_resource
(id, role_id, resource_id, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('rr_admin_root_system_100', 'r_admin_100', 'res_root_system_100', 'r_admin_100:res_root_system_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_tenant_100', 'r_admin_100', 'res_menu_tenant_100', 'r_admin_100:res_menu_tenant_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_dept_100', 'r_admin_100', 'res_menu_dept_100', 'r_admin_100:res_menu_dept_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_user_100', 'r_admin_100', 'res_menu_user_100', 'r_admin_100:res_menu_user_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_role_100', 'r_admin_100', 'res_menu_role_100', 'r_admin_100:res_menu_role_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_role_bind_100', 'r_admin_100', 'res_menu_role_bind_100', 'r_admin_100:res_menu_role_bind_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_role_data_scope_100', 'r_admin_100', 'res_menu_role_data_scope_100', 'r_admin_100:res_menu_role_data_scope_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_resource_100', 'r_admin_100', 'res_menu_resource_100', 'r_admin_100:res_menu_resource_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_resource_tree_100', 'r_admin_100', 'res_menu_resource_tree_100', 'r_admin_100:res_menu_resource_tree_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_auth_root_100', 'r_admin_100', 'res_api_auth_root_100', 'r_admin_100:res_api_auth_root_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_auth_resource_100', 'r_admin_100', 'res_api_auth_resource_100', 'r_admin_100:res_api_auth_resource_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_auth_manage_100', 'r_admin_100', 'res_api_auth_manage_100', 'r_admin_100:res_api_auth_manage_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_code_generate_100', 'r_admin_100', 'res_api_code_generate_100', 'r_admin_100:res_api_code_generate_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_resource_tree_100', 'r_admin_100', 'res_api_resource_tree_100', 'r_admin_100:res_api_resource_tree_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_all_root_100', 'r_scope_all_100', 'res_root_system_100', 'r_scope_all_100:res_root_system_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_self_root_100', 'r_scope_self_100', 'res_root_system_100', 'r_scope_self_100:res_root_system_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_dept_root_100', 'r_scope_dept_100', 'res_root_system_100', 'r_scope_dept_100:res_root_system_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_tree_root_100', 'r_scope_dept_tree_100', 'res_root_system_100', 'r_scope_dept_tree_100:res_root_system_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_custom_root_100', 'r_scope_custom_100', 'res_root_system_100', 'r_scope_custom_100:res_root_system_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_all_root_200', 'r_scope_all_200', 'res_root_system_200', 'r_scope_all_200:res_root_system_200', '租户隔离测试角色资源关系', 'system', 'system', 1, 1, '200'),
('rr_scope_all_api_manage_200', 'r_scope_all_200', 'res_api_auth_manage_200', 'r_scope_all_200:res_api_auth_manage_200', '租户隔离测试角色资源关系', 'system', 'system', 1, 1, '200');

INSERT IGNORE INTO auth_role_resource
(id, role_id, resource_id, code, description, create_name, modify_name, state, version, tenant_id)
SELECT
    CONCAT('rr_', role.id, '_', resource.id),
    role.id,
    resource.id,
    CONCAT(role.id, ':', resource.id),
    '数据权限测试角色资源关系',
    'system',
    'system',
    1,
    1,
    role.tenant_id
FROM auth_role role
JOIN auth_resource resource ON resource.tenant_id = role.tenant_id
WHERE role.id IN (
    'r_scope_all_100',
    'r_scope_self_100',
    'r_scope_dept_100',
    'r_scope_dept_tree_100',
    'r_scope_custom_100',
    'r_scope_all_200'
)
  AND resource.id IN (
    'res_root_system_100',
    'res_menu_dept_100',
    'res_menu_user_100',
    'res_menu_role_100',
    'res_menu_resource_100',
    'res_api_auth_resource_100',
    'res_api_auth_manage_100',
    'res_root_system_200',
    'res_menu_dept_200',
    'res_menu_user_200',
    'res_menu_role_200',
    'res_menu_resource_200',
    'res_api_auth_resource_200',
    'res_api_auth_manage_200'
  );
