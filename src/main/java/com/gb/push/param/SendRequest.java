package com.gb.push.param;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: wgs
 * @Date 2022/1/20 11:20
 * @Classname SendRequest
 * @Description
 */
@Data
@Builder
public class SendRequest {
    /**
     * 执行业务类型
     */
    private String code;

    /**
     * 设备标识
     */
    private String cid;

    /**
     * 别名
     */
    private String alias;

    /**
     * token类型
     * 0:ios，1:Android
     */
    private Integer tokenType;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 消息ID
     */
    private String msgId;

    /**
     * 消息相关的参数
     */
    private MessageParam messageParam;
}
