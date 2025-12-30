package com.gb.account.service.results;

import com.gb.account.entity.Source;
import com.gb.account.entity.vo.SourceVO;
import com.gb.account.entity.bo.SourceBO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.gb.utils.GeneralConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-11-03 03:57:55
 * @description:	TODO  来源,Service返回实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class SourceServiceResults{


    /**
     * 单条，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-11-03 03:57:55
     * @param       sourceVO 来源
     * @return      SourceVO
     */
    public SourceVO assignment(SourceVO sourceVO) {
        if(sourceVO != null){
            return sourceVO;
        }else{
            return sourceVO;
        }
    }


    /**
     * 分页，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-11-03 03:57:55
     * @param       sourceVOList 来源
     * @return      Page<SourceVO>
     */
    public Page<SourceVO> assignment(Page<SourceVO> sourceVOList) {
        sourceVOList.getRecords().forEach(sourceVO -> {
        });
        return sourceVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-11-03 03:57:55
     * @param       sourceVOList 来源
     * @return      List<SourceVO>
     */
    public List<SourceVO> assignment(List<SourceVO> sourceVOList) {
        sourceVOList.forEach(sourceVO -> {
        });
        return sourceVOList;
    }


    /**
     * DO转化VO
     *
     * @author     	孙凯伦
     * @since   	2021-11-03 03:57:55
     * @param       pageDO 来源
     * @return      com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    public Page<SourceVO> toPageVO(Page<Source> pageDO) {
        Page<SourceVO> pageVO = new Page<SourceVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), SourceVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }


}