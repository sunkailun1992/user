package com.gb.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.account.entity.bo.UserBO;
import com.gb.account.entity.bo.UserExtendsBO;
import com.gb.account.entity.enums.UserExtendsCertificateTypeEnum;
import com.gb.account.entity.enums.UserFormalStateEnum;
import com.gb.account.entity.vo.UserExtendsVO;
import com.gb.account.service.UserExtendsService;
import com.gb.account.service.UserService;
import com.gb.user.entity.InstitutionsUser;
import com.gb.user.entity.UserAgentCertification;
import com.gb.user.entity.bo.InstitutionsUserBO;
import com.gb.user.entity.bo.UserBasicInfoBO;
import com.gb.user.entity.enums.UserAgentCertificationStateEnum;
import com.gb.user.entity.query.InstitutionsUserQuery;
import com.gb.user.entity.vo.InstitutionsUserVO;
import com.gb.user.mapper.InstitutionsUserMapper;
import com.gb.user.service.*;
import com.gb.user.service.query.InstitutionsUserServiceQuery;
import com.gb.user.service.results.InstitutionsUserServiceResults;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.enumeration.LenEnum;
import com.gb.utils.enumeration.ReturnCode;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.ParameterNullException;
import com.gb.utils.exception.UserException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.gb.user.entity.enums.InstitutionsUserStateEnum.离职;


