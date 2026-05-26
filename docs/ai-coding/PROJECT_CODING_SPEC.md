# User 项目 AI 编码规范

## 目标

本规范是 `user` 项目的主编码规则。AI 编写代码时，应根据本文件和 `examples/` 下的示例直接创建或修改 Java 文件。

公共团队规则、错误码、数据库变更和乐观锁规则见 `UTILS_PUBLIC_SPEC.md`。归档原文见 `archive/utils-markdown/`，默认不需要阅读。

## 技术基线

- Java 17
- Spring Boot 3.2.4
- Spring Cloud 2023.0.1
- Spring Cloud Alibaba 2023.0.1.0
- MyBatis-Plus 3.5.15
- 包名：`com.kellen`
- Servlet 和 Validation 使用 `jakarta.*`

## 标准分层

新业务模块建议使用以下结构：

```text
com.kellen.<module>
  controller
  dto
  entity
    enums
  mapper
  service
  service.impl
```

复杂查询可以单独补：

```text
com.kellen.<module>.query
com.kellen.<module>.vo
```

## 公共字段

数据库表默认包含这些公共字段：

```sql
code varchar(255) DEFAULT NULL COMMENT '编码',
description varchar(255) DEFAULT NULL COMMENT '说明',
create_date_time datetime DEFAULT NULL COMMENT '创建时间',
create_name varchar(255) DEFAULT NULL COMMENT '创建人',
modify_date_time datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
modify_name varchar(255) DEFAULT NULL COMMENT '修改人',
is_delete bit(1) DEFAULT b'0' COMMENT '删除状态',
label varchar(255) DEFAULT NULL COMMENT '标签',
sorting int DEFAULT '0' COMMENT '排序',
version int DEFAULT '1' COMMENT '版本号',
tenant_id varchar(64) NOT NULL DEFAULT '1' COMMENT '租户id'
```

这些字段由 `utils` 中的 `com.kellen.bean.EntityBase` 承接。实体默认继承：

```java
public class Xxx extends EntityBase {
}
```

`type` 和 `state` 不放入 `EntityBase`。如果某张表需要 `type/state`，由业务模块自己定义字段和枚举。

## 枚举规范

业务枚举放在当前模块的 `entity.enums` 包中，并实现 `IEnum`。

整数枚举：

```java
public enum XxxStateEnum implements IEnum<Integer> {
    默认(0, "默认");

    private final Integer value;
    private final String desc;
}
```

字符串枚举：

```java
public enum XxxCategoryEnum implements IEnum<String> {
    FRONTEND("FRONTEND", "前端资源");

    private final String value;
    private final String desc;
}
```

禁止把业务枚举放进 `EntityBase`。

## 实体规范

实体使用 MyBatis-Plus：

```java
@Getter
@Setter
@TableName("xxx_table")
@Schema(description = "xxx对象")
public class Xxx extends EntityBase {
}
```

字段注释使用 `@Schema(description = "...")`。

如果字段是数据库枚举，Java 类型使用对应枚举，而不是裸 `Integer/String`。

## Mapper 规范

Mapper 继承 `BaseMapper`：

```java
public interface XxxMapper extends BaseMapper<Xxx> {
}
```

除非确实需要复杂 SQL，否则优先使用 MyBatis-Plus 条件构造器。

## Service 规范

Service 负责业务编排，Controller 不直接堆业务逻辑。

写操作需要事务：

```java
@Transactional(rollbackFor = Exception.class)
```

对象转换优先使用项目已有工具：

```java
GeneralConvertor.convertor(source, Target.class)
```

先查询后修改的业务必须带上查询得到的 `version`，由框架乐观锁能力校验并发写入，不能绕过版本号直接更新。

## 注释规范

新代码和本次修改代码必须保持高注释密度：

- 类必须有 JavaDoc，说明业务对象或组件职责。
- 字段必须有业务注释；实体字段优先使用 `@Schema(description = "...")`，必要时补充 JavaDoc。
- 方法必须有 JavaDoc，格式保持历史模板风格，包含用途、`@param`、`@return`、`@author`、`@DateTime`、`@email`。
- 方法参数必须说明业务含义；不能只写“参数”或重复变量名。
- 新增或修改的关键代码行必须有行尾注释，说明业务目的或框架衔接原因。
- 认证授权、租户、权限、Redis、动态数据源、版本校验、SQL 参数校验、事务、异常处理、返回值组装等逻辑必须逐行注释。
- 注释要解释“为什么”或“业务含义”，不要只翻译代码。
- 局部改造旧代码时，只要求本次改动行和相关方法补齐注释，不要无关重写整类历史代码。

## Controller 规范

Controller 返回统一使用：

```java
com.kellen.utils.Json
```

成功：

```java
return new Json<>(ReturnCode.成功, data);
```

失败：

```java
return new Json<>(ReturnCode.用户密码错误, null, "用户名或密码错误");
```

不要新建临时 `Map<String, Object>` 作为接口统一响应壳。具体业务对象可以是 DTO、VO 或 `Map`，但最外层必须是 `Json`。

## 权限规范

需要鉴权的接口使用：

```java
@PreAuthorize("hasAuthority('module:resource:action')")
```

权限码格式建议：

```text
模块:资源:动作
```

示例：

```text
user:tenant-demo:list
user:auth:resources
```

## 登录与资源规范

当前登录能力在 `com.kellen.auth`：

- `POST /auth/login`
- `POST /auth/init-demo`
- `GET /auth/resources`

资源分两类：

| 分类 | 说明 |
| --- | --- |
| `FRONTEND` | 前端菜单、页面、按钮 |
| `BACKEND` | 后端接口权限 |

登录返回的 `permissions` 用于后端权限判断，`frontendResources` 用于前端展示控制。

## 多租户规范

租户字段统一为：

```sql
tenant_id varchar(64) NOT NULL DEFAULT '1'
```

请求头：

```text
X-Tenant-Id: 100
```

规则：

- 插入时由 `MyMetaObjectHandler` 自动填充 `tenantId`。
- 查询时由 `TenantLineInnerInterceptor` 自动追加租户条件。
- 业务代码不要重复手写 `tenant_id = ?`。
- 需要跨租户查询时必须显式使用 `TenantContextHolder.ignore()`，并在 `finally` 清理。

## 逻辑删除规范

`EntityBase.isDelete` 使用 `@TableLogic`。

规则：

- 查询条件不要重复写 `is_delete = 0`。
- 删除优先使用 MyBatis-Plus 逻辑删除能力。

## AI 编码检查清单

AI 每次新增模块时必须检查：

- 是否使用 `com.kellen` 包名。
- 是否继承 `EntityBase`。
- 是否使用 `Json` 统一返回。
- 是否使用 `jakarta.*`。
- 是否给业务枚举实现 `IEnum`。
- 是否给数据库状态字段建立对应枚举，且枚举实现 `IEnum<Integer>` 或匹配的泛型类型。
- 是否避免把业务枚举塞进 `EntityBase`。
- 是否避免重复拼 `tenant_id` 和 `is_delete`。
- 是否在先查后改的更新逻辑中携带 `version`。
- 是否按 `UTILS_PUBLIC_SPEC.md` 选择或扩展错误码。
- 是否给受保护接口加 `@PreAuthorize`。
- 是否给新增或修改代码补齐类注释、字段注释、方法 JavaDoc 和关键行注释。
- 是否运行 `./gradlew clean compileJava -x test`。
