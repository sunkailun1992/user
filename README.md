# User 服务说明

`user` 是用户中心微服务，负责真实用户体系、认证授权、租户、角色和权限资源管理。

个人公共库固定为 `com:utils:1.6.7`、`com:rpc-api:1.0.9`。个人与美联共用同一阿里云私有 Maven 地址：个人沿用 `com` group，公司保留 `com.kellen` group；artifact 名仍为 `utils` / `rpc-api`，Java package 不随 Maven 坐标改变。公共库须先完成 Maven Local 验证并独立发布私库，消费者再按精确坐标拉取；已有同版制品不得覆盖。

## 启动入口

主应用类为 `com.kellen.UserApplication`，IDE 可直接运行该类。Gradle `bootRun` 与可执行 JAR 的入口由 `springBoot.mainClass` 统一指定；仍使用 `./gradlew bootRun` 或 `java -jar build/libs/user.jar`，工作目录保持为当前服务仓库根目录。

## 技术基线

- Java 21
- Spring Boot 4.0.9
- Spring Cloud 2025.1.4
- Spring Cloud Alibaba 2025.1.0.0
- Nacos Client 3.3.0
- Seata Server 2.7.0
- Seata Client 2.6.0
- MyBatis-Plus
- 公共能力依赖 `com:utils`
- 跨服务契约依赖 `com:rpc-api:1.0.9`

## 服务职责

当前服务负责以下业务：

- 租户管理
- 部门管理
- 用户管理
- 角色管理
- 权限资源管理
- 用户角色授权
- 角色资源授权
- 角色数据范围授权
- 登录认证
- 当前用户资源查询

`user` 与 `message` 是同级独立业务模块，可通过 API 互相调用；公共工具、认证上下文、多租户、统一返回、统一异常、数据权限、MyBatis-Plus 公共配置等底层能力统一复用同级 `utils` 项目，不在业务服务内重复实现。

## 认证入口

认证入口在 `com.kellen.auth.controller.AuthController`：

| 方法 | 地址 | 说明 |
| --- | --- | --- |
| `GET` | `/auth/tenants` | 登录前公开查询租户列表，用于前端租户下拉选择 |
| `POST` | `/auth/sessions` | 创建登录会话 |
| `POST` | `/auth/third-party/sessions` | 企业内部三方 HMAC 签名换统一 JWT，保留给 TduckX 等私有集成 |
| `POST` | `/oauth2/token` | OAuth2 client_credentials 换短期 access token，供 MCP 等机器到机器接入 |
| `GET` | `/.well-known/oauth-authorization-server` | OAuth 授权服务器发现 |
| `GET` | `/auth/current/resources` | 查询当前用户权限资源 |

`GET /auth/tenants`、`POST /auth/sessions`、`POST /auth/third-party/sessions`、`POST /oauth2/token` 和 `/.well-known/**` 不加 `@PreAuthorize`。

授权服务器 metadata 的 `token_endpoint` 应通过 `oauth.authorization-server.external-base-url` 指向外部可访问的 gateway / auth 地址，本地可用 `${OAUTH_AUTHORIZATION_SERVER_EXTERNAL_BASE_URL:http://localhost:7100}`。

如果 `security.auth.enabled=true`，需要在 Nacos `security.auth.permit-urls` 中放行：

```text
/auth/tenants
/auth/sessions
/auth/third-party/sessions
/oauth2/token
/.well-known/**
```

## OAuth2 机器到机器接入

新外部系统和 MCP 客户端优先走 OAuth2 `client_credentials`，不要长期配置一次性 `Bearer JWT`。

调用方式：

```bash
curl -X POST 'http://<gateway-or-user>/oauth2/token' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -u '<client_id>:<client_secret>' \
  -d 'grant_type=client_credentials' \
  -d 'scope=mcp.tools.read mcp.tools.call' \
  -d 'resource=mcp'
```

返回字段按 OAuth 标准使用蛇形命名：

```json
{
  "access_token": "<短期JWT>",
  "token_type": "Bearer",
  "expires_in": 900,
  "scope": "mcp.tools.read mcp.tools.call"
}
```

OAuth client 存在 `auth_oauth_client` 表：

