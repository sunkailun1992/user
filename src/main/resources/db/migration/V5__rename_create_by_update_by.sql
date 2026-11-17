-- 审计列改为主流项目的多数命名（RuoYi-Vue、RuoYi-Vue-Plus、JeecgBoot）：creator→create_by、updater→update_by。
-- 只改列名，列注释、默认值与数据随列保留。

-- 认证部门表。
ALTER TABLE auth_dept RENAME COLUMN creator TO create_by;
ALTER TABLE auth_dept RENAME COLUMN updater TO update_by;

-- 三方认证客户端表。
ALTER TABLE auth_external_client RENAME COLUMN creator TO create_by;
ALTER TABLE auth_external_client RENAME COLUMN updater TO update_by;

-- 外部身份映射表。
ALTER TABLE auth_external_identity RENAME COLUMN creator TO create_by;
ALTER TABLE auth_external_identity RENAME COLUMN updater TO update_by;

-- 三方开放接口签名 nonce 表。
ALTER TABLE auth_external_signature_nonce RENAME COLUMN creator TO create_by;
ALTER TABLE auth_external_signature_nonce RENAME COLUMN updater TO update_by;

-- OAuth客户端表。
ALTER TABLE auth_oauth_client RENAME COLUMN creator TO create_by;
ALTER TABLE auth_oauth_client RENAME COLUMN updater TO update_by;

-- 认证资源表。
ALTER TABLE auth_resource RENAME COLUMN creator TO create_by;
ALTER TABLE auth_resource RENAME COLUMN updater TO update_by;

-- 认证角色表。
ALTER TABLE auth_role RENAME COLUMN creator TO create_by;
ALTER TABLE auth_role RENAME COLUMN updater TO update_by;

-- 角色自定义数据范围表。
ALTER TABLE auth_role_data_scope RENAME COLUMN creator TO create_by;
ALTER TABLE auth_role_data_scope RENAME COLUMN updater TO update_by;

-- 认证角色资源关系表。
ALTER TABLE auth_role_resource RENAME COLUMN creator TO create_by;
ALTER TABLE auth_role_resource RENAME COLUMN updater TO update_by;

-- 认证租户表。
ALTER TABLE auth_tenant RENAME COLUMN creator TO create_by;
ALTER TABLE auth_tenant RENAME COLUMN updater TO update_by;

-- 认证用户表。
ALTER TABLE auth_user RENAME COLUMN creator TO create_by;
ALTER TABLE auth_user RENAME COLUMN updater TO update_by;

-- 认证用户角色关系表。
ALTER TABLE auth_user_role RENAME COLUMN creator TO create_by;
ALTER TABLE auth_user_role RENAME COLUMN updater TO update_by;

-- 认证用户租户关联表。
ALTER TABLE auth_user_tenant RENAME COLUMN creator TO create_by;
ALTER TABLE auth_user_tenant RENAME COLUMN updater TO update_by;
