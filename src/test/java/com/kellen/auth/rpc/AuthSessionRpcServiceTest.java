package com.kellen.auth.rpc;

import com.kellen.auth.entity.AuthTenant;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.mapper.AuthTenantMapper;
import com.kellen.auth.mapper.AuthUserMapper;
import com.kellen.auth.mapper.AuthUserTenantMapper;
import com.kellen.auth.service.AuthTokenLifecycleService;
import com.kellen.datapermission.DataPermissionContextHolder;
import com.kellen.security.AuthTokenRedisKeys;
import com.kellen.utils.auth.JwtKeyConfig;
import com.kellen.utils.auth.JwtUtils;
import com.kellen.utils.context.TenantContextHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mock.env.MockEnvironment;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/** 真实认证 Bean + JWT 签名解析；数据库/Redis仅使用合成状态，不连接共享环境。 */
@SpringBootTest(classes = AuthSessionRpcServiceTest.Config.class,
        properties = {"test.identity.fixture=true","spring.config.name=isolated-auth-session", "spring.cloud.nacos.config.enabled=false",
                "spring.cloud.nacos.discovery.enabled=false", "spring.cloud.nacos.config.import-check.enabled=false"})
class AuthSessionRpcServiceTest {
    @Autowired AuthSessionRpcServiceImpl provider;
    @Autowired AuthTokenLifecycleService tokens;
    @Autowired StringRedisTemplate redis;
    @Autowired ValueOperations<String,String> values;
    @Autowired AuthUserMapper users;
    @Autowired AuthTenantMapper tenants;
    @Autowired AuthUserTenantMapper memberships;
    @BeforeEach void setUp() {
        reset(redis,values,users,tenants,memberships);
        new JwtKeyConfig(new MockEnvironment().withProperty("security.jwt.secret", java.util.Base64.getEncoder().encodeToString(new byte[32]))
                .withProperty("security.jwt.issuer", "synthetic-user-issuer"));
        when(redis.opsForValue()).thenReturn(values);
        AuthUser user = new AuthUser(); user.setId("user-a"); user.setTenantId("tenant-a"); user.setState(AuthStateEnum.启用);
        AuthTenant tenant = new AuthTenant(); tenant.setId("tenant-a"); tenant.setState(AuthStateEnum.启用);
        when(users.selectById("user-a")).thenAnswer(call -> {
            assertThat(TenantContextHolder.isIgnore()).isTrue();
            assertThat(DataPermissionContextHolder.isIgnore()).isTrue(); return user;
        });
        when(tenants.selectById("tenant-a")).thenReturn(tenant);
    }
    @AfterEach void clear() { TenantContextHolder.clear(); DataPermissionContextHolder.clear(); }
    private Map<String,Object> claims() {
        Map<String,Object> claims = new HashMap<>(); claims.put("tenantId","tenant-a"); claims.put("userId","user-a");
        claims.put(AuthTokenRedisKeys.CLAIM_TOKEN_TYPE,AuthTokenRedisKeys.ACCESS_TOKEN_TYPE);
        claims.put(AuthTokenRedisKeys.CLAIM_TOKEN_VERSION,"0"); return claims;
    }
    private String token(Map<String,Object> claims) { return JwtUtils.createJwt("synthetic-jti","user-a",claims,60000); }
    @Test void validAccessReturnsOnlyVerifiedIdsAndRestoresCallingContext() {
        TenantContextHolder.setTenantId("untrusted-caller"); DataPermissionContextHolder.ignore();
        var identity = provider.resolveAccessIdentity(token(claims()));
        assertThat(identity.getUserId()).isEqualTo("user-a"); assertThat(identity.getTenantId()).isEqualTo("tenant-a");
        assertThat(TenantContextHolder.getTenantId()).isEqualTo("untrusted-caller");
        assertThat(TenantContextHolder.isIgnore()).isFalse(); assertThat(DataPermissionContextHolder.isIgnore()).isTrue();
    }
    @Test void revokedAndChangedTokenVersionFailBeforeDatabaseLookup() {
        when(redis.hasKey(AuthTokenRedisKeys.accessTokenRevoked("synthetic-jti"))).thenReturn(true);
        assertThatThrownBy(() -> provider.resolveAccessIdentity(token(claims()))).hasMessageNotContaining("eyJ");
        when(redis.hasKey(any())).thenReturn(false);
        when(values.get(AuthTokenRedisKeys.userTokenVersion("user-a"))).thenReturn("1");
        assertThatThrownBy(() -> provider.resolveAccessIdentity(token(claims()))).isInstanceOf(RuntimeException.class);
        verifyNoInteractions(users,tenants,memberships);
    }
    @Test void refreshMissingLifecycleWrongIssuerAndSubjectMismatchAreRejected() {
        var refresh = claims(); refresh.put(AuthTokenRedisKeys.CLAIM_TOKEN_TYPE, AuthTokenRedisKeys.REFRESH_TOKEN_TYPE);
        var missing = claims(); missing.remove(AuthTokenRedisKeys.CLAIM_TOKEN_VERSION);
        var mismatch = claims(); mismatch.put("userId","other-user");
        for (String invalid : java.util.List.of(token(refresh),token(missing),token(mismatch),
                JwtUtils.createJwt("synthetic-jti","user-a",claims(),60000,"untrusted-issuer"),"not-a-jwt")) {
            assertThatThrownBy(() -> provider.resolveAccessIdentity(invalid)).isInstanceOf(RuntimeException.class);
        }
        verifyNoInteractions(users,tenants,memberships);
    }
    @Test void expiredAndForgedSignatureFailBeforeDatabaseLookup() throws Exception {
        String expired = JwtUtils.createJwt("synthetic-expired","user-a",claims(),1);
        Thread.sleep(10);
        assertThatThrownBy(() -> provider.resolveAccessIdentity(expired)).isInstanceOf(RuntimeException.class);
        String valid = token(claims()); String[] parts = valid.split("\\.");
        assertThatThrownBy(() -> provider.resolveAccessIdentity(parts[0]+"."+parts[1]+"."+"A".repeat(43))).isInstanceOf(RuntimeException.class);
        verifyNoInteractions(users,tenants,memberships);
    }
    @Test void disabledDeletedOrUnmappedIdentityNeverReturnsAuthorizationAndRestoresIgnoreFlags() {
        TenantContextHolder.ignore();
        doReturn(null).when(users).selectById("user-a");
        assertThatThrownBy(() -> provider.resolveAccessIdentity(token(claims()))).isInstanceOf(RuntimeException.class);
        assertThat(TenantContextHolder.isIgnore()).isTrue(); assertThat(DataPermissionContextHolder.isIgnore()).isFalse();
        AuthUser user = new AuthUser(); user.setId("user-a"); user.setTenantId("tenant-b"); user.setState(AuthStateEnum.启用);
        doReturn(user).when(users).selectById("user-a"); when(memberships.selectCount(any())).thenReturn(0L);
        assertThatThrownBy(() -> provider.resolveAccessIdentity(token(claims()))).isInstanceOf(RuntimeException.class);
        user.setTenantId("tenant-a"); user.setState(AuthStateEnum.禁用);
        assertThatThrownBy(() -> provider.resolveAccessIdentity(token(claims()))).isInstanceOf(RuntimeException.class);
        user.setState(AuthStateEnum.启用); when(tenants.selectById("tenant-a")).thenReturn(null);
        assertThatThrownBy(() -> provider.resolveAccessIdentity(token(claims()))).isInstanceOf(RuntimeException.class);
    }
    @Test void validExplicitMembershipPreservesExistingCrossTenantLoginContract() {
        AuthUser user = new AuthUser(); user.setId("user-a"); user.setTenantId("home-tenant"); user.setState(AuthStateEnum.启用);
        doReturn(user).when(users).selectById("user-a"); when(memberships.selectCount(any())).thenReturn(1L);
        assertThat(provider.resolveAccessIdentity(token(claims())).getTenantId()).isEqualTo("tenant-a");
    }
    @Configuration @ConditionalOnProperty(name="test.identity.fixture",havingValue="true") @Import({AuthTokenLifecycleService.class,AuthSessionRpcServiceImpl.class})
    static class Config {
        @Bean StringRedisTemplate redis() { return mock(StringRedisTemplate.class); }
        @Bean @SuppressWarnings("unchecked") ValueOperations<String,String> values() { return mock(ValueOperations.class); }
        @Bean AuthUserMapper users() { return mock(AuthUserMapper.class); }
        @Bean AuthTenantMapper tenants() { return mock(AuthTenantMapper.class); }
        @Bean AuthUserTenantMapper memberships() { return mock(AuthUserTenantMapper.class); }
    }
}
