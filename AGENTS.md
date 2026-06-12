# AGENTS.md

本文件是 `user` 服务的 AI 编码入口。AI 修改本项目代码前，必须先阅读本文件，再按任务风险阅读 `README.md` 和 `docs/ai-coding` 下的规范。

## 项目定位

- 项目名称：`user`
- 项目类型：用户中心、认证、租户、角色、权限、数据范围后端
- 技术栈：Java 17、Spring Boot、Gradle、MyBatis-Plus、Nacos、`com:utils`
- 同级依赖：`../utils` 提供统一响应、认证上下文、多租户、错误码和公共工具；`../gateway` 负责路由；`../admin-web` 负责后台页面
- 核心风险：登录认证、JWT、租户隔离、角色权限、数据范围、用户状态、敏感字段、密码和 token 生命周期

## 修改前阅读顺序

任何代码修改前必须先阅读：

1. `README.md`：确认当前用户中心职责、接口、默认数据、权限模型和验证命令。
2. `docs/ai-coding/README.md`：确认 AI 编码入口和阅读顺序。
3. `docs/ai-coding/AI_CODING_GUIDE.md`：确认执行步骤、注释规则、测试和安全要求。
4. `docs/ai-coding/PROJECT_CODING_SPEC.md`：确认微服务分层、RESTful、权限、多租户、数据权限和 DDL 规范。
5. `docs/ai-coding/AI_ENGINEERING_GUARDRAILS.md`：确认风险分级、Definition of Done 和交付门禁。
6. `docs/ai-coding/SECURITY_CODING_SPEC.md`：涉及认证、权限、数据隔离、敏感字段、SQL、上传下载或测试安全时必须阅读。
7. `docs/ai-coding/UTILS_PUBLIC_SPEC.md`：涉及公共规范、错误码、数据库、乐观锁或 `utils` 能力时阅读。

## 项目边界

- `user` 负责认证登录、租户、用户、角色、权限资源、角色资源绑定、用户角色绑定和数据范围。
- 认证、租户、用户上下文必须复用 `utils` 当前公共能力，不恢复旧 token 用户对象或旧响应结构。
- `gateway` 只转发请求，不做业务级鉴权；`admin-web` 只展示和调用接口，不能替代后端权限校验。
- 权限资源、角色绑定、用户状态、租户切换和数据范围变更必须可审计、可回滚、可测试。

## AI 工程门禁

- 登录、刷新 token、退出、租户切换、用户禁用、角色权限、数据范围、密码和敏感字段相关改动默认高风险。
- 新增或修改功能前，必须按 `AI_AUTOMATION_WORKFLOW.md` 整理需求说明、验收标准和开发手册。
- 完成后必须按 `AI_ENGINEERING_GUARDRAILS.md` 做风险分级、Definition of Done、测试证据、安全检查、风险和回滚说明。
- 涉及权限、租户、数据范围或认证状态时，必须补充越权、无权限、租户不匹配、目标不存在和重复提交验证。

## 多智能体协作规则

- 子智能体可以并行分析 Controller、Service、Mapper、DDL、admin-web 调用、gateway 路由和 utils 公共能力。
- 不允许多个 worker 同时修改同一认证链路、权限模型、数据范围逻辑、DDL 脚本或 `utils` 公共 API。
- 最终认证边界、权限模型、租户隔离和测试结论必须由主智能体统一判断。

## 验证命令

按风险选择验证：

```bash
./gradlew clean compileJava -x test
./gradlew test
```

涉及 `utils` 版本、认证上下文、统一响应、租户或公共异常时，还需要验证 `../utils` 当前制品版本和消费者编译结果。

## 禁止事项

- 禁止信任前端传入的当前用户、租户、角色、权限、数据范围和用户状态字段。
- 禁止在普通接口返回密码、token、密钥、完整手机号、完整邮箱、身份证或内部异常堆栈。
- 禁止写死默认租户、默认用户、测试密码、Nacos 地址、数据库连接和本机路径。
- 禁止为了前端页面可用而绕过后端鉴权、授权、租户隔离、数据权限或乐观锁。
