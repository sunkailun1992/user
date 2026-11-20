package com.kellen.auth.controller;

import com.kellen.auth.entity.vo.AuthCurrentResourceVO;
import com.kellen.auth.entity.vo.AuthTenantVO;
import com.kellen.auth.service.AuthCurrentUserService;
import com.kellen.auth.support.ResourceServerTestConfiguration;
import com.kellen.bean.GlobalExceptionHandler;
import com.kellen.security.config.TenantProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.kellen.auth.support.MockMvcSecurityUsers.authority;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 当前用户请求层测试：经资源服务器链访问当前用户的资源与可切换租户，未登录 401、缺权限 403。
 *
 * @author sunkailun
 * @className AuthControllerTest
 * @time 2026/05/27
 */
@WebMvcTest(AuthController.class)
@Import({ResourceServerTestConfiguration.class, GlobalExceptionHandler.class})
@EnableConfigurationProperties(TenantProperties.class)
@ContextConfiguration(classes = {AuthController.class, ResourceServerTestConfiguration.class, GlobalExceptionHandler.class, AuthControllerTest.TestApplication.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthCurrentUserService authCurrentUserService;

    /**
     * 请求层测试最小启动配置。
     */
    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class TestApplication {
    }

    @Test
    void currentResourcesReturnsCurrentUsersResources() throws Exception {
        AuthCurrentResourceVO resources = new AuthCurrentResourceVO();
        resources.setUserId("u1");
        resources.setTenantId("100");
        resources.setPermissions(List.of("user:auth:resources"));
        when(authCurrentUserService.currentResources()).thenReturn(resources);

        mockMvc.perform(get("/auth/current/resources").with(authority("user:auth:resources")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.userId").value("u1"))
                .andExpect(jsonPath("$.data.tenantId").value("100"))
                .andExpect(jsonPath("$.data.permissions[0]").value("user:auth:resources"));
    }

    @Test
    void currentTenantsReturnsSwitchableTenants() throws Exception {
        AuthTenantVO tenant = new AuthTenantVO();
        tenant.setId("100");
        tenant.setName("默认租户");
        when(authCurrentUserService.currentTenants()).thenReturn(List.of(tenant));

        mockMvc.perform(get("/auth/current/tenants").with(authority("user:auth:resources")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value("100"))
                .andExpect(jsonPath("$.data[0].name").value("默认租户"));
    }

    @Test
    void currentResourcesRequiresResourcesAuthority() throws Exception {
        mockMvc.perform(get("/auth/current/resources").with(authority("user:auth:manage")))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value("A0301"));
        verifyNoInteractions(authCurrentUserService);
    }

    @Test
    void unauthenticatedRequestIsRejectedWith401() throws Exception {
        mockMvc.perform(get("/auth/current/tenants"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value("A0220"));
        verifyNoInteractions(authCurrentUserService);
    }
}
