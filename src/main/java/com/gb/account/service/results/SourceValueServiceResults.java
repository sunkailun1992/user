package com.gb.account.service.results;

import com.gb.account.entity.SourceValue;
import com.gb.account.entity.vo.SourceValueVO;
import com.gb.account.entity.bo.SourceValueBO;
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
 * @description:	TODO  来源值,Service返回实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class SourceValueServiceResults{


    /**
     * 单条，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-11-03 03:57:55
     * @param       sourceValueVO 来源值
     * @return      SourceValueVO
     */
    public SourceValueVO assignment(SourceValueVO sourceValueVO) {
        if(sourceValueVO != null){
            return sourceValueVO;
        }else{
            return sourceValueVO;
        }
    }


    /**
     * 分页，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-11-03 03:57:55
     * @param       sourceValueVOList 来源值
     * @return      Page<SourceValueVO>
     */
    public Page<SourceValueVO> assignment(Page<SourceValueVO> sourceValueVOList) {
        sourceValueVOList.getRecords().forEach(sourceValueVO -> {
        });
        return sourceValueVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-11-03 03:57:55
     * @param       sourceValueVOList 来源值
     * @return      List<SourceValueVO>
     */
    public List<SourceValueVO> assignment(List<SourceValueVO> sourceValueVOList) {
        sourceValueVOList.forEach(sourceValueVO -> {
        });
        return sourceValueVOList;
    }


    /**
     * DO转化VO
     *
     * @author     	孙凯伦
     * @since   	2021-11-03 03:57:55
     * @param       pageDO 来源值
     * @return      com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    public Page<SourceValueVO> toPageVO(Page<SourceValue> pageDO) {
        Page<SourceValueVO> pageVO = new Page<SourceValueVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), SourceValueVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }


}