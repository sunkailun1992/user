package com.gb.user.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gb.account.entity.query.SourceQuery;
import com.gb.account.entity.query.SourceValueQuery;
import com.gb.account.entity.vo.CacheUserInfoVO;
import com.gb.account.entity.vo.SourceVO;
import com.gb.account.entity.vo.SourceValueVO;
import com.gb.account.service.CacheUserService;
import com.gb.account.service.SourceService;
import com.gb.account.service.SourceValueService;
import com.gb.aliyun.dingding.SendRebootUtil;
import com.gb.bean.GongBaoConfig;
import com.gb.permissions.entity.query.SystemQuery;
import com.gb.permissions.entity.vo.SystemVO;
import com.gb.permissions.service.SystemService;
import com.gb.user.constant.RedisConstant;
import com.gb.user.entity.bo.UserBasicInfoBO;
import com.gb.user.entity.model.request.BaseUserRequest;
import com.gb.user.entity.model.request.GbtRequest;
import com.gb.user.entity.model.response.GbtResponse;
import com.gb.user.entity.model.response.UserResponse;
import com.gb.user.entity.model.response.body.UserInfoQueryResponseBody;
import com.gb.user.enums.ForwardProcesEnum;
import com.gb.user.enums.ForwardProcessCodeEnum;
import com.gb.user.handle.CommonHandle;
import com.gb.user.service.GbtService;
import com.gb.utils.DataSourceUtil;
import com.gb.utils.IpUtils;
import com.gb.utils.RsaUtils;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.enumeration.*;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.UserException;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static com.gb.user.enums.ForwardProcesEnum.注销用户;

