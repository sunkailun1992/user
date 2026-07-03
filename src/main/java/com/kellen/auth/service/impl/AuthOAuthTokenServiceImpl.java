package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kellen.auth.config.OAuthAuthorizationServerProperties;
import com.kellen.auth.dto.LoginRequest;
import com.kellen.auth.dto.OAuthAuthorizeUser;
import com.kellen.auth.dto.OAuthTokenResponse;
import com.kellen.auth.entity.AuthOAuthClient;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.mapper.AuthOAuthClientMapper;
import com.kellen.auth.service.AuthAuthenticationService;
import com.kellen.auth.service.AuthOAuthTokenService;
import com.kellen.security.AuthTokenRedisKeys;
import com.kellen.utils.auth.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * OAuth token 服务实现。
 */
@Service
public class AuthOAuthTokenServiceImpl implements AuthOAuthTokenService {

    private static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    private static final String RESPONSE_TYPE_CODE = "code";
    private static final String TOKEN_TYPE_BEARER = "Bearer";
    private static final String TOKEN_AUTH_METHOD_NONE = "none";
    private static final String TOKEN_AUTH_METHOD_CLIENT_SECRET_BASIC = "client_secret_basic";
    private static final String TOKEN_AUTH_METHOD_CLIENT_SECRET_POST = "client_secret_post";
    private static final String CODE_CHALLENGE_METHOD_S256 = "S256";
    private static final String CLIENT_TYPE_PUBLIC = "public";
    private static final String AUTHORIZATION_CODE_REDIS_KEY_PREFIX = "oauth:authorization-code:";
    private static final long AUTHORIZATION_CODE_TTL_SECONDS = 10L * 60L;
    private static final long DEFAULT_ACCESS_TOKEN_TTL_SECONDS = 900L;
    private static final long MAX_ACCESS_TOKEN_TTL_SECONDS = 30L * 24L * 60L * 60L;

    private final AuthOAuthClientMapper authOAuthClientMapper;
    private final OAuthAuthorizationServerProperties properties;
    private final AuthAuthenticationService authAuthenticationService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StringRedisTemplate stringRedisTemplate;
    private final Map<String, AuthorizationCodeRecord> localAuthorizationCodes = new ConcurrentHashMap<>();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthOAuthTokenServiceImpl(AuthOAuthClientMapper authOAuthClientMapper,
                                     OAuthAuthorizationServerProperties properties,
                                     AuthAuthenticationService authAuthenticationService,
                                     ObjectProvider<StringRedisTemplate> stringRedisTemplateProvider) {
        this.authOAuthClientMapper = authOAuthClientMapper;
        this.properties = properties;
        this.authAuthenticationService = authAuthenticationService;
        this.stringRedisTemplate = stringRedisTemplateProvider.getIfAvailable();
    }

    @Override
    public String authorizationPage(MultiValueMap<String, String> params, String error) {
        AuthorizationRequest request = validateAuthorizationRequest(params);
        return """
                <!doctype html>
                <html lang="zh-CN">
                <head>
                  <meta charset="utf-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1">
                  <title>Kellen OAuth 授权</title>
                  <style>
                    body{margin:0;background:#f6f8fb;color:#172033;font-family:-apple-system,BlinkMacSystemFont,"Segoe UI",sans-serif}
                    main{max-width:440px;margin:8vh auto;background:#fff;border:1px solid #dfe5ef;border-radius:8px;padding:28px;box-shadow:0 16px 40px rgba(22,34,51,.08)}
                    h1{font-size:22px;margin:0 0 8px}
                    p{margin:0 0 20px;color:#5d6b82;line-height:1.6}
                    label{display:block;font-size:13px;color:#4a5668;margin:14px 0 6px}
                    input{width:100%;box-sizing:border-box;border:1px solid #cfd7e6;border-radius:6px;padding:10px 12px;font-size:15px}
                    button{width:100%;margin-top:22px;border:0;border-radius:6px;background:#1677ff;color:#fff;padding:11px 12px;font-size:15px;cursor:pointer}
                    .error{background:#fff1f0;color:#a8071a;border:1px solid #ffa39e;border-radius:6px;padding:10px 12px;margin-bottom:16px}
                    .meta{font-size:12px;color:#7c8798;word-break:break-all}
                  </style>
                </head>
                <body>
                <main>
                  <h1>授权 MCP 客户端</h1>
                  <p>客户端 {{clientId}} 请求访问 Kellen MCP。登录后将按请求的 scope 签发 MCP 专用访问令牌。</p>
                  {{error}}
                  <form method="post" action="/oauth2/authorize" autocomplete="on">
                    {{inputs}}
                    <label>租户 ID</label>
                    <input name="tenantId" value="100" required>
                    <label>用户名</label>
                    <input name="username" autocomplete="username" required>
                    <label>密码</label>
                    <input name="password" type="password" autocomplete="current-password" required>
                    <button type="submit">登录并授权</button>
                  </form>
                  <p class="meta">scope: {{scope}}<br>resource: {{resource}}</p>
                </main>
                </body>
                </html>
                """
                .replace("{{clientId}}", html(request.clientId()))
                .replace("{{error}}", StringUtils.isBlank(error) ? "" : "<div class=\"error\">" + html(error) + "</div>")
                .replace("{{inputs}}", hiddenAuthorizeInputs(params))
                .replace("{{scope}}", html(request.scope()))
                .replace("{{resource}}", html(request.resource()));
    }

