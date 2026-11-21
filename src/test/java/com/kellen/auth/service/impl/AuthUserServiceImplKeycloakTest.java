package com.kellen.auth.service.impl;

import com.kellen.auth.entity.bo.AuthUserBO;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.keycloak.KeycloakAccountClient;
import com.kellen.auth.mq.producer.UserAccountEventProducer;
import com.kellen.auth.service.AuthSecurityContextInvalidator;
import com.kellen.auth.service.AuthTenantManagementAccess;
import com.kellen.auth.service.query.AuthUserServiceQuery;
import com.kellen.auth.service.results.AuthUserServiceResults;
import com.kellen.auth.support.AuthH2TestConfiguration;
import com.kellen.security.SecurityUser;
import com.kellen.security.UserContextHolder;
import com.kellen.security.oauth2.SecurityUserCache;
import com.kellen.utils.context.TenantContextHolder;
import com.kellen.utils.enumeration.ReturnCode;
import com.kellen.utils.exception.UserException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import javax.sql.DataSource;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * 账号交给 Keycloak：user 建、改、停用、删账号时同步 Keycloak，并让业务上下文缓存换版本；Keycloak 失败时本地事务回滚。
 * 真实 Mapper、租户插件与 Spring 事务跑在 H2（PostgreSQL 模式）上。
 *
 * <p>本地库与 Keycloak 两边写不进同一个事务，按 Saga 的做法（Garcia-Molina &amp; Salem 1987；Richardson《Microservices Patterns》第 4 章）：
 * 能撤回的 Keycloak 改动先做、本地回滚时补偿；撤不回的（重置口令、删账号）排在最后或放到提交之后。</p>
 */
@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(classes = AuthUserServiceImplKeycloakTest.Config.class,
        properties = {"tenant.enabled=true", "tenant.ignore-without-tenant=false"})
class AuthUserServiceImplKeycloakTest {

    @Autowired
    private AuthUserServiceImpl service;

    @Autowired
    private KeycloakAccountClient keycloak;

    @Autowired
    private SecurityUserCache cache;

    @Autowired
    private UserAccountEventProducer producer;

    @MockitoSpyBean
    private AuthSecurityContextInvalidator invalidator;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbc;

    @BeforeEach
    void fixture() {
        AuthH2TestConfiguration.recreateTables(dataSource);
        jdbc = new JdbcTemplate(dataSource);
        jdbc.update("INSERT INTO auth_user(id,username,nickname,admin_type,dept_id,tenant_id,state,idp_subject,version) VALUES"
                + " ('u1','alice','Alice','TENANT_ADMIN','d1','t1',1,'kc-1',0)");
        reset(keycloak, cache, producer, invalidator);
        TenantContextHolder.clear();
        // 调用方是平台超级管理员：本类只测 Keycloak 同步，租户边界由 AuthManagementTenantBoundaryHttpIntegrationTest 覆盖。
        UserContextHolder.set(new SecurityUser("platform-admin", "platform-admin", "t1", List.of("user:auth:manage", "ROLE_PLATFORM_SUPER_ADMIN")));
    }

    @AfterEach
    void clear() {
        TenantContextHolder.clear();
        UserContextHolder.clear();
    }

    @Test
    void createUserCreatesKeycloakAccountWithUserIdAttribute() {
        when(keycloak.createAccount(anyString(), eq("bob"), eq("Bob"), anyString())).thenReturn("kc-new");
        AuthUserBO bo = newUser("bob", "Bob");
        String userId = service.save(bo);
        verify(keycloak).createAccount(eq(userId), eq("bob"), eq("Bob"), eq(bo.getPassword()));
        assertThat(jdbc.queryForObject("SELECT idp_subject FROM auth_user WHERE id = ?", String.class, userId)).isEqualTo("kc-new");
        verify(producer).publishAccountCreated(any(), eq("t1"));
    }

