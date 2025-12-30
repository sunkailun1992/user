package com.gb.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-04-21 11:06:07
 * @description:	mq
 * @source:  	    代码生成器
 */
@Component
@RabbitListener(queues = "test")
public class TextReceiver {

    @RabbitHandler
    public void process(String json) throws Exception {
        System.out.printf(json);
    }

}