| 字段 | 说明 |
| --- | --- |
| `client_id` | 外部系统长期配置的客户端 ID |
| `client_secret` | 客户端密钥，建议保存 BCrypt 哈希；兼容历史明文仅用于迁移 |
| `grant_types` | 当前支持 `client_credentials` |
| `scopes` | 允许的 scope，例如 `mcp.tools.read mcp.tools.call` |
| `audiences` | 允许的 resource/audience，例如 `mcp` |
| `access_token_ttl_seconds` | access token 有效秒数，服务端上限 30 天 |

### OAuth 客户端租户定位（1.3.4）

`client_id` 由全局唯一索引约束，不依赖匿名请求的 `X-Tenant-Id`。公开 token/authorize 入口仅通过专用 Mapper 投影查找启用、未删除客户端的主键和所属租户，再绑定该数据库租户，由正常 MyBatis-Plus 租户与逻辑删除插件读取完整客户端；专用投影不读取 secret，其他 Mapper 方法继续要求租户。未知、异常重复、禁用、已删除或缺少租户的 client 与错误 secret 均返回相同 `invalid_client`/401，不再回退到默认租户。

PKCE 授权页显示注册客户端的租户；提交的不同租户和认证用户的租户不匹配均拒绝，授权码交换还复核其已绑定的租户。成功、认证失败和 SQL 异常后恢复调用前的租户与 ignore 标志，不能让公开认证扩大为业务查询绕过。当前补丁只更新 User 为 `1.3.4`，OAuth 响应、scope、resource、JWT 与 RPC 契约保持。

`AuthOAuthTenantHttpIntegrationTest` 使用仅测试依赖 H2 `2.4.240` 的内存 PostgreSQL 模式，加载真实 Controller/Service、Security/ReqInterceptor、Mapper 与公共 MyBatis-Plus 插件；不连接 Nacos、Redis 或业务库。覆盖匿名 Basic token、伪造租户头、普通查询缺租户仍拒绝、上下文恢复、错误凭据/不可用 client、全局唯一与异常跨租户重复、完整 PKCE/一次性授权码及 client 迁租户后的拒绝。H2 仅证明该查询与拦截器链，不代替 PostgreSQL ICU 排序、实际部署或 MCP token→tools 联调；升级后应使用同一已注册 client 独立验收。

`/auth/third-party/sessions` 仍保留给 TduckX 等企业内部私有 HMAC 集成；未来如果统一认证治理，再双轨迁移到 OAuth，不要直接删除旧接口。

## 基础设施地址

除 `application.yml` 中连接 Nacos 自身的启动入口外，PostgreSQL、Redis、RabbitMQ、Seata、Elasticsearch、Kibana 等基础设施地址统一放在 Nacos `reuse-configuration.yaml`。

蒲公英、Tailscale、节点小宝等组网地址变化时，优先只修改 `custom.infra-host` 和 `custom.local-service-host`：

```yaml
custom:
  infra-host: <INFRA_HOST>
  infra-nacos-addr: ${custom.infra-host}:8848
  infra-postgresql-addr: ${custom.infra-host}:<POSTGRESQL_PORT>
  infra-redis-addr: ${custom.infra-host}:6379
  infra-rabbitmq-addr: ${custom.infra-host}:5672
  infra-seata-addr: ${custom.infra-host}:8091
  infra-elasticsearch-addr: ${custom.infra-host}:9200
  infra-elasticsearch-uri: http://${custom.infra-host}:9200
  infra-kibana-url: http://${custom.infra-host}:5601
  local-service-host: <LOCAL_SERVICE_HOST>
```

其他 Nacos 配置只引用公共变量，不直接写裸 IP：

中间件地址只由最终发布的个人 Nacos 配置契约提供，不恢复已退役执行器的回调配置。

各 `application-*.yml` 的 `custom.nacos-ip` 是读取远程配置前必须先使用的启动入口，不能依赖 `reuse-configuration.yaml`。

## 普通开发启动

默认 profile 为 `dev`。Spring Boot 会自动读取服务根目录下的 `config/application-dev.properties`：IDE Run/Debug 的 working directory 必须设为当前服务仓库根目录；`./gradlew bootRun` 使用项目目录；`java -jar build/libs/<service>.jar` 也从服务仓库根目录执行。base `application.yml` 保持原样，不增加私有 import。

