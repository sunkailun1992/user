package com.gb.account.service.impl;

import com.gb.account.entity.query.SourceValueQuery;
import com.gb.account.entity.vo.SourceValueVO;
import com.gb.account.entity.bo.SourceValueBO;
import com.gb.account.entity.SourceValue;
import com.gb.account.mapper.SourceValueMapper;
import com.gb.account.service.SourceValueService;
import com.gb.account.service.query.SourceValueServiceQuery;
import com.gb.account.service.results.SourceValueServiceResults;
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
 * @description:	TODO  来源值，Service服务实现层
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class SourceValueServiceImpl extends ServiceImpl<SourceValueMapper, SourceValue> implements SourceValueService {


    /**
     * 来源值
     */
    private SourceValueMapper sourceValueMapper;


    /**
     * 来源值
     */
    private SourceValueServiceResults sourceValueServiceResults;


    /**
     * 来源值增强条件
     */
    private SourceValueServiceQuery sourceValueServiceQuery;


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceValueQuery:
     * @return  java.util.List<com.entity.SourceValueVO>
     */
    @Override
    public List<SourceValueVO> listEnhance(SourceValueQuery sourceValueQuery) {
        SourceValue sourceValue = GeneralConvertor.convertor(sourceValueQuery, SourceValue.class);
        QueryWrapper<SourceValue> queryWrapper = new QueryWrapper<>(sourceValue);
        // TODO 自动生成查询，禁止手动写语句
        sourceValueServiceQuery.query(sourceValueQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(sourceValueQuery, queryWrapper);
        //DO数据
        List<SourceValue> sourceValueDO = sourceValueMapper.selectList(queryWrapper);
        //VO数据
        List<SourceValueVO> sourceValueVO = GeneralConvertor.convertor(sourceValueDO, SourceValueVO.class);
        return sourceValueServiceResults.assignment(sourceValueVO);
    }


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   page:
     * @param   sourceValueQuery:
     * @return  com.baomidou.mybatisplus.core.metadata.IPage
     */
    @Override
    public Page<SourceValueVO> pageEnhance(Page page, SourceValueQuery sourceValueQuery) {
        SourceValue sourceValue = GeneralConvertor.convertor(sourceValueQuery, SourceValue.class);
        QueryWrapper<SourceValue> queryWrapper = new QueryWrapper<>(sourceValue);
        // TODO 自动生成查询，禁止手动写语句
        sourceValueServiceQuery.query(sourceValueQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(sourceValueQuery, queryWrapper);
        //DO数据
        Page<SourceValue> pageDO = sourceValueMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<SourceValueVO> pageVO = sourceValueServiceResults.toPageVO(pageDO);
        return sourceValueServiceResults.assignment(pageVO);
    }


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceValueQuery:
     * @return  java.util.List<com.entity.SourceValueVO>
     */
    @Override
    public SourceValueVO getOneEnhance(SourceValueQuery sourceValueQuery) {
        SourceValue sourceValue = GeneralConvertor.convertor(sourceValueQuery, SourceValue.class);
        QueryWrapper<SourceValue> queryWrapper = new QueryWrapper<>(sourceValue);
        // TODO 自动生成查询，禁止手动写语句
        sourceValueServiceQuery.query(sourceValueQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(sourceValueQuery, queryWrapper);
        //DO数据
        SourceValue sourceValueDO = sourceValueMapper.selectOne(queryWrapper);
        //VO数据
        SourceValueVO sourceValueVO = GeneralConvertor.convertor(sourceValueDO, SourceValueVO.class);
        return sourceValueServiceResults.assignment(sourceValueVO);
    }


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceValueQuery:
     * @return  java.lang.Integer
     */
    @Override
    public Long countEnhance(SourceValueQuery sourceValueQuery) {
        SourceValue sourceValue = GeneralConvertor.convertor(sourceValueQuery, SourceValue.class);
        QueryWrapper<SourceValue> queryWrapper = new QueryWrapper<>(sourceValue);
        // TODO 自动生成查询，禁止手动写语句
        sourceValueServiceQuery.query(sourceValueQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(sourceValueQuery, queryWrapper);
        return sourceValueMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceValueBO:
     * @return  java.lang.String
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(SourceValueBO sourceValueBO) {
        SourceValue sourceValue = GeneralConvertor.convertor(sourceValueBO, SourceValue.class);
        sourceValueMapper.insert(sourceValue);
        return sourceValue.getId();
    }


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceValueBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(SourceValueBO sourceValueBO) {
        SourceValue sourceValue = GeneralConvertor.convertor(sourceValueBO, SourceValue.class);
        UpdateWrapper<SourceValue > updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", sourceValueBO.getId());
        Integer i = sourceValueMapper.update(sourceValue, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceValueBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(SourceValueBO sourceValueBO) {
        SourceValue sourceValue = GeneralConvertor.convertor(sourceValueBO, SourceValue.class);
        QueryWrapper<SourceValue> queryWrapper = new QueryWrapper<>(sourceValue);
        Integer i = sourceValueMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 查询人工查询条件
     *
     * @author     	孙凯伦
     * @since   	2021-11-03 03:57:55
     * @param       sourceValueQuery 来源值
     * @return      QueryWrapper
     */
     private QueryWrapper queryArtificial(SourceValueQuery sourceValueQuery, QueryWrapper<SourceValue> queryWrapper) {
        return queryWrapper;
    }
}