/**
 * <p>
 * 工保通服务接口控制器接口
 * </p>
 *
 * @author sunx
 * @since 2021-03-15
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class GbtServiceImpl implements GbtService {

    private CacheUserService cacheUserService;

    private SystemService systemService;

    private SourceService sourceService;

    private SourceValueService sourceValueService;

    @Override
    public void initUserRequest(HttpServletRequest httpServletRequest, BaseUserRequest userBasicRequest) {
        log.debug("初始化请求工保通参数信息开始：请求参数：{}", JSON.toJSONString(userBasicRequest));
        if(Objects.isNull(httpServletRequest) && Objects.isNull(userBasicRequest)) {
            throw new UserException(ReturnCode.请求必填参数为空, "查询用户信息的请求参数不能为空！");
        }
        if(Objects.nonNull(httpServletRequest)) {
            if(StringUtils.isBlank(userBasicRequest.getSourceCode())) {
                String sourceCode = StringUtils.isBlank(httpServletRequest.getHeader(UniversalConstant.SOURCECODE)) ? httpServletRequest.getHeader(UniversalConstant.SOURCE) : httpServletRequest.getHeader(UniversalConstant.SOURCECODE);
                userBasicRequest.setSourceCode(sourceCode);
            }
            if(StringUtils.isBlank(userBasicRequest.getAppCode())) {
                userBasicRequest.setAppCode(httpServletRequest.getHeader(UniversalConstant.APPCODE));
            }
            //初始化TOKEN值
            if(StringUtils.isBlank(userBasicRequest.getToken())) {
                userBasicRequest.setToken(httpServletRequest.getHeader(UniversalConstant.TOKEN));
            }
            if(StringUtils.isNotBlank(userBasicRequest.getToken()) && StringUtils.isBlank(userBasicRequest.getAppCode())) {
                CacheUserInfoVO cacheUserInfoVO = cacheUserService.getOneEnhance(httpServletRequest.getHeader(UniversalConstant.TOKEN));
                userBasicRequest.setAppCode(cacheUserInfoVO.getAppCode());
            }
            if(StringUtils.isBlank(userBasicRequest.getSourceValueCode())) {
                userBasicRequest.setSourceValueCode(httpServletRequest.getHeader(UniversalConstant.SOURCE_VALUE_CODE));
            }
            if(StringUtils.isBlank(userBasicRequest.getIpAddress())) {
                userBasicRequest.setIpAddress(IpUtils.getIp(httpServletRequest));
            }
            //初始化业务明细值
            if(StringUtils.isBlank(userBasicRequest.getBusinessDetails()) && StringUtils.isNotBlank(httpServletRequest.getHeader(UniversalConstant.BUSINESS_DETAILS))) {
                try{
                    userBasicRequest.setBusinessDetails(URLDecoder.decode(httpServletRequest.getHeader(UniversalConstant.BUSINESS_DETAILS), "UTF-8"));
                }catch (UnsupportedEncodingException e){
                    throw new UserException(ReturnCode.用户请求参数错误, "businessDetails参数decode异常！");
                }
            }
            log.debug("初始化请求工保通：http头---token：{}，sourceCode为：{}，请求appCode为：{}，businessDetails为：{}", userBasicRequest.getToken(), userBasicRequest.getSourceCode(), userBasicRequest.getAppCode(), userBasicRequest.getBusinessDetails());
        } else {
          if(StringUtils.isBlank(userBasicRequest.getAppCode())) {
              log.error("初始化请求工保通参数校验出错：请求参数：{}，appCode为空！", JSON.toJSONString(userBasicRequest));
              throw new UserException(ReturnCode.请求必填参数为空, "缺少必要请求参数！");
          }
        }
        validateParams(userBasicRequest);
        //2、初始化appCode值
        String appCode = userBasicRequest.getAppCode();
        if(StringUtils.isNotBlank(appCode)) {
            appCode = appCode.toLowerCase();
            SystemQuery systemQuery = new SystemQuery();
            systemQuery.setCode(appCode);
            SystemVO systemVO = systemService.getOneEnhance(systemQuery);
            if(Objects.isNull(systemVO)) {
                throw new UserException(ReturnCode.请求参数值超出允许的范围, "应用系统不存在！");
            }
            userBasicRequest.setAppCode(appCode);
        }
        log.debug("初始化请求工保通参数信息完毕：请求参数：{}", JSON.toJSONString(userBasicRequest));
    }


    @Override
    public Object callRemoteGbtService(ForwardProcesEnum forwardProcesEnum, BaseUserRequest userBasicRequest) {
        GbtRequest reqObject = buildUserRequest(userBasicRequest);
        String reqJson = JSON.toJSONString(reqObject);
        String url = ForwardProcesEnum.getFullUrl(userBasicRequest.getSourceValueCode(), forwardProcesEnum);
        String respJson = StringUtils.EMPTY;
        String errorMsg = StringUtils.EMPTY;
        ReturnCode returnCode = null;
        try {
            HttpRequest httpRequest = HttpRequest.post(url).timeout(HttpGlobalConfig.getTimeout());
            for (Map.Entry<String, String> map : headerMap(forwardProcesEnum, userBasicRequest).entrySet()) {
                httpRequest.header(map.getKey(), map.getValue());
            }
            respJson = httpRequest.body(reqJson).execute().body();
            log.debug("{}工保通【请求路径：{}，请求参数：{}，响应结果：{}】", forwardProcesEnum.getDesc(), url, reqJson, respJson);
            respJson = respJson.replaceAll("\"msg\":\"未知错误\"","\"msg\":\"token不存在\"");
            if(forwardProcesEnum.equals(注销用户)) {
                GbtResponse gbtResponse = JSONObject.parseObject(respJson, GbtResponse.class);
                if(!gbtResponse.getSuccess() || !StringUtils.equals(gbtResponse.getCode(), String.valueOf(UniversalConstant.TWO_HUNDRED))) {
                    returnCode = ForwardProcessCodeEnum.getGbwCodeEnum(gbtResponse.getCode());
                    errorMsg = getErrorMsg(new UserResponse(){{
                        setMsg(gbtResponse.getMsg());
                    }}, returnCode.getName());
                    throw new UserException(returnCode, errorMsg);
                }
                return gbtResponse.getData();
            } else {
                if(Objects.nonNull(reqObject) && MapUtil.isNotEmpty(reqObject.getReqBody()) && reqObject.getReqBody().containsKey(RedisConstant.P_W)) {
                    String password = (String)reqObject.getReqBody().get("password");
                    if(StringUtils.isNotBlank(password)) {
                        reqObject.getReqBody().put("password", Base64.encode(password));
                        reqJson = JSON.toJSONString(reqObject);
                    }
                }
                UserResponse userResponse = JSONObject.parseObject(respJson, UserResponse.class);
                if(!StringUtils.equals(userResponse.getStatus(), UniversalConstant.SUCCESS) || !StringUtils.equals(userResponse.getCode(), ForwardProcessCodeEnum.SUCCESS.getCode())) {
                    returnCode = ForwardProcessCodeEnum.getGbwCodeEnum(userResponse.getCode());
                    errorMsg = getErrorMsg(userResponse, returnCode.getName());
                    throw new UserException(returnCode, errorMsg);
                }
                return userResponse.getData();
            }

        }catch (UserException e) {
            throw e;
        }catch (Exception e) {
            log.debug("{}工保通异常【请求路径：{}，请求参数：{}】：", forwardProcesEnum.getDesc(), url, reqJson, e);
            errorMsg = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : forwardProcesEnum.getDesc() + "异常！";
            throw new UserException(ReturnCode.用户请求服务异常, forwardProcesEnum.getDesc() + "异常！");
        } finally {
            if(StringUtils.isNotBlank(errorMsg)) {
                if(Objects.isNull(returnCode) || !StringUtils.equals(returnCode.getState(),ReturnCode.用户密码错误.getState())){
                    SendRebootUtil.sendDingNotice(new SendRebootUtil.DingDingParams()
                            .setReqTitle(forwardProcesEnum.getDesc())
                            .setListParams(new HashMap<String, String>(1){{
                                put("url", url);
                            }})
                            .setAtMobileList(SendRebootUtil.ModuleEnum.getMobiles(null, SendRebootUtil.ModuleEnum.USER))
                            .setReqObject(reqObject)
                            .setRespTitle(SystemSourceEnum.GB_T.getDesc())
                            .setRespJson(respJson).setErrorMsg(errorMsg));
                }
            }
        }
    }

    @Override
    public UserBasicInfoBO buildUserBasicInfoBO(UserInfoQueryResponseBody responseBody) {
        UserBasicInfoBO userBasicInfoBO = new UserBasicInfoBO();
        userBasicInfoBO.setUserName(responseBody.getAccount());
        //对接一体化-CA账号登录的时候，可能没有手机号，也没有账户名称，工保通目前用的是userCode
        if(StringUtils.isBlank(userBasicInfoBO.getUserName())){
            userBasicInfoBO.setUserName(responseBody.getUserCode());
        }
        if(Objects.nonNull(responseBody.getUserCode())) {
            userBasicInfoBO.setId(Long.parseLong(responseBody.getUserCode()));
        }
        //对接一体化-CA账号登录的时候，可能没有手机号，也没有账户名称，工保通目前用的是userCode
        userBasicInfoBO.setMobile(responseBody.getPhone());
        if (StringUtils.isBlank(userBasicInfoBO.getMobile())) {
            userBasicInfoBO.setMobile(responseBody.getUserCode());
        }
        userBasicInfoBO.setName(responseBody.getRealName());
        userBasicInfoBO.setAlias(responseBody.getNickname());
        if (StringUtils.isNotBlank(responseBody.getPlatformCode())) {
            userBasicInfoBO.setAppCode(responseBody.getPlatformCode().toLowerCase());
        }
        //性别
        String sex = "2";
        if (Objects.nonNull(responseBody.getSex())) {
            Integer gbtSex = responseBody.getSex();
            if (gbtSex.equals(NumericEnum.ONE.getValue())) {
                sex = "0";
            } else if (gbtSex.equals(NumericEnum.TWO.getValue())) {
                sex = "1";
            }
        }
        userBasicInfoBO.setSex(sex);
        userBasicInfoBO.setPassword(responseBody.getPassword());
        userBasicInfoBO.setEmail(responseBody.getEmail());
        userBasicInfoBO.setBirthday(responseBody.getBirthday());
        return userBasicInfoBO;
    }

    /**
     * 校验请求工保通请求参数
     * @param userBasicRequest：用户基本请求
     * @author sunx
     * @since 2021/3/19  4:35 下午
     */
    private void validateParams(BaseUserRequest userBasicRequest) {
        //1、校验来源码值
        if(StringUtils.isBlank(userBasicRequest.getSourceCode())) {
            throw new UserException(ReturnCode.请求必填参数为空, "来源CODE不能为空！");
        }
        //1.1、初始化sourceCode值，sourceName值
        SourceVO sourceVO = sourceService.getOneEnhance(new SourceQuery(){{
            setCode(userBasicRequest.getSourceCode());
        }});
        if(Objects.isNull(sourceVO)){
            throw new UserException(ReturnCode.请求必填参数为空, "来源CODE不存在！");
        }
        userBasicRequest.setSourceId(sourceVO.getId());
        userBasicRequest.setSourceName(sourceVO.getName());
        //2、校验appCode
        if(CommonHandle.filterSourceList.contains(userBasicRequest.getSourceCode())) {
            if(StringUtils.isNotBlank(userBasicRequest.getAppCode()) && !StringUtils.equalsIgnoreCase(userBasicRequest.getAppCode(), AppCodeEnum.NET_USER.getCode())) {
                throw new UserException(ReturnCode.请求参数值超出允许的范围, "appCode值超出允许的范围！");
            }
            userBasicRequest.setSourceValueCode(SourceValueEnum.静默注册.getCode());
            userBasicRequest.setBusinessDetails("工保网接口");
            userBasicRequest.setAppCode(AppCodeEnum.NET_USER.getCode());
        }
        if(StringUtils.isNotBlank(userBasicRequest.getSourceValueCode())) {
            SourceValueQuery sourceValueQuery = new SourceValueQuery();
            sourceValueQuery.setCode(userBasicRequest.getSourceValueCode());
            SourceValueVO sourceValueVO = sourceValueService.getOneEnhance(sourceValueQuery);
            if(Objects.isNull(sourceValueVO)) {
                throw new UserException(ReturnCode.请求参数值超出允许的范围, "来源值CODE不存在！");
            }
            userBasicRequest.setSourceValueId(sourceValueVO.getId());
            userBasicRequest.setSourceValueName(sourceValueVO.getName());
        }

    }

    /**
     * 组织用户请求工保通请求参数
     * @param userBasicRequest
     * @author sunx
     * @return UserRequest
     * @since 2021/3/19  4:35 下午
     */
    private GbtRequest buildUserRequest(BaseUserRequest userBasicRequest) {
        GbtRequest userRequest = new GbtRequest();
        TreeMap<String, Object> reqBody = objToTreeMap(userBasicRequest);
        userRequest.setReqBody(reqBody);
        String sign = RsaUtils.generateSign(userRequest.getReqBody(), GongBaoConfig.privateKey);
        userRequest.setSign(sign);
        userRequest.setReqTime(LocalDateTime.now());
        String sourceName  = StringUtils.isBlank(userBasicRequest.getSourceCode()) ? userBasicRequest.getSourceCode() : userBasicRequest.getSourceName();
        userRequest.setSource(sourceName);
        //工保通根据这个来变更token
        String platFormCode = StringUtils.isBlank(userBasicRequest.getAppCode()) ? AppCodeEnum.NET_USER.getCode() : userBasicRequest.getAppCode();
        userRequest.setPlatformCode(platFormCode);
        userBasicRequest.setPlatformName(SystemSourceEnum.GB_N.getDesc());
        userRequest.setToken(userBasicRequest.getToken());
        userRequest.setIpAddress(userBasicRequest.getIpAddress());
        userRequest.setReqType("PROGRAM");
        return userRequest;
    }

    /**
     * 组织用户请求工保通请求参数
     * @param userBasicRequest
     * @author sunx
     * @return UserRequest
     * @since 2021/3/19  4:35 下午
     */
    private Map<String, String> headerMap(ForwardProcesEnum forwardProcesEnum, BaseUserRequest userBasicRequest) {
       Map<String, String> headerMap = Maps.newHashMap();
       headerMap.put(DataSourceUtil.DATA_SOURCE, DataSourceUtil.get());
       if(forwardProcesEnum.equals(注销用户)) {
           headerMap.put("Authorization", userBasicRequest.getToken());
           headerMap.put("deviceType","PROGRAM");
           headerMap.put("platform", userBasicRequest.getAppCode());
       }
       return headerMap;
    }

    /**
     * 将对象转成TreeMap,属性名为key,属性值为value
     * @param object; 对象
     * @return TreeMap<String, Object> objToMap(Object object)
     * @since 2021/3/19  4:35 下午
     */
    private <T> TreeMap<String, Object> objToTreeMap(Object object) {
        TreeMap<String, Object> treeMap = Maps.newTreeMap();
        if(Objects.isNull(object)) {
            return treeMap;
        }
        Class classz = object.getClass();
        try{
            for (Field field : classz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(object);
                if(Objects.isNull(value)){
                    continue;
                }
                treeMap.put(field.getName(), value);
            }
            return treeMap;
        }catch(Exception e){
            log.error("bean转TreeMap异常：", e);
            throw new BusinessException("bean转TreeMap异常！");
        }
    }

    /**
     * 获取错误信息
     * B临时需求-VIDH-5100 优化工保通响应报文【未知错误】为【token不存在】
     * @param userResponse 工保通响应结果
     * @param returnCodeName 工保网错误消息
     * @return String 最终显示的错误消息
     */

    private  String getErrorMsg(UserResponse userResponse, String returnCodeName) {
        String errorMsg = returnCodeName;
        if(StringUtils.isNotBlank(userResponse.getMessage()) || StringUtils.isNotBlank(userResponse.getMsg())) {
            errorMsg = StringUtils.isNotBlank(userResponse.getMessage()) ? userResponse.getMessage() : userResponse.getMsg();
            if(StringUtils.equals(errorMsg, "未知错误")) {
                errorMsg = "token不存在";
            }
        }
        return errorMsg;
    }
}
