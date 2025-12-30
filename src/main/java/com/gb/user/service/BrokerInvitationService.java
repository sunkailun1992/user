package com.gb.user.service;

import com.gb.user.entity.bo.InviteLinkBO;
import com.gb.user.entity.vo.InviteLinkVO;

/**
 * <p>
 * 用户中心查询接口控制器接口
 * </p>
 *
 * @author sunx
 * @since 2021-03-15
 */
public interface BrokerInvitationService {

    /**
     * 查询用户邀请链接
     *
     * @param bo: 请求参数
     * @return InviteLinkVO
     * @author sunx
     * @since 2021-05-25
     */
    InviteLinkVO queryInviteLinkByUserId(InviteLinkBO bo);

    /**
     * 下载二维码
     *
     * @param bo: 请求参数
     * @return String
     * @author sunx
     * @since 2021-05-25
     */
    String downQrCode(InviteLinkBO bo);
}
