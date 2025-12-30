package com.gb.permissions.service;

import com.gb.permissions.entity.query.SystemQuery;
import com.gb.permissions.entity.vo.SystemVO;
import com.gb.permissions.entity.bo.SystemBO;
import com.gb.permissions.entity.System;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:43
 * @description:	TODO  系统表，Service服务接口层
 * @source:  	    代码生成器
 */
public interface SystemService extends IService<System> {


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:43
     * @param   systemQuery:
     * @return  java.util.List<com.entity.SystemVO>
     */
    List<SystemVO> listEnhance(SystemQuery systemQuery);


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:43
     * @param   page:
     * @param   systemQuery:
     * @return  com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    Page<SystemVO> pageEnhance(Page page, SystemQuery systemQuery);


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:59:43
     * @param   systemQuery:
     * @return  java.util.List<com.entity.SystemVO>
     */
    SystemVO getOneEnhance(SystemQuery systemQuery);


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:59:43
     * @param   systemQuery:
     * @return  java.lang.Integer
     */
    Long countEnhance(SystemQuery systemQuery);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:59:43
     * @param   systemBO:
     * @return  java.lang.String
     */
     String saveEnhance(SystemBO systemBO);


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:59:43
     * @param   systemBO:
     * @return  java.lang.Boolean
     */
    Boolean updateEnhance(SystemBO systemBO);


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:59:43
     * @param   systemBO:
     * @return  java.lang.Boolean
     */
    Boolean removeEnhance(SystemBO systemBO);
}