    @Override
    public URI authorize(MultiValueMap<String, String> form) {
        AuthorizationRequest request = validateAuthorizationRequest(form);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setTenantId(form.getFirst("tenantId"));
        loginRequest.setUsername(form.getFirst("username"));
        loginRequest.setPassword(form.getFirst("password"));
        OAuthAuthorizeUser user = authAuthenticationService.authenticateForOAuth(loginRequest);

        String code = UUID.randomUUID().toString();
        storeAuthorizationCode(code, new AuthorizationCodeRecord(
                request.clientId(),
                user.userId(),
                user.username(),
                user.tenantId(),
                request.redirectUri(),
                request.scope(),
                request.resource(),
                request.codeChallenge(),
                request.codeChallengeMethod(),
                Instant.now().plusSeconds(AUTHORIZATION_CODE_TTL_SECONDS).getEpochSecond()
        ));

        UriComponentsBuilder redirect = UriComponentsBuilder.fromUriString(request.redirectUri())
                .queryParam("code", code);
        if (StringUtils.isNotBlank(request.state())) {
            redirect.queryParam("state", request.state());
        }
        return redirect.build().toUri();
    }

    @Override
    public OAuthTokenResponse issueToken(String authorization, MultiValueMap<String, String> form) {
        String grantType = form.getFirst("grant_type");
        if (GRANT_TYPE_CLIENT_CREDENTIALS.equals(grantType)) {
            return issueClientCredentialsToken(authorization, form);
        }
        if (GRANT_TYPE_AUTHORIZATION_CODE.equals(grantType)) {
            return issueAuthorizationCodeToken(authorization, form);
        }
        throw oauthError("unsupported_grant_type", "Unsupported grant_type", HttpStatus.BAD_REQUEST);
    }

    @Override
    public OAuthTokenResponse issueClientCredentialsToken(String authorization, MultiValueMap<String, String> form) {
        ClientCredentials credentials = resolveClientCredentials(authorization, form, true);
        AuthOAuthClient client = findEnabledClient(credentials.clientId());
        if (!clientSecretMatches(credentials.clientSecret(), client.getClientSecret())) {
            throw oauthError("invalid_client", "Client authentication failed", HttpStatus.UNAUTHORIZED);
        }
        if (!containsToken(splitValues(client.getGrantTypes()), GRANT_TYPE_CLIENT_CREDENTIALS)) {
            throw oauthError("unauthorized_client", "Client is not allowed to use client_credentials", HttpStatus.BAD_REQUEST);
        }

        Set<String> requestedScopes = resolveScopes(client, form.getFirst("scope"));
        String audience = resolveAudience(form, splitValues(client.getAudiences()));
        return issueAccessToken(client, serviceAccountSubject(client.getClientId()), client.getClientId(), client.getTenantId(),
                GRANT_TYPE_CLIENT_CREDENTIALS, requestedScopes, audience);
    }

