package com.gb.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.user.entity.TeamGroupValueLimit;
import com.gb.user.entity.bo.TeamGroupValueLimitBO;
import com.gb.user.entity.query.TeamGroupValueLimitQuery;
import com.gb.user.entity.vo.TeamGroupValueLimitVO;
import com.gb.user.mapper.TeamGroupValueLimitMapper;
import com.gb.user.service.TeamGroupValueLimitService;
import com.gb.user.service.query.TeamGroupValueLimitServiceQuery;
import com.gb.user.service.results.TeamGroupValueLimitServiceResults;
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
 * TODO 团队组别限制，Service服务实现层
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValueLimitServiceImpl
 * @time 2022-08-31 10:59:01
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamGroupValueLimitServiceImpl extends ServiceImpl<TeamGroupValueLimitMapper, TeamGroupValueLimit> implements TeamGroupValueLimitService {


    /**
     * 团队组别限制
     */
    private TeamGroupValueLimitMapper teamGroupValueLimitMapper;


    /**
     * 团队组别限制
     */
    private TeamGroupValueLimitServiceResults teamGroupValueLimitServiceResults;


    /**
     * 团队组别限制增强条件
     */
    private TeamGroupValueLimitServiceQuery teamGroupValueLimitServiceQuery;


    /**
     * TODO 集合
     *
     * @param teamGroupValueLimitQuery 团队组别限制
     * @return List<TeamGroupValueLimitVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    public List<TeamGroupValueLimitVO> listEnhance(TeamGroupValueLimitQuery teamGroupValueLimitQuery) {
        TeamGroupValueLimit teamGroupValueLimit = GeneralConvertor.convertor(teamGroupValueLimitQuery, TeamGroupValueLimit.class);
        QueryWrapper<TeamGroupValueLimit> queryWrapper = new QueryWrapper<>(teamGroupValueLimit);
        // TODO 自动生成查询，禁止手动写语句
        teamGroupValueLimitServiceQuery.query(teamGroupValueLimitQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamGroupValueLimitQuery, queryWrapper);
        // DO数据
        List<TeamGroupValueLimit> teamGroupValueLimitDO = teamGroupValueLimitMapper.selectList(queryWrapper);
        // VO数据
        List<TeamGroupValueLimitVO> teamGroupValueLimitVO = GeneralConvertor.convertor(teamGroupValueLimitDO, TeamGroupValueLimitVO.class);
        // 判断是否增强
        if(Objects.isNull(teamGroupValueLimitQuery.getAssignment()) || teamGroupValueLimitQuery.getAssignment()) {
            return teamGroupValueLimitServiceResults.assignment(teamGroupValueLimitVO);
        }
        return teamGroupValueLimitVO;
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param teamGroupValueLimitQuery 团队组别限制
     * @return Page<TeamGroupValueLimitVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    public Page<TeamGroupValueLimitVO> pageEnhance(Page page, TeamGroupValueLimitQuery teamGroupValueLimitQuery) {
        TeamGroupValueLimit teamGroupValueLimit = GeneralConvertor.convertor(teamGroupValueLimitQuery, TeamGroupValueLimit.class);
        QueryWrapper<TeamGroupValueLimit> queryWrapper = new QueryWrapper<>(teamGroupValueLimit);
        // TODO 自动生成查询，禁止手动写语句
        teamGroupValueLimitServiceQuery.query(teamGroupValueLimitQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamGroupValueLimitQuery, queryWrapper);
        // DO数据
        Page<TeamGroupValueLimit> pageDO = teamGroupValueLimitMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<TeamGroupValueLimitVO> pageVO = teamGroupValueLimitServiceResults.toPageVO(pageDO);
        // 判断是否增强
        if (Objects.nonNull(teamGroupValueLimitQuery.getAssignment()) && teamGroupValueLimitQuery.getAssignment()) {
            return teamGroupValueLimitServiceResults.assignment(pageVO);
        }
        return pageVO;
    }


    /**
     * TODO 单条
     *
     * @param teamGroupValueLimitQuery 团队组别限制
     * @return TeamGroupValueLimitVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    public TeamGroupValueLimitVO getOneEnhance(TeamGroupValueLimitQuery teamGroupValueLimitQuery) {
        TeamGroupValueLimit teamGroupValueLimit = GeneralConvertor.convertor(teamGroupValueLimitQuery, TeamGroupValueLimit.class);
        QueryWrapper<TeamGroupValueLimit> queryWrapper = new QueryWrapper<>(teamGroupValueLimit);
        // TODO 自动生成查询，禁止手动写语句
        teamGroupValueLimitServiceQuery.query(teamGroupValueLimitQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamGroupValueLimitQuery, queryWrapper);
        // DO数据
        TeamGroupValueLimit teamGroupValueLimitDO = teamGroupValueLimitMapper.selectOne(queryWrapper);
        // VO数据
        TeamGroupValueLimitVO teamGroupValueLimitVO = GeneralConvertor.convertor(teamGroupValueLimitDO, TeamGroupValueLimitVO.class);
        // 判断是否增强
        if(Objects.nonNull(teamGroupValueLimitQuery.getAssignment()) && teamGroupValueLimitQuery.getAssignment()) {
            return teamGroupValueLimitServiceResults.assignment(teamGroupValueLimitVO);
        }
        return teamGroupValueLimitVO;
    }


    /**
     * TODO 总数
     *
     * @param teamGroupValueLimitQuery 团队组别限制
     * @return Integer
     * @author sunx
     * @methodName countEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    public Long countEnhance(TeamGroupValueLimitQuery teamGroupValueLimitQuery) {
        TeamGroupValueLimit teamGroupValueLimit = GeneralConvertor.convertor(teamGroupValueLimitQuery, TeamGroupValueLimit.class);
        QueryWrapper<TeamGroupValueLimit> queryWrapper = new QueryWrapper<>(teamGroupValueLimit);
        // TODO 自动生成查询，禁止手动写语句
        teamGroupValueLimitServiceQuery.query(teamGroupValueLimitQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamGroupValueLimitQuery, queryWrapper);
        return teamGroupValueLimitMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param teamGroupValueLimitBO 团队组别限制
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(TeamGroupValueLimitBO teamGroupValueLimitBO) {
        TeamGroupValueLimit teamGroupValueLimit = GeneralConvertor.convertor(teamGroupValueLimitBO, TeamGroupValueLimit.class);
        teamGroupValueLimitMapper.insert(teamGroupValueLimit);
        return teamGroupValueLimit.getId();
    }


    /**
     * TODO 修改
     *
     * @param teamGroupValueLimitBO 团队组别限制
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(TeamGroupValueLimitBO teamGroupValueLimitBO) {
        TeamGroupValueLimit teamGroupValueLimit = GeneralConvertor.convertor(teamGroupValueLimitBO, TeamGroupValueLimit.class);
        UpdateWrapper<TeamGroupValueLimit> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", teamGroupValueLimitBO.getId());
        Integer i = teamGroupValueLimitMapper.update(teamGroupValueLimit, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 删除
     *
     * @param teamGroupValueLimitBO 团队组别限制
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(TeamGroupValueLimitBO teamGroupValueLimitBO) {
        TeamGroupValueLimit teamGroupValueLimit = GeneralConvertor.convertor(teamGroupValueLimitBO, TeamGroupValueLimit.class);
        QueryWrapper<TeamGroupValueLimit> queryWrapper = new QueryWrapper<>(teamGroupValueLimit);
        Integer i = teamGroupValueLimitMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 人工查询条件
     *
     * @param teamGroupValueLimitQuery 团队组别限制
     * @return QueryWrapper
     * @author sunx
     * @methodName queryArtificial
     * @time 2022-08-31 10:59:01
     */
    private QueryWrapper queryArtificial(TeamGroupValueLimitQuery teamGroupValueLimitQuery, QueryWrapper<TeamGroupValueLimit> queryWrapper) {
        return queryWrapper;
    }
}