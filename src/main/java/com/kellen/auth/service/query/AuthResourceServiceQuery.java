package com.kellen.auth.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.query.AuthResourceQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

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
     * 拼接权限资源公共查询条件。
     *
     * @param authResourceQuery 权限资源查询参数
     * @param queryWrapper      查询包装器
     * @return 查询包装器
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    public QueryWrapper<AuthResource> query(AuthResourceQuery authResourceQuery, QueryWrapper<AuthResource> queryWrapper) {
        // 查询参数为空时只返回已有包装器。
        if (authResourceQuery == null) {
            // 返回调用方传入的包装器。
            return queryWrapper;
        }
        // 计算排序字段，未传时按排序字段查询。
        String sortField = StringUtils.defaultIfBlank(authResourceQuery.getCollationFields(), DEFAULT_SORT_FIELD);
        // 升序标识为 true 时按升序排序。
        if (Boolean.TRUE.equals(authResourceQuery.getCollation())) {
            // 拼接升序排序。
            queryWrapper.orderByAsc(sortField);
        } else {
            // 默认按降序排序。
            queryWrapper.orderByDesc(sortField);
        }
        // 指定查询字段非空时使用 select 控制返回列。
        if (StringUtils.isNotBlank(authResourceQuery.getFields())) {
            // 拼接指定查询列。
            queryWrapper.select(authResourceQuery.getFields());
        }
        // 返回完整包装器。
        return queryWrapper;
    }
}
