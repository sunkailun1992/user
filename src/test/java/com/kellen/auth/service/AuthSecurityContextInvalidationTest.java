package com.kellen.auth.service;

import com.kellen.auth.entity.bo.AuthDeptBO;
import com.kellen.auth.entity.bo.AuthResourceBO;
import com.kellen.auth.entity.bo.AuthRoleBO;
import com.kellen.auth.entity.bo.AuthRoleDataScopeSyncBO;
import com.kellen.auth.entity.bo.AuthRoleResourceBO;
import com.kellen.auth.entity.bo.AuthRoleResourceSyncBO;
import com.kellen.auth.entity.bo.AuthTenantBO;
import com.kellen.auth.entity.bo.AuthUserRoleBO;
import com.kellen.auth.entity.bo.AuthUserRoleSyncBO;
import com.kellen.auth.service.impl.AuthDeptServiceImpl;
import com.kellen.auth.service.impl.AuthGrantServiceImpl;
import com.kellen.auth.service.impl.AuthResourceServiceImpl;
import com.kellen.auth.service.impl.AuthRoleServiceImpl;
import com.kellen.auth.service.impl.AuthTenantServiceImpl;
import com.kellen.auth.service.query.AuthDeptServiceQuery;
import com.kellen.auth.service.query.AuthResourceServiceQuery;
import com.kellen.auth.service.query.AuthRoleServiceQuery;
import com.kellen.auth.service.query.AuthTenantServiceQuery;
import com.kellen.auth.service.results.AuthDeptServiceResults;
import com.kellen.auth.service.results.AuthResourceServiceResults;
import com.kellen.auth.service.results.AuthRoleServiceResults;
import com.kellen.auth.service.results.AuthTenantServiceResults;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * 授权关系、角色、权限资源、部门与租户改动后，受影响用户的业务上下文缓存换版本；只在事务提交后换，回滚不换。
 * 真实 Mapper、租户插件与 Spring 事务跑在 H2（PostgreSQL 模式）上，缓存用替身记录换版本的用户。
 *
 * <p>数据：租户 t1 有 u1（角色 r1，r1 绑资源 res1）与 u2（角色 r2，r2 绑资源 res2）；u3 归属 t2、关联 t1；
 * pa 是归属 t2 的平台超级管理员（可选择任一启用租户）；t1 有部门 d1。</p>
 */
@SpringBootTest(classes = AuthSecurityContextInvalidationTest.Config.class,
        properties = {"tenant.enabled=true", "tenant.ignore-without-tenant=false"})
class AuthSecurityContextInvalidationTest {

    @Autowired private AuthGrantServiceImpl grants;
    @Autowired private AuthRoleServiceImpl roles;
    @Autowired private AuthResourceServiceImpl resources;
    @Autowired private AuthDeptServiceImpl depts;
    @Autowired private AuthTenantServiceImpl tenants;
    @Autowired private SecurityUserCache cache;
    @Autowired private DataSource dataSource;
    @Autowired private PlatformTransactionManager transactionManager;

    @BeforeEach
    void fixture() {
        AuthH2TestConfiguration.recreateTables(dataSource);
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.update("INSERT INTO auth_tenant(id,name,tenant_id,state,version) VALUES ('t1','t1','t1',1,0),('t2','t2','t2',1,0)");
        jdbc.update("INSERT INTO auth_user(id,username,admin_type,tenant_id,state) VALUES ('u1','u1','TENANT_ADMIN','t1',1),"
                + "('u2','u2','TENANT_ADMIN','t1',1),('u3','u3','TENANT_ADMIN','t2',1),('pa','pa','PLATFORM_SUPER_ADMIN','t2',1)");
        jdbc.update("INSERT INTO auth_user_tenant(id,user_id,relation_tenant_id,tenant_id) VALUES ('ut3','u3','t1','t2')");
        jdbc.update("INSERT INTO auth_role(id,name,code,data_scope,tenant_id,state,version) VALUES ('r1','r1','r1','SELF','t1',1,0),"
                + "('r2','r2','r2','SELF','t1',1,0)");
        jdbc.update("INSERT INTO auth_user_role(id,user_id,role_id,tenant_id) VALUES ('ur1','u1','r1','t1'),('ur2','u2','r2','t1')");
        jdbc.update("INSERT INTO auth_resource(id,name,code,resource_category,tenant_id,state,version) VALUES"
                + " ('res1','res1','user:a','BACKEND','t1',1,0),('res2','res2','user:b','BACKEND','t1',1,0)");
        jdbc.update("INSERT INTO auth_role_resource(id,role_id,resource_id,tenant_id) VALUES ('rr1','r1','res1','t1'),('rr2','r2','res2','t1')");
        jdbc.update("INSERT INTO auth_dept(id,name,code,tenant_id,state,version) VALUES ('d1','d1','d1','t1',1,0)");
        reset(cache);
        TenantContextHolder.clear();
    }

