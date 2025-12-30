package com.gb.account.service.results;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.account.entity.UserExtends;
import com.gb.account.entity.vo.UserExtendsVO;
import com.gb.account.service.UserRoleService;
import com.gb.utils.GeneralConvertor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:40
 * @description:	TODO  用户扩展表,Service返回实现
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserExtendsServiceResults{

    private UserRoleService userRoleService;
    /**
     * 单条，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:40
     * @param       userExtendsVO 用户扩展表
     * @return      UserExtendsVO
     */
    public UserExtendsVO assignment(UserExtendsVO userExtendsVO) {
        return userExtendsVO;
    }


    /**
     * 分页，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:40
     * @param       userExtendsVOList 用户扩展表
     * @return      Page<UserExtendsVO>
     */
    public Page<UserExtendsVO> assignment(Page<UserExtendsVO> userExtendsVOList) {
        userExtendsVOList.getRecords().forEach(userExtendsVO -> {
        });
        return userExtendsVOList;
    }


    /**
     * 集合，增强返回参数追加
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:40
     * @param       userExtendsVOList 用户扩展表
     * @return      List<UserExtendsVO>
     */
    public List<UserExtendsVO> assignment(List<UserExtendsVO> userExtendsVOList) {
        userExtendsVOList.forEach(userExtendsVO -> {
        });
        return userExtendsVOList;
    }


    /**
     * DO转化VO
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:40
     * @param       pageDO 用户扩展表
     * @return      com.baomidou.mybatisplus.extension.plugins.pagination.Page
     */
    public Page<UserExtendsVO> toPageVO(Page<UserExtends> pageDO) {
        Page<UserExtendsVO> pageVO = new Page<UserExtendsVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), UserExtendsVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }


}