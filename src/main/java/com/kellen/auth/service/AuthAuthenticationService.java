package com.kellen.auth.service;

import com.kellen.auth.dto.LoginRequest;
import com.kellen.auth.entity.vo.AuthCurrentResourceVO;
import com.kellen.auth.entity.vo.AuthLoginVO;
import com.kellen.auth.entity.vo.AuthTenantVO;

import java.util.List;

/**
 * 认证登录业务服务。
 *
 * @author sunkailun
 * @className AuthAuthenticationService
 * @time 2026/05/26
 */
public interface AuthAuthenticationService {

    /**
     * 用户登录。
     *
     * @param request 登录请求参数
     * @return 登录响应
     */
    AuthLoginVO login(LoginRequest request);

    /**
     * 基于已完成外部身份校验的本地用户创建会话。
     *
     * @param tenantId      本地租户 ID
     * @param userId        本地用户 ID
     * @param loginProvider 登录来源
     * @param subjectType   主体类型
     * @return 登录响应
     */
    AuthLoginVO createSessionForUser(String tenantId, String userId, String loginProvider, String subjectType);

    /**
     * 查询当前登录用户资源。
     *
     * @return 当前登录用户资源
     */
    AuthCurrentResourceVO currentResources();

    /**
     * 查询当前登录用户可切换租户。
     *
     * @return 当前登录用户可切换租户
     */
    List<AuthTenantVO> currentTenants();
}
