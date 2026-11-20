package com.kellen.auth.rpc;

import com.kellen.auth.service.AuthDataScopeResolver;
import com.kellen.auth.service.AuthSecurityContextInvalidator;
import com.kellen.auth.service.AuthTenantAccess;
import com.kellen.auth.service.impl.AuthGrantServiceImpl;
import com.kellen.auth.service.results.AuthResourceServiceResults;
import com.kellen.auth.support.AuthH2TestConfiguration;
import com.kellen.datapermission.DataPermissionContextHolder;
import com.kellen.security.SecurityUser;
import com.kellen.security.oauth2.SecurityContextResult;
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
import static org.mockito.Mockito.mock;

/**
 * 业务上下文提供方：按用户与所请求的租户给出活动租户、部门、数据权限、权限码与角色；用户不存在、停用、租户越权返回对应状态。
 * 真实 Mapper 与租户插件跑在 H2（PostgreSQL 模式）上。
 */
@SpringBootTest(classes = SecurityContextRpcServiceImplTest.Config.class,
        properties = {"tenant.enabled=true", "tenant.ignore-without-tenant=false"})
class SecurityContextRpcServiceImplTest {

    @Autowired
    private SecurityContextRpcServiceImpl provider;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void fixture() {
        AuthH2TestConfiguration.recreateTables(dataSource);
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.update("INSERT INTO auth_tenant(id,name,tenant_id,state) VALUES ('t1','租户一','t1',1),('t2','租户二','t2',1),('t3','停用租户','t3',2)");
        jdbc.update("INSERT INTO auth_user(id,username,nickname,admin_type,dept_id,tenant_id,state) VALUES"
                + " ('u1','alice','Alice','TENANT_ADMIN','d1','t1',1),"
                + " ('u-disabled','bob','Bob','TENANT_ADMIN','d1','t1',2),"
                + " ('u-platform','root','Root','PLATFORM_SUPER_ADMIN','d1','t1',1)");
        jdbc.update("INSERT INTO auth_user_tenant(id,user_id,relation_tenant_id,dept_id,default_tenant,tenant_id,state) VALUES"
                + " ('ut-1','u1','t2','d2',false,'t2',1)");
        jdbc.update("INSERT INTO auth_role(id,code,name,data_scope,tenant_id,state) VALUES"
                + " ('r1','manager','经理','DEPT','t1',1),('r2','viewer','查看','SELF','t2',1)");
        jdbc.update("INSERT INTO auth_user_role(id,user_id,role_id,tenant_id) VALUES ('ur-1','u1','r1','t1'),('ur-2','u1','r2','t2')");
        jdbc.update("INSERT INTO auth_resource(id,code,name,resource_category,tenant_id,state,sorting) VALUES"
                + " ('res-1','user:auth:manage','管理','BACKEND','t1',1,1),('res-2','user:auth:resources','资源','BACKEND','t2',1,1),"
                + " ('res-3','menu:home','首页','FRONTEND','t1',1,2)");
        jdbc.update("INSERT INTO auth_role_resource(id,role_id,resource_id,tenant_id) VALUES"
                + " ('rr-1','r1','res-1','t1'),('rr-2','r2','res-2','t2'),('rr-3','r1','res-3','t1')");
        TenantContextHolder.clear();
        DataPermissionContextHolder.clear();
    }

    @AfterEach
    void clear() {
        TenantContextHolder.clear();
        DataPermissionContextHolder.clear();
    }

    @Test
    void securityContextResolvesRolesPermissionsDeptAndTenant() {
        SecurityContextResult result = provider.resolve("u1", null);
        assertThat(result.getStatus()).isEqualTo(SecurityContextResult.Status.OK);
        SecurityUser user = result.getUser();
        assertThat(user.getUserId()).isEqualTo("u1");
        assertThat(user.getUsername()).isEqualTo("alice");
        assertThat(user.getTenantId()).isEqualTo("t1");
        assertThat(user.getDeptId()).isEqualTo("d1");
        assertThat(user.getDataScope()).isEqualTo("CUSTOM");
        assertThat(user.getDataScopeDeptIds()).containsExactly("d1");
        assertThat(user.getAuthorities()).contains("user:auth:manage", "ROLE_manager", "ROLE_TENANT_ADMIN")
                .doesNotContain("menu:home", "user:auth:resources", "ROLE_viewer");
        assertThat(user.isEnabled()).isTrue();
        assertThat(user.getTenantIds()).containsExactly("t1", "t2");
    }

