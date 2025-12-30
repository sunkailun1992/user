package com.gb.permissions.service.results;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.permissions.entity.System;
import com.gb.permissions.entity.query.RoleQuery;
import com.gb.permissions.entity.vo.RoleVO;
import com.gb.permissions.entity.vo.SystemVO;
import com.gb.permissions.service.RoleService;
import com.gb.utils.GeneralConvertor;
import com.google.common.collect.Lists;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:43
 * @description:	TODO  系统表,Service返回实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class SystemServiceResults{

    private RoleService roleService;

    /**
     * 单条，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:43
     * @param       systemVO 系统表
     * @return      SystemVO
     */
    public SystemVO assignment(boolean isQueryRole, SystemVO systemVO) {
        if(Objects.nonNull(systemVO) && isQueryRole){
            List<String> roleIdList = Lists.newArrayList();
            if(StringUtils.isNotBlank(systemVO.getRoleCode())){
                RoleVO roleVO =  roleService.getOneEnhance(new RoleQuery(){{
                    setSystemId(systemVO.getId());
                    setValue(systemVO.getRoleCode());
                }});
                roleIdList.add(roleVO.getId());
            } else {
                List<RoleVO> roleVOList = roleService.listEnhance(new RoleQuery(){{
                    setSystemId(systemVO.getId());
                }});
                if(CollectionUtils.isNotEmpty(roleVOList)){
                    roleIdList = roleVOList.stream().map(s->s.getId()).collect(Collectors.toList());
                }
            }
            systemVO.setRoleIdList(roleIdList);
        }
        return systemVO;
    }


    /**
     * 分页，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:43
     * @param       systemVOList 系统表
     * @return      Page<SystemVO>
     */
    public Page<SystemVO> assignment(Page<SystemVO> systemVOList) {
        systemVOList.getRecords().forEach(systemVO -> {
        });
        return systemVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:43
     * @param       systemVOList 系统表
     * @return      List<SystemVO>
     */
    public List<SystemVO> assignment(List<SystemVO> systemVOList) {
        systemVOList.forEach(systemVO -> {
        });
        return systemVOList;
    }


    /**
     * DO转化VO
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:43
     * @param       pageDO 系统表
     * @return      com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    public Page<SystemVO> toPageVO(Page<System> pageDO) {
        Page<SystemVO> pageVO = new Page<SystemVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), SystemVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }


}