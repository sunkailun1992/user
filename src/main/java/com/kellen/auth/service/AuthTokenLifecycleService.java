package com.kellen.auth.service;

import com.kellen.security.AuthTokenRedisKeys;
import com.kellen.utils.auth.JwtUtils;
import com.kellen.utils.enumeration.ReturnCode;
import com.kellen.utils.exception.UserException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 认证 token 生命周期服务。
 *
 * <p>Redis 只保存 refresh token 有效性、access token 撤销标记和用户 token 版本，不保存用户对象。</p>
 */
@Service
public class AuthTokenLifecycleService {

    /**
     * access token 有效期：1天。
     */
    public static final long ACCESS_TOKEN_EXPIRE_MILLIS = JwtUtils.DEFAULT_EXPIRE_TIME;

    /**
     * refresh token 有效期：30天。
     */
    public static final long REFRESH_TOKEN_EXPIRE_MILLIS = 30L * 24 * 60 * 60 * 1000;

    /**
     * refresh token 存储值分隔符。
     */
    private static final String VALUE_SEPARATOR = "|";

    /**
     * Redis字符串客户端。
     */
    private final StringRedisTemplate stringRedisTemplate;

    public AuthTokenLifecycleService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 签发 access token 和 refresh token。
     *
     * @param userId        用户ID
     * @param tenantId      租户ID
     * @param loginProvider 登录来源
     * @param subjectType   三方主体类型
     * @param accessClaims  access token 业务声明
     * @return token 对
     */
    public TokenPair issueTokens(String userId, String tenantId, String loginProvider, String subjectType, Map<String, Object> accessClaims) {
        String tokenVersion = currentTokenVersion(userId);
        String refreshTokenId = UUID.randomUUID().toString();
        Map<String, Object> accessTokenClaims = new HashMap<>(accessClaims);
        accessTokenClaims.put(AuthTokenRedisKeys.CLAIM_TOKEN_TYPE, AuthTokenRedisKeys.ACCESS_TOKEN_TYPE);
        accessTokenClaims.put(AuthTokenRedisKeys.CLAIM_TOKEN_VERSION, tokenVersion);
        accessTokenClaims.put(AuthTokenRedisKeys.CLAIM_REFRESH_TOKEN_ID, refreshTokenId);
        String accessToken = JwtUtils.createJwt(UUID.randomUUID().toString(), userId, accessTokenClaims, ACCESS_TOKEN_EXPIRE_MILLIS);

        Map<String, Object> refreshTokenClaims = new HashMap<>();
        refreshTokenClaims.put("userId", userId);
        refreshTokenClaims.put("tenantId", tenantId);
        refreshTokenClaims.put("loginProvider", StringUtils.defaultIfBlank(loginProvider, "LOCAL"));
        refreshTokenClaims.put(AuthTokenRedisKeys.CLAIM_TOKEN_TYPE, AuthTokenRedisKeys.REFRESH_TOKEN_TYPE);
        refreshTokenClaims.put(AuthTokenRedisKeys.CLAIM_TOKEN_VERSION, tokenVersion);
        if (StringUtils.isNotBlank(subjectType)) {
            refreshTokenClaims.put("subjectType", subjectType);
        }
        String refreshToken = JwtUtils.createJwt(refreshTokenId, userId, refreshTokenClaims, REFRESH_TOKEN_EXPIRE_MILLIS);
        stringRedisTemplate.opsForValue().set(AuthTokenRedisKeys.refreshToken(refreshTokenId),
                serializeRefreshSession(userId, tenantId, tokenVersion),
                REFRESH_TOKEN_EXPIRE_MILLIS,
                TimeUnit.MILLISECONDS);

        return new TokenPair(accessToken, refreshToken, ACCESS_TOKEN_EXPIRE_MILLIS / 1000, REFRESH_TOKEN_EXPIRE_MILLIS / 1000);
    }

