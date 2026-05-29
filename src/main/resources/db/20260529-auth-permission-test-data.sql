INSERT IGNORE INTO auth_tenant
(id, name, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('200', '测试租户', 'test-org', '租户隔离测试租户', 'system', 'system', 1, 1, '200');

INSERT IGNORE INTO auth_dept
(id, name, parent_id, code, description, create_name, modify_name, sorting, state, version, tenant_id)
VALUES
('dept_tech_100', '技术部', 'dept_root_100', 'tech', '数据权限测试部门', 'system', 'system', 10, 1, 1, '100'),
('dept_backend_100', '后端组', 'dept_tech_100', 'backend', '数据权限测试部门', 'system', 'system', 11, 1, 1, '100'),
('dept_frontend_100', '前端组', 'dept_tech_100', 'frontend', '数据权限测试部门', 'system', 'system', 12, 1, 1, '100'),
('dept_finance_100', '财务部', 'dept_root_100', 'finance', '数据权限测试部门', 'system', 'system', 20, 1, 1, '100'),
('dept_hr_100', '人事部', 'dept_root_100', 'hr', '数据权限测试部门', 'system', 'system', 30, 1, 1, '100'),
('dept_root_200', '测试租户总部', NULL, 'root', '租户隔离测试根部门', 'system', 'system', 1, 1, 1, '200'),
('dept_tech_200', '测试租户技术部', 'dept_root_200', 'tech', '租户隔离测试部门', 'system', 'system', 10, 1, 1, '200'),
('dept_finance_200', '测试租户财务部', 'dept_root_200', 'finance', '租户隔离测试部门', 'system', 'system', 20, 1, 1, '200');

INSERT IGNORE INTO auth_role
(id, name, code, description, create_name, modify_name, data_scope, state, version, tenant_id)
VALUES
('r_scope_all_100', '测试-全部数据', 'scope_all', '数据权限测试角色：全部数据', 'system', 'system', 'ALL', 1, 1, '100'),
('r_scope_self_100', '测试-仅本人数据', 'scope_self', '数据权限测试角色：仅本人数据', 'system', 'system', 'SELF', 1, 1, '100'),
('r_scope_dept_100', '测试-本部门数据', 'scope_dept', '数据权限测试角色：本部门数据', 'system', 'system', 'DEPT', 1, 1, '100'),
('r_scope_dept_tree_100', '测试-本部门及下级', 'scope_dept_tree', '数据权限测试角色：本部门及下级部门数据', 'system', 'system', 'DEPT_TREE', 1, 1, '100'),
('r_scope_custom_100', '测试-自定义部门', 'scope_custom', '数据权限测试角色：自定义部门数据', 'system', 'system', 'CUSTOM', 1, 1, '100'),
('r_scope_all_200', '测试租户-全部数据', 'scope_all', '租户隔离测试角色：全部数据', 'system', 'system', 'ALL', 1, 1, '200');

INSERT IGNORE INTO auth_user
(id, username, password, nickname, code, description, create_name, modify_name, state, version, tenant_id, dept_id)
VALUES
('u_scope_all_100', 'org_all', '$2a$10$WzvrHmwWViUXVzyNaez6ROLEEg49ATB68RCjSWFai5MlyWojAbraG', '机构测试-全部数据', 'org_all', '数据权限测试账号，密码123456', 'system', 'system', 1, 1, '100', 'dept_root_100'),
('u_scope_self_100', 'org_self', '$2a$10$WzvrHmwWViUXVzyNaez6ROLEEg49ATB68RCjSWFai5MlyWojAbraG', '机构测试-仅本人数据', 'org_self', '数据权限测试账号，密码123456', 'system', 'system', 1, 1, '100', 'dept_tech_100'),
('u_scope_dept_100', 'org_dept', '$2a$10$WzvrHmwWViUXVzyNaez6ROLEEg49ATB68RCjSWFai5MlyWojAbraG', '机构测试-本部门数据', 'org_dept', '数据权限测试账号，密码123456', 'system', 'system', 1, 1, '100', 'dept_tech_100'),
('u_scope_tree_100', 'org_tree', '$2a$10$WzvrHmwWViUXVzyNaez6ROLEEg49ATB68RCjSWFai5MlyWojAbraG', '机构测试-部门树数据', 'org_tree', '数据权限测试账号，密码123456', 'system', 'system', 1, 1, '100', 'dept_tech_100'),
('u_scope_custom_100', 'org_custom', '$2a$10$WzvrHmwWViUXVzyNaez6ROLEEg49ATB68RCjSWFai5MlyWojAbraG', '机构测试-自定义部门', 'org_custom', '数据权限测试账号，密码123456', 'system', 'system', 1, 1, '100', 'dept_finance_100'),
('u_scope_all_200', 'org200_all', '$2a$10$WzvrHmwWViUXVzyNaez6ROLEEg49ATB68RCjSWFai5MlyWojAbraG', '测试租户-全部数据', 'org200_all', '租户隔离测试账号，密码123456', 'system', 'system', 1, 1, '200', 'dept_root_200');

INSERT IGNORE INTO auth_user_role
(id, user_id, role_id, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('ur_scope_all_100', 'u_scope_all_100', 'r_scope_all_100', 'u_scope_all_100:r_scope_all_100', '数据权限测试用户角色关系', 'system', 'system', 1, 1, '100'),
('ur_scope_self_100', 'u_scope_self_100', 'r_scope_self_100', 'u_scope_self_100:r_scope_self_100', '数据权限测试用户角色关系', 'system', 'system', 1, 1, '100'),
('ur_scope_dept_100', 'u_scope_dept_100', 'r_scope_dept_100', 'u_scope_dept_100:r_scope_dept_100', '数据权限测试用户角色关系', 'system', 'system', 1, 1, '100'),
('ur_scope_tree_100', 'u_scope_tree_100', 'r_scope_dept_tree_100', 'u_scope_tree_100:r_scope_dept_tree_100', '数据权限测试用户角色关系', 'system', 'system', 1, 1, '100'),
('ur_scope_custom_100', 'u_scope_custom_100', 'r_scope_custom_100', 'u_scope_custom_100:r_scope_custom_100', '数据权限测试用户角色关系', 'system', 'system', 1, 1, '100'),
('ur_scope_all_200', 'u_scope_all_200', 'r_scope_all_200', 'u_scope_all_200:r_scope_all_200', '租户隔离测试用户角色关系', 'system', 'system', 1, 1, '200');

INSERT IGNORE INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description, create_name, modify_name, sorting, state, version, tenant_id)
VALUES
('res_root_system_200', '系统管理', 'FRONTEND', '/system', NULL, NULL, 'menu:system', '测试租户前端系统管理根菜单', 'system', 'system', 1, 1, 1, '200'),
('res_menu_dept_200', '部门管理', 'FRONTEND', '/system/dept', NULL, 'res_root_system_200', 'menu:dept', '测试租户前端部门菜单', 'system', 'system', 15, 1, 1, '200'),
('res_menu_user_200', '用户管理', 'FRONTEND', '/system/user', NULL, 'res_root_system_200', 'menu:user', '测试租户前端用户菜单', 'system', 'system', 20, 1, 1, '200'),
('res_menu_role_200', '角色管理', 'FRONTEND', '/system/role', NULL, 'res_root_system_200', 'menu:role', '测试租户前端角色菜单', 'system', 'system', 30, 1, 1, '200'),
('res_menu_resource_200', '权限资源', 'FRONTEND', '/system/resource', NULL, 'res_root_system_200', 'menu:resource', '测试租户前端权限资源菜单', 'system', 'system', 40, 1, 1, '200'),
('res_api_auth_resource_200', '当前资源列表', 'BACKEND', '/auth/resources', 'GET', NULL, 'user:auth:resources', '测试租户当前用户资源接口权限', 'system', 'system', 10, 1, 1, '200'),
('res_api_auth_manage_200', '认证体系维护', 'BACKEND', '/auth/manage/**', '*', NULL, 'user:auth:manage', '测试租户认证体系维护接口权限', 'system', 'system', 20, 1, 1, '200');

INSERT IGNORE INTO auth_role_data_scope
(id, role_id, dept_id, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('rds_scope_custom_backend_100', 'r_scope_custom_100', 'dept_backend_100', 'r_scope_custom_100:dept_backend_100', '自定义数据范围测试关系', 'system', 'system', 1, 1, '100'),
('rds_scope_custom_finance_100', 'r_scope_custom_100', 'dept_finance_100', 'r_scope_custom_100:dept_finance_100', '自定义数据范围测试关系', 'system', 'system', 1, 1, '100');

INSERT IGNORE INTO auth_role_resource
(id, role_id, resource_id, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('rr_scope_all_root_100', 'r_scope_all_100', 'res_root_system_100', 'r_scope_all_100:res_root_system_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_all_dept_100', 'r_scope_all_100', 'res_menu_dept_100', 'r_scope_all_100:res_menu_dept_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_all_user_100', 'r_scope_all_100', 'res_menu_user_100', 'r_scope_all_100:res_menu_user_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_all_role_100', 'r_scope_all_100', 'res_menu_role_100', 'r_scope_all_100:res_menu_role_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_all_resource_100', 'r_scope_all_100', 'res_menu_resource_100', 'r_scope_all_100:res_menu_resource_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_all_api_res_100', 'r_scope_all_100', 'res_api_auth_resource_100', 'r_scope_all_100:res_api_auth_resource_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_all_api_manage_100', 'r_scope_all_100', 'res_api_auth_manage_100', 'r_scope_all_100:res_api_auth_manage_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_self_root_100', 'r_scope_self_100', 'res_root_system_100', 'r_scope_self_100:res_root_system_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_self_dept_100', 'r_scope_self_100', 'res_menu_dept_100', 'r_scope_self_100:res_menu_dept_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_self_user_100', 'r_scope_self_100', 'res_menu_user_100', 'r_scope_self_100:res_menu_user_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_self_role_100', 'r_scope_self_100', 'res_menu_role_100', 'r_scope_self_100:res_menu_role_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_self_resource_100', 'r_scope_self_100', 'res_menu_resource_100', 'r_scope_self_100:res_menu_resource_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_self_api_res_100', 'r_scope_self_100', 'res_api_auth_resource_100', 'r_scope_self_100:res_api_auth_resource_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_self_api_manage_100', 'r_scope_self_100', 'res_api_auth_manage_100', 'r_scope_self_100:res_api_auth_manage_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_dept_root_100', 'r_scope_dept_100', 'res_root_system_100', 'r_scope_dept_100:res_root_system_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_dept_dept_100', 'r_scope_dept_100', 'res_menu_dept_100', 'r_scope_dept_100:res_menu_dept_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_dept_user_100', 'r_scope_dept_100', 'res_menu_user_100', 'r_scope_dept_100:res_menu_user_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_dept_role_100', 'r_scope_dept_100', 'res_menu_role_100', 'r_scope_dept_100:res_menu_role_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_dept_resource_100', 'r_scope_dept_100', 'res_menu_resource_100', 'r_scope_dept_100:res_menu_resource_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_dept_api_res_100', 'r_scope_dept_100', 'res_api_auth_resource_100', 'r_scope_dept_100:res_api_auth_resource_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_dept_api_manage_100', 'r_scope_dept_100', 'res_api_auth_manage_100', 'r_scope_dept_100:res_api_auth_manage_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_tree_root_100', 'r_scope_dept_tree_100', 'res_root_system_100', 'r_scope_dept_tree_100:res_root_system_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_tree_dept_100', 'r_scope_dept_tree_100', 'res_menu_dept_100', 'r_scope_dept_tree_100:res_menu_dept_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_tree_user_100', 'r_scope_dept_tree_100', 'res_menu_user_100', 'r_scope_dept_tree_100:res_menu_user_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_tree_role_100', 'r_scope_dept_tree_100', 'res_menu_role_100', 'r_scope_dept_tree_100:res_menu_role_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_tree_resource_100', 'r_scope_dept_tree_100', 'res_menu_resource_100', 'r_scope_dept_tree_100:res_menu_resource_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_tree_api_res_100', 'r_scope_dept_tree_100', 'res_api_auth_resource_100', 'r_scope_dept_tree_100:res_api_auth_resource_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_tree_api_manage_100', 'r_scope_dept_tree_100', 'res_api_auth_manage_100', 'r_scope_dept_tree_100:res_api_auth_manage_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_custom_root_100', 'r_scope_custom_100', 'res_root_system_100', 'r_scope_custom_100:res_root_system_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_custom_dept_100', 'r_scope_custom_100', 'res_menu_dept_100', 'r_scope_custom_100:res_menu_dept_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_custom_user_100', 'r_scope_custom_100', 'res_menu_user_100', 'r_scope_custom_100:res_menu_user_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_custom_role_100', 'r_scope_custom_100', 'res_menu_role_100', 'r_scope_custom_100:res_menu_role_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_custom_resource_100', 'r_scope_custom_100', 'res_menu_resource_100', 'r_scope_custom_100:res_menu_resource_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_custom_api_res_100', 'r_scope_custom_100', 'res_api_auth_resource_100', 'r_scope_custom_100:res_api_auth_resource_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_custom_api_manage_100', 'r_scope_custom_100', 'res_api_auth_manage_100', 'r_scope_custom_100:res_api_auth_manage_100', '数据权限测试角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_scope_all_root_200', 'r_scope_all_200', 'res_root_system_200', 'r_scope_all_200:res_root_system_200', '租户隔离测试角色资源关系', 'system', 'system', 1, 1, '200'),
('rr_scope_all_dept_200', 'r_scope_all_200', 'res_menu_dept_200', 'r_scope_all_200:res_menu_dept_200', '租户隔离测试角色资源关系', 'system', 'system', 1, 1, '200'),
('rr_scope_all_user_200', 'r_scope_all_200', 'res_menu_user_200', 'r_scope_all_200:res_menu_user_200', '租户隔离测试角色资源关系', 'system', 'system', 1, 1, '200'),
('rr_scope_all_role_200', 'r_scope_all_200', 'res_menu_role_200', 'r_scope_all_200:res_menu_role_200', '租户隔离测试角色资源关系', 'system', 'system', 1, 1, '200'),
('rr_scope_all_resource_200', 'r_scope_all_200', 'res_menu_resource_200', 'r_scope_all_200:res_menu_resource_200', '租户隔离测试角色资源关系', 'system', 'system', 1, 1, '200'),
('rr_scope_all_api_res_200', 'r_scope_all_200', 'res_api_auth_resource_200', 'r_scope_all_200:res_api_auth_resource_200', '租户隔离测试角色资源关系', 'system', 'system', 1, 1, '200'),
('rr_scope_all_api_manage_200', 'r_scope_all_200', 'res_api_auth_manage_200', 'r_scope_all_200:res_api_auth_manage_200', '租户隔离测试角色资源关系', 'system', 'system', 1, 1, '200');
