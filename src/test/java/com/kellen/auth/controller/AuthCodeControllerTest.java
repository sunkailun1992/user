package com.kellen.auth.controller;

import com.kellen.auth.entity.query.AuthCodeGenerateQuery;
import com.kellen.auth.service.AuthCodeGenerateService;
import com.kellen.bean.GlobalExceptionHandler;
import com.kellen.security.config.SecurityAuthConfig;
import com.kellen.security.config.TenantProperties;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 认证编码生成请求层测试。
 *
 * @author sunkailun
 * @className AuthCodeControllerTest
 * @time 2026/05/27
 */
@WebMvcTest(AuthCodeController.class)
@Import({SecurityAuthConfig.class, GlobalExceptionHandler.class})
@EnableConfigurationProperties(TenantProperties.class)
@ContextConfiguration(classes = {AuthCodeController.class, SecurityAuthConfig.class, GlobalExceptionHandler.class, AuthCodeControllerTest.TestApplication.class})
class AuthCodeControllerTest {

    /**
     * MockMvc 请求执行器。
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * 编码生成服务 Mock。
     */
    @MockBean
    private AuthCodeGenerateService authCodeGenerateService;

    /**
     * 请求层测试最小启动配置。
     */
    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class TestApplication {
    }

    /**
     * 有管理权限时应生成编码并传递查询参数。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @Test
    @WithMockUser(authorities = "user:auth:manage")
    void shouldGenerateCodeWhenUserHasManageAuthority() throws Exception {
        when(authCodeGenerateService.generate(any(AuthCodeGenerateQuery.class))).thenReturn("role_admin_20260527162000_0001"); // Mock 后端统一生成结果。

        mockMvc.perform(get("/auth/manage/codes/generate")
                        .param("target", "ROLE")
                        .param("tenantId", "100")
                        .param("name", "管理员"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("role_admin_20260527162000_0001"));

        ArgumentCaptor<AuthCodeGenerateQuery> captor = ArgumentCaptor.forClass(AuthCodeGenerateQuery.class); // 捕获请求参数对象。
        verify(authCodeGenerateService).generate(captor.capture()); // 验证 Controller 调用编码服务。
        assertThat(captor.getValue().getTarget()).isEqualTo("ROLE"); // 验证编码目标传递正确。
        assertThat(captor.getValue().getTenantId()).isEqualTo("100"); // 验证租户ID传递正确。
    }

    /**
     * 权限不足时应禁止生成编码。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @Test
    @WithMockUser(authorities = "user:auth:resources")
    void shouldForbidGenerateCodeWhenUserHasNoManageAuthority() throws Exception {
        mockMvc.perform(get("/auth/manage/codes/generate").param("target", "ROLE"))
                .andExpect(status().isForbidden());

        verifyNoInteractions(authCodeGenerateService); // 权限不足时不进入业务服务。
    }
}
