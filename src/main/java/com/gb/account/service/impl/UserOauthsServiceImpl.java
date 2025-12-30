package com.gb.account.service.impl;

import com.gb.account.entity.query.UserOauthsQuery;
import com.gb.account.entity.vo.UserOauthsVO;
import com.gb.account.entity.bo.UserOauthsBO;
import com.gb.account.entity.UserOauths;
import com.gb.account.mapper.UserOauthsMapper;
import com.gb.account.service.UserOauthsService;
import com.gb.account.service.query.UserOauthsServiceQuery;
import com.gb.account.service.results.UserOauthsServiceResults;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Setter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.gb.utils.GeneralConvertor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:37
 * @description:	TODO  用户授权表，Service服务实现层
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserOauthsServiceImpl extends ServiceImpl<UserOauthsMapper, UserOauths> implements UserOauthsService {


    /**
     * 用户授权表
     */
    private UserOauthsMapper userOauthsMapper;


    /**
     * 用户授权表
     */
    private UserOauthsServiceResults userOauthsServiceResults;


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userOauthsQuery:
     * @return  java.util.List<com.entity.UserOauthsVO>
     */
    @Override
    public List<UserOauthsVO> listEnhance(UserOauthsQuery userOauthsQuery) {
        UserOauths userOauths = GeneralConvertor.convertor(userOauthsQuery, UserOauths.class);
        QueryWrapper<UserOauths> queryWrapper = new QueryWrapper<>(userOauths);
        // TODO 自动生成查询，禁止手动写语句
        UserOauthsServiceQuery.query(userOauthsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userOauthsQuery, queryWrapper);
        //DO数据
        List<UserOauths> userOauthsDO = userOauthsMapper.selectList(queryWrapper);
        //VO数据
        List<UserOauthsVO> userOauthsVO = GeneralConvertor.convertor(userOauthsDO, UserOauthsVO.class);
        return userOauthsServiceResults.assignment(userOauthsVO);
    }


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   page:
     * @param   userOauthsQuery:
     * @return  com.baomidou.mybatisplus.core.metadata.IPage
     */
    @Override
    public Page<UserOauthsVO> pageEnhance(Page page, UserOauthsQuery userOauthsQuery) {
        UserOauths userOauths = GeneralConvertor.convertor(userOauthsQuery, UserOauths.class);
        QueryWrapper<UserOauths> queryWrapper = new QueryWrapper<>(userOauths);
        //TODO 自动生成查询，禁止手动写语句
        UserOauthsServiceQuery.query(userOauthsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userOauthsQuery, queryWrapper);
        //DO数据
        Page<UserOauths> pageDO = userOauthsMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<UserOauthsVO> pageVO = userOauthsServiceResults.toPageVO(pageDO);
        return userOauthsServiceResults.assignment(pageVO);
    }


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userOauthsQuery:
     * @return  java.util.List<com.entity.UserOauthsVO>
     */
    @Override
    public UserOauthsVO getOneEnhance(UserOauthsQuery userOauthsQuery) {
        UserOauths userOauths = GeneralConvertor.convertor(userOauthsQuery, UserOauths.class);
        QueryWrapper<UserOauths> queryWrapper = new QueryWrapper<>(userOauths);
        //TODO 自动生成查询，禁止手动写语句
        UserOauthsServiceQuery.query(userOauthsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userOauthsQuery, queryWrapper);
        //DO数据
        UserOauths userOauthsDO = userOauthsMapper.selectOne(queryWrapper);
        //VO数据
        UserOauthsVO userOauthsVO = GeneralConvertor.convertor(userOauthsDO, UserOauthsVO.class);
        return userOauthsServiceResults.assignment(userOauthsVO);
    }


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userOauthsQuery:
     * @return  java.lang.Integer
     */
    @Override
    public Long countEnhance(UserOauthsQuery userOauthsQuery) {
        UserOauths userOauths = GeneralConvertor.convertor(userOauthsQuery, UserOauths.class);
        QueryWrapper<UserOauths> queryWrapper = new QueryWrapper<>(userOauths);
        //TODO 自动生成查询，禁止手动写语句
        UserOauthsServiceQuery.query(userOauthsQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userOauthsQuery, queryWrapper);
        return userOauthsMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userOauthsBO:
     * @return  java.lang.String
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(UserOauthsBO userOauthsBO) {
        UserOauths userOauths = GeneralConvertor.convertor(userOauthsBO, UserOauths.class);
        userOauthsMapper.insert(userOauths);
        return userOauths.getId();
    }


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userOauthsBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(UserOauthsBO userOauthsBO) {
        UserOauths userOauths = GeneralConvertor.convertor(userOauthsBO, UserOauths.class);
        UpdateWrapper<UserOauths > updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userOauthsBO.getId());
        Integer i = userOauthsMapper.update(userOauths, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:50:37
     * @param   userOauthsBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserOauthsBO userOauthsBO) {
        UserOauths userOauths = GeneralConvertor.convertor(userOauthsBO, UserOauths.class);
        QueryWrapper<UserOauths> queryWrapper = new QueryWrapper<>(userOauths);
        Integer i = userOauthsMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 查询人工查询条件
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:50:37
     * @param       userOauthsQuery 用户授权表
     * @return      QueryWrapper
     */
     private QueryWrapper queryArtificial(UserOauthsQuery userOauthsQuery, QueryWrapper<UserOauths> queryWrapper) {
        return queryWrapper;
    }
}