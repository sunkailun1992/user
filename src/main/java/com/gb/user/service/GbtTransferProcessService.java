package com.gb.user.service;

import com.gb.account.entity.bo.UserBO;
import com.gb.account.entity.vo.CacheUserInfoVO;
import com.gb.user.entity.bo.UserBasicInfoBO;
import com.gb.user.entity.bo.UserInfoBO;
import com.gb.user.entity.bo.UserRegisterBO;
import com.gb.user.entity.model.request.BaseUserRequest;
import com.gb.user.entity.model.request.UserLoginRequest;
import com.gb.user.entity.model.request.UserSendCodeRequest;
import com.gb.user.enums.ForwardProcesEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 请求工保通服务中转处理服务
 * </p>
 *
 * @author sunx
 * @since 2021-03-15
 */
public interface GbtTransferProcessService {

    /**
     * 登录
     *
     * @param forwardProcesEnum : 请求类型
     * @param httpServletRequest : http请求
     * @param userLoginRequest : 用户登录请求
     * @return UserInfoBO
     * @author sunx
     * @since 2021-03-04
     */
    UserInfoBO login(ForwardProcesEnum forwardProcesEnum, HttpServletRequest httpServletRequest, UserLoginRequest userLoginRequest);

     /**
     * 注册
     *
     * @param httpServletRequest : http请求
     * @param bo : 用户注册请求
     * @return UserInfoBO
     * @author sunx
     * @since 2021-03-04
     */
    UserInfoBO register(HttpServletRequest httpServletRequest, UserRegisterBO bo);
    /**
     * 用户基本信息查询
     *
     * @param httpServletRequest : http请求
     * @param userBasicRequest : 用户请求
     * @return UserBasicInfoBO
     * @author sunx
     * @since 2021-03-04
     */
    UserBasicInfoBO queryUserBasicInfo(HttpServletRequest httpServletRequest, BaseUserRequest userBasicRequest);

    /**
     * 用户信息修改
     *
     * @param httpServletRequest : http请求
     * @param bo : 用户请求
     * @author sunx
     * @since 2021-03-04
     */
    void updateUserInfo(HttpServletRequest httpServletRequest, UserBO bo);

    /**
     * 用户信息保存
     *
     * @param httpServletRequest : http请求
     * @param bo : 用户请求
     * @return UserBO
     * @author sunx
     * @since 2021-03-04
     */
    UserBO saveUserInfo(HttpServletRequest httpServletRequest, UserBO bo);

    /**
     * 发送验证码
     *
     * @param httpServletRequest : http请求
     * @param userSendCodeRequest : 请求参数
     * @param showSmsCode : 请求参数
     * @return String
     * @author sunx
     * @since 2021-03-04
     */
    String sendCode(HttpServletRequest httpServletRequest, UserSendCodeRequest userSendCodeRequest, Boolean showSmsCode);

    /**
     * 免密登录
     *
     * @param httpServletRequest : http请求
     * @param verifyType : 类型
     * @param mobile : 手机号
     * @param showSmsCode : 是否展示短信验证码
     * @return UserInfoBO
     * @author sunx
     * @since 2021-03-04
     */
    UserInfoBO freePasswordLogin(HttpServletRequest httpServletRequest, String verifyType, String mobile,  Boolean showSmsCode);

    /**
     * 登出
     *
     * @param httpServletRequest : http请求
     * @param userBasicRequest : 请求参数
     * @author sunx
     * @since 2021-03-04
     * @return CacheUserInfoVO
     */
    CacheUserInfoVO logOut(HttpServletRequest httpServletRequest, BaseUserRequest userBasicRequest);

    /**
     * 注销用户
     *
     * @param httpServletRequest : http请求
     * @param userBasicRequest : 请求参数
     * @author sunx
     * @since 2021-03-04
     */
    void logOff(HttpServletRequest httpServletRequest, BaseUserRequest userBasicRequest);

    /**
     * 修改手机号
     *
     * @param httpServletRequest : http请求
     * @param id : 用户扩展表序列
     * @param newMobile : 新手机号
     * @param smsCode : 短信验证码
     * @author sunx
     * @since 2021-03-04
     */
    void updateMobile(HttpServletRequest httpServletRequest, String id, String newMobile, String smsCode);

    /**
     * 通用【请求工保通服务中转处理】接口
     *
     * @param forwardProcesEnum : 请求类型
     * @param httpServletRequest : http请求
     * @param userBasicRequest : 请求参数
     * @return Object
     * @author sunx
     * @since 2021-03-04
     */
    Object dealWith(ForwardProcesEnum forwardProcesEnum, HttpServletRequest httpServletRequest, BaseUserRequest userBasicRequest);

    /**
     * 根据手机号查询对应的用户ID
     * @param httpServletRequest http请求
     * @param userInfoList G端保单融合请求的用户信息列表
     * @return Map<String, String>
     */
    Map<String, String> queryUserIdsByMobiles(HttpServletRequest httpServletRequest, List<Map<String, String>> userInfoList);

}
