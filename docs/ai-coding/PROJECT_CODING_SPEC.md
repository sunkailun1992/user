# 微服务 AI 编码规范

## 目标

本规范是可复制到多个微服务的通用编码规则。AI 编写代码时，应根据本文件和 `examples/` 下的示例直接创建或修改 Java 文件。

公共团队规则、错误码、数据库变更和乐观锁规则见 `UTILS_PUBLIC_SPEC.md`。归档原文见 `archive/utils-markdown/`，默认不需要阅读。

当前微服务的接口、权限码、默认数据、业务边界和特殊约束写在项目根目录 `README.md`，不要写进本通用规范。

## Utils 边界规范

公共工具类、通用组件、基础配置和跨微服务复用能力统一放在同级 `utils` 项目维护，业务微服务只通过依赖引用。AI 编写代码前必须先检查 `utils` 是否已有可复用能力，优先复用已有工具，减少当前业务微服务代码量。

需要进入 `utils` 编写的场景包括：

- 通用返回对象、异常处理、错误码、认证安全、租户上下文、JWT、Redis、HTTP、JSON、对象转换、日期时间、加密解密等公共工具。
- MyBatis-Plus 公共配置、自动填充、乐观锁、租户插件、逻辑删除、DDL 公共能力等基础组件。
- MyBatis-Plus 数据权限插件、数据权限上下文、数据范围解析等跨微服务访问控制能力。
- AOP、幂等、防重复提交、请求日志、动态数据源、SQL 参数校验等跨业务服务复用能力。
- 多个微服务都会使用，或未来明显会复用的工具方法、注解、配置类、基础抽象。

规则：

- AI 不得因为当前业务项目临时缺少工具类，就直接在当前微服务新增 `utils`、`common`、`helper` 等公共包。
- 确认属于公共能力时，先检索同级 `utils` 项目已有实现；已有能力直接调用，不重复编写。
- `utils` 确实缺少能力时，才修改同级 `utils` 项目，执行 `mvn -q -DskipTests install`，再回到当前微服务升级或确认 `com:utils` 依赖版本。
- 业务微服务只保留当前业务专属代码，例如 Controller、Service、Mapper、Entity、BO、Query、VO、业务枚举和业务 SQL。
- 如果无法判断是否应进入 `utils`，先按“是否会被两个以上微服务复用”判断；会复用则进入 `utils`，不会复用才留在当前微服务。

## 技术基线

- Java 17
- Spring Boot 3.2.4
- Spring Cloud 2023.0.1
- Spring Cloud Alibaba 2023.0.1.0
- MyBatis-Plus 3.5.15
- 包名跟随当前微服务已有基础包结构。
- Servlet 和 Validation 使用 `jakarta.*`

## 标准分层

新业务模块建议使用以下结构：

```text
<base-package>.<module>
  controller
  dto
  entity
    bo
    vo
    enums
  mapper
  service
    query
    results
    impl
```

复杂查询和结果转换必须按职责补齐：

```text
<base-package>.<module>.service.query
<base-package>.<module>.service.results
<base-package>.<module>.service.impl
```

## 请求对象拆分规范

Controller 入参必须按接口语义保持清晰，不要把查询条件和写入参数混用。

推荐命名：

```text
XxxBO
XxxQuery
XxxBindRoleBO
XxxBindResourceBO
```

规则：

