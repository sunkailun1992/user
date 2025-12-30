package com.gb.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.user.entity.UserAgentCertification;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 * 用户经纪人认证 服务类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
public interface UserAgentCertificationService extends IService<UserAgentCertification> {


    /**
     * 集合条件查询
     * @author sunx
     * @since 2021-05-25
     * @param userAgentCertification:
     * @return      java.util.List<com.entity.UserAgentCertification>
     */
    List<UserAgentCertification> listEnhance(UserAgentCertification userAgentCertification);


    /**
     * 分页条件查询
     * @author sunx
     * @since 2021-05-25
     * @param page:
     * @param userAgentCertification:
     * @return      java.util.List<com.entity.UserAgentCertification>
     */
    IPage pageEnhance(Page page, UserAgentCertification userAgentCertification);


    /**
     * 单条条件查询
     * @author sunx
     * @since 2021-05-25
     * @param userAgentCertification:
     * @return      java.util.List<com.entity.UserAgentCertification>
     */
    UserAgentCertification getOneEnhance(UserAgentCertification userAgentCertification);


    /**
     * 总数
     * @author sunx
     * @since 2021-05-25
     * @param userAgentCertification:
     * @return      java.util.List<com.entity.UserAgentCertification>
     */
    Long countEnhance(UserAgentCertification userAgentCertification);


    /**
     * 新增
     * @author sunx
     * @since 2021-05-25
     * @param userAgentCertification:
     * @return Boolean
     */
    Boolean saveEnhance(UserAgentCertification userAgentCertification);


    /**
     * 批量新增
     * @author sunx
     * @since 2021-05-25
     * @param createName: 创建人
     * @param roleId: 角色ID
     * @param userIdList: 用户序列列表
     * @return Boolean
     */
    Boolean saveBatchEnhance(String createName, String roleId, List<String> userIdList);



    /**
     * 修改
     * @author sunx
     * @since 2021-05-25
     * @param userAgentCertification:
     * @return UserAgentCertification
     */
    UserAgentCertification updateEnhance(UserAgentCertification userAgentCertification);



    /**
     * 经纪人认证修改
     * @author sunx
     * @since 2021-05-25
     * @param httpServletRequest: http请求
     * @param userAgentCertification: 经纪人认证信息
     * @param isUpdateUserInfo:  是否需要更新用户信息 【true：一定会更新，false：一定不会更新，null：根据所传的newState进行判断，是否要更新】
     */
    void updateAgentCertEnhance(HttpServletRequest httpServletRequest, UserAgentCertification userAgentCertification, Boolean isUpdateUserInfo);



    /**
     * 删除
     * @author sunx
     * @since 2021-05-25
     * @param userAgentCertification:
     * @return      java.util.List<com.entity.UserAgentCertification>
     */
    Boolean removeEnhance(UserAgentCertification userAgentCertification);
}
