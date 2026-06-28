package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.dto.OpenApiSignatureVerifyRequest;
import com.kellen.auth.dto.ThirdPartySessionRequest;
import com.kellen.auth.entity.AuthExternalClient;
import com.kellen.auth.entity.AuthExternalIdentity;
import com.kellen.auth.entity.AuthExternalSignatureNonce;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.vo.AuthLoginVO;
import com.kellen.auth.mapper.AuthExternalClientMapper;
import com.kellen.auth.mapper.AuthExternalIdentityMapper;
import com.kellen.auth.mapper.AuthExternalSignatureNonceMapper;
import com.kellen.auth.service.AuthAuthenticationService;
import com.kellen.auth.service.AuthThirdPartyAuthenticationService;
import com.kellen.datapermission.DataPermissionContextHolder;
import com.kellen.utils.context.TenantContextHolder;
import com.kellen.utils.enumeration.ReturnCode;
import com.kellen.utils.exception.UserException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HexFormat;

/**
 * 三方认证业务服务实现。
 */
@Service
public class AuthThirdPartyAuthenticationServiceImpl implements AuthThirdPartyAuthenticationService {

    private static final long DEFAULT_ALLOWED_CLOCK_SKEW_SECONDS = 300L;

    private final AuthExternalClientMapper authExternalClientMapper;

    private final AuthExternalIdentityMapper authExternalIdentityMapper;

    private final AuthExternalSignatureNonceMapper authExternalSignatureNonceMapper;

    private final AuthAuthenticationService authAuthenticationService;

    public AuthThirdPartyAuthenticationServiceImpl(AuthExternalClientMapper authExternalClientMapper,
                                                   AuthExternalIdentityMapper authExternalIdentityMapper,
                                                   AuthExternalSignatureNonceMapper authExternalSignatureNonceMapper,
                                                   AuthAuthenticationService authAuthenticationService) {
        this.authExternalClientMapper = authExternalClientMapper;
        this.authExternalIdentityMapper = authExternalIdentityMapper;
        this.authExternalSignatureNonceMapper = authExternalSignatureNonceMapper;
        this.authAuthenticationService = authAuthenticationService;
    }

    @Override
    public AuthLoginVO createSession(ThirdPartySessionRequest request) {
        validateRequest(request);
        AuthExternalClient client = findEnabledClient(request.getProviderCode(), request.getClientId());
        verifySignature(request, client);
        recordNonce(request.getProviderCode(), request.getClientId(), request.getNonce(), request.getTimestamp(), client);
        AuthExternalIdentity identity = findEnabledIdentity(request);
        return authAuthenticationService.createSessionForUser(
                identity.getLocalTenantId(),
                identity.getLocalUserId(),
                StringUtils.defaultIfBlank(identity.getProviderCode(), request.getProviderCode()),
                StringUtils.defaultIfBlank(identity.getSubjectType(), request.getSubjectType())
        );
    }

    @Override
    public void verifyOpenApiSignature(OpenApiSignatureVerifyRequest request) {
        validateOpenApiSignatureRequest(request);
        AuthExternalClient client = findEnabledClient(request.getProviderCode(), request.getClientId());
        verifyOpenApiSignatureValue(request, client);
        recordOpenApiNonce(request, client);
    }

    private void validateRequest(ThirdPartySessionRequest request) {
        if (request == null) {
            throw new UserException(ReturnCode.请求必填参数为空, "三方认证请求不能为空");
        }
        if (StringUtils.isAnyBlank(request.getProviderCode(), request.getClientId())) {
            throw new UserException(ReturnCode.请求必填参数为空, "三方系统和客户端不能为空");
        }
        if (StringUtils.isAllBlank(request.getExternalUserId(), request.getExternalPatientId())) {
            throw new UserException(ReturnCode.请求必填参数为空, "外部用户或主体不能为空");
        }
    }

