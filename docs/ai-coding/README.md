# AI 编程规范入口

本目录是 AI 编码规范入口。项目后续以 AI 识别规范、阅读示例、直接编写 Java 代码为主，不再把规范文件放在运行时 `src/main/resources/templates` 目录。

## 阅读顺序

1. `README.md`：当前目录用途和文件索引。
2. `AI_CODING_GUIDE.md`：AI 执行编码任务时必须遵守的步骤。
3. `PROJECT_CODING_SPEC.md`：项目分层、字段、枚举、权限、多租户、统一返回等规范。
4. `examples/`：真实 Java 风格示例，AI 写新模块时优先参考。
5. `legacy-ftl/`：历史 FreeMarker 模板，仅作迁移参考，不再作为代码生成入口。
6. `utils-markdown/`：从 `utils` 项目整合过来的公共规范、公告、错误码和新人培训资料。

## 目录结构

```text
docs/ai-coding/
  README.md
  AI_CODING_GUIDE.md
  PROJECT_CODING_SPEC.md
  examples/
    ExampleBO.java
    ExampleEntity.java
    ExampleQuery.java
    ExampleVO.java
    ExampleMapper.java
    ExampleController.java
    ExampleService.java
    ExampleServiceImpl.java
    ExampleServiceQuery.java
    ExampleServiceResults.java
    ExampleStateEnum.java
  legacy-ftl/
    *.ftl
    config/
  utils-markdown/
    Announcement.md
    ErrorCode.md
    NewPeopleTraining.md
    Specification.md
```

## 当前关键原则

- 直接写 Java 代码，不再新增或依赖 FreeMarker 模板。
- 新代码使用 `com.kellen` 包名。
- Controller 返回统一使用 `com.kellen.utils.Json`。
- 实体公共字段继承 `com.kellen.bean.EntityBase`。
- `type/state` 等业务状态字段由具体业务模块定义 `IEnum`，不要塞进 `EntityBase`。
- 多租户和逻辑删除由框架处理，业务查询不要重复拼 `tenant_id` 或 `is_delete = 0`。
- 权限接口使用 `@PreAuthorize("hasAuthority('权限码')")`。
- `examples/` 示例按历史 `legacy-ftl` 分层编写，类、字段、方法和关键逻辑都保留注释，AI 写代码时优先模仿该风格。
- AI 新增或修改 Java 代码时，新增类、字段、方法、方法参数、关键分支、关键赋值、关键返回值都要写清楚注释；复杂或框架衔接逻辑按行补充行尾注释。
- `utils-markdown/` 是公共规范快照，AI 处理公共工具类、错误码、团队流程或历史风格时需要一并参考。
