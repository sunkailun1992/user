package com.gb.account.service;

import com.gb.account.entity.query.UserOauthsQuery;
import com.gb.account.entity.vo.UserOauthsVO;
import com.gb.account.entity.bo.UserOauthsBO;
import com.gb.account.entity.UserOauths;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:37
 * @description:	TODO  用户授权表，Service服务接口层
 * @source:  	    代码生成器
 */
public interface UserOauthsService extends IService<UserOauths> {


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userOauthsQuery:
     * @return  java.util.List<com.entity.UserOauthsVO>
     */
    List<UserOauthsVO> listEnhance(UserOauthsQuery userOauthsQuery);


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   page:
     * @param   userOauthsQuery:
     * @return  com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    Page<UserOauthsVO> pageEnhance(Page page, UserOauthsQuery userOauthsQuery);


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userOauthsQuery:
     * @return  java.util.List<com.entity.UserOauthsVO>
     */
    UserOauthsVO getOneEnhance(UserOauthsQuery userOauthsQuery);


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userOauthsQuery:
     * @return  java.lang.Integer
     */
    Long countEnhance(UserOauthsQuery userOauthsQuery);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userOauthsBO:
     * @return  java.lang.String
     */
     String saveEnhance(UserOauthsBO userOauthsBO);


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userOauthsBO:
     * @return  java.lang.Boolean
     */
    Boolean updateEnhance(UserOauthsBO userOauthsBO);


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userOauthsBO:
     * @return  java.lang.Boolean
     */
    Boolean removeEnhance(UserOauthsBO userOauthsBO);
}
