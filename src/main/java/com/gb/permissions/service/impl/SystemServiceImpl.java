package com.gb.permissions.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.permissions.entity.System;
import com.gb.permissions.entity.bo.SystemBO;
import com.gb.permissions.entity.query.SystemQuery;
import com.gb.permissions.entity.vo.SystemVO;
import com.gb.permissions.mapper.SystemMapper;
import com.gb.permissions.service.SystemService;
import com.gb.permissions.service.query.SystemServiceQuery;
import com.gb.permissions.service.results.SystemServiceResults;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.exception.BusinessException;
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

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 孙凯伦
 * @since: 2021-10-21 01:59:43
 * @description: TODO 系统表，Service服务实现层
 * @source: 代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class SystemServiceImpl extends ServiceImpl<SystemMapper, System> implements SystemService {


    /**
     * 系统表
     */
    private SystemMapper systemMapper;


    /**
     * 系统表
     */
    private SystemServiceResults systemServiceResults;


    /**
     * 集合条件查询
     *
     * @param systemQuery:
     * @return java.util.List<com.entity.SystemVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Override
    public List<SystemVO> listEnhance(SystemQuery systemQuery) {
        System system = GeneralConvertor.convertor(systemQuery, System.class);
        QueryWrapper<System> queryWrapper = new QueryWrapper<>(system);
        // TODO 自动生成查询，禁止手动写语句
        SystemServiceQuery.query(systemQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(systemQuery, queryWrapper);
        //DO数据
        List<System> systemDO = systemMapper.selectList(queryWrapper);
        //VO数据
        List<SystemVO> systemVOList = GeneralConvertor.convertor(systemDO, SystemVO.class);
        return systemServiceResults.assignment(systemVOList);
    }


    /**
     * 分页条件查询
     *
     * @param page:
     * @param systemQuery:
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Override
    public Page<SystemVO> pageEnhance(Page page, SystemQuery systemQuery) {
        System system = GeneralConvertor.convertor(systemQuery, System.class);
        QueryWrapper<System> queryWrapper = new QueryWrapper<>(system);
        //TODO 自动生成查询，禁止手动写语句
        SystemServiceQuery.query(systemQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(systemQuery, queryWrapper);
        //DO数据
        Page<System> pageDO = systemMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<SystemVO> pageVO = systemServiceResults.toPageVO(pageDO);
        return systemServiceResults.assignment(pageVO);
    }


    /**
     * 单条条件查询
     *
     * @param systemQuery:
     * @return java.util.List<com.entity.SystemVO>
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Override
    public SystemVO getOneEnhance(SystemQuery systemQuery) {
        System system = GeneralConvertor.convertor(systemQuery, System.class);
        QueryWrapper<System> queryWrapper = new QueryWrapper<>(system);
        //TODO 自动生成查询，禁止手动写语句
        SystemServiceQuery.query(systemQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(systemQuery, queryWrapper);
        //DO数据
        System systemDO = systemMapper.selectOne(queryWrapper);
        //VO数据
        SystemVO systemVO = GeneralConvertor.convertor(systemDO, SystemVO.class);
        return systemServiceResults.assignment(systemQuery.isQueryRole(), systemVO);
    }


    /**
     * 总数
     *
     * @param systemQuery:
     * @return java.lang.Integer
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Override
    public Long countEnhance(SystemQuery systemQuery) {
        System system = GeneralConvertor.convertor(systemQuery, System.class);
        QueryWrapper<System> queryWrapper = new QueryWrapper<>(system);
        //TODO 自动生成查询，禁止手动写语句
        SystemServiceQuery.query(systemQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(systemQuery, queryWrapper);
        return systemMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     *
     * @param systemBO:
     * @return java.lang.String
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(SystemBO systemBO) {
        //判断是否为空
        if (StringUtils.isNotBlank(systemBO.getCode())) {
            //限制
            Long x = countEnhance(new SystemQuery() {{
                setCode(systemBO.getCode());
            }});
            if (x > 0) {
                throw new BusinessException("系统编码重复");
            }
        }
        System system = GeneralConvertor.convertor(systemBO, System.class);
        systemMapper.insert(system);
        return system.getId();
    }


    /**
     * 修改
     *
     * @param systemBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(SystemBO systemBO) {
        //默认不修改code
        systemBO.setCode(null);
        System system = GeneralConvertor.convertor(systemBO, System.class);
        UpdateWrapper<System> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", systemBO.getId());
        Integer i = systemMapper.update(system, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     *
     * @param systemBO:
     * @return java.lang.Boolean
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(SystemBO systemBO) {
        System system = GeneralConvertor.convertor(systemBO, System.class);
        QueryWrapper<System> queryWrapper = new QueryWrapper<>(system);
        Integer i = systemMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 查询人工查询条件
     *
     * @param systemQuery 系统表
     * @return QueryWrapper
     * @author 孙凯伦
     * @since 2021-10-21 01:59:43
     */
    private QueryWrapper queryArtificial(SystemQuery systemQuery, QueryWrapper<System> queryWrapper) {
        if (CollectionUtils.isNotEmpty(systemQuery.getSystemIdList())) {
            queryWrapper.in("id", systemQuery.getSystemIdList());
        }
        return queryWrapper;
    }
}