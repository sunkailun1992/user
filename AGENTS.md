# AGENTS.md

本文件是 `user` 服务的 AI 编码入口。AI 修改本项目代码前，必须先阅读本文件，再按任务风险阅读 `README.md` 和 `docs/ai-coding` 下的规范。

## 项目定位

- 项目名称：`user`
- 项目类型：用户中心、认证、租户、角色、权限、数据范围后端
- 技术栈：Java 17、Spring Boot、Gradle、MyBatis-Plus、Nacos、Dubbo、`com:utils`、`com:rpc-api`
- 项目关系：`user` 与 `message` 是同级独立业务模块，可通过 `../rpc-api` 中的 Dubbo 契约互相调用；两者底层统一依赖同级 `../utils` 公共工具项目
- 同级协作：`../rpc-api` 提供跨服务 RPC 接口和 DTO 契约；`../utils` 提供统一响应、认证上下文、多租户、错误码和公共工具；`../message` 提供消息能力；`../gateway` 负责路由；`../admin-web` 负责后台页面
- 核心风险：登录认证、JWT、租户隔离、角色权限、数据范围、用户状态、敏感字段、密码和 token 生命周期

## 修改前阅读顺序

任何代码修改前必须先阅读：

1. `README.md`：确认当前用户中心职责、接口、默认数据、权限模型和验证命令。
2. `docs/ai-coding/README.md`：确认 AI 编码入口和阅读顺序。
3. `docs/ai-coding/AI_CODING_GUIDE.md`：确认执行步骤、注释规则、测试和安全要求。
4. `docs/ai-coding/AI_DIRECTORY_STRUCTURE_GUIDE.md`：确认 Java 微服务目录、测试、资源、文档和跨项目边界。
5. `docs/ai-coding/AI_COMMENT_STYLE_GUIDE.md`：确认注释规范、自解释优先、禁止注释掉死代码和排版要求。
6. `docs/ai-coding/AI_DESIGN_PATTERN_GUIDE.md`：确认用户中心分层、Service、Mapper、Strategy、State 等设计模式边界。
7. `docs/ai-coding/BRANCHING_SPEC.md`：确认分支命名、短分支生命周期、release/hotfix、tag 和清理规则。
8. `docs/ai-coding/ENVIRONMENT_CONFIG_SPEC.md`：确认环境、Nacos namespace、Java profile 和前端/小程序边界。
9. `docs/ai-coding/VERSIONING_SPEC.md`：确认 `group = 'com'`、`version = '1.0.0'`、补丁递增和消费者同步规则。
10. `docs/ai-coding/RPC_API_CODING_SPEC.md`：涉及 Dubbo RPC provider、consumer、接口或 DTO 时必须阅读。
11. `docs/ai-coding/TESTING_SPEC.md`：确认业务模块 SpringBootTest、真实 HTTP 集成测试、测试库和 AssertJ 边界。
12. `docs/ai-coding/PROJECT_CODING_SPEC.md`：确认微服务分层、RESTful、权限、多租户、数据权限和 DDL 规范。
13. `docs/ai-coding/AI_ENGINEERING_GUARDRAILS.md`：确认风险分级、Definition of Done 和交付门禁。
14. `docs/ai-coding/SECURITY_CODING_SPEC.md`：涉及认证、权限、数据隔离、敏感字段、SQL、上传下载或测试安全时必须阅读。
15. `docs/ai-coding/UTILS_PUBLIC_SPEC.md`：涉及公共规范、错误码、数据库、乐观锁或 `utils` 能力时阅读。
16. `docs/ai-coding/NACOS_CONFIG_SPEC.md`：修改 Nacos 配置中心、共享 dataId 或 `application.yml` import 前必读。

## 项目边界

