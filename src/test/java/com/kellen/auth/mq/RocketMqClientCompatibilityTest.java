package com.kellen.auth.mq;

import com.kellen.config.mq.MqAutoConfiguration;
import com.kellen.utils.mq.MqTemplate;
import org.apache.rocketmq.common.MQVersion;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * user 运行时的 RocketMQ 客户端必须与 NAS 上的服务端同一版本线（5.5.1）。
 *
 * <p>Spring Cloud Alibaba 的 BOM 会把客户端管成 5.3.1，而 utils 排除了只发到 5.3.2 的 rocketmq-acl；
 * 5.3.1 里没有 ACL 签名类，配了账号的生产者建不起来，user 启动失败。</p>
 */
class RocketMqClientCompatibilityTest {

    @Test
    void aclProducerStartsOnServerLineClient() throws ClassNotFoundException {
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(RocketMQAutoConfiguration.class, MqAutoConfiguration.class))
                .withPropertyValues("rocketmq.name-server=127.0.0.1:9876",
                        "rocketmq.producer.group=github-dev-user-producer",
                        "rocketmq.producer.access-key=github-dev-user",
                        "rocketmq.producer.secret-key=synthetic-not-a-secret")
                .run(context -> {
                    assertThat(context).hasNotFailed();
                    assertThat(context).hasSingleBean(MqTemplate.class);
                });

        assertThat(MQVersion.getVersionDesc(MQVersion.CURRENT_VERSION)).isEqualTo("V5_5_1");
        assertThat(Class.forName("org.apache.rocketmq.acl.common.AclClientRPCHook")
                .getProtectionDomain().getCodeSource().getLocation().toString()).contains("rocketmq-client-5.5.1");
    }
}