该本机私有文件由同级 `utils/ops/github-dev` 的受控环境入口生成，权限保持 `0600`，仅保存 Nacos 引导键，已按精确路径忽略；不要提交地址、用户名或密码，也不要把 shell dotenv 原文直接当 properties。OS 环境变量优先于私有文件，原 launcher 的 env 注入仍然有效。dev 地址依次取 `GITHUB_NACOS_DEV_SERVER_ADDR`、`GITHUB_NACOS_SERVER_ADDR`，namespace 保留 `github-dev`；私有文件不存在且环境未提供地址时，启动必须失败，不再尝试本机 Nacos。

显式 `spring.profiles.active` 可覆盖默认 `APP_ENV=dev`；仅选择 `test` 或 `prod` 时不读取 dev 私有文件，使用各自 profile 的地址与 namespace。控制台入口端口 `18080` 与 SDK 配置/注册服务端口 `8848` 不可混用；SDK 还需能访问 gRPC 端口 `9848`。真实主机与凭据仅保存在本机私有配置。完整约定见 `docs/ai-coding/ENVIRONMENT_CONFIG_SPEC.md`。

## 前端跨域

本地 `admin-web` 直接请求 `http://localhost:7500`，不走 Umi dev proxy。后端通过 `com.kellen.config.CorsConfig` 注册最高优先级 `CorsFilter`，确保浏览器 `OPTIONS` 预检先于 Spring Security 通过。

默认允许来源：

```text
http://localhost:8000
http://127.0.0.1:8000
```

后续切换前端域名时，统一修改配置项：

```yaml
app:
  cors:
    allowed-origins:
      - http://localhost:8000
      - http://127.0.0.1:8000
```

## 接口文档

OpenAPI 原始文档地址：

```text
http://127.0.0.1:7500/v3/api-docs
```

第三方文档 UI 已移除，服务只保留标准 OpenAPI3 `/v3/api-docs`。调试需要鉴权的用户接口时，先调用 `POST /auth/sessions` 获取登录响应中的 `token`，再把 JWT 写入 `Authorization: Bearer <token>` 请求头。当前 OpenAPI 已声明 Bearer JWT 安全方案。调试 MCP 等机器到机器接口时，优先通过 `POST /oauth2/token` 换短期 access token。

登录示例：

```json
{
  "tenantId": "100",
  "username": "admin",
  "password": "<YOUR_PASSWORD>"
}
```

如果通过网关聚合文档访问，调试地址会带 `/user` 前缀；如果直接访问 `user` 服务文档，调试地址不带网关前缀。

Controller 必须使用 OpenAPI3 注解：

```java
@Tag(name = "用户管理", description = "维护认证用户、所属部门、启用状态和角色授权基础数据")
@Operation(summary = "分页查询用户", description = "按查询条件分页返回当前租户下的用户数据，用于用户管理列表")
```

这样 OpenAPI 文档会展示业务名称，避免出现 `auth-user-controller`、`list_1`、`save_1` 等默认名称。

## 管理接口

管理接口按业务资源拆分 Controller：

| Controller | 地址 |
| --- | --- |
| `AuthCodeController` | `/auth/manage/codes` |
| `AuthTenantController` | `/auth/manage/tenants` |
| `AuthDeptController` | `/auth/manage/depts` |
| `AuthUserController` | `/auth/manage/users` |
| `AuthRoleController` | `/auth/manage/roles` |
| `AuthResourceController` | `/auth/manage/resources` |
| `AuthGrantController` | `/auth/manage/users/{userId}/roles`、`/auth/manage/roles/{roleId}/resources`、`/auth/manage/roles/{roleId}/data-scope-depts` |

授权关系接口：

| 方法 | 地址 | 说明 |
| --- | --- | --- |
| `POST` | `/auth/manage/users/{userId}/roles` | 绑定用户角色 |
| `GET` | `/auth/manage/users/{userId}/roles` | 查询用户已绑定角色 ID 列表 |
| `PUT` | `/auth/manage/users/{userId}/roles` | 按完整角色 ID 列表同步用户角色 |
| `POST` | `/auth/manage/roles/{roleId}/resources` | 追加绑定单个角色资源 |
| `GET` | `/auth/manage/roles/{roleId}/resources` | 查询角色已绑定资源 ID 列表 |
| `PUT` | `/auth/manage/roles/{roleId}/resources` | 按完整资源 ID 列表同步角色资源 |
| `GET` | `/auth/manage/roles/{roleId}/data-scope-depts` | 查询角色自定义数据范围部门 ID 列表 |
| `PUT` | `/auth/manage/roles/{roleId}/data-scope-depts` | 按完整部门 ID 列表同步角色自定义数据范围 |