    @Test
    void localFailureAfterKeycloakCreationDeletesTheKeycloakAccount() {
        when(keycloak.createAccount(anyString(), eq("dave"), eq("Dave"), anyString())).thenReturn("kc-dave");
        doThrow(new IllegalStateException("synthetic event failure")).when(producer).publishAccountCreated(any(), anyString());
        assertThatThrownBy(() -> service.save(newUser("dave", "Dave"))).isInstanceOf(IllegalStateException.class);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM auth_user WHERE username = 'dave'", Integer.class)).isZero();
        verify(keycloak).deleteAccount("kc-dave");
    }

    @Test
    void committedCreationKeepsTheKeycloakAccount() {
        when(keycloak.createAccount(anyString(), eq("frank"), eq("Frank"), anyString())).thenReturn("kc-frank");
        service.save(newUser("frank", "Frank"));
        verify(keycloak, never()).deleteAccount(anyString());
    }

    @Test
    void disabledNewUserIsCreatedDisabledInKeycloak() {
        when(keycloak.createAccount(anyString(), eq("erin"), eq("Erin"), anyString())).thenReturn("kc-erin");
        AuthUserBO bo = newUser("erin", "Erin");
        bo.setState(AuthStateEnum.禁用);
        service.save(bo);
        verify(keycloak).setEnabled("kc-erin", false);
    }

    @Test
    void passwordResetForUserWithoutKeycloakAccountIsRejected() {
        jdbc.update("UPDATE auth_user SET idp_subject = NULL WHERE id = 'u1'");
        AuthUserBO bo = existing();
        bo.setPassword("New-Password-" + UUID.randomUUID());
        assertThatThrownBy(() -> service.update(bo))
                .isInstanceOfSatisfying(UserException.class, ex -> assertThat(ex.getReturnCode()).isEqualTo(ReturnCode.USER_ACCOUNT_NOT_FOUND));
        assertThat(jdbc.queryForObject("SELECT version FROM auth_user WHERE id = 'u1'", Integer.class)).isZero();
    }

    @Test
    void profileChangeForUserWithoutKeycloakAccountStaysLocal() {
        jdbc.update("UPDATE auth_user SET idp_subject = NULL WHERE id = 'u1'");
        AuthUserBO bo = existing();
        bo.setNickname("Alice Local");
        assertThat(service.update(bo)).isTrue();
        assertThat(jdbc.queryForObject("SELECT nickname FROM auth_user WHERE id = 'u1'", String.class)).isEqualTo("Alice Local");
        verifyNoInteractions(keycloak);
    }

    @Test
    void keycloakFailureRollsBackUserCreation() {
        when(keycloak.createAccount(anyString(), anyString(), anyString(), anyString()))
                .thenThrow(new UserException(ReturnCode.THIRD_PARTY_SERVICE_ERROR, "账号同步到 Keycloak 失败"));
        assertThatThrownBy(() -> service.save(newUser("carol", "Carol")))
                .isInstanceOfSatisfying(UserException.class, ex -> assertThat(ex.getReturnCode()).isEqualTo(ReturnCode.THIRD_PARTY_SERVICE_ERROR));
        assertThat(jdbc.queryForObject("SELECT count(*) FROM auth_user WHERE username = 'carol'", Integer.class)).isZero();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM auth_user_tenant", Integer.class)).isZero();
        verify(producer, never()).publishAccountCreated(any(), anyString());
    }

    @Test
    void disableUserDisablesKeycloakAccountAndBumpsVersion() {
        AuthUserBO bo = existing();
        bo.setState(AuthStateEnum.禁用);
        assertThat(service.update(bo)).isTrue();
        verify(keycloak).setEnabled("kc-1", false);
        verify(cache).bumpVersion("u1");
    }

    @Test
    void enableUserEnablesKeycloakAccount() {
        AuthUserBO bo = existing();
        bo.setState(AuthStateEnum.启用);
        service.update(bo);
        verify(keycloak).setEnabled("kc-1", true);
    }

