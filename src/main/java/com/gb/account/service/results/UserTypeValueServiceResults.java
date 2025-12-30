package com.gb.account.service.results;

import com.gb.account.entity.UserTypeValue;
import com.gb.account.entity.vo.UserTypeValueVO;
import com.gb.account.entity.bo.UserTypeValueBO;
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
 * @since:   	    2021-10-21 01:49:04
 * @description:	TODO  用户类型值表,Service返回实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserTypeValueServiceResults{


    /**
     * 单条，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:49:04
     * @param       userTypeValueVO 用户类型值表
     * @return      UserTypeValueVO
     */
    public UserTypeValueVO assignment(UserTypeValueVO userTypeValueVO) {
        if(userTypeValueVO != null){
            return userTypeValueVO;
        }else{
            return userTypeValueVO;
        }
    }


    /**
     * 分页，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:49:04
     * @param       userTypeValueVOList 用户类型值表
     * @return      Page<UserTypeValueVO>
     */
    public Page<UserTypeValueVO> assignment(Page<UserTypeValueVO> userTypeValueVOList) {
        userTypeValueVOList.getRecords().forEach(userTypeValueVO -> {
        });
        return userTypeValueVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:49:04
     * @param       userTypeValueVOList 用户类型值表
     * @return      List<UserTypeValueVO>
     */
    public List<UserTypeValueVO> assignment(List<UserTypeValueVO> userTypeValueVOList) {
        userTypeValueVOList.forEach(userTypeValueVO -> {
        });
        return userTypeValueVOList;
    }


    /**
     * DO转化VO
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:49:04
     * @param       pageDO 用户类型值表
     * @return      com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    public Page<UserTypeValueVO> toPageVO(Page<UserTypeValue> pageDO) {
        Page<UserTypeValueVO> pageVO = new Page<UserTypeValueVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), UserTypeValueVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }


}