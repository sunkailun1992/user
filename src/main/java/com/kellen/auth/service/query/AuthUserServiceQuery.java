package com.kellen.auth.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.query.AuthUserQuery;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户查询条件增强。
 *
 * @author sunkailun
 * @className AuthUserServiceQuery
 * @time 2026/05/27
 */
@Component
public class AuthUserServiceQuery {

    /**
     * 默认排序字段。
     */
    private static final String DEFAULT_SORT_FIELD = "username";

    /**
     * 允许排序和查询的数据库字段白名单。
     */
    private static final Map<String, String> ALLOWED_FIELDS = Map.ofEntries(
            Map.entry("id", "id"), // 允许按用户主键查询和排序。
            Map.entry("code", "code"), // 允许按用户业务编码查询和排序。
            Map.entry("username", "username"), // 允许按用户名查询和排序。
            Map.entry("nickname", "nickname"), // 允许按用户昵称查询和排序。
            Map.entry("adminType", "admin_type"), // 允许按管理员分类查询和排序。
            Map.entry("admin_type", "admin_type"), // 兼容前端传数据库列名。
            Map.entry("deptId", "dept_id"), // 允许按所属部门查询和排序。
            Map.entry("dept_id", "dept_id"), // 兼容前端传数据库列名。
            Map.entry("state", "state"), // 允许按用户状态查询和排序。
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
     * 拼接用户公共查询条件。
     *
     * @param authUserQuery 用户查询参数
     * @param queryWrapper  查询包装器
     * @return 查询包装器
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    public QueryWrapper<AuthUser> query(AuthUserQuery authUserQuery, QueryWrapper<AuthUser> queryWrapper) {
        // 查询参数为空时只返回已有包装器。
        if (authUserQuery == null) {
            // 返回调用方传入的包装器。
            return queryWrapper;
        }
        // 计算白名单内排序字段，非法字段回退到默认用户名字段。
        String sortField = AuthQueryFieldWhitelist.resolveSortField(authUserQuery.getCollationFields(), DEFAULT_SORT_FIELD, ALLOWED_FIELDS);
        // 升序标识为 true 时按升序排序。
        if (Boolean.TRUE.equals(authUserQuery.getCollation())) {
            // 拼接升序排序。
            queryWrapper.orderByAsc(sortField);
        } else {
            // 默认按降序排序。
            queryWrapper.orderByDesc(sortField);
        }
        // 解析白名单内查询列，非法字段不会进入 select。
        String[] selectFields = AuthQueryFieldWhitelist.resolveSelectFields(authUserQuery.getFields(), ALLOWED_FIELDS);
        // 指定查询字段非空时使用安全字段数组控制返回列。
        if (selectFields.length > 0) {
            // 拼接白名单校验后的查询列。
            queryWrapper.select(selectFields);
        }
        // 返回完整包装器。
        return queryWrapper;
    }
}
