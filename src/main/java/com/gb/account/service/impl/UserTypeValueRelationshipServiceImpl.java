package com.gb.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.account.entity.UserTypeValueRelationship;
import com.gb.account.entity.bo.UserTypeValueRelationshipBO;
import com.gb.account.entity.query.UserTypeValueRelationshipQuery;
import com.gb.account.entity.vo.UserTypeValueRelationshipVO;
import com.gb.account.mapper.UserTypeValueRelationshipMapper;
import com.gb.account.service.UserTypeValueRelationshipService;
import com.gb.account.service.query.UserTypeValueRelationshipServiceQuery;
import com.gb.account.service.results.UserTypeValueRelationshipServiceResults;
import com.gb.utils.GeneralConvertor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:49:05
 * @description:	TODO  用户类型值关联，Service服务实现层
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserTypeValueRelationshipServiceImpl extends ServiceImpl<UserTypeValueRelationshipMapper, UserTypeValueRelationship> implements UserTypeValueRelationshipService {


    /**
     * 用户类型值关联
     */
    private UserTypeValueRelationshipMapper userTypeValueRelationshipMapper;


    /**
     * 用户类型值关联
     */
    private UserTypeValueRelationshipServiceResults userTypeValueRelationshipServiceResults;


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:05
     * @param   userTypeValueRelationshipQuery:
     * @return  java.util.List<com.entity.UserTypeValueRelationshipVO>
     */
    @Override
    public List<UserTypeValueRelationshipVO> listEnhance(UserTypeValueRelationshipQuery userTypeValueRelationshipQuery) {
        UserTypeValueRelationship userTypeValueRelationship = GeneralConvertor.convertor(userTypeValueRelationshipQuery, UserTypeValueRelationship.class);
        QueryWrapper<UserTypeValueRelationship> queryWrapper = new QueryWrapper<>(userTypeValueRelationship);
        // TODO 自动生成查询，禁止手动写语句
        UserTypeValueRelationshipServiceQuery.query(userTypeValueRelationshipQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeValueRelationshipQuery, queryWrapper);
        //DO数据
        List<UserTypeValueRelationship> userTypeValueRelationshipDO = userTypeValueRelationshipMapper.selectList(queryWrapper);
        //VO数据
        List<UserTypeValueRelationshipVO> userTypeValueRelationshipVO = GeneralConvertor.convertor(userTypeValueRelationshipDO, UserTypeValueRelationshipVO.class);
        return userTypeValueRelationshipServiceResults.assignment(userTypeValueRelationshipVO);
    }


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:05
     * @param   page:
     * @param   userTypeValueRelationshipQuery:
     * @return  com.baomidou.mybatisplus.core.metadata.IPage
     */
    @Override
    public Page<UserTypeValueRelationshipVO> pageEnhance(Page page, UserTypeValueRelationshipQuery userTypeValueRelationshipQuery) {
        UserTypeValueRelationship userTypeValueRelationship = GeneralConvertor.convertor(userTypeValueRelationshipQuery, UserTypeValueRelationship.class);
        QueryWrapper<UserTypeValueRelationship> queryWrapper = new QueryWrapper<>(userTypeValueRelationship);
        //TODO 自动生成查询，禁止手动写语句
        UserTypeValueRelationshipServiceQuery.query(userTypeValueRelationshipQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeValueRelationshipQuery, queryWrapper);
        //DO数据
        Page<UserTypeValueRelationship> pageDO = userTypeValueRelationshipMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<UserTypeValueRelationshipVO> pageVO = userTypeValueRelationshipServiceResults.toPageVO(pageDO);
        return userTypeValueRelationshipServiceResults.assignment(pageVO);
    }


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:05
     * @param   userTypeValueRelationshipQuery:
     * @return  java.util.List<com.entity.UserTypeValueRelationshipVO>
     */
    @Override
    public UserTypeValueRelationshipVO getOneEnhance(UserTypeValueRelationshipQuery userTypeValueRelationshipQuery) {
        UserTypeValueRelationship userTypeValueRelationship = GeneralConvertor.convertor(userTypeValueRelationshipQuery, UserTypeValueRelationship.class);
        QueryWrapper<UserTypeValueRelationship> queryWrapper = new QueryWrapper<>(userTypeValueRelationship);
        //TODO 自动生成查询，禁止手动写语句
        UserTypeValueRelationshipServiceQuery.query(userTypeValueRelationshipQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeValueRelationshipQuery, queryWrapper);
        //DO数据
        UserTypeValueRelationship userTypeValueRelationshipDO = userTypeValueRelationshipMapper.selectOne(queryWrapper);
        //VO数据
        UserTypeValueRelationshipVO userTypeValueRelationshipVO = GeneralConvertor.convertor(userTypeValueRelationshipDO, UserTypeValueRelationshipVO.class);
        return userTypeValueRelationshipServiceResults.assignment(userTypeValueRelationshipVO);
    }


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:49:05
     * @param   userTypeValueRelationshipQuery:
     * @return  java.lang.Integer
     */
    @Override
    public Long countEnhance(UserTypeValueRelationshipQuery userTypeValueRelationshipQuery) {
        UserTypeValueRelationship userTypeValueRelationship = GeneralConvertor.convertor(userTypeValueRelationshipQuery, UserTypeValueRelationship.class);
        QueryWrapper<UserTypeValueRelationship> queryWrapper = new QueryWrapper<>(userTypeValueRelationship);
        //TODO 自动生成查询，禁止手动写语句
        UserTypeValueRelationshipServiceQuery.query(userTypeValueRelationshipQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeValueRelationshipQuery, queryWrapper);
        return userTypeValueRelationshipMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:49:05
     * @param   userTypeValueRelationshipBO:
     * @return  java.lang.String
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(UserTypeValueRelationshipBO userTypeValueRelationshipBO) {
        UserTypeValueRelationship userTypeValueRelationship = GeneralConvertor.convertor(userTypeValueRelationshipBO, UserTypeValueRelationship.class);
        userTypeValueRelationshipMapper.insert(userTypeValueRelationship);
        return userTypeValueRelationship.getId();
    }


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:49:05
     * @param   userTypeValueRelationshipBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(UserTypeValueRelationshipBO userTypeValueRelationshipBO) {
        UserTypeValueRelationship userTypeValueRelationship = GeneralConvertor.convertor(userTypeValueRelationshipBO, UserTypeValueRelationship.class);
        UpdateWrapper<UserTypeValueRelationship > updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userTypeValueRelationshipBO.getId());
        Integer i = userTypeValueRelationshipMapper.update(userTypeValueRelationship, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:49:05
     * @param   userTypeValueRelationshipBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserTypeValueRelationshipBO userTypeValueRelationshipBO) {
        UserTypeValueRelationship userTypeValueRelationship = GeneralConvertor.convertor(userTypeValueRelationshipBO, UserTypeValueRelationship.class);
        QueryWrapper<UserTypeValueRelationship> queryWrapper = new QueryWrapper<>(userTypeValueRelationship);
        Integer i = userTypeValueRelationshipMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 查询人工查询条件
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:49:05
     * @param       userTypeValueRelationshipQuery 用户类型值关联
     * @return      QueryWrapper
     */
     private QueryWrapper queryArtificial(UserTypeValueRelationshipQuery userTypeValueRelationshipQuery, QueryWrapper<UserTypeValueRelationship> queryWrapper) {
         if(StringUtils.isNotBlank(userTypeValueRelationshipQuery.getUserTypeValueCode())){
             queryWrapper.inSql("user_type_value_id", "select `id` from `user_type_value` where `code` in (" + com.gb.utils.StringUtils.in(userTypeValueRelationshipQuery.getUserTypeValueCode()) + ") and `is_delete` = 0");
         }
         return queryWrapper;
    }
}