    @Override
    public Map<String, Object> authorizationServerMetadata(String issuerBaseUrl) {
        String issuer = normalizeBaseUrl(issuerBaseUrl);
        Map<String, Object> metadata = new LinkedHashMap<>();
        metadata.put("issuer", issuer);
        metadata.put("authorization_endpoint", endpoint(issuer, "/oauth2/authorize"));
        metadata.put("token_endpoint", endpoint(issuer, "/oauth2/token"));
        metadata.put("grant_types_supported", List.of(GRANT_TYPE_AUTHORIZATION_CODE, GRANT_TYPE_CLIENT_CREDENTIALS));
        metadata.put("response_types_supported", List.of(RESPONSE_TYPE_CODE));
        metadata.put("code_challenge_methods_supported", List.of(CODE_CHALLENGE_METHOD_S256));
        metadata.put("token_endpoint_auth_methods_supported", List.of(TOKEN_AUTH_METHOD_NONE, TOKEN_AUTH_METHOD_CLIENT_SECRET_BASIC, TOKEN_AUTH_METHOD_CLIENT_SECRET_POST));
        metadata.put("scopes_supported", List.of("mcp.tools.read", "mcp.tools.call"));
        metadata.put("resource_indicators_supported", true);
        metadata.put("client_id_metadata_document_supported", false);
        return metadata;
    }

    private OAuthTokenResponse issueAuthorizationCodeToken(String authorization, MultiValueMap<String, String> form) {
        ClientCredentials credentials = resolveClientCredentials(authorization, form, false);
        AuthOAuthClient client = findEnabledClient(credentials.clientId());
        if (!containsToken(splitValues(client.getGrantTypes()), GRANT_TYPE_AUTHORIZATION_CODE)) {
            throw oauthError("unauthorized_client", "Client is not allowed to use authorization_code", HttpStatus.BAD_REQUEST);
        }
        if (!TOKEN_AUTH_METHOD_NONE.equals(tokenEndpointAuthMethod(client))
                && !clientSecretMatches(credentials.clientSecret(), client.getClientSecret())) {
            throw oauthError("invalid_client", "Client authentication failed", HttpStatus.UNAUTHORIZED);
        }

        String code = StringUtils.trimToEmpty(form.getFirst("code"));
        String redirectUri = StringUtils.trimToEmpty(form.getFirst("redirect_uri"));
        String codeVerifier = StringUtils.trimToEmpty(form.getFirst("code_verifier"));
        if (StringUtils.isAnyBlank(code, redirectUri, codeVerifier)) {
            throw oauthError("invalid_request", "code, redirect_uri and code_verifier are required", HttpStatus.BAD_REQUEST);
        }
        AuthorizationCodeRecord record = consumeAuthorizationCode(code);
        if (record == null) {
            throw oauthError("invalid_grant", "Authorization code is invalid or expired", HttpStatus.BAD_REQUEST);
        }
        if (!StringUtils.equals(record.clientId(), client.getClientId()) || !StringUtils.equals(record.redirectUri(), redirectUri)) {
            throw oauthError("invalid_grant", "Authorization code binding does not match", HttpStatus.BAD_REQUEST);
        }
        if (!pkceMatches(record, codeVerifier)) {
            throw oauthError("invalid_grant", "PKCE verification failed", HttpStatus.BAD_REQUEST);
        }
        return issueAccessToken(client, record.userId(), record.username(), record.tenantId(),
                GRANT_TYPE_AUTHORIZATION_CODE, splitValues(record.scope()), record.resource());
    }

