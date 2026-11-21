# User 服务说明

`user` 是用户中心微服务，负责真实用户体系、认证授权、租户、角色和权限资源管理。

个人公共库固定为 `com:utils:2.0.6`、`com:rpc-api:2.0.0`。个人与美联共用同一阿里云私有 Maven 地址：个人沿用 `com` group，公司保留 `com.kellen` group；artifact 名仍为 `utils` / `rpc-api`，Java package 不随 Maven 坐标改变。公共库须先完成 Maven Local 验证并独立发布私库，消费者再按精确坐标拉取；已有同版制品不得覆盖。

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
- 跨服务契约依赖 `com:rpc-api:2.0.0`
- 登录与令牌：Keycloak 26（NAS，`github-dev` 域），本服务是 OAuth2 资源服务器（经 `com:utils` 自动配置）
- 消息队列：RocketMQ（经 `com:utils` 的消息模块）

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
- Keycloak 账号同步（登录、口令与令牌在 Keycloak）
- 业务上下文提供方：按访问令牌给出权限码、角色、部门、数据范围与可访问租户（Dubbo `SecurityContextRpcService`）
- 当前用户资源查询
- 新建账号后发“账号已创建”事件（RocketMQ），message 据此发欢迎站内信

`user` 与 `message` 是同级独立业务模块，可通过 API 互相调用；公共工具、认证上下文、多租户、统一返回、统一异常、数据权限、MyBatis-Plus 公共配置等底层能力统一复用同级 `utils` 项目，不在业务服务内重复实现。

## 认证与授权

第5阶段起登录、口令、令牌签发、注销与机器到机器凭证都在 Keycloak（`github-dev` 域，由 `../utils/ops/github-dev/keycloak/terraform` 管理）：浏览器经网关 BFF 登录（授权码加 PKCE），服务与脚本带 Bearer 访问令牌。user 不再签发或校验自签 JWT；登录、刷新、注销会话，第三方 HMAC 会话与开放接口签名校验，`/oauth2/*` 与发现文档，OAuth 客户端管理接口都已删除，不留兼容层。

user 自己是资源服务器（`com:utils:2.0.6` 自动配置）：校验访问令牌的签发方、受众 `github-api` 与有效期，按令牌里的 `user_id` 与请求头 `X-Tenant-Id` 取业务上下文（权限码、`ROLE_<角色编码>`、`ROLE_<管理员分类>`、部门、数据范围、可访问租户）。角色编码不能用管理员分类的保留名（`PLATFORM_SUPER_ADMIN`、`TENANT_ADMIN`），建、改角色时拒绝，拼权限时跳过残留的重名角色。其他服务经 Dubbo 调 user 的 `SecurityContextRpcService` 取同一份上下文；user 自己用本地实现。

当前用户接口在 `com.kellen.auth.controller.AuthController`：

| 方法 | 地址 | 说明 |
| --- | --- | --- |
| `GET` | `/auth/current/resources` | 当前用户的用户名与昵称，以及在活动租户下的前端菜单、后端权限码与可切换租户（登录在 Keycloak，admin-web 从这里拿当前用户的资料） |
| `GET` | `/auth/current/tenants` | 当前用户可切换的启用租户，切换时在请求头 `X-Tenant-Id` 里指定 |

两者都要求 `user:auth:resources`。未登录或令牌无效返回 401（A0220），停用、租户不可访问或缺权限返回 403（A0301），业务上下文暂时取不到返回 503（C0110）。

### 账号同步

admin-web 经 `/auth/manage/users` 建、改、停用、删除账号与重置口令时，user 经 Keycloak Admin REST API 同步（`KeycloakAccountClient`，客户端凭证 `github-user-admin`，服务账号只有 manage-users、view-users）：

- 建账号：在 Keycloak 建登录账号，带属性 `user_id`（业务用户 ID）、昵称（`firstName`）与临时口令，首次登录必须改口令；Keycloak 用户 ID 回填 `auth_user.idp_subject`。
- 改用户名或昵称、启停、重置口令（设为临时口令）、删除账号都同步到 Keycloak；用户名与昵称以 user 为准，用户在 Keycloak 账号控制台只能看不能改。
- Keycloak 调用失败时本地事务回滚；建好 Keycloak 账号后本地事务回滚的，补偿删除该账号。Keycloak 用户名全域唯一，与其他租户的用户重名时返回「用户名已存在」（A0111）。
- 还没有 Keycloak 账号的旧数据（账号迁移前）只改本地资料，这时改口令明确报错（A0201），不静默跳过。
- user 库不存口令（Flyway V6 删除 `auth_user.password`）。

### 业务上下文缓存