    @Test
    void passwordChangeResetsKeycloakPasswordAsTemporary() {
        AuthUserBO bo = existing();
        bo.setPassword("New-Password-" + UUID.randomUUID());
        service.update(bo);
        verify(keycloak).resetPassword("kc-1", bo.getPassword());
        verify(cache).bumpVersion("u1");
    }

    @Test
    void nicknameChangeUpdatesKeycloakProfile() {
        AuthUserBO bo = existing();
        bo.setNickname("Alice Two");
        service.update(bo);
        verify(keycloak).updateProfile("kc-1", "alice", "Alice Two");
    }

    @Test
    void keycloakFailureRollsBackUserUpdate() {
        doThrow(new UserException(ReturnCode.THIRD_PARTY_SERVICE_ERROR, "失败")).when(keycloak).updateProfile("kc-1", "alice", "Alice Two");
        AuthUserBO bo = existing();
        bo.setNickname("Alice Two");
        assertThatThrownBy(() -> service.update(bo)).isInstanceOf(UserException.class);
        assertThat(jdbc.queryForObject("SELECT nickname FROM auth_user WHERE id = 'u1'", String.class)).isEqualTo("Alice");
    }

    @Test
    void deleteUserDeletesKeycloakAccountAndBumpsVersion() {
        assertThat(service.remove("t1", "u1")).isTrue();
        verify(keycloak).deleteAccount("kc-1");
        verify(cache).bumpVersion("u1");
    }

    @Test
    void keycloakFailureKeepsUserWhenDeleting() {
        doThrow(new UserException(ReturnCode.THIRD_PARTY_SERVICE_ERROR, "失败")).when(keycloak).setEnabled("kc-1", false);
        assertThatThrownBy(() -> service.remove("t1", "u1")).isInstanceOf(UserException.class);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM auth_user WHERE id = 'u1' AND is_deleted = false", Integer.class)).isEqualTo(1);
        verify(keycloak, never()).deleteAccount(anyString());
    }

    /**
     * 删账号撤不回：事务里只停用 Keycloak 账号，本地提交之后才删除。
     */
    @Test
    void deletingDisablesTheAccountInTheTransactionAndDeletesItAfterCommit() {
        assertThat(service.remove("t1", "u1")).isTrue();
        InOrder order = inOrder(keycloak);
        order.verify(keycloak).setEnabled("kc-1", false);
        order.verify(keycloak).deleteAccount("kc-1");
    }

    /**
     * 本地删除在 Keycloak 停用之后回滚：重新启用账号，不删除，用户照常登录。
     */
    @Test
    void rolledBackDeletionReEnablesTheKeycloakAccount() {
        doThrow(new IllegalStateException("synthetic failure after keycloak")).when(invalidator).user("u1");
        assertThatThrownBy(() -> service.remove("t1", "u1")).isInstanceOf(IllegalStateException.class);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM auth_user WHERE id = 'u1' AND is_deleted = false", Integer.class)).isEqualTo(1);
        InOrder order = inOrder(keycloak);
        order.verify(keycloak).setEnabled("kc-1", false);
        order.verify(keycloak).setEnabled("kc-1", true);
        verify(keycloak, never()).deleteAccount(anyString());
    }

    /**
     * 提交之后删 Keycloak 账号失败：本地删除已生效、账号已停用（登录不了），记一行日志由维护者清理，不把已提交的删除报成失败。
     */
    @Test
    void failedDeletionAfterCommitIsLoggedForCleanup(CapturedOutput output) {
        doThrow(new UserException(ReturnCode.THIRD_PARTY_SERVICE_ERROR, "失败")).when(keycloak).deleteAccount("kc-1");
        assertThat(service.remove("t1", "u1")).isTrue();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM auth_user WHERE id = 'u1' AND is_deleted = false", Integer.class)).isZero();
        assertThat(output.getAll()).contains("请手工删除").contains("kc-1");
    }

