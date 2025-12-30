package com.gb.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 工保网User配置
 *
 * @author sunx
 * @DateTime 2021/3/17  11:04 上午
 * @email 376253703@qq.com
 * @phone 13777579028
 * @explain
 */
@Configuration
@RefreshScope
@Data
public class GongBaoUserConfig {
    /**
     * 后台权限账户
     */
    @Value("${gongbao.bac_p_a}")
    public String bacPerAccount;
}
