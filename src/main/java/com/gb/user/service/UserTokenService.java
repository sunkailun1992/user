package com.gb.user.service;

import com.gb.user.entity.query.UserTokenQuery;
import com.gb.user.entity.vo.UserTokenVO;
import com.gb.user.entity.bo.UserTokenBO;
import com.gb.user.entity.UserToken;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * TODO 用户设备信息表，Service服务接口层
 * 代码生成器
 *
 * @author wgs
 * @className UserTokenService
 * @time 2022-01-20 03:40:09
 */
public interface UserTokenService extends IService<UserToken> {


    /**
     * TODO 分页
     *
     * @param page
     * @param userTokenQuery 用户设备信息表
     * @return Page<UserTokenVO>
     * @author {author}
     * @methodName pageEnhance
     * @time 2022-01-20 03:40:09
     */
    Page<UserTokenVO> pageEnhance(Page page, UserTokenQuery userTokenQuery);


    /**
     * TODO 集合
     *
     * @param userTokenQuery 用户设备信息表
     * @return List<UserTokenVO>
     * @author wgs
     * @methodName listEnhance
     * @time 2022-01-20 03:40:09
     */
    List<UserTokenVO> listEnhance(UserTokenQuery userTokenQuery);


    /**
     * TODO 单条
     *
     * @param userTokenQuery 用户设备信息表
     * @return UserTokenVO
     * @author wgs
     * @methodName getOneEnhance
     * @time 2022-01-20 03:40:09
     */
    UserTokenVO getOneEnhance(UserTokenQuery userTokenQuery);


    /**
     * TODO 总数
     *
     * @param userTokenQuery 用户设备信息表
     * @return Integer
     * @author wgs
     * @methodName countEnhance
     * @time 2022-01-20 03:40:09
     */
    Long countEnhance(UserTokenQuery userTokenQuery);


    /**
     * TODO 新增
     *
     * @param userTokenBO 用户设备信息表
     * @return String
     * @author wgs
     * @methodName saveEnhance
     * @time 2022-01-20 03:40:09
     */
    String saveEnhance(UserTokenBO userTokenBO);


    /**
     * TODO 修改
     *
     * @param userTokenBO 用户设备信息表
     * @return Boolean
     * @author wgs
     * @methodName updateEnhance
     * @time 2022-01-20 03:40:09
     */
    Boolean updateEnhance(UserTokenBO userTokenBO);


    /**
     * TODO 删除
     *
     * @param userTokenBO 用户设备信息表
     * @return Boolean
     * @author wgs
     * @methodName removeEnhance
     * @time 2022-01-20 03:40:09
     */
    Boolean removeEnhance(UserTokenBO userTokenBO);

    /**
     * 绑定设备
     * @param userTokenBO
     * @return
     */
    String bindToken(UserTokenBO userTokenBO);
}
