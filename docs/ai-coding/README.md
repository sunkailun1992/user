# AI 编码规范入口

本目录是 AI 编码规范入口。AI 新增或修改代码时，先读主规范，再按项目现有代码实现；历史 FreeMarker 和迁移资料只作为归档参考。

## 快速阅读

1. 先读 `AI_CODING_GUIDE.md`，确认执行步骤和禁止事项。
2. 再读 `PROJECT_CODING_SPEC.md`，确认通用分层、返回值、权限、多租户、注释和检查清单。
3. 涉及错误码、乐观锁、数据库变更、分支流程时，读 `UTILS_PUBLIC_SPEC.md`。
4. 当前微服务业务改造时，读项目根目录 `README.md`。
5. 新增业务模块时参考 `examples/`，不要复制 `legacy-ftl/` 生成模板。
6. 需要追溯原始迁移资料时再看 `archive/`。

## 目录结构

```text
docs/ai-coding/
  README.md
  AI_CODING_GUIDE.md
  PROJECT_CODING_SPEC.md
  UTILS_PUBLIC_SPEC.md
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
  archive/
    utils-markdown/
      Announcement.md
      ErrorCode.md
      Specification.md
```

## 必读结论

- 直接写 Java 代码，不再新增或依赖 FreeMarker 模板。
- 新代码使用 `com.kellen` 包名。
- Controller 返回统一使用 `com.kellen.utils.Json`。
- 实体公共字段继承 `com.kellen.bean.EntityBase`。
- `type/state` 等业务状态字段由具体业务模块定义 `IEnum`，不要塞进 `EntityBase`。
- 多租户和逻辑删除由框架处理，业务查询不要重复拼 `tenant_id` 或 `is_delete = 0`。
- 权限接口使用 `@PreAuthorize("hasAuthority('权限码')")`。
- `examples/` 示例按历史 `legacy-ftl` 分层编写，类、字段、方法和关键逻辑都保留注释，AI 写代码时优先模仿该风格。
- AI 新增或修改 Java 代码时，新增类、字段、方法、方法参数、关键分支、关键赋值、关键返回值都要写清楚注释；复杂或框架衔接逻辑按行补充行尾注释。
- `UTILS_PUBLIC_SPEC.md` 是公共规范的 AI 阅读入口；`archive/` 只保留迁移资料，不作为默认阅读内容。

## 复制规则

- 复制到其他微服务时，可以复用本目录下的 `README.md`、`AI_CODING_GUIDE.md`、`PROJECT_CODING_SPEC.md`、`UTILS_PUBLIC_SPEC.md`、`examples/`。
- 当前微服务上下文写在项目根目录 `README.md`，复制规范到其他微服务时不要把当前服务 README 当成通用规范。
- `PROJECT_CODING_SPEC.md` 不写具体业务接口、默认账号、当前服务权限码等服务私有信息。
