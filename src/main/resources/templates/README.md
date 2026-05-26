# 代码生成模板说明

本目录保存 `user` 项目的代码生成模板和生成器配置。AI 或开发者阅读本目录时，优先阅读：

1. `README.md`：模板入口和文件索引。
2. `CODE_GENERATION_SPEC.md`：代码生成规范、字段规则、枚举规则、返回结构和分层约定。
3. `config/mysql.properties`：生成器数据库连接和基础包名配置。

## 文件索引

| 文件 | 生成内容 | 目标包 |
| --- | --- | --- |
| `skl_entity.java.ftl` | DO 实体 | `${packageName}.entity` |
| `skl_entity_bo.java.ftl` | 入参传输对象 | `${packageName}.entity.bo` |
| `skl_entity_vo.java.ftl` | 出参渲染对象 | `${packageName}.entity.vo` |
| `skl_entity_query.java.ftl` | 查询参数对象 | `${packageName}.entity.query` |
| `skl_entity_enums.java.ftl` | MyBatis-Plus 枚举 | `${packageName}.entity.enums` |
| `skl_mapper.java.ftl` | Mapper 接口 | `${packageName}.mapper` |
| `skl_service.java.ftl` | Service 接口 | `${packageName}.service` |
| `skl_serviceImpl.java.ftl` | Service 实现 | `${packageName}.service.impl` |
| `skl_service_query.java.ftl` | 自动查询条件构造 | `${packageName}.service.query` |
| `skl_service_results.java.ftl` | 查询结果增强和 DO/VO 转换 | `${packageName}.service.results` |
| `skl_controller.java.ftl` | REST Controller | `${packageName}.controller` |

## 当前项目关键约定

- 基础包名使用 `com.kellen`，不要再使用历史 `com.gb`。
- 实体默认继承 `com.kellen.bean.EntityBase`。
- Controller 统一返回 `com.kellen.utils.Json`。
- 状态类字段如果有业务含义，应使用对应业务模块自己的 `IEnum` 枚举，不要把业务枚举放进 `EntityBase`。
- 多租户字段 `tenant_id` 由 `EntityBase.tenantId` 和 `TenantContextHolder` 统一承接。

