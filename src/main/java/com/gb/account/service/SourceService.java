package com.gb.account.service;

import com.gb.account.entity.query.SourceQuery;
import com.gb.account.entity.vo.SourceVO;
import com.gb.account.entity.bo.SourceBO;
import com.gb.account.entity.Source;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-03 03:57:55
 * @description:	TODO  来源，Service服务接口层
 * @source:  	    代码生成器
 */
public interface SourceService extends IService<Source> {


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceQuery:
     * @return  java.util.List<com.entity.SourceVO>
     */
    List<SourceVO> listEnhance(SourceQuery sourceQuery);


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   page:
     * @param   sourceQuery:
     * @return  com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    Page<SourceVO> pageEnhance(Page page, SourceQuery sourceQuery);


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceQuery:
     * @return  java.util.List<com.entity.SourceVO>
     */
    SourceVO getOneEnhance(SourceQuery sourceQuery);


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceQuery:
     * @return  java.lang.Integer
     */
    Long countEnhance(SourceQuery sourceQuery);


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceBO:
     * @return  java.lang.String
     */
     String saveEnhance(SourceBO sourceBO);


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceBO:
     * @return  java.lang.Boolean
     */
    Boolean updateEnhance(SourceBO sourceBO);


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-11-03 03:57:55
     * @param   sourceBO:
     * @return  java.lang.Boolean
     */
    Boolean removeEnhance(SourceBO sourceBO);
}
