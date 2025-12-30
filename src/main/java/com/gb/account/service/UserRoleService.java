package com.gb.account.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.account.entity.UserRole;
import com.gb.account.entity.bo.UserRoleBO;
import com.gb.account.entity.query.UserRoleQuery;
import com.gb.account.entity.vo.UserRoleVO;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:37
 * @description:	TODO  用户角色表，Service服务接口层
 * @source:  	    代码生成器
 */
public interface UserRoleService extends IService<UserRole> {


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userRoleQuery:
     * @return  java.util.List<com.entity.UserRoleVO>
     */
    List<UserRoleVO> listEnhance(UserRoleQuery userRoleQuery);


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   page:
     * @param   userRoleQuery:
     * @return  com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    Page<UserRoleVO> pageEnhance(Page page, UserRoleQuery userRoleQuery);


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userRoleQuery:
     * @return  java.util.List<com.entity.UserRoleVO>
     */
    UserRoleVO getOneEnhance(UserRoleQuery userRoleQuery);


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userRoleQuery:
     * @return  java.lang.Integer
     */
    Long countEnhance(UserRoleQuery userRoleQuery);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userRoleBO:
     * @return  java.lang.String
     */
     String saveEnhance(UserRoleBO userRoleBO);


    /**
     * 批量新增
     * @author  孙凯伦
     * @since   2021-10-21 01:50:39
     * @param   userRoleBoList:
     * @return  java.lang.Integer
     */
    Integer saveBatchEnhance(List<UserRoleBO> userRoleBoList);


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userRoleBO:
     * @return  java.lang.Boolean
     */
    Boolean updateEnhance(UserRoleBO userRoleBO);


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userRoleBO:
     * @return  java.lang.Boolean
     */
    Boolean removeEnhance(UserRoleBO userRoleBO);
}
