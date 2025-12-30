package com.gb.push.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author: wgs
 * @Date 2022/1/20 09:29
 * @Classname PushConfig
 * @Description
 */
@Data
@ConfigurationProperties(prefix = "push.getui")
@Component
@RefreshScope
public class GetuiConfig {
    /**
     * 应用id
     */
    public String appId;
    /**
     * 应用key
     */
    public String appKey;
    /**
     * 应用秘钥
     */
    public String masterSecret;

    /**
     * true：验证是否绑定别名
     */
    public boolean aliasFlag;
}
