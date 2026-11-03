package com.kellen.auth.controller;

import com.kellen.auth.dto.OpenApiSignatureVerifyRequest;
import com.kellen.auth.dto.LogoutSessionRequest;
import com.kellen.auth.dto.RefreshSessionRequest;
import com.kellen.auth.entity.vo.AuthLoginVO;
import com.kellen.auth.entity.vo.AuthTenantVO;
import com.kellen.auth.service.AuthAuthenticationService;
import com.kellen.auth.service.AuthTenantService;
import com.kellen.auth.service.AuthThirdPartyAuthenticationService;
import com.kellen.bean.GlobalExceptionHandler;
import com.kellen.security.config.SecurityAuthConfig;
import com.kellen.security.config.TenantProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 认证请求层测试。
 *
 * @author sunkailun
 * @className AuthControllerTest
 * @time 2026/05/27
 */
@WebMvcTest(AuthController.class)
@Import({SecurityAuthConfig.class, GlobalExceptionHandler.class})
@EnableConfigurationProperties(TenantProperties.class)
@ContextConfiguration(classes = {AuthController.class, SecurityAuthConfig.class, GlobalExceptionHandler.class, AuthControllerTest.TestApplication.class})
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    /**
     * HTTP请求测试入口。
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * 认证登录业务服务。
     */
    @MockitoBean
    private AuthAuthenticationService authAuthenticationService;

    /**
     * 租户业务服务。
     */
    @MockitoBean
    private AuthTenantService authTenantService;

    /**
     * 三方认证业务服务。
     */
    @MockitoBean
    private AuthThirdPartyAuthenticationService authThirdPartyAuthenticationService;

    /**
     * 请求层测试最小启动配置。
     */
    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class TestApplication {
    }

    /**
     * 登录前租户列表应公开返回租户展示数据。
     *
     * @throws Exception MockMvc请求异常
     * @author sunkailun
     */
    @Test
    @DisplayName("GET /auth/tenants returns public tenant list")
    void tenantsShouldReturnPublicTenantList() throws Exception {
        AuthTenantVO tenant = new AuthTenantVO(); // 创建租户响应对象，模拟Service层返回。
        tenant.setId("100"); // 设置租户ID，验证响应包含后端真实主键。
        tenant.setCode("default"); // 设置租户编码，供登录页提交tenantCode。
        tenant.setName("默认租户"); // 设置租户名称，供前端下拉展示。
        when(authTenantService.list(any())).thenReturn(List.of(tenant)); // Mock租户服务返回单条默认租户。

        mockMvc.perform(get("/auth/tenants")) // 从HTTP请求层调用公开租户接口。
                .andExpect(status().isOk()) // 未登录状态不应被安全过滤器拦截。
                .andExpect(jsonPath("$.success").value(true)) // 验证统一响应成功标记。
                .andExpect(jsonPath("$.data[0].id").value("100")) // 验证租户ID返回。
                .andExpect(jsonPath("$.data[0].code").value("default")) // 验证租户编码返回。
                .andExpect(jsonPath("$.data[0].name").value("默认租户")); // 验证租户名称返回。

        ArgumentCaptor<com.kellen.auth.entity.query.AuthTenantQuery> captor = ArgumentCaptor.forClass(com.kellen.auth.entity.query.AuthTenantQuery.class); // 捕获Controller传给Service的查询对象。
        verify(authTenantService).list(captor.capture()); // 验证Controller复用租户服务查询。
        assertThat(captor.getValue().getAssignment()).isTrue(); // 验证Controller要求结果增强，方便前端展示。
    }

    /**
     * 开放接口签名校验应委托 user 认证服务。
     *
     * @throws Exception MockMvc请求异常
     * @author sunkailun
     */
    @Test
    @DisplayName("POST /auth/open/signatures/verify delegates signature verification")
    void openApiSignatureVerifyShouldDelegateToService() throws Exception {
        mockMvc.perform(post("/auth/open/signatures/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "providerCode": "partner-system",
                                  "clientId": "partner-client",
                                  "timestamp": "1782460000000",
                                  "nonce": "nonce-1",
                                  "signature": "signature",
                                  "body": "{}"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));

        ArgumentCaptor<OpenApiSignatureVerifyRequest> captor = ArgumentCaptor.forClass(OpenApiSignatureVerifyRequest.class);
        verify(authThirdPartyAuthenticationService).verifyOpenApiSignature(captor.capture());
        assertThat(captor.getValue().getProviderCode()).isEqualTo("partner-system");
        assertThat(captor.getValue().getClientId()).isEqualTo("partner-client");
        assertThat(captor.getValue().getBody()).isEqualTo("{}");
    }

    /**
     * refresh token 应换取新的登录会话。
     *
     * @throws Exception MockMvc请求异常
     */
    @Test
    @DisplayName("POST /auth/sessions/refresh delegates refresh session")
    void refreshSessionShouldDelegateToService() throws Exception {
        AuthLoginVO login = new AuthLoginVO();
        login.setToken("access-token");
        login.setRefreshToken("refresh-token-new");
        login.setExpiresIn(86_400L);
        login.setRefreshExpiresIn(2_592_000L);
        when(authAuthenticationService.refreshSession(any())).thenReturn(login);

        mockMvc.perform(post("/auth/sessions/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "refreshToken": "refresh-token-old"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").value("access-token"))
                .andExpect(jsonPath("$.data.refreshToken").value("refresh-token-new"))
                .andExpect(jsonPath("$.data.expiresIn").value(86_400));

        ArgumentCaptor<RefreshSessionRequest> captor = ArgumentCaptor.forClass(RefreshSessionRequest.class);
        verify(authAuthenticationService).refreshSession(captor.capture());
        assertThat(captor.getValue().getRefreshToken()).isEqualTo("refresh-token-old");
    }

    /**
     * 退出登录应透传当前 access token 和可选 refresh token。
     *
     * @throws Exception MockMvc请求异常
     */
    @Test
    @WithMockUser
    @DisplayName("POST /auth/sessions/logout delegates logout session")
    void logoutShouldDelegateToService() throws Exception {
        mockMvc.perform(post("/auth/sessions/logout")
                        .header("Authorization", "Bearer access-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "refreshToken": "refresh-token"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        ArgumentCaptor<LogoutSessionRequest> captor = ArgumentCaptor.forClass(LogoutSessionRequest.class);
        verify(authAuthenticationService).logout(eq("Bearer access-token"), captor.capture());
        assertThat(captor.getValue().getRefreshToken()).isEqualTo("refresh-token");
    }
}