    @AfterEach
    void clear() {
        TenantContextHolder.clear();
    }

    @Test
    void syncUserRolesBumpsThatUser() {
        AuthUserRoleSyncBO bo = new AuthUserRoleSyncBO();
        bo.setTenantId("t1");
        bo.setUserId("u2");
        bo.setRoleIds(List.of("r1"));
        grants.syncUserRoles(bo);
        verify(cache).bumpVersion("u2");
        verifyNoMoreInteractions(cache);
    }

    @Test
    void bindUserRoleBumpsThatUser() {
        AuthUserRoleBO bo = new AuthUserRoleBO();
        bo.setTenantId("t1");
        bo.setUserId("u2");
        bo.setRoleId("r1");
        grants.bindUserRole(bo);
        verify(cache).bumpVersion("u2");
        verifyNoMoreInteractions(cache);
    }

    @Test
    void roleResourceChangesBumpEveryRoleMember() {
        AuthRoleResourceSyncBO sync = new AuthRoleResourceSyncBO();
        sync.setTenantId("t1");
        sync.setRoleId("r1");
        sync.setResourceIds(List.of("res2"));
        grants.syncRoleResources(sync);
        verify(cache).bumpVersion("u1");
        verifyNoMoreInteractions(cache);

        reset(cache);
        AuthRoleResourceBO bind = new AuthRoleResourceBO();
        bind.setTenantId("t1");
        bind.setRoleId("r2");
        bind.setResourceId("res1");
        grants.bindRoleResource(bind);
        verify(cache).bumpVersion("u2");
        verifyNoMoreInteractions(cache);
    }

    @Test
    void roleDataScopeChangesBumpEveryRoleMember() {
        AuthRoleDataScopeSyncBO bo = new AuthRoleDataScopeSyncBO();
        bo.setTenantId("t1");
        bo.setRoleId("r2");
        bo.setDeptIds(List.of("d1"));
        grants.syncRoleDataScopes(bo);
        verify(cache).bumpVersion("u2");
        verifyNoMoreInteractions(cache);
    }

    @Test
    void roleUpdateAndRemoveBumpRoleMembers() {
        AuthRoleBO bo = new AuthRoleBO();
        bo.setId("r1");
        bo.setVersion(0);
        bo.setTenantId("t1");
        bo.setName("r1-renamed");
        roles.update(bo);
        verify(cache).bumpVersion("u1");
        verifyNoMoreInteractions(cache);

        reset(cache);
        roles.remove("t1", "r2");
        verify(cache).bumpVersion("u2");
        verifyNoMoreInteractions(cache);
    }

    @Test
    void resourceUpdateAndRemoveBumpMembersOfBoundRoles() {
        AuthResourceBO bo = new AuthResourceBO();
        bo.setId("res1");
        bo.setVersion(0);
        bo.setTenantId("t1");
        bo.setName("res1-renamed");
        resources.update(bo);
        verify(cache).bumpVersion("u1");
        verifyNoMoreInteractions(cache);

        reset(cache);
        resources.remove("t1", "res2");
        verify(cache).bumpVersion("u2");
        verifyNoMoreInteractions(cache);
    }