- 简单 CRUD 可以使用一个 `XxxBO`，通过 `Save`、`Update`、`Remove` 校验分组区分新增、修改、删除必填字段。
- 新增、修改、删除字段差异很大，或授权/绑定等语义独立时，再拆成 `XxxSaveBO`、`XxxUpdateBO`、`XxxRemoveBO`、`XxxBindBO` 等专项对象。
- 修改入参必须包含数据库旧 `version`，用于 MyBatis-Plus 乐观锁。
- 删除入参只校验删除所需字段，例如 `id` 和必要的业务校验字段。
- 查询条件使用 `Query`，不要和写入 BO 混用。
- 响应对象使用 `VO`，不要直接把包含密码等敏感字段的 Entity 返回给前端。
- Controller 只接收请求对象、调用 Service、组装 `ApiResponse`，不写业务规则、不写 SQL、不写初始化数据。
- Controller 必须按业务资源拆分，例如租户、用户、角色、资源、授权关系分别建 Controller，不要把多个资源维护接口塞进一个 `ManageController`。
- Service 必须按业务资源拆分，例如登录认证、基础数据、核心业务对象、资源对象、授权关系分别建 Service，不要把多个资源的逻辑塞进一个 `XxxService` 或 `XxxManageService`。
- Service 负责业务编排、事务、鉴权上下文、租户上下文和 Mapper 调用。
- ServiceQuery 负责 `QueryWrapper` 查询条件、排序、显示字段、通用关键字等查询增强。
- ServiceResults 负责 DO 转 VO、分页转换、枚举说明、关联信息补全等结果增强。
- Mapper 只负责数据访问，普通 CRUD 优先使用 MyBatis-Plus。

## DDL 维护规范

数据库结构使用 MyBatis-Plus 自动维护 DDL，不在 Controller 或 Service 中执行 `CREATE TABLE`。

项目 DDL 入口：

```text
com.kellen.bean.MysqlDdl
```

SQL 脚本位置：

```text
src/main/resources/db/*.sql
```

规则：

- 修改 `src/main/resources/db/*.sql` 前，必须先查看 `MysqlDdl#getSqlFiles()` 确认脚本列表和执行顺序。
- 修改任意已存在 SQL 脚本前，必须连接当前目标数据库查询 `ddl_history`，确认该脚本是否已经执行。
- 如果 `ddl_history` 已存在该脚本记录，禁止继续修改该 SQL 文件；表结构、默认数据、权限资源树等后续变更必须新增独立 SQL 脚本。
- 如果无法连接数据库、无法确认 `ddl_history`、或环境不明确，必须按“脚本可能已执行”处理，只能新增 SQL 脚本，不能修改历史 SQL。
- 只有明确确认 `ddl_history` 没有该脚本记录时，才允许修改该未执行脚本。
- `db/auth-schema.sql` 是原始建表和基础数据脚本，一旦在任何环境执行过，后续变更只能新增脚本，不允许把新变更继续塞回原脚本。
- 新增或修改表结构、默认数据、权限资源树时，新增独立 SQL 脚本并追加到 `MysqlDdl#getSqlFiles()`。
- `MysqlDdl#getSqlFiles()` 统一声明脚本路径。
- 基础字典、默认角色、默认权限资源等初始化数据也写入 DDL SQL 脚本。
- SQL 脚本必须支持重复执行，使用 `IF NOT EXISTS`、`INSERT IGNORE` 或具备明确租户/主键条件的确定性 `UPDATE`。
- MySQL 环境不一定支持 `ALTER TABLE ... ADD COLUMN IF NOT EXISTS`；新增字段需要兼容旧 MySQL 时，使用 `information_schema.COLUMNS` 判断字段是否存在，再通过 `PREPARE/EXECUTE` 执行 `ALTER TABLE`。
- 不再为基础数据新增业务初始化接口，避免启动后还需要人工调用初始化接口。
- 表结构必须包含 `version`，并由实体继承 `EntityBase.@Version`。
- 多租户业务表必须包含 `tenant_id`，业务 SQL 不手写租户条件。
- 需要参与数据权限的业务主表默认包含 `owner_user_id` 和 `dept_id`：`owner_user_id` 表示数据负责人或归属用户，`dept_id` 表示数据归属部门。
- `owner_user_id` 不要求无脑加到所有表；租户表、权限资源表、字典表、登录/初始化类配置表、纯关系表通常不加，除非业务明确需要按负责人过滤。
- 关系表优先通过主表做权限控制，例如用户角色、角色资源、角色数据范围这类关系表不要为了统一字段而强行补 `owner_user_id`。

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

