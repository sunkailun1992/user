package com.kellen.auth.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kellen.auth.entity.AuthDept;
import com.kellen.auth.entity.query.AuthDeptQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 部门查询条件增强。
 *
 * @author sunkailun
 * @className AuthDeptServiceQuery
 * @time 2026/05/27
 */
@Component
public class AuthDeptServiceQuery {

    /**
     * 默认排序字段。
     */
    private static final String DEFAULT_SORT_FIELD = "sorting";

    /**
     * 拼接部门公共查询条件。
     *
     * @param query        部门查询参数
     * @param queryWrapper 查询包装器
     * @return 查询包装器
     */
    public QueryWrapper<AuthDept> query(AuthDeptQuery query, QueryWrapper<AuthDept> queryWrapper) {
        if (query == null) {
            return queryWrapper; // 查询参数为空时只返回已有包装器。
        }
        String sortField = StringUtils.defaultIfBlank(query.getCollationFields(), DEFAULT_SORT_FIELD); // 计算排序字段，未传时按部门排序字段查询。
        if (Boolean.FALSE.equals(query.getCollation())) {
            queryWrapper.orderByDesc(sortField); // 显式传 false 时按降序排序。
        } else {
            queryWrapper.orderByAsc(sortField); // 部门树默认按升序排序，便于前端直接组树展示。
        }
        if (StringUtils.isNotBlank(query.getFields())) {
            queryWrapper.select(query.getFields()); // 指定查询字段非空时控制查询列。
        }
        return queryWrapper; // 返回完整查询包装器。
    }
}
