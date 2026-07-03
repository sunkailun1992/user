package com.kellen.auth.service;

import com.kellen.auth.dto.OAuthTokenResponse;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.Map;

/**
 * OAuth token 与 metadata 服务。
 */
public interface AuthOAuthTokenService {

    /**
     * 渲染 OAuth 授权页。
     *
     * @param params 授权请求参数
     * @param error  可选错误提示
     * @return HTML
     */
    String authorizationPage(MultiValueMap<String, String> params, String error);

    /**
     * 完成授权并生成跳转地址。
     *
     * @param form 授权表单
     * @return redirect URI
     */
    URI authorize(MultiValueMap<String, String> form);

    /**
     * 签发 access token。
     *
     * @param authorization Authorization 请求头，可携带 Basic client 凭据
     * @param form          token 请求表单
     * @return OAuth token 响应
     */
    OAuthTokenResponse issueToken(String authorization, MultiValueMap<String, String> form);

    /**
     * 签发 client_credentials access token。
     *
     * @param authorization Authorization 请求头，可携带 Basic client 凭据
     * @param form          token 请求表单
     * @return OAuth token 响应
     */
    OAuthTokenResponse issueClientCredentialsToken(String authorization, MultiValueMap<String, String> form);

    /**
     * OAuth 授权服务器 metadata。
     *
     * @param issuerBaseUrl 当前服务外部访问基准 URL
     * @return metadata
     */
    Map<String, Object> authorizationServerMetadata(String issuerBaseUrl);
}
