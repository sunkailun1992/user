package com.gb.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gb.account.entity.bo.UserBO;
import com.gb.account.entity.bo.UserExtendsBO;
import com.gb.account.entity.bo.UserTypeValueRelationshipBO;
import com.gb.account.entity.query.*;
import com.gb.account.entity.vo.*;
import com.gb.account.service.*;
import com.gb.aliyun.sms.SmsUtils;
import com.gb.bean.GongBaoConfig;
import com.gb.bean.GongBaoUserConfig;
import com.gb.mq.crm.RegistUserEvent;
import com.gb.permissions.entity.query.SystemQuery;
import com.gb.permissions.entity.vo.SystemVO;
import com.gb.permissions.service.SystemService;
import com.gb.user.entity.UserAgentCertification;
import com.gb.user.entity.bo.UserBasicInfoBO;
import com.gb.user.entity.bo.UserInfoBO;
import com.gb.user.entity.bo.UserRegisterBO;
import com.gb.user.entity.model.request.*;
import com.gb.user.entity.model.response.body.UserInfoQueryResponseBody;
import com.gb.user.entity.model.response.body.UserResponseBody;
import com.gb.user.entity.query.TeamUserQuery;
import com.gb.user.entity.vo.TeamUserVO;
import com.gb.user.enums.ForwardProcesEnum;
import com.gb.user.enums.MqNoticeTypeEnum;
import com.gb.user.enums.RoleUserTypeRelatedEnum;
import com.gb.user.handle.CommonHandle;
import com.gb.user.service.GbtService;
import com.gb.user.service.GbtTransferProcessService;
import com.gb.user.service.TeamUserService;
import com.gb.user.service.UserAgentCertificationService;
import com.gb.utils.AddressUtils;
import com.gb.utils.IpUtils;
import com.gb.utils.JsonUtil;
import com.gb.utils.JwtUtil;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.enumeration.*;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.ParameterNullException;
import com.gb.utils.exception.UserException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gb.account.entity.enums.UserFormalStateEnum.注销;
import static com.gb.user.enums.ForwardProcesEnum.*;

;

