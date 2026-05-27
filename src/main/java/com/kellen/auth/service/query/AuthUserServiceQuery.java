package com.kellen.auth.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.query.AuthUserQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

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
        // 计算排序字段，未传时按用户名查询。
        String sortField = StringUtils.defaultIfBlank(authUserQuery.getCollationFields(), DEFAULT_SORT_FIELD);
        // 升序标识为 true 时按升序排序。
        if (Boolean.TRUE.equals(authUserQuery.getCollation())) {
            // 拼接升序排序。
            queryWrapper.orderByAsc(sortField);
        } else {
            // 默认按降序排序。
            queryWrapper.orderByDesc(sortField);
        }
        // 指定查询字段非空时使用 select 控制返回列。
        if (StringUtils.isNotBlank(authUserQuery.getFields())) {
            // 拼接指定查询列。
            queryWrapper.select(authUserQuery.getFields());
        }
        // 返回完整包装器。
        return queryWrapper;
    }
}
