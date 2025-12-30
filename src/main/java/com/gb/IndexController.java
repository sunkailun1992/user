package com.gb;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.gb.account.entity.bo.CacheUserInfoBO;
import com.gb.account.entity.query.UserQuery;
import com.gb.account.entity.vo.CacheUserInfoVO;
import com.gb.account.entity.vo.UserVO;
import com.gb.account.service.CacheUserService;
import com.gb.account.service.UserService;
import com.gb.user.entity.bo.*;
import com.gb.user.entity.model.request.BaseUserRequest;
import com.gb.user.entity.model.request.UserLoginRequest;
import com.gb.user.entity.model.request.UserSendCodeRequest;
import com.gb.user.entity.model.request.UserUniversalRequest;
import com.gb.user.handle.CommonHandle;
import com.gb.user.service.GbtTransferProcessService;
import com.gb.utils.DataSourceUtil;
import com.gb.utils.IpUtils;
import com.gb.utils.Json;
import com.gb.utils.JsonUtil;
import com.gb.utils.annotations.DynamicDataSource;
import com.gb.utils.annotations.Methods;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.enumeration.*;
import com.gb.utils.exception.UserException;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.gb.user.enums.ForwardProcesEnum.快速登录;
import static com.gb.user.enums.ForwardProcesEnum.登录;

/**
 * <p>
 * 用户登录
 * </p>
 *
 * @author 孙凯伦
 * @since 2020-01-02
 */
@RestController
@Api(tags = "用户登录")
@RequestMapping
@Slf4j
@DynamicDataSource
@Setter(onMethod_ = {@Autowired})
public class IndexController {

    /**
     * 用户
     */
    private UserService userService;

    /**
     * 缓存
     */
    private CacheUserService cacheUserService;

    /**
     * 工保通转换处理服务
     */
    private GbtTransferProcessService gbtTransferProcessService;

