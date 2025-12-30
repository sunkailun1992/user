package com.gb.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.platform.entity.ExternalSystem;
import com.gb.platform.entity.bo.ExternalSystemBO;
import com.gb.platform.entity.query.ExternalSystemQuery;
import com.gb.platform.entity.vo.ExternalSystemVO;
import com.gb.platform.mapper.ExternalSystemMapper;
import com.gb.platform.service.ExternalSystemService;
import com.gb.platform.service.query.ExternalSystemServiceQuery;
import com.gb.platform.service.results.ExternalSystemServiceResults;
import com.gb.utils.GeneralConvertor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * TODO 外部系统，Service服务实现层
 * 代码生成器
 *
 * @author sunx
 * @className ExternalSystemServiceImpl
 * @time 2022-12-16 03:10:08
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class ExternalSystemServiceImpl extends ServiceImpl<ExternalSystemMapper, ExternalSystem> implements ExternalSystemService {


    /**
     * 外部系统
     */
    private ExternalSystemMapper externalSystemMapper;


    /**
     * 外部系统
     */
    private ExternalSystemServiceResults externalSystemServiceResults;


    /**
     * 外部系统增强条件
     */
    private ExternalSystemServiceQuery externalSystemServiceQuery;


    /**
     * TODO 集合
     *
     * @param externalSystemQuery 外部系统
     * @return List<ExternalSystemVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-12-16 03:10:08
     */
    @Override
    public List<ExternalSystemVO> listEnhance(ExternalSystemQuery externalSystemQuery) {
        ExternalSystem externalSystem = GeneralConvertor.convertor(externalSystemQuery, ExternalSystem.class);
        QueryWrapper<ExternalSystem> queryWrapper = new QueryWrapper<>(externalSystem);
        // TODO 自动生成查询，禁止手动写语句
        externalSystemServiceQuery.query(externalSystemQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(externalSystemQuery, queryWrapper);
        // DO数据
        List<ExternalSystem> externalSystemDO = externalSystemMapper.selectList(queryWrapper);
        // VO数据
        List<ExternalSystemVO> externalSystemVO = GeneralConvertor.convertor(externalSystemDO, ExternalSystemVO.class);
        // 判断是否增强
        if (externalSystemQuery.getAssignment() == null) {
            return externalSystemServiceResults.assignment(externalSystemVO);
        } else {
            return externalSystemQuery.getAssignment() ? externalSystemServiceResults.assignment(externalSystemVO) : externalSystemVO;
        }
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param externalSystemQuery 外部系统
     * @return Page<ExternalSystemVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-12-16 03:10:08
     */
    @Override
    public Page<ExternalSystemVO> pageEnhance(Page page, ExternalSystemQuery externalSystemQuery) {
        ExternalSystem externalSystem = GeneralConvertor.convertor(externalSystemQuery, ExternalSystem.class);
        QueryWrapper<ExternalSystem> queryWrapper = new QueryWrapper<>(externalSystem);
        // TODO 自动生成查询，禁止手动写语句
        externalSystemServiceQuery.query(externalSystemQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(externalSystemQuery, queryWrapper);
        // DO数据
        Page<ExternalSystem> pageDO = externalSystemMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<ExternalSystemVO> pageVO = externalSystemServiceResults.toPageVO(pageDO);
        // 判断是否增强
        if (externalSystemQuery.getAssignment() == null) {
            return externalSystemServiceResults.assignment(pageVO);
        } else {
            return externalSystemQuery.getAssignment() ? externalSystemServiceResults.assignment(pageVO) : pageVO;
        }
    }


    /**
     * TODO 单条
     *
     * @param externalSystemQuery 外部系统
     * @return ExternalSystemVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-12-16 03:10:08
     */
    @Override
    public ExternalSystemVO getOneEnhance(ExternalSystemQuery externalSystemQuery) {
        ExternalSystem externalSystem = GeneralConvertor.convertor(externalSystemQuery, ExternalSystem.class);
        QueryWrapper<ExternalSystem> queryWrapper = new QueryWrapper<>(externalSystem);
        // TODO 自动生成查询，禁止手动写语句
        externalSystemServiceQuery.query(externalSystemQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(externalSystemQuery, queryWrapper);
        // DO数据
        ExternalSystem externalSystemDO = externalSystemMapper.selectOne(queryWrapper);
        // VO数据
        ExternalSystemVO externalSystemVO = GeneralConvertor.convertor(externalSystemDO, ExternalSystemVO.class);
        // 判断是否增强
        if (externalSystemQuery.getAssignment() == null) {
            return externalSystemServiceResults.assignment(externalSystemVO);
        } else {
            return externalSystemQuery.getAssignment() ? externalSystemServiceResults.assignment(externalSystemVO) : externalSystemVO;
        }
    }


    /**
     * TODO 总数
     *
     * @param externalSystemQuery 外部系统
     * @return Integer
     * @author sunx
     * @methodName countEnhance
     * @time 2022-12-16 03:10:08
     */
    @Override
    public Long countEnhance(ExternalSystemQuery externalSystemQuery) {
        ExternalSystem externalSystem = GeneralConvertor.convertor(externalSystemQuery, ExternalSystem.class);
        QueryWrapper<ExternalSystem> queryWrapper = new QueryWrapper<>(externalSystem);
        // TODO 自动生成查询，禁止手动写语句
        externalSystemServiceQuery.query(externalSystemQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(externalSystemQuery, queryWrapper);
        return externalSystemMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param externalSystemBO 外部系统
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-12-16 03:10:08
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(ExternalSystemBO externalSystemBO) {
        ExternalSystem externalSystem = GeneralConvertor.convertor(externalSystemBO, ExternalSystem.class);
        externalSystemMapper.insert(externalSystem);
        return externalSystem.getId();
    }


    /**
     * TODO 修改
     *
     * @param externalSystemBO 外部系统
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-12-16 03:10:08
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(ExternalSystemBO externalSystemBO) {
        ExternalSystem externalSystem = GeneralConvertor.convertor(externalSystemBO, ExternalSystem.class);
        UpdateWrapper<ExternalSystem> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", externalSystemBO.getId());
        Integer i = externalSystemMapper.update(externalSystem, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 删除
     *
     * @param externalSystemBO 外部系统
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-12-16 03:10:08
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(ExternalSystemBO externalSystemBO) {
        ExternalSystem externalSystem = GeneralConvertor.convertor(externalSystemBO, ExternalSystem.class);
        QueryWrapper<ExternalSystem> queryWrapper = new QueryWrapper<>(externalSystem);
        Integer i = externalSystemMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 人工查询条件
     *
     * @param externalSystemQuery 外部系统
     * @return QueryWrapper
     * @author sunx
     * @methodName queryArtificial
     * @time 2022-12-16 03:10:08
     */
    private QueryWrapper queryArtificial(ExternalSystemQuery externalSystemQuery, QueryWrapper<ExternalSystem> queryWrapper) {
        if(StringUtils.isNotBlank(externalSystemQuery.getNameQuery())) {
            queryWrapper.likeRight("name", externalSystemQuery.getNameQuery());
        }
        return queryWrapper;
    }
}