数据归属字段：

```sql
owner_user_id varchar(64) DEFAULT NULL COMMENT '负责人用户ID',
dept_id varchar(64) DEFAULT NULL COMMENT '归属部门ID'
```

`owner_user_id` 和 `dept_id` 不属于所有表的硬性公共字段，只有需要数据权限过滤的业务主表才默认添加。本人数据优先使用 `owner_user_id`；部门数据使用 `dept_id`。如果表自身就是用户表或部门表，可以按业务语义使用主键字段作为数据权限列，例如 `auth_user.id`、`auth_dept.id`。

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

先查询后修改的业务必须带上数据库记录的 `version` 字段，由 MyBatis-Plus `@Version` 和 `OptimisticLockerInnerInterceptor` 校验并发写入，不能绕过版本号直接更新。

更新示例优先使用：

```java
int count = mapper.updateById(entity);
```

要求：

- BO 修改入参必须包含查询得到的旧 `version`。
- VO 查询响应必须返回当前 `version`，方便前端修改时原样提交。
- 不要用只按 `id` 拼接的 `UpdateWrapper` 替代 `updateById(entity)`，否则 AI 容易漏掉乐观锁旧版本号。
- 如确实使用 `update(entity, wrapper)`，必须保证实体中有旧 `version`，且 wrapper 不复用。

## 测试规范

项目统一使用 JUnit 5 编写测试，不再新增 JUnit4、Spock 或 Groovy 测试。

依赖基线：

```gradle
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation 'org.springframework.security:spring-security-test'
test {
    useJUnitPlatform()
}
```

规则：

- 新增或修改业务代码时，必须同步新增或更新测试用例。
- 测试类放在 `src/test/java`，包名与被测类保持一致。
- 测试类命名使用 `XxxTest`；集成测试可使用 `XxxIntegrationTest`，但不得默认依赖未准备好的外部服务。
- 接口功能测试必须优先从 Controller 请求层开始，使用 `MockMvc` 发起 HTTP 请求，验证请求参数、权限、统一响应、错误场景和 Controller 到 Service 的参数传递。
- Controller 请求层测试优先使用 `@WebMvcTest`，并使用测试最小配置隔离主启动类中的 MapperScan、WebSocket、调度任务、Nacos、数据库等基础设施。
- 纯转换类或工具类测试只能作为补充，不能替代接口请求层测试。
- 普通单元测试使用 JUnit 5、Mockito、AssertJ，不启动完整 Spring 容器。
- Service 测试优先 mock Mapper、外部客户端、Redis、MQ、Nacos、第三方服务，重点验证业务分支、事务边界、租户上下文、乐观锁版本号和异常路径。
- 只有确实需要验证 Spring 装配时才使用 `@SpringBootTest`，避免所有测试都启动完整应用。
- 外部依赖测试必须使用 test profile、mock、测试容器或显式开关，不能默认要求本机存在 MQ、Redis、Nacos、数据库等服务。
- 测试方法名表达业务语义，推荐 `shouldXxxWhenYyy` 风格。
- 断言必须验证关键输出和副作用，不允许只调用方法但没有断言。
- 新增代码涉及权限、多租户、乐观锁、异常处理、返回值转换时，测试必须覆盖至少一个正向场景和一个失败/边界场景。

## 注释规范

新代码和本次修改代码必须保持高注释密度：

