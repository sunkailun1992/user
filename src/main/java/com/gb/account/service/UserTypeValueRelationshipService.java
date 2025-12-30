package com.gb.account.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.account.entity.UserTypeValueRelationship;
import com.gb.account.entity.bo.UserTypeValueRelationshipBO;
import com.gb.account.entity.query.UserTypeValueRelationshipQuery;
import com.gb.account.entity.vo.UserTypeValueRelationshipVO;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:49:05
 * @description:	TODO  用户类型值关联，Service服务接口层
 * @source:  	    代码生成器
 */
public interface UserTypeValueRelationshipService extends IService<UserTypeValueRelationship> {


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:05
     * @param   userTypeValueRelationshipQuery:
     * @return  java.util.List<com.entity.UserTypeValueRelationshipVO>
     */
    List<UserTypeValueRelationshipVO> listEnhance(UserTypeValueRelationshipQuery userTypeValueRelationshipQuery);


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:05
     * @param   page:
     * @param   userTypeValueRelationshipQuery:
     * @return  com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    Page<UserTypeValueRelationshipVO> pageEnhance(Page page, UserTypeValueRelationshipQuery userTypeValueRelationshipQuery);


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:05
     * @param   userTypeValueRelationshipQuery:
     * @return  java.util.List<com.entity.UserTypeValueRelationshipVO>
     */
    UserTypeValueRelationshipVO getOneEnhance(UserTypeValueRelationshipQuery userTypeValueRelationshipQuery);


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:49:05
     * @param   userTypeValueRelationshipQuery:
     * @return  java.lang.Integer
     */
    Long countEnhance(UserTypeValueRelationshipQuery userTypeValueRelationshipQuery);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:49:05
     * @param   userTypeValueRelationshipBO:
     * @return  java.lang.String
     */
     String saveEnhance(UserTypeValueRelationshipBO userTypeValueRelationshipBO);


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:49:05
     * @param   userTypeValueRelationshipBO:
     * @return  java.lang.Boolean
     */
    Boolean updateEnhance(UserTypeValueRelationshipBO userTypeValueRelationshipBO);


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:49:05
     * @param   userTypeValueRelationshipBO:
     * @return  java.lang.Boolean
     */
    Boolean removeEnhance(UserTypeValueRelationshipBO userTypeValueRelationshipBO);
}
