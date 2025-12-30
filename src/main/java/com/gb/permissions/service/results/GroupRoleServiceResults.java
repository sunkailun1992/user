package com.gb.permissions.service.results;

import com.gb.permissions.entity.GroupRole;
import com.gb.permissions.entity.vo.GroupRoleVO;
import com.gb.permissions.entity.bo.GroupRoleBO;
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
 * @since:   	    2021-10-21 01:59:44
 * @description:	TODO  角色用户组,Service返回实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class GroupRoleServiceResults{


    /**
     * 单条，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:44
     * @param       groupRoleVO 角色用户组
     * @return      GroupRoleVO
     */
    public GroupRoleVO assignment(GroupRoleVO groupRoleVO) {
        if(groupRoleVO != null){
            return groupRoleVO;
        }else{
            return groupRoleVO;
        }
    }


    /**
     * 分页，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:44
     * @param       groupRoleVOList 角色用户组
     * @return      Page<GroupRoleVO>
     */
    public Page<GroupRoleVO> assignment(Page<GroupRoleVO> groupRoleVOList) {
        groupRoleVOList.getRecords().forEach(groupRoleVO -> {
        });
        return groupRoleVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:44
     * @param       groupRoleVOList 角色用户组
     * @return      List<GroupRoleVO>
     */
    public List<GroupRoleVO> assignment(List<GroupRoleVO> groupRoleVOList) {
        groupRoleVOList.forEach(groupRoleVO -> {
        });
        return groupRoleVOList;
    }


    /**
     * DO转化VO
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:59:44
     * @param       pageDO 角色用户组
     * @return      com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    public Page<GroupRoleVO> toPageVO(Page<GroupRole> pageDO) {
        Page<GroupRoleVO> pageVO = new Page<GroupRoleVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), GroupRoleVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }


}