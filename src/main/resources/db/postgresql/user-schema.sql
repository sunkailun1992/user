-- PostgreSQL 18 业务结构。历史 MySQL 脚本保留，不在 PostgreSQL 启动时执行。
-- 先以对应业务角色执行 utils 的 db/postgresql/common-infra-schema.sql，再执行本文件。
-- ICU 主级比较保留大小写、重音不敏感的常用匹配；迁移前另查唯一键碰撞与尾空格。
-- MySQL ON UPDATE 时间由应用显式更新；不创建 Seata AT 不支持的数据库触发器。
CREATE COLLATION IF NOT EXISTS public.fleet_general_ci (provider = icu, locale = 'und-u-ks-level1', deterministic = false);

CREATE TABLE IF NOT EXISTS auth_dept (
    id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    name varchar(255) COLLATE public.fleet_general_ci NOT NULL,
    parent_id varchar(64) COLLATE public.fleet_general_ci,
    owner_user_id varchar(64) COLLATE public.fleet_general_ci,
    code varchar(255) COLLATE public.fleet_general_ci,
    description varchar(255) COLLATE public.fleet_general_ci,
    create_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    create_name varchar(255) COLLATE public.fleet_general_ci,
    modify_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    modify_name varchar(255) COLLATE public.fleet_general_ci,
    is_delete boolean DEFAULT false,
    type integer DEFAULT 0,
    state integer DEFAULT 1,
    label varchar(255) COLLATE public.fleet_general_ci,
    sorting integer DEFAULT 0,
    version integer DEFAULT 1,
    tenant_id varchar(64) COLLATE public.fleet_general_ci NOT NULL DEFAULT '1',
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_auth_dept_owner_user_id ON auth_dept (owner_user_id);
CREATE INDEX IF NOT EXISTS idx_auth_dept_tenant_parent ON auth_dept (tenant_id, parent_id);
CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_dept_tenant_code ON auth_dept (tenant_id, code);
COMMENT ON TABLE auth_dept IS '认证部门表';
COMMENT ON COLUMN auth_dept.name IS '部门名称';
COMMENT ON COLUMN auth_dept.parent_id IS '父级部门ID';
COMMENT ON COLUMN auth_dept.owner_user_id IS '负责人用户ID';
COMMENT ON COLUMN auth_dept.code IS '编码';
COMMENT ON COLUMN auth_dept.description IS '说明';
COMMENT ON COLUMN auth_dept.create_date_time IS '创建时间';
COMMENT ON COLUMN auth_dept.create_name IS '创建人';
COMMENT ON COLUMN auth_dept.modify_date_time IS '修改时间';
COMMENT ON COLUMN auth_dept.modify_name IS '修改人';
COMMENT ON COLUMN auth_dept.is_delete IS '删除状态';
COMMENT ON COLUMN auth_dept.type IS '类型（0：默认）';
COMMENT ON COLUMN auth_dept.state IS '状态（1：启用，2：禁用）';
COMMENT ON COLUMN auth_dept.label IS '标签';
COMMENT ON COLUMN auth_dept.sorting IS '排序';
COMMENT ON COLUMN auth_dept.version IS '版本号';
COMMENT ON COLUMN auth_dept.tenant_id IS '租户id';

CREATE TABLE IF NOT EXISTS auth_external_client (
    id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    provider_code varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    client_id varchar(128) COLLATE public.fleet_general_ci NOT NULL,
    client_secret varchar(255) COLLATE public.fleet_general_ci NOT NULL,
    name varchar(255) COLLATE public.fleet_general_ci,
    signature_required boolean DEFAULT true,
    allowed_clock_skew_seconds integer DEFAULT 300,
    code varchar(255) COLLATE public.fleet_general_ci,
    description varchar(255) COLLATE public.fleet_general_ci,
    create_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    create_name varchar(255) COLLATE public.fleet_general_ci,
    modify_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    modify_name varchar(255) COLLATE public.fleet_general_ci,
    is_delete boolean DEFAULT false,
    type integer DEFAULT 0,
    state integer DEFAULT 1,
    label varchar(255) COLLATE public.fleet_general_ci,
    sorting integer DEFAULT 0,
    version integer DEFAULT 1,
    tenant_id varchar(64) COLLATE public.fleet_general_ci NOT NULL DEFAULT '1',
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_auth_external_client_state ON auth_external_client (state);
CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_external_client_provider_client ON auth_external_client (provider_code, client_id);
COMMENT ON TABLE auth_external_client IS '三方认证客户端表';
COMMENT ON COLUMN auth_external_client.provider_code IS '外部系统编码';
COMMENT ON COLUMN auth_external_client.client_id IS '三方客户端ID';
COMMENT ON COLUMN auth_external_client.client_secret IS '三方客户端密钥';
COMMENT ON COLUMN auth_external_client.name IS '客户端名称';
COMMENT ON COLUMN auth_external_client.signature_required IS '是否要求签名';
COMMENT ON COLUMN auth_external_client.allowed_clock_skew_seconds IS '允许的签名时钟偏差秒数';
COMMENT ON COLUMN auth_external_client.code IS '编码';
COMMENT ON COLUMN auth_external_client.description IS '说明';
COMMENT ON COLUMN auth_external_client.create_date_time IS '创建时间';
COMMENT ON COLUMN auth_external_client.create_name IS '创建人';
COMMENT ON COLUMN auth_external_client.modify_date_time IS '修改时间';
COMMENT ON COLUMN auth_external_client.modify_name IS '修改人';
COMMENT ON COLUMN auth_external_client.is_delete IS '删除状态';
COMMENT ON COLUMN auth_external_client.type IS '类型（0：默认）';
COMMENT ON COLUMN auth_external_client.state IS '状态（1：启用，2：禁用）';
COMMENT ON COLUMN auth_external_client.label IS '标签';
COMMENT ON COLUMN auth_external_client.sorting IS '排序';
COMMENT ON COLUMN auth_external_client.version IS '版本号';
COMMENT ON COLUMN auth_external_client.tenant_id IS '租户id';

CREATE TABLE IF NOT EXISTS auth_external_identity (
    id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    provider_code varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    external_tenant_id varchar(128) COLLATE public.fleet_general_ci NOT NULL DEFAULT '',
    external_user_id varchar(128) COLLATE public.fleet_general_ci NOT NULL DEFAULT '',
    external_patient_id varchar(128) COLLATE public.fleet_general_ci NOT NULL DEFAULT '',
    local_tenant_id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    local_user_id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    subject_type varchar(32) COLLATE public.fleet_general_ci,
    display_name varchar(255) COLLATE public.fleet_general_ci,
    metadata_json jsonb,
    code varchar(255) COLLATE public.fleet_general_ci,
    description varchar(255) COLLATE public.fleet_general_ci,
    create_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    create_name varchar(255) COLLATE public.fleet_general_ci,
    modify_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    modify_name varchar(255) COLLATE public.fleet_general_ci,
    is_delete boolean DEFAULT false,
    type integer DEFAULT 0,
    state integer DEFAULT 1,
    label varchar(255) COLLATE public.fleet_general_ci,
    sorting integer DEFAULT 0,
    version integer DEFAULT 1,
    tenant_id varchar(64) COLLATE public.fleet_general_ci NOT NULL DEFAULT '1',
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_auth_external_identity_local_user ON auth_external_identity (local_tenant_id, local_user_id);
CREATE INDEX IF NOT EXISTS idx_auth_external_identity_state ON auth_external_identity (state);
CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_external_identity_subject ON auth_external_identity (provider_code, external_tenant_id, external_user_id, external_patient_id);
COMMENT ON TABLE auth_external_identity IS '外部身份映射表';
COMMENT ON COLUMN auth_external_identity.provider_code IS '外部系统编码';
COMMENT ON COLUMN auth_external_identity.external_tenant_id IS '外部租户ID';
COMMENT ON COLUMN auth_external_identity.external_user_id IS '外部用户ID';
COMMENT ON COLUMN auth_external_identity.external_patient_id IS '外部患者或业务主体ID';
COMMENT ON COLUMN auth_external_identity.local_tenant_id IS '本地租户ID';
COMMENT ON COLUMN auth_external_identity.local_user_id IS '本地用户ID';
COMMENT ON COLUMN auth_external_identity.subject_type IS '主体类型：USER/PATIENT/DOCTOR';
COMMENT ON COLUMN auth_external_identity.display_name IS '展示名称';
COMMENT ON COLUMN auth_external_identity.metadata_json IS '三方扩展元数据JSON';
COMMENT ON COLUMN auth_external_identity.code IS '编码';
COMMENT ON COLUMN auth_external_identity.description IS '说明';
COMMENT ON COLUMN auth_external_identity.create_date_time IS '创建时间';
COMMENT ON COLUMN auth_external_identity.create_name IS '创建人';
COMMENT ON COLUMN auth_external_identity.modify_date_time IS '修改时间';
COMMENT ON COLUMN auth_external_identity.modify_name IS '修改人';
COMMENT ON COLUMN auth_external_identity.is_delete IS '删除状态';
COMMENT ON COLUMN auth_external_identity.type IS '类型（0：默认）';
COMMENT ON COLUMN auth_external_identity.state IS '状态（1：启用，2：禁用）';
COMMENT ON COLUMN auth_external_identity.label IS '标签';
COMMENT ON COLUMN auth_external_identity.sorting IS '排序';
COMMENT ON COLUMN auth_external_identity.version IS '版本号';
COMMENT ON COLUMN auth_external_identity.tenant_id IS '租户id';

CREATE TABLE IF NOT EXISTS auth_external_signature_nonce (
    id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    provider_code varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    client_id varchar(128) COLLATE public.fleet_general_ci NOT NULL,
    nonce varchar(128) COLLATE public.fleet_general_ci NOT NULL,
    timestamp_millis bigint NOT NULL,
    expire_date_time timestamp(0) without time zone,
    code varchar(255) COLLATE public.fleet_general_ci,
    description varchar(255) COLLATE public.fleet_general_ci,
    create_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    create_name varchar(255) COLLATE public.fleet_general_ci,
    modify_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    modify_name varchar(255) COLLATE public.fleet_general_ci,
    is_delete boolean DEFAULT false,
    type integer DEFAULT 0,
    state integer DEFAULT 1,
    label varchar(255) COLLATE public.fleet_general_ci,
    sorting integer DEFAULT 0,
    version integer DEFAULT 1,
    tenant_id varchar(64) COLLATE public.fleet_general_ci NOT NULL DEFAULT '1',
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_auth_external_signature_nonce_expire ON auth_external_signature_nonce (expire_date_time);
CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_external_signature_nonce ON auth_external_signature_nonce (provider_code, client_id, nonce);
COMMENT ON TABLE auth_external_signature_nonce IS '三方开放接口签名 nonce 表';
COMMENT ON COLUMN auth_external_signature_nonce.provider_code IS '外部系统编码';
COMMENT ON COLUMN auth_external_signature_nonce.client_id IS '三方客户端ID';
COMMENT ON COLUMN auth_external_signature_nonce.nonce IS '随机串';
COMMENT ON COLUMN auth_external_signature_nonce.timestamp_millis IS '请求时间戳毫秒';
COMMENT ON COLUMN auth_external_signature_nonce.expire_date_time IS '过期时间';
COMMENT ON COLUMN auth_external_signature_nonce.code IS '编码';
COMMENT ON COLUMN auth_external_signature_nonce.description IS '说明';
COMMENT ON COLUMN auth_external_signature_nonce.create_date_time IS '创建时间';
COMMENT ON COLUMN auth_external_signature_nonce.create_name IS '创建人';
COMMENT ON COLUMN auth_external_signature_nonce.modify_date_time IS '修改时间';
COMMENT ON COLUMN auth_external_signature_nonce.modify_name IS '修改人';
COMMENT ON COLUMN auth_external_signature_nonce.is_delete IS '删除状态';
COMMENT ON COLUMN auth_external_signature_nonce.type IS '类型（0：默认）';
COMMENT ON COLUMN auth_external_signature_nonce.state IS '状态（1：启用，2：禁用）';
COMMENT ON COLUMN auth_external_signature_nonce.label IS '标签';
COMMENT ON COLUMN auth_external_signature_nonce.sorting IS '排序';
COMMENT ON COLUMN auth_external_signature_nonce.version IS '版本号';
COMMENT ON COLUMN auth_external_signature_nonce.tenant_id IS '租户id';

CREATE TABLE IF NOT EXISTS auth_oauth_client (
    id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    client_id varchar(128) COLLATE public.fleet_general_ci NOT NULL,
    client_secret varchar(255) COLLATE public.fleet_general_ci,
    name varchar(255) COLLATE public.fleet_general_ci,
    client_type varchar(32) COLLATE public.fleet_general_ci NOT NULL DEFAULT 'confidential',
    token_endpoint_auth_method varchar(64) COLLATE public.fleet_general_ci NOT NULL DEFAULT 'client_secret_basic',
    grant_types varchar(255) COLLATE public.fleet_general_ci NOT NULL DEFAULT 'client_credentials',
    redirect_uris varchar(2048) COLLATE public.fleet_general_ci NOT NULL DEFAULT '',
    scopes varchar(512) COLLATE public.fleet_general_ci NOT NULL,
    audiences varchar(512) COLLATE public.fleet_general_ci NOT NULL,
    client_uri varchar(512) COLLATE public.fleet_general_ci,
    logo_uri varchar(512) COLLATE public.fleet_general_ci,
    access_token_ttl_seconds integer NOT NULL DEFAULT 900,
    code varchar(255) COLLATE public.fleet_general_ci,
    description varchar(255) COLLATE public.fleet_general_ci,
    create_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    create_name varchar(255) COLLATE public.fleet_general_ci,
    modify_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    modify_name varchar(255) COLLATE public.fleet_general_ci,
    is_delete boolean DEFAULT false,
    type integer DEFAULT 0,
    state integer DEFAULT 1,
    label varchar(255) COLLATE public.fleet_general_ci,
    sorting integer DEFAULT 0,
    version integer DEFAULT 1,
    tenant_id varchar(64) COLLATE public.fleet_general_ci NOT NULL DEFAULT '1',
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_auth_oauth_client_state ON auth_oauth_client (state);
CREATE INDEX IF NOT EXISTS idx_auth_oauth_client_tenant_id ON auth_oauth_client (tenant_id);
CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_oauth_client_client_id ON auth_oauth_client (client_id);
COMMENT ON TABLE auth_oauth_client IS 'OAuth客户端表';
COMMENT ON COLUMN auth_oauth_client.client_id IS 'OAuth client_id';
COMMENT ON COLUMN auth_oauth_client.client_secret IS 'OAuth client_secret，建议保存BCrypt哈希，公开客户端为空';
COMMENT ON COLUMN auth_oauth_client.name IS '客户端名称';
COMMENT ON COLUMN auth_oauth_client.client_type IS '客户端类型：public/confidential';
COMMENT ON COLUMN auth_oauth_client.token_endpoint_auth_method IS 'token endpoint认证方式';
COMMENT ON COLUMN auth_oauth_client.grant_types IS '允许的grant type，空格或逗号分隔';
COMMENT ON COLUMN auth_oauth_client.redirect_uris IS '允许的redirect_uri，换行或空格分隔';
COMMENT ON COLUMN auth_oauth_client.scopes IS '允许的scope，空格或逗号分隔';
COMMENT ON COLUMN auth_oauth_client.audiences IS '允许的audience/resource，空格或逗号分隔';
COMMENT ON COLUMN auth_oauth_client.client_uri IS '客户端主页';
COMMENT ON COLUMN auth_oauth_client.logo_uri IS '客户端图标';
COMMENT ON COLUMN auth_oauth_client.access_token_ttl_seconds IS 'access token有效秒数';
COMMENT ON COLUMN auth_oauth_client.code IS '编码';
COMMENT ON COLUMN auth_oauth_client.description IS '说明';
COMMENT ON COLUMN auth_oauth_client.create_date_time IS '创建时间';
COMMENT ON COLUMN auth_oauth_client.create_name IS '创建人';
COMMENT ON COLUMN auth_oauth_client.modify_date_time IS '修改时间';
COMMENT ON COLUMN auth_oauth_client.modify_name IS '修改人';
COMMENT ON COLUMN auth_oauth_client.is_delete IS '删除状态';
COMMENT ON COLUMN auth_oauth_client.type IS '类型（0：默认）';
COMMENT ON COLUMN auth_oauth_client.state IS '状态（1：启用，2：禁用）';
COMMENT ON COLUMN auth_oauth_client.label IS '标签';
COMMENT ON COLUMN auth_oauth_client.sorting IS '排序';
COMMENT ON COLUMN auth_oauth_client.version IS '版本号';
COMMENT ON COLUMN auth_oauth_client.tenant_id IS '租户id';

CREATE TABLE IF NOT EXISTS auth_resource (
    id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    name varchar(255) COLLATE public.fleet_general_ci NOT NULL,
    resource_category varchar(32) COLLATE public.fleet_general_ci NOT NULL,
    path varchar(255) COLLATE public.fleet_general_ci,
    method varchar(32) COLLATE public.fleet_general_ci,
    parent_id varchar(64) COLLATE public.fleet_general_ci,
    code varchar(255) COLLATE public.fleet_general_ci,
    description varchar(255) COLLATE public.fleet_general_ci,
    create_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    create_name varchar(255) COLLATE public.fleet_general_ci,
    modify_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    modify_name varchar(255) COLLATE public.fleet_general_ci,
    is_delete boolean DEFAULT false,
    type integer DEFAULT 0,
    state integer DEFAULT 1,
    label varchar(255) COLLATE public.fleet_general_ci,
    sorting integer DEFAULT 0,
    version integer DEFAULT 1,
    tenant_id varchar(64) COLLATE public.fleet_general_ci NOT NULL DEFAULT '1',
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_auth_resource_parent_id ON auth_resource (parent_id);
CREATE INDEX IF NOT EXISTS idx_auth_resource_tenant_id ON auth_resource (tenant_id);
CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_resource_tenant_code ON auth_resource (tenant_id, code);
COMMENT ON TABLE auth_resource IS '认证资源表';
COMMENT ON COLUMN auth_resource.name IS '资源名称';
COMMENT ON COLUMN auth_resource.resource_category IS '资源分类：FRONTEND/BACKEND';
COMMENT ON COLUMN auth_resource.path IS '资源路径';
COMMENT ON COLUMN auth_resource.method IS '请求方法';
COMMENT ON COLUMN auth_resource.parent_id IS '父级资源ID';
COMMENT ON COLUMN auth_resource.code IS '编码';
COMMENT ON COLUMN auth_resource.description IS '说明';
COMMENT ON COLUMN auth_resource.create_date_time IS '创建时间';
COMMENT ON COLUMN auth_resource.create_name IS '创建人';
COMMENT ON COLUMN auth_resource.modify_date_time IS '修改时间';
COMMENT ON COLUMN auth_resource.modify_name IS '修改人';
COMMENT ON COLUMN auth_resource.is_delete IS '删除状态';
COMMENT ON COLUMN auth_resource.type IS '类型（0：默认）';
COMMENT ON COLUMN auth_resource.state IS '状态（1：启用，2：禁用）';
COMMENT ON COLUMN auth_resource.label IS '标签';
COMMENT ON COLUMN auth_resource.sorting IS '排序';
COMMENT ON COLUMN auth_resource.version IS '版本号';
COMMENT ON COLUMN auth_resource.tenant_id IS '租户id';

CREATE TABLE IF NOT EXISTS auth_role (
    id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    name varchar(255) COLLATE public.fleet_general_ci NOT NULL,
    data_scope varchar(32) COLLATE public.fleet_general_ci NOT NULL DEFAULT 'SELF',
    owner_user_id varchar(64) COLLATE public.fleet_general_ci,
    dept_id varchar(64) COLLATE public.fleet_general_ci,
    code varchar(255) COLLATE public.fleet_general_ci,
    description varchar(255) COLLATE public.fleet_general_ci,
    create_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    create_name varchar(255) COLLATE public.fleet_general_ci,
    modify_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    modify_name varchar(255) COLLATE public.fleet_general_ci,
    is_delete boolean DEFAULT false,
    type integer DEFAULT 0,
    state integer DEFAULT 1,
    label varchar(255) COLLATE public.fleet_general_ci,
    sorting integer DEFAULT 0,
    version integer DEFAULT 1,
    tenant_id varchar(64) COLLATE public.fleet_general_ci NOT NULL DEFAULT '1',
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_auth_role_dept_id ON auth_role (dept_id);
CREATE INDEX IF NOT EXISTS idx_auth_role_owner_user_id ON auth_role (owner_user_id);
CREATE INDEX IF NOT EXISTS idx_auth_role_tenant_id ON auth_role (tenant_id);
CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_role_tenant_code ON auth_role (tenant_id, code);
COMMENT ON TABLE auth_role IS '认证角色表';
COMMENT ON COLUMN auth_role.name IS '角色名称';
COMMENT ON COLUMN auth_role.data_scope IS '数据权限范围：ALL/SELF/DEPT/DEPT_TREE/CUSTOM';
COMMENT ON COLUMN auth_role.owner_user_id IS '负责人用户ID';
COMMENT ON COLUMN auth_role.dept_id IS '归属部门ID';
COMMENT ON COLUMN auth_role.code IS '编码';
COMMENT ON COLUMN auth_role.description IS '说明';
COMMENT ON COLUMN auth_role.create_date_time IS '创建时间';
COMMENT ON COLUMN auth_role.create_name IS '创建人';
COMMENT ON COLUMN auth_role.modify_date_time IS '修改时间';
COMMENT ON COLUMN auth_role.modify_name IS '修改人';
COMMENT ON COLUMN auth_role.is_delete IS '删除状态';
COMMENT ON COLUMN auth_role.type IS '类型（0：默认）';
COMMENT ON COLUMN auth_role.state IS '状态（1：启用，2：禁用）';
COMMENT ON COLUMN auth_role.label IS '标签';
COMMENT ON COLUMN auth_role.sorting IS '排序';
COMMENT ON COLUMN auth_role.version IS '版本号';
COMMENT ON COLUMN auth_role.tenant_id IS '租户id';

CREATE TABLE IF NOT EXISTS auth_role_data_scope (
    id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    role_id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    dept_id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    code varchar(255) COLLATE public.fleet_general_ci,
    description varchar(255) COLLATE public.fleet_general_ci,
    create_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    create_name varchar(255) COLLATE public.fleet_general_ci,
    modify_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    modify_name varchar(255) COLLATE public.fleet_general_ci,
    is_delete boolean DEFAULT false,
    type integer DEFAULT 0,
    state integer DEFAULT 1,
    label varchar(255) COLLATE public.fleet_general_ci,
    sorting integer DEFAULT 0,
    version integer DEFAULT 1,
    tenant_id varchar(64) COLLATE public.fleet_general_ci NOT NULL DEFAULT '1',
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_auth_role_data_scope_dept_id ON auth_role_data_scope (dept_id);
CREATE INDEX IF NOT EXISTS idx_auth_role_data_scope_role_id ON auth_role_data_scope (role_id);
CREATE INDEX IF NOT EXISTS idx_auth_role_data_scope_tenant_id ON auth_role_data_scope (tenant_id);
CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_role_data_scope_tenant_role_dept ON auth_role_data_scope (tenant_id, role_id, dept_id);
COMMENT ON TABLE auth_role_data_scope IS '角色自定义数据范围表';
COMMENT ON COLUMN auth_role_data_scope.role_id IS '角色ID';
COMMENT ON COLUMN auth_role_data_scope.dept_id IS '部门ID';
COMMENT ON COLUMN auth_role_data_scope.code IS '编码';
COMMENT ON COLUMN auth_role_data_scope.description IS '说明';
COMMENT ON COLUMN auth_role_data_scope.create_date_time IS '创建时间';
COMMENT ON COLUMN auth_role_data_scope.create_name IS '创建人';
COMMENT ON COLUMN auth_role_data_scope.modify_date_time IS '修改时间';
COMMENT ON COLUMN auth_role_data_scope.modify_name IS '修改人';
COMMENT ON COLUMN auth_role_data_scope.is_delete IS '删除状态';
COMMENT ON COLUMN auth_role_data_scope.type IS '类型（0：默认）';
COMMENT ON COLUMN auth_role_data_scope.state IS '状态（1：启用，2：禁用）';
COMMENT ON COLUMN auth_role_data_scope.label IS '标签';
COMMENT ON COLUMN auth_role_data_scope.sorting IS '排序';
COMMENT ON COLUMN auth_role_data_scope.version IS '版本号';
COMMENT ON COLUMN auth_role_data_scope.tenant_id IS '租户id';

CREATE TABLE IF NOT EXISTS auth_role_resource (
    id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    role_id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    resource_id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    code varchar(255) COLLATE public.fleet_general_ci,
    description varchar(255) COLLATE public.fleet_general_ci,
    create_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    create_name varchar(255) COLLATE public.fleet_general_ci,
    modify_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    modify_name varchar(255) COLLATE public.fleet_general_ci,
    is_delete boolean DEFAULT false,
    type integer DEFAULT 0,
    state integer DEFAULT 1,
    label varchar(255) COLLATE public.fleet_general_ci,
    sorting integer DEFAULT 0,
    version integer DEFAULT 1,
    tenant_id varchar(64) COLLATE public.fleet_general_ci NOT NULL DEFAULT '1',
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_auth_role_resource_resource_id ON auth_role_resource (resource_id);
CREATE INDEX IF NOT EXISTS idx_auth_role_resource_role_id ON auth_role_resource (role_id);
CREATE INDEX IF NOT EXISTS idx_auth_role_resource_tenant_id ON auth_role_resource (tenant_id);
CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_role_resource_tenant_role_resource ON auth_role_resource (tenant_id, role_id, resource_id);
COMMENT ON TABLE auth_role_resource IS '认证角色资源关系表';
COMMENT ON COLUMN auth_role_resource.role_id IS '角色ID';
COMMENT ON COLUMN auth_role_resource.resource_id IS '资源ID';
COMMENT ON COLUMN auth_role_resource.code IS '编码';
COMMENT ON COLUMN auth_role_resource.description IS '说明';
COMMENT ON COLUMN auth_role_resource.create_date_time IS '创建时间';
COMMENT ON COLUMN auth_role_resource.create_name IS '创建人';
COMMENT ON COLUMN auth_role_resource.modify_date_time IS '修改时间';
COMMENT ON COLUMN auth_role_resource.modify_name IS '修改人';
COMMENT ON COLUMN auth_role_resource.is_delete IS '删除状态';
COMMENT ON COLUMN auth_role_resource.type IS '类型（0：默认）';
COMMENT ON COLUMN auth_role_resource.state IS '状态（1：启用，2：禁用）';
COMMENT ON COLUMN auth_role_resource.label IS '标签';
COMMENT ON COLUMN auth_role_resource.sorting IS '排序';
COMMENT ON COLUMN auth_role_resource.version IS '版本号';
COMMENT ON COLUMN auth_role_resource.tenant_id IS '租户id';

CREATE TABLE IF NOT EXISTS auth_tenant (
    id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    name varchar(255) COLLATE public.fleet_general_ci NOT NULL,
    code varchar(255) COLLATE public.fleet_general_ci,
    description varchar(255) COLLATE public.fleet_general_ci,
    create_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    create_name varchar(255) COLLATE public.fleet_general_ci,
    modify_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    modify_name varchar(255) COLLATE public.fleet_general_ci,
    is_delete boolean DEFAULT false,
    type integer DEFAULT 0,
    state integer DEFAULT 1,
    label varchar(255) COLLATE public.fleet_general_ci,
    sorting integer DEFAULT 0,
    version integer DEFAULT 1,
    tenant_id varchar(64) COLLATE public.fleet_general_ci NOT NULL DEFAULT '1',
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_auth_tenant_state ON auth_tenant (state);
CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_tenant_code ON auth_tenant (code);
COMMENT ON TABLE auth_tenant IS '认证租户表';
COMMENT ON COLUMN auth_tenant.name IS '租户名称';
COMMENT ON COLUMN auth_tenant.code IS '编码';
COMMENT ON COLUMN auth_tenant.description IS '说明';
COMMENT ON COLUMN auth_tenant.create_date_time IS '创建时间';
COMMENT ON COLUMN auth_tenant.create_name IS '创建人';
COMMENT ON COLUMN auth_tenant.modify_date_time IS '修改时间';
COMMENT ON COLUMN auth_tenant.modify_name IS '修改人';
COMMENT ON COLUMN auth_tenant.is_delete IS '删除状态';
COMMENT ON COLUMN auth_tenant.type IS '类型（0：默认）';
COMMENT ON COLUMN auth_tenant.state IS '状态（1：启用，2：禁用）';
COMMENT ON COLUMN auth_tenant.label IS '标签';
COMMENT ON COLUMN auth_tenant.sorting IS '排序';
COMMENT ON COLUMN auth_tenant.version IS '版本号';
COMMENT ON COLUMN auth_tenant.tenant_id IS '租户id';

CREATE TABLE IF NOT EXISTS auth_user (
    id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    username varchar(255) COLLATE public.fleet_general_ci NOT NULL,
    password varchar(255) COLLATE public.fleet_general_ci NOT NULL,
    nickname varchar(255) COLLATE public.fleet_general_ci,
    admin_type varchar(32) COLLATE public.fleet_general_ci NOT NULL DEFAULT 'TENANT_ADMIN',
    dept_id varchar(64) COLLATE public.fleet_general_ci,
    code varchar(255) COLLATE public.fleet_general_ci,
    description varchar(255) COLLATE public.fleet_general_ci,
    create_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    create_name varchar(255) COLLATE public.fleet_general_ci,
    modify_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    modify_name varchar(255) COLLATE public.fleet_general_ci,
    is_delete boolean DEFAULT false,
    type integer DEFAULT 0,
    state integer DEFAULT 1,
    label varchar(255) COLLATE public.fleet_general_ci,
    sorting integer DEFAULT 0,
    version integer DEFAULT 1,
    tenant_id varchar(64) COLLATE public.fleet_general_ci NOT NULL DEFAULT '1',
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_auth_user_dept_id ON auth_user (dept_id);
CREATE INDEX IF NOT EXISTS idx_auth_user_tenant_id ON auth_user (tenant_id);
CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_user_tenant_username ON auth_user (tenant_id, username);
COMMENT ON TABLE auth_user IS '认证用户表';
COMMENT ON COLUMN auth_user.username IS '用户名';
COMMENT ON COLUMN auth_user.password IS '密码';
COMMENT ON COLUMN auth_user.nickname IS '昵称';
COMMENT ON COLUMN auth_user.admin_type IS '管理员分类：PLATFORM_SUPER_ADMIN/TENANT_ADMIN';
COMMENT ON COLUMN auth_user.dept_id IS '所属部门ID';
COMMENT ON COLUMN auth_user.code IS '编码';
COMMENT ON COLUMN auth_user.description IS '说明';
COMMENT ON COLUMN auth_user.create_date_time IS '创建时间';
COMMENT ON COLUMN auth_user.create_name IS '创建人';
COMMENT ON COLUMN auth_user.modify_date_time IS '修改时间';
COMMENT ON COLUMN auth_user.modify_name IS '修改人';
COMMENT ON COLUMN auth_user.is_delete IS '删除状态';
COMMENT ON COLUMN auth_user.type IS '类型（0：默认）';
COMMENT ON COLUMN auth_user.state IS '状态（1：启用，2：禁用）';
COMMENT ON COLUMN auth_user.label IS '标签';
COMMENT ON COLUMN auth_user.sorting IS '排序';
COMMENT ON COLUMN auth_user.version IS '版本号';
COMMENT ON COLUMN auth_user.tenant_id IS '租户id';

CREATE TABLE IF NOT EXISTS auth_user_role (
    id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    user_id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    role_id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    code varchar(255) COLLATE public.fleet_general_ci,
    description varchar(255) COLLATE public.fleet_general_ci,
    create_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    create_name varchar(255) COLLATE public.fleet_general_ci,
    modify_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    modify_name varchar(255) COLLATE public.fleet_general_ci,
    is_delete boolean DEFAULT false,
    type integer DEFAULT 0,
    state integer DEFAULT 1,
    label varchar(255) COLLATE public.fleet_general_ci,
    sorting integer DEFAULT 0,
    version integer DEFAULT 1,
    tenant_id varchar(64) COLLATE public.fleet_general_ci NOT NULL DEFAULT '1',
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_auth_user_role_role_id ON auth_user_role (role_id);
CREATE INDEX IF NOT EXISTS idx_auth_user_role_tenant_id ON auth_user_role (tenant_id);
CREATE INDEX IF NOT EXISTS idx_auth_user_role_user_id ON auth_user_role (user_id);
CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_user_role_tenant_user_role ON auth_user_role (tenant_id, user_id, role_id);
COMMENT ON TABLE auth_user_role IS '认证用户角色关系表';
COMMENT ON COLUMN auth_user_role.user_id IS '用户ID';
COMMENT ON COLUMN auth_user_role.role_id IS '角色ID';
COMMENT ON COLUMN auth_user_role.code IS '编码';
COMMENT ON COLUMN auth_user_role.description IS '说明';
COMMENT ON COLUMN auth_user_role.create_date_time IS '创建时间';
COMMENT ON COLUMN auth_user_role.create_name IS '创建人';
COMMENT ON COLUMN auth_user_role.modify_date_time IS '修改时间';
COMMENT ON COLUMN auth_user_role.modify_name IS '修改人';
COMMENT ON COLUMN auth_user_role.is_delete IS '删除状态';
COMMENT ON COLUMN auth_user_role.type IS '类型（0：默认）';
COMMENT ON COLUMN auth_user_role.state IS '状态（1：启用，2：禁用）';
COMMENT ON COLUMN auth_user_role.label IS '标签';
COMMENT ON COLUMN auth_user_role.sorting IS '排序';
COMMENT ON COLUMN auth_user_role.version IS '版本号';
COMMENT ON COLUMN auth_user_role.tenant_id IS '租户id';

CREATE TABLE IF NOT EXISTS auth_user_tenant (
    id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    user_id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    relation_tenant_id varchar(64) COLLATE public.fleet_general_ci NOT NULL,
    dept_id varchar(64) COLLATE public.fleet_general_ci,
    default_tenant boolean DEFAULT false,
    code varchar(255) COLLATE public.fleet_general_ci,
    description varchar(255) COLLATE public.fleet_general_ci,
    create_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    create_name varchar(255) COLLATE public.fleet_general_ci,
    modify_date_time timestamp(0) without time zone DEFAULT CURRENT_TIMESTAMP,
    modify_name varchar(255) COLLATE public.fleet_general_ci,
    is_delete boolean DEFAULT false,
    type integer DEFAULT 0,
    state integer DEFAULT 1,
    label varchar(255) COLLATE public.fleet_general_ci,
    sorting integer DEFAULT 0,
    version integer DEFAULT 1,
    tenant_id varchar(64) COLLATE public.fleet_general_ci NOT NULL DEFAULT '1',
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS idx_auth_user_tenant_dept_id ON auth_user_tenant (dept_id);
CREATE INDEX IF NOT EXISTS idx_auth_user_tenant_relation ON auth_user_tenant (relation_tenant_id);
CREATE INDEX IF NOT EXISTS idx_auth_user_tenant_tenant_id ON auth_user_tenant (tenant_id);
CREATE UNIQUE INDEX IF NOT EXISTS uk_auth_user_tenant_user_relation ON auth_user_tenant (user_id, relation_tenant_id);
COMMENT ON TABLE auth_user_tenant IS '认证用户租户关联表';
COMMENT ON COLUMN auth_user_tenant.user_id IS '用户ID';
COMMENT ON COLUMN auth_user_tenant.relation_tenant_id IS '关联租户ID';
COMMENT ON COLUMN auth_user_tenant.dept_id IS '当前租户内部门ID';
COMMENT ON COLUMN auth_user_tenant.default_tenant IS '是否默认租户';
COMMENT ON COLUMN auth_user_tenant.code IS '编码';
COMMENT ON COLUMN auth_user_tenant.description IS '说明';
COMMENT ON COLUMN auth_user_tenant.create_date_time IS '创建时间';
COMMENT ON COLUMN auth_user_tenant.create_name IS '创建人';
COMMENT ON COLUMN auth_user_tenant.modify_date_time IS '修改时间';
COMMENT ON COLUMN auth_user_tenant.modify_name IS '修改人';
COMMENT ON COLUMN auth_user_tenant.is_delete IS '删除状态';
COMMENT ON COLUMN auth_user_tenant.type IS '类型（0：默认）';
COMMENT ON COLUMN auth_user_tenant.state IS '状态（1：启用，2：禁用）';
COMMENT ON COLUMN auth_user_tenant.label IS '标签';
COMMENT ON COLUMN auth_user_tenant.sorting IS '排序';
COMMENT ON COLUMN auth_user_tenant.version IS '版本号';
COMMENT ON COLUMN auth_user_tenant.tenant_id IS '租户id';
