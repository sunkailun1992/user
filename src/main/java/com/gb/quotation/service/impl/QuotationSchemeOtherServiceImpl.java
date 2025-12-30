package com.gb.quotation.service.impl;

import com.gb.quotation.entity.QuotationSchemeOther;
import com.gb.quotation.mapper.QuotationSchemeOtherMapper;
import com.gb.quotation.service.QuotationSchemeOtherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.scheduling.annotation.Async;
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
 * 报价方案其他 服务实现类
 * </p>
 *
 * @author 尹涛涛
 * @since 2021-05-22
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class QuotationSchemeOtherServiceImpl extends ServiceImpl<QuotationSchemeOtherMapper, QuotationSchemeOther> implements QuotationSchemeOtherService {


    /**
     * 报价方案其他
     */
    private QuotationSchemeOtherMapper quotationSchemeOtherMapper;


    /**
     * 集合条件查询
     *
     * @param quotationSchemeOther:
     * @return java.util.List<com.entity.QuotationSchemeOther>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    public List<QuotationSchemeOther> listEnhance(QuotationSchemeOther quotationSchemeOther) {
        QueryWrapper<QuotationSchemeOther> queryWrapper = new QueryWrapper<>(quotationSchemeOther);
        query(quotationSchemeOther, queryWrapper);
        return assignment(quotationSchemeOtherMapper.selectList(queryWrapper));
    }


    /**
     * 分页条件查询
     *
     * @param quotationSchemeOther:
     * @param page:
     * @return java.util.List<com.entity.QuotationSchemeOther>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    public IPage pageEnhance(Page page, QuotationSchemeOther quotationSchemeOther) {
        QueryWrapper<QuotationSchemeOther> queryWrapper = new QueryWrapper<>(quotationSchemeOther);
        query(quotationSchemeOther, queryWrapper);
        return assignment(quotationSchemeOtherMapper.selectPage(page, queryWrapper));
    }


    /**
     * 单条条件查询
     *
     * @param quotationSchemeOther:
     * @return java.util.List<com.entity.QuotationSchemeOther>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    public QuotationSchemeOther getOneEnhance(QuotationSchemeOther quotationSchemeOther) {
        QueryWrapper<QuotationSchemeOther> queryWrapper = new QueryWrapper<>(quotationSchemeOther);
        query(quotationSchemeOther, queryWrapper);
        return assignment(quotationSchemeOtherMapper.selectOne(queryWrapper));
    }


    /**
     * 总数
     *
     * @param quotationSchemeOther:
     * @return java.util.List<com.entity.QuotationSchemeOther>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    public Long countEnhance(QuotationSchemeOther quotationSchemeOther) {
        QueryWrapper<QuotationSchemeOther> queryWrapper = new QueryWrapper<>(quotationSchemeOther);
        query(quotationSchemeOther, queryWrapper);
        return quotationSchemeOtherMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     *
     * @param quotationSchemeOther:
     * @return java.util.List<com.entity.QuotationSchemeOther>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean saveEnhance(QuotationSchemeOther quotationSchemeOther) {
        Integer i = quotationSchemeOtherMapper.insert(quotationSchemeOther);
        return i > 0 ? true : false;
    }


    /**
     * 修改
     *
     * @param quotationSchemeOther:
     * @return java.util.List<com.entity.QuotationSchemeOther>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(QuotationSchemeOther quotationSchemeOther) {
        UpdateWrapper<QuotationSchemeOther> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", quotationSchemeOther.getId());
        Integer i = quotationSchemeOtherMapper.update(quotationSchemeOther, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     *
     * @param quotationSchemeOther:
     * @return java.util.List<com.entity.QuotationSchemeOther>
     * @author 尹涛涛
     * @since 2021-05-22
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(QuotationSchemeOther quotationSchemeOther) {
        QueryWrapper<QuotationSchemeOther> queryWrapper = new QueryWrapper<>(quotationSchemeOther);
        Integer i = quotationSchemeOtherMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 增强查询条件
     *
     * @param quotationSchemeOther:
     * @param queryWrapper:
     * @return void
     * @author 尹涛涛
     * @since 2021-05-22
     */
    private void query(QuotationSchemeOther quotationSchemeOther, QueryWrapper<QuotationSchemeOther> queryWrapper) {
        /**
         * 排序
         */
        if (quotationSchemeOther.getCollation() != null && StringUtils.isNotBlank(quotationSchemeOther.getCollationFields())) {
            if (quotationSchemeOther.getCollation()) {
                queryWrapper.orderByAsc(quotationSchemeOther.getCollationFields());
            } else {
                queryWrapper.orderByDesc(quotationSchemeOther.getCollationFields());
            }
        } else {
            queryWrapper.orderByDesc(quotationSchemeOther.getCollationFields());
        }

        /**
         * 显示字段
         */
        if (StringUtils.isNotBlank(quotationSchemeOther.getFields())) {
            queryWrapper.select(quotationSchemeOther.getFields());
        }
    }


    /**
     * 单条，增强返回参数追加
     *
     * @param quotationSchemeOther:
     * @return QuotationSchemeOther
     * @author 尹涛涛
     * @since 2021-05-22
     */
    private QuotationSchemeOther assignment(QuotationSchemeOther quotationSchemeOther) {
        return quotationSchemeOther;
    }

    /**
     * 分页,增强返回参数追加
     *
     * @param quotationSchemeOtherList:
     * @return QuotationSchemeOther
     * @author 尹涛涛
     * @since 2021-05-22
     */
    private IPage assignment(IPage<QuotationSchemeOther> quotationSchemeOtherList) {
        quotationSchemeOtherList.getRecords().forEach(quotationSchemeOther -> {
        });
        return quotationSchemeOtherList;
    }


    /**
     * 集合,增强返回参数追加
     *
     * @param quotationSchemeOtherList:
     * @return QuotationSchemeOther
     * @author 尹涛涛
     * @since 2021-05-22
     */
    private List<QuotationSchemeOther> assignment(List<QuotationSchemeOther> quotationSchemeOtherList) {
        quotationSchemeOtherList.forEach(quotationSchemeOther -> {
        });
        return quotationSchemeOtherList;
    }
}