/**
 * <p>
 * 请求工保通服务中转处理服务实现类
 * </p>
 *
 * @author sunx
 * @since 2021-03-15
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class GbtTransferProcessServiceImpl implements GbtTransferProcessService {

    private CacheUserService cacheUserService;

    private UserService userService;

    private UserExtendsService userExtendsService;

    private GbtService gbtService;

    private UserAgentCertificationService agentService;

    private UserTypeValueService userTypeValueService;

    private UserTypeValueRelationshipService userTypeValueRelationshipService;

    private SystemService systemService;

    private TeamUserService teamUserService;

    private SourceService sourceService;

    private SourceValueService sourceValueService;

    private GongBaoUserConfig gongBaoUserConfig;

    @Override
    public UserInfoBO login(ForwardProcesEnum forwardProcesEnum, HttpServletRequest httpServletRequest, UserLoginRequest userLoginRequest) {
        log.debug("{}，请求参数：{}", forwardProcesEnum.getDesc(), JSON.toJSONString(userLoginRequest));
        gbtService.initUserRequest(httpServletRequest, userLoginRequest);
        UserVO userVO = verifyLoginAccount(userLoginRequest, forwardProcesEnum);
        if(Objects.isNull(userVO) && StringUtils.isBlank(userLoginRequest.getAppCode())) {
            userLoginRequest.setAppCode(AppCodeEnum.NET_USER.getCode());
            log.debug("请求参数未设置appCode且工保网不存在相关用户用户信息，设置appCode为：{}", userLoginRequest.getAppCode());
        }
        String token  = StringUtils.EMPTY;
        UserBasicInfoBO basicInfoBO;
        StringBuilder traffic = new StringBuilder();
        try {
            BaseUserRequest userBasicRequest = null;
            if(forwardProcesEnum.equals(登录)) {
                String busTypeStr = UniversalConstant.MOBILE.toUpperCase();
                GbtLoginRequest gbtLoginRequest = BeanUtil.copyProperties(userLoginRequest, GbtLoginRequest.class);
                if(StringUtils.equals(userLoginRequest.getBusType(), busTypeStr)) {
                    traffic.append(userLoginRequest.getPhone());
                }else {
                    gbtLoginRequest.setAccount(userLoginRequest.getUserName());
                    traffic.append(userLoginRequest.getUserName());
                }
                traffic.append("-").append(userLoginRequest.getPassword());
                String busType = (StringUtils.isNotBlank(gbtLoginRequest.getAccount()) ? "ACCOUNT" : busTypeStr);
                gbtLoginRequest.setBusType(busType);
                userBasicRequest = gbtLoginRequest;
            } else {
                userBasicRequest = BeanUtil.copyProperties(userLoginRequest, GbtQuickLoginRequest.class);
                traffic.append(userLoginRequest.getPhone()).append("-").append(userLoginRequest.getVerifyCode());
            }
            String respObject = JSON.toJSONString(gbtService.callRemoteGbtService(forwardProcesEnum, userBasicRequest));
            if (StringUtils.isBlank(respObject)) {
                throw new UserException(ReturnCode.用户请求服务异常, forwardProcesEnum.getDesc() + "异常，获取token为空！");
            }
            UserResponseBody responseBody = JSONObject.parseObject(respObject, UserResponseBody.class);
            token = responseBody.getToken();
            userBasicRequest.setUserId(responseBody.getUserCode());
            userBasicRequest.setToken(responseBody.getToken());
            basicInfoBO = queryUserBasicInfo(null, userBasicRequest);
            if(forwardProcesEnum.equals(快速登录) && Objects.isNull(userVO)) {
                RegistUserEvent event = new RegistUserEvent()
                        .setUserId(String.valueOf(basicInfoBO.getId()))
                        .setUserType(0)
                        .setMobile(basicInfoBO.getMobile())
                        .setName(basicInfoBO.getName())
                        .setAgentUserId(userLoginRequest.getAgentUserId());
//                        .setInviteUserId(userLoginRequest.getInviteUserId())
//                        .setBusinessDetails(basicInfoBO.getBusinessDetails());
                MqNoticeTypeEnum.CRM_CLUE_MQ.pushMqMessage(forwardProcesEnum.getDesc(), event);
            }
        }catch (UserException e) {
            if (!e.getReturnCode().equals(ReturnCode.用户请求服务异常)) {
                throw e;
            }
            if (!StringUtils.equals(userLoginRequest.getSourceCode(), SystemSourceEnum.GB_N.getCode()) && !StringUtils.equals(userLoginRequest.getSourceCode(), SystemSourceEnum.APP.getCode())) {
                throw e;
            }
            if (StringUtils.isNotBlank(token) && Objects.isNull(userVO)) {
                log.debug("快速登录，工保通服务异常，本地用户信息不存在，抛出工保通的错误信息！");
                throw e;
            }
            //本地生成token
            Map<String, Object> hashMap = Maps.newHashMap();
            hashMap.put("traffic", traffic);
            token = JwtUtil.createJwt(userVO.getId(), SystemSourceEnum.GB_N.getCode(), hashMap);
            basicInfoBO =  new UserBasicInfoBO().
                    setUserName(userVO.getUserName()).
                    setId(Long.parseLong(userVO.getId()))
                    .setAppCode(userLoginRequest.getAppCode())
                    .setMobile(userVO.getMobile())
                    .setSourceCode(userLoginRequest.getSourceCode())
                    .setBusinessDetails(userLoginRequest.getBusinessDetails());
            log.debug("{}，工保通服务异常，本地用户信息存在，jwt使用traffic为：{}，生成的token为：{}", forwardProcesEnum, traffic, token);
        }
        basicInfoBO.setPassword(userLoginRequest.getPassword());
        return new UserInfoBO().
                setUserBasicInfoBO(basicInfoBO).
                setToken(token);
    }

    @Override
    public UserInfoBO register(HttpServletRequest httpServletRequest, UserRegisterBO bo) {
        //1、初始化工保通注册的请求信息
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setPhone(bo.getPhone()).setVerifyCode(bo.getSmsCode()).setPassword(bo.getPassword()).setAccount(bo.getUserName()).setAppCode(bo.getAppCode());
        gbtService.initUserRequest(httpServletRequest, userRegisterRequest);
        if(StringUtils.isBlank(userRegisterRequest.getBusinessDetails()) || StringUtils.isBlank(userRegisterRequest.getSourceValueCode())) {
            throw new UserException(ReturnCode.请求必填参数为空, "请求头缺少必传参数！");
        }
        UserVO userVO = userService.getOneEnhance(new UserQuery(){{
            setMobile(userRegisterRequest.getPhone());
        }});
        boolean flag = Objects.nonNull(userVO) &&  (CollectionUtils.isNotEmpty(userVO.getGroupList()) || CollectionUtils.isNotEmpty(userVO.getRoleList()));
        if(flag) {
            List<String> allSystemIdList = Lists.newArrayList();
            if(CollectionUtils.isNotEmpty(userVO.getRoleList())) {
                List<String> systemIdList = userVO.getRoleList().stream().map(r -> r.getSystemId()).distinct().collect(Collectors.toList());
                allSystemIdList.addAll(systemIdList);
            }
            if(CollectionUtils.isNotEmpty(userVO.getGroupList())) {
                List<String> systemIdList = userVO.getGroupList().stream().map(r -> r.getSystemId()).distinct().collect(Collectors.toList());
                allSystemIdList.addAll(systemIdList);
            }
            List<SystemVO> systemVOList = systemService.listEnhance(new SystemQuery(){{
                setSystemIdList(allSystemIdList);
            }});
            if(CollectionUtils.isNotEmpty(systemVOList)) {
                List<String> appCodeList = systemVOList.stream().map(s -> s.getCode()).distinct().collect(Collectors.toList());
                if(CommonHandle.filterSourceList.contains(userRegisterRequest.getSourceCode())) {
                    boolean validateFlag = appCodeList.stream().anyMatch(a -> StringUtils.equalsIgnoreCase(a, AppCodeEnum.NET_INS.getCode())
                            || StringUtils.equalsIgnoreCase(a, AppCodeEnum.NET_AGENT.getCode()));
                    if(validateFlag){
                        log.error("手机号：{}，已经授权了【{}或{}】！", userRegisterRequest.getPhone(), AppCodeEnum.NET_AGENT.getDesc(), AppCodeEnum.NET_INS.getCode());
                        throw new UserException(ReturnCode.工保网经纪人用户, GongBaoConfig.gongbaoJinJumpUrl);
                    }
                } else {
                    boolean validateFlag = appCodeList.stream().anyMatch(a -> StringUtils.equalsIgnoreCase(a, AppCodeEnum.NET_USER.getCode())
                            || StringUtils.equalsIgnoreCase(a, AppCodeEnum.NET_AGENT.getCode()));
                    if(validateFlag && (!StringUtils.equalsIgnoreCase(userRegisterRequest.getAppCode(), AppCodeEnum.NET_INS.getCode()))){
                        log.error("手机号：{}，已经注册成为了经纪人或者普通用户！【授权的平台信息为：{}】", bo.getPhone(), JsonUtil.json(systemVOList));
                        throw new UserException(ReturnCode.该手机号已注册, ReturnCode.该手机号已注册.getName());
                    }
                }
            }
        }
        //3、开始调用工保通注册接口
        UserResponseBody responseBody = JSONObject.parseObject(JSON.toJSONString(gbtService.callRemoteGbtService(注册, userRegisterRequest)), UserResponseBody.class);
        //4、根据userCode获取用户信息
        UserUniversalRequest queryRequest = BeanUtil.copyProperties(userRegisterRequest, UserUniversalRequest.class);
        queryRequest.setUserId(responseBody.getUserCode());
        queryRequest.setToken(responseBody.getToken());
        UserBasicInfoBO basicInfoBO = queryUserBasicInfo(null, queryRequest);
        basicInfoBO.setPassword(bo.getPassword());
        //5、MQ通知CRM系统
        addUserNoticeCrm(basicInfoBO, bo.getAgentUserId(), true, null);
        //6、组织结果
        return new UserInfoBO(){{
            setToken(responseBody.getToken());
            setUserBasicInfoBO(basicInfoBO);
        }};
    }
    @Override
    public UserBasicInfoBO queryUserBasicInfo(HttpServletRequest httpServletRequest, BaseUserRequest userBasicRequest) {
        gbtService.initUserRequest(httpServletRequest, userBasicRequest);
        UserSingleInfoQueryRequest userSingleInfoQueryRequest = BeanUtil.copyProperties(userBasicRequest, UserSingleInfoQueryRequest.class);
        userSingleInfoQueryRequest.setToken(userBasicRequest.getToken());
        userSingleInfoQueryRequest.setUserId(userBasicRequest.getUserId());
        userSingleInfoQueryRequest.setUserCode(userBasicRequest.getUserId());
        //查询工保通，获取用户信息
        String respObject = JSON.toJSONString(gbtService.callRemoteGbtService(单个用户信息查询, userSingleInfoQueryRequest));
        if (StringUtils.isBlank(respObject) || StringUtils.equals(respObject, UniversalConstant.NULL_JSON)) {
            log.error("【工保通响应】用户信息获取为空！token：{}，userCode：{}", userBasicRequest.getToken(), userBasicRequest.getUserId());
            throw new UserException(ReturnCode.用户账户不存在, "账户信息不存在！");
        }
        UserInfoQueryResponseBody body = JSONObject.parseObject(respObject, UserInfoQueryResponseBody.class);
        UserBasicInfoBO basicInfoBO = gbtService.buildUserBasicInfoBO(body);
        basicInfoBO.setSourceCode(userBasicRequest.getSourceCode());
        basicInfoBO.setSourceId(userBasicRequest.getSourceId());
        basicInfoBO.setSourceValueId(userBasicRequest.getSourceValueId());
        if (StringUtils.isNotBlank(userBasicRequest.getAppCode())) {
            basicInfoBO.setAppCode(userBasicRequest.getAppCode());
        }
        //设置IP
        if(Objects.isNull(httpServletRequest)) {
            basicInfoBO.setIp(userBasicRequest.getIpAddress());
        }else {
            basicInfoBO.setIp(IpUtils.getIp(httpServletRequest));
        }
        basicInfoBO.setBusinessDetails(userBasicRequest.getBusinessDetails());
        return basicInfoBO;
    }

    @Override
    public void updateUserInfo(HttpServletRequest httpServletRequest, UserBO bo) {
        //1、组织请求工保通用户信息修改接口参数
        UserInfoUpdateRequest userInfoUpdateRequest = new UserInfoUpdateRequest();
        userInfoUpdateRequest.setUpdateType(bo.getUpdateType());
        userInfoUpdateRequest.setUserCode(bo.getId());
        userInfoUpdateRequest.setAccount(bo.getUserName());
        if (Objects.nonNull(bo.getIsAccountLocked())) {
            int status = (bo.getIsAccountLocked() ? 1 : 0);
            userInfoUpdateRequest.setStatus(status);
        }
        UserExtendsBO userExtendsBO = bo.getUserExtendsBO();
        if(Objects.nonNull(userExtendsBO)) {
            userInfoUpdateRequest.setRealName(userExtendsBO.getName());
            userInfoUpdateRequest.setNickname(userExtendsBO.getAlias());
            userInfoUpdateRequest.setEmail(userExtendsBO.getEmail());
            userInfoUpdateRequest.setAddress(userExtendsBO.getAddress());
            userInfoUpdateRequest.setCityCode(userExtendsBO.getCityCode());
            String birthday = userExtendsBO.getBirthdayStr();
            if(StringUtils.isNotBlank(birthday) && LenEnum.NORMAL_DATE_LEN.getLen().equals(birthday.length())) {
                birthday = birthday.concat(" 00:00:00");
            }
            userInfoUpdateRequest.setBirthday(birthday);
            if(Objects.nonNull(userExtendsBO.getSex())) {
                int sex = userExtendsBO.getSex();
                if(sex == 0){
                    sex = 1;
                }else if(sex == 1) {
                    sex = 2;
                }else {
                    sex = 0;
                }
                userInfoUpdateRequest.setSex(sex);
            }
            userInfoUpdateRequest.setPhone(userExtendsBO.getMobile());
        }
        //2、调用工保通用户用户信息更新接口
        dealWith(用户信息更新, httpServletRequest, userInfoUpdateRequest);
    }

    @Override
    public UserBO saveUserInfo(HttpServletRequest httpServletRequest, UserBO bo) {
        //1、组织请求工保通参数
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
        //2、调用工保通用户用户信息新增接口
        UserInfoQueryResponseBody body = JSONObject.parseObject(JSON.toJSONString(dealWith(用户信息新增, httpServletRequest, userInfoSaveRequest)), UserInfoQueryResponseBody.class);
        bo.setId(body.getUserCode());
        bo.setSourceId(userInfoSaveRequest.getSourceId());
        bo.setSourceValueId(userInfoSaveRequest.getSourceValueId());
        //5、MQ通知CRM系统
        String appCode = StringUtils.EMPTY;
        if(Objects.nonNull(bo.getTypeValueId())) {
            List<UserTypeValueVO> userTypeValueVOList = userTypeValueService.listEnhance(new UserTypeValueQuery(){{
                setIdList(StringUtils.join(bo.getTypeValueId(), ","));
            }});
            if(CollectionUtils.isNotEmpty(userTypeValueVOList)) {
                userTypeValueVOList = userTypeValueVOList.stream().filter(s->!StringUtils.equals(s.getUserTypeCode(), String.valueOf(NumericEnum.TWO.getValue()))).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(userTypeValueVOList)) {
                    boolean flag = userTypeValueVOList.stream().anyMatch(t-> StringUtils.equals(t.getUserTypeCode(), String.valueOf(NumericEnum.ONE.getValue())) || StringUtils.equals(t.getUserTypeCode(), String.valueOf(NumericEnum.FOUR.getValue())));
                    if(flag) {
                        appCode = AppCodeEnum.NET_AGENT.getCode();
                        bo.setRoleCode(new String[]{RoleUserTypeRelatedEnum.非正式经纪人.getRoleCode()});
                    }else{
                        appCode = AppCodeEnum.NET_INS.getCode();
                        bo.setRoleCode(new String[]{RoleUserTypeRelatedEnum.工保网保险公司角色.getRoleCode()});
                    }
                } else {
                    appCode = AppCodeEnum.NET_USER.getCode();
                    bo.setRoleCode(new String[]{RoleUserTypeRelatedEnum.工保网普通用户角色.getRoleCode()});
                }
            }
        }
        UserBasicInfoBO basicInfoBO = gbtService.buildUserBasicInfoBO(body);
        basicInfoBO.setAppCode(appCode);
        addUserNoticeCrm(basicInfoBO, null, false, bo.getCreateName());
        bo.setIp(IpUtils.getIp(httpServletRequest));
        bo.setIpAddress(AddressUtils.getAddresses(bo.getIp()));
        bo.setPassword(userInfoSaveRequest.getPassword());
        bo.setBusinessDetails(userInfoSaveRequest.getBusinessDetails());
        return bo;
    }

    @Override
    public String sendCode(HttpServletRequest httpServletRequest, UserSendCodeRequest userSendCodeRequest, Boolean showSmsCode) {
        if (StringUtils.isBlank(userSendCodeRequest.getPhone()) && StringUtils.isBlank(userSendCodeRequest.getEmail())) {
            log.error("发送短信错误：手机号或邮箱必要一个参数！");
            throw new UserException(ReturnCode.请求必填参数为空, "缺少必要参数！");
        }
        if(StringUtils.isBlank(userSendCodeRequest.getVerifyType())) {
            userSendCodeRequest.setVerifyType("PHONE_REGISTER");
        }
        gbtService.initUserRequest(httpServletRequest, userSendCodeRequest);
        if(Objects.isNull(showSmsCode)) {
            if(userSendCodeRequest.getSourceCode().equals(SystemSourceEnum.GB_N.getCode())) {
                if(Objects.nonNull(httpServletRequest) && !userSendCodeRequest.getSourceValueCode().equals(SourceValueEnum.信息收集表单.getCode())) {
                    showSmsCode = false;
                }else {
                    showSmsCode = true;
                }
            }else {
                showSmsCode = true;
            }
        }
        String smsCode = (String)gbtService.callRemoteGbtService(短信验证码, userSendCodeRequest);
        if (showSmsCode) {
            return smsCode;
        }
        return "验证码已成功发送，请注意查收！";
    }

    @Override
    public UserInfoBO freePasswordLogin(HttpServletRequest httpServletRequest, String verifyType, String mobile, Boolean showSmsCode) {
        log.debug("免密登录，请求参数：【verifyType：{}，mobile：{}，showSmsCode】", verifyType, mobile, showSmsCode);
        UserSendCodeRequest userSendCodeRequest = new UserSendCodeRequest();
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        //1、发送短信验证码
        userSendCodeRequest.setPhone(mobile);
        userSendCodeRequest.setVerifyType(verifyType);
        userSendCodeRequest.setNoSendCode(true);
        userSendCodeRequest.setNeedReturnCode(true);
        if(StringUtils.isNotBlank(httpServletRequest.getHeader(UniversalConstant.TOKEN))) {
            userSendCodeRequest.setIpAddress(IpUtils.getIp(httpServletRequest));
            userSendCodeRequest.setAppCode(AppCodeEnum.NET_AGENT.getCode());
            userSendCodeRequest.setSourceCode(SystemSourceEnum.GB_N.getCode());
            userLoginRequest.setIpAddress(userSendCodeRequest.getIpAddress());
            if(StringUtils.isNotBlank(httpServletRequest.getHeader(UniversalConstant.BUSINESS_DETAILS))) {
                try{
                    userLoginRequest.setBusinessDetails(URLDecoder.decode(httpServletRequest.getHeader(UniversalConstant.BUSINESS_DETAILS), "UTF-8"));
                }catch (UnsupportedEncodingException e){
                    throw new UserException(ReturnCode.用户请求参数错误, "businessDetails参数decode异常！");
                }
            }
            httpServletRequest = null;
        }
        String smsCode = sendCode(httpServletRequest, userSendCodeRequest, showSmsCode);
        userLoginRequest.setPassword(UniversalConstant.UNIVERSAL_PD);
        userLoginRequest.setVerifyCode(smsCode);
        userLoginRequest.setPhone(mobile);
        //2、调用快速登录接口
        UserInfoBO userInfoBO = login(快速登录, httpServletRequest, userLoginRequest);
        log.debug("免密登录--smsCode：{}--token：{}", smsCode, userInfoBO.getToken());
        return userInfoBO;
    }

    @Override
    public CacheUserInfoVO logOut(HttpServletRequest httpServletRequest, BaseUserRequest userBasicRequest) {
        //调用工保通【登出】接口
        dealWith(登出, httpServletRequest, userBasicRequest);
        //移除缓存
        return cacheUserService.removeEnchane(httpServletRequest.getHeader(UniversalConstant.TOKEN), httpServletRequest.getHeader(UniversalConstant.SOURCECODE));
    }

    @Override
    public void logOff(HttpServletRequest httpServletRequest, BaseUserRequest userBasicRequest) {
        //查询用户基本信息
        UserBasicInfoBO userBasicInfoBO = queryUserBasicInfo(httpServletRequest, userBasicRequest);
        //判断用户是否是渠道经纪人，如果是渠道经纪人，就不给注销
        String userId = String.valueOf(userBasicInfoBO.getId());
        TeamUserVO teamUserVO = teamUserService.getOneEnhance(new TeamUserQuery(){{setUserId(userId);}});
        if(Objects.nonNull(teamUserVO) && teamUserVO.getChannel()) {
            throw new BusinessException("您的账号渠道合作中，请拨打400-800-5100联系客服人员为您注销！");
        }
        //注销用户
        dealWith(注销用户, httpServletRequest, userBasicRequest);
        //登出
        CacheUserInfoVO cacheUserInfoVO = logOut(httpServletRequest, userBasicRequest);
        //修改用户信息及用户状态
        userService.updateEnhance(new UserBO() {{
            setId(userId);
            setUserName(userBasicInfoBO.getUserName());
            setModifyName(cacheUserInfoVO.getName() + "_" + cacheUserInfoVO.getId());
            setUserFormalStateEnum(注销);
            setUserExtendsBO(new UserExtendsBO() {{
                setMobile(userBasicInfoBO.getMobile());
            }});
        }});
        //删除标签信息，因为管家端可能还会分配线索给改账户
        userTypeValueRelationshipService.removeEnhance(new UserTypeValueRelationshipBO() {{
            setUserId(userId);
        }});
        //发送短信通知用户注销用户成功
        SmsUtils.sendMessage(userBasicInfoBO.getMobile(), SmsEnum.用户注销模板, null);
    }


    @Override
    public void updateMobile(HttpServletRequest httpServletRequest, String id, String newMobile, String smsCode) {
        CacheUserInfoVO cacheUserInfoVO = cacheUserService.getOneEnhance(httpServletRequest.getHeader(UniversalConstant.TOKEN));
        if(StringUtils.isBlank(id) || StringUtils.isBlank(newMobile) || StringUtils.isBlank(smsCode) ) {
            throw new ParameterNullException("修改手机号必传参数不能为空!");
        }
        //调用工保通修改手机号接口
        UserInfoUpdateRequest userInfoUpdateRequest = new UserInfoUpdateRequest();
        userInfoUpdateRequest.setPhone(newMobile).setVerifyCode(smsCode).setUpdateType("UPDATE_PHONE");
        dealWith(用户信息更新, httpServletRequest, userInfoUpdateRequest);
        userExtendsService.updateEnhance(new UserExtendsBO(){{
            setModifyName(cacheUserInfoVO.getName() + "-" + cacheUserInfoVO.getId());
            setMobile(newMobile);
            setId(id);
        }}, null, null);
    }

    @Override
    public Object dealWith(ForwardProcesEnum forwardProcesEnum, HttpServletRequest httpServletRequest, BaseUserRequest userBasicRequest) {
        gbtService.initUserRequest(httpServletRequest, userBasicRequest);
        return gbtService.callRemoteGbtService(forwardProcesEnum, userBasicRequest);
    }

    @Override
    public Map<String, String> queryUserIdsByMobiles(HttpServletRequest httpServletRequest, List<Map<String, String>> userInfoList) {
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
            return resultMap;
        }
        //本地不存在的手机号，调用工保通的逻辑进行处理
        registerGbt(resultMap, noExistMobileList, userInfoList, httpServletRequest);
        return resultMap;
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
     * 校验登录账户信息
     *
     * @param userLoginRequest : 用户登录请求
     * @param forwardProcesEnum : 请求类型
     * @return UserVO
     * @author sunx
     * @since 2021-03-04
     */
    private UserVO verifyLoginAccount(UserLoginRequest userLoginRequest, ForwardProcesEnum forwardProcesEnum) {
        log.debug("开始校验用户登录账户信息：请求参数信息：{}", JSON.toJSONString(userLoginRequest));
        if(StringUtils.isBlank(userLoginRequest.getBusinessDetails()) || StringUtils.isBlank(userLoginRequest.getSourceValueCode())) {
            throw new UserException(ReturnCode.请求必填参数为空, "请求头缺少必传参数！");
        }
        if(StringUtils.equals(userLoginRequest.getAppCode(), AppCodeEnum.NET_INS.getCode()) && StringUtils.equals(userLoginRequest.getSourceCode(), SystemSourceEnum.OFFICIAL_ACCOUNT.getCode())) {
            throw new UserException(ReturnCode.无权限使用, "此账号无权限登录，请确认账号是否正确！");
        }
        //1、查询用户相关信息
        UserVO userVO = userService.getOneEnhance(new UserQuery() {{
            setUserName(userLoginRequest.getUserName());
            setMobile(userLoginRequest.getPhone());
        }});
        if (Objects.isNull(userVO)) {
            return userVO;
        }
        //TODO: nacos配置权限排除过滤
        if(StringUtils.equals(userLoginRequest.getAppCode(), AppCodeEnum.NET_BACKEND.getCode()) && StringUtils.isNotBlank(gongBaoUserConfig.getBacPerAccount()) && gongBaoUserConfig.getBacPerAccount().contains(userLoginRequest.getUserName())) {
            return userVO;
        }
        if (CollectionUtils.isEmpty(userVO.getRoleList()) && CollectionUtils.isEmpty(userVO.getGroupList())) {
            log.error("用户角色信息、组信息未配置！");
            throw new UserException(ReturnCode.无权限使用, ReturnCode.无权限使用.getName());
        }
        List<String> allSystemIdList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(userVO.getRoleList())) {
            List<String> systemIdList = userVO.getRoleList().stream().map(r -> r.getSystemId()).distinct().collect(Collectors.toList());
            allSystemIdList.addAll(systemIdList);
        }
        if (CollectionUtils.isNotEmpty(userVO.getGroupList())) {
            List<String> systemIdList = userVO.getGroupList().stream().map(r -> r.getSystemId()).distinct().collect(Collectors.toList());
            allSystemIdList.addAll(systemIdList);
        }
        if (StringUtils.isNotBlank(userLoginRequest.getAppCode()) && forwardProcesEnum.equals(登录)) {
            SystemVO systemVO = systemService.getOneEnhance(new SystemQuery() {{
                setCode(userLoginRequest.getAppCode());
            }});
            if (Objects.isNull(systemVO)) {
                throw new UserException(ReturnCode.无权限使用, "系统未配置！");
            }
            if (!allSystemIdList.contains(systemVO.getId())) {
                log.error("system表中未找到【userId：{}，请求的appCode：{}】用户配置的系统ID！allSystemIdList：{}，requestSystemId：{}",userVO.getId(), userLoginRequest.getAppCode(), JsonUtil.json(allSystemIdList), systemVO.getId());
                throw new UserException(ReturnCode.无权限使用, ReturnCode.无权限使用.getName());
            }
            return userVO;
        } else {
            List<SystemVO> systemVOList = systemService.listEnhance(new SystemQuery(){{
                setSystemIdList(allSystemIdList);
            }});
            if(CollectionUtils.isEmpty(systemVOList)){
                log.error("用户系统信息不包含请求的系统信息！allSystemIdList：{}", JsonUtil.json(allSystemIdList));
                throw new UserException(ReturnCode.无权限使用, "系统配置为空！");
            }
            //4、校验权限
            List<String> appCodeList = systemVOList.stream().filter(s -> !(StringUtils.equalsIgnoreCase(s.getCode(), AppCodeEnum.NET_BACKEND.getCode())
                            || StringUtils.equalsIgnoreCase(s.getCode(), AppCodeEnum.SCRM.getCode())))
                    .map(s -> s.getCode()).distinct().collect(Collectors.toList());
            if(CollectionUtils.isEmpty(appCodeList)) {
                log.error("后台账号无法登录前台！");
                throw new UserException(ReturnCode.无权限使用, ReturnCode.无权限使用.getName());
            }
            //TODO-sunx：非工保网的页面跳转是否和工保金一样
            if (CommonHandle.filterSourceList.contains(userLoginRequest.getSourceCode())) {
                boolean validateFlag = appCodeList.stream().anyMatch(a -> StringUtils.equalsIgnoreCase(a, AppCodeEnum.NET_INS.getCode())
                        || StringUtils.equalsIgnoreCase(a, AppCodeEnum.NET_AGENT.getCode()));
                if (validateFlag) {
                    log.error("手机号：{}，已经授权了【{}或{}】！", userLoginRequest.getPhone(), AppCodeEnum.NET_AGENT.getDesc(), AppCodeEnum.NET_INS.getCode());
                    throw new UserException(ReturnCode.工保网经纪人用户, GongBaoConfig.gongbaoJinJumpUrl);
                }
            }
            if(StringUtils.equals(appCodeList.get(0), AppCodeEnum.NET_INS.getCode()) && StringUtils.equals(userLoginRequest.getSourceCode(), SystemSourceEnum.OFFICIAL_ACCOUNT.getCode())) {
                throw new UserException(ReturnCode.无权限使用, "此账号无权限登录，请确认账号是否正确！");
            }
            userLoginRequest.setAppCode(appCodeList.get(0));
            return userVO;
        }
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
}