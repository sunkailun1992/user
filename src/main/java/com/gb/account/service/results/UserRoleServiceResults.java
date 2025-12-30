package com.gb.account.service.results;

import com.gb.account.entity.UserRole;
import com.gb.account.entity.vo.UserRoleVO;
import com.gb.account.entity.bo.UserRoleBO;
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
 * @since:   	    2021-10-21 01:50:37
 * @description:	TODO  用户角色表,Service返回实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserRoleServiceResults{


    /**
     * 单条，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:37
     * @param       userRoleVO 用户角色表
     * @return      UserRoleVO
     */
    public UserRoleVO assignment(UserRoleVO userRoleVO) {
        if(userRoleVO != null){
            return userRoleVO;
        }else{
            return userRoleVO;
        }
    }


    /**
     * 分页，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:37
     * @param       userRoleVOList 用户角色表
     * @return      Page<UserRoleVO>
     */
    public Page<UserRoleVO> assignment(Page<UserRoleVO> userRoleVOList) {
        userRoleVOList.getRecords().forEach(userRoleVO -> {
        });
        return userRoleVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:37
     * @param       userRoleVOList 用户角色表
     * @return      List<UserRoleVO>
     */
    public List<UserRoleVO> assignment(List<UserRoleVO> userRoleVOList) {
        userRoleVOList.forEach(userRoleVO -> {
        });
        return userRoleVOList;
    }


    /**
     * DO转化VO
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:37
     * @param       pageDO 用户角色表
     * @return      com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    public Page<UserRoleVO> toPageVO(Page<UserRole> pageDO) {
        Page<UserRoleVO> pageVO = new Page<UserRoleVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), UserRoleVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }


}