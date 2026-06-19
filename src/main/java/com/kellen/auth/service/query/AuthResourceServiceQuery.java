package com.kellen.auth.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.query.AuthResourceQuery;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 权限资源查询条件增强。
 *
 * @author sunkailun
 * @className AuthResourceServiceQuery
 * @time 2026/05/27
 */
@Component
public class AuthResourceServiceQuery {

    /**
     * 默认排序字段。
     */
    private static final String DEFAULT_SORT_FIELD = "sorting";

    /**
     * 允许排序和查询的数据库字段白名单。
     */
    private static final Map<String, String> ALLOWED_FIELDS = Map.ofEntries(
            Map.entry("id", "id"), // 允许按资源主键查询和排序。
            Map.entry("code", "code"), // 允许按权限编码查询和排序。
            Map.entry("name", "name"), // 允许按资源名称查询和排序。
            Map.entry("resourceCategory", "resource_category"), // 允许按资源分类查询和排序。
            Map.entry("resource_category", "resource_category"), // 兼容前端传数据库列名。
            Map.entry("path", "path"), // 允许按资源路径查询和排序。
            Map.entry("method", "method"), // 允许按请求方法查询和排序。
            Map.entry("parentId", "parent_id"), // 允许按父级资源查询和排序。
            Map.entry("parent_id", "parent_id"), // 兼容前端传数据库列名。
            Map.entry("state", "state"), // 允许按资源状态查询和排序。
            Map.entry("type", "type"), // 允许按认证数据类型查询和排序。
            Map.entry("tenantId", "tenant_id"), // 允许按租户ID查询和排序。
            Map.entry("tenant_id", "tenant_id"), // 兼容前端传数据库列名。
            Map.entry("sorting", "sorting"), // 允许按排序值查询和排序。
            Map.entry("version", "version"), // 允许查询乐观锁版本号。
            Map.entry("createDateTime", "create_date_time"), // 允许按创建时间查询和排序。
            Map.entry("create_date_time", "create_date_time"), // 兼容前端传数据库列名。
            Map.entry("modifyDateTime", "modify_date_time"), // 允许按修改时间查询和排序。
            Map.entry("modify_date_time", "modify_date_time") // 兼容前端传数据库列名。
    );

    /**
     * 拼接权限资源公共查询条件。
     *
     * @param authResourceQuery 权限资源查询参数
     * @param queryWrapper      查询包装器
     * @return 查询包装器
     * @author sunkailun
     */
    public QueryWrapper<AuthResource> query(AuthResourceQuery authResourceQuery, QueryWrapper<AuthResource> queryWrapper) {
        // 查询参数为空时只返回已有包装器。
        if (authResourceQuery == null) {
            // 返回调用方传入的包装器。
            return queryWrapper;
        }
        // 计算白名单内排序字段，非法字段回退到默认排序字段。
        String sortField = AuthQueryFieldWhitelist.resolveSortField(authResourceQuery.getCollationFields(), DEFAULT_SORT_FIELD, ALLOWED_FIELDS);
        // 升序标识为 true 时按升序排序。
        if (Boolean.TRUE.equals(authResourceQuery.getCollation())) {
            // 拼接升序排序。
            queryWrapper.orderByAsc(sortField);
        } else {
            // 默认按降序排序。
            queryWrapper.orderByDesc(sortField);
        }
        // 解析白名单内查询列，非法字段不会进入 select。
        String[] selectFields = AuthQueryFieldWhitelist.resolveSelectFields(authResourceQuery.getFields(), ALLOWED_FIELDS);
        // 指定查询字段非空时使用安全字段数组控制返回列。
        if (selectFields.length > 0) {
            // 拼接白名单校验后的查询列。
            queryWrapper.select(selectFields);
        }
        // 返回完整包装器。
        return queryWrapper;
    }
}
