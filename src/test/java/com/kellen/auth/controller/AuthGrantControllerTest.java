package com.kellen.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kellen.auth.entity.bo.AuthRoleResourceSyncBO;
import com.kellen.auth.service.AuthGrantService;
import com.kellen.bean.GlobalExceptionHandler;
import com.kellen.security.config.SecurityAuthConfig;
import com.kellen.security.config.TenantProperties;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.kellen.auth.controller.MockMvcSecurityUsers.authority;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 授权关系维护请求层测试。
 *
 * @author sunkailun
 * @className AuthGrantControllerTest
 * @time 2026/05/27
 */
@WebMvcTest(AuthGrantController.class)
@Import({SecurityAuthConfig.class, GlobalExceptionHandler.class})
@EnableConfigurationProperties(TenantProperties.class)
@ContextConfiguration(classes = {AuthGrantController.class, SecurityAuthConfig.class, GlobalExceptionHandler.class, AuthGrantControllerTest.TestApplication.class})
class AuthGrantControllerTest {

    /**
     * MockMvc 请求执行器。
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * JSON 序列化工具。
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 授权关系业务服务 Mock。
     */
    @MockitoBean
    private AuthGrantService authGrantService;

    /**
     * 请求层测试最小启动配置。
     */
    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class TestApplication {
    }

    /**
     * 查询角色资源时应返回已绑定资源ID列表。
     *
     * @throws Exception MockMvc 请求执行异常
     */
    @Test
    void shouldReturnRoleResourceIdsWhenRequestIsValid() throws Exception {
        // Mock 角色已经绑定两个资源，用于验证前端编辑弹窗可回显历史勾选。
        when(authGrantService.listRoleResourceIds("100", "role-1")).thenReturn(List.of("resource-1", "resource-2"));

        // 发起角色资源查询请求，并验证统一响应和资源ID列表。
        mockMvc.perform(get("/auth/manage/roles/role-1/resources")
                        .param("tenantId", "100")
                        .with(authority("user:auth:manage"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0]").value("resource-1"))
                .andExpect(jsonPath("$.data[1]").value("resource-2"));
        // 验证 Controller 将租户和角色传递给业务服务。
        verify(authGrantService).listRoleResourceIds("100", "role-1");
    }

    /**
     * 同步角色资源时应传递完整资源ID列表。
     *
     * @throws Exception MockMvc 请求执行异常
     */
    @Test
    void shouldSyncRoleResourcesWhenRequestIsValid() throws Exception {
        // Mock 同步成功，避免请求层测试依赖真实数据库。
        when(authGrantService.syncRoleResources(any(AuthRoleResourceSyncBO.class))).thenReturn(true);
        // 创建同步授权请求，resourceIds 表示保存时的完整勾选结果。
        AuthRoleResourceSyncBO request = new AuthRoleResourceSyncBO();
        // 设置租户ID。
        request.setTenantId("100");
        // 设置角色ID。
        request.setRoleId("role-1");
        // 设置完整资源ID列表。
        request.setResourceIds(List.of("resource-1", "resource-3"));

        // 发起角色资源同步请求，并验证统一响应。
        mockMvc.perform(put("/auth/manage/roles/role-1/resources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(authority("user:auth:manage"))
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
        // 捕获同步请求，验证资源ID列表没有在 Controller 层丢失。
        ArgumentCaptor<AuthRoleResourceSyncBO> captor = ArgumentCaptor.forClass(AuthRoleResourceSyncBO.class);
        verify(authGrantService).syncRoleResources(captor.capture());
        assertThat(captor.getValue().getTenantId()).isEqualTo("100");
        assertThat(captor.getValue().getRoleId()).isEqualTo("role-1");
        assertThat(captor.getValue().getResourceIds()).containsExactly("resource-1", "resource-3");
    }

    /**
     * 权限不足时应禁止查询角色资源。
     *
     * @throws Exception MockMvc 请求执行异常
     */
    @Test
    void shouldForbidRoleResourceRequestWhenUserHasNoManageAuthority() throws Exception {
        // 发起无管理权限的角色资源查询请求，并验证类级权限拦截。
        mockMvc.perform(get("/auth/manage/roles/role-1/resources")
                        .param("tenantId", "100")
                        .with(authority("user:auth:resources"))
                )
                .andExpect(status().isForbidden());
        // 权限不足时不进入业务服务。
        verifyNoInteractions(authGrantService);
    }
}
