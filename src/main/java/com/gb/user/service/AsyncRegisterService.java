package com.gb.user.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * TODO 异步注册服务
 * 代码生成器
 *
 * @author sunx
 * @className AsyncRegisterService
 * @time 2022-08-30 04:44:18
 */
public interface AsyncRegisterService {

    /**
     * 根据手机号异步注册
     * @param httpServletRequest http请求
     * @param userInfoList G端保单融合请求的用户信息列表
     */
    void asyncRegisterByMobile(HttpServletRequest httpServletRequest, List<Map<String, String>> userInfoList);
}
