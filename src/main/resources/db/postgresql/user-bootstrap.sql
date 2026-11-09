-- 仅补齐缺失的默认租户、角色与权限；不创建账号、密码或覆盖已有权限。
-- 迁移已有用户库时保留原密码、权限关系和自定义标签。

INSERT INTO auth_tenant
(id, name, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('100', '默认租户', 'default', '系统默认租户', 'system', 'system', 1, 1, '100')
ON CONFLICT DO NOTHING;

INSERT INTO auth_role
(id, name, data_scope, owner_user_id, dept_id, code, description, create_name, modify_name, state, version, tenant_id)
VALUES
('r_admin_100', '管理员', 'ALL', NULL, NULL, 'admin', '默认管理员角色', 'system', 'system', 1, 1, '100')
ON CONFLICT DO NOTHING;

INSERT INTO auth_resource
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
('res_root_message_100', '消息管理', 'FRONTEND', '/message', NULL, NULL, 'menu:message', '前端消息管理根菜单', 'system', 'system', 50, 1, 1, '100'),
('res_menu_message_send_100', '消息发送', 'FRONTEND', '/message/send', NULL, 'res_root_message_100', 'menu:message:send', '前端消息发送菜单', 'system', 'system', 51, 1, 1, '100'),
('res_api_auth_root_100', '认证接口', 'BACKEND', '/auth', '*', NULL, 'api:auth', '认证授权接口根节点', 'system', 'system', 1, 1, 1, '100'),
('res_api_auth_resource_100', '当前资源列表', 'BACKEND', '/auth/current/resources', 'GET', 'res_api_auth_root_100', 'user:auth:resources', '当前用户资源接口权限', 'system', 'system', 10, 1, 1, '100'),
('res_api_auth_manage_100', '认证体系维护', 'BACKEND', '/auth/manage/**', '*', 'res_api_auth_root_100', 'user:auth:manage', '认证体系维护接口权限', 'system', 'system', 20, 1, 1, '100'),
('res_api_code_generate_100', '编码自动生成', 'BACKEND', '/auth/manage/codes', 'POST', 'res_api_auth_manage_100', 'user:auth:code-generate', '业务编码自动生成接口权限', 'system', 'system', 21, 1, 1, '100'),
('res_api_resource_tree_100', '资源树查询', 'BACKEND', '/auth/manage/resources', 'GET', 'res_api_auth_manage_100', 'user:auth:resource-tree', '资源树查询接口权限示例', 'system', 'system', 22, 1, 1, '100'),
('res_api_message_root_100', '消息接口', 'BACKEND', '/messages', '*', NULL, 'api:message', '消息服务接口根节点', 'system', 'system', 50, 1, 1, '100'),
('res_api_message_user_message_list_100', '用户消息查询', 'BACKEND', '/messages/user-messages', 'GET', 'res_api_message_root_100', 'message:user-message:list', '用户消息查询接口权限', 'system', 'system', 51, 1, 1, '100'),
('res_api_message_user_message_send_100', '用户消息发送', 'BACKEND', '/messages/user-messages', 'POST', 'res_api_message_root_100', 'message:user-message:send', '用户消息发送接口权限', 'system', 'system', 52, 1, 1, '100')
ON CONFLICT DO NOTHING;

INSERT INTO auth_role_resource
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
('rr_admin_root_message_100', 'r_admin_100', 'res_root_message_100', 'r_admin_100:res_root_message_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_menu_message_send_100', 'r_admin_100', 'res_menu_message_send_100', 'r_admin_100:res_menu_message_send_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_auth_root_100', 'r_admin_100', 'res_api_auth_root_100', 'r_admin_100:res_api_auth_root_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_auth_resource_100', 'r_admin_100', 'res_api_auth_resource_100', 'r_admin_100:res_api_auth_resource_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_auth_manage_100', 'r_admin_100', 'res_api_auth_manage_100', 'r_admin_100:res_api_auth_manage_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_code_generate_100', 'r_admin_100', 'res_api_code_generate_100', 'r_admin_100:res_api_code_generate_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_resource_tree_100', 'r_admin_100', 'res_api_resource_tree_100', 'r_admin_100:res_api_resource_tree_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_message_root_100', 'r_admin_100', 'res_api_message_root_100', 'r_admin_100:res_api_message_root_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_message_list_100', 'r_admin_100', 'res_api_message_user_message_list_100', 'r_admin_100:res_api_message_user_message_list_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100'),
('rr_admin_api_message_send_100', 'r_admin_100', 'res_api_message_user_message_send_100', 'r_admin_100:res_api_message_user_message_send_100', '默认管理员角色资源关系', 'system', 'system', 1, 1, '100')
ON CONFLICT DO NOTHING;

INSERT INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(encode(sha256(convert_to(CONCAT(tenant.id, ':menu:rag'), 'UTF8')), 'hex'), 32)),
    'RAG知识库',
    'FRONTEND',
    '/rag-knowledge',
    NULL,
    NULL,
    'menu:rag',
    '前端RAG知识库根菜单',
    'system',
    'system',
    70,
    1,
    1,
    tenant.id