- 类必须有 JavaDoc，说明业务对象或组件职责。
- 字段必须有业务注释；实体字段优先使用 `@Schema(description = "...")`，必要时补充 JavaDoc。
- 方法必须有 JavaDoc，格式保持历史模板风格，包含用途、`@param`、`@return`、`@author`、`@DateTime`、`@email`。
- 方法参数必须说明业务含义；不能只写“参数”或重复变量名。
- 新增或修改的关键代码行必须有行尾注释，说明业务目的或框架衔接原因。
- 认证授权、租户、权限、Redis、动态数据源、数据库乐观锁、SQL 参数校验、事务、异常处理、返回值组装等逻辑必须逐行注释。
- 注释要解释“为什么”或“业务含义”，不要只翻译代码。
- 局部改造旧代码时，只要求本次改动行和相关方法补齐注释，不要无关重写整类历史代码。

## Controller 规范

Controller 返回统一使用：

```java
com.kellen.utils.ApiResponse
```

成功：

```java
return ApiResponse.success(data);
```

Knife4j / OpenAPI3 文档规则：

- Spring Boot 3 项目使用 OpenAPI3 注解，Controller 类必须添加 `@Tag(name = "...", description = "...")`。
- Controller 方法必须添加 `@Operation(summary = "...", description = "...")`，`summary` 使用前端和测试人员能直接理解的业务动作，不使用 `list`、`save`、`update` 等裸方法名。
- 查询参数、路径参数、请求头参数语义不明显时，使用 `@Parameter` 或 `@Parameters` 说明业务含义、是否必填和来源位置。
- `@Tag.name` 按业务资源命名，例如“用户管理”“角色管理”“权限资源管理”，不要让 Knife4j 展示默认的 `xxx-controller`。
- `@Operation.summary` 必须唯一且可读，避免 Knife4j 生成 `list_1`、`save_1`、`update_1` 这类默认 operationId。
- DTO、BO、Query、VO、Entity 字段继续使用 `@Schema(description = "...")`，让请求体和响应体在 Knife4j 中可读。
- 接口鉴权需要通过 `@PreAuthorize` 和 OpenAPI 安全头共同表达；认证头由 `utils` 中统一 OpenAPI 配置维护，业务 Controller 不重复定义全局安全模型。

失败：

```java
return ApiResponse.fail(ReturnCode.用户密码错误, "用户名或密码错误");
```

接口响应字段统一为 `success`、`code`、`msg`、`data`、`errorMessage`、`timestamp`。不要新建临时 `Map<String, Object>` 作为接口统一响应壳。具体业务对象可以是 DTO、VO 或 `Map`，但最外层必须是 `ApiResponse`。

## 权限规范

需要鉴权的接口使用：

```java
@PreAuthorize("hasAuthority('module:resource:action')")
```

权限码格式建议：

```text
模块:资源:动作
```

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

## 数据权限规范

数据权限使用 `utils` 中的 MyBatis-Plus `DataPermissionInterceptor` 统一处理，业务微服务不得重复编写 SQL 拦截器。

职责边界：

- `@PreAuthorize` 控制接口是否允许访问。
- `TenantLineInnerInterceptor` 控制租户之间的数据隔离。
- `DataPermissionInterceptor` 控制同一租户内本人、部门、部门树或自定义部门的数据范围。

用户体系建议字段：

```text
用户表：dept_id
角色表：data_scope
角色自定义部门表：role_id、dept_id
业务主表：owner_user_id、dept_id
```

标准数据范围：

```text
ALL        全部数据
SELF       仅本人数据
DEPT       本部门数据
DEPT_TREE  本部门及下级部门数据
CUSTOM     自定义部门数据
```

规则：

