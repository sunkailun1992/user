# User 项目代码生成规范

## 目标

本规范用于说明 `src/main/resources/templates` 下 FreeMarker 模板的职责、生成代码分层、公共字段归属、枚举规则和接口返回约定。后续 AI 修改模板或新增业务模块时，应优先遵守本文件。

## 生成器配置

配置文件位于 `config/mysql.properties`：

```properties
database=user
url=jdbc:mysql://127.0.0.1/user?useUnicode=true&characterEncoding=UTF-8
username=root
password=root
packageName=com.kellen
```

`packageName` 是生成代码的基础包。当前项目已经迁移到 `com.kellen`，模板里不应再出现 `com.gb`。

## 分层结构

生成后的标准目录结构：

```text
com.kellen
  controller
  entity
    bo
    enums
    query
    vo
  mapper
  service
    impl
    query
    results
```

每层职责：

| 层 | 职责 |
| --- | --- |
| `controller` | HTTP 入口、参数校验、统一 `Json` 返回 |
| `entity` | 数据库实体，继承 `EntityBase` |
| `entity.bo` | 新增、修改、删除入参 |
| `entity.query` | 查询、分页、排序、字段选择入参 |
| `entity.vo` | 返回前端的渲染对象 |
| `entity.enums` | 业务枚举，必须实现 `IEnum` |
| `mapper` | MyBatis-Plus `BaseMapper` |
| `service` | 业务服务接口 |
| `service.impl` | 业务服务实现、事务、DO/BO/VO 转换 |
| `service.query` | 自动查询条件拼装 |
| `service.results` | 查询结果增强、分页 VO 转换 |

## 公共字段规范

历史公共字段由 `utils` 包的 `com.kellen.bean.EntityBase` 管理。实体模板 `skl_entity.java.ftl` 会继承 `EntityBase`，并跳过已由基类承接的字段，避免重复生成。

当前应由 `EntityBase` 统一管理的字段：

| 数据库字段 | Java 字段 | 说明 |
| --- | --- | --- |
| `id` | `id` | 主键，`ASSIGN_ID` |
| `code` | `code` | 编码 |
| `description` | `description` | 说明 |
| `create_date_time` | `createDateTime` | 创建时间，插入时自动填充 |
| `create_name` | `createName` | 创建人 |
| `modify_date_time` | `modifyDateTime` | 修改时间，插入/更新时自动填充 |
| `modify_name` | `modifyName` | 修改人 |
| `is_delete` | `isDelete` | 逻辑删除，`@TableLogic` |
| `label` | `label` | 标签 |
| `sorting` | `sorting` | 排序 |
| `version` | `version` | 乐观锁版本 |
| `tenant_id` | `tenantId` | 租户 ID，插入时自动填充 |

`type` 和 `state` 不放在 `EntityBase` 中统一枚举管理。原因是不同业务表的类型和状态含义不同，必须由各业务模块自己定义枚举字段。

如果某张表包含 `type/state`：

- 在该业务模块自己的 `entity.enums` 下生成或编写枚举。
- 枚举实现 `com.baomidou.mybatisplus.annotation.IEnum`。
- 实体类中字段类型应使用业务枚举，例如 `AuthTypeEnum type`、`AuthStateEnum state`。

## 枚举规范

枚举模板：`skl_entity_enums.java.ftl`

生成枚举必须遵守：

```java
public enum XxxEnum implements IEnum<Integer> {
    默认(0, "默认");

    private final Integer value;
    private final String desc;
}
```

如果数据库字段是字符串枚举，可以手写 `IEnum<String>`，例如认证资源分类：

```java
public enum AuthResourceCategoryEnum implements IEnum<String> {
    FRONTEND("FRONTEND", "前端资源"),
    BACKEND("BACKEND", "后端接口");
}
```

枚举放置原则：

| 枚举类型 | 放置位置 |
| --- | --- |
| 通用技术枚举 | `utils` |
| 业务状态/类型枚举 | 当前业务模块的 `entity.enums` |
| 表字段专属枚举 | 当前实体所在模块的 `entity.enums` |

禁止把业务状态枚举放入 `EntityBase`。

## 实体模板规范

模板：`skl_entity.java.ftl`

生成规则：

- 包名：`${packageName}.entity`
- 继承：`EntityBase`
- 表名：`@TableName("`${tableName}`")`
- 日期字段使用：

```java
@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
```

- 如果字段在 `enumsList` 中匹配，应生成对应枚举类型字段。
- 不重复生成 `EntityBase` 已经承接的公共字段。

## BO/VO/Query 规范

模板：

- `skl_entity_bo.java.ftl`
- `skl_entity_vo.java.ftl`
- `skl_entity_query.java.ftl`

BO 用于写操作入参：

- `id` 在 `Update` 和 `Remove` 分组中必填。
- 包含 `Save`、`Update`、`Remove` 校验分组接口。

VO 用于响应给前端：

- 可包含公共字段和业务字段。
- 可包含枚举字段，用于把数据库值转换成语义明确的枚举。

Query 用于查询：

