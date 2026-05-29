INSERT IGNORE INTO auth_tenant
(id, name, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('100', '默认租户', 'default', '系统默认租户', 'system', 'system', 1, 1, '100'),
('200', '测试租户', 'test-org', '租户隔离测试租户', 'system', 'system', 1, 1, '200');

UPDATE auth_tenant
SET is_delete = b'0', state = 1
WHERE id IN ('100', '200');

INSERT IGNORE INTO auth_dept
(id, name, parent_id, code, description, create_name, modify_name, sorting, state, version, tenant_id)
VALUES
('dept_root_100', '默认租户总部', NULL, 'root', '默认租户根部门', 'system', 'system', 1, 1, 1, '100');

UPDATE auth_dept
SET is_delete = b'0', state = 1
WHERE tenant_id = '100'
  AND id = 'dept_root_100';

UPDATE auth_user
SET dept_id = 'dept_root_100', is_delete = b'0', state = 1
WHERE tenant_id = '100'
  AND id = 'u_admin_100';

UPDATE auth_role
SET data_scope = 'ALL', is_delete = b'0', state = 1
WHERE tenant_id = '100'
  AND id = 'r_admin_100';

INSERT IGNORE INTO auth_user_role
(id, user_id, role_id, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('ur_admin_100', 'u_admin_100', 'r_admin_100', 'u_admin_100:r_admin_100', '默认管理员用户角色关系', 'system', 'system', 1, 1, '100');

UPDATE auth_user_role
SET is_delete = b'0', state = 1
WHERE tenant_id = '100'
  AND user_id = 'u_admin_100'
  AND role_id = 'r_admin_100';

INSERT IGNORE INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description, create_name, modify_name, sorting, state, version, tenant_id)
VALUES
('res_root_system_100', '系统管理', 'FRONTEND', '/system', NULL, NULL, 'menu:system', '前端系统管理根菜单', 'system', 'system', 1, 1, 1, '100'),
('res_menu_tenant_100', '租户管理', 'FRONTEND', '/system/tenant', NULL, 'res_root_system_100', 'menu:tenant', '前端租户菜单', 'system', 'system', 10, 1, 1, '100'),
('res_menu_dept_100', '部门管理', 'FRONTEND', '/system/dept', NULL, 'res_root_system_100', 'menu:dept', '前端部门菜单', 'system', 'system', 15, 1, 1, '100'),
('res_menu_user_100', '用户管理', 'FRONTEND', '/system/user', NULL, 'res_root_system_100', 'menu:user', '前端用户菜单', 'system', 'system', 20, 1, 1, '100'),
('res_menu_role_100', '角色管理', 'FRONTEND', '/system/role', NULL, 'res_root_system_100', 'menu:role', '前端角色菜单', 'system', 'system', 30, 1, 1, '100'),
('res_menu_resource_100', '权限资源', 'FRONTEND', '/system/resource', NULL, 'res_root_system_100', 'menu:resource', '前端权限资源菜单', 'system', 'system', 40, 1, 1, '100'),
('res_api_auth_resource_100', '当前资源列表', 'BACKEND', '/auth/resources', 'GET', NULL, 'user:auth:resources', '当前用户资源接口权限', 'system', 'system', 10, 1, 1, '100'),
('res_api_auth_manage_100', '认证体系维护', 'BACKEND', '/auth/manage/**', '*', NULL, 'user:auth:manage', '认证体系维护接口权限', 'system', 'system', 20, 1, 1, '100'),
('res_api_code_generate_100', '编码自动生成', 'BACKEND', '/auth/manage/codes/generate', 'GET', 'res_api_auth_manage_100', 'user:auth:code-generate', '业务编码自动生成接口权限', 'system', 'system', 21, 1, 1, '100'),
('res_api_resource_tree_100', '资源树查询', 'BACKEND', '/auth/manage/resources', 'GET', 'res_api_auth_manage_100', 'user:auth:resource-tree', '资源树查询接口权限示例', 'system', 'system', 22, 1, 1, '100');

UPDATE auth_resource
SET is_delete = b'0', state = 1
WHERE tenant_id = '100'
  AND id IN (
      'res_root_system_100',
      'res_menu_tenant_100',
      'res_menu_dept_100',
      'res_menu_user_100',
      'res_menu_role_100',
      'res_menu_resource_100',
      'res_api_auth_resource_100',
      'res_api_auth_manage_100',
      'res_api_code_generate_100',
      'res_api_resource_tree_100'
  );

INSERT IGNORE INTO auth_role_resource
(id, role_id, resource_id, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('rr_admin_root_system_100', 'r_admin_100', 'res_root_system_100', 'r_admin_100:res_root_system_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_tenant_100', 'r_admin_100', 'res_menu_tenant_100', 'r_admin_100:res_menu_tenant_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_dept_100', 'r_admin_100', 'res_menu_dept_100', 'r_admin_100:res_menu_dept_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_user_100', 'r_admin_100', 'res_menu_user_100', 'r_admin_100:res_menu_user_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_role_100', 'r_admin_100', 'res_menu_role_100', 'r_admin_100:res_menu_role_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_resource_100', 'r_admin_100', 'res_menu_resource_100', 'r_admin_100:res_menu_resource_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_auth_resource_100', 'r_admin_100', 'res_api_auth_resource_100', 'r_admin_100:res_api_auth_resource_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_auth_manage_100', 'r_admin_100', 'res_api_auth_manage_100', 'r_admin_100:res_api_auth_manage_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_code_generate_100', 'r_admin_100', 'res_api_code_generate_100', 'r_admin_100:res_api_code_generate_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_resource_tree_100', 'r_admin_100', 'res_api_resource_tree_100', 'r_admin_100:res_api_resource_tree_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100');

UPDATE auth_role_resource
SET is_delete = b'0', state = 1
WHERE tenant_id = '100'
  AND role_id = 'r_admin_100'
  AND resource_id IN (
      'res_root_system_100',
      'res_menu_tenant_100',
      'res_menu_dept_100',
      'res_menu_user_100',
      'res_menu_role_100',
      'res_menu_resource_100',
      'res_api_auth_resource_100',
      'res_api_auth_manage_100',
      'res_api_code_generate_100',
      'res_api_resource_tree_100'
  );
