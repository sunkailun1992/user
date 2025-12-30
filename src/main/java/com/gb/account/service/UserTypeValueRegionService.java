package com.gb.account.service;

import com.gb.account.entity.query.UserTypeValueRegionQuery;
import com.gb.account.entity.vo.UserTypeValueRegionVO;
import com.gb.account.entity.bo.UserTypeValueRegionBO;
import com.gb.account.entity.UserTypeValueRegion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * TODO 用户类型值地区，Service服务接口层
 * 代码生成器
 *
 * @author lijh
 * @className UserTypeValueRegionService
 * @time 2022-07-12 11:45:19
 */
public interface UserTypeValueRegionService extends IService<UserTypeValueRegion> {


    /**
     * TODO 分页
     *
     * @param page
     * @param userTypeValueRegionQuery 用户类型值地区
     * @return Page<UserTypeValueRegionVO>
     * @author lijh
     * @methodName pageEnhance
     * @time 2022-07-12 11:45:19
     */
    Page<UserTypeValueRegionVO> pageEnhance(Page page, UserTypeValueRegionQuery userTypeValueRegionQuery);


    /**
     * TODO 集合
     *
     * @param userTypeValueRegionQuery 用户类型值地区
     * @return List<UserTypeValueRegionVO>
     * @author lijh
     * @methodName listEnhance
     * @time 2022-07-12 11:45:19
     */
    List<UserTypeValueRegionVO> listEnhance(UserTypeValueRegionQuery userTypeValueRegionQuery);


    /**
     * TODO 单条
     *
     * @param userTypeValueRegionQuery 用户类型值地区
     * @return UserTypeValueRegionVO
     * @author lijh
     * @methodName getOneEnhance
     * @time 2022-07-12 11:45:19
     */
    UserTypeValueRegionVO getOneEnhance(UserTypeValueRegionQuery userTypeValueRegionQuery);


    /**
     * TODO 总数
     *
     * @param userTypeValueRegionQuery 用户类型值地区
     * @return Long
     * @author lijh
     * @methodName countEnhance
     * @time 2022-07-12 11:45:19
     */
    Long countEnhance(UserTypeValueRegionQuery userTypeValueRegionQuery);


    /**
     * TODO 新增
     *
     * @param userTypeValueRegionBO 用户类型值地区
     * @return String
     * @author lijh
     * @methodName saveEnhance
     * @time 2022-07-12 11:45:19
     */
    String saveEnhance(UserTypeValueRegionBO userTypeValueRegionBO);


    /**
     * TODO 修改
     *
     * @param userTypeValueRegionBO 用户类型值地区
     * @return Boolean
     * @author lijh
     * @methodName updateEnhance
     * @time 2022-07-12 11:45:19
     */
    Boolean updateEnhance(UserTypeValueRegionBO userTypeValueRegionBO);


    /**
     * TODO 删除
     *
     * @param userTypeValueRegionBO 用户类型值地区
     * @return Boolean
     * @author lijh
     * @methodName removeEnhance
     * @time 2022-07-12 11:45:19
     */
    Boolean removeEnhance(UserTypeValueRegionBO userTypeValueRegionBO);
}