    private AuthorizationRequest validateAuthorizationRequest(MultiValueMap<String, String> params) {
        String responseType = StringUtils.trimToEmpty(params.getFirst("response_type"));
        String clientId = StringUtils.trimToEmpty(params.getFirst("client_id"));
        String redirectUri = StringUtils.trimToEmpty(params.getFirst("redirect_uri"));
        String codeChallenge = StringUtils.trimToEmpty(params.getFirst("code_challenge"));
        String codeChallengeMethod = StringUtils.defaultIfBlank(params.getFirst("code_challenge_method"), CODE_CHALLENGE_METHOD_S256);
        if (!RESPONSE_TYPE_CODE.equals(responseType)) {
            throw oauthError("unsupported_response_type", "Only response_type=code is supported", HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isAnyBlank(clientId, redirectUri, codeChallenge)) {
            throw oauthError("invalid_request", "client_id, redirect_uri and code_challenge are required", HttpStatus.BAD_REQUEST);
        }
        if (!CODE_CHALLENGE_METHOD_S256.equals(codeChallengeMethod)) {
            throw oauthError("invalid_request", "Only PKCE S256 is supported", HttpStatus.BAD_REQUEST);
        }
        AuthOAuthClient client = findEnabledClient(clientId);
        if (!containsToken(splitValues(client.getGrantTypes()), GRANT_TYPE_AUTHORIZATION_CODE)) {
            throw oauthError("unauthorized_client", "Client is not allowed to use authorization_code", HttpStatus.BAD_REQUEST);
        }
        if (!redirectAllowed(client, redirectUri)) {
            throw oauthError("invalid_request", "redirect_uri is not registered", HttpStatus.BAD_REQUEST);
        }
        Set<String> requestedScopes = resolveScopes(client, params.getFirst("scope"));
        String resource = resolveAudience(params, splitValues(client.getAudiences()));
        return new AuthorizationRequest(
                clientId,
                redirectUri,
                String.join(" ", requestedScopes),
                resource,
                StringUtils.trimToEmpty(params.getFirst("state")),
                codeChallenge,
                codeChallengeMethod
        );
    }

    private OAuthTokenResponse issueAccessToken(AuthOAuthClient client,
                                                String subject,
                                                String username,
                                                String tenantId,
                                                String grantType,
                                                Set<String> scopes,
                                                String audience) {
        long ttlSeconds = resolveTtl(client.getAccessTokenTtlSeconds());
        String joinedScopes = String.join(" ", scopes);
        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put(AuthTokenRedisKeys.CLAIM_TOKEN_TYPE, AuthTokenRedisKeys.ACCESS_TOKEN_TYPE);
        claims.put("userId", subject);
        claims.put("username", username);
        claims.put("tenantId", StringUtils.defaultIfBlank(tenantId, StringUtils.defaultIfBlank(client.getTenantId(), "1")));
        claims.put("client_id", client.getClientId());
        claims.put("grant_type", grantType);
        claims.put("scope", joinedScopes);
        claims.put("scp", new ArrayList<>(scopes));
        claims.put("aud", audience);
        claims.put("resource", audience);
        claims.put("permissions", scopeAuthorities(scopes));

        String accessToken = JwtUtils.createJwt(
                UUID.randomUUID().toString(),
                subject,
                claims,
                ttlSeconds * 1000L,
                tokenIssuer()
        );
        return new OAuthTokenResponse(accessToken, TOKEN_TYPE_BEARER, ttlSeconds, joinedScopes);
    }

    private ClientCredentials resolveClientCredentials(String authorization, MultiValueMap<String, String> form, boolean secretRequired) {
        ClientCredentials basicCredentials = resolveBasicCredentials(authorization);
        if (basicCredentials != null) {
            return basicCredentials;
        }
        String clientId = form.getFirst("client_id");
        String clientSecret = form.getFirst("client_secret");
        if (StringUtils.isBlank(clientId) || (secretRequired && StringUtils.isBlank(clientSecret))) {
            throw oauthError("invalid_client", "Client credentials are required", HttpStatus.UNAUTHORIZED);
        }
        return new ClientCredentials(clientId, clientSecret);
    }

    private ClientCredentials resolveBasicCredentials(String authorization) {
        if (StringUtils.isBlank(authorization) || !authorization.startsWith("Basic ")) {
            return null;
        }
        try {
            String raw = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())), StandardCharsets.UTF_8);
            int separator = raw.indexOf(':');
            if (separator <= 0) {
                throw oauthError("invalid_client", "Invalid Basic client credentials", HttpStatus.UNAUTHORIZED);
            }
            return new ClientCredentials(raw.substring(0, separator), raw.substring(separator + 1));
        } catch (IllegalArgumentException e) {
            throw oauthError("invalid_client", "Invalid Basic client credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    private AuthOAuthClient findEnabledClient(String clientId) {
        AuthOAuthClient client = authOAuthClientMapper.selectOne(new LambdaQueryWrapper<AuthOAuthClient>()
                .eq(AuthOAuthClient::getClientId, clientId)
                .eq(AuthOAuthClient::getState, AuthStateEnum.启用)
                .last("LIMIT 1"));
        if (client == null) {
            throw oauthError("invalid_client", "Client authentication failed", HttpStatus.UNAUTHORIZED);
        }
        return client;
    }

    private boolean clientSecretMatches(String rawSecret, String storedSecret) {
        if (StringUtils.isAnyBlank(rawSecret, storedSecret)) {
            return false;
        }
        if (storedSecret.startsWith("$2a$") || storedSecret.startsWith("$2b$") || storedSecret.startsWith("$2y$")) {
            return passwordEncoder.matches(rawSecret, storedSecret);
        }
        return MessageDigestSupport.constantTimeEquals(rawSecret, storedSecret);
    }

    private Set<String> resolveScopes(AuthOAuthClient client, String requestedScope) {
        Set<String> allowedScopes = splitValues(client.getScopes());
        Set<String> requestedScopes = splitValues(requestedScope);
        if (requestedScopes.isEmpty()) {
            requestedScopes = allowedScopes;
        }
        if (requestedScopes.isEmpty() || !allowedScopes.containsAll(requestedScopes)) {
            throw oauthError("invalid_scope", "Requested scope is not allowed", HttpStatus.BAD_REQUEST);
        }
        return requestedScopes;
    }

    private String resolveAudience(MultiValueMap<String, String> form, Set<String> allowedAudiences) {
        String requested = StringUtils.firstNonBlank(form.getFirst("resource"), form.getFirst("audience"));
        if (StringUtils.isNotBlank(requested)) {
            if (allowedAudiences.contains(requested)) {
                return requested;
            }
            throw oauthError("invalid_target", "Requested resource is not allowed", HttpStatus.BAD_REQUEST);
        }
        return allowedAudiences.stream()
                .findFirst()
                .filter(StringUtils::isNotBlank)
                .orElseThrow(() -> oauthError("invalid_target", "OAuth resource is not configured", HttpStatus.BAD_REQUEST));
    }

    private boolean redirectAllowed(AuthOAuthClient client, String redirectUri) {
        return splitValues(client.getRedirectUris()).stream().anyMatch(registered -> redirectMatches(registered, redirectUri));
    }

    private boolean redirectMatches(String registered, String requested) {
        if (StringUtils.equals(registered, requested)) {
            return true;
        }
        try {
            URI registeredUri = URI.create(registered);
            URI requestedUri = URI.create(requested);
            if (!StringUtils.equalsIgnoreCase(registeredUri.getScheme(), requestedUri.getScheme())) {
                return false;
            }
            if (!isLoopbackHost(registeredUri.getHost()) || !isLoopbackHost(requestedUri.getHost())) {
                return false;
            }
            if (!StringUtils.equals(registeredUri.getPath(), requestedUri.getPath())) {
                return false;
            }
            if (registeredUri.getPort() > 0 && registeredUri.getPort() != requestedUri.getPort()) {
                return false;
            }
            return StringUtils.equals(StringUtils.defaultString(registeredUri.getQuery()), StringUtils.defaultString(requestedUri.getQuery()));
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }

    private boolean isLoopbackHost(String host) {
        return "localhost".equalsIgnoreCase(host) || "127.0.0.1".equals(host) || "::1".equals(host);
    }

    private boolean pkceMatches(AuthorizationCodeRecord record, String codeVerifier) {
        if (!CODE_CHALLENGE_METHOD_S256.equals(record.codeChallengeMethod())) {
            return false;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
            String expected = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
            return MessageDigestSupport.constantTimeEquals(expected, record.codeChallenge());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }

    private void storeAuthorizationCode(String code, AuthorizationCodeRecord record) {
        if (stringRedisTemplate == null) {
            localAuthorizationCodes.put(code, record);
            return;
        }
        try {
            stringRedisTemplate.opsForValue().set(
                    authorizationCodeKey(code),
                    objectMapper.writeValueAsString(record),
                    AUTHORIZATION_CODE_TTL_SECONDS,
                    TimeUnit.SECONDS
            );
        } catch (JsonProcessingException e) {
            throw oauthError("server_error", "Authorization code cannot be stored", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private AuthorizationCodeRecord consumeAuthorizationCode(String code) {
        if (stringRedisTemplate == null) {
            AuthorizationCodeRecord record = localAuthorizationCodes.remove(code);
            if (record == null || record.expireEpochSeconds() < Instant.now().getEpochSecond()) {
                return null;
            }
            return record;
        }
        String key = authorizationCodeKey(code);
        String raw = stringRedisTemplate.opsForValue().get(key);
        stringRedisTemplate.delete(key);
        if (StringUtils.isBlank(raw)) {
            return null;
        }
        try {
            return objectMapper.readValue(raw, AuthorizationCodeRecord.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private String authorizationCodeKey(String code) {
        return AUTHORIZATION_CODE_REDIS_KEY_PREFIX + code;
    }

    private Set<String> splitValues(String raw) {
        Set<String> values = new LinkedHashSet<>();
        if (StringUtils.isBlank(raw)) {
            return values;
        }
        for (String line : raw.split("\\R")) {
            for (String value : line.split("[,\\s]+")) {
                String normalized = StringUtils.trimToNull(value);
                if (normalized != null) {
                    values.add(normalized);
                }
            }
        }
        return values;
    }

    private boolean containsToken(Set<String> values, String token) {
        return values.stream().anyMatch(token::equals);
    }

    private List<String> scopeAuthorities(Set<String> scopes) {
        List<String> authorities = new ArrayList<>();
        if (scopes.contains("mcp.tools.read")) {
            authorities.add("mcp:tool:list");
        }
        if (scopes.contains("mcp.tools.call")) {
            authorities.add("mcp:tool:call");
        }
        return authorities;
    }

    private long resolveTtl(Long configuredTtlSeconds) {
        if (configuredTtlSeconds == null || configuredTtlSeconds <= 0) {
            return DEFAULT_ACCESS_TOKEN_TTL_SECONDS;
        }
        return Math.min(configuredTtlSeconds, MAX_ACCESS_TOKEN_TTL_SECONDS);
    }

    private String tokenEndpointAuthMethod(AuthOAuthClient client) {
        if (StringUtils.isNotBlank(client.getTokenEndpointAuthMethod())) {
            return client.getTokenEndpointAuthMethod();
        }
        if (CLIENT_TYPE_PUBLIC.equalsIgnoreCase(client.getClientType())) {
            return TOKEN_AUTH_METHOD_NONE;
        }
        return TOKEN_AUTH_METHOD_CLIENT_SECRET_BASIC;
    }

    private String serviceAccountSubject(String clientId) {
        return "client:" + clientId;
    }

    private String tokenIssuer() {
        return StringUtils.defaultIfBlank(normalizeBaseUrl(properties.externalBaseUrl()), JwtUtils.ISSUER);
    }

    private String normalizeBaseUrl(String baseUrl) {
        return StringUtils.removeEnd(StringUtils.defaultIfBlank(baseUrl, ""), "/");
    }

    private String endpoint(String issuer, String path) {
        return UriComponentsBuilder.fromUriString(issuer).path(path).build().toUriString();
    }

    private String hiddenAuthorizeInputs(MultiValueMap<String, String> params) {
        List<String> names = List.of("response_type", "client_id", "redirect_uri", "scope", "state", "code_challenge", "code_challenge_method", "resource");
        return names.stream()
                .map(name -> hiddenInput(name, params.getFirst(name)))
                .filter(Objects::nonNull)
                .reduce("", String::concat);
    }

    private String hiddenInput(String name, String value) {
        if (value == null) {
            return null;
        }
        return "<input type=\"hidden\" name=\"" + html(name) + "\" value=\"" + html(value) + "\">\n";
    }

    private String html(String value) {
        return HtmlUtils.htmlEscape(StringUtils.defaultString(value));
    }

    private AuthOAuthTokenException oauthError(String error, String description, HttpStatus status) {
        return new AuthOAuthTokenException(error, description, status);
    }

    private record ClientCredentials(String clientId, String clientSecret) {
    }

    private record AuthorizationRequest(
            String clientId,
            String redirectUri,
            String scope,
            String resource,
            String state,
            String codeChallenge,
            String codeChallengeMethod
    ) {
    }

    private record AuthorizationCodeRecord(
            String clientId,
            String userId,
            String username,
            String tenantId,
            String redirectUri,
            String scope,
            String resource,
            String codeChallenge,
            String codeChallengeMethod,
            long expireEpochSeconds
    ) {
    }
}