租户、部门、用户、角色、权限资源维护接口均提供：

| 方法 | 地址 | 说明 |
| --- | --- | --- |
| `GET` | 当前资源地址 `/options` | 非分页轻量选择项列表，使用 `*Query` 承接查询条件，用于下拉、树、授权回显 |
| `GET` | 当前资源地址 | 分页查询，使用 URL 参数 `current` 和 `size` |
| `POST` | 当前资源地址 | 新增，使用 `*BO` |
| `PUT` | 当前资源地址 `/{id}` | 修改，路径 `id` 定位资源，租户内资源使用 `*BO.tenantId` 设置租户上下文，并使用 `*BO.version` 触发 MyBatis-Plus 乐观锁 |
| `DELETE` | 当前资源地址 `/{id}` | 逻辑删除，租户内资源使用 `tenantId` 查询参数设置租户上下文 |

集合辅助接口不是每个资源必写；只有页面或业务流程明确需要时才增加，例如：

| 方法 | 地址 | 说明 |
| --- | --- | --- |
| `GET` | 当前资源地址 `/count` | 按查询条件统计数量 |

`options` 表示当前资源的轻量选择项集合，适合下拉框、树选择器、授权回显等场景；如果接口返回完整管理列表，应使用分页 `GET 当前资源地址`，不要滥用 `/options`。

管理接口统一要求：

```java
@PreAuthorize("hasAuthority('user:auth:manage')")
```

编码生成接口：

| 方法 | 地址 | 说明 |
| --- | --- | --- |
| `POST` | `/auth/manage/codes` | 按 `target`、`resourceCategory`、`name` 创建租户、部门、角色或权限资源编码候选 |

编码生成规则统一在后端 `AuthCodeGenerateService` 维护，前端只调用接口，不自行拼接随机编码。当前目标值：

| target | 说明 | 编码形态 |
| --- | --- | --- |
| `TENANT` | 租户编码 | `tenant_<name>_<yyyyMMddHHmmss>_<随机4位>` |
| `DEPT` | 部门编码 | `dept_<name>_<yyyyMMddHHmmss>_<随机4位>` |
| `ROLE` | 角色编码 | `role_<name>_<yyyyMMddHHmmss>_<随机4位>` |
| `RESOURCE` + `FRONTEND` | 前端资源编码 | `menu:<name>:<yyyyMMddHHmmss>_<随机4位>` |
| `RESOURCE` + `BACKEND` | 后端资源编码 | `api:<name>:<yyyyMMddHHmmss>_<随机4位>` |

当前用户资源接口要求：

```java
@PreAuthorize("hasAuthority('user:auth:resources')")
```

## 权限码

当前服务内置后端权限码：

```text
user:auth:resources
user:auth:manage
user:auth:code-generate
user:auth:resource-tree
api:rag
rag:document:manage
rag:retrieval:use
rag:graph:view
rag:graph:manage
```

当前服务内置前端资源码：

```text
menu:tenant
menu:user
menu:role
menu:resource
menu:role:bind-resource
menu:resource:tree
menu:rag
menu:rag:document
menu:rag:graph
```

资源分类：

| 分类 | 说明 |
| --- | --- |
| `FRONTEND` | 前端菜单、页面、按钮 |
| `BACKEND` | 后端接口权限 |

登录返回的 `permissions` 用于后端权限判断，`frontendResources` 用于前端展示控制。RAG 增量脚本按每个现有租户创建稳定资源，并只让原本已拥有 `user:auth:manage` 的角色继承 RAG 权限；不依赖环境中的用户、角色或资源主键。

## 数据权限

数据权限由 `utils` 中的 MyBatis-Plus `DataPermissionInterceptor` 统一处理，`user` 服务负责维护登录用户所需的组织和授权数据。

当前模型：

| 表 | 说明 |
| --- | --- |
| `auth_dept` | 租户内部门树 |
| `auth_user.dept_id` | 用户所属部门 |
| `auth_dept.owner_user_id` | 部门负责人用户 |
| `auth_role.owner_user_id` | 角色负责人用户 |
| `auth_role.dept_id` | 角色归属部门 |
| `auth_role.data_scope` | 角色数据范围 |
| `auth_role_data_scope` | 角色自定义可见部门 |

标准数据范围：