    private AuthExternalClient findEnabledClient(String providerCode, String clientId) {
        try {
            TenantContextHolder.ignore();
            DataPermissionContextHolder.ignore();
            AuthExternalClient client = authExternalClientMapper.selectOne(new LambdaQueryWrapper<AuthExternalClient>()
                    .eq(AuthExternalClient::getProviderCode, providerCode)
                    .eq(AuthExternalClient::getClientId, clientId)
                    .eq(AuthExternalClient::getState, AuthStateEnum.启用)
                    .last("LIMIT 1"));
            if (client == null) {
                throw new UserException(ReturnCode.用户未获得第三方登录授权, "三方客户端未授权");
            }
            return client;
        } finally {
            TenantContextHolder.clearIgnore();
            DataPermissionContextHolder.clear();
        }
    }

    private void verifySignature(ThirdPartySessionRequest request, AuthExternalClient client) {
        if (Boolean.FALSE.equals(client.getSignatureRequired())) {
            return;
        }
        if (request.getTimestamp() == null || StringUtils.isAnyBlank(request.getNonce(), request.getSignature())) {
            throw new UserException(ReturnCode.用户签名异常, "三方签名参数不完整");
        }
        long skewSeconds = client.getAllowedClockSkewSeconds() == null
                ? DEFAULT_ALLOWED_CLOCK_SKEW_SECONDS
                : client.getAllowedClockSkewSeconds();
        long nowMillis = Instant.now().toEpochMilli();
        if (Math.abs(nowMillis - request.getTimestamp()) > skewSeconds * 1000L) {
            throw new UserException(ReturnCode.用户签名异常, "三方签名已过期");
        }
        String expected = hmacSha256Hex(client.getClientSecret(), canonicalSignText(request));
        if (!MessageDigest.isEqual(expected.getBytes(StandardCharsets.UTF_8), request.getSignature().getBytes(StandardCharsets.UTF_8))) {
            throw new UserException(ReturnCode.用户签名异常, "三方签名不正确");
        }
    }

    private void validateOpenApiSignatureRequest(OpenApiSignatureVerifyRequest request) {
        if (request == null) {
            throw new UserException(ReturnCode.请求必填参数为空, "开放接口签名校验请求不能为空");
        }
        if (StringUtils.isAnyBlank(request.getProviderCode(), request.getClientId())) {
            throw new UserException(ReturnCode.请求必填参数为空, "开放接口系统和客户端不能为空");
        }
    }

    private void verifyOpenApiSignatureValue(OpenApiSignatureVerifyRequest request, AuthExternalClient client) {
        if (Boolean.FALSE.equals(client.getSignatureRequired())) {
            return;
        }
        if (StringUtils.isAnyBlank(request.getTimestamp(), request.getNonce(), request.getSignature())) {
            throw new UserException(ReturnCode.用户签名异常, "开放接口签名参数不完整");
        }
        long requestTimestamp = parseTimestamp(request.getTimestamp());
        long skewSeconds = client.getAllowedClockSkewSeconds() == null
                ? DEFAULT_ALLOWED_CLOCK_SKEW_SECONDS
                : client.getAllowedClockSkewSeconds();
        long nowMillis = Instant.now().toEpochMilli();
        if (Math.abs(nowMillis - requestTimestamp) > skewSeconds * 1000L) {
            throw new UserException(ReturnCode.用户签名异常, "开放接口签名已过期");
        }
        String expected = hmacSha256Hex(client.getClientSecret(), canonicalOpenApiSignText(request));
        if (!MessageDigest.isEqual(expected.getBytes(StandardCharsets.UTF_8), request.getSignature().getBytes(StandardCharsets.UTF_8))) {
            throw new UserException(ReturnCode.用户签名异常, "开放接口签名不正确");
        }
    }

