package com.gb.account.service.results;

import com.gb.account.entity.UserType;
import com.gb.account.entity.vo.UserTypeVO;
import com.gb.account.entity.bo.UserTypeBO;
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
 * @since:   	    2021-10-21 01:49:03
 * @description:	TODO  用户类型表,Service返回实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserTypeServiceResults{


    /**
     * 单条，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:49:03
     * @param       userTypeVO 用户类型表
     * @return      UserTypeVO
     */
    public UserTypeVO assignment(UserTypeVO userTypeVO) {
        if(userTypeVO != null){
            return userTypeVO;
        }else{
            return userTypeVO;
        }
    }


    /**
     * 分页，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:49:03
     * @param       userTypeVOList 用户类型表
     * @return      Page<UserTypeVO>
     */
    public Page<UserTypeVO> assignment(Page<UserTypeVO> userTypeVOList) {
        userTypeVOList.getRecords().forEach(userTypeVO -> {
        });
        return userTypeVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:49:03
     * @param       userTypeVOList 用户类型表
     * @return      List<UserTypeVO>
     */
    public List<UserTypeVO> assignment(List<UserTypeVO> userTypeVOList) {
        userTypeVOList.forEach(userTypeVO -> {
        });
        return userTypeVOList;
    }


    /**
     * DO转化VO
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:49:03
     * @param       pageDO 用户类型表
     * @return      com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    public Page<UserTypeVO> toPageVO(Page<UserType> pageDO) {
        Page<UserTypeVO> pageVO = new Page<UserTypeVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), UserTypeVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }


}