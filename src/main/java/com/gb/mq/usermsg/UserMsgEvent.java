package com.gb.mq.usermsg;

import com.gb.user.entity.UserMessage;
import com.gb.user.enums.UserMsgTypeEnum;
import lombok.Data;

/**
 * @Author: wgs
 * @Date 2021/4/26 16:18
 * @Classname RefundNoticeEvent
 * @Description 用户消息
 */
@Data
public class UserMsgEvent {

    /**
     * 消息内容
     */
    private String content;
    /**
     * 用户id(username)
     */
    private String userId;

    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 消息类型
     */
    private Integer userType;

    /**
     * 产生时间
     */
    private String createDate;

    public UserMessage buildMsg() {
        UserMessage userMessage = new UserMessage();
        userMessage.setUserId(userId);
        userMessage.setName(UserMsgTypeEnum.getAppCodeEnum(msgType));
        userMessage.setContent(content);
        userMessage.setJumpAddress("baidu.com");
        userMessage.setReading(Boolean.FALSE);
        userMessage.setUserType(userType);
         return userMessage;
    }
}
