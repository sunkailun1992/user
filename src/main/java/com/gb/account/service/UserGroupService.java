package com.gb.account.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.account.entity.UserGroup;
import com.gb.account.entity.bo.UserGroupBO;
import com.gb.account.entity.query.UserGroupQuery;
import com.gb.account.entity.vo.UserGroupVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:39
 * @description:	TODO  用户组，Service服务接口层
 * @source:  	    代码生成器
 */
public interface UserGroupService extends IService<UserGroup> {


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:39
     * @param   userGroupQuery:
     * @return  java.util.List<com.entity.UserGroupVO>
     */
    List<UserGroupVO> listEnhance(UserGroupQuery userGroupQuery);


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:39
     * @param   page:
     * @param   userGroupQuery:
     * @return  com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    Page<UserGroupVO> pageEnhance(Page page, UserGroupQuery userGroupQuery);


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:39
     * @param   userGroupQuery:
     * @return  java.util.List<com.entity.UserGroupVO>
     */
    UserGroupVO getOneEnhance(UserGroupQuery userGroupQuery);


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:50:39
     * @param   userGroupQuery:
     * @return  java.lang.Integer
     */
    Long countEnhance(UserGroupQuery userGroupQuery);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:50:39
     * @param   userGroupBO:
     * @return  java.lang.String
     */
     String saveEnhance(UserGroupBO userGroupBO);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:50:39
     * @param userId:
     * @param groupId:
     * @param httpServletRequest:
     * @return  java.lang.String
     */
    void saveEnhanceBatch(String[] userId,String groupId, HttpServletRequest httpServletRequest);


    /**
     * 批量新增
     * @author  孙凯伦
     * @since   2021-10-21 01:50:39
     * @param   userGroupBOList:
     * @return  java.lang.Integer
     */
     Integer saveBatchEnhance(List<UserGroupBO> userGroupBOList);


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:50:39
     * @param   userGroupBO:
     * @return  java.lang.Boolean
     */
    Boolean updateEnhance(UserGroupBO userGroupBO);


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:50:39
     * @param   userGroupBO:
     * @return  java.lang.Boolean
     */
    Boolean removeEnhance(UserGroupBO userGroupBO);
}
