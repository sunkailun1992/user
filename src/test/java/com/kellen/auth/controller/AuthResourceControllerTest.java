package com.kellen.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.bo.AuthResourceBO;
import com.kellen.auth.entity.enums.AuthResourceCategoryEnum;
import com.kellen.auth.entity.query.AuthResourceQuery;
import com.kellen.auth.entity.vo.AuthResourceVO;
import com.kellen.auth.service.AuthResourceService;
import com.kellen.bean.GlobalExceptionHandler;
import com.kellen.security.config.SecurityAuthConfig;
import com.kellen.security.config.TenantProperties;
import com.kellen.utils.enumeration.ReturnCode;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

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
 * 权限资源请求层测试。
 * <p>
 * 从 HTTP 请求入口验证参数校验、权限控制、统一响应结构和 Controller 到 Service 的参数传递。
 *
 * @author sunkailun
 * @className AuthResourceControllerTest
 * @time 2026/05/27
 */
@WebMvcTest(AuthResourceController.class)
@Import({SecurityAuthConfig.class, GlobalExceptionHandler.class})
@EnableConfigurationProperties(TenantProperties.class)
@ContextConfiguration(classes = {AuthResourceController.class, SecurityAuthConfig.class, GlobalExceptionHandler.class, AuthResourceControllerTest.TestApplication.class})
class AuthResourceControllerTest {

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
     * 权限资源业务服务 Mock。
     */
    @MockBean
    private AuthResourceService authResourceService;

    /**
     * 请求层测试最小启动配置。
     */
    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class TestApplication {
    }

