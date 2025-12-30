package com.gb.user.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.user.entity.TeamGroup;
import com.gb.user.entity.bo.TeamGroupBO;
import com.gb.user.entity.query.TeamGroupQuery;
import com.gb.user.entity.vo.TeamGroupVO;
import com.gb.user.mapper.TeamGroupMapper;
import com.gb.user.service.TeamGroupService;
import com.gb.user.service.query.TeamGroupServiceQuery;
import com.gb.user.service.results.TeamGroupServiceResults;
import com.gb.utils.GeneralConvertor;
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
 * TODO 团队组别，Service服务实现层
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupServiceImpl
 * @time 2022-08-31 10:59:01
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamGroupServiceImpl extends ServiceImpl<TeamGroupMapper, TeamGroup> implements TeamGroupService {


    /**
     * 团队组别
     */
    private TeamGroupMapper teamGroupMapper;


    /**
     * 团队组别
     */
    private TeamGroupServiceResults teamGroupServiceResults;


    /**
     * 团队组别增强条件
     */
    private TeamGroupServiceQuery teamGroupServiceQuery;


    /**
     * TODO 集合
     *
     * @param teamGroupQuery 团队组别
     * @return List<TeamGroupVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    public List<TeamGroupVO> listEnhance(TeamGroupQuery teamGroupQuery) {
        TeamGroup teamGroup = GeneralConvertor.convertor(teamGroupQuery, TeamGroup.class);
        QueryWrapper<TeamGroup> queryWrapper = new QueryWrapper<>(teamGroup);
        // TODO 自动生成查询，禁止手动写语句
        teamGroupServiceQuery.query(teamGroupQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamGroupQuery, queryWrapper);
        // DO数据
        List<TeamGroup> teamGroupDO = teamGroupMapper.selectList(queryWrapper);
        // VO数据
        List<TeamGroupVO> teamGroupVO = GeneralConvertor.convertor(teamGroupDO, TeamGroupVO.class);
        // 判断是否增强
        if (Objects.isNull(teamGroupQuery.getAssignment()) || teamGroupQuery.getAssignment()) {
            return teamGroupServiceResults.assignment(teamGroupVO);
        }
        return teamGroupVO;
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param teamGroupQuery 团队组别
     * @return Page<TeamGroupVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    public Page<TeamGroupVO> pageEnhance(Page page, TeamGroupQuery teamGroupQuery) {
        TeamGroup teamGroup = GeneralConvertor.convertor(teamGroupQuery, TeamGroup.class);
        QueryWrapper<TeamGroup> queryWrapper = new QueryWrapper<>(teamGroup);
        // TODO 自动生成查询，禁止手动写语句
        teamGroupServiceQuery.query(teamGroupQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamGroupQuery, queryWrapper);
        // DO数据
        Page<TeamGroup> pageDO = teamGroupMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<TeamGroupVO> pageVO = teamGroupServiceResults.toPageVO(pageDO);
        // 判断是否增强
        if (teamGroupQuery.getAssignment() == null) {
            return teamGroupServiceResults.assignment(pageVO);
        } else {
            return teamGroupQuery.getAssignment() ? teamGroupServiceResults.assignment(pageVO) : pageVO;
        }
    }


    /**
     * TODO 单条
     *
     * @param teamGroupQuery 团队组别
     * @return TeamGroupVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    public TeamGroupVO getOneEnhance(TeamGroupQuery teamGroupQuery) {
        TeamGroup teamGroup = GeneralConvertor.convertor(teamGroupQuery, TeamGroup.class);
        QueryWrapper<TeamGroup> queryWrapper = new QueryWrapper<>(teamGroup);
        // TODO 自动生成查询，禁止手动写语句
        teamGroupServiceQuery.query(teamGroupQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamGroupQuery, queryWrapper);
        // DO数据
        TeamGroup teamGroupDO = teamGroupMapper.selectOne(queryWrapper);
        // VO数据
        TeamGroupVO teamGroupVO = GeneralConvertor.convertor(teamGroupDO, TeamGroupVO.class);
        // 判断是否增强
        if (teamGroupQuery.getAssignment() == null) {
            return teamGroupServiceResults.assignment(teamGroupVO);
        } else {
            return teamGroupQuery.getAssignment() ? teamGroupServiceResults.assignment(teamGroupVO) : teamGroupVO;
        }
    }


    /**
     * TODO 总数
     *
     * @param teamGroupQuery 团队组别
     * @return Integer
     * @author sunx
     * @methodName countEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    public Long countEnhance(TeamGroupQuery teamGroupQuery) {
        TeamGroup teamGroup = GeneralConvertor.convertor(teamGroupQuery, TeamGroup.class);
        QueryWrapper<TeamGroup> queryWrapper = new QueryWrapper<>(teamGroup);
        // TODO 自动生成查询，禁止手动写语句
        teamGroupServiceQuery.query(teamGroupQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamGroupQuery, queryWrapper);
        return teamGroupMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param teamGroupBO 团队组别
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(TeamGroupBO teamGroupBO) {
        TeamGroup teamGroup = GeneralConvertor.convertor(teamGroupBO, TeamGroup.class);
        String teamGroupCode = "TGN" + DateUtil.format(DateUtil.date(), DatePattern.PURE_DATETIME_PATTERN);
        teamGroup.setCode(teamGroupCode);
        teamGroupMapper.insert(teamGroup);
        return teamGroup.getId();
    }


    /**
     * TODO 修改
     *
     * @param teamGroupBO 团队组别
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(TeamGroupBO teamGroupBO) {
        TeamGroup teamGroup = GeneralConvertor.convertor(teamGroupBO, TeamGroup.class);
        UpdateWrapper<TeamGroup> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", teamGroupBO.getId());
        Integer i = teamGroupMapper.update(teamGroup, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 删除
     *
     * @param teamGroupBO 团队组别
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(TeamGroupBO teamGroupBO) {
        TeamGroup teamGroup = GeneralConvertor.convertor(teamGroupBO, TeamGroup.class);
        QueryWrapper<TeamGroup> queryWrapper = new QueryWrapper<>(teamGroup);
        Integer i = teamGroupMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 人工查询条件
     *
     * @param teamGroupQuery 团队组别
     * @return QueryWrapper
     * @author sunx
     * @methodName queryArtificial
     * @time 2022-08-31 10:59:01
     */
    private QueryWrapper queryArtificial(TeamGroupQuery teamGroupQuery, QueryWrapper<TeamGroup> queryWrapper) {
        return queryWrapper;
    }
}