FROM auth_tenant tenant
WHERE tenant.is_delete = false
ON CONFLICT DO NOTHING;

INSERT INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(encode(sha256(convert_to(CONCAT(tenant.id, ':menu:rag:document'), 'UTF8')), 'hex'), 32)),
    '文档向量管理',
    'FRONTEND',
    '/rag-knowledge/documents',
    NULL,
    parent.id,
    'menu:rag:document',
    '前端RAG文档向量管理菜单',
    'system',
    'system',
    71,
    1,
    1,
    tenant.id
FROM auth_tenant tenant
JOIN auth_resource parent
  ON parent.tenant_id = tenant.id
 AND parent.code = 'menu:rag'
WHERE tenant.is_delete = false
ON CONFLICT DO NOTHING;

INSERT INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(encode(sha256(convert_to(CONCAT(tenant.id, ':api:rag'), 'UTF8')), 'hex'), 32)),
    'RAG接口',
    'BACKEND',
    '/rag/api/rag',
    '*',
    NULL,
    'api:rag',
    'RAG服务接口根节点',
    'system',
    'system',
    70,
    1,
    1,
    tenant.id
FROM auth_tenant tenant
WHERE tenant.is_delete = false
ON CONFLICT DO NOTHING;

INSERT INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(encode(sha256(convert_to(CONCAT(tenant.id, ':rag:document:manage'), 'UTF8')), 'hex'), 32)),
    'RAG文档管理',
    'BACKEND',
    '/rag/api/rag/documents/**',
    '*',
    parent.id,
    'rag:document:manage',
    'RAG文档上传、列表、校验和停用权限',
    'system',
    'system',
    71,
    1,
    1,
    tenant.id
FROM auth_tenant tenant
JOIN auth_resource parent
  ON parent.tenant_id = tenant.id
 AND parent.code = 'api:rag'
WHERE tenant.is_delete = false
ON CONFLICT DO NOTHING;

INSERT INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(encode(sha256(convert_to(CONCAT(tenant.id, ':rag:retrieval:use'), 'UTF8')), 'hex'), 32)),
    'RAG知识检索',
    'BACKEND',
    '/rag/api/rag/retrievals',
    'POST',
    parent.id,
    'rag:retrieval:use',
    'RAG知识上下文检索权限',
    'system',
    'system',
    72,
    1,
    1,
    tenant.id
FROM auth_tenant tenant
JOIN auth_resource parent
  ON parent.tenant_id = tenant.id
 AND parent.code = 'api:rag'
WHERE tenant.is_delete = false
ON CONFLICT DO NOTHING;

INSERT INTO auth_role_resource
(id, role_id, resource_id, code, description, create_name, modify_name,
 state, version, tenant_id)
SELECT
    CONCAT('rr_', LEFT(encode(sha256(convert_to(CONCAT(role.id, ':', rag_resource.id), 'UTF8')), 'hex'), 32)),
    role.id,
    rag_resource.id,
    CONCAT(role.id, ':', rag_resource.id),
    'RAG权限资源关系',
    'system',
    'system',
    1,
    1,
    role.tenant_id
FROM auth_role role
JOIN auth_role_resource manage_grant
  ON manage_grant.tenant_id = role.tenant_id
 AND manage_grant.role_id = role.id
 AND manage_grant.is_delete = false
 AND manage_grant.state = 1