    @Test
    void roleCodeNamedAfterAnAdminTypeNeverGrantsThatRole() {
        // 写入侧拒绝保留编码；库里已有（旧数据、直接写库）的冲突编码在拼权限时也跳过，租户管理员冒充不了平台管理员。
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.update("INSERT INTO auth_role(id,code,name,data_scope,tenant_id,state) VALUES"
                + " ('r-forged','PLATFORM_SUPER_ADMIN','伪造','SELF','t1',1),('r-forged-lower','platform_super_admin','伪造二','SELF','t1',1)");
        jdbc.update("INSERT INTO auth_user_role(id,user_id,role_id,tenant_id) VALUES ('ur-f1','u1','r-forged','t1'),('ur-f2','u1','r-forged-lower','t1')");
        SecurityUser user = provider.resolve("u1", null).getUser();
        assertThat(user.getAuthorities()).contains("ROLE_manager", "ROLE_TENANT_ADMIN")
                .doesNotContain("ROLE_PLATFORM_SUPER_ADMIN", "ROLE_platform_super_admin");
    }

    @Test
    void ownSecondTenantUsesRelationDeptAndThatTenantsRoles() {
        SecurityUser user = provider.resolve("u1", "t2").getUser();
        assertThat(user.getTenantId()).isEqualTo("t2");
        assertThat(user.getDeptId()).isEqualTo("d2");
        assertThat(user.getAuthorities()).contains("user:auth:resources", "ROLE_viewer").doesNotContain("user:auth:manage", "ROLE_manager");
        assertThat(user.getDataScope()).isEqualTo("SELF");
    }

    @Test
    void unknownUserReturnsUserNotFound() {
        assertThat(provider.resolve("nobody", null).getStatus()).isEqualTo(SecurityContextResult.Status.USER_NOT_FOUND);
    }

    @Test
    void deletedUserReturnsUserNotFound() {
        new JdbcTemplate(dataSource).update("UPDATE auth_user SET is_deleted = true WHERE id = 'u1'");
        assertThat(provider.resolve("u1", null).getStatus()).isEqualTo(SecurityContextResult.Status.USER_NOT_FOUND);
    }

    @Test
    void disabledUserReturnsDisabled() {
        assertThat(provider.resolve("u-disabled", null).getStatus()).isEqualTo(SecurityContextResult.Status.DISABLED);
    }

    @Test
    void foreignTenantReturnsTenantDenied() {
        assertThat(provider.resolve("u1", "t9").getStatus()).isEqualTo(SecurityContextResult.Status.TENANT_DENIED);
        assertThat(provider.resolve("u1", "t3").getStatus()).isEqualTo(SecurityContextResult.Status.TENANT_DENIED);
    }

    @Test
    void platformAdminCanSelectAnyEnabledTenant() {
        SecurityUser user = provider.resolve("u-platform", "t2").getUser();
        assertThat(user.getTenantId()).isEqualTo("t2");
        assertThat(user.getAuthorities()).contains("ROLE_PLATFORM_SUPER_ADMIN");
        assertThat(user.getTenantIds()).containsExactlyInAnyOrder("t1", "t2");
        assertThat(provider.resolve("u-platform", "t3").getStatus()).isEqualTo(SecurityContextResult.Status.TENANT_DENIED);
    }

    @Test
    void callerTenantAndIgnoreContextsAreRestored() {
        TenantContextHolder.setTenantId("caller-tenant");
        DataPermissionContextHolder.ignore();
        provider.resolve("u1", "t2");
        assertThat(TenantContextHolder.getTenantId()).isEqualTo("caller-tenant");
        assertThat(TenantContextHolder.isIgnore()).isFalse();
        assertThat(DataPermissionContextHolder.isIgnore()).isTrue();
    }

    /**
     * 测试上下文：H2 认证表加提供方与它依赖的组件。
     */
    @Configuration(proxyBeanMethods = false)
    @Import({AuthH2TestConfiguration.class, SecurityContextRpcServiceImpl.class, AuthTenantAccess.class, AuthDataScopeResolver.class,
            AuthGrantServiceImpl.class, AuthSecurityContextInvalidator.class})
    static class Config {

        @Bean
        AuthResourceServiceResults authResourceServiceResults() {
            return mock(AuthResourceServiceResults.class);
        }
    }
}
