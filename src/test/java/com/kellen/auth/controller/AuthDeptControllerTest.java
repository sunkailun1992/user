package com.kellen.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kellen.auth.entity.AuthDept;
import com.kellen.auth.entity.bo.AuthDeptBO;
import com.kellen.auth.entity.query.AuthDeptQuery;
import com.kellen.auth.entity.vo.AuthDeptVO;
import com.kellen.auth.service.AuthDeptService;
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
 * 部门维护请求层测试。
 * <p>
 * 从 HTTP 请求入口验证部门管理接口的参数传递、权限控制和统一响应结构。
 *
 * @author sunkailun
 * @className AuthDeptControllerTest
 * @time 2026/06/08
 */
@WebMvcTest(AuthDeptController.class)
@Import({SecurityAuthConfig.class, GlobalExceptionHandler.class})
@EnableConfigurationProperties(TenantProperties.class)
@ContextConfiguration(classes = {AuthDeptController.class, SecurityAuthConfig.class, GlobalExceptionHandler.class, AuthDeptControllerTest.TestApplication.class})
class AuthDeptControllerTest {

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
     * 部门业务服务 Mock。
     */
    @MockitoBean
    private AuthDeptService authDeptService;

    /**
     * 请求层测试最小启动配置。
     */
    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class TestApplication {
    }

    /**
     * 查询部门选项时应返回轻量列表并传递查询参数。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     */
    @Test
    void shouldReturnDeptOptionsWhenRequestIsValid() throws Exception {
        AuthDeptVO deptVO = new AuthDeptVO(); // 创建部门响应对象，模拟 Service 层返回。
        deptVO.setId("dept-1"); // 设置部门ID，验证响应数据会透传给前端。
        deptVO.setName("研发部"); // 设置部门名称，验证响应数据会透传给前端。
        when(authDeptService.list(any(AuthDeptQuery.class))).thenReturn(List.of(deptVO)); // Mock 部门选项查询结果。

        mockMvc.perform(get("/auth/manage/depts/options")
                        .param("tenantId", "100")
                        .param("query", "研发")
                        .with(authority("user:auth:manage")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value("dept-1"))
                .andExpect(jsonPath("$.data[0].name").value("研发部"));

        ArgumentCaptor<AuthDeptQuery> captor = ArgumentCaptor.forClass(AuthDeptQuery.class); // 捕获 Controller 传给 Service 的查询对象。
        verify(authDeptService).list(captor.capture()); // 验证 Controller 调用了部门选项查询服务。
        assertThat(captor.getValue().getTenantId()).isEqualTo("100"); // 验证租户ID从请求参数传递到业务层。
        assertThat(captor.getValue().getQuery()).isEqualTo("研发"); // 验证模糊查询关键字从请求参数传递到业务层。
    }

    /**
     * 分页查询部门时应返回分页结构并传递分页参数。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     */
    @Test
    @SuppressWarnings("unchecked")
    void shouldReturnDeptPageWhenRequestIsValid() throws Exception {
        Page<AuthDeptVO> responsePage = new Page<>(1, 10, 1); // 创建部门分页响应对象。
        AuthDeptVO deptVO = new AuthDeptVO(); // 创建部门响应记录。
        deptVO.setId("dept-1"); // 设置部门ID，验证分页记录返回。
        responsePage.setRecords(List.of(deptVO)); // 设置分页记录集合。
        when(authDeptService.page(any(Page.class), any(AuthDeptQuery.class))).thenReturn(responsePage); // Mock 分页查询结果。

        mockMvc.perform(get("/auth/manage/depts")
                        .param("tenantId", "100")
                        .param("current", "1")
                        .param("size", "10")
                        .with(authority("user:auth:manage")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.records[0].id").value("dept-1"));

        ArgumentCaptor<AuthDeptQuery> captor = ArgumentCaptor.forClass(AuthDeptQuery.class); // 捕获分页查询参数。
        verify(authDeptService).page(any(Page.class), captor.capture()); // 验证分页服务被调用。
        assertThat(captor.getValue().getCurrent()).isEqualTo(1L); // 验证当前页参数传递正确。
        assertThat(captor.getValue().getSize()).isEqualTo(10L); // 验证每页数量参数传递正确。
    }

    /**
     * 新增部门时应传递请求体参数。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     */
    @Test
    void shouldSaveDeptWhenRequestIsValid() throws Exception {
        when(authDeptService.save(any(AuthDeptBO.class))).thenReturn("dept-1"); // Mock 新增部门成功返回部门ID。
        AuthDeptBO request = new AuthDeptBO(); // 创建新增部门请求体。
        request.setTenantId("100"); // 设置租户ID，满足新增校验。
        request.setCode("dept_rd"); // 设置部门编码，满足新增校验。
        request.setName("研发部"); // 设置部门名称，满足新增校验。

        mockMvc.perform(post("/auth/manage/depts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(authority("user:auth:manage"))
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("dept-1"));

        ArgumentCaptor<AuthDeptBO> captor = ArgumentCaptor.forClass(AuthDeptBO.class); // 捕获新增部门请求体。
        verify(authDeptService).save(captor.capture()); // 验证新增部门服务被调用。
        assertThat(captor.getValue().getCode()).isEqualTo("dept_rd"); // 验证部门编码传递正确。
        assertThat(captor.getValue().getName()).isEqualTo("研发部"); // 验证部门名称传递正确。
    }

    /**
     * 权限不足时应禁止查询部门选项。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     */
    @Test
    void shouldForbidDeptOptionsWhenUserHasNoManageAuthority() throws Exception {
        mockMvc.perform(get("/auth/manage/depts/options")
                        .param("tenantId", "100")
                        .with(authority("user:auth:resources")))
                .andExpect(status().isForbidden());

        verifyNoInteractions(authDeptService); // 权限不足时不进入部门业务服务。
    }
}
