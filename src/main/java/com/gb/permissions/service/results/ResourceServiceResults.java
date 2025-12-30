package com.gb.permissions.service.results;

import com.gb.permissions.entity.Resource;
import com.gb.permissions.entity.vo.ResourceVO;
import com.gb.permissions.entity.bo.ResourceBO;
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
 * @since:   	    2021-10-21 01:59:45
 * @description:	TODO  资源表,Service返回实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class ResourceServiceResults{


    /**
     * 单条，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:45
     * @param       resourceVO 资源表
     * @return      ResourceVO
     */
    public ResourceVO assignment(ResourceVO resourceVO) {
        if(resourceVO != null){
            return resourceVO;
        }else{
            return resourceVO;
        }
    }


    /**
     * 分页，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:45
     * @param       resourceVOList 资源表
     * @return      Page<ResourceVO>
     */
    public Page<ResourceVO> assignment(Page<ResourceVO> resourceVOList) {
        resourceVOList.getRecords().forEach(resourceVO -> {
        });
        return resourceVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:45
     * @param       resourceVOList 资源表
     * @return      List<ResourceVO>
     */
    public List<ResourceVO> assignment(List<ResourceVO> resourceVOList) {
        resourceVOList.forEach(resourceVO -> {
        });
        return resourceVOList;
    }


    /**
     * DO转化VO
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:45
     * @param       pageDO 资源表
     * @return      com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    public Page<ResourceVO> toPageVO(Page<Resource> pageDO) {
        Page<ResourceVO> pageVO = new Page<ResourceVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), ResourceVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }


}