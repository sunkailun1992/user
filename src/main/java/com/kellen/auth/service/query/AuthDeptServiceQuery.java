package com.kellen.auth.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kellen.auth.entity.AuthDept;
import com.kellen.auth.entity.query.AuthDeptQuery;
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
     * 拼接部门公共查询条件。
     *
     * @param query        部门查询参数
     * @param queryWrapper 查询包装器
     * @return 查询包装器
     */
    public QueryWrapper<AuthDept> query(AuthDeptQuery query, QueryWrapper<AuthDept> queryWrapper) {
        queryWrapper.orderByAsc("sorting"); // 部门树默认按排序值升序，便于前端直接组树展示。
        return queryWrapper; // 返回完整查询包装器。
    }
}
