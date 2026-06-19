package com.kellen.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kellen.auth.entity.AuthRole;
import com.kellen.auth.entity.bo.AuthRoleBO;
import com.kellen.auth.entity.query.AuthRoleQuery;
import com.kellen.auth.entity.vo.AuthRoleVO;
import com.kellen.auth.service.AuthRoleService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 角色维护请求层测试。
 * <p>
 * 从 HTTP 请求入口验证角色管理接口的参数传递、权限控制和统一响应结构。
 *
 * @author sunkailun
 * @className AuthRoleControllerTest
 * @time 2026/06/08
 */
@WebMvcTest(AuthRoleController.class)
@Import({SecurityAuthConfig.class, GlobalExceptionHandler.class})
@EnableConfigurationProperties(TenantProperties.class)
@ContextConfiguration(classes = {AuthRoleController.class, SecurityAuthConfig.class, GlobalExceptionHandler.class, AuthRoleControllerTest.TestApplication.class})
class AuthRoleControllerTest {

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
     * 角色业务服务 Mock。
     */
    @MockitoBean
    private AuthRoleService authRoleService;

    /**
     * 请求层测试最小启动配置。
     */
    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class TestApplication {
    }

    /**
     * 查询角色选项时应返回轻量列表并传递查询参数。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     * @DateTime 2026/06/08
     * @email 376253703@qq.com
     */
    @Test
    void shouldReturnRoleOptionsWhenRequestIsValid() throws Exception {
        AuthRoleVO roleVO = new AuthRoleVO(); // 创建角色响应对象，模拟 Service 层返回。
        roleVO.setId("role-1"); // 设置角色ID，验证响应数据会透传给前端。
        roleVO.setName("租户管理员"); // 设置角色名称，验证响应数据会透传给前端。
        when(authRoleService.list(any(AuthRoleQuery.class))).thenReturn(List.of(roleVO)); // Mock 角色选项查询结果。

        mockMvc.perform(get("/auth/manage/roles/options")
                        .param("tenantId", "100")
                        .param("query", "管理员")
                        .with(authority("user:auth:manage")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value("role-1"))
                .andExpect(jsonPath("$.data[0].name").value("租户管理员"));

        ArgumentCaptor<AuthRoleQuery> captor = ArgumentCaptor.forClass(AuthRoleQuery.class); // 捕获 Controller 传给 Service 的查询对象。
        verify(authRoleService).list(captor.capture()); // 验证 Controller 调用了角色选项查询服务。
        assertThat(captor.getValue().getTenantId()).isEqualTo("100"); // 验证租户ID从请求参数传递到业务层。
        assertThat(captor.getValue().getQuery()).isEqualTo("管理员"); // 验证模糊查询关键字从请求参数传递到业务层。
    }

    /**
     * 分页查询角色时应返回分页结构并传递分页参数。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     * @DateTime 2026/06/08
     * @email 376253703@qq.com
     */
    @Test
    @SuppressWarnings("unchecked")
    void shouldReturnRolePageWhenRequestIsValid() throws Exception {
        Page<AuthRoleVO> responsePage = new Page<>(1, 10, 1); // 创建角色分页响应对象。
        AuthRoleVO roleVO = new AuthRoleVO(); // 创建角色响应记录。
        roleVO.setId("role-1"); // 设置角色ID，验证分页记录返回。
        responsePage.setRecords(List.of(roleVO)); // 设置分页记录集合。
        when(authRoleService.page(any(Page.class), any(AuthRoleQuery.class))).thenReturn(responsePage); // Mock 分页查询结果。

        mockMvc.perform(get("/auth/manage/roles")
                        .param("tenantId", "100")
                        .param("current", "1")
                        .param("size", "10")
                        .with(authority("user:auth:manage")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.records[0].id").value("role-1"));

        ArgumentCaptor<AuthRoleQuery> captor = ArgumentCaptor.forClass(AuthRoleQuery.class); // 捕获分页查询参数。
        verify(authRoleService).page(any(Page.class), captor.capture()); // 验证分页服务被调用。
        assertThat(captor.getValue().getCurrent()).isEqualTo(1L); // 验证当前页参数传递正确。
        assertThat(captor.getValue().getSize()).isEqualTo(10L); // 验证每页数量参数传递正确。
    }

    /**
     * 新增角色时应传递请求体参数。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     * @DateTime 2026/06/08
     * @email 376253703@qq.com
     */
    @Test
    void shouldSaveRoleWhenRequestIsValid() throws Exception {
        when(authRoleService.save(any(AuthRoleBO.class))).thenReturn("role-1"); // Mock 新增角色成功返回角色ID。
        AuthRoleBO request = new AuthRoleBO(); // 创建新增角色请求体。
        request.setTenantId("100"); // 设置租户ID，满足新增校验。
        request.setCode("tenant_admin"); // 设置角色编码，满足新增校验。
        request.setName("租户管理员"); // 设置角色名称，满足新增校验。

        mockMvc.perform(post("/auth/manage/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(authority("user:auth:manage"))
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("role-1"));

        ArgumentCaptor<AuthRoleBO> captor = ArgumentCaptor.forClass(AuthRoleBO.class); // 捕获新增角色请求体。
        verify(authRoleService).save(captor.capture()); // 验证新增角色服务被调用。
        assertThat(captor.getValue().getCode()).isEqualTo("tenant_admin"); // 验证角色编码传递正确。
        assertThat(captor.getValue().getName()).isEqualTo("租户管理员"); // 验证角色名称传递正确。
    }

    /**
     * 权限不足时应禁止查询角色选项。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     * @DateTime 2026/06/08
     * @email 376253703@qq.com
     */
    @Test
    void shouldForbidRoleOptionsWhenUserHasNoManageAuthority() throws Exception {
        mockMvc.perform(get("/auth/manage/roles/options")
                        .param("tenantId", "100")
                        .with(authority("user:auth:resources")))
                .andExpect(status().isForbidden());

        verifyNoInteractions(authRoleService); // 权限不足时不进入角色业务服务。
    }
}
