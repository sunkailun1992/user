package com.gb.user.service;

import com.gb.account.entity.bo.UserBO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 认证流程 服务类接口
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
public interface CertificationProcessService {

    /**
     * 经纪人认证成功的处理流程
     * @param httpServletRequest
     * @param isUpdateUserInfo 是否需要更新用户信息 【true：一定会更新，false：一定不会更新，null：根据所传的newState进行判断，是否要更新】
     * @param userBO 请求参数
     * @param oldState 老的经纪人认证状态【0：待认证，1：认证成功，2：认证失败，3：认证关闭】
     * @param newState 新的经纪人认证状态【0：待认证，1：认证成功，2：认证失败，3：认证关闭】
     */
    void certificationSuccessProcess(HttpServletRequest httpServletRequest, Boolean isUpdateUserInfo, UserBO userBO, Integer oldState, Integer newState);
}
