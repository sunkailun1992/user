package com.gb.account.service.results;

import com.gb.account.entity.UserGroup;
import com.gb.account.entity.vo.UserGroupVO;
import com.gb.account.entity.bo.UserGroupBO;
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
 * @since:   	    2021-10-21 01:50:39
 * @description:	TODO  用户组,Service返回实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserGroupServiceResults{


    /**
     * 单条，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:39
     * @param       userGroupVO 用户组
     * @return      UserGroupVO
     */
    public UserGroupVO assignment(UserGroupVO userGroupVO) {
        if(userGroupVO != null){
            return userGroupVO;
        }else{
            return userGroupVO;
        }
    }


    /**
     * 分页，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:39
     * @param       userGroupVOList 用户组
     * @return      Page<UserGroupVO>
     */
    public Page<UserGroupVO> assignment(Page<UserGroupVO> userGroupVOList) {
        userGroupVOList.getRecords().forEach(userGroupVO -> {
        });
        return userGroupVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:39
     * @param       userGroupVOList 用户组
     * @return      List<UserGroupVO>
     */
    public List<UserGroupVO> assignment(List<UserGroupVO> userGroupVOList) {
        userGroupVOList.forEach(userGroupVO -> {
        });
        return userGroupVOList;
    }


    /**
     * DO转化VO
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:39
     * @param       pageDO 用户组
     * @return      com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    public Page<UserGroupVO> toPageVO(Page<UserGroup> pageDO) {
        Page<UserGroupVO> pageVO = new Page<UserGroupVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), UserGroupVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }


}