package com.kellen.auth.service;

import com.kellen.auth.dto.LoginRequest;
import com.kellen.auth.entity.vo.AuthCurrentResourceVO;
import com.kellen.auth.entity.vo.AuthLoginVO;

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
     * 查询当前登录用户资源。
     *
     * @return 当前登录用户资源
     */
    AuthCurrentResourceVO currentResources();
}
