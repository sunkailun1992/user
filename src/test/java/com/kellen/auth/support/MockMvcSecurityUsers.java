package com.kellen.auth.support;

import com.kellen.security.SecurityUser;
import com.kellen.security.oauth2.FleetAuthenticationToken;
import com.kellen.security.oauth2.SecurityContextResult;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;

/**
 * MockMvc 安全测试用户工厂：注入资源服务器的认证结果（{@link FleetAuthenticationToken}），等同于访问令牌已校验、
 * user 已给出业务上下文。
 *
 * @author 孙凯伦
 */
public final class MockMvcSecurityUsers {

    /**
     * 测试用户的活动租户。
     */
    public static final String TENANT_ID = "100";

    private MockMvcSecurityUsers() {
    }

    /**
     * 活动租户为 {@link #TENANT_ID}、只有给定权限的用户。
     *
     * @param authorities 权限码或角色
     * @return 请求处理器
     */
    public static RequestPostProcessor authority(String... authorities) {
        return user(new SecurityUser("test-user", "test-user", TENANT_ID, List.of(authorities)));
    }

    /**
     * 给定业务上下文的用户。
     *
     * @param user 业务上下文
     * @return 请求处理器
     */
    public static RequestPostProcessor user(SecurityUser user) {
        Jwt jwt = Jwt.withTokenValue("test-access-token").header("alg", "RS256").subject("kc-" + user.getUserId())
                .claim("user_id", user.getUserId()).claim("azp", "github-gateway").build();
        List<GrantedAuthority> granted = user.getAuthorities().stream().<GrantedAuthority>map(SimpleGrantedAuthority::new).toList();
        return authentication(new FleetAuthenticationToken(jwt, SecurityContextResult.ok(user), granted, user.getUserId()));
    }
}
