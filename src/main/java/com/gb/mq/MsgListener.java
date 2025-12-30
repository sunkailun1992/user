package com.gb.mq;

import com.gb.mq.usermsg.UserMsgEvent;
import com.gb.push.param.MessageParam;
import com.gb.push.param.SendRequest;
import com.gb.user.entity.UserMessage;
import com.gb.user.service.UserMessageService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.gb.bean.RabbitConfig.USER_MSG_INSURANCE;

/**
 * @author: ranyang
 * @Date: 2021/04/15 17:19
 * @descript: mq消息消费
 */

@Slf4j
@Component
@RabbitListener(queues = {USER_MSG_INSURANCE})
@Setter(onMethod_ = {@Autowired})
public class MsgListener {
    private UserMessageService userMessageService;


    @RabbitHandler
    public void consumeUserMsgEvent(UserMsgEvent event) {
        log.debug("收到mq消息 {}", event.toString());
        try {
            UserMessage userMessage = event.buildMsg();
            // 推送消息
            userMessageService.saveEnhance(userMessage);
        } catch (Exception e) {
            log.error(" 用户消息事件消费失败", e);
        }
    }

}