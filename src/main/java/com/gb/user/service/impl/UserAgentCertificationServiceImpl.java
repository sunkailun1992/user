package com.gb.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.account.entity.bo.UserBO;
import com.gb.account.entity.bo.UserExtendsBO;
import com.gb.account.entity.query.UserExtendsQuery;
import com.gb.account.entity.vo.UserExtendsVO;
import com.gb.account.service.UserExtendsService;
import com.gb.account.service.UserRoleService;
import com.gb.permissions.entity.query.RoleQuery;
import com.gb.permissions.entity.vo.RoleVO;
import com.gb.permissions.service.RoleService;
import com.gb.rpc.component.RpcComponent;
import com.gb.user.entity.UserAgentCertification;
import com.gb.user.entity.enums.UserAgentCertificationStateEnum;
import com.gb.user.enums.RoleUserTypeRelatedEnum;
import com.gb.user.mapper.UserAgentCertificationMapper;
import com.gb.user.service.CertificationProcessService;
import com.gb.user.service.UserAgentCertificationService;
import com.gb.utils.JsonUtil;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.ParameterNullException;
import com.gb.utils.exception.PreventRepeatException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gb.rpc.enums.RpcTypeEnum.JJRAUTH_ORDERCLOSE;

/**
 * <p>
 * 用户经纪人认证 服务实现类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserAgentCertificationServiceImpl extends ServiceImpl<UserAgentCertificationMapper, UserAgentCertification> implements UserAgentCertificationService {


    /**
     * 用户经纪人认证
     */
    private UserAgentCertificationMapper userAgentCertificationMapper;

    private UserExtendsService userExtendsService;

    private RpcComponent rpcComponent;

    private UserRoleService userRoleService;

    private RoleService roleService;

    private CertificationProcessService certificationProcessService;


    /**
     * 集合条件查询
     *
     * @param userAgentCertification:
     * @return java.util.List<com.entity.UserAgentCertification>
     * @author sunx
     * @since 2021-05-25
     */
    @Override
    public List<UserAgentCertification> listEnhance(UserAgentCertification userAgentCertification) {
        //1、模糊查询用户扩展信息
        Map<String, List<UserExtendsVO>> userExtendsVoGroup = Maps.newHashMap();
        if (StringUtils.isNotBlank(userAgentCertification.getAliasQuery())
                || StringUtils.isNotBlank(userAgentCertification.getNameQuery())
                || StringUtils.isNotBlank(userAgentCertification.getMobileQuery())) {
            List<UserExtendsVO> userExtendsVOList = userExtendsService.listEnhance(new UserExtendsQuery() {{
                setNameQuery(userAgentCertification.getNameQuery());
                setMobileQuery(userAgentCertification.getMobileQuery());
                setAliasQuery(userAgentCertification.getAliasQuery());
            }});
            if (CollectionUtils.isEmpty(userExtendsVOList)) {
                return Lists.newArrayList();
            }
            userExtendsVoGroup = userExtendsVOList.stream().collect(Collectors.groupingBy(s -> s.getUserId()));
            userAgentCertification.setUserIdList(Lists.newArrayList(userExtendsVoGroup.keySet()));
        }
        //2、查询认证表信息
        QueryWrapper<UserAgentCertification> queryWrapper = new QueryWrapper<>(userAgentCertification);
        query(userAgentCertification, queryWrapper);
        return assignment(userAgentCertification, userExtendsVoGroup, userAgentCertificationMapper.selectList(queryWrapper));
    }


    /**
     * 分页条件查询
     *
     * @param userAgentCertification:
     * @param page:
     * @return java.util.List<com.entity.UserAgentCertification>
     * @author sunx
     * @since 2021-05-25
     */
    @Override
    public IPage pageEnhance(Page page, UserAgentCertification userAgentCertification) {
        //1、模糊查询用户扩展信息
        Map<String, List<UserExtendsVO>> userExtendsVoGroup = Maps.newHashMap();
        if (StringUtils.isNotBlank(userAgentCertification.getAliasQuery())
                || StringUtils.isNotBlank(userAgentCertification.getNameQuery())
                || StringUtils.isNotBlank(userAgentCertification.getMobileQuery())) {
            List<UserExtendsVO> userExtendsVOList = userExtendsService.listEnhance(new UserExtendsQuery() {{
                setNameQuery(userAgentCertification.getNameQuery());
                setMobileQuery(userAgentCertification.getMobileQuery());
                setAliasQuery(userAgentCertification.getAliasQuery());
            }});
            if (CollectionUtils.isEmpty(userExtendsVOList)) {
                return page;
            }
            userExtendsVoGroup = userExtendsVOList.stream().collect(Collectors.groupingBy(s -> s.getUserId()));
            userAgentCertification.setUserIdList(Lists.newArrayList(userExtendsVoGroup.keySet()));
        }
        //2、查询认证表信息
        QueryWrapper<UserAgentCertification> queryWrapper = new QueryWrapper<>(userAgentCertification);
        query(userAgentCertification, queryWrapper);
        return assignment(userAgentCertification, userExtendsVoGroup, userAgentCertificationMapper.selectPage(page, queryWrapper));
    }


    /**
     * 单条条件查询
     *
     * @param userAgentCertification:
     * @return java.util.List<com.entity.UserAgentCertification>
     * @author sunx
     * @since 2021-05-25
     */
    @Override
    public UserAgentCertification getOneEnhance(UserAgentCertification userAgentCertification) {
        QueryWrapper<UserAgentCertification> queryWrapper = new QueryWrapper<>(userAgentCertification);
        query(userAgentCertification, queryWrapper);
        UserAgentCertification agentCertification = userAgentCertificationMapper.selectOne(queryWrapper);
        if (Objects.isNull(agentCertification)) {
            log.debug("用户认证信息为空！【请求参数：{}】", JsonUtil.json(userAgentCertification));
            return agentCertification;
        }
        return assignment(agentCertification);
    }


    /**
     * 总数
     *
     * @param userAgentCertification:
     * @return java.util.List<com.entity.UserAgentCertification>
     * @author sunx
     * @since 2021-05-25
     */
    @Override
    public Long countEnhance(UserAgentCertification userAgentCertification) {
        QueryWrapper<UserAgentCertification> queryWrapper = new QueryWrapper<>(userAgentCertification);
        query(userAgentCertification, queryWrapper);
        return userAgentCertificationMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     *
     * @param userAgentCertification:
     * @return java.util.List<com.entity.UserAgentCertification>
     * @author sunx
     * @since 2021-05-25
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean saveEnhance(UserAgentCertification userAgentCertification) {
        Long count = countEnhance(new UserAgentCertification() {{
            setUserId(userAgentCertification.getUserId());
        }});
        if (count > 0) {
            throw new PreventRepeatException("用户经纪人信息已经存在！");
        }
        if(StringUtils.isNotBlank(userAgentCertification.getIssueCertificatesDateStr())) {
            LocalDate issueCertificatesDate = LocalDate.parse(userAgentCertification.getIssueCertificatesDateStr(), DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
            userAgentCertification.setIssueCertificatesDate(issueCertificatesDate);
        }
        int i = userAgentCertificationMapper.insert(userAgentCertification);
        if (i == 0) {
            log.error("用户经纪人认证【userId：{}】新增失败", userAgentCertification.getUserId());
            throw new BusinessException("用户经纪人新增失败！");
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean saveBatchEnhance(String createName, String roleId, List<String> userIdList) {
        RoleVO roleVO = roleService.getOneEnhance(new RoleQuery(){{
            setId(roleId);
        }});
        if(Objects.isNull(roleVO) || (!StringUtils.equals(roleVO.getValue(), RoleUserTypeRelatedEnum.非正式经纪人.getRoleCode()))){
            log.debug("roleId：{}，不需要同步数据到经纪人认证列表！", roleId);
            return true;
        }
        List<UserAgentCertification> userAgentCertificationList = listEnhance(new UserAgentCertification(){{
            setUserIdList(userIdList);
        }});
        List<UserAgentCertification> updateUserAgentList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(userAgentCertificationList)){
            for(UserAgentCertification userAgentCertification : userAgentCertificationList) {
                if(userIdList.contains(userAgentCertification.getUserId())){
                    continue;
                }
                updateUserAgentList.add(new UserAgentCertification(){{
                    setUserId(userAgentCertification.getUserId());
                    setCreateName(createName);
                }});
            }
            userAgentCertificationList.clear();
        }else{
            for(String userId : userIdList){
                updateUserAgentList.add(new UserAgentCertification(){{
                    setUserId(userId);
                    setCreateName(createName);
                }});
            }
        }

        if(CollectionUtils.isEmpty(updateUserAgentList)){
            log.debug("roleId：{}，用户列表数据已存在于经纪人认证列表！", roleId, JsonUtil.json(userIdList));
            return true;
        }
       if(!saveBatch(updateUserAgentList)){
           log.debug("roleId：{}，经纪人认证列表添加失败！", roleId, JsonUtil.json(userIdList));
           throw new BusinessException("经纪人认证列表添加失败！");
       }
       return true;
    }


    /**
     * 修改
     *
     * @param userAgentCertification:
     * @return java.lang.String
     * @author sunx
     * @since 2021-05-25
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public UserAgentCertification updateEnhance(UserAgentCertification userAgentCertification) {
        UserAgentCertification oriCertInfo = getOneEnhance(new UserAgentCertification() {{
            setUserId(userAgentCertification.getUserId());
            setId(userAgentCertification.getId());
        }});
        if (Objects.isNull(oriCertInfo)) {
            throw new ParameterNullException("未找到待更新的经纪人认证信息！");
        }
        userAgentCertification.setId(oriCertInfo.getId());
        userAgentCertification.setUserId(oriCertInfo.getUserId());
        if(UserAgentCertificationStateEnum.认证成功.getValue().equals(userAgentCertification.getState())) {
            if (StringUtils.isBlank(userAgentCertification.getName()) || StringUtils.isBlank(userAgentCertification.getCertificateCode())) {
                throw new ParameterNullException("缺少经纪人认证通过必要参数！");
            }
            String certificateCode = userAgentCertification.getCertificateCode().toUpperCase();
            List<UserAgentCertification> userAgentCertificationList = listEnhance(new UserAgentCertification() {{
                setCertificateCode(certificateCode);
            }});
            boolean existFlag = userAgentCertificationList.size() > 1 ? true : (CollectionUtils.isNotEmpty(userAgentCertificationList) && !StringUtils.equals(userAgentCertificationList.get(0).getUserId(), oriCertInfo.getUserId()));
            if(existFlag) {
                log.error("职业证书编号：{}已做过认证！认证的信息为：{}", userAgentCertification.getCertificateCode(), JSON.toJSONString(userAgentCertificationList));
                throw new PreventRepeatException("该执政编号已做过认证！");
            }
            userAgentCertification.setCertificateCode(certificateCode);
        }else if(UserAgentCertificationStateEnum.认证失败.getValue().equals(userAgentCertification.getState())) {
            //关闭订单
            rpcComponent.rpcQuery(userAgentCertification.getUserId(), JJRAUTH_ORDERCLOSE, Integer.class);
        }
        //4、更新经纪人认证信息表
        UpdateWrapper<UserAgentCertification> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userAgentCertification.getId());
        if(Objects.nonNull(userAgentCertification.getIssueCertificatesDateStr())) {
            if(StringUtils.isNotBlank(userAgentCertification.getIssueCertificatesDateStr())) {
                LocalDate issueCertificatesDate = LocalDate.parse(userAgentCertification.getIssueCertificatesDateStr(), DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
                userAgentCertification.setIssueCertificatesDate(issueCertificatesDate);
            } else {
                updateWrapper.set("issue_certificates_date", null);
            }
        }
        Integer i = userAgentCertificationMapper.update(userAgentCertification, updateWrapper);
        if (i < 1) {
            throw new BusinessException("经纪人认证状态修改失败！");
        }
        return oriCertInfo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void updateAgentCertEnhance(HttpServletRequest httpServletRequest, UserAgentCertification userAgentCertification, Boolean isUpdateUserInfo) {
        //1、更新经纪人认证信息
        UserAgentCertification oriCertInfo = updateEnhance(userAgentCertification);
        //2、更新经纪人的用户信息，角色信息，并通知到工保通，CRM【注意：原来已经认证成功过的，不需要在通知CRM和工保通，也不需要进行修改本地用户库信息】
        certificationProcessService.certificationSuccessProcess(httpServletRequest, isUpdateUserInfo, new UserBO(){{
            setId(oriCertInfo.getUserId());
            setUserExtendsBO(new UserExtendsBO(){{
                setUserId(oriCertInfo.getUserId());
                setName(userAgentCertification.getName());
                setModifyName(userAgentCertification.getModifyName());
            }});
            setUpdateType("UPDATE_USER");
        }}, oriCertInfo.getState(), userAgentCertification.getState());
    }

    /**
     * 增强查询条件
     *
     * @param userAgentCertification:
     * @param queryWrapper:
     * @return void
     * @author sunx
     * @since 2021-05-25
     */
    private void query(UserAgentCertification userAgentCertification, QueryWrapper<UserAgentCertification> queryWrapper) {
        /**
         * 排序
         */
        if (userAgentCertification.getCollation() != null && StringUtils.isNotBlank(userAgentCertification.getCollationFields())) {
            if (userAgentCertification.getCollation()) {
                queryWrapper.orderByAsc(userAgentCertification.getCollationFields());
            } else {
                queryWrapper.orderByDesc(userAgentCertification.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(userAgentCertification.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(userAgentCertification.getFields())) {
            queryWrapper.select(userAgentCertification.getFields());
        }

        /**
         * 注册时间止
         */
        if (StringUtils.isNotBlank(userAgentCertification.getCreateDateTimeEnd())) {
            queryWrapper.le("create_date_time", userAgentCertification.getCreateDateTimeEnd());
        }

        /**
         * 注册时间起
         */
        if (StringUtils.isNotBlank(userAgentCertification.getCreateDateTimeStart())) {
            queryWrapper.ge("create_date_time", userAgentCertification.getCreateDateTimeStart());
        }

        /**
         * 用户序列列表
         */
        if (CollectionUtils.isNotEmpty(userAgentCertification.getUserIdList())) {
            queryWrapper.in("user_id", userAgentCertification.getUserIdList());

        }

        /**
         * 离职过滤
         */
        if(Objects.nonNull(userAgentCertification.getDimission())){
            queryWrapper.notInSql("user_id","select `user_id` from `user_extends` where `leave_date_time` is not null");
        }
    }


    /**
     * 单条，增强返回参数追加
     *
     * @param userAgentCertification:
     * @return UserAgentCertification
     * @author sunx
     * @since 2021-05-25
     */
    private UserAgentCertification assignment(UserAgentCertification userAgentCertification) {
        if (Objects.isNull(userAgentCertification)) {
            return userAgentCertification;
        }
        UserExtendsVO userExtendsVO = userExtendsService.getOneEnhance(new UserExtendsQuery() {{
            setUserId(userAgentCertification.getUserId());
            setMobile(userAgentCertification.getMobile());
        }});
        if (Objects.isNull(userExtendsVO)) {
            log.debug("用户经纪人认证信息：{}，对应的用户扩展信息不存在！", JsonUtil.json(userAgentCertification));
            return userAgentCertification;
        }
        userAgentCertification.setMobile(userExtendsVO.getMobile());
        userAgentCertification.setAlias(userExtendsVO.getAlias());
        userAgentCertification.setName(userExtendsVO.getName());
        return userAgentCertification;
    }

    /**
     * 分页,增强返回参数追加
     *
     * @param userAgentCertification:
     * @param userExtendsVoGroup:
     * @return userAgentCertificationList
     * @author sunx
     * @since 2021-05-25
     */
    private IPage assignment(UserAgentCertification userAgentCertification, Map<String, List<UserExtendsVO>> userExtendsVoGroup, IPage<UserAgentCertification> userAgentCertificationList) {
        if (CollectionUtils.isEmpty(userAgentCertificationList.getRecords())) {
            return userAgentCertificationList;
        }
        userAgentCertificationList.setRecords(assignment(userAgentCertification, userExtendsVoGroup, userAgentCertificationList.getRecords()));
        return userAgentCertificationList;
    }

    /**
     * 删除
     *
     * @param userAgentCertification:
     * @return java.util.List<com.entity.UserAgentCertification>
     * @author sunx
     * @since 2021-05-25
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserAgentCertification userAgentCertification) {
        QueryWrapper<UserAgentCertification> queryWrapper = new QueryWrapper<>(userAgentCertification);
        Integer i = userAgentCertificationMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 集合,增强返回参数追加
     *
     * @param userAgentCertification:
     * @return userExtendsVoGroup
     * @return userAgentCertificationList
     * @author sunx
     * @since 2021-05-25
     */
    private List<UserAgentCertification> assignment(UserAgentCertification userAgentCertification, Map<String, List<UserExtendsVO>> userExtendsVoGroup, List<UserAgentCertification> userAgentCertificationList) {
        if (CollectionUtils.isEmpty(userAgentCertificationList)) {
            return userAgentCertificationList;
        }
        //1、组装列表返回结果
        if (MapUtils.isNotEmpty(userExtendsVoGroup)) {
            userAgentCertificationList.forEach(u -> {
                if (userExtendsVoGroup.containsKey(u.getUserId())) {
                    buildUserAgentCertification(u, userExtendsVoGroup.get(u.getUserId()).get(0));
                }
            });
            return userAgentCertificationList;
        }
        //2、查询用户扩展信息表，组装列表返回结果
        Map<String, List<UserAgentCertification>> userAgentCertificationGroup = userAgentCertificationList.stream().collect(Collectors.groupingBy(UserAgentCertification::getUserId));
        List<UserExtendsVO> userExtendsVOList = userExtendsService.listEnhance(new UserExtendsQuery() {{
            setUserIdList(Lists.newArrayList(userAgentCertificationGroup.keySet()));
        }});
        Map<String, List<UserExtendsVO>> userExtendsGroup = userExtendsVOList.stream().collect(Collectors.groupingBy(u->u.getUserId()));
        List<UserAgentCertification> certificationList = Lists.newArrayList();
        for (Map.Entry<String, List<UserAgentCertification>> entry : userAgentCertificationGroup.entrySet()) {
            if(!userExtendsGroup.containsKey(entry.getKey())){
                certificationList.addAll(entry.getValue());
            } else {
                UserAgentCertification certification = entry.getValue().get(0);
                buildUserAgentCertification(certification, userExtendsGroup.get(entry.getKey()).get(0));
                certificationList.add(certification);
            }
        }
        return certificationList;
    }

    /**
     * 组织用户认证信息
     * @param userAgentCertification
     * @param userExtendsVO
     */
    private void buildUserAgentCertification(UserAgentCertification userAgentCertification, UserExtendsVO userExtendsVO) {
        if(Objects.nonNull(userAgentCertification.getIssueCertificatesDate())) {
            userAgentCertification.setIssueCertificatesDateStr(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN).format(userAgentCertification.getIssueCertificatesDate()));
        }
        if(Objects.nonNull(userExtendsVO)) {
            userAgentCertification.setName(userExtendsVO.getName());
            userAgentCertification.setAlias(userExtendsVO.getAlias());
            userAgentCertification.setMobile(userExtendsVO.getMobile());
            UserExtendsBO userExtendsBO = BeanUtil.copyProperties(userExtendsVO, UserExtendsBO.class);
            if(Objects.nonNull(userExtendsVO.getBirthday())) {
                userExtendsBO.setBirthdayStr(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN).format(userExtendsVO.getBirthday()));
            }
            if(Objects.nonNull(userExtendsVO.getCertificateEndDate())) {
                userExtendsBO.setCertificateEndDateStr(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN).format(userExtendsVO.getCertificateEndDate()));
            }
            if(Objects.nonNull(userExtendsVO.getCertificateEndDate())) {
                userExtendsBO.setCertificateEndDateStr(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN).format(userExtendsVO.getCertificateEndDate()));
            }
            if(Objects.nonNull(userExtendsVO.getCertificateStartDate())) {
                userExtendsBO.setCertificateStartDateStr(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN).format(userExtendsVO.getCertificateStartDate()));
            }
            if(Objects.nonNull(userExtendsVO.getLeaveDateTime())) {
                userExtendsBO.setLeaveDateTimeStr(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN).format(userExtendsVO.getLeaveDateTime()));
            }
            if(Objects.nonNull(userExtendsVO.getOnboardingDateTime())) {
                userExtendsBO.setOnboardingDateTimeStr(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN).format(userExtendsVO.getOnboardingDateTime()));
            }
            userAgentCertification.setUserExtendsBO(userExtendsBO);
        }
    }
}
