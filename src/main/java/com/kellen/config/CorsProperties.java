package com.kellen.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理后台跨域配置属性。
 *
 * @author sunkailun
 * @className CorsProperties
 * @time 2026/05/27
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {

    /**
     * 允许访问后端的前端源，默认支持本地 Ant Design Pro 开发端口。
     */
    private List<String> allowedOrigins = new ArrayList<>(List.of("http://localhost:8000", "http://127.0.0.1:8000"));

    /**
     * 允许的请求方法。
     */
    private List<String> allowedMethods = new ArrayList<>(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

    /**
     * 允许的请求头。
     */
    private List<String> allowedHeaders = new ArrayList<>(List.of("*"));

    /**
     * 暴露给浏览器读取的响应头。
     */
    private List<String> exposedHeaders = new ArrayList<>(List.of("Authorization"));

    /**
     * 是否允许携带凭证。
     */
    private Boolean allowCredentials = Boolean.TRUE;

    /**
     * 预检请求缓存秒数。
     */
    private Long maxAge = 3600L;
}