    @Test
    void deptChangesBumpEveryoneWhoCanUseThatTenant() {
        AuthDeptBO update = new AuthDeptBO();
        update.setId("d1");
        update.setVersion(0);
        update.setTenantId("t1");
        update.setName("d1-renamed");
        depts.update(update);
        verifyTenantUsersOfT1Bumped();

        reset(cache);
        AuthDeptBO child = new AuthDeptBO();
        child.setTenantId("t1");
        child.setCode("d2");
        child.setName("d2");
        child.setParentId("d1");
        depts.save(child);
        verifyTenantUsersOfT1Bumped();

        reset(cache);
        depts.remove("t1", "d1");
        verifyTenantUsersOfT1Bumped();
    }

    @Test
    void tenantChangesBumpItsUsersAndPlatformAdmins() {
        AuthTenantBO update = new AuthTenantBO();
        update.setId("t1");
        update.setVersion(0);
        update.setName("t1-renamed");
        tenants.update(update);
        verifyTenantUsersOfT1Bumped();

        reset(cache);
        AuthTenantBO created = new AuthTenantBO();
        created.setId("t3");
        created.setCode("t3");
        created.setName("t3");
        tenants.save(created);
        verify(cache).bumpVersion("pa");
        verifyNoMoreInteractions(cache);

        reset(cache);
        tenants.remove("t2");
        verify(cache).bumpVersion("u3");
        verify(cache).bumpVersion("pa");
        verifyNoMoreInteractions(cache);
    }

    @Test
    void versionIsBumpedOnlyAfterCommit() {
        new TransactionTemplate(transactionManager).executeWithoutResult(status -> {
            AuthUserRoleBO bo = new AuthUserRoleBO();
            bo.setTenantId("t1");
            bo.setUserId("u2");
            bo.setRoleId("r1");
            grants.bindUserRole(bo);
            verify(cache, never()).bumpVersion(anyString());
        });
        verify(cache).bumpVersion("u2");
    }

    @Test
    void rolledBackChangeDoesNotBump() {
        new TransactionTemplate(transactionManager).executeWithoutResult(status -> {
            AuthUserRoleBO bo = new AuthUserRoleBO();
            bo.setTenantId("t1");
            bo.setUserId("u2");
            bo.setRoleId("r1");
            grants.bindUserRole(bo);
            status.setRollbackOnly();
        });
        verify(cache, never()).bumpVersion(anyString());
    }

    /** 能使用 t1 的用户：归属 t1 的 u1、u2，关联 t1 的 u3，以及可选任一租户的平台超级管理员 pa。 */
    private void verifyTenantUsersOfT1Bumped() {
        verify(cache).bumpVersion("u1");
        verify(cache).bumpVersion("u2");
        verify(cache).bumpVersion("u3");
        verify(cache).bumpVersion("pa");
        verifyNoMoreInteractions(cache);
    }

    /**
     * 测试上下文：H2 认证表、真实的授权、角色、资源、部门与租户服务；查询与结果增强、业务上下文缓存用替身。
     */
    @Configuration(proxyBeanMethods = false)
    @Import({AuthH2TestConfiguration.class, AuthGrantServiceImpl.class, AuthRoleServiceImpl.class, AuthResourceServiceImpl.class,
            AuthDeptServiceImpl.class, AuthTenantServiceImpl.class, AuthSecurityContextInvalidator.class})
    static class Config {

        @Bean SecurityUserCache securityUserCache() { return mock(SecurityUserCache.class); }
        @Bean AuthRoleServiceQuery authRoleServiceQuery() { return mock(AuthRoleServiceQuery.class); }
        @Bean AuthRoleServiceResults authRoleServiceResults() { return mock(AuthRoleServiceResults.class); }
        @Bean AuthResourceServiceQuery authResourceServiceQuery() { return mock(AuthResourceServiceQuery.class); }
        @Bean AuthResourceServiceResults authResourceServiceResults() { return mock(AuthResourceServiceResults.class); }
        @Bean AuthDeptServiceQuery authDeptServiceQuery() { return mock(AuthDeptServiceQuery.class); }
        @Bean AuthDeptServiceResults authDeptServiceResults() { return mock(AuthDeptServiceResults.class); }
        @Bean AuthTenantServiceQuery authTenantServiceQuery() { return mock(AuthTenantServiceQuery.class); }
        @Bean AuthTenantServiceResults authTenantServiceResults() { return mock(AuthTenantServiceResults.class); }
    }
}
