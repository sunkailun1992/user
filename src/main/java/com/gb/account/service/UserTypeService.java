package com.gb.account.service;

import com.gb.account.entity.query.UserTypeQuery;
import com.gb.account.entity.vo.UserTypeVO;
import com.gb.account.entity.bo.UserTypeBO;
import com.gb.account.entity.UserType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:49:03
 * @description:	TODO  用户类型表，Service服务接口层
 * @source:  	    代码生成器
 */
public interface UserTypeService extends IService<UserType> {


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:03
     * @param   userTypeQuery:
     * @return  java.util.List<com.entity.UserTypeVO>
     */
    List<UserTypeVO> listEnhance(UserTypeQuery userTypeQuery);


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:03
     * @param   page:
     * @param   userTypeQuery:
     * @return  com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    Page<UserTypeVO> pageEnhance(Page page, UserTypeQuery userTypeQuery);


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:03
     * @param   userTypeQuery:
     * @return  java.util.List<com.entity.UserTypeVO>
     */
    UserTypeVO getOneEnhance(UserTypeQuery userTypeQuery);


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:49:03
     * @param   userTypeQuery:
     * @return  java.lang.Integer
     */
    Long countEnhance(UserTypeQuery userTypeQuery);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:49:03
     * @param   userTypeBO:
     * @return  java.lang.String
     */
     String saveEnhance(UserTypeBO userTypeBO);


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:49:03
     * @param   userTypeBO:
     * @return  java.lang.Boolean
     */
    Boolean updateEnhance(UserTypeBO userTypeBO);


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:49:03
     * @param   userTypeBO:
     * @return  java.lang.Boolean
     */
    Boolean removeEnhance(UserTypeBO userTypeBO);
}
