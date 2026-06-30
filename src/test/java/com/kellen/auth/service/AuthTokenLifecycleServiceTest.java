package com.kellen.auth.service;

import com.kellen.security.AuthTokenRedisKeys;
import com.kellen.utils.auth.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthTokenLifecycleServiceTest {

    @Mock
    private StringRedisTemplate stringRedisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    private AuthTokenLifecycleService authTokenLifecycleService;

    @BeforeEach
    void setUp() {
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        authTokenLifecycleService = new AuthTokenLifecycleService(stringRedisTemplate);
    }

    @Test
    void issueTokensStoresRefreshSessionAndEmbedsLifecycleClaims() {
        when(valueOperations.get(AuthTokenRedisKeys.userTokenVersion("user-1"))).thenReturn("3");

        Map<String, Object> claims = new HashMap<>();
        claims.put("tenantId", "tenant-1");
        claims.put("username", "tester");

        AuthTokenLifecycleService.TokenPair tokenPair = authTokenLifecycleService.issueTokens("user-1",
                "tenant-1",
                "WECHAT_MINI",
                "PATIENT",
                claims);

        Claims accessClaims = JwtUtils.parseJwt(tokenPair.accessToken());
        Claims refreshClaims = JwtUtils.parseJwt(tokenPair.refreshToken());

        assertThat(accessClaims.get(AuthTokenRedisKeys.CLAIM_TOKEN_TYPE, String.class)).isEqualTo(AuthTokenRedisKeys.ACCESS_TOKEN_TYPE);
        assertThat(accessClaims.get(AuthTokenRedisKeys.CLAIM_TOKEN_VERSION, String.class)).isEqualTo("3");
        assertThat(accessClaims.get(AuthTokenRedisKeys.CLAIM_REFRESH_TOKEN_ID, String.class)).isEqualTo(refreshClaims.getId());
        assertThat(refreshClaims.get(AuthTokenRedisKeys.CLAIM_TOKEN_TYPE, String.class)).isEqualTo(AuthTokenRedisKeys.REFRESH_TOKEN_TYPE);
        assertThat(refreshClaims.get(AuthTokenRedisKeys.CLAIM_TOKEN_VERSION, String.class)).isEqualTo("3");
        assertThat(tokenPair.expiresIn()).isEqualTo(AuthTokenLifecycleService.ACCESS_TOKEN_EXPIRE_MILLIS / 1000);
        assertThat(tokenPair.refreshExpiresIn()).isEqualTo(AuthTokenLifecycleService.REFRESH_TOKEN_EXPIRE_MILLIS / 1000);
        verify(valueOperations).set(AuthTokenRedisKeys.refreshToken(refreshClaims.getId()),
                "user-1|tenant-1|3",
                AuthTokenLifecycleService.REFRESH_TOKEN_EXPIRE_MILLIS,
                TimeUnit.MILLISECONDS);
    }

    @Test
    void consumeRefreshTokenDeletesStoredSessionAndReturnsSession() {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AuthTokenRedisKeys.CLAIM_TOKEN_TYPE, AuthTokenRedisKeys.REFRESH_TOKEN_TYPE);
        claims.put(AuthTokenRedisKeys.CLAIM_TOKEN_VERSION, "5");
        String refreshToken = JwtUtils.createJwt("refresh-1", "user-1", claims, AuthTokenLifecycleService.REFRESH_TOKEN_EXPIRE_MILLIS);
        when(valueOperations.get(AuthTokenRedisKeys.refreshToken("refresh-1"))).thenReturn("user-1|tenant-1|5");
        when(valueOperations.get(AuthTokenRedisKeys.userTokenVersion("user-1"))).thenReturn("5");

        AuthTokenLifecycleService.RefreshSession refreshSession = authTokenLifecycleService.consumeRefreshToken(refreshToken);

        assertThat(refreshSession.userId()).isEqualTo("user-1");
        assertThat(refreshSession.tenantId()).isEqualTo("tenant-1");
        assertThat(refreshSession.tokenVersion()).isEqualTo("5");
        verify(stringRedisTemplate).delete(AuthTokenRedisKeys.refreshToken("refresh-1"));
    }

    @Test
    void revokeAccessTokenMarksAccessTokenAndDeletesPairedRefreshToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AuthTokenRedisKeys.CLAIM_TOKEN_TYPE, AuthTokenRedisKeys.ACCESS_TOKEN_TYPE);
        claims.put(AuthTokenRedisKeys.CLAIM_REFRESH_TOKEN_ID, "refresh-1");
        String accessToken = JwtUtils.createJwt("access-1", "user-1", claims, 60_000);

        authTokenLifecycleService.revokeAccessToken("Bearer " + accessToken);

        ArgumentCaptor<Long> ttlCaptor = ArgumentCaptor.forClass(Long.class);
        verify(valueOperations).set(eq(AuthTokenRedisKeys.accessTokenRevoked("access-1")),
                eq("1"),
                ttlCaptor.capture(),
                eq(TimeUnit.MILLISECONDS));
        assertThat(ttlCaptor.getValue()).isPositive();
        assertThat(ttlCaptor.getValue()).isLessThanOrEqualTo(60_000);
        verify(stringRedisTemplate).delete(AuthTokenRedisKeys.refreshToken("refresh-1"));
    }

    @Test
    void revokeUserTokensIncrementsUserTokenVersion() {
        authTokenLifecycleService.revokeUserTokens("user-1");

        verify(valueOperations).increment(AuthTokenRedisKeys.userTokenVersion("user-1"));
    }
}