- 部门归属挂在用户下，角色只维护授权能力和数据范围。
- 用户有多个角色时，登录或网关侧应合并数据范围；`ALL` 最大，其他部门范围按部门集合并集处理。
- 登录态或网关 Header 需要携带 `deptId`、`dataScope`、`dataScopeDeptIds`，供 `utils` 数据权限插件拼接 SQL。
- 业务表只有声明了数据权限表规则后才会追加条件，避免给没有 `owner_user_id` 或 `dept_id` 字段的表拼错 SQL。
- 新增需要数据权限控制的业务主表时，DDL、Entity、BO、VO、Query、ServiceQuery 和 Nacos `security.data-permission.table-rules` 要同步补齐 `owner_user_id` 与 `dept_id`。
- 本人数据默认按 `owner_user_id` 过滤；如果历史表没有该字段，必须在表规则中明确指定可替代的用户字段，例如 `auth_user.id`。
- 多租户字段仍由租户插件处理，数据权限不要重复拼 `tenant_id`。
- 不需要数据权限的初始化、登录、字典、资源树等查询，要通过配置或 `DataPermissionContextHolder.ignore()` 显式跳过。

Nacos 配置示例：

```yaml
security:
  data-permission:
    enabled: true
    default-user-column: owner_user_id
    default-dept-column: dept_id
    ignore-tables:
      - auth_tenant
      - auth_resource
    table-rules:
      auth_user:
        user-column: id
        dept-column: dept_id
      auth_dept:
        user-column: id
        dept-column: id
      business_order:
        user-column: owner_user_id
        dept-column: dept_id
```

`auth_user` 和 `auth_dept` 这类认证管理页面也必须走 `DataPermissionInterceptor` 的全局表规则，不要在 Service 查询方法里为每个页面手写数据范围过滤。`auth_dept` 没有 `dept_id` 字段时，使用部门表自身 `id` 作为 `dept-column`。

## 逻辑删除规范

`EntityBase.isDelete` 使用 `@TableLogic`。

规则：

- 查询条件不要重复写 `is_delete = 0`。
- 删除优先使用 MyBatis-Plus 逻辑删除能力。

## README 维护规范

AI 自动化新增或修改功能代码后，必须同步检查项目根目录 `README.md`。

规则：

- 如果项目根目录已有 `README.md`，需要补充本次新增或调整的业务说明、接口说明、权限码、DDL 脚本、默认数据、启动或验证命令。
- 如果项目根目录没有 `README.md`，必须新建并写入当前服务职责、核心接口、关键配置、数据库维护方式和验证命令。
- `README.md` 只写当前微服务真实业务上下文，不写可复制的通用 AI 编码规范。
- 通用 AI 编码规范继续放在 `docs/ai-coding`，不要把当前服务的私有接口、默认账号、权限码写回通用规范。

## AI 编码检查清单

AI 每次新增模块时必须检查：

- 是否跟随当前微服务已有基础包结构。
- 是否继承 `EntityBase`。
- 是否使用 `ApiResponse` 统一返回。
- 是否使用 `jakarta.*`。
- 是否给业务枚举实现 `IEnum`。
- 是否给数据库状态字段建立对应枚举，且枚举实现 `IEnum<Integer>` 或匹配的泛型类型。
- 是否避免把业务枚举塞进 `EntityBase`。
- 是否避免重复拼 `tenant_id` 和 `is_delete`。
- 是否把跨服务通用数据权限逻辑放到 `utils`，业务服务只维护用户部门、角色数据范围和业务表字段。
- 是否在先查后改的更新逻辑中携带数据库记录的 `version`，并依赖 MyBatis-Plus 乐观锁处理并发覆盖。
- 是否按 `UTILS_PUBLIC_SPEC.md` 选择或扩展错误码。
- 是否先检查同级 `utils` 项目已有能力，并优先复用已有公共工具以减少当前微服务代码量。
- 是否把确实缺失的公共工具类、通用组件、基础配置或跨微服务复用能力放到同级 `utils` 项目，而不是写进当前业务微服务。
- 是否给受保护接口加 `@PreAuthorize`。
- 是否给新增或修改代码补齐类注释、字段注释、方法 JavaDoc 和关键行注释。
- 是否同步补充或新建项目根目录 `README.md`。
- 是否同步补充或更新 JUnit 5 测试用例。
- 是否运行 `./gradlew clean compileJava test`。