utils 的 `SecurityUserCache` 缓存业务上下文（本实例 30 秒，Redis 副本按用户版本号区分）。下面的改动由 `AuthSecurityContextInvalidator` 让受影响用户换版本，事务提交后才换，回滚不换（提交前换的话，并发请求可能读到旧数据又按新版本缓存）：

| 改动 | 受影响用户 |
| --- | --- |
| 用户建、改、删 | 该用户 |
| 用户角色授权 | 该用户 |
| 角色资源、角色数据范围部门、角色改删 | 角色下的用户 |
| 权限资源改删 | 绑定了该资源的角色下的用户 |
| 部门建改删、租户改删 | 归属与关联该租户的用户、平台超级管理员 |
| 新建租户 | 平台超级管理员 |

## 基础设施地址

除 `application.yml` 中连接 Nacos 自身的启动入口外，PostgreSQL、Redis、RocketMQ、Seata、Elasticsearch、Kibana 等基础设施地址统一放在 Nacos `reuse-configuration.yaml`。

蒲公英、Tailscale、节点小宝等组网地址变化时，优先只修改 `custom.infra-host` 和 `custom.local-service-host`：

```yaml
custom:
  infra-host: <INFRA_HOST>
  infra-nacos-addr: ${custom.infra-host}:8848
  infra-postgresql-addr: ${custom.infra-host}:<POSTGRESQL_PORT>
  infra-redis-addr: ${custom.infra-host}:6379
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

第三方文档 UI 已移除，服务只保留标准 OpenAPI3 `/v3/api-docs`。调试需要鉴权的接口时，把 Keycloak 签发的访问令牌（受众含 `github-api`）写入 `Authorization: Bearer <访问令牌>`；经网关调试时先在浏览器登录，网关会话代为带上令牌（写请求要带 CSRF 头）。当前 OpenAPI 已声明 Bearer JWT 安全方案。

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

`GET /auth/current/resources` 返回的 `permissions` 用于后端权限判断，`frontendResources` 用于前端展示控制。RAG 增量脚本按每个现有租户创建稳定资源，并只让原本已拥有 `user:auth:manage` 的角色继承 RAG 权限；不依赖环境中的用户、角色或资源主键。

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
| `traceId` | 当前请求的追踪号，没有时不输出 |
| `data` | 业务返回数据 |
| `errorMessage` | 失败时的稳定错误提示 |
| `timestamp` | 服务端响应时间 |

## DDL 和默认数据

PostgreSQL 使用 `postgresql.*` / `postgresql-gray.*` 连接键与 `org.postgresql.Driver`，不回退到旧 MySQL 键。字符字段使用 ICU `fleet_general_ci` 保留常用大小写、重音不敏感的等值、唯一键和 `LIKE` 查询；这不等于完整复刻 MySQL Unicode 权重或尾空格规则，迁移前必须检查唯一键碰撞和尾空格。PG 18 的 nondeterministic collation 支持 `LIKE`，不要将现有查询改成不支持该排序规则的 `ILIKE` 或正则匹配。

真实 PG 结构回归通过受控进程环境提供 `FLEET_PG_TEST_URL`、`FLEET_PG_TEST_USERNAME`、`FLEET_PG_TEST_PASSWORD`；只允许 `fleet_pg_test_` 前缀的独立测试库，测试只创建并清理随机 schema。未提供时明确跳过，H2 回归不能代替 PostgreSQL 验收。凭据不得写进命令文本、仓库或测试报告。

表结构和默认基础数据只由本服务的 Flyway 迁移维护：

```text
src/main/resources/db/migration/V1__baseline.sql             现有结构快照与默认数据，只在空库执行
src/main/resources/db/migration/V2__drop_ddl_history.sql     删除旧 DDL 方式的执行记录表
src/main/resources/db/migration/V3__rename_audit_columns.sql 审计列按实体基类改名
src/main/resources/db/migration/V4__add_create_dept.sql      租户表新增创建部门列 create_dept
src/main/resources/db/migration/V5__rename_create_by_update_by.sql 审计列 creator/updater 改为 create_by/update_by
src/main/resources/db/migration/V6__keycloak_accounts.sql    auth_user 加 idp_subject、删 password；删自研 OAuth 与第三方认证四张表
```

表结构只由本服务 `src/main/resources/db/migration` 的 Flyway 迁移管理；已提交的迁移文件不修改，改结构只新增 `V<下一个>__<说明>.sql`；新库保持空库，由服务首次启动时的 Flyway 建表，不手工执行任何公共脚本。已有的库由 `baseline-on-migrate` 把版本 1 登记为基线、不执行 V1，再执行 V2 起的迁移。Seata AT 的 `undo_log` 在 `V1__baseline.sql` 里；数据源块固定 `spring.datasource.dynamic.seata: true`，不设总开关；建池时 Seata AT 代理就检查该表，utils 在创建任何 Bean 之前先执行 Flyway 迁移，新空库首次启动即建出该表。

V1 的种子段仅用 `ON CONFLICT DO NOTHING` 补齐缺失默认租户、角色与权限，不覆盖既有权限、角色数据范围或自定义标签；SHA-256 派生资源 ID 的算法保持不变。

默认数据包含：

- 默认租户：`tenantId = 100`
- 不预置演示部门，后续按个人实际组织创建。
- 不预置管理员或测试人员账号，也不保存固定密码。
- 通用管理员角色：`r_admin_100`，数据范围 `ALL`，没有人员或部门绑定。
- 默认权限资源和授权关系

## 个人数据库初始化边界

迁移保留通用表结构、默认租户 `100`、无人员绑定的管理员角色 `r_admin_100` 及通用权限资源。首次启动不创建管理员、测试用户、测试部门、第二演示租户或任何固定密码；本轮也不自动创建人员账号。后续首次管理员创建必须通过明确授权的独立流程并使用独立强密码。

数据权限测试应使用测试隔离 fixture；不要把演示账号或密码 seed 加进 Flyway 迁移。

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

公共依赖固定为 `com:utils:2.0.6`。Actuator 管理端点使用公共包的独立令牌安全链；`security.actuator.token` 必须从个人 namespace 或环境注入，缺失时仅 health 可访问。业务接口只认 Keycloak 访问令牌，不使用该管理令牌。

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

### 授权关系管理入口的租户边界

`AuthGrantController` 的全部八个 HTTP 入口（用户角色 POST/GET/PUT、角色资源 POST/GET/PUT、角色数据范围部门 GET/PUT）在调用任何授权关系 Service/Mapper 之前先过 `AuthTenantManagementAccess`：当前用户由资源服务器按 Keycloak 访问令牌与业务上下文写入，必须有 `user:auth:manage`；目标租户是自己的活动租户时按既有管理权限操作，跨租户还要求 `ROLE_PLATFORM_SUPER_ADMIN`。BO/query 的 `tenantId` 只表示待授权目标；请求头里的管理员分类与权限一律不认，`X-Tenant-Id` 只用来选择活动租户，能否访问由 user 核对。

角色资源回显只在本次查询内绑定目标租户并清除旧 tenant-ignore 标志，成功、空结果、权限拒绝和 SQL 异常都恢复调用前的租户与 ignore 标志；普通 Mapper 仍要求租户。

`AuthRoleResourceTenantHttpIntegrationTest` 走真实 HTTP 与资源服务器链：测试解码器给出带 `user_id` 的令牌，业务上下文由 user 自己的提供方按 H2 数据计算，经 utils 的令牌转换、缓存与授权判断。逐入口验证租户管理员跨租户（含伪造头）、无管理权限、未登录、停用、归属租户停用以及业务上下文取不到（503）时都不执行授权关系 SQL，三张关系表的完整行保持不变；同租户与平台跨租户读写正向保持。访问令牌的撤销由 Keycloak 负责（短时效访问令牌加会话注销），资源服务器不查撤销名单。H2 不替代 PostgreSQL ICU 与实际部署验收。

### 账号已创建事件（1.4.5）

`POST /auth/manage/users` 新建账号、插入用户与租户关联后，`UserAccountEventProducer` 经 utils 的 `MqTemplate.sendAfterCommit` 在事务提交后发 `com.kellen.rpc.user.event.UserAccountCreatedEvent`（事件 ID、发生时刻、租户 ID、用户 ID、用户名与昵称，消息 key 为事件 ID）；同租户同用户名已存在时直接返回、不发。事务回滚不发，提交后发送失败只记日志、不影响建账号。主题读 Nacos `user-spring.yaml` 的 `fleet.mq.topic.user-account-created`，RocketMQ 地址、生产者组与账号也在该文件（见 utils 的消息队列规范 `docs/ai-coding/MQ_SPEC.md`）。message 收到后给新用户发欢迎站内信。依赖当时升级为 `com:utils:1.7.2`、`com:rpc-api:1.1.0`（第5阶段起为 2.0.0）。

### RocketMQ 客户端版本锁定（1.4.6）

`build.gradle` 照 Nacos、Seata 的写法把 `rocketmq-client`、`rocketmq-remoting`、`rocketmq-common` 锁到 `5.5.1`（与 NAS 上 5.5 服务端同一版本线）。Spring Cloud Alibaba 的 BOM 会把客户端管成 5.3.1，而 utils 排除了只发到 5.3.2 的 rocketmq-acl，5.3.1 里没有 ACL 签名类 `AclClientRPCHook`，配了 RocketMQ 账号的生产者建不起来，1.4.5 因此启动失败。`RocketMqClientCompatibilityTest` 锁住这一点。

### Keycloak 账号与资源服务器（1.5.0）

第5阶段（设计见 kellen-workspace 的 `docs/superpowers/specs/2026-11-20-phase5-keycloak-design.md`）：登录、口令与令牌交给 Keycloak，user 改为资源服务器加账号同步，依赖升级为 `com:utils:2.0.0`、`com:rpc-api:2.0.0`（两者都删除了自签 JWT 与旧认证契约）。

- 删除（不留兼容层）：登录、刷新、注销会话，第三方 HMAC 会话与开放接口签名校验，`/oauth2/*` 与发现文档，OAuth 客户端管理接口，令牌生命周期与第三方认证服务，`AuthSessionRpcService`、`AuthIdentityRpcService`、`AuthOpenApiRpcService` 三个 Dubbo 提供方；`/auth/tenants` 只给旧登录页用，一并删除。
- 新增：Dubbo 提供方 `SecurityContextRpcService`；Keycloak 账号同步与业务上下文缓存失效（见「认证与授权」）。
- Flyway V6：`auth_user` 加 `idp_subject`（Keycloak 用户 ID，唯一），删 `password`；删 `auth_oauth_client`、`auth_external_client`、`auth_external_identity`、`auth_external_signature_nonce`。迁移前用 `nas_pg.py backup` 备份 user 库；在副本库上核对过迁移后各业务表行数不变。已有账号的 Keycloak 账号由第5阶段切换时的账号迁移脚本建立并回填 `idp_subject`。
- 配置：资源服务器与 `keycloak-admin` 客户端注册在 Nacos `user-spring.yaml`（Keycloak 地址与客户端密钥是运行时占位符）；三套 profile 不再导入 `security-jwt.yaml`。
- 测试：`KeycloakAccountClientTest`（MockWebServer 模拟 Admin REST，版本跟随 Spring Cloud BOM 管理的 OkHttp）、`AuthUserServiceImplKeycloakTest`、`SecurityContextRpcServiceImplTest`、`AuthSecurityContextInvalidationTest`、`OldAuthEndpointsRemovedTest`、`PostgresqlSchemaLiveTest`（V6）与 `KeycloakAccountLiveTest`（NAS 上的真实 Keycloak，只在设置了 `FLEET_KC_TEST_USER_ADMIN_CLIENT_SECRET` 时运行，CI 自动跳过）。

### 角色编码保留名与取令牌超时（1.5.2）

- 角色编码不能用管理员分类的保留名（`PLATFORM_SUPER_ADMIN`、`TENANT_ADMIN`，忽略大小写与首尾空白）：业务上下文把角色拼成 `ROLE_<角色编码>`、把管理员分类拼成 `ROLE_<分类>`，重名会让租户管理员建一个这样的角色授给自己，就带上 `ROLE_PLATFORM_SUPER_ADMIN`（user 的跨租户管理与 ai 的平台控制面都按它判）。建角色、改角色时拒绝（`A0400`）；库里残留的重名角色在拼权限时跳过并记警告。做法同 RuoYi-Vue-Plus 的 `checkRoleAllowed`、芋道的 `validateRoleDuplicate`（不允许新角色用内置管理员标识）。2026-11-21 只读核对 `github_dev_user` 的 9 个角色，没有重名。
- 依赖升到 `com:utils:2.0.1`：取 Keycloak 管理令牌（客户端凭证）访问令牌端点有连接 5 秒、读取 10 秒超时，Keycloak 卡住时账号同步按失败处理，不再无限等待。
- 测试：`AuthRoleServiceImplReservedCodeTest`（建、改角色的保留名，先失败后通过）、`SecurityContextRpcServiceImplTest.roleCodeNamedAfterAnAdminTypeNeverGrantsThatRole`（库里已有的重名角色不给角色）。

### Nacos 账号不带默认值（1.5.3）

- 去掉 dev、test、prod 三套配置里 Nacos 账号与口令的默认值（原为 `nacos`，即 Nacos 管理员账号）：`custom.nacos-username`、`custom.nacos-password` 只引用 `GITHUB_NACOS_USERNAME`、`GITHUB_NACOS_PASSWORD`，缺值时启动即失败，不再静默以管理员登录（CWE-1392 默认凭据）；值由私有环境提供（IDEA 的 env 文件、Jar 的 application.properties 或容器同名环境变量）。

### utils 2.0.6（1.5.4）

- 依赖从 `com:utils:2.0.1` 升到 `2.0.6`：业务上下文的 Dubbo 引用改为独立自动配置、启动时不初始化（2.0.4、2.0.5；user 本地提供实现，原先就不受影响），受保护资源元数据声明授权服务器（2.0.3），没配受众时只收 `github-api`（2.0.6）。本服务代码不变；此前 Jar 打的仍是 2.0.1，与链路验证所用的 utils 源码不一致，这次对齐。
