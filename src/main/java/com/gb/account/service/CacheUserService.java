package com.gb.account.service;

import com.gb.account.entity.bo.CacheUserInfoBO;
import com.gb.account.entity.vo.CacheUserInfoVO;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:40
 * @description:	TODO  缓存用户信息Service服务接口层
 * @source:  	    代码生成器
 */
public interface CacheUserService {

    /**
     * 短信验证码发送
     * @param mobile 手机号
     */
    void smsSendLogin(String mobile);


    /**
     * 单个用户信息查询
     * @author  sunx
     * @since   2021-10-21 01:50:40
     * @param   token: 鉴权令牌
     * @return  CacheUserInfoVO
     */
    CacheUserInfoVO getOneEnhance(String token);


    /**
     * 获取缓存信息
     * @param cacheKey 缓存key
     * @param paramsKey 参数key
     * @return Object
     */
    Object getCacheParamsInfo(String cacheKey, String paramsKey);


    /**
     * 新增
     * @author  sunx
     * @since   2021-10-21 01:50:40
     * @param   token: 鉴权临牌
     * @param   businessDetails: 业务明细
     * @param   cacheUserInfoBO: 缓存用户信息BO
     */
    void saveEnhance(String token, String businessDetails, CacheUserInfoBO cacheUserInfoBO);


    /**
     * 移除
     * @param token: 鉴权临牌
     * @param sourceCode: 来源CODE
     * @return CacheUserInfoVO
     */
    CacheUserInfoVO removeEnchane(String token, String sourceCode);
}
