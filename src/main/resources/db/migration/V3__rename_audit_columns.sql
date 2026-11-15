-- 审计列按实体基类改名：create_date_time→create_time、modify_date_time→update_time、create_name→creator、
-- modify_name→updater、is_delete→is_deleted。列注释、默认值与索引随列保留，数据不变。

-- 认证部门表。
ALTER TABLE auth_dept RENAME COLUMN create_date_time TO create_time;
ALTER TABLE auth_dept RENAME COLUMN modify_date_time TO update_time;
ALTER TABLE auth_dept RENAME COLUMN create_name TO creator;
ALTER TABLE auth_dept RENAME COLUMN modify_name TO updater;
ALTER TABLE auth_dept RENAME COLUMN is_delete TO is_deleted;

-- 三方认证客户端表。
ALTER TABLE auth_external_client RENAME COLUMN create_date_time TO create_time;
ALTER TABLE auth_external_client RENAME COLUMN modify_date_time TO update_time;
ALTER TABLE auth_external_client RENAME COLUMN create_name TO creator;
ALTER TABLE auth_external_client RENAME COLUMN modify_name TO updater;
ALTER TABLE auth_external_client RENAME COLUMN is_delete TO is_deleted;

-- 外部身份映射表。
ALTER TABLE auth_external_identity RENAME COLUMN create_date_time TO create_time;
ALTER TABLE auth_external_identity RENAME COLUMN modify_date_time TO update_time;
ALTER TABLE auth_external_identity RENAME COLUMN create_name TO creator;
ALTER TABLE auth_external_identity RENAME COLUMN modify_name TO updater;
ALTER TABLE auth_external_identity RENAME COLUMN is_delete TO is_deleted;

-- 三方开放接口签名 nonce 表。
ALTER TABLE auth_external_signature_nonce RENAME COLUMN create_date_time TO create_time;
ALTER TABLE auth_external_signature_nonce RENAME COLUMN modify_date_time TO update_time;
ALTER TABLE auth_external_signature_nonce RENAME COLUMN create_name TO creator;
ALTER TABLE auth_external_signature_nonce RENAME COLUMN modify_name TO updater;
ALTER TABLE auth_external_signature_nonce RENAME COLUMN is_delete TO is_deleted;

-- OAuth客户端表。
ALTER TABLE auth_oauth_client RENAME COLUMN create_date_time TO create_time;
ALTER TABLE auth_oauth_client RENAME COLUMN modify_date_time TO update_time;
ALTER TABLE auth_oauth_client RENAME COLUMN create_name TO creator;
ALTER TABLE auth_oauth_client RENAME COLUMN modify_name TO updater;
ALTER TABLE auth_oauth_client RENAME COLUMN is_delete TO is_deleted;

-- 认证资源表。
ALTER TABLE auth_resource RENAME COLUMN create_date_time TO create_time;
ALTER TABLE auth_resource RENAME COLUMN modify_date_time TO update_time;
ALTER TABLE auth_resource RENAME COLUMN create_name TO creator;
ALTER TABLE auth_resource RENAME COLUMN modify_name TO updater;
ALTER TABLE auth_resource RENAME COLUMN is_delete TO is_deleted;

-- 认证角色表。
ALTER TABLE auth_role RENAME COLUMN create_date_time TO create_time;
ALTER TABLE auth_role RENAME COLUMN modify_date_time TO update_time;
ALTER TABLE auth_role RENAME COLUMN create_name TO creator;
ALTER TABLE auth_role RENAME COLUMN modify_name TO updater;
ALTER TABLE auth_role RENAME COLUMN is_delete TO is_deleted;

-- 角色自定义数据范围表。
ALTER TABLE auth_role_data_scope RENAME COLUMN create_date_time TO create_time;
ALTER TABLE auth_role_data_scope RENAME COLUMN modify_date_time TO update_time;
ALTER TABLE auth_role_data_scope RENAME COLUMN create_name TO creator;
ALTER TABLE auth_role_data_scope RENAME COLUMN modify_name TO updater;
ALTER TABLE auth_role_data_scope RENAME COLUMN is_delete TO is_deleted;

-- 认证角色资源关系表。
ALTER TABLE auth_role_resource RENAME COLUMN create_date_time TO create_time;
ALTER TABLE auth_role_resource RENAME COLUMN modify_date_time TO update_time;
ALTER TABLE auth_role_resource RENAME COLUMN create_name TO creator;
ALTER TABLE auth_role_resource RENAME COLUMN modify_name TO updater;
ALTER TABLE auth_role_resource RENAME COLUMN is_delete TO is_deleted;

-- 认证租户表。
ALTER TABLE auth_tenant RENAME COLUMN create_date_time TO create_time;
ALTER TABLE auth_tenant RENAME COLUMN modify_date_time TO update_time;
ALTER TABLE auth_tenant RENAME COLUMN create_name TO creator;
ALTER TABLE auth_tenant RENAME COLUMN modify_name TO updater;
ALTER TABLE auth_tenant RENAME COLUMN is_delete TO is_deleted;

-- 认证用户表。
ALTER TABLE auth_user RENAME COLUMN create_date_time TO create_time;
ALTER TABLE auth_user RENAME COLUMN modify_date_time TO update_time;
ALTER TABLE auth_user RENAME COLUMN create_name TO creator;
ALTER TABLE auth_user RENAME COLUMN modify_name TO updater;
ALTER TABLE auth_user RENAME COLUMN is_delete TO is_deleted;

-- 认证用户角色关系表。
ALTER TABLE auth_user_role RENAME COLUMN create_date_time TO create_time;
ALTER TABLE auth_user_role RENAME COLUMN modify_date_time TO update_time;
ALTER TABLE auth_user_role RENAME COLUMN create_name TO creator;
ALTER TABLE auth_user_role RENAME COLUMN modify_name TO updater;
ALTER TABLE auth_user_role RENAME COLUMN is_delete TO is_deleted;

-- 认证用户租户关联表。
ALTER TABLE auth_user_tenant RENAME COLUMN create_date_time TO create_time;
ALTER TABLE auth_user_tenant RENAME COLUMN modify_date_time TO update_time;
ALTER TABLE auth_user_tenant RENAME COLUMN create_name TO creator;
ALTER TABLE auth_user_tenant RENAME COLUMN modify_name TO updater;
ALTER TABLE auth_user_tenant RENAME COLUMN is_delete TO is_deleted;
