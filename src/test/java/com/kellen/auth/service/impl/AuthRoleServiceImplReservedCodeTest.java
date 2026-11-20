package com.kellen.auth.service.impl;

import com.kellen.auth.entity.bo.AuthRoleBO;
import com.kellen.auth.service.AuthSecurityContextInvalidator;
import com.kellen.auth.service.query.AuthRoleServiceQuery;
import com.kellen.auth.service.results.AuthRoleServiceResults;
import com.kellen.auth.support.AuthH2TestConfiguration;
import com.kellen.security.oauth2.SecurityUserCache;
import com.kellen.utils.context.TenantContextHolder;
import com.kellen.utils.enumeration.ReturnCode;
import com.kellen.utils.exception.UserException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

/**
 * 角色编码不能用管理员分类的保留名：业务上下文把角色编码拼成 ROLE_<编码>，与管理员分类的 ROLE_<分类> 同一命名空间，
 * 租户管理员建一个编码为 PLATFORM_SUPER_ADMIN 的角色授给自己就等于冒充平台管理员。真实 Mapper 跑在 H2 上。
 */
@SpringBootTest(classes = AuthRoleServiceImplReservedCodeTest.Config.class)
class AuthRoleServiceImplReservedCodeTest {

    @Autowired
    private AuthRoleServiceImpl service;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbc;

    @BeforeEach
    void fixture() {
        AuthH2TestConfiguration.recreateTables(dataSource);
        jdbc = new JdbcTemplate(dataSource);
        jdbc.update("INSERT INTO auth_tenant(id,name,tenant_id,state) VALUES ('t1','租户一','t1',1)");
        jdbc.update("INSERT INTO auth_role(id,code,name,data_scope,tenant_id,state,version) VALUES ('r1','manager','经理','SELF','t1',1,1)");
        TenantContextHolder.clear();
    }

    @AfterEach
    void clear() {
        TenantContextHolder.clear();
    }

    @ParameterizedTest
    @ValueSource(strings = {"PLATFORM_SUPER_ADMIN", "platform_super_admin", " TENANT_ADMIN ", "Tenant_Admin"})
    void newRolesCannotUseAnAdminTypeAsTheirCode(String code) {
        assertThatThrownBy(() -> service.save(role(null, code)))
                .isInstanceOfSatisfying(UserException.class, ex -> assertThat(ex.getReturnCode()).isEqualTo(ReturnCode.REQUEST_PARAMETER_ERROR));
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM auth_role", Integer.class)).isEqualTo(1);
    }

    @Test
    void existingRolesCannotBeRenamedToAnAdminType() {
        AuthRoleBO rename = role("r1", "PLATFORM_SUPER_ADMIN");
        rename.setVersion(1);
        assertThatThrownBy(() -> service.update(rename))
                .isInstanceOfSatisfying(UserException.class, ex -> assertThat(ex.getReturnCode()).isEqualTo(ReturnCode.REQUEST_PARAMETER_ERROR));
        assertThat(jdbc.queryForObject("SELECT code FROM auth_role WHERE id='r1'", String.class)).isEqualTo("manager");
    }

    @Test
    void ordinaryCodesAreStillAccepted() {
        assertThat(service.save(role(null, "auditor"))).isNotBlank();
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM auth_role WHERE code='auditor'", Integer.class)).isEqualTo(1);
    }

    private static AuthRoleBO role(String id, String code) {
        AuthRoleBO bo = new AuthRoleBO();
        bo.setId(id);
        bo.setTenantId("t1");
        bo.setCode(code);
        bo.setName("测试角色");
        return bo;
    }

    /**
     * 测试上下文：H2 认证表加角色服务与它的查询、结果增强和上下文失效通知。
     */
    @Configuration(proxyBeanMethods = false)
    @Import({AuthH2TestConfiguration.class, AuthRoleServiceImpl.class, AuthRoleServiceQuery.class, AuthRoleServiceResults.class,
            AuthSecurityContextInvalidator.class})
    static class Config {

        @Bean
        SecurityUserCache securityUserCache() {
            return mock(SecurityUserCache.class);
        }
    }
}
