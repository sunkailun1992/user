package com.gb.permissions.service.results;

import com.gb.permissions.entity.Role;
import com.gb.permissions.entity.vo.RoleVO;
import com.gb.permissions.entity.bo.RoleBO;
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
 * @since:   	    2021-10-21 01:59:43
 * @description:	TODO  角色表,Service返回实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class RoleServiceResults{


    /**
     * 单条，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:43
     * @param       roleVO 角色表
     * @return      RoleVO
     */
    public RoleVO assignment(RoleVO roleVO) {
        if(roleVO != null){
            return roleVO;
        }else{
            return roleVO;
        }
    }


    /**
     * 分页，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:43
     * @param       roleVOList 角色表
     * @return      Page<RoleVO>
     */
    public Page<RoleVO> assignment(Page<RoleVO> roleVOList) {
        roleVOList.getRecords().forEach(roleVO -> {
        });
        return roleVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:43
     * @param       roleVOList 角色表
     * @return      List<RoleVO>
     */
    public List<RoleVO> assignment(List<RoleVO> roleVOList) {
        roleVOList.forEach(roleVO -> {
        });
        return roleVOList;
    }


    /**
     * DO转化VO
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:43
     * @param       pageDO 角色表
     * @return      com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    public Page<RoleVO> toPageVO(Page<Role> pageDO) {
        Page<RoleVO> pageVO = new Page<RoleVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), RoleVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }


}