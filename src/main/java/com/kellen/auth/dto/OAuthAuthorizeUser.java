package com.kellen.auth.dto;

/**
 * OAuth 授权码流程中的资源所有者。
 *
 * @param userId   用户ID
 * @param username 用户名
 * @param tenantId 租户ID
 */
public record OAuthAuthorizeUser(
        String userId,
        String username,
        String tenantId
) {
}
