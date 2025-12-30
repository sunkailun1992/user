package com.gb.permissions.service;

import com.gb.permissions.entity.query.GroupRoleQuery;
import com.gb.permissions.entity.vo.GroupRoleVO;
import com.gb.permissions.entity.bo.GroupRoleBO;
import com.gb.permissions.entity.GroupRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:44
 * @description:	TODO  角色用户组，Service服务接口层
 * @source:  	    代码生成器
 */
public interface GroupRoleService extends IService<GroupRole> {


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupRoleQuery:
     * @return  java.util.List<com.entity.GroupRoleVO>
     */
    List<GroupRoleVO> listEnhance(GroupRoleQuery groupRoleQuery);


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   page:
     * @param   groupRoleQuery:
     * @return  com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    Page<GroupRoleVO> pageEnhance(Page page, GroupRoleQuery groupRoleQuery);


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupRoleQuery:
     * @return  java.util.List<com.entity.GroupRoleVO>
     */
    GroupRoleVO getOneEnhance(GroupRoleQuery groupRoleQuery);


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupRoleQuery:
     * @return  java.lang.Integer
     */
    Long countEnhance(GroupRoleQuery groupRoleQuery);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupRoleBO:
     * @return  java.lang.String
     */
     String saveEnhance(GroupRoleBO groupRoleBO);


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupRoleBO:
     * @return  java.lang.Boolean
     */
    Boolean updateEnhance(GroupRoleBO groupRoleBO);


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupRoleBO:
     * @return  java.lang.Boolean
     */
    Boolean removeEnhance(GroupRoleBO groupRoleBO);
}
