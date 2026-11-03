package com.kellen.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kellen.auth.entity.AuthUser;
import com.kellen.auth.entity.bo.AuthUserBO;
import com.kellen.auth.entity.query.AuthUserQuery;
import com.kellen.auth.entity.vo.AuthUserVO;
import com.kellen.auth.service.AuthUserService;
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
 * 用户维护请求层测试。
 * <p>
 * 从 HTTP 请求入口验证用户管理接口的参数传递、权限控制和统一响应结构。
 *
 * @author sunkailun
 * @className AuthUserControllerTest
 * @time 2026/06/08
 */
@WebMvcTest(AuthUserController.class)
@Import({SecurityAuthConfig.class, GlobalExceptionHandler.class})
@EnableConfigurationProperties(TenantProperties.class)
@ContextConfiguration(classes = {AuthUserController.class, SecurityAuthConfig.class, GlobalExceptionHandler.class, AuthUserControllerTest.TestApplication.class})
class AuthUserControllerTest {

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
     * 用户业务服务 Mock。
     */
    @MockitoBean
    private AuthUserService authUserService;

    /**
     * 请求层测试最小启动配置。
     */
    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class TestApplication {
    }

    /**
     * 查询用户选项时应返回轻量列表并传递查询参数。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     */
    @Test
    void shouldReturnUserOptionsWhenRequestIsValid() throws Exception {
        AuthUserVO userVO = new AuthUserVO(); // 创建用户响应对象，模拟 Service 层返回。
        userVO.setId("user-1"); // 设置用户ID，验证响应数据会透传给前端。
        userVO.setUsername("admin"); // 设置用户名，验证响应数据会透传给前端。
        when(authUserService.list(any(AuthUserQuery.class))).thenReturn(List.of(userVO)); // Mock 用户选项查询结果。

        mockMvc.perform(get("/auth/manage/users/options")
                        .param("tenantId", "100")
                        .param("query", "admin")
                        .with(authority("user:auth:manage")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value("user-1"))
                .andExpect(jsonPath("$.data[0].username").value("admin"));

        ArgumentCaptor<AuthUserQuery> captor = ArgumentCaptor.forClass(AuthUserQuery.class); // 捕获 Controller 传给 Service 的查询对象。
        verify(authUserService).list(captor.capture()); // 验证 Controller 调用了用户选项查询服务。
        assertThat(captor.getValue().getTenantId()).isEqualTo("100"); // 验证租户ID从请求参数传递到业务层。
        assertThat(captor.getValue().getQuery()).isEqualTo("admin"); // 验证模糊查询关键字从请求参数传递到业务层。
    }

    /**
     * 分页查询用户时应返回分页结构并传递分页参数。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     */
    @Test
    @SuppressWarnings("unchecked")
    void shouldReturnUserPageWhenRequestIsValid() throws Exception {
        Page<AuthUserVO> responsePage = new Page<>(1, 10, 1); // 创建用户分页响应对象。
        AuthUserVO userVO = new AuthUserVO(); // 创建用户响应记录。
        userVO.setId("user-1"); // 设置用户ID，验证分页记录返回。
        responsePage.setRecords(List.of(userVO)); // 设置分页记录集合。
        when(authUserService.page(any(Page.class), any(AuthUserQuery.class))).thenReturn(responsePage); // Mock 分页查询结果。

        mockMvc.perform(get("/auth/manage/users")
                        .param("tenantId", "100")
                        .param("current", "1")
                        .param("size", "10")
                        .with(authority("user:auth:manage")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.records[0].id").value("user-1"));

        ArgumentCaptor<AuthUserQuery> captor = ArgumentCaptor.forClass(AuthUserQuery.class); // 捕获分页查询参数。
        verify(authUserService).page(any(Page.class), captor.capture()); // 验证分页服务被调用。
        assertThat(captor.getValue().getCurrent()).isEqualTo(1L); // 验证当前页参数传递正确。
        assertThat(captor.getValue().getSize()).isEqualTo(10L); // 验证每页数量参数传递正确。
    }

    /**
     * 新增用户时应传递请求体参数。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     */
    @Test
    void shouldSaveUserWhenRequestIsValid() throws Exception {
        when(authUserService.save(any(AuthUserBO.class))).thenReturn("user-1"); // Mock 新增用户成功返回用户ID。
        AuthUserBO request = new AuthUserBO(); // 创建新增用户请求体。
        request.setTenantId("100"); // 设置租户ID，满足新增校验。
        request.setUsername("admin"); // 设置用户名，满足新增校验。
        request.setPassword("123456"); // 设置密码，满足新增校验。
        request.setNickname("管理员"); // 设置昵称，验证普通字段传递。

        mockMvc.perform(post("/auth/manage/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(authority("user:auth:manage"))
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("user-1"));

        ArgumentCaptor<AuthUserBO> captor = ArgumentCaptor.forClass(AuthUserBO.class); // 捕获新增用户请求体。
        verify(authUserService).save(captor.capture()); // 验证新增用户服务被调用。
        assertThat(captor.getValue().getUsername()).isEqualTo("admin"); // 验证用户名传递正确。
        assertThat(captor.getValue().getNickname()).isEqualTo("管理员"); // 验证昵称传递正确。
    }

    /**
     * 权限不足时应禁止查询用户选项。
     *
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     */
    @Test
    void shouldForbidUserOptionsWhenUserHasNoManageAuthority() throws Exception {
        mockMvc.perform(get("/auth/manage/users/options")
                        .param("tenantId", "100")
                        .with(authority("user:auth:resources")))
                .andExpect(status().isForbidden());

        verifyNoInteractions(authUserService); // 权限不足时不进入用户业务服务。
    }
}
