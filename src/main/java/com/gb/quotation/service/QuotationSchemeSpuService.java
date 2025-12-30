package com.gb.quotation.service;

import com.gb.quotation.entity.QuotationSchemeSpu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * <p>
 * 报价方案产品 服务类
 * </p>
 *
 * @author 尹涛涛
 * @since 2021-05-22
 */
public interface QuotationSchemeSpuService extends IService<QuotationSchemeSpu> {


    /**
     * 集合条件查询
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationSchemeSpu:
     * @return      java.util.List<com.entity.QuotationSchemeSpu>
     */
    List<QuotationSchemeSpu> listEnhance(QuotationSchemeSpu quotationSchemeSpu);


    /**
     * 分页条件查询
     * @author 尹涛涛
     * @since 2021-05-22
     * @param page:
     * @param quotationSchemeSpu:
     * @return      java.util.List<com.entity.QuotationSchemeSpu>
     */
    IPage pageEnhance(Page page, QuotationSchemeSpu quotationSchemeSpu);


    /**
     * 单条条件查询
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationSchemeSpu:
     * @return      java.util.List<com.entity.QuotationSchemeSpu>
     */
    QuotationSchemeSpu getOneEnhance(QuotationSchemeSpu quotationSchemeSpu);


    /**
     * 总数
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationSchemeSpu:
     * @return      java.util.List<com.entity.QuotationSchemeSpu>
     */
    Long countEnhance(QuotationSchemeSpu quotationSchemeSpu);


    /**
     * 新增
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationSchemeSpu:
     * @return      java.util.List<com.entity.QuotationSchemeSpu>
     */
    Boolean saveEnhance(QuotationSchemeSpu quotationSchemeSpu);


    /**
     * 修改
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationSchemeSpu:
     * @return      java.util.List<com.entity.QuotationSchemeSpu>
     */
    Boolean updateEnhance(QuotationSchemeSpu quotationSchemeSpu);


    /**
     * 删除
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationSchemeSpu:
     * @return      java.util.List<com.entity.QuotationSchemeSpu>
     */
    Boolean removeEnhance(QuotationSchemeSpu quotationSchemeSpu);
}
