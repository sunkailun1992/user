-- 第5阶段：登录、口令与令牌交给 Keycloak。auth_user 记对应的 Keycloak 用户 ID，不再存口令；
-- 自研 OAuth 客户端与第三方认证的接口已删除，四张表一起删除。
-- 迁移前先用 nas_pg.py backup 备份 user 库；回滚做法：恢复备份，并把服务退回第4阶段的版本。

-- 认证用户表：Keycloak 用户 ID（访问令牌的 sub）。一个 Keycloak 账号只对应一个用户；
-- 迁移前已有的用户为空，由账号迁移脚本建 Keycloak 账号后回填。
ALTER TABLE auth_user ADD COLUMN idp_subject varchar(64) COLLATE fleet_general_ci;
CREATE UNIQUE INDEX uk_auth_user_idp_subject ON auth_user (idp_subject);
COMMENT ON COLUMN auth_user.idp_subject IS 'Keycloak用户ID（访问令牌sub）';

-- 认证用户表：口令只存在 Keycloak。
ALTER TABLE auth_user DROP COLUMN password;

-- OAuth客户端表、三方认证客户端表、外部身份映射表、三方开放接口签名 nonce 表。
DROP TABLE auth_oauth_client;
DROP TABLE auth_external_client;
DROP TABLE auth_external_identity;
DROP TABLE auth_external_signature_nonce;
