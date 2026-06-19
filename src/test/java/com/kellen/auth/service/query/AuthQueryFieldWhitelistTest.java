package com.kellen.auth.service.query;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 认证查询字段白名单测试。
 * <p>
 * 直接验证动态排序字段和动态显示字段不会绕过白名单进入 SQL 片段。
 *
 * @author sunkailun
 * @className AuthQueryFieldWhitelistTest
 * @time 2026/06/08
 */
class AuthQueryFieldWhitelistTest {

    /**
     * 测试字段白名单。
     */
    private static final Map<String, String> ALLOWED_FIELDS = Map.of(
            "id", "id", // 允许主键字段。
            "name", "name", // 允许名称字段。
            "createDateTime", "create_date_time" // 允许创建时间驼峰字段映射数据库字段。
    );

    /**
     * 非法排序字段应回退到默认排序字段。
     *
     * @author sunkailun
     */
    @Test
    void shouldFallbackDefaultSortFieldWhenRequestedSortFieldIsUnsafe() {
        String sortField = AuthQueryFieldWhitelist.resolveSortField("name desc", "createDateTime", ALLOWED_FIELDS); // 解析带 SQL 片段的非法排序字段。

        assertThat(sortField).isEqualTo("create_date_time"); // 验证非法排序字段没有原样进入 SQL。
    }

    /**
     * 非法显示字段应被过滤。
     *
     * @author sunkailun
     */
    @Test
    void shouldFilterUnsafeSelectFields() {
        String[] selectFields = AuthQueryFieldWhitelist.resolveSelectFields("id,name,password,(select 1)", ALLOWED_FIELDS); // 解析混合合法和非法字段的显示字段。

        assertThat(selectFields).containsExactly("id", "name"); // 验证只保留白名单字段。
    }
}
