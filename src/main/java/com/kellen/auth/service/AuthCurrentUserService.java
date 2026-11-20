package com.kellen.auth.service;

import com.kellen.auth.entity.vo.AuthCurrentResourceVO;
import com.kellen.auth.entity.vo.AuthTenantVO;

import java.util.List;

/**
 * 当前登录用户的资源与可切换租户（身份由 Keycloak 访问令牌证明，业务上下文由资源服务器按请求加载）。
 *
 * @author 孙凯伦
 */
public interface AuthCurrentUserService {

    /**
     * 当前用户在活动租户下的前端菜单、后端权限码与可切换租户。
     *
     * @return 当前用户资源
     */
    AuthCurrentResourceVO currentResources();

    /**
     * 当前用户可切换的租户。
     *
     * @return 租户
     */
    List<AuthTenantVO> currentTenants();
}