| 值 | 说明 |
| --- | --- |
| `ALL` | 全部数据 |
| `SELF` | 仅本人数据 |
| `DEPT` | 本部门数据 |
| `DEPT_TREE` | 本部门及下级部门数据 |
| `CUSTOM` | 自定义部门数据 |

登录时会把以下字段写入 JWT，并返回给前端：

```text
deptId
dataScope
dataScopeDeptIds
```

网关 Header 透传时也使用同名语义：

```text
X-Dept-Id
X-Data-Scope
X-Data-Scope-Dept-Ids
```

Nacos 数据权限配置示例：

```yaml
security:
  data-permission:
    enabled: true
    default-user-column: owner_user_id
    default-dept-column: dept_id
    table-rules:
      auth_user:
        user-column: id
        dept-column: dept_id
      auth_dept:
        user-column: owner_user_id
        dept-column: id
      auth_role:
        user-column: owner_user_id
        dept-column: dept_id
```

说明：

- `@PreAuthorize` 仍负责接口权限。
- 租户隔离仍由租户插件负责。
- 数据权限只负责同一租户内的数据范围。
- 没有在 `table-rules` 声明的表不会自动追加数据权限条件。
- `auth_user` 的本人数据用用户表 `id` 匹配当前登录用户ID，部门范围用 `dept_id`。
- `auth_dept` 没有 `dept_id` 字段，部门范围用部门表自身 `id` 匹配可见部门ID；本人范围用 `owner_user_id`。
- `auth_role` 使用 `owner_user_id` 匹配本人范围，使用 `dept_id` 匹配部门范围。
- 新业务主表如果需要参与数据权限，默认设计 `owner_user_id` 和 `dept_id`；纯关系表、租户表、权限资源表没有明确负责人过滤语义时不强制添加。

## 代码拆分约定

真实业务代码需要按查询、写入、返回拆分：

| 类型 | 位置 | 职责 |
| --- | --- | --- |
| `*Query` | `entity.query` | 承接列表/分页查询条件 |
| `*BO` | `entity.bo` | 承接新增、修改、删除写入参数 |
| `*VO` | `entity.vo` | 对外返回，不直接返回 Entity |
| `*ServiceQuery` | `service.query` | 拼接公共查询条件、排序、指定字段 |
| `*ServiceResults` | `service.results` | 负责 Entity 转 VO、分页转换、枚举说明补充 |
| `*ServiceImpl` | `service.impl` / `service/impl` | 负责编排业务流程、事务、人工查询条件 |

查询链路统一为：

```java
Entity entity = GeneralConvertor.convertor(query, Entity.class);
QueryWrapper<Entity> queryWrapper = new QueryWrapper<>(entity);
serviceQuery.query(query, queryWrapper);
queryArtificial(query, queryWrapper);
Page<Entity> pageDO = mapper.selectPage(page, queryWrapper);
Page<VO> pageVO = serviceResults.toPageVO(pageDO);
```

## 统一返回

Controller 统一返回 `com.kellen.utils.response.ApiResponse`。

成功返回推荐：

```java
return ApiResponse.success(data);
```

失败返回推荐：

```java
return ApiResponse.fail(returnCode, "稳定错误提示");
```

接口响应字段：

| 字段 | 说明 |
| --- | --- |
| `success` | 是否成功 |
| `code` | 统一错误码 |
| `msg` | 错误码默认提示 |
| `data` | 业务返回数据 |
| `errorMessage` | 失败时的稳定错误提示 |
| `timestamp` | 服务端响应时间 |

## DDL 和默认数据

PostgreSQL 使用 `postgresql.*` / `postgresql-gray.*` 连接键与 `org.postgresql.Driver`，不回退到旧 MySQL 键。字符字段使用 ICU `fleet_general_ci` 保留常用大小写、重音不敏感的等值、唯一键和 `LIKE` 查询；这不等于完整复刻 MySQL Unicode 权重或尾空格规则，迁移前必须检查唯一键碰撞和尾空格。PG 18 的 nondeterministic collation 支持 `LIKE`，不要将现有查询改成不支持该排序规则的 `ILIKE` 或正则匹配。

真实 PG 结构回归通过受控进程环境提供 `FLEET_PG_TEST_URL`、`FLEET_PG_TEST_USERNAME`、`FLEET_PG_TEST_PASSWORD`；只允许 `fleet_pg_test_` 前缀的独立测试库，测试只创建并清理随机 schema。未提供时明确跳过，H2 回归不能代替 PostgreSQL 验收。凭据不得写进命令文本、仓库或测试报告。

