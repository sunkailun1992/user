package com.kellen.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kellen.auth.entity.AuthTenant;
import com.kellen.auth.entity.bo.AuthTenantBO;
import com.kellen.auth.entity.query.AuthTenantQuery;
import com.kellen.auth.entity.vo.AuthTenantVO;
import com.kellen.auth.service.AuthTenantService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 租户维护请求层测试。
 * <p>
 * 从 HTTP 请求入口验证租户管理接口的参数传递、权限控制和统一响应结构。
 *
 * @author sunkailun
 * @className AuthTenantControllerTest
 * @time 2026/06/08
 */
@WebMvcTest(AuthTenantController.class)
@Import({SecurityAuthConfig.class, GlobalExceptionHandler.class})
@EnableConfigurationProperties(TenantProperties.class)
@ContextConfiguration(classes = {AuthTenantController.class, SecurityAuthConfig.class, GlobalExceptionHandler.class, AuthTenantControllerTest.TestApplication.class})
class AuthTenantControllerTest {

    /**
     * MockMvc 请求执行器。
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * JSON 序列化工具。
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 租户业务服务 Mock。
     */
    @MockitoBean
    private AuthTenantService authTenantService;

    /**
     * 请求层测试最小启动配置。
     */
    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class TestApplication {
    }

    /**
     * 查询租户选项时应返回轻量列表并传递查询参数。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     * @DateTime 2026/06/08
     * @email 376253703@qq.com
     */
    @Test
    @WithMockUser(authorities = "user:auth:manage")
    void shouldReturnTenantOptionsWhenRequestIsValid() throws Exception {
        AuthTenantVO tenantVO = new AuthTenantVO(); // 创建租户响应对象，模拟 Service 层返回。
        tenantVO.setId("100"); // 设置租户ID，验证响应数据会透传给前端。
        tenantVO.setName("演示租户"); // 设置租户名称，验证响应数据会透传给前端。
        when(authTenantService.list(any(AuthTenantQuery.class))).thenReturn(List.of(tenantVO)); // Mock 租户选项查询结果。

        mockMvc.perform(get("/auth/manage/tenants/options")
                        .param("query", "演示"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value("100"))
                .andExpect(jsonPath("$.data[0].name").value("演示租户"));

        ArgumentCaptor<AuthTenantQuery> captor = ArgumentCaptor.forClass(AuthTenantQuery.class); // 捕获 Controller 传给 Service 的查询对象。
        verify(authTenantService).list(captor.capture()); // 验证 Controller 调用了租户选项查询服务。
        assertThat(captor.getValue().getQuery()).isEqualTo("演示"); // 验证模糊查询关键字从请求参数传递到业务层。
    }

    /**
     * 分页查询租户时应返回分页结构并传递分页参数。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     * @DateTime 2026/06/08
     * @email 376253703@qq.com
     */
    @Test
    @WithMockUser(authorities = "user:auth:manage")
    @SuppressWarnings("unchecked")
    void shouldReturnTenantPageWhenRequestIsValid() throws Exception {
        Page<AuthTenantVO> responsePage = new Page<>(1, 10, 1); // 创建租户分页响应对象。
        AuthTenantVO tenantVO = new AuthTenantVO(); // 创建租户响应记录。
        tenantVO.setId("100"); // 设置租户ID，验证分页记录返回。
        responsePage.setRecords(List.of(tenantVO)); // 设置分页记录集合。
        when(authTenantService.page(any(Page.class), any(AuthTenantQuery.class))).thenReturn(responsePage); // Mock 分页查询结果。

        mockMvc.perform(get("/auth/manage/tenants")
                        .param("current", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.records[0].id").value("100"));

        ArgumentCaptor<AuthTenantQuery> captor = ArgumentCaptor.forClass(AuthTenantQuery.class); // 捕获分页查询参数。
        verify(authTenantService).page(any(Page.class), captor.capture()); // 验证分页服务被调用。
        assertThat(captor.getValue().getCurrent()).isEqualTo(1L); // 验证当前页参数传递正确。
        assertThat(captor.getValue().getSize()).isEqualTo(10L); // 验证每页数量参数传递正确。
    }

    /**
     * 新增租户时应传递请求体参数。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     * @DateTime 2026/06/08
     * @email 376253703@qq.com
     */
    @Test
    @WithMockUser(authorities = "user:auth:manage")
    void shouldSaveTenantWhenRequestIsValid() throws Exception {
        when(authTenantService.save(any(AuthTenantBO.class))).thenReturn("100"); // Mock 新增租户成功返回租户ID。
        AuthTenantBO request = new AuthTenantBO(); // 创建新增租户请求体。
        request.setCode("tenant_demo"); // 设置租户编码，满足新增校验。
        request.setName("演示租户"); // 设置租户名称，满足新增校验。

        mockMvc.perform(post("/auth/manage/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("100"));

        ArgumentCaptor<AuthTenantBO> captor = ArgumentCaptor.forClass(AuthTenantBO.class); // 捕获新增租户请求体。
        verify(authTenantService).save(captor.capture()); // 验证新增租户服务被调用。
        assertThat(captor.getValue().getCode()).isEqualTo("tenant_demo"); // 验证租户编码传递正确。
        assertThat(captor.getValue().getName()).isEqualTo("演示租户"); // 验证租户名称传递正确。
    }

    /**
     * 权限不足时应禁止查询租户选项。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     * @DateTime 2026/06/08
     * @email 376253703@qq.com
     */
    @Test
    @WithMockUser(authorities = "user:auth:resources")
    void shouldForbidTenantOptionsWhenUserHasNoManageAuthority() throws Exception {
        mockMvc.perform(get("/auth/manage/tenants/options"))
                .andExpect(status().isForbidden());

        verifyNoInteractions(authTenantService); // 权限不足时不进入租户业务服务。
    }
}