    /**
     * 用户登录验证码发送
     *
     * @param mobile: 用户手机号
     * @return com.utils.Json
     * @author 孙凯伦
     * @since 2020/1/2  3:49 下午
     */
    @ResponseBody
    @PostMapping("/smsSendLogin")
    @ApiOperation(value = "用户登录验证码发送", httpMethod = "POST", notes = "用户登录验证码发送", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "用户手机号", dataType = "String", required = true),
    })
    public Json smsSendLogin(String mobile) {
        cacheUserService.smsSendLogin(mobile);
        return new Json(ReturnCode.成功, null);
    }


    /**
     * 登录接口
     *
     * @param httpServletRequest: 客户端请求
     * @param bo:                 请求参数
     * @return com.gb.utils.Json
     * @author sunkailun
     * @DateTime 2021/3/17  10:54 上午
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @ResponseBody
    @PostMapping("/login")
    @ApiOperation(value = "登录接口", httpMethod = "POST", notes = "登录接口", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号（手机号或登录账号必填一个）", dataType = "String"),
            @ApiImplicitParam(name = "userName", value = "登录账号 （手机号或账号必填一个）", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true),
    })
    public Json<String> login(HttpServletRequest httpServletRequest, UserLoginBO bo) {
        //1、校验请求参数
        if (StringUtils.isBlank(bo.getPassword())) {
            throw new UserException("缺少登录必要参数");
        }
        if (StringUtils.isBlank(bo.getPhone()) && StringUtils.isBlank(bo.getUserName())) {
            throw new UserException("手机号或账号必要一个参数");
        }
        UserLoginRequest userLoginRequest = BeanUtil.copyProperties(bo, UserLoginRequest.class);
        UserInfoBO userInfoBO = gbtTransferProcessService.login(登录, httpServletRequest, userLoginRequest);
        user(userInfoBO.getUserBasicInfoBO(), userInfoBO.getToken());
        return new Json(ReturnCode.成功, userInfoBO.getToken());
    }

    /**
     * 获取用户权限
     *
     * @return com.utils.Json
     * @author sunx
     * @since 2021/3/19  4:35 下午
     */
    @ResponseBody
    @PostMapping("/getUserPermission")
    @ApiOperation(value = "获取用户权限", httpMethod = "POST", notes = "获取用户权限", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型（0：全部，1：菜单，2：按钮）", dataType = "Integer"),
    })
    public Json<Object> getUserPermission(HttpServletRequest httpServletRequest, Integer type) {
        //1、取出本地缓存用户相关等信息
        CacheUserInfoVO cacheUserInfoVO = getUserInfo(httpServletRequest, null).getObj();
        if (StringUtils.isBlank(cacheUserInfoVO.getAppCode())) {
            throw new UserException("请求参数appCode未找到，请检查问题！");
        }
        String cacheKey = cacheUserInfoVO.getAppCode() + "_" + cacheUserInfoVO.getUserName();
        if (CommonHandle.tokenTimeSettingMap.containsKey(httpServletRequest.getHeader(UniversalConstant.SOURCECODE))) {
            cacheKey = cacheKey + CommonHandle.tokenTimeSettingMap.get(httpServletRequest.getHeader(UniversalConstant.SOURCECODE)).getKeyTail();
        }
        Object redisResource = cacheUserService.getCacheParamsInfo(cacheKey, UniversalConstant.RESOURCE);
        log.debug("调用获取权限接口【缓存key：{}，type：{}，userName：{}，获取权限完毕，resource：{}】", cacheKey, type, cacheUserInfoVO.getUserName(), redisResource);
        List<Map> resource = Lists.newArrayList();
        List<Map> button = Lists.newArrayList();
        List<Map> navigation = Lists.newArrayList();
        if (Objects.nonNull(redisResource)) {
            //判断类型区分
            resource = JsonUtil.list(Convert.toStr(redisResource), Map.class);
            resource.stream().forEach(resourceVO -> {
                if (Convert.toBool(resourceVO.get(UniversalConstant.NAVIGATION))) {
                    navigation.add(resourceVO);
                } else {
                    button.add(resourceVO);
                }
            });

        }
        //2、返回用户权限
        if (Objects.isNull(type)) {
            return new Json(ReturnCode.成功, resource);
        }
        if (type.equals(NumericEnum.ONE.getValue())) {
            return new Json(ReturnCode.成功, navigation);
        }
        if (type.equals(NumericEnum.TWO.getValue())) {
            return new Json(ReturnCode.成功, button);
        }
        return new Json(ReturnCode.成功, resource);
    }

    /**
     * 获取用户信息
     *
     * @return com.utils.Json
     * @author sunx
     * @since 2021/3/19  4:35 下午
     */
    @ResponseBody
    @PostMapping("/getUserInfo")
    @ApiOperation(value = "获取用户信息", httpMethod = "POST", notes = "获取用户信息", response = String.class)
    public Json<CacheUserInfoVO> getUserInfo(HttpServletRequest httpServletRequest, String token) {
        String reqToken = (StringUtils.isBlank(token)) ? httpServletRequest.getHeader(UniversalConstant.TOKEN) : token;
        CacheUserInfoVO cacheUserInfoVO = cacheUserService.getOneEnhance(reqToken);
        if (Objects.isNull(cacheUserInfoVO)) {
            UserBasicInfoBO basicInfoBO = gbtTransferProcessService.queryUserBasicInfo(null, new BaseUserRequest() {{
                setToken(reqToken);
                setSourceCode(SystemSourceEnum.GB_N.getCode());
                setSourceValueCode(SourceValueEnum.静默注册.getCode());
                setAppCode(AppCodeEnum.NET_USER.getCode());
                setIpAddress(IpUtils.getIp(httpServletRequest));
                setBusinessDetails("gateWay-define");
            }});
            log.debug("用户信息获取-授权用户体系-token：{}，用户基本信息：{}", token, JSON.toJSONString(basicInfoBO));
            user(basicInfoBO, reqToken);
        }
        cacheUserInfoVO = cacheUserService.getOneEnhance(reqToken);
        if (Objects.isNull(cacheUserInfoVO)) {
            return new Json(ReturnCode.无效TOKEN, ReturnCode.无效TOKEN.getName());
        }
        return new Json(ReturnCode.成功, cacheUserInfoVO);
    }

    /**
     * 发送短信验证码
     *
     * @param bo: 请求参数
     * @return com.utils.Json
     * @author sunx
     * @since 2021/3/19  4:35 下午
     */
    @ResponseBody
    @PostMapping("/sendCode")
    @ApiOperation(value = "发送短信验证码", httpMethod = "GET", notes = "发送短信验证码", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "verifyType", value = "验证类型", dataType = "String", required = true),
            @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", dataType = "String")
    })
    public Json<Object> sendCode(HttpServletRequest httpServletRequest, UserSmsCodeBO bo) {
        UserSendCodeRequest userSendCodeRequest = BeanUtil.copyProperties(bo, UserSendCodeRequest.class);
        if (bo.getNoSendCode() == null) {
            userSendCodeRequest.setNoSendCode(false);
        }
        userSendCodeRequest.setNeedReturnCode(true);
        return new Json(ReturnCode.成功, gbtTransferProcessService.sendCode(httpServletRequest, userSendCodeRequest, null));
    }

    /**
     * 免密登录：支持信息收集表单:INFO_COLLECTION_FORM
     *
     * @param mobile:             手机号
     * @param httpServletRequest: http请求;
     * @return com.utils.Json
     * @author sunx
     * @since 2021/3/19  4:35 下午
     */
    @ResponseBody
    @PostMapping("/freePasswordLogin")
    @ApiOperation(value = "免密登录", httpMethod = "POST", notes = "免密登录", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String", required = true)
    })
    public Json<String> freePasswordLogin(HttpServletRequest httpServletRequest, String verifyType, String mobile) {
        log.debug("免密登录，请求参数：【verifyType：{}-mobile：{}】，请求头信息：【appCode：{}】", verifyType, mobile, httpServletRequest.getHeader(UniversalConstant.APPCODE));
        //1、调用工保通免密登录接口
        UserInfoBO userInfoBO = gbtTransferProcessService.freePasswordLogin(httpServletRequest, verifyType, mobile, null);
        //2、用户信息存缓存
        user(userInfoBO.getUserBasicInfoBO(), userInfoBO.getToken());
        //3、将用户中心的结果返回
        return new Json(ReturnCode.成功, userInfoBO.getToken());
    }

    /**
     * 快速登录
     *
     * @param bo:                 用户请求参数
     * @param httpServletRequest: http请求;
     * @return com.utils.Json
     * @author sunx
     * @since 2021/3/19  4:35 下午
     */
    @ResponseBody
    @PostMapping("/quickLogin")
    @ApiOperation(value = "快速登录", httpMethod = "POST", notes = "快速登录", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String", required = true),
            @ApiImplicitParam(name = "smsCode", value = "短信验证码", dataType = "String", required = true)
    })
    public Json<String> quickLogin(HttpServletRequest httpServletRequest, UserQuickLoginBO bo) {
        try {
            //1、校验请求参数是否为空
            if (StringUtils.isBlank(bo.getMobile()) || StringUtils.isBlank(bo.getSmsCode())) {
                throw new UserException("缺少快速登录必要参数");
            }
            //2、调用工保通
            UserInfoBO userInfoBO = gbtTransferProcessService.login(快速登录, httpServletRequest, new UserLoginRequest() {{
                setPhone(bo.getMobile());
                setVerifyCode(bo.getSmsCode());
                setAppCode(bo.getAppCode());
                setAgentUserId(bo.getAgentUserId());
//                setInviteUserId(bo.getInviteUserId());
                setPassword(UniversalConstant.UNIVERSAL_PD);
            }});
            //3、用户信息存缓存
            user(userInfoBO.getUserBasicInfoBO(), userInfoBO.getToken());
            //5、将用户中心的结果返回
            return new Json(ReturnCode.成功, userInfoBO.getToken());
        } catch (UserException e) {
            log.error("快速登录失败：", e);
            return new Json(ReturnCode.用户端错误, e.getMessage());
        }
    }

    /**
     * 登出
     *
     * @param httpServletRequest
     * @return com.utils.Json
     */
    @Methods(methodsName = "登出", methods = "logOut")
    @ApiOperation(value = "登出", httpMethod = "GET", notes = "登出", response = String.class)
    @GetMapping("/logOut")
    public Json<String> logOut(HttpServletRequest httpServletRequest) {
        if (StringUtils.isBlank(httpServletRequest.getHeader(UniversalConstant.SOURCECODE)) || StringUtils.isBlank(httpServletRequest.getHeader(UniversalConstant.TOKEN))) {
            throw new UserException(ReturnCode.请求必填参数为空, "请求头缺少必要参数！");
        }
        gbtTransferProcessService.logOut(httpServletRequest, new UserUniversalRequest());
        return new Json(ReturnCode.成功, "登出成功！");
    }

    /**
     * 注销用户
     *
     * @param httpServletRequest
     * @return com.utils.Json
     */
    @Methods(methodsName = "注销用户", methods = "logOff")
    @ApiOperation(value = "注销用户", httpMethod = "GET", notes = "注销用户", response = Json.class)
    @GetMapping("/logOff")
    public Json<String> logOff(HttpServletRequest httpServletRequest) {
        if (StringUtils.isBlank(httpServletRequest.getHeader(UniversalConstant.SOURCECODE)) || StringUtils.isBlank(httpServletRequest.getHeader(UniversalConstant.TOKEN))) {
            throw new UserException(ReturnCode.请求必填参数为空, "请求头缺少必要参数！");
        }
        gbtTransferProcessService.logOff(httpServletRequest, new UserUniversalRequest());
        return new Json(ReturnCode.成功, "注销用户成功！");
    }

    @ResponseBody
    @Methods(methodsName = "用户注册", methods = "userRegister")
    @ApiOperation(value = "用户注册", httpMethod = "POST", notes = "用户注册", response = String.class)
    @PostMapping("/userRegister")
    public Json<Map<String, String>> userRegister(HttpServletRequest httpServletRequest, UserRegisterBO bo) {
        //注册用户
        UserInfoBO userInfoBO = gbtTransferProcessService.register(httpServletRequest, bo);
        //保存缓存
        user(userInfoBO.getUserBasicInfoBO(), userInfoBO.getToken());
        if (StringUtils.equals(httpServletRequest.getHeader(UniversalConstant.SOURCE), SystemSourceEnum.GB_J.getCode())) {
            return new Json(ReturnCode.成功, new HashMap<String, String>(2) {{
                put(UniversalConstant.TOKEN, userInfoBO.getToken());
                put("errorMsg", null);
            }});
        }
        return new Json(ReturnCode.成功, userInfoBO.getToken());
    }

    /**
     * 授权用户体系
     *
     * @param bo                 请求参数
     * @param httpServletRequest
     * @return com.utils.Json
     */
    @Methods(methodsName = "授权用户体系", methods = "authUserSystem")
    @ApiOperation(value = "授权用户体系", httpMethod = "POST", notes = "授权用户体系", response = Json.class)
    @PostMapping("/authUserSystem")
    public Json<String> authUserSystem(@RequestBody UserSystemBO bo, HttpServletRequest httpServletRequest) {
        log.debug("授权用户体系-请求参数：{}", JSON.toJSONString(bo));
        if (StringUtils.isBlank(bo.getToken()) || StringUtils.isBlank(httpServletRequest.getHeader(UniversalConstant.APPCODE))) {
            throw new UserException(ReturnCode.请求必填参数为空, "缺少授权用户体系必要参数！");
        }
        UserBasicInfoBO basicInfoBO = gbtTransferProcessService.queryUserBasicInfo(null, new BaseUserRequest() {{
            setToken(bo.getToken());
            setSourceCode(httpServletRequest.getHeader(UniversalConstant.SOURCECODE));
            setSourceValueCode(httpServletRequest.getHeader(UniversalConstant.SOURCE_VALUE_CODE));
            setAppCode(httpServletRequest.getHeader(UniversalConstant.APPCODE));
            setIpAddress(IpUtils.getIp(httpServletRequest));
            setBusinessDetails(httpServletRequest.getHeader(UniversalConstant.BUSINESS_DETAILS));
        }});
        log.debug("授权用户体系-请求参数：{}，用户基本信息：{}", JSON.toJSONString(bo), JSON.toJSONString(basicInfoBO));
        user(basicInfoBO, bo.getToken());
        return new Json(ReturnCode.成功, "用户体系授权成功！");
    }

    /**
     * 用户信息保存缓存
     *
     * @param basicInfoBO; 用户基本信息
     * @param token:       token
     * @return void
     * @author sunkailun
     * @DateTime 2021/3/18  10:06 上午
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    private void user(UserBasicInfoBO basicInfoBO, String token) {
        log.debug("当前用户环境变量：{}", DataSourceUtil.get());
        userService.initUserInfo(basicInfoBO);
        //新增缓存
        UserVO u = userService.getOneEnhance(new UserQuery() {{
            setId(String.valueOf(basicInfoBO.getId()));
        }});
        CacheUserInfoBO cacheUserInfoBO = BeanUtil.copyProperties(u, CacheUserInfoBO.class);
        cacheUserInfoBO.setIp(basicInfoBO.getIp());
        cacheUserInfoBO.setAppCode(basicInfoBO.getAppCode());
        cacheUserInfoBO.setLoginSourceCode(basicInfoBO.getSourceCode());
        cacheUserService.saveEnhance(token, basicInfoBO.getBusinessDetails(), cacheUserInfoBO);
    }
}