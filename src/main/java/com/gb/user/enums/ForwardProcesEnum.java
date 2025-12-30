package com.gb.user.enums;

import com.gb.bean.GongBaoConfig;
import com.gb.user.constant.UrlConstant;
import com.gb.utils.enumeration.SourceValueEnum;
import com.gb.utils.exception.ParameterNullException;
import jodd.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

/**
 * 转发处理枚举
 *
 * @author: sunx
 * @Date: 2021/11/11 14:25
 * @descript: 转发处理枚举
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("all")
public enum ForwardProcesEnum {
    登录("登录",  UrlConstant.GBT_LOGIN),
    注册("注册",  UrlConstant.GBT_REGISTER),
    短信验证码("短信验证码",  UrlConstant.GBT_SMSCODE),
    验证身份("验证身份",  UrlConstant.GBT_AUTHENTICATION),
    单个用户信息查询("单个用户信息查询",  UrlConstant.GBT_USERINFO_ONE),
    用户信息更新("用户信息更新",  UrlConstant.GBT_USERINFO_UPDATE),
    用户信息新增("用户信息新增",  UrlConstant.GBT_USERINFO_SAVE),
    快速登录("快速登录",  UrlConstant.GBT_QUICKLOGIN),
    登出("登出",  UrlConstant.GBT_LOGOUT),
    注销用户("注销用户",  UrlConstant.GBT_LOGOFF);

    private String desc;
    private String url;

    public static String getFullUrl(String sourceValueCode, ForwardProcesEnum forwardProcesEnum){
        if(Objects.isNull(forwardProcesEnum)){
            throw new ParameterNullException("forwardProcesEnum不能为空！");
        }
        if(forwardProcesEnum.equals(注销用户)) {
            return GongBaoConfig.gbtBackUrl.replaceAll("license-admin/", StringUtils.EMPTY) + forwardProcesEnum.url;
        }
        if(StringUtils.equals(forwardProcesEnum.getDesc(), ForwardProcesEnum.登录.getDesc())
                || StringUtils.equals(forwardProcesEnum.getDesc(), ForwardProcesEnum.短信验证码.getDesc())
                || StringUtils.equals(forwardProcesEnum.getDesc(), ForwardProcesEnum.登出.getDesc())
                || StringUtil.equals(forwardProcesEnum.getDesc(), ForwardProcesEnum.单个用户信息查询.getDesc())
                || StringUtil.equals(forwardProcesEnum.getDesc(), ForwardProcesEnum.快速登录.getDesc())
                || !StringUtils.equals(sourceValueCode, SourceValueEnum.后台注册.getCode())) {
            return GongBaoConfig.gbtFrontUrl + forwardProcesEnum.url;
        }
        return GongBaoConfig.gbtBackUrl + forwardProcesEnum.url;
    }
}