- 默认带分页字段 `current`、`size`。
- 默认带排序字段 `collation`、`collationFields`。
- 默认带增强开关 `assignment`。
- 查询模板跳过 `tenantId`，租户条件由多租户插件统一拼接。

## 查询增强规范

模板：`skl_service_query.java.ftl`

该模板根据 `serviceQueryList` 生成自动查询条件。查询类型约定：

| type | 查询方式 |
| --- | --- |
| `0` | 等于查询 `eq` |
| `1` | 右模糊查询 `likeRight` |
| `2` | 为空查询 `isNull` |
| `3` | 不为空查询 `isNotNull` |
| `4` | 包含查询 `in` |
| `5` | 不包含查询 `notIn` |
| `6` | SQL 包含查询 `inSql` |
| `7` | SQL 不包含查询 `notInSql` |
| `8` | 区间查询 `between` |
| `9` | 非区间查询 `notBetween` |

注意：

- `tenant_id` 不应在手写查询中重复拼接。
- `is_delete` 已由 `@TableLogic` 控制时，不要重复手写 `is_delete = 0`。
- 人工补充条件放在 `ServiceImpl.queryArtificial(...)`。

## Service 规范

模板：

- `skl_service.java.ftl`
- `skl_serviceImpl.java.ftl`
- `skl_service_results.java.ftl`

标准方法：

| 方法 | 用途 |
| --- | --- |
| `pageEnhance` | 分页查询并转换 VO |
| `listEnhance` | 列表查询并转换 VO |
| `getOneEnhance` | 单条查询并转换 VO |
| `countEnhance` | 统计数量 |
| `saveEnhance` | 新增 |
| `updateEnhance` | 修改 |
| `removeEnhance` | 删除 |

转换工具统一使用：

```java
com.kellen.utils.GeneralConvertor
```

写操作默认使用事务：

```java
@Transactional(
    propagation = Propagation.REQUIRED,
    isolation = Isolation.DEFAULT,
    timeout = 36000,
    rollbackFor = Exception.class
)
```

结果增强统一放在 `service.results`，不要把关联查询和结果补全堆到 Controller。

## Controller 规范

模板：`skl_controller.java.ftl`

接口路径：

```java
@RequestMapping("/${tableName?replace("_","-")}")
```

标准接口：

| 方法 | HTTP | 路径 | 用途 |
| --- | --- | --- | --- |
| `select` | POST | `/select` | 分页查询 |
| `selectList` | POST | `/selectList` | 列表查询 |
| `selectOne` | POST | `/selectOne` | 单条查询 |
| `count` | POST | `/count` | 总数查询 |
| `save` | POST | `/save` | 新增 |
| `update` | PUT | `/update` | 修改 |
| `remove` | DELETE | `/remove` | 删除 |

返回结构必须使用：

```java
com.kellen.utils.Json
```

成功返回：

```java
return new Json(ReturnCode.成功, data);
```

错误返回优先选择 `ReturnCode` 中已有业务码。

## 多租户规范

租户字段：

```sql
tenant_id varchar(64) NOT NULL DEFAULT '1' COMMENT '租户id'
```

代码约定：

- 实体继承 `EntityBase` 后拥有 `tenantId`。
- 插入时由 `MyMetaObjectHandler` 根据 `TenantContextHolder` 自动填充。
- 查询时由 MyBatis-Plus `TenantLineInnerInterceptor` 自动拼接租户条件。
- 业务代码不要手写 `tenant_id = ?`，除非明确使用了忽略租户上下文。

请求头约定：

```text
X-Tenant-Id: 100
```

## 权限和登录规范

当前登录与权限能力在 `com.kellen.auth` 中：

- 登录接口：`POST /auth/login`
- 初始化演示数据：`POST /auth/init-demo`
- 当前用户资源：`GET /auth/resources`

权限资源分两类：

| 分类 | 含义 | 示例 |
| --- | --- | --- |
| `FRONTEND` | 前端菜单、页面、按钮资源 | `/system/user` |
| `BACKEND` | 后端接口权限码 | `user:tenant-demo:list` |

后端接口用 `@PreAuthorize` 控制：

```java
@PreAuthorize("hasAuthority('user:tenant-demo:list')")
```

前端资源由登录返回的 `frontendResources` 控制展示，后端权限由 `permissions` 和 `backendResources` 控制访问。

## AI 修改模板时的注意事项

1. 修改模板前先确认生成代码是否属于 Java 17 / Spring Boot 3。
2. 新代码包名使用 `com.kellen`。
3. Servlet 包在 Boot 3 中应使用 `jakarta.servlet`，旧模板如仍出现 `javax.servlet`，需要迁移时一并处理。
4. 统一返回用 `Json`，不要新增临时 `Map<String, Object>` 响应结构。
5. 状态和类型字段不要写死在 `EntityBase` 中，业务模块自己定义 `IEnum`。
6. 多租户、逻辑删除由框架插件处理，业务查询不要重复拼条件。
7. 生成模板内只保留稳定通用逻辑，特殊业务逻辑放到生成后的业务类中维护。

