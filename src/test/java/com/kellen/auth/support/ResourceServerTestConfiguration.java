package com.kellen.auth.support;

import com.kellen.security.oauth2.FleetResourceServerAutoConfiguration;
import com.kellen.security.oauth2.SecurityContextRpcService;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtDecoder;

/**
 * 请求层测试的资源服务器：加载 utils 的资源服务器自动配置（过滤链、方法级权限、统一 401/403/503 响应），与线上同一条链；
 * 令牌解码器换成一律拒绝的占位，测试用 {@link MockMvcSecurityUsers} 直接注入认证结果（Spring Security 测试的
 * {@code authentication()} 写法），不解码令牌，也不连接签发方。签发方地址见测试 {@code application.yml}。
 *
 * @author 孙凯伦
 */
@TestConfiguration(proxyBeanMethods = false)
@ImportAutoConfiguration(FleetResourceServerAutoConfiguration.class)
public class ResourceServerTestConfiguration {

    /**
     * 业务上下文提供方占位：资源服务器要求有提供方（utils 2.0.8 起没有就启动失败）；请求层测试直接注入认证结果，
     * 令牌一律被上面的解码器拒绝，走不到这里，走到就按 user 服务不可用（503）暴露出来。
     *
     * @return 提供方
     */
    @Bean
    SecurityContextRpcService securityContextRpcService() {
        return (userId, requestedTenantId) -> {
            throw new IllegalStateException("请求层测试直接注入认证结果，不加载业务上下文");
        };
    }

    /**
     * 不解码令牌的解码器：带 Bearer 头的请求一律 401。错误说明会进 {@code WWW-Authenticate} 响应头，只用 ASCII。
     *
     * @return 解码器
     */
    @Bean
    JwtDecoder jwtDecoder() {
        return token -> {
            throw new BadJwtException("Test decoder rejects every token");
        };
    }
}
