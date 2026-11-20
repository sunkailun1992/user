package com.kellen.auth.service.impl;

import com.kellen.auth.entity.vo.AuthCurrentResourceVO;
import com.kellen.auth.entity.vo.AuthResourceVO;
import com.kellen.auth.entity.vo.AuthTenantVO;
import com.kellen.auth.service.AuthSecurityContextInvalidator;
import com.kellen.auth.service.AuthTenantAccess;
import com.kellen.auth.service.results.AuthResourceServiceResults;
import com.kellen.auth.support.AuthH2TestConfiguration;
import com.kellen.security.SecurityUser;
import com.kellen.security.UserContextHolder;
import com.kellen.security.oauth2.SecurityUserCache;
import com.kellen.utils.context.TenantContextHolder;
import com.kellen.utils.enumeration.ReturnCode;
import com.kellen.utils.exception.UserException;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

/**
 * 当前用户的资源与可切换租户：按资源服务器写入的当前用户与活动租户查询，带上用户名与昵称供后台显示
 * （登录在 Keycloak，前端只能从这里拿当前用户的资料）。真实 Mapper 与租户插件跑在 H2 上。
 */
@SpringBootTest(classes = AuthCurrentUserServiceImplTest.Config.class,
        properties = {"tenant.enabled=true", "tenant.ignore-without-tenant=false"})
class AuthCurrentUserServiceImplTest {

    @Autowired
    private AuthCurrentUserServiceImpl service;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void fixture() {
        AuthH2TestConfiguration.recreateTables(dataSource);
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.update("INSERT INTO auth_tenant(id,name,tenant_id,state,sorting) VALUES ('t1','租户一','t1',1,1),('t2','租户二','t2',1,2)");
        jdbc.update("INSERT INTO auth_user(id,username,nickname,admin_type,dept_id,tenant_id,state) VALUES ('u1','alice','Alice','TENANT_ADMIN','d1','t1',1)");
        jdbc.update("INSERT INTO auth_user_tenant(id,user_id,relation_tenant_id,tenant_id) VALUES ('ut1','u1','t2','t1')");
        jdbc.update("INSERT INTO auth_role(id,name,code,data_scope,tenant_id,state) VALUES ('r1','管理员','manager','SELF','t1',1)");
        jdbc.update("INSERT INTO auth_user_role(id,user_id,role_id,tenant_id) VALUES ('ur1','u1','r1','t1')");
        jdbc.update("INSERT INTO auth_resource(id,name,code,resource_category,tenant_id,state,sorting) VALUES"
                + " ('res-menu','首页','menu:home','FRONTEND','t1',1,1),('res-api','当前资源','user:auth:resources','BACKEND','t1',1,2)");
        jdbc.update("INSERT INTO auth_role_resource(id,role_id,resource_id,tenant_id) VALUES ('rr1','r1','res-menu','t1'),('rr2','r1','res-api','t1')");
        TenantContextHolder.clear();
        UserContextHolder.set(new SecurityUser("u1", "alice", "t1", "d1", "SELF", List.of(), List.of("user:auth:resources")));
    }

    @AfterEach
    void clear() {
        UserContextHolder.clear();
        TenantContextHolder.clear();
    }

    @Test
    void currentResourcesCarryTheUsersNameMenusPermissionsAndTenants() {
        AuthCurrentResourceVO resources = service.currentResources();
        assertThat(resources.getUserId()).isEqualTo("u1");
        assertThat(resources.getUsername()).isEqualTo("alice");
        assertThat(resources.getNickname()).isEqualTo("Alice");
        assertThat(resources.getTenantId()).isEqualTo("t1");
        assertThat(resources.getDeptId()).isEqualTo("d1");
        assertThat(resources.getAdminType()).isEqualTo("TENANT_ADMIN");
        assertThat(resources.getPermissions()).containsExactly("user:auth:resources");
        assertThat(resources.getFrontendResources()).extracting(AuthResourceVO::getCode).containsExactly("menu:home");
        assertThat(resources.getAvailableTenants()).extracting(AuthTenantVO::getId).containsExactly("t1", "t2");
    }

    @Test
    void currentTenantsListEveryEnabledTenantTheUserCanUse() {
        assertThat(service.currentTenants()).extracting(AuthTenantVO::getId).containsExactly("t1", "t2");
    }

    @Test
    void withoutCurrentUserTheRequestIsUnauthenticated() {
        UserContextHolder.clear();
        assertThatThrownBy(() -> service.currentResources())
                .isInstanceOfSatisfying(UserException.class, ex -> assertThat(ex.getReturnCode()).isEqualTo(ReturnCode.USER_AUTHENTICATION_FAILED));
    }

    /**
     * 测试上下文：H2 认证表加当前用户服务与它依赖的授权、租户访问组件。
     */
    @Configuration(proxyBeanMethods = false)
    @Import({AuthH2TestConfiguration.class, AuthCurrentUserServiceImpl.class, AuthGrantServiceImpl.class, AuthTenantAccess.class,
            AuthResourceServiceResults.class, AuthSecurityContextInvalidator.class})
    static class Config {

        @Bean
        SecurityUserCache securityUserCache() {
            return mock(SecurityUserCache.class);
        }
    }
}
