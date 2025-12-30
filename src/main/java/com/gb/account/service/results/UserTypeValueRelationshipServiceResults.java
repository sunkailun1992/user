package com.gb.account.service.results;

import com.gb.account.entity.UserTypeValueRelationship;
import com.gb.account.entity.vo.UserTypeValueRelationshipVO;
import com.gb.account.entity.bo.UserTypeValueRelationshipBO;
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
 * @since:   	    2021-10-21 01:49:05
 * @description:	TODO  用户类型值关联,Service返回实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserTypeValueRelationshipServiceResults{


    /**
     * 单条，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:49:05
     * @param       userTypeValueRelationshipVO 用户类型值关联
     * @return      UserTypeValueRelationshipVO
     */
    public UserTypeValueRelationshipVO assignment(UserTypeValueRelationshipVO userTypeValueRelationshipVO) {
        if(userTypeValueRelationshipVO != null){
            return userTypeValueRelationshipVO;
        }else{
            return userTypeValueRelationshipVO;
        }
    }


    /**
     * 分页，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:49:05
     * @param       userTypeValueRelationshipVOList 用户类型值关联
     * @return      Page<UserTypeValueRelationshipVO>
     */
    public Page<UserTypeValueRelationshipVO> assignment(Page<UserTypeValueRelationshipVO> userTypeValueRelationshipVOList) {
        userTypeValueRelationshipVOList.getRecords().forEach(userTypeValueRelationshipVO -> {
        });
        return userTypeValueRelationshipVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:49:05
     * @param       userTypeValueRelationshipVOList 用户类型值关联
     * @return      List<UserTypeValueRelationshipVO>
     */
    public List<UserTypeValueRelationshipVO> assignment(List<UserTypeValueRelationshipVO> userTypeValueRelationshipVOList) {
        userTypeValueRelationshipVOList.forEach(userTypeValueRelationshipVO -> {
        });
        return userTypeValueRelationshipVOList;
    }


    /**
     * DO转化VO
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:49:05
     * @param       pageDO 用户类型值关联
     * @return      com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    public Page<UserTypeValueRelationshipVO> toPageVO(Page<UserTypeValueRelationship> pageDO) {
        Page<UserTypeValueRelationshipVO> pageVO = new Page<UserTypeValueRelationshipVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), UserTypeValueRelationshipVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }


}