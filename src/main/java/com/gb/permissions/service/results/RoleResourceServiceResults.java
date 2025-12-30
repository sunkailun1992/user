package com.gb.permissions.service.results;

import com.gb.permissions.entity.RoleResource;
import com.gb.permissions.entity.vo.RoleResourceVO;
import com.gb.permissions.entity.bo.RoleResourceBO;
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
 * @since:   	    2021-10-21 01:59:42
 * @description:	TODO  角色资源表,Service返回实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class RoleResourceServiceResults{


    /**
     * 单条，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:42
     * @param       roleResourceVO 角色资源表
     * @return      RoleResourceVO
     */
    public RoleResourceVO assignment(RoleResourceVO roleResourceVO) {
        if(roleResourceVO != null){
            return roleResourceVO;
        }else{
            return roleResourceVO;
        }
    }


    /**
     * 分页，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:42
     * @param       roleResourceVOList 角色资源表
     * @return      Page<RoleResourceVO>
     */
    public Page<RoleResourceVO> assignment(Page<RoleResourceVO> roleResourceVOList) {
        roleResourceVOList.getRecords().forEach(roleResourceVO -> {
        });
        return roleResourceVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:42
     * @param       roleResourceVOList 角色资源表
     * @return      List<RoleResourceVO>
     */
    public List<RoleResourceVO> assignment(List<RoleResourceVO> roleResourceVOList) {
        roleResourceVOList.forEach(roleResourceVO -> {
        });
        return roleResourceVOList;
    }


    /**
     * DO转化VO
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:42
     * @param       pageDO 角色资源表
     * @return      com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    public Page<RoleResourceVO> toPageVO(Page<RoleResource> pageDO) {
        Page<RoleResourceVO> pageVO = new Page<RoleResourceVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), RoleResourceVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }


}