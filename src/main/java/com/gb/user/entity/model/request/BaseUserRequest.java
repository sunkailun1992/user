package com.gb.user.entity.model.request;

import lombok.Data;

/**
 * <p>
 * 请求工保通类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Data
public abstract class BaseUserRequest {
    /**
     * 业务明细
     */
    private String businessDetails;
    private String sourceValueCode;
    private String sourceValueId;
    private String sourceValueName;
    private String sourceCode;
    private String sourceId;
    private String sourceName;
    private String appCode;
    private String token;
    private String userId;
    private String ipAddress;
    /**
     * 平台名称
     */
    private String platformName;
    private String platformCode;

}
