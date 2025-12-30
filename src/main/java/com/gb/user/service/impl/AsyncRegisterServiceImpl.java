package com.gb.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gb.account.entity.bo.UserBO;
import com.gb.account.entity.bo.UserExtendsBO;
import com.gb.account.entity.query.SourceQuery;
import com.gb.account.entity.query.SourceValueQuery;
import com.gb.account.entity.query.UserExtendsQuery;
import com.gb.account.entity.vo.SourceVO;
import com.gb.account.entity.vo.SourceValueVO;
import com.gb.account.entity.vo.UserExtendsVO;
import com.gb.account.service.SourceService;
import com.gb.account.service.SourceValueService;
import com.gb.account.service.UserExtendsService;
import com.gb.account.service.UserService;
import com.gb.mq.crm.RegistUserEvent;
import com.gb.user.entity.UserAgentCertification;
import com.gb.user.entity.bo.UserBasicInfoBO;
import com.gb.user.entity.model.request.GbtQuickLoginRequest;
import com.gb.user.entity.model.request.UserInfoSaveRequest;
import com.gb.user.entity.model.request.UserSendCodeRequest;
import com.gb.user.entity.model.response.body.UserInfoQueryResponseBody;
import com.gb.user.entity.model.response.body.UserResponseBody;
import com.gb.user.enums.MqNoticeTypeEnum;
import com.gb.user.enums.RoleUserTypeRelatedEnum;
import com.gb.user.service.AsyncRegisterService;
import com.gb.user.service.GbtService;
import com.gb.user.service.UserAgentCertificationService;
import com.gb.utils.AddressUtils;
import com.gb.utils.IpUtils;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.enumeration.*;
import com.gb.utils.exception.UserException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gb.user.enums.ForwardProcesEnum.*;

