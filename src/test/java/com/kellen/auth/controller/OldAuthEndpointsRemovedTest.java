package com.kellen.auth.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 第5阶段起登录、令牌、OAuth 授权服务器、第三方会话与开放接口验签都交给 Keycloak：这些接口与实现已删除。
 * 逐个控制器读取请求映射（没有映射的路径由 Spring MVC 返回 404），并确认旧实现类不可加载。
 */
class OldAuthEndpointsRemovedTest {

    private static final List<String> REMOVED = List.of(
            "POST /auth/sessions", "POST /auth/sessions/refresh", "POST /auth/sessions/logout", "POST /auth/third-party/sessions",
            "POST /auth/open/signatures/verify", "POST /oauth2/token", "GET /oauth2/authorize", "POST /oauth2/authorize",
            "GET /.well-known/openid-configuration", "GET /.well-known/oauth-authorization-server", "GET /auth/manage/oauth-clients");

    @Test
    void oldEndpointsAreGone() throws Exception {
        List<String> mappings = mappings();
        assertThat(mappings).isNotEmpty().contains("GET /auth/current/resources", "GET /auth/current/tenants");
        assertThat(mappings).doesNotContainAnyElementsOf(REMOVED);
        assertThat(mappings).noneMatch(mapping -> mapping.contains("/oauth2/") || mapping.contains("/oauth-clients")
                || mapping.contains("/.well-known/"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "com.kellen.auth.service.AuthTokenLifecycleService",
            "com.kellen.auth.service.AuthAuthenticationService",
            "com.kellen.auth.service.AuthThirdPartyAuthenticationService",
            "com.kellen.auth.service.AuthOAuthTokenService",
            "com.kellen.auth.service.AuthOAuthClientService",
            "com.kellen.auth.config.OAuthAuthorizationServerProperties",
            "com.kellen.auth.rpc.AuthSessionRpcServiceImpl",
            "com.kellen.auth.rpc.AuthIdentityRpcServiceImpl",
            "com.kellen.auth.rpc.AuthOpenApiRpcServiceImpl",
            "com.kellen.auth.entity.AuthOAuthClient",
            "com.kellen.auth.entity.AuthExternalClient",
            "com.kellen.auth.entity.AuthExternalIdentity",
            "com.kellen.auth.entity.AuthExternalSignatureNonce"
    })
    void oldImplementationsAreGone(String className) {
        assertThatThrownBy(() -> Class.forName(className, false, getClass().getClassLoader())).isInstanceOf(ClassNotFoundException.class);
    }

    private static List<String> mappings() throws Exception {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        List<String> mappings = new ArrayList<>();
        for (BeanDefinition definition : scanner.findCandidateComponents("com.kellen")) {
            Class<?> type = Class.forName(definition.getBeanClassName());
            RequestMapping root = AnnotatedElementUtils.findMergedAnnotation(type, RequestMapping.class);
            List<String> prefixes = root == null || root.path().length == 0 ? List.of("") : List.of(root.path());
            for (Method method : type.getDeclaredMethods()) {
                RequestMapping mapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
                if (mapping == null) {
                    continue;
                }
                List<String> paths = mapping.path().length == 0 ? List.of("") : List.of(mapping.path());
                List<String> verbs = mapping.method().length == 0 ? List.of("GET", "POST", "PUT", "DELETE")
                        : List.of(mapping.method()).stream().map(Enum::name).toList();
                for (String prefix : prefixes) {
                    for (String path : paths) {
                        for (String verb : verbs) {
                            mappings.add(verb + " " + prefix + path);
                        }
                    }
                }
            }
        }
        return mappings;
    }
}
