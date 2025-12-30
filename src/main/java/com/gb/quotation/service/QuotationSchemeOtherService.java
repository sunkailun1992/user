package com.gb.quotation.service;

import com.gb.quotation.entity.QuotationSchemeOther;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * <p>
 * 报价方案其他 服务类
 * </p>
 *
 * @author 尹涛涛
 * @since 2021-05-22
 */
public interface QuotationSchemeOtherService extends IService<QuotationSchemeOther> {


    /**
     * 集合条件查询
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationSchemeOther:
     * @return      java.util.List<com.entity.QuotationSchemeOther>
     */
    List<QuotationSchemeOther> listEnhance(QuotationSchemeOther quotationSchemeOther);


    /**
     * 分页条件查询
     * @author 尹涛涛
     * @since 2021-05-22
     * @param page:
     * @param quotationSchemeOther:
     * @return      java.util.List<com.entity.QuotationSchemeOther>
     */
    IPage pageEnhance(Page page, QuotationSchemeOther quotationSchemeOther);


    /**
     * 单条条件查询
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationSchemeOther:
     * @return      java.util.List<com.entity.QuotationSchemeOther>
     */
    QuotationSchemeOther getOneEnhance(QuotationSchemeOther quotationSchemeOther);


    /**
     * 总数
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationSchemeOther:
     * @return      java.util.List<com.entity.QuotationSchemeOther>
     */
    Long countEnhance(QuotationSchemeOther quotationSchemeOther);


    /**
     * 新增
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationSchemeOther:
     * @return      java.util.List<com.entity.QuotationSchemeOther>
     */
    Boolean saveEnhance(QuotationSchemeOther quotationSchemeOther);


    /**
     * 修改
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationSchemeOther:
     * @return      java.util.List<com.entity.QuotationSchemeOther>
     */
    Boolean updateEnhance(QuotationSchemeOther quotationSchemeOther);


    /**
     * 删除
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationSchemeOther:
     * @return      java.util.List<com.entity.QuotationSchemeOther>
     */
    Boolean removeEnhance(QuotationSchemeOther quotationSchemeOther);
}
