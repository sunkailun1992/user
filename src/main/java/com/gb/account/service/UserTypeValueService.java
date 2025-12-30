package com.gb.account.service;

import com.gb.account.entity.query.UserTypeValueQuery;
import com.gb.account.entity.vo.UserTypeValueVO;
import com.gb.account.entity.bo.UserTypeValueBO;
import com.gb.account.entity.UserTypeValue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:49:04
 * @description:	TODO  用户类型值表，Service服务接口层
 * @source:  	    代码生成器
 */
public interface UserTypeValueService extends IService<UserTypeValue> {


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:04
     * @param   userTypeValueQuery:
     * @return  java.util.List<com.entity.UserTypeValueVO>
     */
    List<UserTypeValueVO> listEnhance(UserTypeValueQuery userTypeValueQuery);


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:04
     * @param   page:
     * @param   userTypeValueQuery:
     * @return  com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    Page<UserTypeValueVO> pageEnhance(Page page, UserTypeValueQuery userTypeValueQuery);


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:04
     * @param   userTypeValueQuery:
     * @return  java.util.List<com.entity.UserTypeValueVO>
     */
    UserTypeValueVO getOneEnhance(UserTypeValueQuery userTypeValueQuery);


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:49:04
     * @param   userTypeValueQuery:
     * @return  java.lang.Integer
     */
    Long countEnhance(UserTypeValueQuery userTypeValueQuery);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:49:04
     * @param   userTypeValueBO:
     * @return  java.lang.String
     */
     String saveEnhance(UserTypeValueBO userTypeValueBO);


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:49:04
     * @param   userTypeValueBO:
     * @return  java.lang.Boolean
     */
    Boolean updateEnhance(UserTypeValueBO userTypeValueBO);


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:49:04
     * @param   userTypeValueBO:
     * @return  java.lang.Boolean
     */
    Boolean removeEnhance(UserTypeValueBO userTypeValueBO);
}
