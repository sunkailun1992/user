package com.gb.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.account.entity.bo.UserRoleBO;
import com.gb.account.entity.enums.UserFormalStateEnum;
import com.gb.account.entity.query.UserQuery;
import com.gb.account.entity.query.UserRoleQuery;
import com.gb.account.entity.vo.UserVO;
import com.gb.account.service.UserRoleService;
import com.gb.account.service.UserService;
import com.gb.bean.GongBaoConfig;
import com.gb.mq.insurance.UpdateInsuranceAssistantEvent;
import com.gb.rpc.component.RpcComponent;
import com.gb.user.entity.TeamUser;
import com.gb.user.entity.TeamUserDataPermissions;
import com.gb.user.entity.bo.TeamUserBO;
import com.gb.user.entity.bo.TeamUserDataPermissionsBO;
import com.gb.user.entity.enums.TeamUserTypeEnum;
import com.gb.user.entity.query.TeamAuthBrokerQuery;
import com.gb.user.entity.query.TeamGroupValueLimitQuery;
import com.gb.user.entity.query.TeamUserDataPermissionsQuery;
import com.gb.user.entity.query.TeamUserQuery;
import com.gb.user.entity.vo.*;
import com.gb.user.enums.MqNoticeTypeEnum;
import com.gb.user.enums.RoleUserTypeRelatedEnum;
import com.gb.user.mapper.TeamUserMapper;
import com.gb.user.service.TeamGroupValueLimitService;
import com.gb.user.service.TeamService;
import com.gb.user.service.TeamUserDataPermissionsService;
import com.gb.user.service.TeamUserService;
import com.gb.user.service.query.TeamUserServiceQuery;
import com.gb.user.service.results.TeamUserServiceResults;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.ParameterNullException;
import com.gb.utils.exception.PreventRepeatException;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.gb.rpc.enums.RpcTypeEnum.INSURANCE_STATISTICS_QUERY;


