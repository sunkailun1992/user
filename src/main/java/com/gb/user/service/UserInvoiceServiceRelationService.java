package com.gb.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.user.entity.UserInvoiceServiceRelation;

import java.util.List;


/**
 * <p>
 * 用户发票与发票服务关联关系表 服务类
 * </p>
 *
 * @author sunx
 * @since 2021-05-27
 */
public interface UserInvoiceServiceRelationService extends IService<UserInvoiceServiceRelation> {


    /**
     * 集合条件查询
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelation:
     * @return      java.util.List<com.entity.UserInvoiceServiceRelation>
     */
    List<UserInvoiceServiceRelation> listEnhance(UserInvoiceServiceRelation userInvoiceServiceRelation);


    /**
     * 分页条件查询
     * @author sunx
     * @since 2021-05-27
     * @param page:
     * @param userInvoiceServiceRelation:
     * @return      java.util.List<com.entity.UserInvoiceServiceRelation>
     */
    IPage pageEnhance(Page page, UserInvoiceServiceRelation userInvoiceServiceRelation);


    /**
     * 单条条件查询
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelation:
     * @return      java.util.List<com.entity.UserInvoiceServiceRelation>
     */
    UserInvoiceServiceRelation getOneEnhance(UserInvoiceServiceRelation userInvoiceServiceRelation);


    /**
     * 总数
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelation:
     * @return      java.util.List<com.entity.UserInvoiceServiceRelation>
     */
    Long countEnhance(UserInvoiceServiceRelation userInvoiceServiceRelation);


    /**
     * 新增
     * @author sunx
     * @since 2021-05-27
     * @param userFlag: 用户设置过来的标志：true，false
     * @param userInvoiceServiceRelation:
     * @throws Exception:
     * @return      java.util.List<com.entity.UserInvoiceServiceRelation>
     */
    Boolean saveEnhance(boolean userFlag, UserInvoiceServiceRelation userInvoiceServiceRelation) throws Exception;


    /**
     * 修改
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelation:
     * @return      java.util.List<com.entity.UserInvoiceServiceRelation>
     */
    Boolean updateEnhance(UserInvoiceServiceRelation userInvoiceServiceRelation);


    /**
     * 删除
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelation:
     * @return      java.util.List<com.entity.UserInvoiceServiceRelation>
     */
    Boolean removeEnhance(UserInvoiceServiceRelation userInvoiceServiceRelation);
}
