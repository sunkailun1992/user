package com.kellen.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 管理后台跨域过滤器配置。
 *
 * @author sunkailun
 * @className CorsConfig
 * @time 2026/05/27
 */
@Configuration
@EnableConfigurationProperties(CorsProperties.class)
public class CorsConfig {

    /**
     * 注册最高优先级 CORS 过滤器，保证浏览器 OPTIONS 预检先于 Spring Security 执行。
     *
     * @param corsProperties 跨域配置属性
     * @return CORS 过滤器注册对象
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean(CorsProperties corsProperties) {
        CorsConfiguration configuration = new CorsConfiguration(); // 创建 Spring Web 标准 CORS 配置对象。
        configuration.setAllowedOriginPatterns(corsProperties.getAllowedOrigins()); // 使用 pattern 形式兼容 localhost 和后续域名配置。
        configuration.setAllowedMethods(corsProperties.getAllowedMethods()); // 放行 REST 接口和浏览器预检 OPTIONS。
        configuration.setAllowedHeaders(corsProperties.getAllowedHeaders()); // 放行 Authorization 等业务请求头。
        configuration.setExposedHeaders(corsProperties.getExposedHeaders()); // 允许前端读取后端暴露的认证相关响应头。
        configuration.setAllowCredentials(corsProperties.getAllowCredentials()); // 保持与浏览器凭证模式兼容。
        configuration.setMaxAge(corsProperties.getMaxAge()); // 减少重复预检请求。

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // 创建基于路径的 CORS 配置源。
        source.registerCorsConfiguration("/**", configuration); // 对当前服务所有接口统一生效，避免各 Controller 分散配置。

        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>(new CorsFilter(source)); // 注册 Servlet 过滤器。
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 确保预检请求先通过 CORS，再进入安全过滤链。
        return registrationBean; // 返回过滤器注册对象。
    }
}