/**
 * TODO 团队人员，Service服务实现层
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserServiceImpl
 * @time 2022-08-31 11:01:59
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamUserServiceImpl extends ServiceImpl<TeamUserMapper, TeamUser> implements TeamUserService {


    /**
     * 团队人员
     */
    private TeamUserMapper teamUserMapper;

    /**
     * 团队人员
     */
    private TeamUserServiceResults teamUserServiceResults;


    /**
     * 团队人员增强条件
     */
    private TeamUserServiceQuery teamUserServiceQuery;


    /**
     * 团队人员数据权限
     */
    private TeamUserDataPermissionsService teamUserDataPermissionsService;


    /**
     * 团队组别限制
     */
    private TeamGroupValueLimitService teamGroupValueLimitService;


    /**
     * 远程组件
     */
    private RpcComponent rpcComponent;


    /**
     * 用户服务
     */
    private UserService userService;

    /**
     * 用户角色服务
     */
    private UserRoleService userRoleService;

    /**
     * 团队服务
     */
    private TeamService teamService;

    /**
     * TODO 集合
     *
     * @param teamUserQuery 团队人员
     * @return List<TeamUserVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-08-31 11:01:59
     */
    @Override
    public Object listEnhance(TeamUserQuery teamUserQuery) {
        TeamUser teamUser = GeneralConvertor.convertor(teamUserQuery, TeamUser.class);
        QueryWrapper<TeamUser> queryWrapper = new QueryWrapper<>(teamUser);
        // TODO 自动生成查询，禁止手动写语句
        teamUserServiceQuery.query(teamUserQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamUserQuery, queryWrapper);
        // DO数据
        List<TeamUser> teamUserDO = teamUserMapper.selectList(queryWrapper);
        // VO数据
        List<TeamUserVO> teamUserList = GeneralConvertor.convertor(teamUserDO, TeamUserVO.class);
        // 判断是否增强
        if(Objects.isNull(teamUserQuery.getAssignment()) || teamUserQuery.getAssignment()) {
            teamUserList = teamUserServiceResults.assignment(teamUserList);
        }
        //TODO: 查询结果以userName为key，map结果返回 -- 【后期优化，存在同名的情况，产品需要变更（姓名和ID的格式进行展示）】
        if(Objects.nonNull(teamUserQuery.getBackMapQueryResult()) && teamUserQuery.getBackMapQueryResult()) {
            IdentityHashMap<String,TeamUserVO> identityHashMap = Maps.newIdentityHashMap();
            for(TeamUserVO teamUserVO : teamUserList) {
                //如果姓名是空的情况，不能作为key值，初始化数据时，生产的数据不一定存在在灰度和小工保网的环境
                if(StringUtils.isBlank(teamUserVO.getUserName())) {
                    continue;
                }
                identityHashMap.put(teamUserVO.getUserName(), teamUserVO);
            }
            return identityHashMap;
        }
        return teamUserList;
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param teamUserQuery 团队人员
     * @return Page<TeamUserVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-08-31 11:01:59
     */
    @Override
    public Page<TeamUserVO> pageEnhance(Page page, TeamUserQuery teamUserQuery) {
        if(Objects.nonNull(teamUserQuery.getTeamLevelQuery()) && teamUserQuery.getTeamLevelQuery() && StringUtils.isNotBlank(teamUserQuery.getTeamId())) {
            List<TeamVO> teamList = teamService.teamLevelQuery(true, teamUserQuery.getTeamId(), null, null);
            if(CollectionUtils.isNotEmpty(teamList)) {
                teamUserQuery.setTeamId(null);
                List<String> teamIdList = teamList.stream().map(s -> s.getId()).collect(Collectors.toList());
                teamUserQuery.setTeamIdList(teamIdList);
            }
        }
        TeamUser teamUser = GeneralConvertor.convertor(teamUserQuery, TeamUser.class);
        QueryWrapper<TeamUser> queryWrapper = new QueryWrapper<>(teamUser);
        // TODO 自动生成查询，禁止手动写语句
        teamUserServiceQuery.query(teamUserQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamUserQuery, queryWrapper);
        // DO数据
        Page<TeamUser> pageDO = teamUserMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<TeamUserVO> pageVO = teamUserServiceResults.toPageVO(pageDO);
        // 判断是否增强
        if (Objects.isNull(teamUserQuery.getAssignment()) || teamUserQuery.getAssignment()) {
            return teamUserServiceResults.assignment(pageVO);
        }
        return pageVO;
    }


    /**
     * TODO 单条
     *
     * @param teamUserQuery 团队人员
     * @return TeamUserVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-08-31 11:01:59
     */
    @Override
    public TeamUserVO getOneEnhance(TeamUserQuery teamUserQuery) {
        TeamUser teamUser = GeneralConvertor.convertor(teamUserQuery, TeamUser.class);
        QueryWrapper<TeamUser> queryWrapper = new QueryWrapper<>(teamUser);
        // TODO 自动生成查询，禁止手动写语句
        teamUserServiceQuery.query(teamUserQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamUserQuery, queryWrapper);
        // DO数据
        TeamUser teamUserDO = teamUserMapper.selectOne(queryWrapper);
        // VO数据
        TeamUserVO teamUserVO = GeneralConvertor.convertor(teamUserDO, TeamUserVO.class);
        // 判断是否增强
        if(Objects.isNull(teamUserQuery.getAssignment()) || teamUserQuery.getAssignment()) {
            return teamUserServiceResults.assignment(teamUserVO);
        }
        return teamUserVO;
    }


    /**
     * TODO 总数
     *
     * @param teamUserQuery 团队人员
     * @return Integer
     * @author sunx
     * @methodName countEnhance
     * @time 2022-08-31 11:01:59
     */
    @Override
    public Long countEnhance(TeamUserQuery teamUserQuery) {
        TeamUser teamUser = GeneralConvertor.convertor(teamUserQuery, TeamUser.class);
        QueryWrapper<TeamUser> queryWrapper = new QueryWrapper<>(teamUser);
        // TODO 自动生成查询，禁止手动写语句
        teamUserServiceQuery.query(teamUserQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamUserQuery, queryWrapper);
        return teamUserMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param teamUserBO 团队人员
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-08-31 11:01:59
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(TeamUserBO teamUserBO) {
        validateParams(teamUserBO, true, true);
        TeamUser teamUser = GeneralConvertor.convertor(teamUserBO, TeamUser.class);
        int num = teamUserMapper.insert(teamUser);
        if(num < 1 ) {
            throw new BusinessException("人员管理新增失败！");
        }
        //团体数据权限
        if(!teamUserBO.getPersonal()) {
            log.debug("新增团队权限--请求参数信息：{}", JSON.toJSONString(teamUserBO));
            setTeamPermissons(teamUserBO.getAuthTeamIdArray(), teamUserBO.getUserId(), teamUserBO.getCreateName());
        }
        //设置团队成员角色
        setTeamUserRole(teamUserBO, null, null);
        return teamUser.getId();
    }


    /**
     * TODO 修改
     *
     * @param teamUserBO 团队人员
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-08-31 11:01:59
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void updateEnhance(TeamUserBO teamUserBO) {
        //判断待编辑的人员管理记录是否存在
        TeamUserVO teamUserVO = getOneEnhance(new TeamUserQuery(){{
            setId(teamUserBO.getId());
        }});
        if(Objects.isNull(teamUserVO)) {
            throw new ParameterNullException("待编辑的人员管理记录不存在！");
        }
        //校验编辑的参数信息
        validateParams(teamUserBO, !StringUtils.equals(teamUserVO.getUserId(), teamUserBO.getUserId()), !StringUtils.equals(teamUserVO.getTeamGroupValueLimitId(), teamUserBO.getTeamGroupValueId()));
        //修改人员管理信息
        TeamUser teamUser = GeneralConvertor.convertor(teamUserBO, TeamUser.class);
        UpdateWrapper<TeamUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", teamUserBO.getId());
        Integer i = teamUserMapper.update(teamUser, updateWrapper);
        if(i < 1 ) {
            throw new BusinessException("人员管理编辑失败！");
        }
        //修改成员权限
        if(Objects.nonNull(teamUserBO.getPersonal())) {
            log.debug("更新团队权限--请求参数信息：{}", JSON.toJSONString(teamUserBO));
            setTeamPermissons(teamUserBO.getAuthTeamIdArray(), teamUserBO.getUserId(), teamUserBO.getModifyName());
        }
        //设置团队成员角色
        setTeamUserRole(teamUserBO, teamUserVO.getUserId(), teamUserVO.getType());
        //MQ通知--查询当前成员下是否有未完成的订单，如果有，需要更新未完成订单对应的业务助理为当前的业务助理
        List<String> userIdList = Lists.newArrayList();
        userIdList.add(teamUserBO.getUserId());
        userIdList.add(teamUserBO.getAssistantUserId());
        List<UserVO> userVoList = userService.listEnhance(new UserQuery(){{
            setUserIdList(userIdList);
        }});
        Map<String, UserVO> userMap = userVoList.stream().collect(Collectors.toMap(UserVO :: getId, data -> data));
        UpdateInsuranceAssistantEvent event = new UpdateInsuranceAssistantEvent()
                .setTeamUserId(teamUserBO.getUserId())
                .setTeamUserName(userMap.get(teamUserBO.getUserId()).getName())
                .setTeamUserMobile(userMap.get(teamUserBO.getUserId()).getMobile())
                .setAssistantUserId(teamUserBO.getAssistantUserId())
                .setAssistantUserName(userMap.get(teamUserBO.getAssistantUserId()).getName())
                .setAssistantUserMobile(userMap.get(teamUserBO.getAssistantUserId()).getMobile());
        MqNoticeTypeEnum.UPDATE_INSURANCE_ASSISTANT_MQ.pushMqMessage("人员管理编辑", event);
    }


    /**
     * TODO 删除
     *
     * @param teamUserBO 团队人员
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-08-31 11:01:59
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(TeamUserBO teamUserBO) {
        TeamUser teamUser = GeneralConvertor.convertor(teamUserBO, TeamUser.class);
        QueryWrapper<TeamUser> queryWrapper = new QueryWrapper<>(teamUser);
        Integer i = teamUserMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }

    @Override
    public Page<TeamUserPolicyVO> teamAuthBrokerSelect(Page page, TeamAuthBrokerQuery teamAuthBrokerQuery) {
        if(Objects.nonNull(teamAuthBrokerQuery.getTeamLevelQuery()) && teamAuthBrokerQuery.getTeamLevelQuery()) {
            List<TeamVO> teamVOList = teamService.teamLevelQuery(true, teamAuthBrokerQuery.getTeamId(), null, null);
            if(CollectionUtils.isEmpty(teamVOList)) {
                return new Page<>();
            }
            String teamId = teamVOList.stream().map(TeamVO::getId).collect(Collectors.joining("','"));
            teamAuthBrokerQuery.setTeamId(teamId);
        }
        //查询团队权限下的所有成员--分页
        QueryWrapper<TeamUser> queryWrapper = new QueryWrapper();
        Page<TeamUser> teamUserPage = (Page<TeamUser>) teamUserMapper.teamAuthBrokerSelect(page, teamAuthBrokerQuery.getTeamId(), teamAuthBrokerQuery.getAuthUserId(), teamAuthBrokerQuery.getTeamUserId(), queryWrapper);
        //对团队权限下经纪人查询结果进行处理
        List<TeamUserPolicyVO> teamUserPolicyList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(teamUserPage.getRecords())) {
            //查询保险统计
            String[] teamUserIdArray = teamUserPage.getRecords().stream().map(TeamUser::getUserId).toArray(String[]::new);
            Map<String, Object> insuanceStatisticsMap = Maps.newHashMap();
            insuanceStatisticsMap.put("agentUserIds", teamUserIdArray);
            insuanceStatisticsMap.put("startTime", teamAuthBrokerQuery.getSigningStartDate());
            insuanceStatisticsMap.put("endTime", teamAuthBrokerQuery.getSigningEndDate());
            Map<String, Object> policyMap = rpcComponent.rpcQuery(insuanceStatisticsMap, INSURANCE_STATISTICS_QUERY, Map.class);
            Map<String, Object> insuranceTransactionPremiumMap = (Map<String, Object>) policyMap.get("insuranceTransactionPremiumMap");
            Map<String, Object> insuranceSettlementPremiumMap = (Map<String, Object>) policyMap.get("insuranceSettlementPremiumMap");
            //查询团队成员的详细信息
            for (TeamUser teamUser : teamUserPage.getRecords()) {
                TeamUserPolicyVO teamUserPolicyVO = BeanUtil.toBean(teamUser, TeamUserPolicyVO.class);
                if (MapUtils.isNotEmpty(insuranceTransactionPremiumMap) && insuranceTransactionPremiumMap.containsKey(teamUserPolicyVO.getUserId()) && Objects.nonNull(insuranceTransactionPremiumMap.get(teamUserPolicyVO.getUserId()))) {
                    teamUserPolicyVO.setFinalPremiums((double) insuranceTransactionPremiumMap.get(teamUserPolicyVO.getUserId()));
                }
                if (MapUtils.isNotEmpty(insuranceSettlementPremiumMap) && insuranceSettlementPremiumMap.containsKey(teamUserPolicyVO.getUserId()) && Objects.nonNull(insuranceSettlementPremiumMap.get(teamUserPolicyVO.getUserId()))) {
                    teamUserPolicyVO.setInsuranceSettlementPremium((double)insuranceSettlementPremiumMap.get(teamUserPolicyVO.getUserId()));
                }
                teamUserPolicyList.add(teamUserPolicyVO);
            }
        }
        //组织响应的结果
        Page<TeamUserPolicyVO> teamUserPolicyPage = new Page<TeamUserPolicyVO>(teamUserPage.getCurrent(), teamUserPage.getSize());
        teamUserPolicyPage.setTotal(teamUserPage.getTotal());
        teamUserPolicyPage.setPages(teamUserPage.getPages());
        teamUserPolicyPage.setRecords(teamUserPolicyList);
        return teamUserPolicyPage;
    }

    @Override
    public Object teamAuthBrokerSelectList(TeamAuthBrokerQuery teamAuthBrokerQuery) {
        List<TeamUser> teamUserList = Lists.newArrayList();
        if(StringUtils.isNotBlank(GongBaoConfig.crmAuthUserIds) && GongBaoConfig.crmAuthUserIds.contains(teamAuthBrokerQuery.getAuthUserId())) {
            QueryWrapper<TeamUser> queryWrapper = new QueryWrapper();
            if(Objects.nonNull(teamAuthBrokerQuery.getType())) {
                queryWrapper.eq("type", teamAuthBrokerQuery.getType());
            }
            if(StringUtils.isNotBlank(teamAuthBrokerQuery.getTeamUserNameQuery())) {
                String sql = "select `user_extends`.`user_id` from `user`,`user_extends` where `user`.`is_delete` = 0 and `user_extends`.`is_delete` = 0 and `user`.`is_delete` = 0 and `user_extends`.`user_id` = `user`.`id` and `user_extends`.`name` like '" +teamAuthBrokerQuery.getTeamUserNameQuery() +"%'";
                queryWrapper.inSql("user_id", sql);
            }
            teamUserList = teamUserMapper.selectList(queryWrapper);

        } else {
            if(Objects.nonNull(teamAuthBrokerQuery.getTeamLevelQuery()) && teamAuthBrokerQuery.getTeamLevelQuery()) {
                List<TeamVO> teamVOList = teamService.teamLevelQuery(true, teamAuthBrokerQuery.getTeamId(), null, null);
                if(CollectionUtils.isEmpty(teamVOList)) {
                    return Lists.newArrayList();
                }
                String teamId = teamVOList.stream().map(TeamVO::getId).collect(Collectors.joining(","));
                teamAuthBrokerQuery.setTeamId(teamId);
            }
            teamUserList = teamUserMapper.queryAuthBrokerCount(teamAuthBrokerQuery.getTeamUserNameQuery(), teamAuthBrokerQuery.getTeamId(), teamAuthBrokerQuery.getAuthUserId(), teamAuthBrokerQuery.getTeamUserId(), teamAuthBrokerQuery.getType());
        }
        //查询团队权限下的所有成员--统计
        if(CollectionUtils.isEmpty(teamUserList)) {
            return Lists.newArrayList();
        }
        if(Objects.isNull(teamAuthBrokerQuery.getBackResultGroup()) || !teamAuthBrokerQuery.getBackResultGroup()) {
            return GeneralConvertor.convertor(teamUserList, TeamUserVO.class);
        }
        List<TeamGroupUserVO> teamPermissionsUserList = Lists.newArrayList();
        Map<String, List<TeamUser>> teamUserMap = teamUserList.stream().collect(Collectors.groupingBy(TeamUser::getTeamId));
        for (Map.Entry<String, List<TeamUser>> map : teamUserMap.entrySet()) {
            TeamGroupUserVO teamGroupUserVO = new TeamGroupUserVO();
            teamGroupUserVO.setTeamId(map.getKey());
            List<TeamUser> list = map.getValue();
            teamGroupUserVO.setTeamName(list.get(0).getTeamName());
            teamGroupUserVO.setTeamUserList(GeneralConvertor.convertor(list, TeamUserVO.class));
            teamPermissionsUserList.add(teamGroupUserVO);
        }
        return teamPermissionsUserList;
    }

    @Override
    public Map<String, Object> queryAuthBrokerCount(TeamAuthBrokerQuery teamAuthBrokerQuery) {
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("insuranceAmountTotal", 0);
        resultMap.put("insurancePremiumTotal", 0);
        resultMap.put("insuranceCount", 0);
        resultMap.put("teamUserTotal", 0);
        resultMap.put("insuranceSettlementTotal", 0);
        if(Objects.nonNull(teamAuthBrokerQuery.getTeamLevelQuery()) && teamAuthBrokerQuery.getTeamLevelQuery()) {
            List<TeamVO> teamVOList = teamService.teamLevelQuery(true, teamAuthBrokerQuery.getTeamId(), null, null);
            if(CollectionUtils.isEmpty(teamVOList)) {
                return resultMap;
            }
            String teamId = teamVOList.stream().map(TeamVO::getId).collect(Collectors.joining(","));
            teamAuthBrokerQuery.setTeamId(teamId);
        }
        //查询团队权限下的所有成员--统计
        List<TeamUser> teamUserList = teamUserMapper.queryAuthBrokerCount(teamAuthBrokerQuery.getTeamUserNameQuery(), teamAuthBrokerQuery.getTeamId(), teamAuthBrokerQuery.getAuthUserId(), teamAuthBrokerQuery.getTeamUserId(), teamAuthBrokerQuery.getType());
        //对团队权限下经纪人查询结果进行处理
        if(CollectionUtils.isNotEmpty(teamUserList)) {
            //查询保险统计
            String[] teamUserIdArray = teamUserList.stream().map(TeamUser::getUserId).toArray(String[]::new);
            Map<String, Object> insuanceStatisticsMap = Maps.newHashMap();
            insuanceStatisticsMap.put("agentUserIds", teamUserIdArray);
            insuanceStatisticsMap.put("startTime", teamAuthBrokerQuery.getSigningStartDate());
            insuanceStatisticsMap.put("endTime", teamAuthBrokerQuery.getSigningEndDate());
            Map<String, Object> policyMap = rpcComponent.rpcQuery(insuanceStatisticsMap, INSURANCE_STATISTICS_QUERY, Map.class);
            resultMap.put("insuranceAmountTotal", policyMap.get("insuranceAmountTotal"));
            resultMap.put("insurancePremiumTotal", policyMap.get("insurancePremiumTotal"));
            resultMap.put("insuranceCount", policyMap.get("insuranceCount"));
            resultMap.put("insuranceSettlementTotal", policyMap.get("insuranceSettlementTotal"));
        }
        resultMap.put("teamUserTotal", teamUserList.size());
        return resultMap;
    }


    /**
     * TODO 人工查询条件
     *
     * @param teamUserQuery 团队人员
     * @return QueryWrapper
     * @author sunx
     * @methodName queryArtificial
     * @time 2022-08-31 11:01:59
     */
    private QueryWrapper queryArtificial(TeamUserQuery teamUserQuery, QueryWrapper<TeamUser> queryWrapper) {
        if(CollectionUtils.isNotEmpty(teamUserQuery.getTeamIdList())) {
            queryWrapper.in("team_id", teamUserQuery.getTeamIdList());
        }
        if(CollectionUtils.isNotEmpty(teamUserQuery.getUserIdList())) {
            queryWrapper.in("user_id", teamUserQuery.getUserIdList());
        }
        StringBuffer sbf = new StringBuffer();
        if(StringUtils.isNotBlank(teamUserQuery.getUserName())) {
            sbf.append(" and `user_extends`.`name` = '" + teamUserQuery.getUserName()+"'");
        }
        if(StringUtils.isNotBlank(teamUserQuery.getUserNameQuery())) {
            sbf.append(" and `user_extends`.`name` like '" + teamUserQuery.getUserNameQuery() + "%'");
        }
        if(StringUtils.isNotBlank(teamUserQuery.getMobile())) {
            sbf.append(" and `user_extends`.`mobile` = '" + teamUserQuery.getMobile() + "'");
        }
        if(Objects.nonNull(teamUserQuery.getUserFormalStateEnum())) {
            if(teamUserQuery.getUserFormalStateEnum().equals(UserFormalStateEnum.离职) || teamUserQuery.getUserFormalStateEnum().equals(UserFormalStateEnum.注销)) {
                sbf.append(" and `user_extends`.`leave_date_time` is not null");
                if(teamUserQuery.getUserFormalStateEnum().equals(UserFormalStateEnum.注销)) {
                    sbf.append(" and `user`.`state` = 1");
                }
            }else{
                sbf.append(" and `user_extends`.`leave_date_time` is null and `user`.`state` = " + teamUserQuery.getUserFormalStateEnum().getValue());
            }
        }
        if(sbf.length() > 0) {
            String sql = "select `user_extends`.`user_id` from `user`,`user_extends` where `user`.`is_delete` = 0 and `user_extends`.`is_delete` = 0 and `user`.`is_delete` = 0 and `user_extends`.`user_id` = `user`.`id`" + sbf.toString();
            queryWrapper.inSql("user_id", sql);
        }
        sbf.setLength(0);
        if(StringUtils.isNotBlank(teamUserQuery.getExternalPlatformCode())) {
            sbf.append("and `transformation_external_platform_system`.`external_platform_code` = '" + teamUserQuery.getExternalPlatformCode() + "'");
        }
        if(StringUtils.isNotBlank(teamUserQuery.getExternalSystemId())) {
            sbf.append("and `transformation_external_platform_system`.`external_system_id` = '" + teamUserQuery.getExternalSystemId() + "'");
        }
        if(StringUtils.isNotBlank(teamUserQuery.getTransformationExternalPlatformSystemId())) {
            sbf.append("and `transformation_external_platform_system`.`id` = '" + teamUserQuery.getTransformationExternalPlatformSystemId() + "'");
        }
        if(sbf.length() > 0) {
            String sql = "select `transformation_external_platform_system_user`.`user_id` from `transformation_external_platform_system`,`transformation_external_platform_system_user` where `transformation_external_platform_system_user`.`is_delete` = 0 and `transformation_external_platform_system`.`id` = `transformation_external_platform_system_user`.`transformation_external_platform_system_id` " + sbf.toString();
            queryWrapper.inSql("user_id", sql);
        }
        return queryWrapper;
    }

    /**
     * 校验参数信息
     * @param teamUserBO 团队成员BO
     * @param checkUserId 是否校验成员
     * @param checkGroupLimited 是否校验组别限制ID
     */

    private void validateParams(TeamUserBO teamUserBO, Boolean checkUserId, Boolean checkGroupLimited) {
        //校验成员是否已经分配了团队
        if(StringUtils.isNotBlank(teamUserBO.getUserId())) {
            if(userService.countEnhance(new UserQuery() {{setId(teamUserBO.getUserId());}}) < 1) {
                throw new ParameterNullException("未找到该成员信息！");
            }
            if(checkUserId && countEnhance(new TeamUserQuery(){{
                setUserId(teamUserBO.getUserId());
            }}) > 0) {
                throw new ParameterNullException("该成员已分配了团队！");
            }
        }
        //校验业务助理是否存在
        if(StringUtils.isNotBlank(teamUserBO.getAssistantUserId())) {
            if(userService.countEnhance(new UserQuery() {{setId(teamUserBO.getAssistantUserId());}}) < 1) {
                throw new ParameterNullException("未找到该业务助理信息！");
            }
        }
        //校验销售类别
        if(Objects.nonNull(teamUserBO.getType())) {
            //分销
            if(teamUserBO.getType().equals(TeamUserTypeEnum.分销)) {
                if(Objects.isNull(teamUserBO.getChannel())) {
                    throw new ParameterNullException("是否渠道经纪人未选择！");
                }
            }
            //自营
            if(teamUserBO.getType().equals(TeamUserTypeEnum.自营)) {
                if(Objects.nonNull(teamUserBO.getChannel()) && teamUserBO.getChannel()) {
                    throw new ParameterNullException("自营的团队人员，不能是渠道经纪人！");
                }
            }
            if(StringUtils.isBlank(teamUserBO.getTeamGroupId()) || StringUtils.isBlank(teamUserBO.getTeamGroupValueId())) {
                throw new ParameterNullException("组别或岗位未选择！");
            }
        }
        //校验数据权限
        if(Objects.nonNull(teamUserBO.getPersonal())) {
            if(teamUserBO.getPersonal()) {
                //个人权限
                if(Objects.nonNull(teamUserBO.getAuthTeamIdArray()) && teamUserBO.getAuthTeamIdArray().length > 0) {
                    throw new ParameterNullException("个人权限不能选择团队权限的具体团队！");
                }
            } else {
                //团队权限
                if(Objects.isNull(teamUserBO.getAuthTeamIdArray()) || teamUserBO.getAuthTeamIdArray().length < 1) {
                    throw new ParameterNullException("团队权限未选择具体团队！");
                }
            }
        }
        //校验团队组别限制
        if(StringUtils.isNotBlank(teamUserBO.getTeamGroupValueId()) && checkGroupLimited) {
            TeamGroupValueLimitVO teamGroupValueLimitVO = teamGroupValueLimitService.getOneEnhance(new TeamGroupValueLimitQuery(){{
                setTeamGroupValueId(teamUserBO.getTeamGroupValueId());
            }});
            if(Objects.isNull(teamGroupValueLimitVO)) {
                log.error("未找到团队组别限制配置信息，{}该团队组别值序列对应的团队组别限制配置信息不存在！", teamUserBO.getTeamGroupValueId());
                throw new ParameterNullException("未找到团队组别限制配置信息！");
            }
            teamUserBO.setTeamGroupValueLimitId(teamGroupValueLimitVO.getId());
        }
    }

    /**
     * 团队成员的权限设定
     * @param authTeamIdArray 授权的团队权限数组
     * @param userId 团队成员
     * @param createName 创建人
     */
    private void setTeamPermissons(String[] authTeamIdArray, String userId, String createName) {
        //团体数据权限--删除
        if(teamUserDataPermissionsService.countEnhance(new TeamUserDataPermissionsQuery() {{
            setTeamUserId(userId);
        }}) > 0 ) {
            teamUserDataPermissionsService.removeEnhance(new TeamUserDataPermissionsBO() {{
                setTeamUserId(userId);
            } });
        }
        if(Objects.isNull(authTeamIdArray) || authTeamIdArray.length < 1) {
            return;
        }
        //团体数据权限--校验
        Set<TeamUserDataPermissions> teamUserDataPermissionsBOList = Sets.newHashSet();
        for(String authTeamId : authTeamIdArray) {
            if(StringUtils.isBlank(authTeamId)) {
                continue;
            }
            TeamUserDataPermissions teamUserDataPermissions = new TeamUserDataPermissions();
            teamUserDataPermissions.setTeamUserId(userId);
            teamUserDataPermissions.setTeamId(authTeamId);
            teamUserDataPermissions.setCreateName(createName);
            if(!teamUserDataPermissionsBOList.add(teamUserDataPermissions)) {
                throw new PreventRepeatException("存在重复的团队权限，请重新选择！");
            };
        }
        if(CollectionUtils.isEmpty(teamUserDataPermissionsBOList)) {
            return;
        }
        teamUserDataPermissionsService.saveBatch(teamUserDataPermissionsBOList);
    }

    /**
     * 设置团队成员角色
     * @param teamUserBO 团队用户BO
     */
    private void setTeamUserRole(TeamUserBO teamUserBO, String oldTeamUserId, TeamUserTypeEnum oldTeamUserTypeEnum) {
        if(Objects.isNull(teamUserBO.getType())) {
            return;
        }
        //待修改团队成员ID与修改成员ID，不一致的情况时，删除待修改成员ID--对应的角色信息
        UserRoleQuery userRoleQuery = new UserRoleQuery();
        UserRoleBO userRoleBO = new UserRoleBO();
        String roleCode = RoleUserTypeRelatedEnum.自营客户管理角色.getRoleCode() + "," + RoleUserTypeRelatedEnum.分销客户管理角色.getRoleCode();
        if(StringUtils.isNotBlank(oldTeamUserId) && Objects.nonNull(oldTeamUserTypeEnum) && !StringUtils.equals(teamUserBO.getUserId(), oldTeamUserId)) {
            userRoleQuery.setUserId(oldTeamUserId);
            userRoleQuery.setRoleCode(roleCode);
            if(userRoleService.countEnhance(userRoleQuery) > 0) {
                userRoleBO.setUserId(oldTeamUserId);
                userRoleBO.setRoleCode(roleCode);
                userRoleService.removeEnhance(userRoleBO);
            }
        }
        //新增--销售类型对应-修改团队成员ID角色记录
        userRoleBO.setUserId(teamUserBO.getUserId());
        roleCode = teamUserBO.getType().equals(TeamUserTypeEnum.自营)? RoleUserTypeRelatedEnum.自营客户管理角色.getRoleCode() : RoleUserTypeRelatedEnum.分销客户管理角色.getRoleCode();
        userRoleQuery.setUserId(teamUserBO.getUserId());
        userRoleQuery.setRoleCode(roleCode);
        if(userRoleService.countEnhance(userRoleQuery) < 1) {
            String createName = StringUtils.isBlank(teamUserBO.getCreateName()) ? teamUserBO.getModifyName() : teamUserBO.getCreateName();
            userRoleBO.setCreateName(createName);
            userRoleBO.setRoleCode(roleCode);
            userRoleService.saveEnhance(userRoleBO);
        }
        //删除--相反销售类型-修改团队成员ID角色记录
        roleCode = teamUserBO.getType().equals(TeamUserTypeEnum.自营)? RoleUserTypeRelatedEnum.分销客户管理角色.getRoleCode() : RoleUserTypeRelatedEnum.自营客户管理角色.getRoleCode();
        userRoleQuery.setRoleCode(roleCode);
        if(userRoleService.countEnhance(userRoleQuery) > 0) {
            userRoleBO.setCreateName(null);
            userRoleBO.setRoleCode(roleCode);
            userRoleService.removeEnhance(userRoleBO);
        }
    }
}