package com.kellen.auth.mq.producer;

import com.kellen.auth.entity.AuthUser;
import com.kellen.rpc.user.event.UserAccountCreatedEvent;
import com.kellen.utils.mq.MqSendOptions;
import com.kellen.utils.mq.MqTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * 账号已创建事件的生产者：事务提交后才发，消息 key 是事件 ID，事件字段来自新账号。
 */
class UserAccountEventProducerTest {

    private static final String TOPIC = "github-dev-user-account-created";

    @Test
    void usesAfterCommitWithEventIdAsKey() {
        MqTemplate mqTemplate = mock(MqTemplate.class);
        UserAccountEventProducer producer = new UserAccountEventProducer(mqTemplate, TOPIC);
        AuthUser user = new AuthUser();
        user.setId("u-1");
        user.setUsername("alice");
        user.setNickname("Alice");

        producer.publishAccountCreated(user, "tenant-1");

        ArgumentCaptor<Object> payload = ArgumentCaptor.forClass(Object.class);
        ArgumentCaptor<MqSendOptions> options = ArgumentCaptor.forClass(MqSendOptions.class);
        verify(mqTemplate).sendAfterCommit(eq(TOPIC), payload.capture(), options.capture());
        assertThat(payload.getValue()).isInstanceOf(UserAccountCreatedEvent.class);
        UserAccountCreatedEvent event = (UserAccountCreatedEvent) payload.getValue();
        assertThat(event.getEventId()).isNotBlank();
        assertThat(options.getValue().keys()).containsExactly(event.getEventId());
        assertThat(event.getOccurredAt()).isNotNull();
        assertThat(event.getTenantId()).isEqualTo("tenant-1");
        assertThat(event.getUserId()).isEqualTo("u-1");
        assertThat(event.getUsername()).isEqualTo("alice");
        assertThat(event.getNickname()).isEqualTo("Alice");
    }
}