表结构和默认基础数据由 MyBatis-Plus 自动 DDL 维护。

DDL 入口：

```text
src/main/java/com/kellen/bean/PostgresqlDdl.java
```

SQL 脚本按公共基础表、认证业务结构、缺失默认权限补齐排序：

```text
../utils/src/main/resources/db/postgresql/common-infra-schema.sql
src/main/resources/db/postgresql/user-schema.sql
src/main/resources/db/postgresql/user-bootstrap.sql
```

全新或空业务库首次启动前，必须先手动执行公共基础表和业务结构；Seata AT 会在 `DataSource` 初始化时先检查 `undo_log`。迁移现有数据时先仅建结构、迁入原有全部行，再启动应用；不要在迁移前执行 bootstrap 或复制旧 MySQL `ddl_history` 为新 PostgreSQL 任务完成证据。

`user-bootstrap.sql` 仅使用 `ON CONFLICT DO NOTHING` 补齐缺失默认租户、角色与权限，不覆盖既有密码、权限、角色数据范围或自定义标签。SHA-256 派生资源 ID 的算法保持不变；历史覆盖式权限 UPDATE 不在新入口重放。历史 MySQL SQL 保留，后续结构调整仍新增 PostgreSQL 脚本，不回改已执行或执行状态不明的文件。

默认数据包含：

- 默认租户：`tenantId = 100`
- 不预置演示部门，后续按个人实际组织创建。
- 不预置管理员或测试人员账号，也不保存固定密码。
- 通用管理员角色：`r_admin_100`，数据范围 `ALL`，没有人员或部门绑定。
- 默认权限资源和授权关系

## 个人数据库初始化边界

运行时 DDL 保留通用表结构、默认租户 `100`、无人员绑定的管理员角色 `r_admin_100` 及通用权限资源。首次启动不创建管理员、测试用户、测试部门、第二演示租户或任何固定密码；本轮也不自动创建人员账号。后续首次管理员创建必须通过明确授权的独立流程并使用独立强密码。

数据权限测试应使用测试隔离 fixture；不要把演示账号或密码 seed 加回运行时 `PostgresqlDdl` 脚本。

## 本地日志

服务日志统一使用 Spring Boot 日志配置和本地文件滚动策略。

公共日志配置在 Nacos：

```text
Data ID: logging.yml
Group: DEFAULT_GROUP
```

当前服务通过 `application.yml` 的 `spring.config.import` 加载该配置。后续微服务统一引入同一个 `logging.yml`，保证日志目录、日志格式和滚动策略一致。

推荐默认日志目录：

```text
${user.home}/logs/${spring.application.name}
```

可通过环境变量覆盖日志目录：

```bash
LOG_PATH=/data/logs
```

日志文件：

| 文件 | 说明 |
| --- | --- |
| `${spring.application.name}.log` | 全量业务日志 |
| `archive/*.log.gz` | 按日期和大小滚动后的历史日志 |

日志路径使用 Java 文件系统兼容写法，默认目录可在 macOS、Linux、Windows 下工作；生产、容器或服务器部署时建议显式设置 `LOG_PATH`。

Nacos `logging.yml` 推荐配置：

```yaml
logging:
  level:
    root: INFO
    com.kellen: INFO
    heartbeat: ERROR
    timer: ERROR
    org.apache.http.impl.conn.Wire: WARN
    org.elasticsearch.client.RestClient: ERROR
    com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor: ERROR
  file:
    name: ${LOG_PATH:${user.home}/logs}/${spring.application.name:${SERVICE_NAME:application}}/${spring.application.name:${SERVICE_NAME:application}}.log
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [${spring.application.name:${SERVICE_NAME:application}}] [%thread] [%X{traceId},%X{spanId}] %logger{96} [%line] - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [${spring.application.name:${SERVICE_NAME:application}}] [%thread] [%X{traceId},%X{spanId}] %logger{96} [%line] - %msg%n"
  logback:
    rollingpolicy:
      file-name-pattern: ${LOG_PATH:${user.home}/logs}/${spring.application.name:${SERVICE_NAME:application}}/archive/${spring.application.name:${SERVICE_NAME:application}}.%d{yyyy-MM-dd}.%i.log.gz
      max-file-size: 100MB
      max-history: 30
      total-size-cap: 10GB
```

