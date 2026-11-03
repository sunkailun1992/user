-- RAG 知识图谱权限资源增量迁移。
--
-- 不回改已执行的 RAG 权限脚本；本脚本为每个现有租户补齐图谱菜单、查看和同步权限，
-- 并只继承原本拥有 user:auth:manage 的管理员角色。确定性主键保证重复执行安全。

INSERT IGNORE INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(SHA2(CONCAT(tenant.id, ':menu:rag:graph'), 256), 32)),
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
 AND parent.is_delete = b'0'
WHERE tenant.is_delete = b'0';

UPDATE auth_resource resource
JOIN auth_resource parent
  ON parent.tenant_id = resource.tenant_id
 AND parent.code = 'menu:rag'
 AND parent.is_delete = b'0'
SET resource.name = '知识图谱',
    resource.resource_category = 'FRONTEND',
    resource.path = '/rag-knowledge/graph',
    resource.method = NULL,
    resource.parent_id = parent.id,
    resource.description = '前端RAG知识图谱菜单',
    resource.modify_date_time = CURRENT_TIMESTAMP,
    resource.modify_name = 'system',
    resource.is_delete = b'0',
    resource.sorting = 72,
    resource.state = 1
WHERE resource.code = 'menu:rag:graph';

INSERT IGNORE INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(SHA2(CONCAT(tenant.id, ':rag:graph:view'), 256), 32)),
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
 AND parent.is_delete = b'0'
WHERE tenant.is_delete = b'0';

UPDATE auth_resource resource
JOIN auth_resource parent
  ON parent.tenant_id = resource.tenant_id
 AND parent.code = 'api:rag'
 AND parent.is_delete = b'0'
SET resource.name = 'RAG图谱查看',
    resource.resource_category = 'BACKEND',
    resource.path = '/rag/api/rag/graphs',
    resource.method = 'GET',
    resource.parent_id = parent.id,
    resource.description = 'RAG知识图谱查询权限',
    resource.modify_date_time = CURRENT_TIMESTAMP,
    resource.modify_name = 'system',
    resource.is_delete = b'0',
    resource.sorting = 73,
    resource.state = 1
WHERE resource.code = 'rag:graph:view';

INSERT IGNORE INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(SHA2(CONCAT(tenant.id, ':rag:graph:manage'), 256), 32)),
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
 AND parent.is_delete = b'0'
WHERE tenant.is_delete = b'0';

UPDATE auth_resource resource
JOIN auth_resource parent
  ON parent.tenant_id = resource.tenant_id
 AND parent.code = 'api:rag'
 AND parent.is_delete = b'0'
SET resource.name = 'RAG图谱同步',
    resource.resource_category = 'BACKEND',
    resource.path = '/rag/api/rag/graphs/sync',
    resource.method = 'POST',
    resource.parent_id = parent.id,
    resource.description = '从活动向量切片同步知识图谱权限',
    resource.modify_date_time = CURRENT_TIMESTAMP,
    resource.modify_name = 'system',
    resource.is_delete = b'0',
    resource.sorting = 74,
    resource.state = 1
WHERE resource.code = 'rag:graph:manage';

INSERT IGNORE INTO auth_role_resource
(id, role_id, resource_id, code, description, create_name, modify_name,
 state, version, tenant_id)
SELECT
    CONCAT('rr_', LEFT(SHA2(CONCAT(role.id, ':', graph_resource.id), 256), 32)),
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
 AND manage_grant.is_delete = b'0'
 AND manage_grant.state = 1
JOIN auth_resource manage_resource
  ON manage_resource.tenant_id = role.tenant_id
 AND manage_resource.id = manage_grant.resource_id
 AND manage_resource.code = 'user:auth:manage'
 AND manage_resource.is_delete = b'0'
 AND manage_resource.state = 1
JOIN auth_resource graph_resource
  ON graph_resource.tenant_id = role.tenant_id
 AND graph_resource.code IN ('menu:rag:graph', 'rag:graph:view', 'rag:graph:manage')
 AND graph_resource.is_delete = b'0'
 AND graph_resource.state = 1
WHERE role.is_delete = b'0'
  AND role.state = 1;

UPDATE auth_role_resource role_resource
JOIN auth_role role
  ON role.tenant_id = role_resource.tenant_id
 AND role.id = role_resource.role_id
JOIN auth_role_resource manage_grant
  ON manage_grant.tenant_id = role.tenant_id
 AND manage_grant.role_id = role.id
 AND manage_grant.is_delete = b'0'
 AND manage_grant.state = 1
JOIN auth_resource manage_resource
  ON manage_resource.tenant_id = role.tenant_id
 AND manage_resource.id = manage_grant.resource_id
 AND manage_resource.code = 'user:auth:manage'
 AND manage_resource.is_delete = b'0'
 AND manage_resource.state = 1
JOIN auth_resource graph_resource
  ON graph_resource.tenant_id = role_resource.tenant_id
 AND graph_resource.id = role_resource.resource_id
 AND graph_resource.code IN ('menu:rag:graph', 'rag:graph:view', 'rag:graph:manage')
SET role_resource.description = 'RAG知识图谱权限资源关系',
    role_resource.modify_date_time = CURRENT_TIMESTAMP,
    role_resource.modify_name = 'system',
    role_resource.is_delete = b'0',
    role_resource.state = 1
WHERE role.is_delete = b'0'
  AND role.state = 1;
