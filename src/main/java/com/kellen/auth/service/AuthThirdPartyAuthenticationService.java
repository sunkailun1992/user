package com.kellen.auth.service;

import com.kellen.auth.dto.OpenApiSignatureVerifyRequest;
import com.kellen.auth.dto.ThirdPartySessionRequest;
import com.kellen.auth.entity.vo.AuthLoginVO;

/**
 * 三方认证业务服务。
 */
public interface AuthThirdPartyAuthenticationService {

    /**
     * 校验三方签名并创建统一登录会话。
     *
     * @param request 三方认证请求
     * @return 登录响应
     */
    AuthLoginVO createSession(ThirdPartySessionRequest request);

    /**
     * 校验开放接口签名。
     *
     * @param request 开放接口签名校验请求
     */
    void verifyOpenApiSignature(OpenApiSignatureVerifyRequest request);
}
