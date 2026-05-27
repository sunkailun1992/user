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
- 用户管理
- 角色管理
- 权限资源管理
- 用户角色授权
- 角色资源授权
- 登录认证
- 当前用户资源查询

## 认证入口

认证入口在 `com.kellen.auth.controller.AuthController`：

| 方法 | 地址 | 说明 |
| --- | --- | --- |
| `POST` | `/auth/login` | 用户登录 |
| `GET` | `/auth/resources` | 查询当前用户权限资源 |

`POST /auth/login` 不加 `@PreAuthorize`。

如果 `security.auth.enabled=true`，需要在 Nacos `security.auth.permit-urls` 中放行：

```text
/auth/login
```

## 管理接口

管理接口按业务资源拆分 Controller：

| Controller | 地址 |
| --- | --- |
| `AuthTenantController` | `/auth/manage/tenants` |
| `AuthUserController` | `/auth/manage/users` |
| `AuthRoleController` | `/auth/manage/roles` |
| `AuthResourceController` | `/auth/manage/resources` |
| `AuthGrantController` | `/auth/manage/user-roles`、`/auth/manage/role-resources` |

租户、用户、角色、权限资源维护接口均提供：

| 方法 | 地址 | 说明 |
| --- | --- | --- |
| `GET` | 当前资源地址 | 列表查询，使用 `*Query` 承接查询条件 |
| `POST` | 当前资源地址 `/page` | 分页查询，使用 `*Query.current` 和 `*Query.size` |
| `POST` | 当前资源地址 | 新增，使用 `*BO` |
| `PUT` | 当前资源地址 | 修改，使用 `*BO.version` 触发 MyBatis-Plus 乐观锁 |
| `POST` | 当前资源地址 `/remove` | 逻辑删除，使用 `*BO.id` |

管理接口统一要求：

```java
@PreAuthorize("hasAuthority('user:auth:manage')")
```

当前用户资源接口要求：

```java
@PreAuthorize("hasAuthority('user:auth:resources')")
```

## 权限码

当前服务内置后端权限码：

```text
user:auth:resources
user:auth:manage
```

当前服务内置前端资源码：

```text
menu:tenant
menu:user
menu:role
menu:resource
```

资源分类：

| 分类 | 说明 |
| --- | --- |
| `FRONTEND` | 前端菜单、页面、按钮 |
| `BACKEND` | 后端接口权限 |

登录返回的 `permissions` 用于后端权限判断，`frontendResources` 用于前端展示控制。

## 代码拆分约定

真实业务代码需要按查询、写入、返回拆分：

| 类型 | 位置 | 职责 |
| --- | --- | --- |
| `*Query` | `entity.query` | 承接列表/分页查询条件 |
| `*BO` | `entity.bo` | 承接新增、修改、删除写入参数 |
| `*VO` | `entity.vo` | 对外返回，不直接返回 Entity |
| `*ServiceQuery` | `service.query` | 拼接公共查询条件、排序、指定字段 |
| `*ServiceResults` | `service.results` | 负责 Entity 转 VO、分页转换、枚举说明补充 |
| `*ServiceImpl` | `service.impl` | 负责编排业务流程、事务、人工查询条件 |

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
```

默认数据包含：

- 默认租户：`tenantId = 100`
- 默认管理员：`username = admin`，`password = 123456`
- 默认管理员角色：`admin`
- 默认权限资源和授权关系

不要再新增 `/auth/init` 这类业务初始化接口。

## AI 编码规范

AI 编码规范在：

```text
docs/ai-coding
```

`docs/ai-coding` 只放通用编码规范、示例和公共规范归档。当前服务的业务说明以本 README 为准。

## 验证命令

```bash
./gradlew clean compileJava -x test
```