JOIN auth_resource manage_resource
  ON manage_resource.tenant_id = role.tenant_id
 AND manage_resource.id = manage_grant.resource_id
 AND manage_resource.code = 'user:auth:manage'
 AND manage_resource.is_delete = false
 AND manage_resource.state = 1
JOIN auth_resource rag_resource
  ON rag_resource.tenant_id = role.tenant_id
 AND rag_resource.code IN (
     'menu:rag',
     'menu:rag:document',
     'api:rag',
     'rag:document:manage',
     'rag:retrieval:use'
 )
 AND rag_resource.is_delete = false
 AND rag_resource.state = 1
WHERE role.is_delete = false
  AND role.state = 1
ON CONFLICT DO NOTHING;

INSERT INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(encode(sha256(convert_to(CONCAT(tenant.id, ':menu:rag:graph'), 'UTF8')), 'hex'), 32)),
    '知识图谱',
    'FRONTEND',
    '/rag-knowledge/graph',
    NULL,
    parent.id,
    'menu:rag:graph',
    '前端RAG知识图谱菜单',
    'system',
    'system',
    72,
    1,
    1,
    tenant.id
FROM auth_tenant tenant
JOIN auth_resource parent
  ON parent.tenant_id = tenant.id
 AND parent.code = 'menu:rag'
 AND parent.is_delete = false
WHERE tenant.is_delete = false
ON CONFLICT DO NOTHING;

INSERT INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(encode(sha256(convert_to(CONCAT(tenant.id, ':rag:graph:view'), 'UTF8')), 'hex'), 32)),
    'RAG图谱查看',
    'BACKEND',
    '/rag/api/rag/graphs',
    'GET',
    parent.id,
    'rag:graph:view',
    'RAG知识图谱查询权限',
    'system',
    'system',
    73,
    1,
    1,
    tenant.id
FROM auth_tenant tenant
JOIN auth_resource parent
  ON parent.tenant_id = tenant.id
 AND parent.code = 'api:rag'
 AND parent.is_delete = false
WHERE tenant.is_delete = false
ON CONFLICT DO NOTHING;

INSERT INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(encode(sha256(convert_to(CONCAT(tenant.id, ':rag:graph:manage'), 'UTF8')), 'hex'), 32)),
    'RAG图谱同步',
    'BACKEND',
    '/rag/api/rag/graphs/sync',
    'POST',
    parent.id,
    'rag:graph:manage',
    '从活动向量切片同步知识图谱权限',
    'system',
    'system',
    74,
    1,
    1,
    tenant.id
FROM auth_tenant tenant
JOIN auth_resource parent
  ON parent.tenant_id = tenant.id
 AND parent.code = 'api:rag'
 AND parent.is_delete = false
WHERE tenant.is_delete = false
ON CONFLICT DO NOTHING;

INSERT INTO auth_role_resource
(id, role_id, resource_id, code, description, create_name, modify_name,
 state, version, tenant_id)
SELECT
    CONCAT('rr_', LEFT(encode(sha256(convert_to(CONCAT(role.id, ':', graph_resource.id), 'UTF8')), 'hex'), 32)),
    role.id,
    graph_resource.id,
    CONCAT(role.id, ':', graph_resource.id),
    'RAG知识图谱权限资源关系',
    'system',
    'system',
    1,
    1,
    role.tenant_id
FROM auth_role role
JOIN auth_role_resource manage_grant
  ON manage_grant.tenant_id = role.tenant_id
 AND manage_grant.role_id = role.id
 AND manage_grant.is_delete = false
 AND manage_grant.state = 1
JOIN auth_resource manage_resource
  ON manage_resource.tenant_id = role.tenant_id
 AND manage_resource.id = manage_grant.resource_id
 AND manage_resource.code = 'user:auth:manage'
 AND manage_resource.is_delete = false
 AND manage_resource.state = 1
JOIN auth_resource graph_resource
  ON graph_resource.tenant_id = role.tenant_id
 AND graph_resource.code IN ('menu:rag:graph', 'rag:graph:view', 'rag:graph:manage')
 AND graph_resource.is_delete = false
 AND graph_resource.state = 1
WHERE role.is_delete = false
  AND role.state = 1
ON CONFLICT DO NOTHING;
