package com.gb.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.account.entity.query.UserExtendsQuery;
import com.gb.account.entity.query.UserQuery;
import com.gb.account.entity.query.UserTypeValueQuery;
import com.gb.account.entity.query.UserTypeValueRelationshipQuery;
import com.gb.account.entity.vo.UserExtendsVO;
import com.gb.account.entity.vo.UserTypeValueRelationshipVO;
import com.gb.account.entity.vo.UserTypeValueVO;
import com.gb.account.entity.vo.UserVO;
import com.gb.account.service.UserExtendsService;
import com.gb.account.service.UserService;
import com.gb.account.service.UserTypeValueRelationshipService;
import com.gb.account.service.UserTypeValueService;
import com.gb.log.entity.RequestLog;
import com.gb.log.entity.RequestLogQuery;
import com.gb.log.service.RequestLogService;
import com.gb.rpc.InsuranceRpc;
import com.gb.rpc.ProductRpc;
import com.gb.user.entity.UserAgentCertification;
import com.gb.user.entity.bo.UserBatchQueryBO;
import com.gb.user.entity.query.UserInfoQuery;
import com.gb.user.entity.vo.UserBasicInfoVO;
import com.gb.user.entity.vo.UserLabelInfoVO;
import com.gb.user.entity.vo.UserLogVO;
import com.gb.user.service.UserAgentCertificationService;
import com.gb.user.service.UserQueryService;
import com.gb.utils.AddressUtils;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.Json;
import com.gb.utils.JsonUtil;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.enumeration.ReturnCode;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.RpcException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * <p>
 * 用户中心查询接口控制器接口实现类
 * </p>
 *
 * @author sunx
 * @since 2021-03-17
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserQueryServiceImpl implements UserQueryService {

    private UserService userService;

    private UserExtendsService userExtendsService;

    private UserTypeValueService userTypeValueService;

    private UserAgentCertificationService userAgentCertificationService;

    private InsuranceRpc insuranceRpc;

    private ProductRpc productRpc;

    private UserTypeValueRelationshipService userTypeValueRelationshipService;

    private RequestLogService requestLogService;

    @Override
    public Map<String, Object> queryEnterpriseInfoByUser(Integer current, Integer size, String userTypeCode, String userNameList, String enterpriseId) {
        if(Objects.isNull(userTypeCode)){
            return Maps.newHashMap();
        }
        try {
            //1、用户标签码值1：经纪人，2：普通用户，3、保险公司
            Json resultJson = new Json();
            resultJson.setCode(ReturnCode.成功.getState());
            switch (userTypeCode){
                case "2":
                    log.debug("开始调用投保项目企业RPC...");
                    if(StringUtils.isNotBlank(userNameList)){
                        resultJson = insuranceRpc.selectBatch(userNameList);
                    }
                    log.debug("调用投保项目企业RPC结束.");
                    break;
                case "3":
                    log.debug("开始调用产品保险企业RPC...");
                    resultJson = productRpc.selectBatch(current, size, userNameList, enterpriseId);
                    log.debug("调用产品保险企业RPC结束.");
                    break;
                default:
                    break;
            }
            if (!resultJson.getCode().equals(ReturnCode.成功.getState())) {
                log.error("查询用户对应的企业信息失败");
                throw new BusinessException(resultJson.getErrorMessage());
            }
            if (Objects.nonNull(resultJson.getObj())) {
                return JSONObject.parseObject(JSON.toJSONString(resultJson.getObj()), Map.class);
            }
            log.debug("用户没有对应的企业关联！");
            return Maps.newHashMap();
        } catch (Exception e) {
            log.error("远程调用查询人员关联企业异常：", e);
            String errorMsg = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "远程调用查询人员关联企业异常";
            throw new RpcException(errorMsg);
        }
    }

    @Override
    public Map<String, List<UserBasicInfoVO>> groupQueryUserInfoByUserIdList(UserBatchQueryBO bo) {
        List<UserVO> userVOList = userService.listEnhance(new UserQuery(){{
            setUserIdList(bo.getUserIdList());
            setUserTypeCode(bo.getUserTypeCode());
            setQueryUserExtendsInfo(true);
        }});
        if(CollectionUtils.isEmpty(userVOList)){
            return Maps.newHashMap();
        }
        List<UserBasicInfoVO> userBasicInfoVoList = buildUserBasicInfoVO(userVOList);
        return userBasicInfoVoList.stream().collect(Collectors.groupingBy(UserBasicInfoVO:: getUserId));
    }

    @Override
    public Map<String, Object> queryUserEnterpriseInfoList(List<UserVO> userVOList, UserQuery userQuery) {
        String userIdListStr = null;
        if(CollectionUtils.isNotEmpty(userVOList)){
            List<String> userIdList = userVOList.stream().map(u -> u.getId()).collect(Collectors.toList());
            userIdListStr = StringUtils.join(userIdList, ",");
        }
        return queryEnterpriseInfoByUser(null, null, userQuery.getUserTypeCode(), userIdListStr, userQuery.getEnterpriseId());
    }

    @Override
    public List<UserLabelInfoVO> queryLabelInUserByTypeParams(UserInfoQuery userInfoQuery) throws Exception {
        List<UserTypeValueVO> userTypeValueVOList = userTypeValueService.listEnhance(new UserTypeValueQuery(){{
            setCode(userInfoQuery.getUserTypeValueCode());
            setUserTypeCode(userInfoQuery.getUserTypeCode());
            setId(userInfoQuery.getUserTypeValueId());
            setUserTypeId(userInfoQuery.getUserTypeId());
        }});
        if(Objects.isNull(userTypeValueVOList)){
            log.debug("userInfoQuery：{}，未找到对应user_type_value配置信息！", JsonUtil.json(userInfoQuery));
            return Lists.newArrayList();
        }
        List<UserLabelInfoVO> userLabelInfoVOList = Lists.newArrayList();
        Map<String, List<UserTypeValueVO>> userTypeValueCodeGroup = userTypeValueVOList.stream().collect(Collectors.groupingBy(UserTypeValueVO::getCode));
        for (Map.Entry<String, List<UserTypeValueVO>> entry : userTypeValueCodeGroup.entrySet()) {
            UserLabelInfoVO userLabelInfoVO = new UserLabelInfoVO();
            userLabelInfoVO.setUserTypeValueName(entry.getValue().get(0).getName());
            userLabelInfoVO.setUserTypeValueCode(entry.getKey());
            userLabelInfoVO.setUserTypeValueId(entry.getValue().get(0).getId());
            List<UserTypeValueRelationshipVO> userTypeValueRelationshipVOList = userTypeValueRelationshipService.listEnhance(new UserTypeValueRelationshipQuery(){{
                setUserTypeValueCode(entry.getKey());
            }});
            if(CollectionUtils.isNotEmpty(userTypeValueRelationshipVOList)){
                List<String> userIdList = userTypeValueRelationshipVOList.stream().map(s->s.getUserId()).collect(Collectors.toList());
                List<UserVO> userVOList = userService.listEnhance(new UserQuery(){{
                    setUserIdList(userIdList);
                    setQueryUserExtendsInfo(true);
                }});
                if(CollectionUtil.isNotEmpty(userVOList)){
                    if(userInfoQuery.getQueryType().equals(1)){
                        userLabelInfoVO.setUserBasicInfoVOList(queryCertUserInfo(userVOList));
                    }else{
                        userLabelInfoVO.setUserBasicInfoVOList(buildUserBasicInfoVO(userVOList));
                    }
                }
            }
           userLabelInfoVOList.add(userLabelInfoVO);
       }
        return userLabelInfoVOList;
    }

    @Override
    public Page<UserLogVO> pageEnhance(String userId, Integer pageNumber, Integer pageSize) {
        RequestLogQuery requestLogQuery = new RequestLogQuery();
        requestLogQuery.setFieldDesc("createDateTime");
        if(StringUtils.isNotBlank(userId)){
            long uId = Long.parseLong(userId);
            requestLogQuery.setUserId(uId);
            requestLogQuery.setRequestLog(new RequestLog(){{
                setUserId(uId);
            }});
        }
        Page<UserLogVO> userLogVoPage = new Page<UserLogVO>(pageNumber, pageSize);
        Page<RequestLog> logPage = requestLogService.pageEnhance(requestLogQuery, pageNumber, pageSize);
        if(logPage.getRecords().size() < 1){
            log.debug("userId：{}，用户日志为空！", userId);
            return userLogVoPage;
        }
        List<UserLogVO> userLogVOList = Lists.newArrayList();
        logPage.getRecords().forEach(s->{{
            UserLogVO userLogVO = new UserLogVO();
            userLogVO.setId(String.valueOf(s.get_id()));
            if (StringUtils.isNotBlank(s.getIp()) && !StringUtils.equals(s.getIp(), UniversalConstant.NULL_STR)) {
                userLogVO.setIp(s.getIp());
            }
            if (StringUtils.isNotBlank(userLogVO.getIp())) {
                userLogVO.setBaiduArea(AddressUtils.getAddresses(userLogVO.getIp()));
                if (StringUtils.contains(userLogVO.getBaiduArea(), UniversalConstant.V_SEPARATOR)) {
                    String[] areaArray = StringUtils.split(userLogVO.getBaiduArea(), UniversalConstant.V_SEPARATOR);
                    //省
                    String provinceName = areaArray[1];
                    if (StringUtils.equals(provinceName, UniversalConstant.NONE_STR)) {
                        provinceName = null;
                    }
                    userLogVO.setProvinceName(provinceName);
                    //市
                    String cityName = areaArray[2];
                    if (StringUtils.equals(cityName, UniversalConstant.NONE_STR)) {
                        cityName = null;
                    }
                    userLogVO.setCityName(cityName);
                    //区
                    String areaName = areaArray[3];
                    if (StringUtils.equals(areaName, UniversalConstant.NONE_STR)) {
                        areaName = null;
                    }
                    userLogVO.setAreaName(areaName);
                }
            }
            userLogVO.setCreateDateTime(s.getCreateDateTime());
            userLogVOList.add(userLogVO);
        }});
        userLogVoPage.setTotal(logPage.getTotal());
        userLogVoPage.setRecords(userLogVOList);
        return userLogVoPage;
    }

    @Override
    public void verifyIdentity(String mobile, String userId) {
        UserExtendsVO userExtendsVO = userExtendsService.getOneEnhance(new UserExtendsQuery(){{
            setMobile(mobile);
        }});
        if(Objects.isNull(userExtendsVO) || !StringUtils.equals(userExtendsVO.getUserId(), userId)){
            throw new BusinessException("校验的手机号身份信息与登录的手机号身份信息不一致！");
        }
    }

    /**
     * 组织用户基本信息VO
     *
     * @author sunx
     * @since 2021-05-25
     * @param userVOList:
     * @return  List<UserBasicInfoVo>
     */
    private List<UserBasicInfoVO> buildUserBasicInfoVO(List<UserVO> userVOList) {
        if(CollectionUtils.isEmpty(userVOList)) {
            return Lists.newArrayList();
        }
        List<UserBasicInfoVO> userBasicInfoVoList = Lists.newArrayList();
        userVOList.forEach(u ->{
            UserBasicInfoVO userBasicInfoVo = new UserBasicInfoVO();
            if(Objects.nonNull(u.getUserExtends())){
                userBasicInfoVo = GeneralConvertor.convertor(u.getUserExtends(), UserBasicInfoVO.class);
            }
            userBasicInfoVo.setUserName(u.getUserName());
            userBasicInfoVoList.add(userBasicInfoVo);
        });
        return userBasicInfoVoList;
    }

    /**
     * 查询认证的用户信息
     *
     * @author sunx
     * @since 2021-05-25
     * @param userVOList:
     * @return  List<UserBasicInfoVo>
     */
    private List<UserBasicInfoVO> queryCertUserInfo(List<UserVO> userVOList) {
        if(CollectionUtils.isEmpty(userVOList)) {
            return Lists.newArrayList();
        }
        Map<String, List<UserVO>> userIdMap = userVOList.stream().collect(Collectors.groupingBy(UserVO::getId));
        List<UserAgentCertification> userAgentCertificationList = userAgentCertificationService.listEnhance(new UserAgentCertification(){{
            setUserIdList(Lists.newArrayList(userIdMap.keySet()));
            setState(1);
        }});
        if(CollectionUtil.isEmpty(userAgentCertificationList)){
            return Lists.newArrayList();
        }
        List<UserBasicInfoVO> userBasicInfoVOList = Lists.newArrayList();
        userAgentCertificationList.forEach(a->{{
            if(StringUtils.isNotBlank(a.getUserId()) && userIdMap.containsKey(a.getUserId())){
                UserVO userVO = userIdMap.get(a.getUserId()).get(0);
                UserBasicInfoVO userBasicInfoVo = new UserBasicInfoVO();
                if(Objects.nonNull(userVO.getUserExtends())) {
                    userBasicInfoVo = GeneralConvertor.convertor(userVO.getUserExtends(), UserBasicInfoVO.class);
                }
                userBasicInfoVo.setUserName(a.getUserId());
                userBasicInfoVOList.add(userBasicInfoVo);
            }
        }});
        return userBasicInfoVOList;
    }
}
