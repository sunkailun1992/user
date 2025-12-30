package com.gb.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.account.entity.UserType;
import com.gb.account.entity.bo.UserTypeBO;
import com.gb.account.entity.query.UserTypeQuery;
import com.gb.account.entity.query.UserTypeValueQuery;
import com.gb.account.entity.vo.UserTypeVO;
import com.gb.account.mapper.UserTypeMapper;
import com.gb.account.service.UserTypeService;
import com.gb.account.service.query.UserTypeServiceQuery;
import com.gb.account.service.results.UserTypeServiceResults;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.exception.BusinessException;
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
 * @since:   	    2021-10-21 01:49:03
 * @description:	TODO  用户类型表，Service服务实现层
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserTypeServiceImpl extends ServiceImpl<UserTypeMapper, UserType> implements UserTypeService {


    /**
     * 用户类型表
     */
    private UserTypeMapper userTypeMapper;


    /**
     * 用户类型表
     */
    private UserTypeServiceResults userTypeServiceResults;


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:03
     * @param   userTypeQuery:
     * @return  java.util.List<com.entity.UserTypeVO>
     */
    @Override
    public List<UserTypeVO> listEnhance(UserTypeQuery userTypeQuery) {
        UserType userType = GeneralConvertor.convertor(userTypeQuery, UserType.class);
        QueryWrapper<UserType> queryWrapper = new QueryWrapper<>(userType);
        // TODO 自动生成查询，禁止手动写语句
        UserTypeServiceQuery.query(userTypeQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeQuery, queryWrapper);
        //DO数据
        List<UserType> userTypeDO = userTypeMapper.selectList(queryWrapper);
        //VO数据
        List<UserTypeVO> userTypeVO = GeneralConvertor.convertor(userTypeDO, UserTypeVO.class);
        return userTypeServiceResults.assignment(userTypeVO);
    }


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:03
     * @param   page:
     * @param   userTypeQuery:
     * @return  com.baomidou.mybatisplus.core.metadata.IPage
     */
    @Override
    public Page<UserTypeVO> pageEnhance(Page page, UserTypeQuery userTypeQuery) {
        UserType userType = GeneralConvertor.convertor(userTypeQuery, UserType.class);
        QueryWrapper<UserType> queryWrapper = new QueryWrapper<>(userType);
        //TODO 自动生成查询，禁止手动写语句
        UserTypeServiceQuery.query(userTypeQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeQuery, queryWrapper);
        //DO数据
        Page<UserType> pageDO = userTypeMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<UserTypeVO> pageVO = userTypeServiceResults.toPageVO(pageDO);
        return userTypeServiceResults.assignment(pageVO);
    }


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:03
     * @param   userTypeQuery:
     * @return  java.util.List<com.entity.UserTypeVO>
     */
    @Override
    public UserTypeVO getOneEnhance(UserTypeQuery userTypeQuery) {
        UserType userType = GeneralConvertor.convertor(userTypeQuery, UserType.class);
        QueryWrapper<UserType> queryWrapper = new QueryWrapper<>(userType);
        //TODO 自动生成查询，禁止手动写语句
        UserTypeServiceQuery.query(userTypeQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeQuery, queryWrapper);
        //DO数据
        UserType userTypeDO = userTypeMapper.selectOne(queryWrapper);
        //VO数据
        UserTypeVO userTypeVO = GeneralConvertor.convertor(userTypeDO, UserTypeVO.class);
        return userTypeServiceResults.assignment(userTypeVO);
    }


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:49:03
     * @param   userTypeQuery:
     * @return  java.lang.Integer
     */
    @Override
    public Long countEnhance(UserTypeQuery userTypeQuery) {
        UserType userType = GeneralConvertor.convertor(userTypeQuery, UserType.class);
        QueryWrapper<UserType> queryWrapper = new QueryWrapper<>(userType);
        //TODO 自动生成查询，禁止手动写语句
        UserTypeServiceQuery.query(userTypeQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeQuery, queryWrapper);
        return userTypeMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:49:03
     * @param   userTypeBO:
     * @return  java.lang.String
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(UserTypeBO userTypeBO) {
        //判断是否为空
        if (StringUtils.isNotBlank(userTypeBO.getCode())) {
            //限制
            Long x = countEnhance(new UserTypeQuery() {{
                setCode(userTypeBO.getCode());
            }});
            if (x > 0) {
                throw new BusinessException("标签编码重复");
            }
        }
        UserType userType = GeneralConvertor.convertor(userTypeBO, UserType.class);
        userTypeMapper.insert(userType);
        return userType.getId();
    }


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:49:03
     * @param   userTypeBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(UserTypeBO userTypeBO) {
        //默认编码不做修改
        userTypeBO.setCode(null);
        UserType userType = GeneralConvertor.convertor(userTypeBO, UserType.class);
        UpdateWrapper<UserType > updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userTypeBO.getId());
        Integer i = userTypeMapper.update(userType, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:49:03
     * @param   userTypeBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserTypeBO userTypeBO) {
        UserType userType = GeneralConvertor.convertor(userTypeBO, UserType.class);
        QueryWrapper<UserType> queryWrapper = new QueryWrapper<>(userType);
        Integer i = userTypeMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 查询人工查询条件
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:49:03
     * @param       userTypeQuery 用户类型表
     * @return      QueryWrapper
     */
     private QueryWrapper queryArtificial(UserTypeQuery userTypeQuery, QueryWrapper<UserType> queryWrapper) {
        return queryWrapper;
    }
}