package com.gb.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.user.entity.UserShippingAddress;

import java.util.List;


/**
 * <p>
 * 用户收货地址 服务类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
public interface UserShippingAddressService extends IService<UserShippingAddress> {


    /**
     * 集合条件查询
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddress:
     * @return      java.util.List<com.entity.UserShippingAddress>
     */
    List<UserShippingAddress> listEnhance(UserShippingAddress userShippingAddress);


    /**
     * 分页条件查询
     * @author sunx
     * @since 2021-05-25
     * @param page:
     * @param userShippingAddress:
     * @return      java.util.List<com.entity.UserShippingAddress>
     */
    IPage pageEnhance(Page page, UserShippingAddress userShippingAddress);


    /**
     * 单条条件查询
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddress:
     * @return      java.util.List<com.entity.UserShippingAddress>
     */
    UserShippingAddress getOneEnhance(UserShippingAddress userShippingAddress);


    /**
     * 总数
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddress:
     * @return      java.util.List<com.entity.UserShippingAddress>
     */
    Long countEnhance(UserShippingAddress userShippingAddress);


    /**
     * 新增
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddress:
     * @throws Exception:
     * @return      java.util.List<com.entity.UserShippingAddress>
     */
    Boolean saveEnhance(UserShippingAddress userShippingAddress) throws Exception;


    /**
     * 修改
     * @author sunx
     * @since 2021-05-25
     * @param stateUpdate: 状态更新
     * @param userShippingAddress:
     * @throws Exception:
     * @return      java.util.List<com.entity.UserShippingAddress>
     */
    Boolean updateEnhance(boolean stateUpdate, UserShippingAddress userShippingAddress) throws Exception;


    /**
     * 删除
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddress:
     * @return      java.util.List<com.entity.UserShippingAddress>
     */
    Boolean removeEnhance(UserShippingAddress userShippingAddress);
}
