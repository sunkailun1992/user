# AI 设计模式规范

本规范约束 AI 在 `user` 服务中选择、引入和调整设计模式的方式。目标是让认证、租户、用户、角色、权限资源和数据范围逻辑结构稳定，而不是为了模式增加抽象。

## 1. 总原则

- 先识别当前语言、框架和模块边界；本项目默认按 Java 17、Spring Boot、MyBatis-Plus、Gradle 和 `com.kellen:utils` 生态落地。
- 优先沿用当前分层：Controller、BO、Query、VO、Entity、Mapper、Service、ServiceQuery、ServiceResults。
- 设计模式必须服务用户中心边界：认证、租户、角色权限、数据范围、乐观锁、编码生成、RESTful 接口和 API 契约。
- 不允许为简单 CRUD 硬套 Factory、Manager、Abstract 层或过深继承。
- 公共工具、通用组件和跨微服务能力优先回到真实同级 `../utils`，不在 `user` 内复制公共源码。

## 2. 标准参考

- GoF 设计模式：Strategy、Adapter、Factory、Template Method、State、Builder 等。
- SOLID 原则：判断职责拆分、依赖方向和接口稳定性。
- GRASP 原则：判断业务职责应该放在哪个对象上。
- Martin Fowler《Patterns of Enterprise Application Architecture》：Service Layer、Repository/Mapper、DTO、Transaction Script 等企业应用结构。
- Domain-Driven Design：用于用户、租户、角色、权限、数据范围等上下文边界判断。
- Spring / MyBatis-Plus 官方惯例：依赖注入、事务、Mapper、Wrapper、配置和插件边界。

## 3. 本项目推荐模式

### Application Service / Service Layer

- Controller 只处理 HTTP 入参、权限注解和统一响应。
- Service 编排业务流程、事务、权限上下文和异常处理。
- 复杂查询放 ServiceQuery，返回转换放 ServiceResults。
- 不把认证、授权、租户或数据范围逻辑写进 Controller。

### Repository / Mapper

- Mapper 只处理数据库访问。
- 动态条件优先通过 ServiceQuery 和 MyBatis-Plus Wrapper 构建。
- Mapper XML 只放确实需要 SQL 表达的查询，不写业务决策。

### Strategy

适用不同编码生成目标、权限资源类型、数据范围类型和状态处理。

- 多种目标生成规则优先用策略或枚举分发表达。
- 不把所有分支堆在一个 Controller 方法里。
- 策略实现必须受权限和租户边界约束。

### Template Method / Pipeline

适用用户、租户、角色、资源等 CRUD 具有相同校验流程时。

- 可以抽公共校验步骤：参数校验、租户校验、唯一性校验、乐观锁、保存、审计。
- 只有重复流程稳定后才抽模板方法。
- 不用继承掩盖不同业务实体的权限差异。

### State

适用用户启用/禁用、角色状态、租户状态等状态迁移。

- 简单状态用枚举即可。
- 存在非法迁移、状态副作用或审计要求时，集中状态迁移规则。
- 禁止多个 Service 分散修改同一状态规则。

### Adapter

适用与 `message`、`gateway`、`admin-web` 或 `utils` 的 API/依赖边界。

- 互调走 API 契约，不复制对方业务代码。
- 公共能力通过 `com.kellen:utils` 依赖适配，不在 `user` 下嵌套其它项目副本。

### Builder

- BO、VO、Entity、权限树或复杂响应对象字段较多时可使用 Builder。
- 简单对象无需强制 Builder。
- Builder 不隐藏写库、发请求或鉴权副作用。

## 4. 谨慎或禁止使用

- 手写 Singleton：Spring Bean 已管理生命周期。
- Service Locator：优先构造器注入。
- 巨型 Manager：不要把用户、租户、角色、权限、数据范围全部塞进一个类。
- 过深继承：业务差异优先用组合、策略、ServiceQuery/ServiceResults。
- 反射式权限或字段更新：权限和字段必须显式、可审计。
- 模式先行重构：没有重复实现、稳定扩展点或真实维护痛点时不改结构。

## 5. 检查清单

- 是否符合 Controller、Service、Mapper、Entity、BO、Query、VO 分层？
- 是否复用了 `utils` 公共能力而非复制公共源码？
- 是否没有绕过认证、租户、数据范围、乐观锁和统一响应？
- 新模式是否解决真实重复、扩展点或风险隔离问题？
- 是否存在更简单的函数、枚举、接口或组合方案？
- 是否补充了 Controller 请求层或关键 Service 测试？
