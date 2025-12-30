package com.gb.mq.insurance;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: sunx
 * @Date 2021/4/26 16:18
 * @Classname UpdateInsuranceAssistantEvent
 * @Description 更新投保用户业务助理Event
 */
@Data
@Accessors(chain = true)
public class UpdateInsuranceAssistantEvent {

    /**
     * 业务助理ID
     */
    private String assistantUserId;

    /**
     * 业务助理姓名
     */
    private String assistantUserName;

    /**
     * 业务助理手机号
     */
    private String assistantUserMobile;

    /**
     * 成员用户ID
     */
    private String teamUserId;

    /**
     * 成员用户姓名
     */
    private String teamUserName;

    /**
     * 成员用户手机号
     */
    private String teamUserMobile;
}
