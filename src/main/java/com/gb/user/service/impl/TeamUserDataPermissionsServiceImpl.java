package com.gb.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.user.entity.TeamUserDataPermissions;
import com.gb.user.entity.bo.TeamUserDataPermissionsBO;
import com.gb.user.entity.query.TeamUserDataPermissionsQuery;
import com.gb.user.entity.vo.TeamUserDataPermissionsVO;
import com.gb.user.mapper.TeamUserDataPermissionsMapper;
import com.gb.user.service.TeamUserDataPermissionsService;
import com.gb.user.service.query.TeamUserDataPermissionsServiceQuery;
import com.gb.user.service.results.TeamUserDataPermissionsServiceResults;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.exception.PreventRepeatException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


/**
 * TODO 团队人员数据权限，Service服务实现层
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserDataPermissionsServiceImpl
 * @time 2022-08-30 04:44:18
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamUserDataPermissionsServiceImpl extends ServiceImpl<TeamUserDataPermissionsMapper, TeamUserDataPermissions> implements TeamUserDataPermissionsService {


    /**
     * 团队人员数据权限
     */
    private TeamUserDataPermissionsMapper teamUserDataPermissionsMapper;


    /**
     * 团队人员数据权限
     */
    private TeamUserDataPermissionsServiceResults teamUserDataPermissionsServiceResults;


    /**
     * 团队人员数据权限增强条件
     */
    private TeamUserDataPermissionsServiceQuery teamUserDataPermissionsServiceQuery;


    /**
     * TODO 集合
     *
     * @param teamUserDataPermissionsQuery 团队人员数据权限
     * @return List<TeamUserDataPermissionsVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-08-30 04:44:18
     */
    @Override
    public List<TeamUserDataPermissionsVO> listEnhance(TeamUserDataPermissionsQuery teamUserDataPermissionsQuery) {
        TeamUserDataPermissions teamUserDataPermissions = GeneralConvertor.convertor(teamUserDataPermissionsQuery, TeamUserDataPermissions.class);
        QueryWrapper<TeamUserDataPermissions> queryWrapper = new QueryWrapper<>(teamUserDataPermissions);
        // TODO 自动生成查询，禁止手动写语句
        teamUserDataPermissionsServiceQuery.query(teamUserDataPermissionsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamUserDataPermissionsQuery, queryWrapper);
        // DO数据
        List<TeamUserDataPermissions> teamUserDataPermissionsDO = teamUserDataPermissionsMapper.selectList(queryWrapper);
        // VO数据
        List<TeamUserDataPermissionsVO> teamUserDataPermissionsVO = GeneralConvertor.convertor(teamUserDataPermissionsDO, TeamUserDataPermissionsVO.class);
        // 判断是否增强
        if(Objects.nonNull(teamUserDataPermissionsQuery.getAssignment()) && teamUserDataPermissionsQuery.getAssignment()) {
            return teamUserDataPermissionsServiceResults.assignment(teamUserDataPermissionsVO);
        }
        return teamUserDataPermissionsVO;
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param teamUserDataPermissionsQuery 团队人员数据权限
     * @return Page<TeamUserDataPermissionsVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-08-30 04:44:18
     */
    @Override
    public Page<TeamUserDataPermissionsVO> pageEnhance(Page page, TeamUserDataPermissionsQuery teamUserDataPermissionsQuery) {
        TeamUserDataPermissions teamUserDataPermissions = GeneralConvertor.convertor(teamUserDataPermissionsQuery, TeamUserDataPermissions.class);
        QueryWrapper<TeamUserDataPermissions> queryWrapper = new QueryWrapper<>(teamUserDataPermissions);
        // TODO 自动生成查询，禁止手动写语句
        teamUserDataPermissionsServiceQuery.query(teamUserDataPermissionsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamUserDataPermissionsQuery, queryWrapper);
        // DO数据
        Page<TeamUserDataPermissions> pageDO = teamUserDataPermissionsMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<TeamUserDataPermissionsVO> pageVO = teamUserDataPermissionsServiceResults.toPageVO(pageDO);
        // 判断是否增强
        if(Objects.nonNull(teamUserDataPermissionsQuery.getAssignment()) && teamUserDataPermissionsQuery.getAssignment()) {
            return teamUserDataPermissionsServiceResults.assignment(pageVO);
        }
        return pageVO;
    }


    /**
     * TODO 单条
     *
     * @param teamUserDataPermissionsQuery 团队人员数据权限
     * @return TeamUserDataPermissionsVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-08-30 04:44:18
     */
    @Override
    public TeamUserDataPermissionsVO getOneEnhance(TeamUserDataPermissionsQuery teamUserDataPermissionsQuery) {
        TeamUserDataPermissions teamUserDataPermissions = GeneralConvertor.convertor(teamUserDataPermissionsQuery, TeamUserDataPermissions.class);
        QueryWrapper<TeamUserDataPermissions> queryWrapper = new QueryWrapper<>(teamUserDataPermissions);
        // TODO 自动生成查询，禁止手动写语句
        teamUserDataPermissionsServiceQuery.query(teamUserDataPermissionsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamUserDataPermissionsQuery, queryWrapper);
        // DO数据
        TeamUserDataPermissions teamUserDataPermissionsDO = teamUserDataPermissionsMapper.selectOne(queryWrapper);
        // VO数据
        TeamUserDataPermissionsVO teamUserDataPermissionsVO = GeneralConvertor.convertor(teamUserDataPermissionsDO, TeamUserDataPermissionsVO.class);
        // 判断是否增强
        if (Objects.nonNull(teamUserDataPermissionsQuery.getAssignment()) && teamUserDataPermissionsQuery.getAssignment()) {
            return teamUserDataPermissionsServiceResults.assignment(teamUserDataPermissionsVO);
        }
        return teamUserDataPermissionsVO;
    }


    /**
     * TODO 总数
     *
     * @param teamUserDataPermissionsQuery 团队人员数据权限
     * @return Integer
     * @author sunx
     * @methodName countEnhance
     * @time 2022-08-30 04:44:18
     */
    @Override
    public Long countEnhance(TeamUserDataPermissionsQuery teamUserDataPermissionsQuery) {
        TeamUserDataPermissions teamUserDataPermissions = GeneralConvertor.convertor(teamUserDataPermissionsQuery, TeamUserDataPermissions.class);
        QueryWrapper<TeamUserDataPermissions> queryWrapper = new QueryWrapper<>(teamUserDataPermissions);
        // TODO 自动生成查询，禁止手动写语句
        teamUserDataPermissionsServiceQuery.query(teamUserDataPermissionsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamUserDataPermissionsQuery, queryWrapper);
        return teamUserDataPermissionsMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param teamUserDataPermissionsBO 团队人员数据权限
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-08-30 04:44:18
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(TeamUserDataPermissionsBO teamUserDataPermissionsBO) {
        if(countEnhance(new TeamUserDataPermissionsQuery(){{
            setTeamUserId(teamUserDataPermissionsBO.getTeamUserId());
            setTeamId(teamUserDataPermissionsBO.getTeamId());
        }}) > 0) {
            throw new PreventRepeatException("该成员已经选择了该团队权限，请重新选择！");
        }
        TeamUserDataPermissions teamUserDataPermissions = GeneralConvertor.convertor(teamUserDataPermissionsBO, TeamUserDataPermissions.class);
        teamUserDataPermissionsMapper.insert(teamUserDataPermissions);
        return teamUserDataPermissions.getId();
    }


    /**
     * TODO 修改
     *
     * @param teamUserDataPermissionsBO 团队人员数据权限
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-08-30 04:44:18
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(TeamUserDataPermissionsBO teamUserDataPermissionsBO) {
        if(countEnhance(new TeamUserDataPermissionsQuery(){{
            setTeamUserId(teamUserDataPermissionsBO.getTeamUserId());
            setTeamId(teamUserDataPermissionsBO.getTeamId());
        }}) > 0) {
            throw new PreventRepeatException("该成员已经选择了该团队权限，请重新选择！");
        }
        TeamUserDataPermissions teamUserDataPermissions = GeneralConvertor.convertor(teamUserDataPermissionsBO, TeamUserDataPermissions.class);
        UpdateWrapper<TeamUserDataPermissions> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", teamUserDataPermissionsBO.getId());
        Integer i = teamUserDataPermissionsMapper.update(teamUserDataPermissions, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 删除
     *
     * @param teamUserDataPermissionsBO 团队人员数据权限
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-08-30 04:44:18
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(TeamUserDataPermissionsBO teamUserDataPermissionsBO) {
        TeamUserDataPermissions teamUserDataPermissions = GeneralConvertor.convertor(teamUserDataPermissionsBO, TeamUserDataPermissions.class);
        QueryWrapper<TeamUserDataPermissions> queryWrapper = new QueryWrapper<>(teamUserDataPermissions);
        Integer i = teamUserDataPermissionsMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 人工查询条件
     *
     * @param teamUserDataPermissionsQuery 团队人员数据权限
     * @return QueryWrapper
     * @author sunx
     * @methodName queryArtificial
     * @time 2022-08-30 04:44:18
     */
    private QueryWrapper queryArtificial(TeamUserDataPermissionsQuery teamUserDataPermissionsQuery, QueryWrapper<TeamUserDataPermissions> queryWrapper) {
        return queryWrapper;
    }
}