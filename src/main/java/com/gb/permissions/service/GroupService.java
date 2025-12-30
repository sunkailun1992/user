package com.gb.permissions.service;

import com.gb.permissions.entity.query.GroupQuery;
import com.gb.permissions.entity.vo.GroupVO;
import com.gb.permissions.entity.bo.GroupBO;
import com.gb.permissions.entity.Group;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:44
 * @description:	TODO  组，Service服务接口层
 * @source:  	    代码生成器
 */
public interface GroupService extends IService<Group> {


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupQuery:
     * @return  java.util.List<com.entity.GroupVO>
     */
    List<GroupVO> listEnhance(GroupQuery groupQuery);


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   page:
     * @param   groupQuery:
     * @return  com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    Page<GroupVO> pageEnhance(Page page, GroupQuery groupQuery);


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupQuery:
     * @return  java.util.List<com.entity.GroupVO>
     */
    GroupVO getOneEnhance(GroupQuery groupQuery);


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupQuery:
     * @return  java.lang.Integer
     */
    Long countEnhance(GroupQuery groupQuery);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupBO:
     * @return  java.lang.String
     */
     String saveEnhance(GroupBO groupBO);


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupBO:
     * @return  java.lang.Boolean
     */
    Boolean updateEnhance(GroupBO groupBO);


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:59:44
     * @param   groupBO:
     * @return  java.lang.Boolean
     */
    Boolean removeEnhance(GroupBO groupBO);
}
