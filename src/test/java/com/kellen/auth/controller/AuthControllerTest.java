package com.kellen.auth.controller;

import com.kellen.auth.entity.vo.AuthTenantVO;
import com.kellen.auth.service.AuthAuthenticationService;
import com.kellen.auth.service.AuthTenantService;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @MockBean
    private AuthAuthenticationService authAuthenticationService;

    /**
     * 租户业务服务。
     */
    @MockBean
    private AuthTenantService authTenantService;

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
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
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
}
