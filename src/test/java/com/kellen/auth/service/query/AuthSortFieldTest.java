package com.kellen.auth.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kellen.auth.entity.AuthDept;
import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.AuthRole;
import com.kellen.auth.entity.AuthTenant;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.query.AuthDeptQuery;
import com.kellen.auth.entity.query.AuthResourceQuery;
import com.kellen.auth.entity.query.AuthRoleQuery;
import com.kellen.auth.entity.query.AuthTenantQuery;
import com.kellen.auth.entity.query.AuthUserQuery;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 排序字段改名 sort（第6阶段第三部分）：默认排序、旧字段名回落与查询字段。
 *
 * @author sunkailun
 * @className AuthSortFieldTest
 * @time 2026/11/22
 */
class AuthSortFieldTest {

    @Test
    void defaultOrderIsSort() {
        // 租户、部门、角色、资源不传排序字段时按 sort 排；用户默认按用户名排，见 userQueryKeepsUsernameDefault。
        assertThat(tenant(null, null).getSqlSegment()).containsPattern("ORDER BY sort\\b");
        assertThat(dept(null, null).getSqlSegment()).containsPattern("ORDER BY sort\\b");
        assertThat(role(null, null).getSqlSegment()).containsPattern("ORDER BY sort\\b");
        assertThat(resource(null, null).getSqlSegment()).containsPattern("ORDER BY sort\\b");
    }

    @Test
    void legacySortingFieldFallsBackToSort() {
        // 旧字段名 sorting 不在白名单里，回落到默认排序字段 sort，不会把原始字符串拼进 SQL。
        assertThat(tenant("sorting", null).getSqlSegment()).containsPattern("ORDER BY sort\\b").doesNotContain("sorting");
        assertThat(dept("sorting", null).getSqlSegment()).containsPattern("ORDER BY sort\\b").doesNotContain("sorting");
        assertThat(role("sorting", null).getSqlSegment()).containsPattern("ORDER BY sort\\b").doesNotContain("sorting");
        assertThat(resource("sorting", null).getSqlSegment()).containsPattern("ORDER BY sort\\b").doesNotContain("sorting");
    }

    @Test
    void userQueryKeepsUsernameDefault() {
        // 用户列表默认按用户名排（改名前后都是）；显式传 sort 才按 sort 排，传旧名 sorting 回落到用户名。
        assertThat(user(null, null).getSqlSegment()).containsPattern("ORDER BY username\\b");
        assertThat(user("sort", null).getSqlSegment()).containsPattern("ORDER BY sort\\b");
        assertThat(user("sorting", null).getSqlSegment()).containsPattern("ORDER BY username\\b").doesNotContain("sorting");
    }

    @Test
    void sortIsSelectable() {
        // fields 里点名 sort 时进入 select 列表。
        assertThat(tenant(null, "id,sort").getSqlSelect()).containsPattern("\\bsort\\b");
        assertThat(dept(null, "id,sort").getSqlSelect()).containsPattern("\\bsort\\b");
        assertThat(role(null, "id,sort").getSqlSelect()).containsPattern("\\bsort\\b");
        assertThat(resource(null, "id,sort").getSqlSelect()).containsPattern("\\bsort\\b");
        assertThat(user(null, "id,sort").getSqlSelect()).containsPattern("\\bsort\\b");
    }

    private static QueryWrapper<AuthTenant> tenant(String collationFields, String fields) {
        AuthTenantQuery query = new AuthTenantQuery();
        query.setCollationFields(collationFields);
        query.setFields(fields);
        return new AuthTenantServiceQuery().query(query, new QueryWrapper<>());
    }

    private static QueryWrapper<AuthDept> dept(String collationFields, String fields) {
        AuthDeptQuery query = new AuthDeptQuery();
        query.setCollationFields(collationFields);
        query.setFields(fields);
        return new AuthDeptServiceQuery().query(query, new QueryWrapper<>());
    }

    private static QueryWrapper<AuthRole> role(String collationFields, String fields) {
        AuthRoleQuery query = new AuthRoleQuery();
        query.setCollationFields(collationFields);
        query.setFields(fields);
        return new AuthRoleServiceQuery().query(query, new QueryWrapper<>());
    }

    private static QueryWrapper<AuthResource> resource(String collationFields, String fields) {
        AuthResourceQuery query = new AuthResourceQuery();
        query.setCollationFields(collationFields);
        query.setFields(fields);
        return new AuthResourceServiceQuery().query(query, new QueryWrapper<>());
    }

    private static QueryWrapper<AuthUser> user(String collationFields, String fields) {
        AuthUserQuery query = new AuthUserQuery();
        query.setCollationFields(collationFields);
        query.setFields(fields);
        return new AuthUserServiceQuery().query(query, new QueryWrapper<>());
    }
}
