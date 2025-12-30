package com.gb.quotation.service.impl;

import com.gb.quotation.entity.QuotationSchemeSpu;
import com.gb.quotation.mapper.QuotationSchemeSpuMapper;
import com.gb.quotation.service.QuotationSchemeSpuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Setter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * <p>
 * 报价方案产品 服务实现类
 * </p>
 *
 * @author 尹涛涛
 * @since 2021-05-22
 */
@Slf4j
@Service
public class QuotationSchemeSpuServiceImpl extends ServiceImpl<QuotationSchemeSpuMapper, QuotationSchemeSpu> implements QuotationSchemeSpuService {


    /**
     * 报价方案产品
     */
    @Autowired
    private QuotationSchemeSpuMapper quotationSchemeSpuMapper;


    /**
     * 集合条件查询
     *
     * @param quotationSchemeSpu:
     * @return java.util.List<com.entity.QuotationSchemeSpu>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    public List<QuotationSchemeSpu> listEnhance(QuotationSchemeSpu quotationSchemeSpu) {
        QueryWrapper<QuotationSchemeSpu> queryWrapper = new QueryWrapper<>(quotationSchemeSpu);
        query(quotationSchemeSpu, queryWrapper);
        return assignment(quotationSchemeSpuMapper.selectList(queryWrapper));
    }


    /**
     * 分页条件查询
     *
     * @param quotationSchemeSpu:
     * @param page:
     * @return java.util.List<com.entity.QuotationSchemeSpu>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    public IPage pageEnhance(Page page, QuotationSchemeSpu quotationSchemeSpu) {
        QueryWrapper<QuotationSchemeSpu> queryWrapper = new QueryWrapper<>(quotationSchemeSpu);
        query(quotationSchemeSpu, queryWrapper);
        return assignment(quotationSchemeSpuMapper.selectPage(page, queryWrapper));
    }


    /**
     * 单条条件查询
     *
     * @param quotationSchemeSpu:
     * @return java.util.List<com.entity.QuotationSchemeSpu>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    public QuotationSchemeSpu getOneEnhance(QuotationSchemeSpu quotationSchemeSpu) {
        QueryWrapper<QuotationSchemeSpu> queryWrapper = new QueryWrapper<>(quotationSchemeSpu);
        query(quotationSchemeSpu, queryWrapper);
        return assignment(quotationSchemeSpuMapper.selectOne(queryWrapper));
    }


    /**
     * 总数
     *
     * @param quotationSchemeSpu:
     * @return java.util.List<com.entity.QuotationSchemeSpu>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    public Long countEnhance(QuotationSchemeSpu quotationSchemeSpu) {
        QueryWrapper<QuotationSchemeSpu> queryWrapper = new QueryWrapper<>(quotationSchemeSpu);
        query(quotationSchemeSpu, queryWrapper);
        return quotationSchemeSpuMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     *
     * @param quotationSchemeSpu:
     * @return java.util.List<com.entity.QuotationSchemeSpu>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean saveEnhance(QuotationSchemeSpu quotationSchemeSpu) {
        Integer i = quotationSchemeSpuMapper.insert(quotationSchemeSpu);
        return i > 0 ? true : false;
    }


    /**
     * 修改
     *
     * @param quotationSchemeSpu:
     * @return java.util.List<com.entity.QuotationSchemeSpu>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(QuotationSchemeSpu quotationSchemeSpu) {
        UpdateWrapper<QuotationSchemeSpu> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", quotationSchemeSpu.getId());
        Integer i = quotationSchemeSpuMapper.update(quotationSchemeSpu, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     *
     * @param quotationSchemeSpu:
     * @return java.util.List<com.entity.QuotationSchemeSpu>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(QuotationSchemeSpu quotationSchemeSpu) {
        QueryWrapper<QuotationSchemeSpu> queryWrapper = new QueryWrapper<>(quotationSchemeSpu);
        Integer i = quotationSchemeSpuMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 增强查询条件
     *
     * @param quotationSchemeSpu:
     * @param queryWrapper:
     * @return void
     * @author 尹涛涛
     * @since 2021-05-22
     */
    private void query(QuotationSchemeSpu quotationSchemeSpu, QueryWrapper<QuotationSchemeSpu> queryWrapper) {
        /**
         * 排序
         */
        if (quotationSchemeSpu.getCollation() != null && StringUtils.isNotBlank(quotationSchemeSpu.getCollationFields())) {
            if (quotationSchemeSpu.getCollation()) {
                queryWrapper.orderByAsc(quotationSchemeSpu.getCollationFields());
            } else {
                queryWrapper.orderByDesc(quotationSchemeSpu.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(quotationSchemeSpu.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(quotationSchemeSpu.getFields())) {
            queryWrapper.select(quotationSchemeSpu.getFields());
        }
    }


    /**
     * 单条，增强返回参数追加
     *
     * @param quotationSchemeSpu:
     * @return QuotationSchemeSpu
     * @author 尹涛涛
     * @since 2021-05-22
     */
    private QuotationSchemeSpu assignment(QuotationSchemeSpu quotationSchemeSpu) {
        return quotationSchemeSpu;
    }

    /**
     * 分页,增强返回参数追加
     *
     * @param quotationSchemeSpuList:
     * @return QuotationSchemeSpu
     * @author 尹涛涛
     * @since 2021-05-22
     */
    private IPage assignment(IPage<QuotationSchemeSpu> quotationSchemeSpuList) {
        quotationSchemeSpuList.getRecords().forEach(quotationSchemeSpu -> {
        });
        return quotationSchemeSpuList;
    }


    /**
     * 集合,增强返回参数追加
     *
     * @param quotationSchemeSpuList:
     * @return QuotationSchemeSpu
     * @author 尹涛涛
     * @since 2021-05-22
     */
    private List<QuotationSchemeSpu> assignment(List<QuotationSchemeSpu> quotationSchemeSpuList) {
        quotationSchemeSpuList.forEach(quotationSchemeSpu -> {
        });
        return quotationSchemeSpuList;
    }
}
