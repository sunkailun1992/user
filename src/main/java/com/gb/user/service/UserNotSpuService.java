package com.gb.user.service;

import com.gb.user.entity.query.UserNotSpuQuery;
import com.gb.user.entity.vo.UserNotSpuVO;
import com.gb.user.entity.bo.UserNotSpuBO;
import com.gb.user.entity.UserNotSpu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * TODO 企业渠道用户排除产品，Service服务接口层
 * 代码生成器
 *
 * @author 孙凯伦
 * @className UserNotSpuService
 * @time 2023-07-07 04:36:59
 */
public interface UserNotSpuService extends IService<UserNotSpu> {


    /**
     * TODO 分页
     *
     * @param page
     * @param userNotSpuQuery 企业渠道用户排除产品
     * @return Page<UserNotSpuVO>
     * @author 孙凯伦
     * @methodName pageEnhance
     * @time 2023-07-07 04:36:59
     */
    Page<UserNotSpuVO> pageEnhance(Page page, UserNotSpuQuery userNotSpuQuery);


    /**
     * TODO 集合
     *
     * @param userNotSpuQuery 企业渠道用户排除产品
     * @return List<UserNotSpuVO>
     * @author 孙凯伦
     * @methodName listEnhance
     * @time 2023-07-07 04:36:59
     */
    List<UserNotSpuVO> listEnhance(UserNotSpuQuery userNotSpuQuery);


    /**
     * TODO 单条
     *
     * @param userNotSpuQuery 企业渠道用户排除产品
     * @return UserNotSpuVO
     * @author 孙凯伦
     * @methodName getOneEnhance
     * @time 2023-07-07 04:36:59
     */
    UserNotSpuVO getOneEnhance(UserNotSpuQuery userNotSpuQuery);


    /**
     * TODO 总数
     *
     * @param userNotSpuQuery 企业渠道用户排除产品
     * @return Long
     * @author 孙凯伦
     * @methodName countEnhance
     * @time 2023-07-07 04:36:59
     */
    Long countEnhance(UserNotSpuQuery userNotSpuQuery);


    /**
     * TODO 新增
     *
     * @param userNotSpuBO 企业渠道用户排除产品
     * @return String
     * @author 孙凯伦
     * @methodName saveEnhance
     * @time 2023-07-07 04:36:59
     */
    String saveEnhance(UserNotSpuBO userNotSpuBO);


    /**
     * TODO 修改
     *
     * @param userNotSpuBO 企业渠道用户排除产品
     * @return Boolean
     * @author 孙凯伦
     * @methodName updateEnhance
     * @time 2023-07-07 04:36:59
     */
    Boolean updateEnhance(UserNotSpuBO userNotSpuBO);


    /**
     * TODO 删除
     *
     * @param userNotSpuBO 企业渠道用户排除产品
     * @return Boolean
     * @author 孙凯伦
     * @methodName removeEnhance
     * @time 2023-07-07 04:36:59
     */
    Boolean removeEnhance(UserNotSpuBO userNotSpuBO);
}
