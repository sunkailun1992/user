package com.gb.user.constant;

/**
 * <p>
 * 路径常量类
 * </p>
 *
 * @author 孙凯伦
 * @since 2020-12-29
 */
public class UrlConstant {

    /**
     *  登录
     */
    public static final String  GBT_LOGIN = "user/login";

    /**
     *  注冊
     */
    public static final String  GBT_REGISTER = "user/register";

    /**
     *  登出
     */
    public static final String  GBT_LOGOUT = "user/logout";

    /**
     *  注销用户
     */
    public static final String  GBT_LOGOFF = "api/organization/user/logoff";

    /**
     *  短信验证码
     */
    public static final String  GBT_SMSCODE = "verify/sendCode";

    /**
     *  校验身份
     */
    public static final String  GBT_VERIFY_IDENTITY = "verify/codeVerify";

    /**
     *  单个用户信息查询
     */
    public static final String GBT_USERINFO_ONE = "user/findOne";

    /**
     *  用户信息更新
     */
    public static final String GBT_USERINFO_UPDATE = "user/update";

    /**
     *  用户信息新增
     */
    public static final String GBT_USERINFO_SAVE = "user/save";

    /**
     *  快速登录
     */
    public static final String GBT_QUICKLOGIN = "user/loginRegister";

    /**
     *  短信验证身份
     */
    public static final String GBT_AUTHENTICATION = "verify/codeVerify";
}