    /**
     * 重置口令撤不回，排在能撤回的改动之后：改资料或启停失败时口令不动，用户不会被意外换掉口令。
     */
    @Test
    void failedProfileChangeLeavesThePasswordUntouched() {
        doThrow(new UserException(ReturnCode.THIRD_PARTY_SERVICE_ERROR, "失败")).when(keycloak).updateProfile("kc-1", "alice", "Alice Two");
        AuthUserBO bo = existing();
        bo.setNickname("Alice Two");
        bo.setPassword("New-Password-" + UUID.randomUUID());
        assertThatThrownBy(() -> service.update(bo)).isInstanceOf(UserException.class);
        verify(keycloak, never()).resetPassword(anyString(), anyString());
    }

    /**
     * 重置口令失败（如不满足口令策略）时本地回滚，已写进 Keycloak 的资料与启停状态改回原样。
     */
    @Test
    void failedPasswordResetRestoresTheKeycloakProfileAndEnabledState() {
        AuthUserBO bo = existing();
        bo.setNickname("Alice Two");
        bo.setState(AuthStateEnum.禁用);
        bo.setPassword("New-Password-" + UUID.randomUUID());
        doThrow(new UserException(ReturnCode.PASSWORD_VALIDATION_FAILED, "口令不符合策略")).when(keycloak).resetPassword("kc-1", bo.getPassword());
        assertThatThrownBy(() -> service.update(bo)).isInstanceOf(UserException.class);
        assertThat(jdbc.queryForObject("SELECT nickname FROM auth_user WHERE id = 'u1'", String.class)).isEqualTo("Alice");
        InOrder order = inOrder(keycloak);
        order.verify(keycloak).updateProfile("kc-1", "alice", "Alice Two");
        order.verify(keycloak).setEnabled("kc-1", false);
        order.verify(keycloak).resetPassword("kc-1", bo.getPassword());
        verify(keycloak).updateProfile("kc-1", "alice", "Alice");
        verify(keycloak).setEnabled("kc-1", true);
    }

    @Test
    void committedUpdateIsNotCompensated() {
        AuthUserBO bo = existing();
        bo.setNickname("Alice Two");
        bo.setState(AuthStateEnum.禁用);
        assertThat(service.update(bo)).isTrue();
        verify(keycloak, never()).updateProfile("kc-1", "alice", "Alice");
        verify(keycloak, never()).setEnabled("kc-1", true);
    }

    private static AuthUserBO newUser(String username, String nickname) {
        AuthUserBO bo = new AuthUserBO();
        bo.setTenantId("t1");
        bo.setUsername(username);
        bo.setNickname(nickname);
        bo.setDeptId("d1");
        bo.setPassword("Temp-" + UUID.randomUUID());
        return bo;
    }

    private static AuthUserBO existing() {
        AuthUserBO bo = new AuthUserBO();
        bo.setId("u1");
        bo.setVersion(0);
        bo.setTenantId("t1");
        bo.setUsername("alice");
        bo.setNickname("Alice");
        bo.setDeptId("d1");
        return bo;
    }

    /**
     * 测试上下文：H2 认证表加用户服务；Keycloak、业务上下文缓存与账号事件用替身。
     */
    @Configuration(proxyBeanMethods = false)
    @Import({AuthH2TestConfiguration.class, AuthUserServiceImpl.class, AuthSecurityContextInvalidator.class, AuthTenantManagementAccess.class})
    static class Config {

        @Bean
        KeycloakAccountClient keycloakAccountClient() {
            return mock(KeycloakAccountClient.class);
        }

        @Bean
        SecurityUserCache securityUserCache() {
            return mock(SecurityUserCache.class);
        }

        @Bean
        UserAccountEventProducer userAccountEventProducer() {
            return mock(UserAccountEventProducer.class);
        }

        @Bean
        AuthUserServiceQuery authUserServiceQuery() {
            return mock(AuthUserServiceQuery.class);
        }

        @Bean
        AuthUserServiceResults authUserServiceResults() {
            return mock(AuthUserServiceResults.class);
        }
    }
}
