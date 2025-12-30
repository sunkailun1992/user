package com.gb.user.service.impl;

import com.gb.user.entity.query.InstitutionsExecutivesQuery;
import com.gb.user.entity.vo.InstitutionsExecutivesVO;
import com.gb.user.entity.bo.InstitutionsExecutivesBO;
import com.gb.user.entity.InstitutionsExecutives;
import com.gb.user.mapper.InstitutionsExecutivesMapper;
import com.gb.user.service.InstitutionsExecutivesService;
import com.gb.user.service.query.InstitutionsExecutivesServiceQuery;
import com.gb.user.service.results.InstitutionsExecutivesServiceResults;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Setter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.gb.utils.GeneralConvertor;

import java.util.List;


/**
 * TODO 机构高管，Service服务实现层
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsExecutivesServiceImpl
 * @time 2022-07-04 10:48:36
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class InstitutionsExecutivesServiceImpl extends ServiceImpl<InstitutionsExecutivesMapper, InstitutionsExecutives> implements InstitutionsExecutivesService {


    /**
     * 机构高管
     */
    private InstitutionsExecutivesMapper institutionsExecutivesMapper;


    /**
     * 机构高管
     */
    private InstitutionsExecutivesServiceResults institutionsExecutivesServiceResults;


    /**
     * 机构高管增强条件
     */
    private InstitutionsExecutivesServiceQuery institutionsExecutivesServiceQuery;


    /**
     * TODO 集合
     *
     * @param institutionsExecutivesQuery 机构高管
     * @return List<InstitutionsExecutivesVO>
     * @author sunxin
     * @methodName listEnhance
     * @time 2022-07-04 10:48:36
     */
    @Override
    public List<InstitutionsExecutivesVO> listEnhance(InstitutionsExecutivesQuery institutionsExecutivesQuery) {
        InstitutionsExecutives institutionsExecutives = GeneralConvertor.convertor(institutionsExecutivesQuery, InstitutionsExecutives.class);
        QueryWrapper<InstitutionsExecutives> queryWrapper = new QueryWrapper<>(institutionsExecutives);
        // TODO 自动生成查询，禁止手动写语句
        institutionsExecutivesServiceQuery.query(institutionsExecutivesQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(institutionsExecutivesQuery, queryWrapper);
        // DO数据
        List<InstitutionsExecutives> institutionsExecutivesDO = institutionsExecutivesMapper.selectList(queryWrapper);
        // VO数据
        List<InstitutionsExecutivesVO> institutionsExecutivesVO = GeneralConvertor.convertor(institutionsExecutivesDO, InstitutionsExecutivesVO.class);
        return institutionsExecutivesServiceResults.assignment(institutionsExecutivesVO);
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param institutionsExecutivesQuery 机构高管
     * @return Page<InstitutionsExecutivesVO>
     * @author sunxin
     * @methodName pageEnhance
     * @time 2022-07-04 10:48:36
     */
    @Override
    public Page<InstitutionsExecutivesVO> pageEnhance(Page page, InstitutionsExecutivesQuery institutionsExecutivesQuery) {
        InstitutionsExecutives institutionsExecutives = GeneralConvertor.convertor(institutionsExecutivesQuery, InstitutionsExecutives.class);
        QueryWrapper<InstitutionsExecutives> queryWrapper = new QueryWrapper<>(institutionsExecutives);
        // TODO 自动生成查询，禁止手动写语句
        institutionsExecutivesServiceQuery.query(institutionsExecutivesQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(institutionsExecutivesQuery, queryWrapper);
        // DO数据
        Page<InstitutionsExecutives> pageDO = institutionsExecutivesMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<InstitutionsExecutivesVO> pageVO = institutionsExecutivesServiceResults.toPageVO(pageDO);
        return institutionsExecutivesServiceResults.assignment(pageVO);
    }


    /**
     * TODO 单条
     *
     * @param institutionsExecutivesQuery 机构高管
     * @return InstitutionsExecutivesVO
     * @author sunxin
     * @methodName getOneEnhance
     * @time 2022-07-04 10:48:36
     */
    @Override
    public InstitutionsExecutivesVO getOneEnhance(InstitutionsExecutivesQuery institutionsExecutivesQuery) {
        InstitutionsExecutives institutionsExecutives = GeneralConvertor.convertor(institutionsExecutivesQuery, InstitutionsExecutives.class);
        QueryWrapper<InstitutionsExecutives> queryWrapper = new QueryWrapper<>(institutionsExecutives);
        // TODO 自动生成查询，禁止手动写语句
        institutionsExecutivesServiceQuery.query(institutionsExecutivesQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(institutionsExecutivesQuery, queryWrapper);
        // DO数据
        InstitutionsExecutives institutionsExecutivesDO = institutionsExecutivesMapper.selectOne(queryWrapper);
        // VO数据
        InstitutionsExecutivesVO institutionsExecutivesVO = GeneralConvertor.convertor(institutionsExecutivesDO, InstitutionsExecutivesVO.class);
        return institutionsExecutivesServiceResults.assignment(institutionsExecutivesVO);
    }


    /**
     * TODO 总数
     *
     * @param institutionsExecutivesQuery 机构高管
     * @return Integer
     * @author sunxin
     * @methodName countEnhance
     * @time 2022-07-04 10:48:36
     */
    @Override
    public Long countEnhance(InstitutionsExecutivesQuery institutionsExecutivesQuery) {
        InstitutionsExecutives institutionsExecutives = GeneralConvertor.convertor(institutionsExecutivesQuery, InstitutionsExecutives.class);
        QueryWrapper<InstitutionsExecutives> queryWrapper = new QueryWrapper<>(institutionsExecutives);
        // TODO 自动生成查询，禁止手动写语句
        institutionsExecutivesServiceQuery.query(institutionsExecutivesQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(institutionsExecutivesQuery, queryWrapper);
        return institutionsExecutivesMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param institutionsExecutivesBO 机构高管
     * @return String
     * @author sunxin
     * @methodName saveEnhance
     * @time 2022-07-04 10:48:36
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(InstitutionsExecutivesBO institutionsExecutivesBO) {
        InstitutionsExecutives institutionsExecutives = GeneralConvertor.convertor(institutionsExecutivesBO, InstitutionsExecutives.class);
        institutionsExecutivesMapper.insert(institutionsExecutives);
        return institutionsExecutives.getId();
    }


    /**
     * TODO 修改
     *
     * @param institutionsExecutivesBO 机构高管
     * @return Boolean
     * @author sunxin
     * @methodName updateEnhance
     * @time 2022-07-04 10:48:36
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(InstitutionsExecutivesBO institutionsExecutivesBO) {
        InstitutionsExecutives institutionsExecutives = GeneralConvertor.convertor(institutionsExecutivesBO, InstitutionsExecutives.class);
        UpdateWrapper<InstitutionsExecutives> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", institutionsExecutivesBO.getId());
        Integer i = institutionsExecutivesMapper.update(institutionsExecutives, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 删除
     *
     * @param institutionsExecutivesBO 机构高管
     * @return Boolean
     * @author sunxin
     * @methodName removeEnhance
     * @time 2022-07-04 10:48:36
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(InstitutionsExecutivesBO institutionsExecutivesBO) {
        InstitutionsExecutives institutionsExecutives = GeneralConvertor.convertor(institutionsExecutivesBO, InstitutionsExecutives.class);
        QueryWrapper<InstitutionsExecutives> queryWrapper = new QueryWrapper<>(institutionsExecutives);
        Integer i = institutionsExecutivesMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 人工查询条件
     *
     * @param institutionsExecutivesQuery 机构高管
     * @return QueryWrapper
     * @author sunxin
     * @methodName queryArtificial
     * @time 2022-07-04 10:48:36
     */
    private QueryWrapper queryArtificial(InstitutionsExecutivesQuery institutionsExecutivesQuery, QueryWrapper<InstitutionsExecutives> queryWrapper) {
        return queryWrapper;
    }
}