    private void recordOpenApiNonce(OpenApiSignatureVerifyRequest request, AuthExternalClient client) {
        if (Boolean.FALSE.equals(client.getSignatureRequired())) {
            return;
        }
        recordNonce(request.getProviderCode(), request.getClientId(), request.getNonce(), parseTimestamp(request.getTimestamp()), client);
    }

    private void recordNonce(String providerCode, String clientId, String nonceValue, Long timestampMillis, AuthExternalClient client) {
        if (Boolean.FALSE.equals(client.getSignatureRequired())) {
            return;
        }
        try {
            TenantContextHolder.ignore();
            DataPermissionContextHolder.ignore();
            long skewSeconds = client.getAllowedClockSkewSeconds() == null
                    ? DEFAULT_ALLOWED_CLOCK_SKEW_SECONDS
                    : client.getAllowedClockSkewSeconds();
            AuthExternalSignatureNonce nonce = new AuthExternalSignatureNonce();
            nonce.setProviderCode(providerCode);
            nonce.setClientId(clientId);
            nonce.setNonce(nonceValue);
            nonce.setTimestampMillis(timestampMillis);
            nonce.setExpireDateTime(LocalDateTime.now().plusSeconds(skewSeconds));
            nonce.setState(AuthStateEnum.启用);
            nonce.setTenantId(StringUtils.defaultIfBlank(client.getTenantId(), "1"));
            authExternalSignatureNonceMapper.insert(nonce);
        } catch (DuplicateKeyException e) {
            throw new UserException(ReturnCode.用户签名异常, "开放接口签名随机串已使用");
        } finally {
            TenantContextHolder.clearIgnore();
            DataPermissionContextHolder.clear();
        }
    }

    private AuthExternalIdentity findEnabledIdentity(ThirdPartySessionRequest request) {
        try {
            TenantContextHolder.ignore();
            DataPermissionContextHolder.ignore();
            AuthExternalIdentity identity = authExternalIdentityMapper.selectOne(new LambdaQueryWrapper<AuthExternalIdentity>()
                    .eq(AuthExternalIdentity::getProviderCode, request.getProviderCode())
                    .eq(AuthExternalIdentity::getExternalTenantId, normalize(request.getExternalTenantId()))
                    .eq(AuthExternalIdentity::getExternalUserId, normalize(request.getExternalUserId()))
                    .eq(AuthExternalIdentity::getExternalPatientId, normalize(request.getExternalPatientId()))
                    .eq(AuthExternalIdentity::getState, AuthStateEnum.启用)
                    .last("LIMIT 1"));
            if (identity == null) {
                throw new UserException(ReturnCode.用户未获得第三方登录授权, "外部身份未绑定本地用户");
            }
            return identity;
        } finally {
            TenantContextHolder.clearIgnore();
            DataPermissionContextHolder.clear();
        }
    }

    private String canonicalSignText(ThirdPartySessionRequest request) {
        return String.join("\n",
                normalize(request.getClientId()),
                normalize(request.getProviderCode()),
                normalize(request.getExternalTenantId()),
                normalize(request.getExternalUserId()),
                normalize(request.getExternalPatientId()),
                String.valueOf(request.getTimestamp()),
                normalize(request.getNonce())
        );
    }

    private String canonicalOpenApiSignText(OpenApiSignatureVerifyRequest request) {
        return String.join("\n",
                normalize(request.getClientId()),
                normalize(request.getTimestamp()),
                normalize(request.getNonce()),
                normalize(request.getBody())
        );
    }

    private long parseTimestamp(String timestamp) {
        try {
            return Long.parseLong(timestamp);
        } catch (NumberFormatException e) {
            throw new UserException(ReturnCode.用户签名异常, "开放接口时间戳不正确");
        }
    }

    private String hmacSha256Hex(String secret, String text) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(normalize(secret).getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return HexFormat.of().formatHex(mac.doFinal(text.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new UserException(ReturnCode.用户签名异常, "三方签名计算失败");
        }
    }

    private String normalize(String value) {
        return StringUtils.defaultString(value);
    }
}