如果某个服务需要调试业务日志，可以在该服务自己的 `user.yaml` 或对应服务配置中单独覆盖：

```yaml
logging:
  level:
    com.kellen: DEBUG
```

## AI 编码规范

AI 编码规范在：

```text
AGENTS.md
docs/ai-coding
```

`docs/ai-coding` 只放通用编码规范、示例和公共规范归档。当前服务的业务说明以本 README 为准。

## 验证命令

公共依赖固定为 `com:utils:1.6.2`。Actuator 管理端点使用公共包的独立令牌安全链；`security.actuator.token` 必须从个人 namespace 或环境注入，缺失时仅 health 可访问。普通登录和业务权限不使用该管理令牌。

个人新数据库的 `auth-oauth-client-schema.sql` 只创建通用 OAuth 表结构，不预置外部客户端、固定 secret 或正式回调域名。当前 admin-web 使用 `/auth/sessions`，无需 OAuth 客户端 seed；后续接入 OAuth 前端时，显式注册本地 public / none 客户端和实际 callback，并使用 PKCE。此脚本不会删除既有数据库中的客户端。

严格开放接口签名 RPC 使用 `com:rpc-api:1.0.8` 的 `verifyRequiredOpenApiSignature`；只有客户端明确启用签名且 HMAC、时钟和 nonce 唯一登记全部通过才返回 true。旧 `verifyOpenApiSignature` 保留可选签名契约，严格消费者不得回退调用。JWT 签名使用个人 namespace 的 `security.jwt.secret`（Base64 至少 32 字节），不使用公司环境或历史固定口令；OAuth 显式 issuer 仍由原参数控制。

```bash
./gradlew clean compileJava test
bash scripts/check-secrets.sh
```

## 本批可观测性接入

当前服务版本为 `1.3.4`，公共能力精确消费 `com:utils:1.6.2`，并由公共包传递引入轻量 `com:fleet-observability:0.1.2` 的安全 HTTP 终态事件。公共制品先完成 Maven Local 验证，再按对应 main 确定提交发布远端；目标环境的实际采集与全链读回单独验收。九服务共享 `actuator.yaml` 暴露 health/info/prometheus；除 health 外仍要求 `actuatorReq` 运维令牌，空值或错误值失败关闭。`logging.yml` 使用 ECS 固定字段白名单，未知 MDC、原始消息、正文和异常不进入采集结果；OTel agent 标准 `trace_id/span_id` 与业务会话 ID 分开。应用指标带固定 application/environment 标签，禁止动态业务 ID。

Admin/Zipkin 模板与三 profile 的 imports 已退役。服务 JVM 的可选 OTel agent 由统一启动入口负责，不通过全局 JAVA_TOOL_OPTIONS 注入 Gradle 或测试。部署时 Java 指标只由 Micrometer Prometheus 提供，OTEL_METRICS_EXPORTER 与 OTEL_LOGS_EXPORTER 均为 none；监控组件、配置发布和实际采集仍须独立验证。回滚须恢复匹配的制品与配置，先恢复旧 dataId 再回退旧消费者，不能用已删除的配置项假装完成回滚。

个人业务 PostgreSQL 迁移基线服务版本为 `1.3.0`，首版公共库为 `com:utils:1.6.0`（传递安全采集库 `com:fleet-observability:0.1.1`）。版本和本机构建通过不代表目标环境已切库，正式迁移与运行态验收另行记录。

个人 Java 服务已移除 XXL-JOB 依赖、执行器配置与演示任务，不再读取 `xxl-job.yaml`。Conductor OSS 作为 NAS 独立服务通过 REST 接入；当前未接入业务 Java 调度接口，不添加 Java 21 SDK 或虚构任务开关。其他环境仍使用的 NAS 容器不由本次个人源码退役处理。

## PostgreSQL 启动修复补丁

当前服务补丁版本为 `1.3.3`，公共依赖精确固定 `com:utils:1.6.2`，接入 PostgreSQL/Druid 超时单位修复及事务化、失败即拒绝启动的 DDL 执行。原定连接 3 秒、读取 30 秒预算保持，RPC 契约 `com:rpc-api:1.0.8` 与轻量采集库 `com:fleet-observability:0.1.2` 保持。公共制品、Jar 构建、服务器与 IDEA 原生启动须分别验证，修改依赖不代表目标环境已恢复。

