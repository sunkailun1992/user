package com.gb.account.service;

import com.gb.account.entity.query.SourceValueQuery;
import com.gb.account.entity.vo.SourceValueVO;
import com.gb.account.entity.bo.SourceValueBO;
import com.gb.account.entity.SourceValue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-03 03:57:55
 * @description:	TODO  来源值，Service服务接口层
 * @source:  	    代码生成器
 */
public interface SourceValueService extends IService<SourceValue> {


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceValueQuery:
     * @return  java.util.List<com.entity.SourceValueVO>
     */
    List<SourceValueVO> listEnhance(SourceValueQuery sourceValueQuery);


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   page:
     * @param   sourceValueQuery:
     * @return  com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    Page<SourceValueVO> pageEnhance(Page page, SourceValueQuery sourceValueQuery);


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceValueQuery:
     * @return  java.util.List<com.entity.SourceValueVO>
     */
    SourceValueVO getOneEnhance(SourceValueQuery sourceValueQuery);


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceValueQuery:
     * @return  java.lang.Integer
     */
    Long countEnhance(SourceValueQuery sourceValueQuery);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceValueBO:
     * @return  java.lang.String
     */
     String saveEnhance(SourceValueBO sourceValueBO);


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceValueBO:
     * @return  java.lang.Boolean
     */
    Boolean updateEnhance(SourceValueBO sourceValueBO);


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceValueBO:
     * @return  java.lang.Boolean
     */
    Boolean removeEnhance(SourceValueBO sourceValueBO);
}
