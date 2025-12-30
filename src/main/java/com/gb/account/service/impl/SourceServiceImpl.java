package com.gb.account.service.impl;

import com.gb.account.entity.query.SourceQuery;
import com.gb.account.entity.vo.SourceVO;
import com.gb.account.entity.bo.SourceBO;
import com.gb.account.entity.Source;
import com.gb.account.mapper.SourceMapper;
import com.gb.account.service.SourceService;
import com.gb.account.service.query.SourceServiceQuery;
import com.gb.account.service.results.SourceServiceResults;
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
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-03 03:57:55
 * @description:	TODO  来源，Service服务实现层
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class SourceServiceImpl extends ServiceImpl<SourceMapper, Source> implements SourceService {


    /**
     * 来源
     */
    private SourceMapper sourceMapper;


    /**
     * 来源
     */
    private SourceServiceResults sourceServiceResults;


    /**
     * 来源增强条件
     */
    private SourceServiceQuery sourceServiceQuery;


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceQuery:
     * @return  java.util.List<com.entity.SourceVO>
     */
    @Override
    public List<SourceVO> listEnhance(SourceQuery sourceQuery) {
        Source source = GeneralConvertor.convertor(sourceQuery, Source.class);
        QueryWrapper<Source> queryWrapper = new QueryWrapper<>(source);
        // TODO 自动生成查询，禁止手动写语句
        sourceServiceQuery.query(sourceQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(sourceQuery, queryWrapper);
        //DO数据
        List<Source> sourceDO = sourceMapper.selectList(queryWrapper);
        //VO数据
        List<SourceVO> sourceVO = GeneralConvertor.convertor(sourceDO, SourceVO.class);
        return sourceServiceResults.assignment(sourceVO);
    }


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   page:
     * @param   sourceQuery:
     * @return  com.baomidou.mybatisplus.core.metadata.IPage
     */
    @Override
    public Page<SourceVO> pageEnhance(Page page, SourceQuery sourceQuery) {
        Source source = GeneralConvertor.convertor(sourceQuery, Source.class);
        QueryWrapper<Source> queryWrapper = new QueryWrapper<>(source);
        // TODO 自动生成查询，禁止手动写语句
        sourceServiceQuery.query(sourceQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(sourceQuery, queryWrapper);
        //DO数据
        Page<Source> pageDO = sourceMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<SourceVO> pageVO = sourceServiceResults.toPageVO(pageDO);
        return sourceServiceResults.assignment(pageVO);
    }


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceQuery:
     * @return  java.util.List<com.entity.SourceVO>
     */
    @Override
    public SourceVO getOneEnhance(SourceQuery sourceQuery) {
        Source source = GeneralConvertor.convertor(sourceQuery, Source.class);
        QueryWrapper<Source> queryWrapper = new QueryWrapper<>(source);
        // TODO 自动生成查询，禁止手动写语句
        sourceServiceQuery.query(sourceQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(sourceQuery, queryWrapper);
        //DO数据
        Source sourceDO = sourceMapper.selectOne(queryWrapper);
        //VO数据
        SourceVO sourceVO = GeneralConvertor.convertor(sourceDO, SourceVO.class);
        return sourceServiceResults.assignment(sourceVO);
    }


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceQuery:
     * @return  java.lang.Integer
     */
    @Override
    public Long countEnhance(SourceQuery sourceQuery) {
        Source source = GeneralConvertor.convertor(sourceQuery, Source.class);
        QueryWrapper<Source> queryWrapper = new QueryWrapper<>(source);
        // TODO 自动生成查询，禁止手动写语句
        sourceServiceQuery.query(sourceQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(sourceQuery, queryWrapper);
        return sourceMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceBO:
     * @return  java.lang.String
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(SourceBO sourceBO) {
        Source source = GeneralConvertor.convertor(sourceBO, Source.class);
        sourceMapper.insert(source);
        return source.getId();
    }


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(SourceBO sourceBO) {
        Source source = GeneralConvertor.convertor(sourceBO, Source.class);
        UpdateWrapper<Source > updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", sourceBO.getId());
        Integer i = sourceMapper.update(source, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(SourceBO sourceBO) {
        Source source = GeneralConvertor.convertor(sourceBO, Source.class);
        QueryWrapper<Source> queryWrapper = new QueryWrapper<>(source);
        Integer i = sourceMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 查询人工查询条件
     *
     * @author     	孙凯伦
     * @since   	2021-11-03 03:57:55
     * @param       sourceQuery 来源
     * @return      QueryWrapper
     */
     private QueryWrapper queryArtificial(SourceQuery sourceQuery, QueryWrapper<Source> queryWrapper) {
        return queryWrapper;
    }
}