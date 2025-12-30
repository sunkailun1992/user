package com.gb.user.service;

import com.gb.user.entity.bo.UserBasicInfoBO;
import com.gb.user.entity.model.request.BaseUserRequest;
import com.gb.user.entity.model.response.body.UserInfoQueryResponseBody;
import com.gb.user.enums.ForwardProcesEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 工保通服务接口控制器接口
 * </p>
 *
 * @author sunx
 * @since 2021-03-15
 */
public interface GbtService {

     /**
      * 初始化用户请求信息
      *
      * @param httpServletRequest : http请求
      * @param userBasicRequest : 请求参数
      * @return void
      * @author sunx
      * @since 2021-03-04
      */
     void initUserRequest(HttpServletRequest httpServletRequest, BaseUserRequest userBasicRequest);

     /**
      * 调用远程工保通服务
      *
      * @param forwardProcesEnum : 转发功能
      * @param userBasicRequest : 请求参数
      * @return Object
      * @author sunx
      * @since 2021-03-04
      */
     Object callRemoteGbtService(ForwardProcesEnum forwardProcesEnum, BaseUserRequest userBasicRequest);

     /**
      * 组织用户基本信息BO
      *
      * @param responseBody: 响应Body
      * @return UserBasicInfoBO
      * @author sunx
      * @since 2021/3/19  4:35 下午
      */
     UserBasicInfoBO buildUserBasicInfoBO(UserInfoQueryResponseBody responseBody);
}
