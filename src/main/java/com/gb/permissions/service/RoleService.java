package com.gb.permissions.service;

import com.gb.permissions.entity.query.RoleQuery;
import com.gb.permissions.entity.vo.RoleVO;
import com.gb.permissions.entity.bo.RoleBO;
import com.gb.permissions.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:43
 * @description:	TODO  角色表，Service服务接口层
 * @source:  	    代码生成器
 */
public interface RoleService extends IService<Role> {


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:43
     * @param   roleQuery:
     * @return  java.util.List<com.entity.RoleVO>
     */
    List<RoleVO> listEnhance(RoleQuery roleQuery);


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:43
     * @param   page:
     * @param   roleQuery:
     * @return  com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    Page<RoleVO> pageEnhance(Page page, RoleQuery roleQuery);


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:43
     * @param   roleQuery:
     * @return  java.util.List<com.entity.RoleVO>
     */
    RoleVO getOneEnhance(RoleQuery roleQuery);


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:59:43
     * @param   roleQuery:
     * @return  java.lang.Integer
     */
    Long countEnhance(RoleQuery roleQuery);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:59:43
     * @param   roleBO:
     * @return  java.lang.String
     */
     String saveEnhance(RoleBO roleBO);


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:59:43
     * @param   roleBO:
     * @return  java.lang.Boolean
     */
    Boolean updateEnhance(RoleBO roleBO);


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:59:43
     * @param   roleBO:
     * @return  java.lang.Boolean
     */
    Boolean removeEnhance(RoleBO roleBO);
}
