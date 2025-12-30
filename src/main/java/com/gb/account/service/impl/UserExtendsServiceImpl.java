package com.gb.account.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.account.entity.UserExtends;
import com.gb.account.entity.bo.UserExtendsBO;
import com.gb.account.entity.bo.UserRoleBO;
import com.gb.account.entity.enums.UserFormalStateEnum;
import com.gb.account.entity.query.UserExtendsQuery;
import com.gb.account.entity.query.UserRoleQuery;
import com.gb.account.entity.query.UserTypeValueRelationshipQuery;
import com.gb.account.entity.vo.UserExtendsVO;
import com.gb.account.entity.vo.UserTypeValueRelationshipVO;
import com.gb.account.mapper.UserExtendsMapper;
import com.gb.account.service.UserExtendsService;
import com.gb.account.service.UserRoleService;
import com.gb.account.service.UserTypeValueRelationshipService;
import com.gb.account.service.query.UserExtendsServiceQuery;
import com.gb.account.service.results.UserExtendsServiceResults;
import com.gb.mq.crm.BindUserEvent;
import com.gb.platform.entity.bo.BatchPlatformSystemUserBO;
import com.gb.platform.service.TransformationExternalPlatformSystemUserService;
import com.gb.user.entity.bo.InstitutionsUserBO;
import com.gb.user.entity.enums.InstitutionsUserStateEnum;
import com.gb.user.entity.query.InstitutionsUserQuery;
import com.gb.user.entity.query.TeamUserQuery;
import com.gb.user.entity.vo.TeamUserVO;
import com.gb.user.enums.MqNoticeTypeEnum;
import com.gb.user.enums.RoleUserTypeRelatedEnum;
import com.gb.user.service.InstitutionsUserService;
import com.gb.user.service.TeamUserService;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.ParameterNullException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gb.account.entity.enums.UserFormalStateEnum.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 孙凯伦
 * @since: 2021-10-21 01:50:40
 * @description: TODO 用户扩展表，Service服务实现层
 * @source: 代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserExtendsServiceImpl extends ServiceImpl<UserExtendsMapper, UserExtends> implements UserExtendsService {

    /**
     * 用户扩展表
     */
    private UserExtendsMapper userExtendsMapper;


    /**
     * 用户扩展表
     */
    private UserExtendsServiceResults userExtendsServiceResults;


    /**
     * 用户类型值关联表
     */
    private UserTypeValueRelationshipService userTypeValueRelationshipService;


    /**
     * 机构用户关联表
     */
    private InstitutionsUserService institutionsUserService;


    /**
     * 团队用户人员服务表
     */
    private TeamUserService teamUserService;


    /**
     * 用户角色关系表
     */
    private UserRoleService userRoleService;


    /**
     * 转化外部系统平台用户关联
     */
    private TransformationExternalPlatformSystemUserService transformationExternalPlatformSystemUserService;


    /**
     * 集合条件查询
     *
     * @param userExtendsQuery:
     * @return java.util.List<com.entity.UserExtendsVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:50:40
     */
    @Override
    public List<UserExtendsVO> listEnhance(UserExtendsQuery userExtendsQuery) {
        UserExtends userExtends = GeneralConvertor.convertor(userExtendsQuery, UserExtends.class);
        QueryWrapper<UserExtends> queryWrapper = new QueryWrapper<>(userExtends);
        // TODO 自动生成查询，禁止手动写语句
        UserExtendsServiceQuery.query(userExtendsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userExtendsQuery, queryWrapper);
        //DO数据
        List<UserExtends> userExtendsDO = userExtendsMapper.selectList(queryWrapper);
        //VO数据
        List<UserExtendsVO> userExtendsVO = GeneralConvertor.convertor(userExtendsDO, UserExtendsVO.class);
        return userExtendsServiceResults.assignment(userExtendsVO);
    }


    /**
     * 分页条件查询
     *
     * @param page:
     * @param userExtendsQuery:
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     * @author 孙凯伦
     * @since 2021-10-21 01:50:40
     */
    @Override
    public Page<UserExtendsVO> pageEnhance(Page page, UserExtendsQuery userExtendsQuery) {
        UserExtends userExtends = GeneralConvertor.convertor(userExtendsQuery, UserExtends.class);
        QueryWrapper<UserExtends> queryWrapper = new QueryWrapper<>(userExtends);
        //TODO 自动生成查询，禁止手动写语句
        UserExtendsServiceQuery.query(userExtendsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userExtendsQuery, queryWrapper);
        //DO数据
        Page<UserExtends> pageDO = userExtendsMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<UserExtendsVO> pageVO = userExtendsServiceResults.toPageVO(pageDO);
        return userExtendsServiceResults.assignment(pageVO);
    }


    /**
     * 单条条件查询
     *
     * @param userExtendsQuery:
     * @return java.util.List<com.entity.UserExtendsVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:50:40
     */
    @Override
    public UserExtendsVO getOneEnhance(UserExtendsQuery userExtendsQuery) {
        UserExtends userExtends = GeneralConvertor.convertor(userExtendsQuery, UserExtends.class);
        QueryWrapper<UserExtends> queryWrapper = new QueryWrapper<>(userExtends);
        //TODO 自动生成查询，禁止手动写语句
        UserExtendsServiceQuery.query(userExtendsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userExtendsQuery, queryWrapper);
        //DO数据
        UserExtends userExtendsDO = userExtendsMapper.selectOne(queryWrapper);
        //VO数据
        UserExtendsVO userExtendsVO = GeneralConvertor.convertor(userExtendsDO, UserExtendsVO.class);
        return userExtendsServiceResults.assignment(userExtendsVO);
    }


    /**
     * 总数
     *
     * @param userExtendsQuery:
     * @return java.lang.Integer
     * @author 孙凯伦
     * @since 2021-10-21 01:50:40
     */
    @Override
    public Long countEnhance(UserExtendsQuery userExtendsQuery) {
        UserExtends userExtends = GeneralConvertor.convertor(userExtendsQuery, UserExtends.class);
        QueryWrapper<UserExtends> queryWrapper = new QueryWrapper<>(userExtends);
        //TODO 自动生成查询，禁止手动写语句
        UserExtendsServiceQuery.query(userExtendsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userExtendsQuery, queryWrapper);
        return userExtendsMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     *
     * @param userExtendsBO:
     * @return java.lang.String
     * @author 孙凯伦
     * @since 2021-10-21 01:50:40
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(UserExtendsBO userExtendsBO) {
        UserExtends userExtends = buildUserExtends(userExtendsBO, null);
        if (countEnhance(new UserExtendsQuery() {{
            setUserId(userExtends.getUserId());
        }}) < 1) {
            userExtendsMapper.insert(userExtends);
        }
        return userExtends.getId();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void updateEnhance(UserExtendsBO userExtendsBO, String userName, String newUserName) {
        if(Objects.isNull(userExtendsBO.getUserFormalStateEnum())) {
            userExtendsBO.setUserFormalStateEnum(UserFormalStateEnum.修改);
        }
        //校验用户扩展信息--存在
        UserExtendsQuery userExtendsQuery = new UserExtendsQuery();
        if(StringUtils.isBlank(userExtendsBO.getId())) {
            userExtendsQuery.setUserId(userExtendsBO.getUserId());
        } else {
            userExtendsQuery.setId(userExtendsBO.getId());
        }
        UserExtendsVO userExtendsVO = getOneEnhance(userExtendsQuery);
        if(Objects.isNull(userExtendsVO)) {
            if(!userExtendsBO.getUserFormalStateEnum().equals(UserFormalStateEnum.修改新增)) {
                throw new ParameterNullException("未找到该用户的扩展信息！");
            }
            saveEnhance(userExtendsBO);
            return;
        }
        //非正常状态用户信息处理
        BindUserEvent userMobileEvent = null;
        if(userExtendsBO.getUserFormalStateEnum().equals(注销) || userExtendsBO.getUserFormalStateEnum().equals(离职)) {
            userMobileEvent = dealAbnormalStateUser(userExtendsBO.getUserId(), userExtendsBO.getModifyName(), userExtendsBO.getUserFormalStateEnum());
            if(StringUtils.isBlank(userExtendsBO.getLeaveDateTimeStr())) {
                userExtendsBO.setLeaveDateTimeStr(DateUtil.now());
            }
        }
        if(userExtendsBO.getUserFormalStateEnum().equals(修改) && !StringUtils.equals(userExtendsBO.getMobile(), userExtendsVO.getMobile())) {
            userMobileEvent = new BindUserEvent();
        }
        //更新用户信息
        UpdateWrapper<UserExtends> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userExtendsVO.getId());
        UserExtends userExtends = buildUserExtends(userExtendsBO, updateWrapper);
        Integer i = userExtendsMapper.update(userExtends, updateWrapper);
        if(i < 0) {
            throw new BusinessException("用户扩展信息更新失败！");
        }
        //通知crm
        if(Objects.nonNull(userMobileEvent)) {
            userMobileEvent.setUserId(userExtendsVO.getUserId())
                    .setMobile(userExtendsVO.getMobile())
                    .setUserName(userName)
                    .setNewUserName(newUserName)
                    .setNewMobile(userExtendsBO.getMobile())
                    .setUserFormalStateValue(userExtendsBO.getUserFormalStateEnum().getValue());
            MqNoticeTypeEnum.CRM_BIND_USER_MQ.pushMqMessage(userExtendsBO.getUserFormalStateEnum().getDesc(), userMobileEvent);
        }
    }


    /**
     * 删除
     *
     * @param userExtendsBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 01:50:40
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserExtendsBO userExtendsBO) {
        UserExtends userExtends = GeneralConvertor.convertor(userExtendsBO, UserExtends.class);
        QueryWrapper<UserExtends> queryWrapper = new QueryWrapper<>(userExtends);
        Integer i = userExtendsMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }

    @Override
    public UserExtends buildUserExtends(UserExtendsBO userExtendsBO, UpdateWrapper<UserExtends> updateWrapper) {
        if(Objects.isNull(userExtendsBO)) {
            return null;
        }
        UserExtends userExtends = GeneralConvertor.convertor(userExtendsBO, UserExtends.class);
        //出生日期
        if(Objects.nonNull(userExtendsBO.getBirthdayStr())) {
            if(StringUtils.isNotBlank(userExtendsBO.getBirthdayStr())) {
                userExtends.setBirthday(LocalDate.parse(userExtendsBO.getBirthdayStr(), DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            }else {
                if(Objects.nonNull(updateWrapper)) {
                    updateWrapper.set("birthday", null);
                }
            }
        }
        //离职
        if (Objects.nonNull((userExtendsBO.getLeaveDateTimeStr()))) {
            if (StringUtils.isNotBlank((userExtendsBO.getLeaveDateTimeStr()))) {
                userExtends.setLeaveDateTime(LocalDateTime.parse(userExtendsBO.getLeaveDateTimeStr(), DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            }else {
                if(Objects.nonNull(updateWrapper)) {
                    updateWrapper.set("leave_date_time", null);
                }
            }
        }
        //在职
        if (Objects.nonNull((userExtendsBO.getOnboardingDateTimeStr()))) {
            if (StringUtils.isNotBlank((userExtendsBO.getOnboardingDateTimeStr()))) {
                userExtends.setOnboardingDateTime(LocalDateTime.parse(userExtendsBO.getOnboardingDateTimeStr(), DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            }else {
                if(Objects.nonNull(updateWrapper)) {
                    updateWrapper.set("onboarding_date_time", null);
                }
            }
        }
        //证件有效期止
        if (Objects.nonNull((userExtendsBO.getCertificateEndDateStr()))) {
            if (StringUtils.isNotBlank((userExtendsBO.getCertificateEndDateStr()))) {
                userExtends.setCertificateEndDate(LocalDate.parse(userExtendsBO.getCertificateEndDateStr(), DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            }else {
                if(Objects.nonNull(updateWrapper)) {
                    updateWrapper.set("certificate_end_date", null);
                }
            }
        }
        //证件有效期起
        if (Objects.nonNull((userExtendsBO.getCertificateStartDateStr()))) {
            if (StringUtils.isNotBlank((userExtendsBO.getCertificateStartDateStr()))) {
                userExtends.setCertificateStartDate(LocalDate.parse(userExtendsBO.getCertificateStartDateStr(), DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            }else {
                if(Objects.nonNull(updateWrapper)) {
                    updateWrapper.set("certificate_start_date", null);
                }
            }
        }
        return userExtends;
    }


    /**
     * 查询人工查询条件
     *
     * @param userExtendsQuery 用户扩展表
     * @return QueryWrapper
     * @author 孙凯伦
     * @since 2021-10-21 01:50:40
     */
    private QueryWrapper queryArtificial(UserExtendsQuery userExtendsQuery, QueryWrapper<UserExtends> queryWrapper) {
        if (CollectionUtils.isNotEmpty(userExtendsQuery.getUserIdList())) {
            queryWrapper.in("`user_id`", userExtendsQuery.getUserIdList());
        }
        //1、手机号列表查询，备注：目前只有【财务结算】用到了这个查询条件
        if (CollectionUtils.isNotEmpty(userExtendsQuery.getMobileList())) {
            queryWrapper.in("`mobile`", userExtendsQuery.getMobileList());
        }
        //2、手机号模糊查询
        if (StringUtils.isNotBlank(userExtendsQuery.getMobileQuery())) {
            queryWrapper.likeRight("mobile", userExtendsQuery.getMobileQuery());
        }
        //3、昵称模糊查询
        if (StringUtils.isNotBlank(userExtendsQuery.getAliasQuery())) {
            queryWrapper.likeRight("alias", userExtendsQuery.getAliasQuery());
        }
        //4、姓名模糊查询
        if (StringUtils.isNotBlank(userExtendsQuery.getNameQuery())) {
            queryWrapper.likeRight("name", userExtendsQuery.getNameQuery());
        }
        if(Objects.nonNull(userExtendsQuery.getUserFormalStateEnum())) {
            StringBuilder sbf = new StringBuilder("select `id` from `user` where `user`.`is_delete` = 0");
            if(userExtendsQuery.getUserFormalStateEnum().equals(注销) || userExtendsQuery.getUserFormalStateEnum().equals(离职)) {
                queryWrapper.isNotNull("leave_date_time");
                if(userExtendsQuery.getUserFormalStateEnum().equals(注销)) {
                    sbf.append(" and user.`state` = 1");
                } else {
                    sbf.append(" and user.`state` = 0");
                }
            } else {
                queryWrapper.isNull("leave_date_time");
                sbf.append(" and user.`state` = 0");
            }
            queryWrapper.inSql("user_id", sbf.toString());
        }
        return queryWrapper;
    }

    /**
     * 非正常状态用户处理
     * @param userId 用户ID
     * @param modifyName 修改人名称
     * @param userFormalStateEnum 状态类型
     * @return BindUserEvent
     */
    private BindUserEvent dealAbnormalStateUser(String userId, String modifyName, UserFormalStateEnum userFormalStateEnum) {
        if(StringUtils.isBlank(userId)) {
            return null;
        }
        if(!userFormalStateEnum.equals(UserFormalStateEnum.离职) && !userFormalStateEnum.equals(注销)) {
            return null;
        }
        //判断注销或离职人员是否是团队成员
        String msg = "您的账号渠道合作中，请拨打400-800-5100联系客服人员为您离职！";
        if(userFormalStateEnum.equals(注销)) {
            msg = "您的账号渠道合作中，请拨打400-800-5100联系客服人员为您注销！";
        }
        TeamUserVO teamUserVO = teamUserService.getOneEnhance(new TeamUserQuery(){{setUserId(userId);}});
        if(Objects.nonNull(teamUserVO) && teamUserVO.getChannel()) {
            throw new BusinessException(msg);
        }
       //判断是否是机构用户
        if(institutionsUserService.countEnhance(new InstitutionsUserQuery(){{setUserId(userId);}}) > 0) {
            InstitutionsUserBO institutionsUserBO = new InstitutionsUserBO();
            institutionsUserBO.setModifyName(modifyName);
            institutionsUserBO.setState(InstitutionsUserStateEnum.离职);
            institutionsUserBO.setUserId(userId);
            institutionsUserService.updateEnhance(null, institutionsUserBO);
        }

        //删除--销售类别--团队成员ID角色记录
        String roleCode = RoleUserTypeRelatedEnum.自营客户管理角色.getRoleCode() + "," + RoleUserTypeRelatedEnum.分销客户管理角色.getRoleCode();
        if(userRoleService.countEnhance(new UserRoleQuery(){{
            setUserId(userId);
            setRoleCode(roleCode);
        }}) > 0) {
            UserRoleBO userRoleBO = new UserRoleBO();
            userRoleBO.setUserId(userId);
            userRoleBO.setRoleCode(roleCode);
            userRoleService.removeEnhance(userRoleBO);
        }
        //批量外部系统用户更新
        transformationExternalPlatformSystemUserService.updateBatchPlateform(new BatchPlatformSystemUserBO() {{
            setUserId(userId);
        }});
        //获取用户标签
        List<UserTypeValueRelationshipVO> userTypeValueList = userTypeValueRelationshipService.listEnhance(new UserTypeValueRelationshipQuery() {{
            setUserId(userId);
        }});
        String userTypeValueCode = userTypeValueList.stream().map(UserTypeValueRelationshipVO::getUserTypeValueCode).collect(Collectors.joining());
        BindUserEvent bindUserEvent = new BindUserEvent();
        bindUserEvent.setUserTypeValueCode(userTypeValueCode);
        return bindUserEvent;
    }
}