@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class AsyncRegisterServiceImpl implements AsyncRegisterService {

    private UserService userService;

    private UserExtendsService userExtendsService;

    private GbtService gbtService;

    private UserAgentCertificationService agentService;

    private SourceService sourceService;

    private SourceValueService sourceValueService;

    @Override
    @Async
    public void asyncRegisterByMobile(HttpServletRequest httpServletRequest, List<Map<String, String>> userInfoList) {
        Map<String, String> resultMap = Maps.newHashMap();
        List<String> noExistMobileList = Lists.newArrayList();
        //请求参数，去重与不符合要求的手机号
        List<String> distinctMobileList = userInfoList.stream().map(u -> u.get("mobile")).distinct().filter(s -> s.matches("^1(3|4|5|6|7|8|9)\\d{9}$")).collect(Collectors.toList());
        List<UserExtendsVO> userExtendsList = userExtendsService.listEnhance(new UserExtendsQuery(){{
            setMobileList(distinctMobileList);
        }});
        if(CollectionUtils.isNotEmpty(userExtendsList)) {
            resultMap = userExtendsList.stream().collect(Collectors.toMap(UserExtendsVO::getMobile, UserExtendsVO::getUserId));
            for (String mobile : distinctMobileList) {
                if(!resultMap.containsKey(mobile) && !noExistMobileList.contains(mobile)) {
                    noExistMobileList.add(mobile);
                }
            }
        }else {
            noExistMobileList = distinctMobileList;
        }
        if(CollectionUtils.isEmpty(noExistMobileList)) {
            return;
        }
        try{
            registerGbt(resultMap, noExistMobileList, userInfoList, httpServletRequest);
        }catch (Exception e) {
            log.error("asyncRegisterByMobile-异常：", e);
        }
    }


    /**
     * 注册工保通
     * @param resultMap 工保网已经存在的手机号与用户ID对应结果Map
     * @param noExistMobileList 未注册工保网的手机号
     * @param userInfoList G端保单融合请求的用户信息列表
     * @param httpServletRequest http请求
     */
    private void registerGbt(Map<String, String> resultMap, List<String> noExistMobileList, List<Map<String,String>> userInfoList, HttpServletRequest httpServletRequest) {
        //不存在的手机号，注册工保通
        for (String mobile : noExistMobileList) {
            UserBO userBO = null;
            for(Map<String,String> userInfoMap : userInfoList) {
                if(StringUtils.equals(mobile, userInfoMap.get(UniversalConstant.MOBILE))) {
                    userBO = buildUserBO(mobile, userInfoMap.get(UniversalConstant.NAME), "queryUserIdsByMobiles", userInfoMap.get("type"), IpUtils.getIp(httpServletRequest));
                    break;
                }
            }
            try{
                //组织调用工保通新增用户接口的请求参数
                UserInfoSaveRequest userInfoSaveRequest = buildUserInfoSaveRequest(httpServletRequest, new UserBO(){{
                    setUserName(mobile);
                    setUserExtendsBO(new UserExtendsBO() {{
                        setMobile(mobile);
                    }});
                }}, SystemSourceEnum.GB_N, SourceValueEnum.后台注册.getCode(), AppCodeEnum.NET_USER.getCode());
                //调用工保通新增用户接口
                UserInfoQueryResponseBody body = JSONObject.parseObject(JSON.toJSONString(gbtService.callRemoteGbtService(用户信息新增, userInfoSaveRequest)), UserInfoQueryResponseBody.class);
                userBO.getUserExtendsBO().setUserId(body.getUserCode());
                userBO.setId(body.getUserCode());
                userService.saveEnhance(userBO);
                //通知crm系统
                addUserNoticeCrm(new UserBasicInfoBO(){{
                    setId(Long.parseLong(body.getUserCode()));
                    setAppCode(AppCodeEnum.NET_USER.getCode());
                    setMobile(mobile);
                }}, null, false, userBO.getCreateName());
                resultMap.put(mobile, body.getUserCode());
            }catch (UserException e) {
                if(!e.getReturnCode().equals(ReturnCode.该手机号已注册)) {
                    log.error("当前mobile：{}，name：{}，sourceId：{}，批量注册工保通异常：{}", mobile, userBO.getUserExtendsBO().getName(), userBO.getSourceId(), e.getMessage());
                    continue;
                }
                //工保通用户已经存在，但是工保网本地不存在，需要走工保通快速登录接口
                try{
                    String userId = freePasswordLoginNoValidate(AppCodeEnum.NET_USER.getCode(), mobile, userBO.getIp(), SystemSourceEnum.GB_N.getDesc(),SystemSourceEnum.GB_N.getCode(), SourceValueEnum.后台注册.getCode());
                    userBO.getUserExtendsBO().setUserId(userId);
                    userBO.setId(userId);
                    userService.saveEnhance(userBO);
                    //通知crm
                    RegistUserEvent event = new RegistUserEvent()
                            .setUserId(userId)
                            .setUserType(0)
                            .setMobile(mobile)
                            .setName(null)
                            .setAgentUserId(null);
                    MqNoticeTypeEnum.CRM_CLUE_MQ.pushMqMessage(快速登录.getDesc(), event);
                    resultMap.put(mobile, userBO.getId());
                }catch (UserException e2) {
                    log.error("当前mobile：{}，name：{}，sourceId：{}，批量注册工保通异常2：{}", mobile, userBO.getUserExtendsBO().getName(), userBO.getSourceId(), e2.getMessage());
                }
            }
        }
    }


    /**
     * 组织用户新增请求参数
     * @param httpServletRequest
     * @param bo
     * @return UserInfoSaveRequest
     */
    private UserInfoSaveRequest buildUserInfoSaveRequest(HttpServletRequest httpServletRequest, UserBO bo, SystemSourceEnum systemSourceEnum, String sourceValueCode, String appCode) {
        UserInfoSaveRequest userInfoSaveRequest = new UserInfoSaveRequest();
        userInfoSaveRequest.setAccount(bo.getUserName());
        userInfoSaveRequest.setStatus(0);
        userInfoSaveRequest.setPassword(UniversalConstant.UNIVERSAL_PD);
        userInfoSaveRequest.setRePassword(userInfoSaveRequest.getPassword());
        UserExtendsBO userExtendsBO = bo.getUserExtendsBO();
        if(Objects.nonNull(userExtendsBO)) {
            userInfoSaveRequest.setRealName(userExtendsBO.getName());
            userInfoSaveRequest.setNickname(userExtendsBO.getAlias());
            userInfoSaveRequest.setEmail(userExtendsBO.getEmail());
            userInfoSaveRequest.setAddress(userExtendsBO.getAddress());
            userInfoSaveRequest.setCityCode(userExtendsBO.getCityCode());
            String birthday = userExtendsBO.getBirthdayStr();
            if(StringUtils.isNotBlank(birthday) && LenEnum.NORMAL_DATE_LEN.getLen().equals(birthday.length())) {
                birthday = birthday.concat(" 00:00:00");
            }
            userInfoSaveRequest.setBirthday(birthday);
            if(Objects.nonNull(userExtendsBO.getSex())) {
                int sex = userExtendsBO.getSex();
                if(sex == 0){
                    sex = 1;
                }else if(sex == 1) {
                    sex = 2;
                }else {
                    sex = 0;
                }
                userInfoSaveRequest.setSex(sex);
            }
            userInfoSaveRequest.setPhone(userExtendsBO.getMobile());
        }
        userInfoSaveRequest.setIpAddress(IpUtils.getIp(httpServletRequest));
        if(Objects.nonNull(systemSourceEnum)) {
            userInfoSaveRequest.setSourceCode(systemSourceEnum.getCode());
            userInfoSaveRequest.setSourceName(systemSourceEnum.getDesc());
        }
        if(StringUtils.isNotBlank(sourceValueCode)) {
            userInfoSaveRequest.setSourceValueCode(sourceValueCode);
        }
        if(StringUtils.isNotBlank(appCode)) {
            userInfoSaveRequest.setAppCode(appCode);
        }
        return userInfoSaveRequest;
    }


    /**
     * 免密登录接口【无校验】
     * @param appCode 应用码值
     * @param mobile 手机号
     * @param ip ip地址
     * @param sourceName 来源名称
     * @param sourceCode 来源值
     * @param sourceValueCode 来源值码值
     * @return String
     */
    private String freePasswordLoginNoValidate(String appCode, String mobile, String ip, String sourceName, String sourceCode, String sourceValueCode) {
        //调用工保通发送短信验证码接口
        UserSendCodeRequest userSendCodeRequest = new UserSendCodeRequest();
        userSendCodeRequest.setVerifyType("PHONE_REGISTER");
        userSendCodeRequest.setPhone(mobile);
        userSendCodeRequest.setAppCode(appCode);
        userSendCodeRequest.setNoSendCode(true);
        userSendCodeRequest.setNeedReturnCode(true);
        userSendCodeRequest.setSourceCode(sourceCode);
        userSendCodeRequest.setSourceCode(sourceName);
        userSendCodeRequest.setSourceValueCode(sourceValueCode);
        userSendCodeRequest.setIpAddress(ip);
        String smsCode = (String)gbtService.callRemoteGbtService(短信验证码, userSendCodeRequest);
        //调用工保通快速登录接口
        GbtQuickLoginRequest gbtQuickLoginRequest = new GbtQuickLoginRequest();
        gbtQuickLoginRequest.setPassword(UniversalConstant.UNIVERSAL_PD);
        gbtQuickLoginRequest.setIpAddress(ip);
        gbtQuickLoginRequest.setVerifyCode(smsCode);
        gbtQuickLoginRequest.setSourceCode(sourceCode);
        gbtQuickLoginRequest.setSourceName(sourceName);
        gbtQuickLoginRequest.setSourceValueCode(sourceValueCode);
        gbtQuickLoginRequest.setPhone(mobile);
        String respObject = JSON.toJSONString(gbtService.callRemoteGbtService(快速登录, gbtQuickLoginRequest));
        if (StringUtils.isBlank(respObject)) {
            throw new UserException(ReturnCode.用户请求服务异常, "快速登录异常，获取token为空！");
        }
        UserResponseBody responseBody = JSONObject.parseObject(respObject, UserResponseBody.class);
        return responseBody.getUserCode();
    }


    /**
     * MQ通知CRM【设置经纪人到认证表】
     *
     * @param basicInfoBO: 请求参数
     * @param agentUserId: 经纪人id
     * @return void
     * @author sunx
     * @since 2021/3/19  4:35 下午
     */
    private void addUserNoticeCrm(UserBasicInfoBO basicInfoBO, String agentUserId, Boolean typeRegister, String createName) {
        if (Objects.isNull(basicInfoBO)) {
            return;
        }
        //0：普通用户，1：经纪人用户
        Integer noticeCrmType = (StringUtils.equalsIgnoreCase(basicInfoBO.getAppCode(), AppCodeEnum.NET_USER.getCode()) ? 0 : null);
        if (StringUtils.equalsIgnoreCase(basicInfoBO.getAppCode(), AppCodeEnum.NET_AGENT.getCode())) {
            noticeCrmType = 1;
            String userId = String.valueOf(basicInfoBO.getId());
            String opName = StringUtils.isBlank(createName) ? (basicInfoBO.getName() + "-" + userId) : createName;
            try {
                agentService.saveEnhance(new UserAgentCertification(){{
                    setUserId(userId);
                    setCreateName(opName);
                }});
            }catch (Exception e){
                log.error("注册经纪人【USERID:{}, 经纪人认证信息表保存】异常：", userId, e);
            }
        }
        if(Objects.isNull(noticeCrmType)) {
            return;
        }
        String type = typeRegister ? "注册" : "新增";
        RegistUserEvent event = new RegistUserEvent()
                .setUserId(String.valueOf(basicInfoBO.getId()))
                .setUserType(noticeCrmType)
                .setMobile(basicInfoBO.getMobile())
                .setName(basicInfoBO.getName())
                .setAgentUserId(agentUserId);
        MqNoticeTypeEnum.CRM_CLUE_MQ.pushMqMessage(type, event);
    }


    /**
     * 组织用户信息BO
     * @param mobile 手机号
     * @param name 用户姓名
     * @param createName 创建人
     * @param sourceEndType G端保单融合来源端类型（0：工保网、1：电子保函、2：一体化、3：智慧保证金-工保金、4、农民工系统）
     * @param ip 请求IP
     * @return UserBO
     */
    public UserBO buildUserBO(String mobile, String name, String createName, String sourceEndType, String ip) {
        //组织扩展表信息
        UserExtendsBO userExtendsBO = new UserExtendsBO();
        userExtendsBO.setMobile(mobile);
        userExtendsBO.setCreateName(createName);
        if(StringUtils.isNotBlank(name)) {
            userExtendsBO.setName(name);
        }
        //组织用户表信息
        UserBO userBO = new UserBO();
        userBO.setUserName(mobile);
        userBO.setCreateName(createName);
        userBO.setIp(ip);
        userBO.setIpAddress(AddressUtils.getAddresses(userBO.getIp()));
        userBO.setBusinessDetails("G端保单");
        userBO.setRoleCode(new String[]{RoleUserTypeRelatedEnum.工保网普通用户角色.getRoleCode()});
        userBO.setTypeValueCode(new String[]{RoleUserTypeRelatedEnum.工保网普通用户角色.getUserTypeValueCode()});
        userBO.setPassword(UniversalConstant.UNIVERSAL_PD);
        //来源配置表判断
        SourceVO sourceVO = sourceService.getOneEnhance(new SourceQuery(){{
            setLabel(sourceEndType);
        }});
        SourceValueVO sourceValueVO = sourceValueService.getOneEnhance(new SourceValueQuery(){{
            setCode(SourceValueEnum.后台注册.getCode());
        }});
        if(Objects.isNull(sourceVO) || Objects.isNull(sourceValueVO)) {
            log.error("sourceEndType-lable-{}，来源表或来源值表配置异常！mobile:{}，name:{}，没有找到对应source！", sourceEndType, mobile, name);
        } else {
            userBO.setSourceId(sourceVO.getId());
            userBO.setSourceValueId(sourceValueVO.getId());
        }
        userBO.setUserExtendsBO(userExtendsBO);
        return userBO;
    }
}
