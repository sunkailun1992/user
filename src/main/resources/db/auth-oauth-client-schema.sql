CREATE TABLE IF NOT EXISTS auth_oauth_client (
    id varchar(64) NOT NULL PRIMARY KEY,
    client_id varchar(128) NOT NULL COMMENT 'OAuth client_id',
    client_secret varchar(255) DEFAULT NULL COMMENT 'OAuth client_secret，建议保存BCrypt哈希，公开客户端为空',
    name varchar(255) DEFAULT NULL COMMENT '客户端名称',
    client_type varchar(32) NOT NULL DEFAULT 'confidential' COMMENT '客户端类型：public/confidential',
    token_endpoint_auth_method varchar(64) NOT NULL DEFAULT 'client_secret_basic' COMMENT 'token endpoint认证方式',
    grant_types varchar(255) NOT NULL DEFAULT 'client_credentials' COMMENT '允许的grant type，空格或逗号分隔',
    redirect_uris varchar(2048) NOT NULL DEFAULT '' COMMENT '允许的redirect_uri，换行或空格分隔',
    scopes varchar(512) NOT NULL COMMENT '允许的scope，空格或逗号分隔',
    audiences varchar(512) NOT NULL COMMENT '允许的audience/resource，空格或逗号分隔',
    client_uri varchar(512) DEFAULT NULL COMMENT '客户端主页',
    logo_uri varchar(512) DEFAULT NULL COMMENT '客户端图标',
    access_token_ttl_seconds int NOT NULL DEFAULT 900 COMMENT 'access token有效秒数',
    code varchar(255) DEFAULT NULL COMMENT '编码',
    description varchar(255) DEFAULT NULL COMMENT '说明',
    create_date_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_name varchar(255) DEFAULT NULL COMMENT '创建人',
    modify_date_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    modify_name varchar(255) DEFAULT NULL COMMENT '修改人',
    is_delete bit(1) DEFAULT b'0' COMMENT '删除状态',
    type int DEFAULT '0' COMMENT '类型（0：默认）',
    state int DEFAULT '1' COMMENT '状态（1：启用，2：禁用）',
    label varchar(255) DEFAULT NULL COMMENT '标签',
    sorting int DEFAULT '0' COMMENT '排序',
    version int DEFAULT '1' COMMENT '版本号',
    tenant_id varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '租户id',
    UNIQUE KEY uk_auth_oauth_client_client_id (client_id),
    KEY idx_auth_oauth_client_state (state),
    KEY idx_auth_oauth_client_tenant_id (tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='OAuth客户端表';

-- 个人环境只初始化通用表结构，不预置外部 OAuth 客户端或固定凭据。
-- 需要 OAuth 前端时，在管理端显式注册 public / none 客户端并绑定实际本地回调，使用 PKCE。
