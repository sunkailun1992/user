# User 服务说明

`user` 是用户中心微服务，负责真实用户体系、认证授权、租户、角色和权限资源管理。

## 技术基线

- Java 17
- Spring Boot 3.2.4
- Spring Cloud 2023.0.1
- Spring Cloud Alibaba 2023.0.1.0
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

## 认证入口

认证入口在 `com.kellen.auth.controller.AuthController`：

| 方法 | 地址 | 说明 |
| --- | --- | --- |
| `GET` | `/auth/tenants` | 登录前公开查询租户列表，用于前端租户下拉选择 |
| `POST` | `/auth/login` | 用户登录 |
| `GET` | `/auth/resources` | 查询当前用户权限资源 |

`GET /auth/tenants` 和 `POST /auth/login` 不加 `@PreAuthorize`。

如果 `security.auth.enabled=true`，需要在 Nacos `security.auth.permit-urls` 中放行：

```text
/auth/tenants
/auth/login
```

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
| `AuthGrantController` | `/auth/manage/user-roles`、`/auth/manage/role-resources`、`/auth/manage/role-data-scopes` |

授权关系接口：

| 方法 | 地址 | 说明 |
| --- | --- | --- |
| `POST` | `/auth/manage/user-roles` | 绑定用户角色 |
| `POST` | `/auth/manage/role-resources` | 追加绑定单个角色资源 |
| `GET` | `/auth/manage/role-resources` | 查询角色已绑定资源 ID 列表 |
| `PUT` | `/auth/manage/role-resources` | 按完整资源 ID 列表同步角色资源 |
| `GET` | `/auth/manage/role-data-scopes` | 查询角色自定义数据范围部门 ID 列表 |
| `PUT` | `/auth/manage/role-data-scopes` | 按完整部门 ID 列表同步角色自定义数据范围 |

租户、部门、用户、角色、权限资源维护接口均提供：

| 方法 | 地址 | 说明 |
| --- | --- | --- |
| `GET` | 当前资源地址 | 列表查询，使用 `*Query` 承接查询条件 |
| `POST` | 当前资源地址 `/page` | 分页查询，使用 `*Query.current` 和 `*Query.size` |
| `POST` | 当前资源地址 | 新增，使用 `*BO` |
| `PUT` | 当前资源地址 | 修改，租户内资源使用 `*BO.tenantId` 设置租户上下文，并使用 `*BO.version` 触发 MyBatis-Plus 乐观锁 |
| `POST` | 当前资源地址 `/remove` | 逻辑删除，租户内资源使用 `*BO.tenantId` 设置租户上下文，并使用 `*BO.id` 定位记录 |

管理接口统一要求：

```java
@PreAuthorize("hasAuthority('user:auth:manage')")
```

编码生成接口：

| 方法 | 地址 | 说明 |
| --- | --- | --- |
| `GET` | `/auth/manage/codes/generate` | 按 `target`、`resourceCategory`、`name` 生成租户、角色或权限资源编码 |

编码生成规则统一在后端 `AuthCodeGenerateService` 维护，前端只调用接口，不自行拼接随机编码。当前目标值：

| target | 说明 | 编码形态 |
| --- | --- | --- |
| `TENANT` | 租户编码 | `tenant_<name>_<yyyyMMddHHmmss>_<随机4位>` |
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
    default-user-column: create_name
    default-dept-column: dept_id
    table-rules:
      business_order:
        user-column: create_name
        dept-column: dept_id
```

说明：

- `@PreAuthorize` 仍负责接口权限。
- 租户隔离仍由租户插件负责。
- 数据权限只负责同一租户内的数据范围。
- 没有在 `table-rules` 声明的表不会自动追加数据权限条件。

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

Controller 统一返回 `com.kellen.utils.ApiResponse`。

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
src/main/resources/db/auth-schema.sql
src/main/resources/db/20260527-auth-resource-tree-data.sql
src/main/resources/db/20260527-auth-data-permission.sql
```

脚本由 MyBatis-Plus 按 `MysqlDdl#getSqlFiles()` 顺序执行，并写入 `ddl_history`。修改历史 SQL 前必须先查当前数据库 `ddl_history`；已经执行过、可能执行过或无法确认执行状态的脚本不再回改，后续表结构和默认数据调整统一新增 SQL 脚本。

默认数据包含：

- 默认租户：`tenantId = 100`
- 默认部门：`dept_root_100`
- 默认管理员：`username = admin`，`password = 123456`
- 默认管理员角色：`admin`，数据范围 `ALL`
- 默认权限资源和授权关系

不要再新增 `/auth/init` 这类业务初始化接口。

## 本地日志

服务日志统一使用 Spring Boot 日志配置和本地文件滚动策略。

公共日志配置在 Nacos：

```text
Data ID: logging.yml
Group: DEFAULT_GROUP
```

当前服务通过 `bootstrap.yml` 的 `extension-configs` 加载该配置。后续微服务统一引入同一个 `logging.yml`，保证日志目录、日志格式和滚动策略一致。

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
docs/ai-coding
```

`docs/ai-coding` 只放通用编码规范、示例和公共规范归档。当前服务的业务说明以本 README 为准。

## 验证命令

```bash
./gradlew clean compileJava test
```
