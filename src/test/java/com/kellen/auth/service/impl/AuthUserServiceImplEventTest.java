package com.kellen.auth.service.impl;

import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.bo.AuthUserBO;
import com.kellen.auth.keycloak.KeycloakAccountClient;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.auth.mapper.AuthUserTenantMapper;
import com.kellen.auth.mq.producer.UserAccountEventProducer;
import com.kellen.auth.service.AuthSecurityContextInvalidator;
import com.kellen.auth.service.query.AuthUserServiceQuery;
import com.kellen.auth.service.results.AuthUserServiceResults;
import com.kellen.utils.context.TenantContextHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * 新建账号时发“账号已创建”事件：只在真的插入了新用户时发，同租户同用户名已存在时不发。
 */
class AuthUserServiceImplEventTest {

    private final AuthUserMapper authUserMapper = mock(AuthUserMapper.class);

    private final UserAccountEventProducer producer = mock(UserAccountEventProducer.class);

    private final KeycloakAccountClient keycloak = mock(KeycloakAccountClient.class);

    private final AuthUserServiceImpl service = new AuthUserServiceImpl(authUserMapper, mock(AuthUserServiceQuery.class),
            mock(AuthUserServiceResults.class), mock(AuthUserTenantMapper.class), keycloak, mock(AuthSecurityContextInvalidator.class), producer);

    @AfterEach
    void clearContext() {
        TenantContextHolder.clear();
    }

    @Test
    void newUserPublishesAccountCreatedEvent() {
        doAnswer(invocation -> {
            invocation.<AuthUser>getArgument(0).setId("u-new");
            return 1;
        }).when(authUserMapper).insert(any(AuthUser.class));
        when(keycloak.createAccount(eq("u-new"), eq("alice"), eq("Alice"), any())).thenReturn("kc-new");

        assertThat(service.save(newUser())).isEqualTo("u-new");

        ArgumentCaptor<AuthUser> user = ArgumentCaptor.forClass(AuthUser.class);
        verify(producer, times(1)).publishAccountCreated(user.capture(), eq("tenant-1"));
        assertThat(user.getValue().getId()).isEqualTo("u-new");
        assertThat(user.getValue().getUsername()).isEqualTo("alice");
        assertThat(user.getValue().getNickname()).isEqualTo("Alice");
    }

    @Test
    void existingUsernameDoesNotPublish() {
        AuthUser existing = new AuthUser();
        existing.setId("u-old");
        when(authUserMapper.selectOne(any())).thenReturn(existing);

        assertThat(service.save(newUser())).isEqualTo("u-old");

        verifyNoInteractions(producer, keycloak);
    }

    /** 新账号的写入参数；口令是每次随机生成的测试值。 */
    private static AuthUserBO newUser() {
        AuthUserBO bo = new AuthUserBO();
        bo.setTenantId("tenant-1");
        bo.setUsername("alice");
        bo.setNickname("Alice");
        bo.setPassword(UUID.randomUUID().toString());
        return bo;
    }
}
