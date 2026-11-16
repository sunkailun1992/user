-- 实体租户基类新增创建部门：13 张认证表加 create_dept，插入时由 utils 自动填当前登录用户的部门，没有登录用户（种子、后台任务）时为空。
-- 只加列，不删列、不改已有数据；已有行的创建部门未知，保持为空。数据权限按部门过滤仍按表在 security.data-permission 里开启。

-- 认证部门表。
ALTER TABLE auth_dept ADD COLUMN create_dept varchar(64) COLLATE fleet_general_ci;
COMMENT ON COLUMN auth_dept.create_dept IS '创建部门ID';

-- 三方认证客户端表。
ALTER TABLE auth_external_client ADD COLUMN create_dept varchar(64) COLLATE fleet_general_ci;
COMMENT ON COLUMN auth_external_client.create_dept IS '创建部门ID';

-- 外部身份映射表。
ALTER TABLE auth_external_identity ADD COLUMN create_dept varchar(64) COLLATE fleet_general_ci;
COMMENT ON COLUMN auth_external_identity.create_dept IS '创建部门ID';

-- 三方开放接口签名 nonce 表。
ALTER TABLE auth_external_signature_nonce ADD COLUMN create_dept varchar(64) COLLATE fleet_general_ci;
COMMENT ON COLUMN auth_external_signature_nonce.create_dept IS '创建部门ID';

-- OAuth客户端表。
ALTER TABLE auth_oauth_client ADD COLUMN create_dept varchar(64) COLLATE fleet_general_ci;
COMMENT ON COLUMN auth_oauth_client.create_dept IS '创建部门ID';

-- 认证资源表。
ALTER TABLE auth_resource ADD COLUMN create_dept varchar(64) COLLATE fleet_general_ci;
COMMENT ON COLUMN auth_resource.create_dept IS '创建部门ID';

-- 认证角色表。
ALTER TABLE auth_role ADD COLUMN create_dept varchar(64) COLLATE fleet_general_ci;
COMMENT ON COLUMN auth_role.create_dept IS '创建部门ID';

-- 角色自定义数据范围表。
ALTER TABLE auth_role_data_scope ADD COLUMN create_dept varchar(64) COLLATE fleet_general_ci;
COMMENT ON COLUMN auth_role_data_scope.create_dept IS '创建部门ID';

-- 认证角色资源关系表。
ALTER TABLE auth_role_resource ADD COLUMN create_dept varchar(64) COLLATE fleet_general_ci;
COMMENT ON COLUMN auth_role_resource.create_dept IS '创建部门ID';

-- 认证租户表。
ALTER TABLE auth_tenant ADD COLUMN create_dept varchar(64) COLLATE fleet_general_ci;
COMMENT ON COLUMN auth_tenant.create_dept IS '创建部门ID';

-- 认证用户表。
ALTER TABLE auth_user ADD COLUMN create_dept varchar(64) COLLATE fleet_general_ci;
COMMENT ON COLUMN auth_user.create_dept IS '创建部门ID';

-- 认证用户角色关系表。
ALTER TABLE auth_user_role ADD COLUMN create_dept varchar(64) COLLATE fleet_general_ci;
COMMENT ON COLUMN auth_user_role.create_dept IS '创建部门ID';

-- 认证用户租户关联表。
ALTER TABLE auth_user_tenant ADD COLUMN create_dept varchar(64) COLLATE fleet_general_ci;
COMMENT ON COLUMN auth_user_tenant.create_dept IS '创建部门ID';
