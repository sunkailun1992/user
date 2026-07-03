package com.kellen.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kellen.auth.config.OAuthAuthorizationServerProperties;
import com.kellen.auth.dto.LoginRequest;
import com.kellen.auth.dto.OAuthAuthorizeUser;
import com.kellen.auth.dto.OAuthTokenResponse;
import com.kellen.auth.entity.AuthOAuthClient;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.mapper.AuthOAuthClientMapper;
import com.kellen.auth.service.impl.AuthOAuthTokenException;
import com.kellen.auth.service.impl.AuthOAuthTokenServiceImpl;
import com.kellen.utils.auth.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthOAuthTokenServiceTest {

    private AuthOAuthClientMapper authOAuthClientMapper;
    private AuthAuthenticationService authAuthenticationService;
    private AuthOAuthTokenServiceImpl authOAuthTokenService;

    @BeforeEach
    void setUp() {
        authOAuthClientMapper = mock(AuthOAuthClientMapper.class);
        authAuthenticationService = mock(AuthAuthenticationService.class);
        @SuppressWarnings("unchecked")
        ObjectProvider<StringRedisTemplate> redisProvider = mock(ObjectProvider.class);
        when(redisProvider.getIfAvailable()).thenReturn(null);
        authOAuthTokenService = new AuthOAuthTokenServiceImpl(
                authOAuthClientMapper,
                new OAuthAuthorizationServerProperties("http://localhost:7100"),
                authAuthenticationService,
                redisProvider
        );
    }

    @Test
    void issueClientCredentialsTokenEmbedsMcpClaims() {
        when(authOAuthClientMapper.selectOne(ArgumentMatchers.<LambdaQueryWrapper<AuthOAuthClient>>any()))
                .thenReturn(client());

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("scope", "mcp.tools.read mcp.tools.call");
        form.add("resource", "http://localhost:7100/api/mcp");
        OAuthTokenResponse response = authOAuthTokenService.issueClientCredentialsToken(
                basic("mcp-client", "client-secret"),
                form
        );

        Claims claims = JwtUtils.parseJwt(response.accessToken());
        assertThat(response.tokenType()).isEqualTo("Bearer");
        assertThat(response.expiresIn()).isEqualTo(900L);
        assertThat(response.scope()).isEqualTo("mcp.tools.read mcp.tools.call");
        assertThat(claims.getSubject()).isEqualTo("client:mcp-client");
        assertThat(claims.get("client_id", String.class)).isEqualTo("mcp-client");
        assertThat(claims.getIssuer()).isEqualTo("http://localhost:7100");
        assertThat(claims.get("scope", String.class)).isEqualTo("mcp.tools.read mcp.tools.call");
        assertThat(claims.get("aud", String.class)).isEqualTo("http://localhost:7100/api/mcp");
        assertThat(claims.get("permissions").toString()).contains("mcp:tool:list", "mcp:tool:call");
    }

    @Test
    void issueClientCredentialsTokenRejectsUnauthorizedScope() {
        when(authOAuthClientMapper.selectOne(ArgumentMatchers.<LambdaQueryWrapper<AuthOAuthClient>>any()))
                .thenReturn(client());

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("scope", "mcp.admin");
        form.add("resource", "http://localhost:7100/api/mcp");

        assertThatThrownBy(() -> authOAuthTokenService.issueClientCredentialsToken(
                basic("mcp-client", "client-secret"),
                form
        )).isInstanceOf(AuthOAuthTokenException.class)
                .hasMessageContaining("scope");
    }

    @Test
    void issueClientCredentialsTokenCapsTtlAtThirtyDays() {
        AuthOAuthClient client = client();
        client.setAccessTokenTtlSeconds(60L * 24L * 60L * 60L);
        when(authOAuthClientMapper.selectOne(ArgumentMatchers.<LambdaQueryWrapper<AuthOAuthClient>>any()))
                .thenReturn(client);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("scope", "mcp.tools.read");
        form.add("resource", "http://localhost:7100/api/mcp");
        OAuthTokenResponse response = authOAuthTokenService.issueClientCredentialsToken(
                basic("mcp-client", "client-secret"),
                form
        );

        assertThat(response.expiresIn()).isEqualTo(30L * 24L * 60L * 60L);
    }

    @Test
    void authorizationServerMetadataContainsAuthorizationEndpoint() {
        Map<String, Object> metadata = authOAuthTokenService.authorizationServerMetadata("http://localhost:7100");

        assertThat(metadata.get("issuer")).isEqualTo("http://localhost:7100");
        assertThat(metadata.get("authorization_endpoint")).isEqualTo("http://localhost:7100/oauth2/authorize");
        assertThat(metadata.get("token_endpoint")).isEqualTo("http://localhost:7100/oauth2/token");
        assertThat(metadata.get("grant_types_supported").toString()).contains("authorization_code", "client_credentials");
        assertThat(metadata.get("code_challenge_methods_supported").toString()).contains("S256");
    }

    @Test
    void authorizationCodeFlowIssuesMcpTokenWithPkce() throws Exception {
        AuthOAuthClient client = publicClient();
        when(authOAuthClientMapper.selectOne(ArgumentMatchers.<LambdaQueryWrapper<AuthOAuthClient>>any()))
                .thenReturn(client);
        when(authAuthenticationService.authenticateForOAuth(any(LoginRequest.class)))
                .thenReturn(new OAuthAuthorizeUser("user-1", "admin", "100"));

        String verifier = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String challenge = codeChallenge(verifier);
        MultiValueMap<String, String> authorizeForm = new LinkedMultiValueMap<>();
        authorizeForm.add("response_type", "code");
        authorizeForm.add("client_id", "cherry-studio-local");
        authorizeForm.add("redirect_uri", "http://127.0.0.1:49152/callback");
        authorizeForm.add("scope", "mcp.tools.read");
        authorizeForm.add("resource", "http://localhost:7100/api/mcp");
        authorizeForm.add("state", "state-1");
        authorizeForm.add("code_challenge", challenge);
        authorizeForm.add("code_challenge_method", "S256");
        authorizeForm.add("tenantId", "100");
        authorizeForm.add("username", "admin");
        authorizeForm.add("password", "password");

        URI redirect = authOAuthTokenService.authorize(authorizeForm);
        String code = redirect.getQuery().replaceAll("(^|.*&)code=([^&]+).*", "$2");

        MultiValueMap<String, String> tokenForm = new LinkedMultiValueMap<>();
        tokenForm.add("grant_type", "authorization_code");
        tokenForm.add("client_id", "cherry-studio-local");
        tokenForm.add("code", code);
        tokenForm.add("redirect_uri", "http://127.0.0.1:49152/callback");
        tokenForm.add("code_verifier", verifier);
        OAuthTokenResponse response = authOAuthTokenService.issueToken(null, tokenForm);

        Claims claims = JwtUtils.parseJwt(response.accessToken());
        assertThat(response.scope()).isEqualTo("mcp.tools.read");
        assertThat(claims.getSubject()).isEqualTo("user-1");
        assertThat(claims.get("client_id", String.class)).isEqualTo("cherry-studio-local");
        assertThat(claims.get("grant_type", String.class)).isEqualTo("authorization_code");
        assertThat(claims.get("aud", String.class)).isEqualTo("http://localhost:7100/api/mcp");
    }

    private AuthOAuthClient client() {
        AuthOAuthClient client = new AuthOAuthClient();
        client.setClientId("mcp-client");
        client.setClientSecret(new BCryptPasswordEncoder().encode("client-secret"));
        client.setName("MCP Client");
        client.setClientType("confidential");
        client.setTokenEndpointAuthMethod("client_secret_basic");
        client.setGrantTypes("client_credentials");
        client.setScopes("mcp.tools.read mcp.tools.call");
        client.setAudiences("http://localhost:7100/api/mcp");
        client.setAccessTokenTtlSeconds(900L);
        client.setTenantId("1");
        client.setState(AuthStateEnum.启用);
        return client;
    }

    private AuthOAuthClient publicClient() {
        AuthOAuthClient client = new AuthOAuthClient();
        client.setClientId("cherry-studio-local");
        client.setName("Cherry Studio Local");
        client.setClientType("public");
        client.setTokenEndpointAuthMethod("none");
        client.setGrantTypes("authorization_code");
        client.setRedirectUris("http://127.0.0.1/callback http://localhost/callback");
        client.setScopes("mcp.tools.read mcp.tools.call");
        client.setAudiences("http://localhost:7100/api/mcp");
        client.setAccessTokenTtlSeconds(900L);
        client.setTenantId("100");
        client.setState(AuthStateEnum.启用);
        return client;
    }

    private String basic(String clientId, String clientSecret) {
        String raw = clientId + ":" + clientSecret;
        return "Basic " + Base64.getEncoder().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }

    private String codeChallenge(String verifier) throws Exception {
        byte[] digest = MessageDigest.getInstance("SHA-256").digest(verifier.getBytes(StandardCharsets.US_ASCII));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }
}