/**
 * TODO 机构用户关联，Service服务实现层
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsUserServiceImpl
 * @time 2022-07-04 10:48:37
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class InstitutionsUserServiceImpl extends ServiceImpl<InstitutionsUserMapper, InstitutionsUser> implements InstitutionsUserService {

    /**
     * 机构用户关联
     */
    private InstitutionsUserMapper institutionsUserMapper;

    /**
     * 机构用户关联
     */
    private InstitutionsUserServiceResults institutionsUserServiceResults;

    /**
     * 机构用户关联增强条件
     */
    private InstitutionsUserServiceQuery institutionsUserServiceQuery;

    /**
     * 机构
     */
    private InstitutionsService institutionsService;

    /**
     * 用户扩展服务
     */
    private UserExtendsService userExtendsService;

    /**
     * 用户服务
     */
    private UserService userService;

    /**
     * 经纪人认证服务
     */
    private UserAgentCertificationService userAgentCertificationService;

    /**
     * 工保通转发处理服务
     */
    private GbtTransferProcessService gbtTransferProcessService;

    /**
     * 认证处理
     */
    private CertificationProcessService certificationProcessService;

    /**
     * TODO 集合
     *
     * @param institutionsUserQuery 机构用户关联
     * @return List<InstitutionsUserVO>
     * @author sunxin
     * @methodName listEnhance
     * @time 2022-07-04 10:48:37
     */
    @Override
    public List<InstitutionsUserVO> listEnhance(InstitutionsUserQuery institutionsUserQuery) {
        InstitutionsUser institutionsUser = GeneralConvertor.convertor(institutionsUserQuery, InstitutionsUser.class);
        QueryWrapper<InstitutionsUser> queryWrapper = new QueryWrapper<>(institutionsUser);
        // TODO 自动生成查询，禁止手动写语句
        institutionsUserServiceQuery.query(institutionsUserQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(institutionsUserQuery, queryWrapper);
        // DO数据
        List<InstitutionsUser> institutionsUserDO = institutionsUserMapper.selectList(queryWrapper);
        // VO数据
        List<InstitutionsUserVO> institutionsUserVO = GeneralConvertor.convertor(institutionsUserDO, InstitutionsUserVO.class);
        return institutionsUserServiceResults.assignment(institutionsUserVO);
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param institutionsUserQuery 机构用户关联
     * @return Page<InstitutionsUserVO>
     * @author sunxin
     * @methodName pageEnhance
     * @time 2022-07-04 10:48:37
     */
    @Override
    public Page<InstitutionsUserVO> pageEnhance(Page page, InstitutionsUserQuery institutionsUserQuery) {
        InstitutionsUser institutionsUser = GeneralConvertor.convertor(institutionsUserQuery, InstitutionsUser.class);
        QueryWrapper<InstitutionsUser> queryWrapper = new QueryWrapper<>(institutionsUser);
        // TODO 自动生成查询，禁止手动写语句
        institutionsUserServiceQuery.query(institutionsUserQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(institutionsUserQuery, queryWrapper);
        // DO数据
        Page<InstitutionsUser> pageDO = institutionsUserMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<InstitutionsUserVO> pageVO = institutionsUserServiceResults.toPageVO(pageDO);
        return institutionsUserServiceResults.assignment(pageVO);
    }


    /**
     * TODO 单条
     *
     * @param institutionsUserQuery 机构用户关联
     * @return InstitutionsUserVO
     * @author sunxin
     * @methodName getOneEnhance
     * @time 2022-07-04 10:48:37
     */
    @Override
    public InstitutionsUserVO getOneEnhance(InstitutionsUserQuery institutionsUserQuery) {
        InstitutionsUser institutionsUser = GeneralConvertor.convertor(institutionsUserQuery, InstitutionsUser.class);
        QueryWrapper<InstitutionsUser> queryWrapper = new QueryWrapper<>(institutionsUser);
        // TODO 自动生成查询，禁止手动写语句
        institutionsUserServiceQuery.query(institutionsUserQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(institutionsUserQuery, queryWrapper);
        // DO数据
        InstitutionsUser institutionsUserDO = institutionsUserMapper.selectOne(queryWrapper);
        // VO数据
        InstitutionsUserVO institutionsUserVO = GeneralConvertor.convertor(institutionsUserDO, InstitutionsUserVO.class);
        return institutionsUserServiceResults.assignment(institutionsUserVO);
    }


    /**
     * TODO 总数
     *
     * @param institutionsUserQuery 机构用户关联
     * @return Integer
     * @author sunxin
     * @methodName countEnhance
     * @time 2022-07-04 10:48:37
     */
    @Override
    public Long countEnhance(InstitutionsUserQuery institutionsUserQuery) {
        InstitutionsUser institutionsUser = GeneralConvertor.convertor(institutionsUserQuery, InstitutionsUser.class);
        QueryWrapper<InstitutionsUser> queryWrapper = new QueryWrapper<>(institutionsUser);
        // TODO 自动生成查询，禁止手动写语句
        institutionsUserServiceQuery.query(institutionsUserQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(institutionsUserQuery, queryWrapper);
        return institutionsUserMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param institutionsUserBO 机构用户关联
     * @return String
     * @author sunxin
     * @methodName saveEnhance
     * @time 2022-07-04 10:48:37
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(HttpServletRequest httpServletRequest, InstitutionsUserBO institutionsUserBO) {
        //1、校验请求参数
        if(StringUtils.isBlank(httpServletRequest.getHeader(UniversalConstant.BUSINESS_DETAILS)) || StringUtils.isBlank(httpServletRequest.getHeader(UniversalConstant.SOURCE_VALUE_CODE))) {
            throw new ParameterNullException("请求头缺少必要参数！");
        }
        UserExtendsBO userExtendsBO = institutionsUserBO.getUserExtendsBO();
        if(StringUtils.isBlank(institutionsUserBO.getInstitutionsId()) || Objects.isNull(institutionsUserBO.getState()) || Objects.isNull(userExtendsBO) || StringUtils.isBlank(userExtendsBO.getMobile()) ||StringUtils.isBlank(userExtendsBO.getName()) || Objects.isNull(userExtendsBO.getCertificateType()) || StringUtils.isBlank(userExtendsBO.getIdCard())
                || StringUtils.isBlank(userExtendsBO.getBirthdayStr()) || Objects.isNull(userExtendsBO.getSex()) || Objects.isNull(userExtendsBO.getNatureWork()) || Objects.isNull(userExtendsBO.getContractType()) || StringUtils.isBlank(userExtendsBO.getOnboardingDateTimeStr())) {
            log.error("缺少必要参数--请求参数：{}", JSON.toJSONString(institutionsUserBO));
            throw new ParameterNullException("缺少必要参数！");
        }
        if(Objects.isNull(userExtendsBO.getCertificatePermanent())) {
            if(StringUtils.isBlank(userExtendsBO.getCertificateStartDateStr()) || StringUtils.isBlank(userExtendsBO.getCertificateEndDateStr())) {
                log.error("缺少必要参数--证件起止与长期；请求参数：{}", JSON.toJSONString(institutionsUserBO));
                throw new ParameterNullException("缺少必要参数！");
            }
        }
        //2、其他参数校验
        validateParams(institutionsUserBO);
        //3、新增用户信息【工保通、本地信息保存，CRM】
        String userId = saveBrokerCertInfo(httpServletRequest, userExtendsBO, institutionsUserBO.getUserAgentCertification(), institutionsUserBO.getCreateName());
        institutionsUserBO.setUserId(userId);
        //4、本地新增关联信息
        InstitutionsUser institutionsUser = GeneralConvertor.convertor(institutionsUserBO, InstitutionsUser.class);
        institutionsUserMapper.insert(institutionsUser);
        return institutionsUser.getId();
    }


    /**
     * TODO 修改
     *
     * @param institutionsUserBO 机构用户关联
     * @return Boolean
     * @author sunxin
     * @methodName updateEnhance
     * @time 2022-07-04 10:48:37
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(HttpServletRequest httpServletRequest, InstitutionsUserBO institutionsUserBO) {
        UpdateWrapper<InstitutionsUser> updateWrapper = new UpdateWrapper<>();
        if(Objects.nonNull(httpServletRequest)) {
            //1、校验请求的机构信息是否存在
            InstitutionsUserVO institutionsUserVO = getOneEnhance(new InstitutionsUserQuery(){{
                setId(institutionsUserBO.getId());
            }});
            if(Objects.isNull(institutionsUserVO)) {
                throw new ParameterNullException("机构用户信息不存在！");
            }
            institutionsUserBO.setUserId(institutionsUserVO.getUserId());
            UserExtendsBO userExtendsBO = institutionsUserBO.getUserExtendsBO();
            UserAgentCertification userAgentCertification = institutionsUserBO.getUserAgentCertification();
            //2、校验经纪人认证参数信息
            if(Objects.nonNull(userAgentCertification)) {
                boolean validateFlag = StringUtils.isNotBlank(userAgentCertification.getCertificateCode()) && (Objects.isNull(userExtendsBO) || StringUtils.isBlank(userExtendsBO.getName()));
                if(validateFlag) {
                    throw new ParameterNullException("缺少认证必要参数！");
                }
            }
            //3、其他参数校验
            validateParams(institutionsUserBO);
            //4、修改用户信息【工保通、本地信息保存，CRM】
            updateBrokerCertInfo(httpServletRequest, new UserBO(){{
                setUserExtendsBO(userExtendsBO);
                setId(institutionsUserBO.getUserId());
                setModifyName(institutionsUserBO.getModifyName());
                setUpdateType("UPDATE_USER");
            }}, userAgentCertification);
            updateWrapper.eq("id", institutionsUserBO.getId());
        }else {
            updateWrapper.eq("user_id", institutionsUserBO.getUserId());
        }
        //5、本地修改关联信息
        InstitutionsUser institutionsUser = GeneralConvertor.convertor(institutionsUserBO, InstitutionsUser.class);
        Integer i = institutionsUserMapper.update(institutionsUser, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 删除
     *
     * @param institutionsUserBO 机构用户关联
     * @return Boolean
     * @author sunxin
     * @methodName removeEnhance
     * @time 2022-07-04 10:48:37
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(InstitutionsUserBO institutionsUserBO) {
        InstitutionsUser institutionsUser = GeneralConvertor.convertor(institutionsUserBO, InstitutionsUser.class);
        QueryWrapper<InstitutionsUser> queryWrapper = new QueryWrapper<>(institutionsUser);
        Integer i = institutionsUserMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    @Override
    public void exportExcel(HttpServletResponse response, InstitutionsUserQuery institutionsUserQuery) {
        List<InstitutionsUserVO> institutionsUserVOList = listEnhance(institutionsUserQuery);
        if(CollectionUtils.isEmpty(institutionsUserVOList)) {
            log.error("无可供导出的数据--经纪人数据不存在！");
            throw new BusinessException("无可供导出的数据！");
        }
        List<Map<String, String>> mapList = Lists.newArrayList();
        for (InstitutionsUserVO vo: institutionsUserVOList) {
            UserExtendsVO userExtendsVO = vo.getUserExtendsVO();
            Map<String, String> map = MapUtil.newHashMap(true);
            String name = Objects.isNull(userExtendsVO) ? StringUtils.EMPTY : StringUtils.defaultString(userExtendsVO.getName());
            map.put("经纪人姓名", name);
            map.put("用户ID", StringUtils.defaultString(vo.getUserId()));
            String mobile = Objects.isNull(userExtendsVO) ? StringUtils.EMPTY : StringUtils.defaultString(userExtendsVO.getMobile());
            map.put("手机号", mobile);
            map.put("所属机构", StringUtils.defaultString(vo.getInstitutionsName()));
            String idCard = Objects.isNull(userExtendsVO) ? StringUtils.EMPTY : StringUtils.defaultString(userExtendsVO.getIdCard());
            map.put("证件号码", idCard);
            String state = Objects.isNull(vo.getState()) ? StringUtils.EMPTY : StringUtils.defaultString(vo.getState().getDesc());
            map.put("在职状态", state);
            String joinDate = (Objects.isNull(userExtendsVO)|| Objects.isNull(userExtendsVO.getOnboardingDateTime())) ? StringUtils.EMPTY : userExtendsVO.getOnboardingDateTime().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
            map.put("入职时间", joinDate);
            String leaveDate = (Objects.isNull(userExtendsVO) || Objects.isNull(userExtendsVO.getLeaveDateTime())) ? StringUtils.EMPTY : userExtendsVO.getLeaveDateTime().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
            map.put("离职日期", leaveDate);
            map.put("银行卡", StringUtils.defaultString(userExtendsVO.getBankCardNumber()));
            map.put("所属银行", StringUtils.defaultString(userExtendsVO.getBank()));
            map.put("银行卡开户地址", StringUtils.defaultString(userExtendsVO.getOpenAccountAddress()));
            map.put("银行卡开户姓名", StringUtils.defaultString(userExtendsVO.getOpenAccountName()));
            UserAgentCertification userAgentCertification = vo.getUserAgentCertification();
            String certificateCode = Objects.isNull(userAgentCertification) ? StringUtils.EMPTY : StringUtils.defaultString(userAgentCertification.getCertificateCode());
            map.put("执业证编号", certificateCode);
            mapList.add(map);
        }
        String tempFileName = "经纪人明细.xls";
        institutionsService.generateExcel(response, mapList, tempFileName, tempFileName);
    }


    /**
     * TODO 人工查询条件
     *
     * @param institutionsUserQuery 机构用户关联
     * @return QueryWrapper
     * @author sunxin
     * @methodName queryArtificial
     * @time 2022-07-04 10:48:37
     */
    private QueryWrapper queryArtificial(InstitutionsUserQuery institutionsUserQuery, QueryWrapper<InstitutionsUser> queryWrapper) {
        //1、经纪人姓名模糊查询
        if(StringUtils.isNotBlank(institutionsUserQuery.getNameQuery())) {
            queryWrapper.inSql("user_id", "select `user_id` from `user_extends` where `user_extends`.`is_delete` = 0 and `user_extends`.`name` like '%" + institutionsUserQuery.getNameQuery() + "%'");
        }
        return queryWrapper;
    }


    /**
     * TODO 参数校验
     *
     * @param institutionsUserBO 机构用户关联
     * @author sunxin
     * @methodName validateParams
     * @time 2022-07-04 10:48:37
     */
    private void validateParams(InstitutionsUserBO institutionsUserBO) {
        //1、校验扩展信息
        UserExtendsBO userExtendsBO = institutionsUserBO.getUserExtendsBO();
        if(Objects.nonNull(userExtendsBO)) {
            //1.1、校验离职日期与在职日期
            if(StringUtils.isNotBlank(userExtendsBO.getLeaveDateTimeStr()) || StringUtils.isNotBlank(userExtendsBO.getOnboardingDateTimeStr())) {
                if(Objects.isNull(institutionsUserBO.getState())) {
                    throw new ParameterNullException("在职状态不能为空！");
                }
                if(StringUtils.isNotBlank(userExtendsBO.getLeaveDateTimeStr())) {
                     if(!institutionsUserBO.getState().equals(离职)) {
                        throw new ParameterNullException("已有离职日期，在职状态应为离职！");
                     }
                     if(StringUtils.isNotBlank(userExtendsBO.getOnboardingDateTimeStr())) {
                         LocalDateTime onboardingDateTime = LocalDateTime.parse(userExtendsBO.getOnboardingDateTimeStr(), DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
                         LocalDateTime leaveDateTime = LocalDateTime.parse(userExtendsBO.getLeaveDateTimeStr(), DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
                         if(leaveDateTime.isBefore(onboardingDateTime)) {
                            throw new ParameterNullException("离职日期不能早于入职时间！");
                         }
                    }
                }
            }
            //1.2、校验身份证、出生日期、性别
            if(StringUtils.isNotBlank(userExtendsBO.getIdCard()) && userExtendsBO.getCertificateType().equals(UserExtendsCertificateTypeEnum.身份证)) {
                if(!Validator.isCitizenId(userExtendsBO.getIdCard())) {
                    throw new BusinessException("身份证校验失败！");
                }
                if(StringUtils.isBlank(userExtendsBO.getBirthdayStr()) || Objects.isNull(userExtendsBO.getSex())) {
                    throw new ParameterNullException("已有身份证，出生日期或者性别不能为空！");
                }
                String idCard = userExtendsBO.getIdCard();
                String birth = StringUtils.EMPTY;
                String sex = StringUtils.EMPTY;;
                if(LenEnum.OLD_ID_CARD_LEN.getLen().equals(idCard.length())) {
                    birth = "19" + idCard.substring(6, 8) + "-" + idCard.substring(8, 10) +"-" + idCard.substring(10, 12);
                    sex = idCard.substring(14, 15);
                } else {
                    birth = idCard.substring(6, 10) + "-" + idCard.substring(10, 12) +"-" + idCard.substring(12, 14);
                    sex = idCard.substring(16, 17);
                }
                if(!StringUtils.equals(birth, userExtendsBO.getBirthdayStr())) {
                    throw new BusinessException("出生日期与证件匹配不上！");
                }
                int sexNo = (Integer.parseInt(sex) % 2 == 0) ? 1: 0;
                if(!userExtendsBO.getSex().equals(sexNo)) {
                    throw new BusinessException("性别与证件匹配不上！");
                }
            }
            //1.3、校验证件有效期起止
            if(StringUtils.isNotBlank(userExtendsBO.getCertificateStartDateStr()) && StringUtils.isNotBlank(userExtendsBO.getCertificateEndDateStr())) {
                LocalDate certificateEndDate = LocalDate.parse(userExtendsBO.getCertificateEndDateStr(), DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
                LocalDate certificateStartDate = LocalDate.parse(userExtendsBO.getCertificateStartDateStr(), DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
                if(certificateEndDate.isBefore(certificateStartDate)) {
                    throw new BusinessException("证件有效期起不能晚于证件有效期止！");
                }
            }
        }
        //2、校验入职时间与离职日期
        if(Objects.nonNull(institutionsUserBO.getState())) {
            if(Objects.isNull(userExtendsBO) || StringUtils.isBlank(userExtendsBO.getOnboardingDateTimeStr())) {
                throw new ParameterNullException("入职时间不能为空！");
            }
            if(institutionsUserBO.getState().equals(离职) && StringUtils.isBlank(userExtendsBO.getLeaveDateTimeStr())) {
                throw new ParameterNullException("离职日期不能为空！");
            }
            UserFormalStateEnum userFormalStateEnum = (institutionsUserBO.getState().equals(离职) ? UserFormalStateEnum.离职 : UserFormalStateEnum.修改);
            userExtendsBO.setUserFormalStateEnum(userFormalStateEnum);
        }
        //3、查询账户信息是否存在校验
        userService.checkUserRepeat(institutionsUserBO.getUserId(), null, userExtendsBO);
    }


    /**
     * TODO 修改经纪人认证信息
     *
     * @param httpServletRequest
     * @param bo 用信息BO
     * @param userAgentCertification 用户认证
     * @author sunxin
     * @return void
     * @methodName updateBrokerCertInfo
     * @time 2022-07-04 10:48:37
     */
    private void updateBrokerCertInfo(HttpServletRequest httpServletRequest, UserBO bo, UserAgentCertification userAgentCertification) {
        UserExtendsBO userExtendsBO = bo.getUserExtendsBO();
        userExtendsBO.setModifyName(bo.getModifyName());
        userExtendsBO.setUserId(bo.getId());
        bo.setUserExtendsBO(userExtendsBO);
        if(Objects.nonNull(userAgentCertification)) {
            userAgentCertification.setName(userExtendsBO.getName());
            userAgentCertification.setModifyName(bo.getModifyName());
            userAgentCertification.setUserId(bo.getId());
            if(StringUtils.isNotBlank(userAgentCertification.getCertificateCode())) {
                userAgentCertification.setState(UserAgentCertificationStateEnum.认证成功.getValue());
            }
            UserAgentCertification oriCertInfo = userAgentCertificationService.updateEnhance(userAgentCertification);
            certificationProcessService.certificationSuccessProcess(httpServletRequest, true, bo, oriCertInfo.getState(), userAgentCertification.getState());
        } else {
            //4、发送工保通修改--经纪人
            gbtTransferProcessService.updateUserInfo(httpServletRequest, bo);
            userExtendsService.updateEnhance(userExtendsBO, null, null);
        }
    }


    /**
     * TODO 新增经纪人认证信息
     *
     * @param httpServletRequest
     * @param userExtendsBO 用户扩展信息BO
     * @param userAgentCertification 用户认证
     * @author sunxin
     * @return String
     * @methodName saveBrokerCertInfo
     * @time 2022-07-04 10:48:37
     */
    private String saveBrokerCertInfo(HttpServletRequest httpServletRequest, UserExtendsBO userExtendsBO, UserAgentCertification userAgentCertification, String createName) {
        //1、工保通新增用户，包含了经纪人认证入库、通知CRM逻辑
        UserBO bo = new UserBO();
        try{
            userExtendsBO.setCreateName(createName);
            userExtendsBO.setModifyName(createName);
            bo = gbtTransferProcessService.saveUserInfo(httpServletRequest, new UserBO(){{
                setUserExtendsBO(userExtendsBO);
                setTypeValueId(new String[]{"1"});
                setCreateName(createName);
            }});
        }catch(UserException e) {
            if(!e.getReturnCode().equals(ReturnCode.该手机号已注册)) {
                throw e;
            }
            try{
                UserBasicInfoBO userBasicInfoBO = gbtTransferProcessService.freePasswordLogin(httpServletRequest, "PHONE_LOGIN", userExtendsBO.getMobile(), true).getUserBasicInfoBO();
                bo.setId(String.valueOf(userBasicInfoBO.getId()));
                bo.setUserExtendsBO(userExtendsBO);
                bo.setBusinessDetails(userBasicInfoBO.getBusinessDetails());
            }catch (UserException e2) {
                log.error("新增机构关联用户失败：工保通新增用户显示已有用户信息，然后查询工保通用户信息时，报错：", e2);
                throw new UserException(ReturnCode.用户端错误, "工保通异常！");
            }
        }
        UserBO userBO = BeanUtil.copyProperties(bo, UserBO.class);
        //2、本地新增用户，包含了角色、标签入库
        userService.saveEnhance(new UserBO(){{
            setId(userBO.getId());
            setUserName(userExtendsBO.getMobile());
            setUserExtendsBO(userExtendsBO);
            setRoleCode(userBO.getRoleCode());
            setTypeValueId(userBO.getTypeValueId());
            setIpAddress(userBO.getIpAddress());
            setIp(userBO.getIp());
            setPassword(userBO.getPassword());
            setCreateName(createName);
            setSourceId(userBO.getSourceId());
            setSourceValueId(userBO.getSourceValueId());
            setBusinessDetails(userBO.getBusinessDetails());
        }});
        //3、请求参数中，认证信息不为空，包含了经纪人认证修改、通知CRM逻辑
        if(Objects.nonNull(userAgentCertification)) {
            userAgentCertification.setUserId(userBO.getId());
            userAgentCertification.setName(userExtendsBO.getName());
            userAgentCertification.setModifyName(createName);
            if(StringUtils.isNotBlank(userAgentCertification.getCertificateCode())) {
                userAgentCertification.setState(UserAgentCertificationStateEnum.认证成功.getValue());
            }else {
                userAgentCertification.setState(UserAgentCertificationStateEnum.待认证.getValue());
            }
            userAgentCertificationService.updateAgentCertEnhance(httpServletRequest, userAgentCertification, false);
        }
        return userBO.getId();
    }
}