- `user` 负责认证登录、租户、用户、角色、权限资源、角色资源绑定、用户角色绑定和数据范围。
- 新增认证、租户、角色权限、数据范围、编码生成或跨服务 API 能力时，必须优先沿用 `docs/ai-coding/AI_DESIGN_PATTERN_GUIDE.md` 中的 Service Layer、Mapper、Strategy、State、Adapter 等项目适用模式。
- 认证、租户、用户上下文必须复用 `../utils` 当前公共能力，不恢复旧 token 用户对象或旧响应结构。
- 与 `message` 等业务模块互调时走 `../rpc-api` 中的 RPC 契约，不直接复制对方业务代码、接口或 DTO。
- `gateway` 只转发请求，不做业务级鉴权；`admin-web` 只展示和调用接口，不能替代后端权限校验。
- 新增本服务 OpenAPI 入口、调整服务前缀，或新增同级 Java 微服务需要接入网关时，必须同步检查 `../gateway` 的 Nacos `gateway-spring.yaml`；需要聚合到 Swagger UI 的服务要补业务路由和 `springdoc.swagger-ui.urls`，并验证对应网关文档路径与 `/swagger-ui/index.html`。
- 权限资源、角色绑定、用户状态、租户切换和数据范围变更必须可审计、可回滚、可测试。
- 不允许在 `user` 根目录嵌套 `utils`、`message`、`gateway`、`admin-web`、`ai` 等项目副本；需要改同级项目时切换到真实同级仓库。

## AI 工程门禁

- 登录、刷新 token、退出、租户切换、用户禁用、角色权限、数据范围、密码和敏感字段相关改动默认高风险。
- 新增或修改功能前，必须按 `AI_AUTOMATION_WORKFLOW.md` 整理需求说明、验收标准和开发手册。
- 完成后必须按 `docs/ai-coding/AI_ENGINEERING_GUARDRAILS.md` 做风险分级、Definition of Done、测试证据、安全检查、风险和回滚说明。
- 涉及权限、租户、数据范围或认证状态时，必须补充越权、无权限、租户不匹配、目标不存在和重复提交验证。
- 测试分层按 `docs/ai-coding/TESTING_SPEC.md` 执行；核心业务不能只靠 mock 或纯对象 `assertThat`，必须补 Spring Boot 级别测试。

## 多智能体协作规则

- 子智能体可以并行分析 Controller、Service、Mapper、DDL、admin-web 调用、gateway 路由、message API 契约和 utils 公共能力。
- 不允许多个 worker 同时修改同一认证链路、权限模型、数据范围逻辑、DDL 脚本或 `utils` 公共 API。
- 用户服务全新或空业务库首次启动前，必须先在目标业务库手动执行 `../utils/src/main/resources/db/common-infra-schema.sql`，再执行或放行 `db/auth-schema.sql`；Seata AT 会在 `DataSource` 初始化时先检查 `undo_log`，不能依赖应用首次启动自动创建该表。
- 最终认证边界、权限模型、租户隔离和测试结论必须由主智能体统一判断。

## 验证命令

按风险选择验证：

```bash
./gradlew clean compileJava -x test
./gradlew test
bash scripts/check-secrets.sh
```

涉及 `rpc-api` 契约、`utils` 版本、认证上下文、统一响应、租户或公共异常时，还需要先在同级 `../rpc-api` / `../utils` 发布 Maven Local，再验证 `user` 编译结果。

## 禁止事项

- 禁止信任前端传入的当前用户、租户、角色、权限、数据范围和用户状态字段。
- 禁止在普通接口返回密码、token、密钥、完整手机号、完整邮箱、身份证或内部异常堆栈。
- 禁止写死默认租户、默认用户、测试密码、Nacos 地址、数据库连接和本机路径。
- 禁止 AI 触碰真实密钥/凭证、数据库密码或默认账号口令（疑似密钥只能告警，由项目负责人处理）；配置中心结构性调整（dataId 拆分/合并、import 顺序、`${}` 引用、Nacos 接入地址/namespace/group）允许 AI 自主完成，但必须保值不改值，不得擅自变更生产业务配置的实际取值。
- 禁止为了前端页面可用而绕过后端鉴权、授权、租户隔离、数据权限或乐观锁。
- 禁止在 `user` 仓库内复制其它同级项目源码；业务 RPC 契约缺失时回到真实 `../rpc-api` 实现，公共能力缺失时评估是否回到真实 `../utils` 实现。
