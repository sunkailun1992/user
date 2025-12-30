package com.gb.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.user.entity.UserInvoice;

import java.util.List;


/**
 * <p>
 * 用户发票表 服务类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
public interface UserInvoiceService extends IService<UserInvoice> {


    /**
     * 集合条件查询
     * @author sunx
     * @since 2021-05-25
     * @param userInvoice:
     * @return      java.util.List<com.entity.UserInvoice>
     */
    List<UserInvoice> listEnhance(UserInvoice userInvoice);


    /**
     * 分页条件查询
     * @author sunx
     * @since 2021-05-25
     * @param page:
     * @param userInvoice:
     * @return      java.util.List<com.entity.UserInvoice>
     */
    IPage pageEnhance(Page page, UserInvoice userInvoice);


    /**
     * 单条条件查询
     * @author sunx
     * @since 2021-05-25
     * @param userInvoice:
     * @return      java.util.List<com.entity.UserInvoice>
     */
    UserInvoice getOneEnhance(UserInvoice userInvoice);


    /**
     * 总数
     * @author sunx
     * @since 2021-05-25
     * @param userInvoice:
     * @return      java.util.List<com.entity.UserInvoice>
     */
    Long countEnhance(UserInvoice userInvoice);


    /**
     * 新增
     * @author sunx
     * @since 2021-05-25
     * @param userInvoice:
     * @throws Exception:
     * @return      java.util.List<com.entity.UserInvoice>
     */
    Boolean saveEnhance(UserInvoice userInvoice) throws Exception;


    /**
     * 修改
     * @author sunx
     * @since 2021-05-25
     * @param stateUpdate: 状态更新
     * @param userInvoice:
     * @throws Exception:
     * @return      java.util.List<com.entity.UserInvoice>
     */
    Boolean updateEnhance(boolean stateUpdate, UserInvoice userInvoice) throws Exception;


    /**
     * 删除
     * @author sunx
     * @since 2021-05-25
     * @param userInvoice:
     * @return      java.util.List<com.entity.UserInvoice>
     */
    Boolean removeEnhance(UserInvoice userInvoice);
}
