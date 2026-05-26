# AI 编码执行指南

## 使用方式

当用户要求新增或修改业务功能时，AI 应按以下顺序工作：

1. 阅读当前业务模块已有代码。
2. 阅读 `PROJECT_CODING_SPEC.md`。
3. 参考 `examples/` 下的示例。
4. 按项目现有结构直接创建 Java 文件。
5. 编译验证。

`examples/` 是当前 AI 编码的主要参考，不是可编译源码目录。示例需要贴近历史 `legacy-ftl` 的生成风格：完整分层、统一方法命名、类/字段/方法/关键逻辑注释齐全。

## 禁止事项

- 不再新增 `.ftl` 模板。
- 不再依赖 FreeMarker 生成代码。
- 不要把业务状态枚举加入 `EntityBase`。
- 不要返回裸 `Map` 作为统一响应。
- 不要在业务 SQL 中重复处理租户和逻辑删除。
- 不要把历史 `com.gb` 包名带回新代码。

## 推荐生成顺序

新增一个普通业务模块时，按顺序创建：

1. `entity/enums/*Enum.java`
2. `entity/*Entity.java`
3. `entity/bo/*BO.java`
4. `entity/query/*Query.java`
5. `entity/vo/*VO.java`
6. `mapper/*Mapper.java`
7. `service/*Service.java`
8. `service/query/*ServiceQuery.java`
9. `service/results/*ServiceResults.java`
10. `service/impl/*ServiceImpl.java`
11. `controller/*Controller.java`

## 验证命令

```bash
./gradlew clean compileJava -x test
```

如果依赖 `utils` 有调整，先在 `utils` 执行：

```bash
mvn -q -DskipTests install
```

再回到当前项目编译。
