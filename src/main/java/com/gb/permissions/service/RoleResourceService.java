package com.gb.permissions.service;

import com.gb.permissions.entity.query.RoleResourceQuery;
import com.gb.permissions.entity.vo.RoleResourceVO;
import com.gb.permissions.entity.bo.RoleResourceBO;
import com.gb.permissions.entity.RoleResource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:42
 * @description:	TODO  角色资源表，Service服务接口层
 * @source:  	    代码生成器
 */
public interface RoleResourceService extends IService<RoleResource> {


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:42
     * @param   roleResourceQuery:
     * @return  java.util.List<com.entity.RoleResourceVO>
     */
    List<RoleResourceVO> listEnhance(RoleResourceQuery roleResourceQuery);


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:42
     * @param   page:
     * @param   roleResourceQuery:
     * @return  com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    Page<RoleResourceVO> pageEnhance(Page page, RoleResourceQuery roleResourceQuery);


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:42
     * @param   roleResourceQuery:
     * @return  java.util.List<com.entity.RoleResourceVO>
     */
    RoleResourceVO getOneEnhance(RoleResourceQuery roleResourceQuery);


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:59:42
     * @param   roleResourceQuery:
     * @return  java.lang.Integer
     */
    Long countEnhance(RoleResourceQuery roleResourceQuery);


    /**
     * 批量新增
     * @param roleId
     * @param resourceList
     * @param api
     * @param httpServletRequest
     * @return
     */
    String saveBatchEnhance(String roleId,String [] resourceList, Boolean api, HttpServletRequest httpServletRequest);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:59:42
     * @param   roleResourceBO:
     * @return  java.lang.String
     */
     String saveEnhance(RoleResourceBO roleResourceBO);


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:59:42
     * @param   roleResourceBO:
     * @return  java.lang.Boolean
     */
    Boolean updateEnhance(RoleResourceBO roleResourceBO);


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:59:42
     * @param   roleResourceBO:
     * @return  java.lang.Boolean
     */
    Boolean removeEnhance(RoleResourceBO roleResourceBO);
}
