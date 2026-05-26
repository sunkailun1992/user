# AI 编码执行指南

## 使用方式

当用户要求新增或修改业务功能时，AI 应按以下顺序工作：

1. 阅读当前业务模块已有代码。
2. 阅读 `PROJECT_CODING_SPEC.md`。
3. 参考 `examples/` 下的示例。
4. 如涉及公共规范、错误码、团队流程或 `utils` 能力，阅读 `utils-markdown/` 下对应文档。
5. 按项目现有结构直接创建 Java 文件。
6. 编译验证。

`examples/` 是当前 AI 编码的主要参考，不是可编译源码目录。示例需要贴近历史 `legacy-ftl` 的生成风格：完整分层、统一方法命名、类/字段/方法/关键逻辑注释齐全。

## 注释要求

- 新增或修改 Java 代码时，类、字段、方法都必须补充注释。
- 方法前必须使用 JavaDoc，格式保持项目历史风格，至少说明方法用途、`@param`、`@return`、`@author`、`@DateTime`、`@email`。
- 方法参数较多时，每个参数都要说明业务含义，不能只写参数名。
- 方法体内新增或调整的关键逻辑必须逐行补充注释，尤其是认证用户、租户、权限、Redis、动态数据源、版本校验、SQL 参数校验、事务、异常处理、返回值组装等代码。
- 行尾注释要说明“为什么这样做”或“该行承担什么业务含义”，不要只重复代码字面意思。
- 对已有代码做局部改造时，本次改动行必须补充注释；不要为了补注释大面积重写无关历史代码。
- 示例：

```java
/**
 * 获取幂等锁缓存Key
 *
 * @param joinPoint: aop拦截类
 * @return java.lang.String
 * @author sunkailun
 * @DateTime 2026/5/26  下午
 * @email 376253703@qq.com
 */
private String getRepeatKey(JoinPoint joinPoint) {
    SecurityUser user = UserContextHolder.get(); // 获取当前认证用户，替代历史 Redis token 用户查询。
    String tenantId = StringUtils.defaultIfBlank(TenantContextHolder.getTenantId(), "default"); // 缺少租户时使用 default，保证 key 结构稳定。
    String userFlag = user == null ? "anonymous" : StringUtils.defaultIfBlank(user.getUserId(), user.getUsername()); // 优先使用用户 ID，没有则回退用户名或匿名标识。
    return "prevent-repeat:" + tenantId + ":" + userFlag + ":" + joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName(); // key 粒度为租户、用户、类名和方法名。
}
```

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
