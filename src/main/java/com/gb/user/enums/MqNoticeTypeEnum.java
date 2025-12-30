package com.gb.user.enums;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * MQ通知类型枚举类
 * @author: ranyang
 * @Date: 2021/04/06 14:25
 * @descript:
 */

@AllArgsConstructor
@Getter
@Slf4j
public enum MqNoticeTypeEnum {

    /**
     * 注册：经纪人或者普通用户的时候通知
     */
    CRM_CLUE_MQ("registUserQ", "CRM线索") {
        @Override
        public void pushMqMessage(String logDesc, Object object) {
            RABBIT_TEMPLATE.convertAndSend(this.getCode(), object);
            log.debug("{}-发送MQ通知CRM完毕，MQ参数：{}", logDesc, JSON.toJSONString(object));
        }
    },
    /**
     * 绑定用户队列
     * 【注销和用户手机号修改的时候，通知CRM】
     */
    CRM_BIND_USER_MQ("bindUserQ", "绑定用户队列") {
        @Override
        public void pushMqMessage(String logDesc, Object object) {
            RABBIT_TEMPLATE.convertAndSend(this.getCode(), object);
            log.debug("{}-发送MQ通知CRM完毕，MQ参数：{}", logDesc, JSON.toJSONString(object));
        }
    },

    /**
     * 更新投保用户业务助理队列【修改团队人员的信息时候，通知INSURANCE服务】
     */
    UPDATE_INSURANCE_ASSISTANT_MQ("updateInsuranceAssistantQ","更新投保用户业务助理队列") {
        @Override
        public void pushMqMessage(String logDesc, Object object) {
        RABBIT_TEMPLATE.convertAndSend(this.getCode(), object);
        log.debug("{}-发送MQ通知INSURANCE完毕，MQ参数：{}", logDesc, JSON.toJSONString(object));
        }
    };

    /**
     * 码值
     */
    private String code;
    /**
     * 描述
     */
    private String desc;

    private static final RabbitTemplate RABBIT_TEMPLATE = SpringUtil.getBean(RabbitTemplate.class);

    /**
     * 推送MQ消息的方法
     * @param logDesc 消息描述
     * @param object 消息参数
     */
    public abstract void pushMqMessage(String logDesc, Object object);
}