本次公共观测补丁通过 `com:utils:1.6.2` 精确传递 `com:fleet-observability:0.1.2`，修复 Servlet Security 在 ERROR 分派中再次拒绝导致终态缺失的问题；认证和错误响应保持。源码与测试通过后，目标进程和追踪/日志联结仍须单独读回验收。

### 终端访问身份 RPC

`com:rpc-api:1.0.8` 新增 `AuthSessionRpcService.resolveAccessIdentity`，在认证中心校验 access JWT 的签名、issuer、有效期、jti、类型、撤销状态和 tokenVersion，并用精确主键复核启用账号、启用租户及既有默认租户/平台管理员/显式会员关系。只返回 userId/tenantId，不回传 token、权限或内部实体。该认证 bootstrap 不信任调用方 tenant attachment；最小认证查询短暂忽略租户和数据权限，finally 恢复调用前标志，不扩大为业务查询绕过。


### 角色资源授权回显的租户范围（1.3.5）

`GET /auth/manage/roles/{roleId}/resources?tenantId=...` 保留 `user:auth:manage` 管理权限，并在读取前复用 User 本地访问身份流程验证 access JWT 生命周期、启用账号和租户授权。同一签名 JWT 的用户、租户必须与已验证身份一致；自己的租户可按既有管理权限读取，跨租户回显还必须具有 `PLATFORM_SUPER_ADMIN` 声明。请求头中的管理员类别、权限和租户不能提升访问范围。未知、失效身份、生命周期后端失败及越权请求均拒绝。

通过校验后，服务只在本次角色资源查询内绑定目标租户，并清除旧 tenant-ignore 标志，让公共 MyBatis-Plus 租户插件与固定 SQL 的目标租户一致；成功、空结果、权限拒绝和 SQL 异常都恢复调用前的租户与 ignore 标志。逻辑删除及角色条件保持，普通 Mapper 仍要求租户。本补丁只修该 GET 的身份与查询范围，不代表相邻授权写接口已完成统一跨租户权限治理。

`AuthRoleResourceTenantHttpIntegrationTest` 使用真实 HTTP、Security/ReqInterceptor、User token 生命周期/本地身份实现、Mapper、公共租户插件与 H2；Redis 生命周期数据仅使用合成内存替身，不连接 Nacos 或业务库。覆盖平台跨租户、租户管理员同租户/他租户、伪造头、无权限、失效身份、隔离/逻辑删除以及上下文恢复；H2 不替代 PostgreSQL ICU 与实际部署验收。版本仅更新 User `1.3.5`，不修改 RPC、DDL 或其余业务服务。


### 授权关系 HTTP 管理入口的统一租户边界（1.3.6）

`AuthGrantController` 的全部八个 HTTP 入口（用户角色 POST/GET/PUT、角色资源 POST/GET/PUT、角色数据范围部门 GET/PUT）统一先调用 `AuthTenantManagementAccess`。该边界复用 1.3.5 的活跃 User 身份和同一签名 JWT 校验：必须有 `user:auth:manage`，同租户按既有管理权限操作，跨租户额外要求 `PLATFORM_SUPER_ADMIN`。BO/query 的 `tenantId` 仅表示待授权目标，不是调用方身份；请求头不能提升权限。检查在任何授权关系 Service/Mapper 调用之前完成，身份验证或其后端失败均拒绝。

原角色资源 GET 的目标租户绑定和精确上下文恢复保留；认证规则迁到统一 HTTP 边界，不再在 Service 内维护第二份 JWT 策略。内部 bootstrap 绑定及 Service 契约继续不依赖 HTTP token。此补丁只覆盖授权关系 Controller 的八个入口，不代表其他用户/角色/部门等 CRUD 已完成统一租户管理授权审计。

真实 HTTP/MP 回归逐入口验证租户管理员跨租户、失效 token、无管理权限、伪造头及本地身份查询失败均不执行目标授权关系 SQL；三张关系表的完整行（含 version/时间戳）保持不变。空资源同步越权原错误已在 H2 真实重现，修复后 403；同租户和平台跨租户读写正向保持。夹具加载实际公共字段填充器、租户插件和三类 Mapper，避免只凭 Mock Service 断言权限。运行态验收只操作本轮自有隔离 fixture，不向真实其他租户发修改请求。
