INSERT IGNORE INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description, create_name, modify_name, sorting, state, version, tenant_id)
VALUES
('res_root_system_100', '系统管理', 'FRONTEND', '/system', NULL, NULL, 'menu:system', '前端系统管理根菜单', 'system', 'system', 1, 1, 1, '100'),
('res_menu_role_bind_100', '角色资源授权', 'FRONTEND', '/system/role#bind-resource', NULL, 'res_menu_role_100', 'menu:role:bind-resource', '前端角色资源授权按钮示例', 'system', 'system', 31, 1, 1, '100'),
('res_menu_resource_tree_100', '资源树维护', 'FRONTEND', '/system/resource#tree', NULL, 'res_menu_resource_100', 'menu:resource:tree', '前端资源树维护按钮示例', 'system', 'system', 41, 1, 1, '100'),
('res_api_auth_root_100', '认证接口', 'BACKEND', '/auth', '*', NULL, 'api:auth', '认证授权接口根节点', 'system', 'system', 1, 1, 1, '100'),
('res_api_code_generate_100', '编码自动生成', 'BACKEND', '/auth/manage/codes/generate', 'GET', 'res_api_auth_manage_100', 'user:auth:code-generate', '业务编码自动生成接口权限', 'system', 'system', 21, 1, 1, '100'),
('res_api_resource_tree_100', '资源树查询', 'BACKEND', '/auth/manage/resources', 'GET', 'res_api_auth_manage_100', 'user:auth:resource-tree', '资源树查询接口权限示例', 'system', 'system', 22, 1, 1, '100');

UPDATE auth_resource
SET parent_id = 'res_root_system_100'
WHERE tenant_id = '100'
  AND id IN ('res_menu_tenant_100', 'res_menu_user_100', 'res_menu_role_100', 'res_menu_resource_100');

UPDATE auth_resource
SET parent_id = 'res_api_auth_root_100'
WHERE tenant_id = '100'
  AND id IN ('res_api_auth_resource_100', 'res_api_auth_manage_100');

INSERT IGNORE INTO auth_role_resource
(id, role_id, resource_id, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('rr_admin_root_system_100', 'r_admin_100', 'res_root_system_100', 'r_admin_100:res_root_system_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_role_bind_100', 'r_admin_100', 'res_menu_role_bind_100', 'r_admin_100:res_menu_role_bind_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_resource_tree_100', 'r_admin_100', 'res_menu_resource_tree_100', 'r_admin_100:res_menu_resource_tree_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_auth_root_100', 'r_admin_100', 'res_api_auth_root_100', 'r_admin_100:res_api_auth_root_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_code_generate_100', 'r_admin_100', 'res_api_code_generate_100', 'r_admin_100:res_api_code_generate_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_resource_tree_100', 'r_admin_100', 'res_api_resource_tree_100', 'r_admin_100:res_api_resource_tree_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100');
