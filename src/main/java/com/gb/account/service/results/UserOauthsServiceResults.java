package com.gb.account.service.results;

import com.gb.account.entity.UserOauths;
import com.gb.account.entity.vo.UserOauthsVO;
import com.gb.account.entity.bo.UserOauthsBO;
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
 * @description:	TODO  用户授权表,Service返回实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserOauthsServiceResults{


    /**
     * 单条，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:37
     * @param       userOauthsVO 用户授权表
     * @return      UserOauthsVO
     */
    public UserOauthsVO assignment(UserOauthsVO userOauthsVO) {
        if(userOauthsVO != null){
            return userOauthsVO;
        }else{
            return userOauthsVO;
        }
    }


    /**
     * 分页，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:37
     * @param       userOauthsVOList 用户授权表
     * @return      Page<UserOauthsVO>
     */
    public Page<UserOauthsVO> assignment(Page<UserOauthsVO> userOauthsVOList) {
        userOauthsVOList.getRecords().forEach(userOauthsVO -> {
        });
        return userOauthsVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:37
     * @param       userOauthsVOList 用户授权表
     * @return      List<UserOauthsVO>
     */
    public List<UserOauthsVO> assignment(List<UserOauthsVO> userOauthsVOList) {
        userOauthsVOList.forEach(userOauthsVO -> {
        });
        return userOauthsVOList;
    }


    /**
     * DO转化VO
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:37
     * @param       pageDO 用户授权表
     * @return      com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    public Page<UserOauthsVO> toPageVO(Page<UserOauths> pageDO) {
        Page<UserOauthsVO> pageVO = new Page<UserOauthsVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), UserOauthsVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }


}