package com.gb.user.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.user.entity.TeamGroupValue;
import com.gb.user.entity.bo.TeamGroupValueBO;
import com.gb.user.entity.query.TeamGroupValueQuery;
import com.gb.user.entity.vo.TeamGroupValueVO;
import com.gb.user.mapper.TeamGroupValueMapper;
import com.gb.user.service.TeamGroupValueService;
import com.gb.user.service.query.TeamGroupValueServiceQuery;
import com.gb.user.service.results.TeamGroupValueServiceResults;
import com.gb.utils.GeneralConvertor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * TODO 团队组别值，Service服务实现层
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValueServiceImpl
 * @time 2022-08-31 10:59:01
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamGroupValueServiceImpl extends ServiceImpl<TeamGroupValueMapper, TeamGroupValue> implements TeamGroupValueService {


    /**
     * 团队组别值
     */
    private TeamGroupValueMapper teamGroupValueMapper;


    /**
     * 团队组别值
     */
    private TeamGroupValueServiceResults teamGroupValueServiceResults;


    /**
     * 团队组别值增强条件
     */
    private TeamGroupValueServiceQuery teamGroupValueServiceQuery;


    /**
     * TODO 集合
     *
     * @param teamGroupValueQuery 团队组别值
     * @return List<TeamGroupValueVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    public List<TeamGroupValueVO> listEnhance(TeamGroupValueQuery teamGroupValueQuery) {
        TeamGroupValue teamGroupValue = GeneralConvertor.convertor(teamGroupValueQuery, TeamGroupValue.class);
        QueryWrapper<TeamGroupValue> queryWrapper = new QueryWrapper<>(teamGroupValue);
        // TODO 自动生成查询，禁止手动写语句
        teamGroupValueServiceQuery.query(teamGroupValueQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamGroupValueQuery, queryWrapper);
        // DO数据
        List<TeamGroupValue> teamGroupValueDO = teamGroupValueMapper.selectList(queryWrapper);
        // VO数据
        List<TeamGroupValueVO> teamGroupValueVO = GeneralConvertor.convertor(teamGroupValueDO, TeamGroupValueVO.class);
        // 判断是否增强
        if (teamGroupValueQuery.getAssignment() == null) {
            return teamGroupValueServiceResults.assignment(teamGroupValueVO);
        } else {
            return teamGroupValueQuery.getAssignment() ? teamGroupValueServiceResults.assignment(teamGroupValueVO) : teamGroupValueVO;
        }
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param teamGroupValueQuery 团队组别值
     * @return Page<TeamGroupValueVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    public Page<TeamGroupValueVO> pageEnhance(Page page, TeamGroupValueQuery teamGroupValueQuery) {
        TeamGroupValue teamGroupValue = GeneralConvertor.convertor(teamGroupValueQuery, TeamGroupValue.class);
        QueryWrapper<TeamGroupValue> queryWrapper = new QueryWrapper<>(teamGroupValue);
        // TODO 自动生成查询，禁止手动写语句
        teamGroupValueServiceQuery.query(teamGroupValueQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamGroupValueQuery, queryWrapper);
        // DO数据
        Page<TeamGroupValue> pageDO = teamGroupValueMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<TeamGroupValueVO> pageVO = teamGroupValueServiceResults.toPageVO(pageDO);
        // 判断是否增强
        if (teamGroupValueQuery.getAssignment() == null) {
            return teamGroupValueServiceResults.assignment(pageVO);
        } else {
            return teamGroupValueQuery.getAssignment() ? teamGroupValueServiceResults.assignment(pageVO) : pageVO;
        }
    }


    /**
     * TODO 单条
     *
     * @param teamGroupValueQuery 团队组别值
     * @return TeamGroupValueVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    public TeamGroupValueVO getOneEnhance(TeamGroupValueQuery teamGroupValueQuery) {
        TeamGroupValue teamGroupValue = GeneralConvertor.convertor(teamGroupValueQuery, TeamGroupValue.class);
        QueryWrapper<TeamGroupValue> queryWrapper = new QueryWrapper<>(teamGroupValue);
        // TODO 自动生成查询，禁止手动写语句
        teamGroupValueServiceQuery.query(teamGroupValueQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamGroupValueQuery, queryWrapper);
        // DO数据
        TeamGroupValue teamGroupValueDO = teamGroupValueMapper.selectOne(queryWrapper);
        // VO数据
        TeamGroupValueVO teamGroupValueVO = GeneralConvertor.convertor(teamGroupValueDO, TeamGroupValueVO.class);
        // 判断是否增强
        if (teamGroupValueQuery.getAssignment() == null) {
            return teamGroupValueServiceResults.assignment(teamGroupValueVO);
        } else {
            return teamGroupValueQuery.getAssignment() ? teamGroupValueServiceResults.assignment(teamGroupValueVO) : teamGroupValueVO;
        }
    }


    /**
     * TODO 总数
     *
     * @param teamGroupValueQuery 团队组别值
     * @return Integer
     * @author sunx
     * @methodName countEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    public Long countEnhance(TeamGroupValueQuery teamGroupValueQuery) {
        TeamGroupValue teamGroupValue = GeneralConvertor.convertor(teamGroupValueQuery, TeamGroupValue.class);
        QueryWrapper<TeamGroupValue> queryWrapper = new QueryWrapper<>(teamGroupValue);
        // TODO 自动生成查询，禁止手动写语句
        teamGroupValueServiceQuery.query(teamGroupValueQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(teamGroupValueQuery, queryWrapper);
        return teamGroupValueMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param teamGroupValueBO 团队组别值
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(TeamGroupValueBO teamGroupValueBO) {
        TeamGroupValue teamGroupValue = GeneralConvertor.convertor(teamGroupValueBO, TeamGroupValue.class);
        String teamGroupValueCode = "TGVN" + DateUtil.format(DateUtil.date(), DatePattern.PURE_DATETIME_PATTERN);
        teamGroupValue.setCode(teamGroupValueCode);
        teamGroupValueMapper.insert(teamGroupValue);
        return teamGroupValue.getId();
    }


    /**
     * TODO 修改
     *
     * @param teamGroupValueBO 团队组别值
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(TeamGroupValueBO teamGroupValueBO) {
        TeamGroupValue teamGroupValue = GeneralConvertor.convertor(teamGroupValueBO, TeamGroupValue.class);
        UpdateWrapper<TeamGroupValue> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", teamGroupValueBO.getId());
        Integer i = teamGroupValueMapper.update(teamGroupValue, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 删除
     *
     * @param teamGroupValueBO 团队组别值
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-08-31 10:59:01
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(TeamGroupValueBO teamGroupValueBO) {
        TeamGroupValue teamGroupValue = GeneralConvertor.convertor(teamGroupValueBO, TeamGroupValue.class);
        QueryWrapper<TeamGroupValue> queryWrapper = new QueryWrapper<>(teamGroupValue);
        Integer i = teamGroupValueMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 人工查询条件
     *
     * @param teamGroupValueQuery 团队组别值
     * @return QueryWrapper
     * @author sunx
     * @methodName queryArtificial
     * @time 2022-08-31 10:59:01
     */
    private QueryWrapper queryArtificial(TeamGroupValueQuery teamGroupValueQuery, QueryWrapper<TeamGroupValue> queryWrapper) {
        return queryWrapper;
    }
}