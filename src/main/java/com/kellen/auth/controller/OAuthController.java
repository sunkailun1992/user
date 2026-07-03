package com.kellen.auth.controller;

import com.kellen.auth.config.OAuthAuthorizationServerProperties;
import com.kellen.auth.dto.OAuthTokenResponse;
import com.kellen.auth.service.AuthOAuthTokenService;
import com.kellen.auth.service.impl.AuthOAuthTokenException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * OAuth2 授权服务器入口。
 */
@RestController
@Tag(name = "OAuth2 授权", description = "提供 MCP OAuth 授权码、client_credentials token 签发和授权服务器发现")
public class OAuthController {

    private final AuthOAuthTokenService authOAuthTokenService;
    private final OAuthAuthorizationServerProperties properties;

    public OAuthController(AuthOAuthTokenService authOAuthTokenService,
                           OAuthAuthorizationServerProperties properties) {
        this.authOAuthTokenService = authOAuthTokenService;
        this.properties = properties;
    }

    @PostMapping(value = "/oauth2/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Operation(summary = "签发 OAuth2 access token", description = "支持 authorization_code + PKCE 和 client_credentials")
    public ResponseEntity<?> token(@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authorization,
                                   @RequestParam MultiValueMap<String, String> form) {
        try {
            OAuthTokenResponse response = authOAuthTokenService.issueToken(authorization, form);
            return ResponseEntity.ok(response);
        } catch (AuthOAuthTokenException e) {
            return oauthError(e);
        }
    }

    @GetMapping(value = "/oauth2/authorize", produces = MediaType.TEXT_HTML_VALUE)
    @Operation(summary = "OAuth2 授权页", description = "渲染 authorization_code + PKCE 登录授权页")
    public ResponseEntity<String> authorizePage(@RequestParam MultiValueMap<String, String> params) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(authOAuthTokenService.authorizationPage(params, null));
        } catch (AuthOAuthTokenException e) {
            return oauthErrorPage(e);
        }
    }

    @PostMapping(value = "/oauth2/authorize", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Operation(summary = "OAuth2 授权确认", description = "校验资源所有者账号并回调 authorization code")
    public ResponseEntity<String> authorize(@RequestParam MultiValueMap<String, String> form) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(authOAuthTokenService.authorize(form))
                    .build();
        } catch (AuthOAuthTokenException e) {
            return authorizeFailedPage(form, e);
        } catch (RuntimeException e) {
            return authorizeFailedPage(form, new AuthOAuthTokenException("access_denied", e.getMessage(), HttpStatus.UNAUTHORIZED));
        }
    }

    @GetMapping({
            "/.well-known/oauth-authorization-server",
            "/.well-known/openid-configuration"
    })
    @Operation(summary = "OAuth2 授权服务器发现", description = "返回 token endpoint、grant type 和 scope 等标准 metadata")
    public Map<String, Object> authorizationServerMetadata(HttpServletRequest request) {
        return authOAuthTokenService.authorizationServerMetadata(baseUrl(request));
    }

    private String baseUrl(HttpServletRequest request) {
        if (properties.externalBaseUrl() != null && !properties.externalBaseUrl().isBlank()) {
            return removeTrailingSlash(properties.externalBaseUrl());
        }
        String forwardedProto = request.getHeader("X-Forwarded-Proto");
        String forwardedHost = request.getHeader("X-Forwarded-Host");
        if (forwardedHost != null && !forwardedHost.isBlank()) {
            String proto = forwardedProto == null || forwardedProto.isBlank() ? request.getScheme() : forwardedProto;
            return proto + "://" + forwardedHost;
        }
        return request.getScheme() + "://" + request.getServerName() + portPart(request.getScheme(), request.getServerPort());
    }

    private String portPart(String scheme, int port) {
        if (("http".equalsIgnoreCase(scheme) && port == 80) || ("https".equalsIgnoreCase(scheme) && port == 443)) {
            return "";
        }
        return ":" + port;
    }

    private String removeTrailingSlash(String value) {
        while (value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    private ResponseEntity<?> oauthError(AuthOAuthTokenException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", e.getError());
        body.put("error_description", e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(body);
    }

    private ResponseEntity<String> authorizeFailedPage(MultiValueMap<String, String> form, AuthOAuthTokenException e) {
        try {
            return ResponseEntity.status(e.getStatus())
                    .contentType(MediaType.TEXT_HTML)
                    .body(authOAuthTokenService.authorizationPage(form, e.getMessage()));
        } catch (AuthOAuthTokenException ignored) {
            return oauthErrorPage(e);
        }
    }

    private ResponseEntity<String> oauthErrorPage(AuthOAuthTokenException e) {
        String body = """
                <!doctype html>
                <html lang="zh-CN">
                <head><meta charset="utf-8"><title>OAuth Error</title></head>
                <body><h1>OAuth 请求失败</h1><p>%s</p><p>%s</p></body>
                </html>
                """.formatted(e.getError(), e.getMessage());
        return ResponseEntity.status(e.getStatus()).contentType(MediaType.TEXT_HTML).body(body);
    }
}
