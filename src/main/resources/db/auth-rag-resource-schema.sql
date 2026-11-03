-- RAG 权限资源增量迁移。
--
-- 该脚本为每个现有租户补齐 RAG 菜单和后端接口资源，并将资源授予原本已经拥有
-- user:auth:manage 的角色。迁移不依赖环境中的租户、用户、角色或资源主键，重复执行安全。

INSERT IGNORE INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(SHA2(CONCAT(tenant.id, ':menu:rag'), 256), 32)),
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
WHERE tenant.is_delete = b'0';

UPDATE auth_resource resource
SET resource.name = 'RAG知识库',
    resource.resource_category = 'FRONTEND',
    resource.path = '/rag-knowledge',
    resource.method = NULL,
    resource.parent_id = NULL,
    resource.description = '前端RAG知识库根菜单',
    resource.modify_date_time = CURRENT_TIMESTAMP,
    resource.modify_name = 'system',
    resource.is_delete = b'0',
    resource.sorting = 70,
    resource.state = 1
WHERE resource.code = 'menu:rag';

INSERT IGNORE INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(SHA2(CONCAT(tenant.id, ':menu:rag:document'), 256), 32)),
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
WHERE tenant.is_delete = b'0';

UPDATE auth_resource resource
JOIN auth_resource parent
  ON parent.tenant_id = resource.tenant_id
 AND parent.code = 'menu:rag'
SET resource.name = '文档向量管理',
    resource.resource_category = 'FRONTEND',
    resource.path = '/rag-knowledge/documents',
    resource.method = NULL,
    resource.parent_id = parent.id,
    resource.description = '前端RAG文档向量管理菜单',
    resource.modify_date_time = CURRENT_TIMESTAMP,
    resource.modify_name = 'system',
    resource.is_delete = b'0',
    resource.sorting = 71,
    resource.state = 1
WHERE resource.code = 'menu:rag:document';

INSERT IGNORE INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(SHA2(CONCAT(tenant.id, ':api:rag'), 256), 32)),
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
WHERE tenant.is_delete = b'0';

UPDATE auth_resource resource
SET resource.name = 'RAG接口',
    resource.resource_category = 'BACKEND',
    resource.path = '/rag/api/rag',
    resource.method = '*',
    resource.parent_id = NULL,
    resource.description = 'RAG服务接口根节点',
    resource.modify_date_time = CURRENT_TIMESTAMP,
    resource.modify_name = 'system',
    resource.is_delete = b'0',
    resource.sorting = 70,
    resource.state = 1
WHERE resource.code = 'api:rag';

INSERT IGNORE INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(SHA2(CONCAT(tenant.id, ':rag:document:manage'), 256), 32)),
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
WHERE tenant.is_delete = b'0';

UPDATE auth_resource resource
JOIN auth_resource parent
  ON parent.tenant_id = resource.tenant_id
 AND parent.code = 'api:rag'
SET resource.name = 'RAG文档管理',
    resource.resource_category = 'BACKEND',
    resource.path = '/rag/api/rag/documents/**',
    resource.method = '*',
    resource.parent_id = parent.id,
    resource.description = 'RAG文档上传、列表、校验和停用权限',
    resource.modify_date_time = CURRENT_TIMESTAMP,
    resource.modify_name = 'system',
    resource.is_delete = b'0',
    resource.sorting = 71,
    resource.state = 1
WHERE resource.code = 'rag:document:manage';

INSERT IGNORE INTO auth_resource
(id, name, resource_category, path, method, parent_id, code, description,
 create_name, modify_name, sorting, state, version, tenant_id)
SELECT
    CONCAT('res_', LEFT(SHA2(CONCAT(tenant.id, ':rag:retrieval:use'), 256), 32)),
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
WHERE tenant.is_delete = b'0';

UPDATE auth_resource resource
JOIN auth_resource parent
  ON parent.tenant_id = resource.tenant_id
 AND parent.code = 'api:rag'
SET resource.name = 'RAG知识检索',
    resource.resource_category = 'BACKEND',
    resource.path = '/rag/api/rag/retrievals',
    resource.method = 'POST',
    resource.parent_id = parent.id,
    resource.description = 'RAG知识上下文检索权限',
    resource.modify_date_time = CURRENT_TIMESTAMP,
    resource.modify_name = 'system',
    resource.is_delete = b'0',
    resource.sorting = 72,
    resource.state = 1
WHERE resource.code = 'rag:retrieval:use';

-- 只继承现有认证体系管理员角色的 RAG 权限，避免向普通角色扩权。
INSERT IGNORE INTO auth_role_resource
(id, role_id, resource_id, code, description, create_name, modify_name,
 state, version, tenant_id)
SELECT
    CONCAT('rr_', LEFT(SHA2(CONCAT(role.id, ':', rag_resource.id), 256), 32)),
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
 AND manage_grant.is_delete = b'0'
 AND manage_grant.state = 1
JOIN auth_resource manage_resource
  ON manage_resource.tenant_id = role.tenant_id
 AND manage_resource.id = manage_grant.resource_id
 AND manage_resource.code = 'user:auth:manage'
 AND manage_resource.is_delete = b'0'
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
 AND rag_resource.is_delete = b'0'
 AND rag_resource.state = 1
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
JOIN auth_resource rag_resource
  ON rag_resource.tenant_id = role_resource.tenant_id
 AND rag_resource.id = role_resource.resource_id
 AND rag_resource.code IN (
     'menu:rag',
     'menu:rag:document',
     'api:rag',
     'rag:document:manage',
     'rag:retrieval:use'
 )
SET role_resource.description = 'RAG权限资源关系',
    role_resource.modify_date_time = CURRENT_TIMESTAMP,
    role_resource.modify_name = 'system',
    role_resource.is_delete = b'0',
    role_resource.state = 1
WHERE role.is_delete = b'0'
  AND role.state = 1;