    /**
     * 消费 refresh token 并返回刷新会话。
     *
     * @param refreshToken refresh token
     * @return refresh 会话
     */
    public RefreshSession consumeRefreshToken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            throw new UserException(ReturnCode.无效TOKEN, "refreshToken不能为空");
        }
        Claims claims = parseToken(refreshToken);
        if (!AuthTokenRedisKeys.REFRESH_TOKEN_TYPE.equals(claims.get(AuthTokenRedisKeys.CLAIM_TOKEN_TYPE, String.class))) {
            throw new UserException(ReturnCode.无效TOKEN, "无效refreshToken");
        }
        String tokenId = claims.getId();
        if (StringUtils.isBlank(tokenId)) {
            throw new UserException(ReturnCode.无效TOKEN, "无效refreshToken");
        }
        String key = AuthTokenRedisKeys.refreshToken(tokenId);
        String rawSession = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(rawSession)) {
            throw new UserException(ReturnCode.用户登录已过期, "refreshToken已失效");
        }
        stringRedisTemplate.delete(key);
        RefreshSession session = deserializeRefreshSession(rawSession);
        if (!StringUtils.equals(session.userId(), claims.getSubject())) {
            throw new UserException(ReturnCode.无效TOKEN, "refreshToken主体不匹配");
        }
        if (!StringUtils.equals(session.tokenVersion(), currentTokenVersion(session.userId()))) {
            throw new UserException(ReturnCode.用户登录已过期, "登录状态已失效");
        }
        return session;
    }

    /**
     * 撤销当前 access token。
     *
     * @param authorization Authorization请求头
     */
    public void revokeAccessToken(String authorization) {
        String token = resolveBearerToken(authorization);
        if (StringUtils.isBlank(token)) {
            return;
        }
        try {
            Claims claims = JwtUtils.parseJwt(token);
            Date expiration = claims.getExpiration();
            long ttlMillis = expiration == null ? 0 : expiration.getTime() - System.currentTimeMillis();
            if (ttlMillis > 0 && StringUtils.isNotBlank(claims.getId())) {
                stringRedisTemplate.opsForValue().set(AuthTokenRedisKeys.accessTokenRevoked(claims.getId()), "1", ttlMillis, TimeUnit.MILLISECONDS);
            }
            String refreshTokenId = claims.get(AuthTokenRedisKeys.CLAIM_REFRESH_TOKEN_ID, String.class);
            if (StringUtils.isNotBlank(refreshTokenId)) {
                stringRedisTemplate.delete(AuthTokenRedisKeys.refreshToken(refreshTokenId));
            }
        } catch (Exception ignored) {
            // 退出登录不因已过期或格式错误的 access token 失败，客户端本地态仍可清理。
        }
    }

    /**
     * 撤销 refresh token。
     *
     * @param refreshToken refresh token
     */
    public void revokeRefreshToken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            return;
        }
        try {
            Claims claims = JwtUtils.parseJwt(refreshToken);
            if (StringUtils.isNotBlank(claims.getId())) {
                stringRedisTemplate.delete(AuthTokenRedisKeys.refreshToken(claims.getId()));
            }
        } catch (Exception ignored) {
            // 退出登录不因 refresh token 已过期或格式错误失败。
        }
    }

    /**
     * 撤销指定用户的全部历史 token。
     *
     * @param userId 用户ID
     */
    public void revokeUserTokens(String userId) {
        if (StringUtils.isBlank(userId)) {
            return;
        }
        stringRedisTemplate.opsForValue().increment(AuthTokenRedisKeys.userTokenVersion(userId));
    }

    /**
     * 查询用户当前 token 版本。
     *
     * @param userId 用户ID
     * @return token版本
     */
    public String currentTokenVersion(String userId) {
        String tokenVersion = stringRedisTemplate.opsForValue().get(AuthTokenRedisKeys.userTokenVersion(userId));
        return StringUtils.defaultIfBlank(tokenVersion, "0");
    }

    private Claims parseToken(String token) {
        try {
            return JwtUtils.parseJwt(token);
        } catch (Exception e) {
            throw new UserException(ReturnCode.无效TOKEN, "无效token");
        }
    }

    private String resolveBearerToken(String authorization) {
        if (StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")) {
            return null;
        }
        return authorization.substring("Bearer ".length());
    }

    private String serializeRefreshSession(String userId, String tenantId, String tokenVersion) {
        return String.join(VALUE_SEPARATOR, userId, StringUtils.defaultString(tenantId), tokenVersion);
    }

    private RefreshSession deserializeRefreshSession(String rawSession) {
        String[] values = rawSession.split("\\|", -1);
        if (values.length < 3 || StringUtils.isBlank(values[0])) {
            throw new UserException(ReturnCode.无效TOKEN, "无效refreshToken");
        }
        return new RefreshSession(values[0], values[1], values[2]);
    }

    /**
     * 已签发 token 对。
     *
     * @param accessToken      access token
     * @param refreshToken     refresh token
     * @param expiresIn        access token有效秒数
     * @param refreshExpiresIn refresh token有效秒数
     */
    public record TokenPair(String accessToken, String refreshToken, long expiresIn, long refreshExpiresIn) {
    }

    /**
     * refresh token 会话。
     *
     * @param userId       用户ID
     * @param tenantId     租户ID
     * @param tokenVersion token版本
     */
    public record RefreshSession(String userId, String tenantId, String tokenVersion) {
    }
}
