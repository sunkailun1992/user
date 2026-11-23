package com.kellen.auth.service.impl;

import com.kellen.auth.entity.bo.AuthDeptBO;
import com.kellen.auth.entity.query.AuthDeptQuery;
import com.kellen.auth.entity.vo.AuthDeptVO;
import com.kellen.auth.service.AuthSecurityContextInvalidator;
import com.kellen.auth.service.query.AuthDeptServiceQuery;
import com.kellen.auth.service.results.AuthDeptServiceResults;
import com.kellen.auth.support.AuthH2TestConfiguration;
import com.kellen.security.oauth2.SecurityUserCache;
import com.kellen.utils.context.TenantContextHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.mock;

/**
 * 部门排序值：部门的 VO、BO 带上 sort，新建与修改照存，部门列表（含 /auth/manage/depts/options）按 sort 升序给出，
 * 不填时取列的默认值 0。之前部门表有这一列、查询也按它排，但 VO、BO 没有这个字段，前端填的值被丢、列表恒为空。
 * 真实 Mapper 跑在 H2 上（表结构来自 Flyway 迁移）。
 */
@SpringBootTest(classes = AuthDeptServiceImplSortTest.Config.class)
class AuthDeptServiceImplSortTest {

    @Autowired
    private AuthDeptServiceImpl service;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbc;

    @BeforeEach
    void fixture() {
        AuthH2TestConfiguration.recreateTables(dataSource);
        jdbc = new JdbcTemplate(dataSource);
        jdbc.update("INSERT INTO auth_tenant(id,name,tenant_id,state) VALUES ('t1','租户一','t1',1)");
        TenantContextHolder.clear();
    }

    @AfterEach
    void clear() {
        TenantContextHolder.clear();
    }

    @Test
    void savedDeptsKeepTheirSortAndTheListIsOrderedByIt() {
        service.save(dept("DEPT_B", "乙部", 2));
        service.save(dept("DEPT_A", "甲部", 1));

        assertThat(service.list(query())).extracting(AuthDeptVO::getName, AuthDeptVO::getSort)
                .containsExactly(tuple("甲部", 1), tuple("乙部", 2));
    }

    @Test
    void updatingADeptChangesItsSort() {
        String id = service.save(dept("DEPT_A", "甲部", 1));
        AuthDeptBO update = dept("DEPT_A", "甲部", 5);
        update.setId(id);
        update.setVersion(jdbc.queryForObject("SELECT version FROM auth_dept WHERE id = ?", Integer.class, id));

        assertThat(service.update(update)).isTrue();
        assertThat(jdbc.queryForObject("SELECT sort FROM auth_dept WHERE id = ?", Integer.class, id)).isEqualTo(5);
    }

    @Test
    void updatingWithoutASortKeepsTheSavedOne() {
        String id = service.save(dept("DEPT_A", "甲部", 3));
        AuthDeptBO rename = dept("DEPT_A", "甲部门", null);
        rename.setId(id);
        rename.setVersion(jdbc.queryForObject("SELECT version FROM auth_dept WHERE id = ?", Integer.class, id));

        assertThat(service.update(rename)).isTrue();
        assertThat(jdbc.queryForObject("SELECT sort FROM auth_dept WHERE id = ?", Integer.class, id)).isEqualTo(3);
        assertThat(jdbc.queryForObject("SELECT name FROM auth_dept WHERE id = ?", String.class, id)).isEqualTo("甲部门");
    }

    @Test
    void withoutASortTheColumnDefaultOfZeroApplies() {
        String id = service.save(dept("DEPT_A", "甲部", null));

        assertThat(jdbc.queryForObject("SELECT sort FROM auth_dept WHERE id = ?", Integer.class, id)).isZero();
        assertThat(service.list(query())).extracting(AuthDeptVO::getSort).containsExactly(0);
    }

    private static AuthDeptBO dept(String code, String name, Integer sort) {
        AuthDeptBO bo = new AuthDeptBO();
        bo.setTenantId("t1");
        bo.setCode(code);
        bo.setName(name);
        bo.setSort(sort);
        return bo;
    }

    private static AuthDeptQuery query() {
        AuthDeptQuery query = new AuthDeptQuery();
        query.setTenantId("t1");
        return query;
    }

    /**
     * 测试上下文：H2 认证表加部门服务与它的查询、结果增强和上下文失效通知。
     */
    @Configuration(proxyBeanMethods = false)
    @Import({AuthH2TestConfiguration.class, AuthDeptServiceImpl.class, AuthDeptServiceQuery.class, AuthDeptServiceResults.class,
            AuthSecurityContextInvalidator.class})
    static class Config {

        @Bean
        SecurityUserCache securityUserCache() {
            return mock(SecurityUserCache.class);
        }
    }
}
