# User 服务说明

`user` 是用户中心微服务，负责真实用户体系、认证授权、租户、角色和权限资源管理。

## 技术基线

- Java 17
- Spring Boot 4.0.4
- Spring Cloud 2025.1.1
- Spring Cloud Alibaba 2025.1.0.0
- Nacos Client 3.2.2
- Seata Server 2.7.0
- Seata Client 2.6.0
- MyBatis-Plus
- 公共能力依赖 `com:utils`

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
| `GET` | `/auth/current/resources` | 查询当前用户权限资源 |

`GET /auth/tenants` 和 `POST /auth/sessions` 不加 `@PreAuthorize`。

如果 `security.auth.enabled=true`，需要在 Nacos `security.auth.permit-urls` 中放行：

```text
/auth/tenants
/auth/sessions
```

## 基础设施地址

除 `application.yml` 中连接 Nacos 自身的启动入口外，MySQL、Redis、RabbitMQ、Seata、XXL-JOB、Elasticsearch、Kibana 等基础设施地址统一放在 Nacos `reuse-configuration.yaml`。

蒲公英、Tailscale、节点小宝等组网地址变化时，优先只修改 `custom.infra-host` 和 `custom.local-service-host`：

```yaml
custom:
  infra-host: <INFRA_HOST>
  infra-nacos-addr: ${custom.infra-host}:8848
  infra-mysql-addr: ${custom.infra-host}:3306
  infra-redis-addr: ${custom.infra-host}:6379
  infra-rabbitmq-addr: ${custom.infra-host}:5672
  infra-seata-addr: ${custom.infra-host}:8091
  infra-xxl-job-admin: http://${custom.infra-host}:19090/xxl-job-admin
  infra-elasticsearch-addr: ${custom.infra-host}:9200
  infra-elasticsearch-uri: http://${custom.infra-host}:9200
  infra-kibana-url: http://${custom.infra-host}:5601
  admin-server-url: <ADMIN_SERVER_URL>
  zipkin-base-url: <ZIPKIN_BASE_URL>
  local-service-host: <LOCAL_SERVICE_HOST>
```

其他 Nacos 配置只引用公共变量，不直接写裸 IP：

```yaml
xxl:
  job:
    admin:
      addresses: ${custom.infra-xxl-job-admin}
    executor:
      ip: ${custom.local-service-host}
```

本地 `application.yml` 的 `custom.nacos-ip` 是读取远程配置前必须先使用的启动入口，不能依赖 `reuse-configuration.yaml`。

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

第三方文档 UI 已移除，服务只保留标准 OpenAPI3 `/v3/api-docs`。调试需要鉴权的接口时，先调用 `POST /auth/sessions` 获取登录响应中的 `accessToken`，再把 JWT 写入 `Authorization: Bearer <accessToken>` 请求头。当前 OpenAPI 已声明 Bearer JWT 安全方案。

登录示例：

```json
{
  "tenantId": "100",
  "username": "admin",
  "password": "123456"
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
```

当前服务内置前端资源码：

```text
menu:tenant
menu:user
menu:role
menu:resource
menu:role:bind-resource
menu:resource:tree
```

资源分类：

| 分类 | 说明 |
| --- | --- |
| `FRONTEND` | 前端菜单、页面、按钮 |
| `BACKEND` | 后端接口权限 |

登录返回的 `permissions` 用于后端权限判断，`frontendResources` 用于前端展示控制。

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

表结构和默认基础数据由 MyBatis-Plus 自动 DDL 维护。

DDL 入口：

```text
src/main/java/com/kellen/bean/MysqlDdl.java
```

SQL 脚本：

```text
../utils/src/main/resources/db/common-infra-schema.sql
src/main/resources/db/auth-schema.sql
```

全新或空业务库首次启动前，必须先在目标业务库手动执行同级 `../utils/src/main/resources/db/common-infra-schema.sql`，先建 `ddl_history` 和 Seata AT `undo_log`。Seata AT 会在 `DataSource` 初始化时先检查 `undo_log`，不能依赖应用首次启动自动创建该表。

当前 `MysqlDdl#getSqlFiles()` 按顺序声明 `db/common-infra-schema.sql` 和 `db/auth-schema.sql`。业务脚本由 MyBatis-Plus 执行并写入 `ddl_history`；正式环境后续变更仍必须先查当前数据库 `ddl_history`，已经执行过、可能执行过或无法确认执行状态的脚本不再回改，后续表结构和默认数据调整统一新增 SQL 脚本。

默认数据包含：

- 默认租户：`tenantId = 100`
- 默认部门：`dept_root_100`
- 默认管理员：`username = admin`，`password = 123456`
- 默认管理员角色：`admin`，数据范围 `ALL`
- 默认权限资源和授权关系

## 租户和数据权限测试账号

测试账号由 `src/main/resources/db/auth-schema.sql` 统一维护，所有账号密码统一为：

```text
123456
```

登录默认租户时，前端选择 `默认租户`；直接调用接口时可传：

```json
{
  "tenantCode": "default",
  "username": "org_all",
  "password": "123456"
}
```

默认租户 `100` 的部门树：

```text
默认部门 dept_root_100
├── 技术部 dept_tech_100
│   ├── 后端组 dept_backend_100
│   └── 前端组 dept_frontend_100
├── 财务部 dept_finance_100
└── 人事部 dept_hr_100
```

默认租户数据权限账号：

| 登录租户 | tenantCode | 用户名 | 密码 | 所属部门 | 角色数据范围 | 登录后 dataScope | 登录后 dataScopeDeptIds |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 默认租户 | `default` | `org_all` | `123456` | 默认部门 | `ALL` 全部数据 | `ALL` | 空 |
| 默认租户 | `default` | `org_self` | `123456` | 技术部 | `SELF` 仅本人数据 | `SELF` | 空 |
| 默认租户 | `default` | `org_dept` | `123456` | 技术部 | `DEPT` 本部门数据 | `CUSTOM` | `dept_tech_100` |
| 默认租户 | `default` | `org_tree` | `123456` | 技术部 | `DEPT_TREE` 本部门及下级部门 | `CUSTOM` | `dept_tech_100,dept_backend_100,dept_frontend_100` |
| 默认租户 | `default` | `org_custom` | `123456` | 财务部 | `CUSTOM` 自定义部门 | `CUSTOM` | `dept_backend_100,dept_finance_100` |

租户隔离测试账号：

| 登录租户 | tenantCode | 用户名 | 密码 | tenantId | 说明 |
| --- | --- | --- | --- | --- | --- |
| 测试租户 | `test-org` | `org200_all` | `123456` | `200` | 用于验证只能看到租户 `200` 的组织、用户、角色和资源 |

这些账号均绑定了后台管理所需的基础前端菜单和 `user:auth:manage` 后端权限，便于在前端页面直接测试租户隔离和数据范围回显。

注意：`DataPermissionInterceptor` 只会对配置在 `security.data-permission.table-rules` 中的表自动追加数据权限条件。使用上述账号验证业务数据过滤时，目标表必须具备租户字段，并在配置中声明本人字段和部门字段；例如 `auth_dept` 使用 `owner_user_id` 作为本人字段，使用 `id` 作为部门字段。

不要再新增 `/auth/init` 这类业务初始化接口。

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

```bash
./gradlew clean compileJava test
bash scripts/check-secrets.sh
```
