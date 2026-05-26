# AI 编码执行指南

## 使用方式

当用户要求新增或修改业务功能时，AI 应按以下顺序工作：

1. 阅读当前业务模块已有代码。
2. 阅读 `PROJECT_CODING_SPEC.md`。
3. 阅读 `UTILS_PUBLIC_SPEC.md`，确认公共规范、错误码、数据库、乐观锁、分支流程和注释要求。
4. 如果任务涉及当前微服务已有业务、接口、权限码、默认数据或启动配置，阅读项目根目录 `README.md`。
5. 参考 `examples/` 下的示例。
6. 如涉及公共规范原文、错误码完整表、团队流程或 `utils` 能力，阅读 `archive/utils-markdown/` 下对应文档。
7. 按项目现有结构直接创建 Java 文件。
8. 代码编写完成后检查项目根目录 `README.md`，已有内容时补充本次新增或调整的业务说明，缺少文件时新建。
9. 编译验证。

`examples/` 是当前 AI 编码的主要参考，不是可编译源码目录。示例需要贴近当前分层规范：完整分层、统一方法命名、类/字段/方法/关键逻辑注释齐全。

## 注释要求

注释规则以 `PROJECT_CODING_SPEC.md` 的“注释规范”为准。新增或修改方法时使用以下 JavaDoc 形态：

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

- 不要把业务状态枚举加入 `EntityBase`。
- 不要返回裸 `Map` 作为统一响应。
- 不要在业务 SQL 中重复处理租户和逻辑删除。
- 不要忽略 `UTILS_PUBLIC_SPEC.md` 中的乐观锁、枚举、错误码和数据库变更记录要求。

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
