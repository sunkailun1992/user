package com.gb.quotation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.quotation.entity.QuotationScheme;

import java.util.List;


/**
 * <p>
 * 报价方案 服务类
 * </p>
 *
 * @author 尹涛涛
 * @since 2021-05-22
 */
public interface QuotationSchemeService extends IService<QuotationScheme> {
    /**
     * pdf模板文件地址
     */
    String PDF_TEMPLATE_PATH = "template/quotationTemplate.pdf";

    /**
     * 集合条件查询
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationScheme:
     * @return      java.util.List<com.entity.QuotationScheme>
     */
    List<QuotationScheme> listEnhance(QuotationScheme quotationScheme);


    /**
     * 分页条件查询
     * @author 尹涛涛
     * @since 2021-05-22
     * @param page:
     * @param quotationScheme:
     * @return      java.util.List<com.entity.QuotationScheme>
     */
    IPage pageEnhance(Page page, QuotationScheme quotationScheme);


    /**
     * 单条条件查询
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationScheme:
     * @return      java.util.List<com.entity.QuotationScheme>
     */
    QuotationScheme getOneEnhance(QuotationScheme quotationScheme);


    /**
     * 总数
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationScheme:
     * @return      java.util.List<com.entity.QuotationScheme>
     */
    Long countEnhance(QuotationScheme quotationScheme);


    /**
     * 新增
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationScheme:
     * @throws Exception
     * @return      java.util.List<com.entity.QuotationScheme>
     */
    Boolean saveEnhance(QuotationScheme quotationScheme) throws Exception;


    /**
     * 修改
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationScheme:
     * @return      java.util.List<com.entity.QuotationScheme>
     */
    Boolean updateEnhance(QuotationScheme quotationScheme);


    /**
     * 删除
     * @author 尹涛涛
     * @since 2021-05-22
     * @param quotationScheme:
     * @return      java.util.List<com.entity.QuotationScheme>
     */
    Boolean removeEnhance(QuotationScheme quotationScheme);

    /**
     * 根据id查询报价单详情
     * @author 尹涛涛
     * @since 2021-06-02
     * @param quotationScheme:
     * @return      com.entity.QuotationScheme
     */
    QuotationScheme findQuotationSchemeInfoById(QuotationScheme quotationScheme);
}