    /**
     * 新增权限资源请求成功时应返回统一成功响应并传递业务参数。
     *
     * @return void
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @Test
    @WithMockUser(authorities = "user:auth:manage")
    void shouldReturnSuccessWhenSaveResourceRequestIsValid() throws Exception {
        // Mock Service 新增成功后的资源ID，避免测试依赖真实数据库。
        when(authResourceService.save(any(AuthResourceBO.class))).thenReturn("resource-1");
        // 创建新增资源请求体，模拟前端提交后端接口权限。
        AuthResourceBO request = new AuthResourceBO();
        // 设置租户ID，验证多租户必填参数会传入业务层。
        request.setTenantId("100");
        // 设置权限编码，验证新增资源必填参数会传入业务层。
        request.setCode("user:auth:resources");
        // 设置资源名称，验证新增资源必填参数会传入业务层。
        request.setName("权限资源列表");
        // 设置资源分类，验证枚举请求参数可以完成 JSON 反序列化。
        request.setResourceCategory(AuthResourceCategoryEnum.BACKEND);
        // 设置接口路径，验证普通业务字段会传入业务层。
        request.setPath("/auth/current/resources");
        // 设置请求方法，验证普通业务字段会传入业务层。
        request.setMethod("GET");

        // 发起 HTTP 新增请求，并验证统一响应结构和业务数据。
        mockMvc.perform(post("/auth/manage/resources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(ReturnCode.成功.getState()))
                .andExpect(jsonPath("$.msg").value(ReturnCode.成功.getName()))
                .andExpect(jsonPath("$.data").value("resource-1"));
        // 捕获 Controller 传给 Service 的 BO，验证请求层没有丢字段。
        ArgumentCaptor<AuthResourceBO> captor = ArgumentCaptor.forClass(AuthResourceBO.class);
        // 验证新增服务被调用一次。
        verify(authResourceService).save(captor.capture());
        // 验证租户ID被正确传递。
        assertThat(captor.getValue().getTenantId()).isEqualTo("100");
        // 验证权限编码被正确传递。
        assertThat(captor.getValue().getCode()).isEqualTo("user:auth:resources");
        // 验证资源分类被正确传递。
        assertThat(captor.getValue().getResourceCategory()).isEqualTo(AuthResourceCategoryEnum.BACKEND);
    }

    /**
     * 缺少新增必填参数时应返回统一参数错误响应。
     *
     * @return void
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @Test
    @WithMockUser(authorities = "user:auth:manage")
    void shouldReturnParameterErrorWhenSaveResourceRequestIsInvalid() throws Exception {
        // 创建缺少 tenantId、code、name、resourceCategory 的非法请求体。
        AuthResourceBO request = new AuthResourceBO();

        // 发起 HTTP 新增请求，并验证全局异常处理器会返回统一参数错误响应。
        mockMvc.perform(post("/auth/manage/resources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value(ReturnCode.请求必填参数为空.getState()))
                .andExpect(jsonPath("$.msg").value(ReturnCode.请求必填参数为空.getName()))
                .andExpect(jsonPath("$.errorMessage").exists());
        // 验证参数校验失败时不会进入业务服务，避免写入脏数据。
        verifyNoInteractions(authResourceService);
    }

    /**
     * 权限不足时应拦截资源维护请求。
     *
     * @return void
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @Test
    @WithMockUser(authorities = "user:auth:resources")
    void shouldForbidSaveResourceWhenUserHasNoManageAuthority() throws Exception {
        // 创建合法请求体，用于确认本用例失败原因来自权限而不是参数校验。
        AuthResourceBO request = new AuthResourceBO();
        // 设置租户ID，满足新增校验。
        request.setTenantId("100");
        // 设置权限编码，满足新增校验。
        request.setCode("user:auth:resources");
        // 设置资源名称，满足新增校验。
        request.setName("权限资源列表");
        // 设置资源分类，满足新增校验。
        request.setResourceCategory(AuthResourceCategoryEnum.BACKEND);

        // 发起 HTTP 新增请求，并验证类级 @PreAuthorize 会拦截无管理权限用户。
        mockMvc.perform(post("/auth/manage/resources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
        // 验证权限不足时不会进入业务服务。
        verifyNoInteractions(authResourceService);
    }

    /**
     * 分页查询请求成功时应返回分页结构并传递分页参数。
     *
     * @return void
     * @throws Exception MockMvc 请求执行异常
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @Test
    @WithMockUser(authorities = "user:auth:manage")
    @SuppressWarnings("unchecked")
    void shouldReturnPageWhenPageResourceRequestIsValid() throws Exception {
        // 创建分页响应对象，模拟业务层已完成查询和 VO 转换。
        Page<AuthResourceVO> responsePage = new Page<>(1, 10, 1);
        // 创建权限资源响应记录。
        AuthResourceVO resourceVO = new AuthResourceVO();
        // 设置资源ID，用于验证分页数据会返回给前端。
        resourceVO.setId("resource-1");
        // 设置权限编码，用于验证分页数据会返回给前端。
        resourceVO.setCode("user:auth:resources");
        // 设置分页记录。
        responsePage.setRecords(java.util.List.of(resourceVO));
        // Mock Service 分页查询结果。
        when(authResourceService.page(any(Page.class), any(AuthResourceQuery.class))).thenReturn(responsePage);
        // 创建分页查询请求体。
        AuthResourceQuery request = new AuthResourceQuery();
        // 设置租户ID，验证分页查询租户边界必填。
        request.setTenantId("100");
        // 设置当前页，验证分页参数会传入业务层。
        request.setCurrent(1L);
        // 设置每页数量，验证分页参数会传入业务层。
        request.setSize(10L);

        // 发起 HTTP 分页请求，并验证统一响应和分页记录。
        mockMvc.perform(get("/auth/manage/resources")
                        .param("tenantId", request.getTenantId())
                        .param("current", String.valueOf(request.getCurrent()))
                        .param("size", String.valueOf(request.getSize())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.current").value(1))
                .andExpect(jsonPath("$.data.size").value(10))
                .andExpect(jsonPath("$.data.total").value(1))
                .andExpect(jsonPath("$.data.records[0].id").value("resource-1"))
                .andExpect(jsonPath("$.data.records[0].code").value("user:auth:resources"));
        // 捕获 Controller 创建的 MyBatis-Plus 分页对象。
        ArgumentCaptor<Page<AuthResource>> pageCaptor = ArgumentCaptor.forClass(Page.class);
        // 捕获 Controller 传给 Service 的查询参数。
        ArgumentCaptor<AuthResourceQuery> queryCaptor = ArgumentCaptor.forClass(AuthResourceQuery.class);
        // 验证分页服务被调用一次。
        verify(authResourceService).page(pageCaptor.capture(), queryCaptor.capture());
        // 验证当前页传递正确。
        assertThat(pageCaptor.getValue().getCurrent()).isEqualTo(1);
        // 验证每页数量传递正确。
        assertThat(pageCaptor.getValue().getSize()).isEqualTo(10);
        // 验证租户ID传递正确。
        assertThat(queryCaptor.getValue().getTenantId()).isEqualTo("100");
    }
}
