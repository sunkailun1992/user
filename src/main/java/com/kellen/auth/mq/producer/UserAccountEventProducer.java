package com.kellen.auth.mq.producer;

import com.kellen.auth.entity.AuthUser;
import com.kellen.rpc.user.event.UserAccountCreatedEvent;
import com.kellen.utils.mq.MqSendOptions;
import com.kellen.utils.mq.MqTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

/**
 * “账号已创建”事件的生产者：新账号写入后在事务提交时经 RocketMQ 发出，message 据此发欢迎站内信。
 *
 * <p>事务回滚不发；提交后发送失败只记日志，不影响建账号。事件只带 ID 与称呼，不带口令或个人信息。</p>
 */
@Component
public class UserAccountEventProducer {

    /**
     * utils 的消息发送工具。
     */
    private final MqTemplate mqTemplate;

    /**
     * 账号已创建事件的主题，来自 Nacos 的 fleet.mq.topic.user-account-created。
     */
    private final String topic;

    /**
     * 创建生产者。
     *
     * @param mqTemplate 消息发送工具
     * @param topic      账号已创建事件的主题
     */
    public UserAccountEventProducer(MqTemplate mqTemplate,
                                    @Value("${fleet.mq.topic.user-account-created}") String topic) {
        this.mqTemplate = mqTemplate;
        this.topic = topic;
    }

    /**
     * 在当前事务提交后发“账号已创建”事件；消息 key 是事件 ID，便于在 dashboard 按 key 查。
     *
     * @param user     已插入、带用户 ID 的新账号
     * @param tenantId 新账号所属租户 ID
     */
    public void publishAccountCreated(AuthUser user, String tenantId) {
        UserAccountCreatedEvent event = new UserAccountCreatedEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setOccurredAt(Instant.now());
        event.setTenantId(tenantId);
        event.setUserId(user.getId());
        event.setUsername(user.getUsername());
        event.setNickname(user.getNickname());
        mqTemplate.sendAfterCommit(topic, event, MqSendOptions.none().withKeys(event.getEventId()));
    }
}
