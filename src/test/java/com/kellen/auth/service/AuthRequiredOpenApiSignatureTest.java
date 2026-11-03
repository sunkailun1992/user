package com.kellen.auth.service;

import com.kellen.auth.dto.OpenApiSignatureVerifyRequest;
import com.kellen.auth.entity.AuthExternalClient;
import com.kellen.auth.entity.AuthExternalSignatureNonce;
import com.kellen.auth.mapper.AuthExternalClientMapper;
import com.kellen.auth.mapper.AuthExternalIdentityMapper;
import com.kellen.auth.mapper.AuthExternalSignatureNonceMapper;
import com.kellen.auth.service.impl.AuthThirdPartyAuthenticationServiceImpl;
import com.kellen.auth.rpc.AuthOpenApiRpcServiceImpl;
import com.kellen.rpc.auth.OpenApiSignatureVerifyRpcRequest;
import com.kellen.utils.exception.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HexFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/** 使用真实业务实现与独立 HMAC 计算，不能把 mock RPC 的无异常当成验签证明。 */
class AuthRequiredOpenApiSignatureTest {
    private final AuthExternalClientMapper clients = mock(AuthExternalClientMapper.class);
    private final AuthExternalSignatureNonceMapper nonces = mock(AuthExternalSignatureNonceMapper.class);
    private final AuthThirdPartyAuthenticationServiceImpl service = new AuthThirdPartyAuthenticationServiceImpl(
            clients, mock(AuthExternalIdentityMapper.class), nonces, mock(AuthAuthenticationService.class));
    private final AuthExternalClient client = new AuthExternalClient();

    @BeforeEach
    void clientFixture() {
        client.setProviderCode("synthetic-provider");
        client.setClientId("synthetic-client");
        client.setClientSecret("synthetic-test-only-signing-secret");
        client.setSignatureRequired(true);
        client.setAllowedClockSkewSeconds(60);
        when(clients.selectOne(any())).thenReturn(client);
        when(nonces.insert(any(AuthExternalSignatureNonce.class))).thenReturn(1);
    }

    @Test
    void strictRpcReturnsTrueOnlyAfterRealHmacAndNonceRegistration() throws Exception {
        OpenApiSignatureVerifyRequest signed = signedRequest(Instant.now().toEpochMilli());
        var rpc = new OpenApiSignatureVerifyRpcRequest();
        rpc.setProviderCode(signed.getProviderCode());
        rpc.setClientId(signed.getClientId());
        rpc.setTimestamp(signed.getTimestamp());
        rpc.setNonce(signed.getNonce());
        rpc.setSignature(signed.getSignature());
        rpc.setBody(signed.getBody());
        assertThat(new AuthOpenApiRpcServiceImpl(service).verifyRequiredOpenApiSignature(rpc)).isTrue();
        verify(nonces).insert(argThat((AuthExternalSignatureNonce nonce) ->
                nonce.getClientId().equals(rpc.getClientId()) && nonce.getNonce().equals(rpc.getNonce())));
    }

    @Test
    void disabledOrMissingSignatureRequirementCannotAuthenticateEvenWithAValidSignature() throws Exception {
        var signed = signedRequest(Instant.now().toEpochMilli());
        client.setSignatureRequired(false);
        assertThatThrownBy(() -> service.verifyRequiredOpenApiSignature(signed)).isInstanceOf(UserException.class);
        // 旧调用方的可选签名语义保留，严格消费者只能调用新入口。
        service.verifyOpenApiSignature(signed);
        client.setSignatureRequired(null);
        assertThatThrownBy(() -> service.verifyRequiredOpenApiSignature(signed)).isInstanceOf(UserException.class);
        verifyNoInteractions(nonces);
    }

    @Test
    void forgedBodySignatureExpiredAndExtremeTimestampsAreRejectedBeforeNonceWrite() throws Exception {
        var forged = signedRequest(Instant.now().toEpochMilli());
        forged.setBody("{\"message\":\"tampered\"}");
        assertThatThrownBy(() -> service.verifyRequiredOpenApiSignature(forged)).isInstanceOf(UserException.class);
        var expired = signedRequest(Instant.now().minusSeconds(120).toEpochMilli());
        assertThatThrownBy(() -> service.verifyRequiredOpenApiSignature(expired)).isInstanceOf(UserException.class);
        var extreme = signedRequest(Long.MIN_VALUE);
        assertThatThrownBy(() -> service.verifyRequiredOpenApiSignature(extreme)).isInstanceOf(UserException.class);
        verifyNoInteractions(nonces);
    }

    @Test
    void duplicateNonceOrUnrecordedNonceNeverReturnsSuccess() throws Exception {
        var signed = signedRequest(Instant.now().toEpochMilli());
        when(nonces.insert(any(AuthExternalSignatureNonce.class)))
                .thenReturn(1).thenThrow(new DuplicateKeyException("synthetic replay"));
        assertThat(service.verifyRequiredOpenApiSignature(signed)).isTrue();
        assertThatThrownBy(() -> service.verifyRequiredOpenApiSignature(signed)).isInstanceOf(UserException.class);
        when(nonces.insert(any(AuthExternalSignatureNonce.class))).thenReturn(0);
        assertThatThrownBy(() -> service.verifyRequiredOpenApiSignature(signed)).isInstanceOf(UserException.class);
    }

    private OpenApiSignatureVerifyRequest signedRequest(long timestamp) throws Exception {
        var request = new OpenApiSignatureVerifyRequest();
        request.setProviderCode(client.getProviderCode());
        request.setClientId(client.getClientId());
        request.setTimestamp(Long.toString(timestamp));
        request.setNonce("synthetic-nonce");
        request.setBody("{\"message\":\"synthetic\"}");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(client.getClientSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        request.setSignature(HexFormat.of().formatHex(mac.doFinal(String.join("\n", request.getClientId(),
                request.getTimestamp(), request.getNonce(), request.getBody()).getBytes(StandardCharsets.UTF_8))));
        return request;
    }
}
