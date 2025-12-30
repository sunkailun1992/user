package com.gb.user.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.user.entity.Team;
import com.gb.user.entity.TeamTreeNode;
import com.gb.user.entity.bo.TeamBO;
import com.gb.user.entity.query.TeamQuery;
import com.gb.user.entity.query.TeamUserDataPermissionsQuery;
import com.gb.user.entity.vo.TeamUserDataPermissionsVO;
import com.gb.user.entity.vo.TeamVO;
import com.gb.user.mapper.TeamMapper;
import com.gb.user.service.TeamService;
import com.gb.user.service.TeamUserDataPermissionsService;
import com.gb.user.service.query.TeamServiceQuery;
import com.gb.user.service.results.TeamServiceResults;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.exception.ParameterNullException;
import com.gb.utils.exception.PreventRepeatException;
import com.google.common.collect.Lists;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * TODO 团队，Service服务实现层
 * 代码生成器
 *
 * @author sunx
 * @className TeamServiceImpl
 * @time 2022-08-30 04:44:17
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {


    /**
     * 团队
     */
    private TeamMapper teamMapper;


    /**
     * 团队
     */
    private TeamServiceResults teamServiceResults;


    /**
     * 团队增强条件
     */
    private TeamServiceQuery teamServiceQuery;


    /**
     * 团队人员权限增强条件
     */
    private TeamUserDataPermissionsService teamUserDataPermissionsService;


    /**
     * TODO 集合
     *
     * @param teamQuery 团队
     * @return List<TeamVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-08-30 04:44:17
     */
    @Override
    public List<TeamVO> listEnhance(TeamQuery teamQuery) {
        Team team = GeneralConvertor.convertor(teamQuery, Team.class);
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>(team);
        // TODO 自动生成查询，禁止手动写语句
        teamServiceQuery.query(teamQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamQuery, queryWrapper);
        // DO数据
        List<Team> teamDO = teamMapper.selectList(queryWrapper);
        // VO数据
        List<TeamVO> teamVO = GeneralConvertor.convertor(teamDO, TeamVO.class);
        // 判断是否增强
        if(Objects.nonNull(teamQuery.getAssignment()) && teamQuery.getAssignment()) {
            return teamServiceResults.assignment(teamVO);
        }
        return teamVO;
    }


    @Override
    public List<TeamVO> teamAuthSelect(String authUserId, String nameQuery, Boolean teamLevelQuery) {
        TeamQuery teamQuery = new TeamQuery();
        teamQuery.setAuthUserId(authUserId);
        if(Objects.nonNull(teamLevelQuery) && teamLevelQuery) {
            List<TeamUserDataPermissionsVO> teamUserDataPermissionsVOList = teamUserDataPermissionsService.listEnhance(new TeamUserDataPermissionsQuery(){{
                setTeamUserId(authUserId);
                setAssignment(false);
            }});
            List<String> idList = Lists.newArrayList();
            idList.add("0");
            teamQuery.setIdList(idList);
            if(CollectionUtils.isNotEmpty(teamUserDataPermissionsVOList)) {
                List<String> authIdList = teamUserDataPermissionsVOList.stream().map(s -> s.getTeamId()).collect(Collectors.toList());
                List<String> levelIdList = teamLevelQuery(true, null, nameQuery, authIdList).stream().map(s -> s.getId()).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(levelIdList)) {
                    teamQuery.setIdList(levelIdList);

                }
            }
            teamQuery.setAuthUserId(null);
        }
        return listEnhance(teamQuery);
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param teamQuery 团队
     * @return Page<TeamVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-08-30 04:44:17
     */
    @Override
    public Page<TeamVO> pageEnhance(Page page, TeamQuery teamQuery) {
        Team team = GeneralConvertor.convertor(teamQuery, Team.class);
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>(team);
        // TODO 自动生成查询，禁止手动写语句
        teamServiceQuery.query(teamQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamQuery, queryWrapper);
        // DO数据
        Page<Team> pageDO = teamMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<TeamVO> pageVO = teamServiceResults.toPageVO(pageDO);
        // 判断是否增强
        if (Objects.isNull(teamQuery.getAssignment()) || teamQuery.getAssignment()) {
            return teamServiceResults.assignment(pageVO);
        }
        return pageVO;
    }


    /**
     * TODO 单条
     *
     * @param teamQuery 团队
     * @return TeamVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-08-30 04:44:17
     */
    @Override
    public TeamVO getOneEnhance(TeamQuery teamQuery) {
        Team team = GeneralConvertor.convertor(teamQuery, Team.class);
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>(team);
        // TODO 自动生成查询，禁止手动写语句
        teamServiceQuery.query(teamQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamQuery, queryWrapper);
        // DO数据
        Team teamDO = teamMapper.selectOne(queryWrapper);
        // VO数据
        TeamVO teamVO = GeneralConvertor.convertor(teamDO, TeamVO.class);
        // 判断是否增强
        if (teamQuery.getAssignment() == null) {
            return teamServiceResults.assignment(teamVO);
        } else {
            return teamQuery.getAssignment() ? teamServiceResults.assignment(teamVO) : teamVO;
        }
    }


    /**
     * TODO 总数
     *
     * @param teamQuery 团队
     * @return Integer
     * @author sunx
     * @methodName countEnhance
     * @time 2022-08-30 04:44:17
     */
    @Override
    public Long countEnhance(TeamQuery teamQuery) {
        Team team = GeneralConvertor.convertor(teamQuery, Team.class);
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>(team);
        // TODO 自动生成查询，禁止手动写语句
        teamServiceQuery.query(teamQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamQuery, queryWrapper);
        return teamMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param teamBO 团队
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-08-30 04:44:17
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(TeamBO teamBO) {
        Team team = new Team();
        team.setName(teamBO.getName());
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>(team);
        if(teamMapper.exists(queryWrapper)) {
            throw new PreventRepeatException("您输入团队名称已存在，请修改！");
        }
        team = GeneralConvertor.convertor(teamBO, Team.class);
        String teamCode = "TN" + DateUtil.format(DateUtil.date(), DatePattern.PURE_DATETIME_PATTERN);
        team.setCode(teamCode);
        teamMapper.insert(team);
        return team.getId();
    }


    /**
     * TODO 修改
     *
     * @param teamBO 团队
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-08-30 04:44:17
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(TeamBO teamBO) {
        //校验团队名称是否存在
        if(StringUtils.isNotBlank(teamBO.getName())) {
            TeamVO teamVO = getOneEnhance(new TeamQuery() {{
                setName(teamBO.getName());
            }});
            if(Objects.nonNull(teamVO) && (!StringUtils.equals(teamVO.getId(), teamBO.getId()))) {
                throw new PreventRepeatException("您输入团队名称已存在，请修改！");
            }
        }
        Team team = GeneralConvertor.convertor(teamBO, Team.class);
        UpdateWrapper<Team> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", teamBO.getId());
        Integer i = teamMapper.update(team, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 删除
     *
     * @param teamBO 团队
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-08-30 04:44:17
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(TeamBO teamBO) {
        Team team = GeneralConvertor.convertor(teamBO, Team.class);
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>(team);
        Integer i = teamMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }

    @Override
    public List<TeamTreeNode> teamTree(Boolean advanceSelected, String teamId) {
        //查询一级团队
        if(StringUtils.isBlank(teamId)) {
            teamId = "0";
        }
        TeamQuery teamQuery = new TeamQuery();
        teamQuery.setTeamId(teamId);
        List<TeamVO> teamVOList = listEnhance(teamQuery);
        //团队树
        List<TeamTreeNode> list = Lists.newArrayList();
        //递归团队树
        recursive(advanceSelected, teamVOList, list);
        return list;
    }

    @Override
    public TeamTreeNode queryParentTeam(String teamId) {
        return upTreeNodeQuery(null, teamId).get(0);
    }

    @Override
    public List<TeamVO> teamLevelQuery(Boolean levelQuery, String teamId, String teamNameQuery, List<String> teamIdList) {
        if(StringUtils.isBlank(teamId) && StringUtils.isBlank(teamNameQuery)) {
            return Lists.newArrayList();
        }
        //查询本级团队
        List<Team> teamList = Lists.newArrayList();
        if(Objects.nonNull(teamId) && !StringUtils.equals(teamId, "0")) {
            QueryWrapper<Team> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", teamId);
            Team team = teamMapper.selectOne(queryWrapper);
            if(Objects.isNull(team)) {
                return Lists.newArrayList();
            }
            teamList.add(team);
        }
        //查询下级团队
        if(Objects.nonNull(levelQuery) && levelQuery) {
            queryChildTeamList(teamList, teamId, teamNameQuery, teamIdList);
        }
        return GeneralConvertor.convertor(teamList, TeamVO.class);
    }

    /**
     * 查询下级团队列表（查询结果包含请求的teamList参数）
     * @param teamList 团队列表
     * @param teamId 父级团队ID
     * @param teamNameQuery 父级团队名称模糊查询
     * @param teamIdList 父级团队ID列表
     */
    private void queryChildTeamList(List<Team> teamList, String teamId, String teamNameQuery, List<String> teamIdList) {
        if(Objects.isNull(teamList)) {
            throw new ParameterNullException("teamList不能为空！");
        }
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(teamId)) {
            queryWrapper.eq("team_id", teamId);
        }
        if(StringUtils.isNotBlank(teamNameQuery)) {
            queryWrapper.likeRight("name", teamNameQuery);
        }
        if(CollectionUtils.isNotEmpty(teamIdList)) {
            queryWrapper.in("id", teamIdList);
        }
        List<Team> teamResultList = teamMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(teamResultList)) {
            return;
        }
        teamList.addAll(teamResultList);
        for(Team team : teamResultList) {
            queryChildTeamList(teamList, team.getId(), null, null);
        }
    }

    /**
     * 查询当前团队的所有父团队
     * @param children 孩子团队
     * @param teamId 当前团队ID
     * @return TreeNode
     */
    private List<TeamTreeNode> upTreeNodeQuery(List<TeamTreeNode> children, String teamId) {
        TeamVO teamVO = getOneEnhance(new TeamQuery(){{setId(teamId);}});
        if(Objects.nonNull(teamVO)) {
            List<TeamTreeNode> parentNode = Lists.newArrayList();
            TeamTreeNode teamTreeNode = new TeamTreeNode();
            teamTreeNode.setId(teamId);
            teamTreeNode.setName(teamVO.getName());
            if(CollectionUtils.isEmpty(children)) {
                parentNode.add(teamTreeNode);
            } else {
                teamTreeNode.setChildren(children);
                parentNode.add(teamTreeNode);
            }
           return upTreeNodeQuery(parentNode, teamVO.getTeamId());
        } else {
            if(CollectionUtils.isEmpty(children)) {
                throw new ParameterNullException("不存在的团队！");
            }
            return children;
        }
    }


    /**
     * 递归查询团队树
     * @param advanceSelected 待选状态，后期可能和权限挂钩
     * @param teamVOList 团队列表
     * @param list 团队树列表
     * @return void
     */
    private void recursive(Boolean advanceSelected, List<TeamVO> teamVOList, List<TeamTreeNode> list) {
        for (TeamVO teamVO : teamVOList) {
            //菜单对象
            TeamTreeNode teamTreeNode = new TeamTreeNode();
            //放入树内容
            tree(advanceSelected, teamVO, teamTreeNode);
            //递归判断，是否有下级
            TeamQuery teamQuery = new TeamQuery();
            teamQuery.setTeamId(teamVO.getId());
            if (countEnhance(teamQuery) > 0) {
                //递归集合
                List<TeamTreeNode> childList = Lists.newArrayList();
                //递归菜单
                List<TeamVO> menuRecursive = listEnhance(teamQuery);
                //递归结果
                recursive(advanceSelected, menuRecursive, childList);
                //递归下级
                teamTreeNode.setChildren(childList);
            }
            //放入第一节点结合
            list.add(teamTreeNode);
        }
    }


    /**
     * @param advanceSelected
     * @param teamVO
     * @param teamTreeNode 团队树节点
     * @auther: sunx
     * @mobile: 13777579028
     * @email: 376253703@qq.com
     * @name: tree
     * @description: TODO  团队树结构内容
     * @return: void
     * @date: 2021/10/26 4:50 下午
     */
    private void tree(Boolean advanceSelected, TeamVO teamVO, TeamTreeNode teamTreeNode) {
        teamTreeNode.setId(teamVO.getId());
        teamTreeNode.setName(teamVO.getName());
        teamTreeNode.setTeamId(teamVO.getTeamId());
        teamTreeNode.setTeamName(teamVO.getTeamName());
        //TODO:保留---后期看看有没有什么权限
        if (Objects.nonNull(advanceSelected) && advanceSelected) {
//            //判断用户权限
//            Long integer = countEnhance(new ResourceQuery() {{
//                setId(resourceVO.getId());
//                setRoleId(roleId);
//            }});
//            if (integer > 0) {
//                treeNode.setState(new TreeNode.State() {{
//                    setChecked(true);
//                }});
//            } else {
//                treeNode.setState(new TreeNode.State() {{
//                    setChecked(false);
//                }});
//            }
        }
    }


    /**
     * TODO 人工查询条件
     *
     * @param teamQuery 团队
     * @return QueryWrapper
     * @author sunx
     * @methodName queryArtificial
     * @time 2022-08-30 04:44:17
     */
    private QueryWrapper queryArtificial(TeamQuery teamQuery, QueryWrapper<Team> queryWrapper) {
        //团队名称模糊搜索
        if(StringUtils.isNotBlank(teamQuery.getNameQuery())) {
            queryWrapper.like("name", teamQuery.getNameQuery());
        }
        if(StringUtils.isNotBlank(teamQuery.getAuthUserId())) {
            StringBuilder sbf = new StringBuilder("select `team_id` from `team_user_data_permissions` where `is_delete` = 0");
            sbf.append(" and `team_user_id` = '" + teamQuery.getAuthUserId() +"'");
            queryWrapper.inSql("id", sbf.toString());
        }
        if(CollectionUtils.isNotEmpty(teamQuery.getIdList())) {
            queryWrapper.in("id", teamQuery.getIdList());
        }
        return queryWrapper;
    }
}