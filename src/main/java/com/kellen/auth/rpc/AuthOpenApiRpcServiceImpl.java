package com.kellen.auth.rpc;

import com.kellen.auth.dto.OpenApiSignatureVerifyRequest;
import com.kellen.auth.service.AuthThirdPartyAuthenticationService;
import com.kellen.rpc.auth.AuthOpenApiRpcService;
import com.kellen.rpc.auth.OpenApiSignatureVerifyRpcRequest;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 统一开放接口认证 Dubbo RPC 服务实现。
 */
@DubboService
public class AuthOpenApiRpcServiceImpl implements AuthOpenApiRpcService {

    private final AuthThirdPartyAuthenticationService authThirdPartyAuthenticationService;

    public AuthOpenApiRpcServiceImpl(AuthThirdPartyAuthenticationService authThirdPartyAuthenticationService) {
        this.authThirdPartyAuthenticationService = authThirdPartyAuthenticationService;
    }

    @Override
    public void verifyOpenApiSignature(OpenApiSignatureVerifyRpcRequest request) {
        authThirdPartyAuthenticationService.verifyOpenApiSignature(toServiceRequest(request));
    }

    private OpenApiSignatureVerifyRequest toServiceRequest(OpenApiSignatureVerifyRpcRequest request) {
        OpenApiSignatureVerifyRequest serviceRequest = new OpenApiSignatureVerifyRequest();
        if (request == null) {
            return serviceRequest;
        }
        serviceRequest.setProviderCode(request.getProviderCode());
        serviceRequest.setClientId(request.getClientId());
        serviceRequest.setTimestamp(request.getTimestamp());
        serviceRequest.setNonce(request.getNonce());
        serviceRequest.setSignature(request.getSignature());
        serviceRequest.setBody(request.getBody());
        return serviceRequest;
    }
}
