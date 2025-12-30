package com.gb.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.account.entity.UserTypeValue;
import com.gb.account.entity.bo.UserTypeValueBO;
import com.gb.account.entity.query.UserTypeValueQuery;
import com.gb.account.entity.vo.UserTypeValueVO;
import com.gb.account.mapper.UserTypeValueMapper;
import com.gb.account.service.UserTypeValueService;
import com.gb.account.service.query.UserTypeValueServiceQuery;
import com.gb.account.service.results.UserTypeValueServiceResults;
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
 * @since:   	    2021-10-21 01:49:04
 * @description:	TODO  用户类型值表，Service服务实现层
 * @source:  	    代码生成器
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserTypeValueServiceImpl extends ServiceImpl<UserTypeValueMapper, UserTypeValue> implements UserTypeValueService {


    /**
     * 用户类型值表
     */
    private UserTypeValueMapper userTypeValueMapper;


    /**
     * 用户类型值表
     */
    private UserTypeValueServiceResults userTypeValueServiceResults;


    /**
     * 集合条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:04
     * @param   userTypeValueQuery:
     * @return  java.util.List<com.entity.UserTypeValueVO>
     */
    @Override
    public List<UserTypeValueVO> listEnhance(UserTypeValueQuery userTypeValueQuery) {
        UserTypeValue userTypeValue = GeneralConvertor.convertor(userTypeValueQuery, UserTypeValue.class);
        QueryWrapper<UserTypeValue> queryWrapper = new QueryWrapper<>(userTypeValue);
        // TODO 自动生成查询，禁止手动写语句
        UserTypeValueServiceQuery.query(userTypeValueQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeValueQuery, queryWrapper);
        //DO数据
        List<UserTypeValue> userTypeValueDO = userTypeValueMapper.selectList(queryWrapper);
        //VO数据
        List<UserTypeValueVO> userTypeValueVO = GeneralConvertor.convertor(userTypeValueDO, UserTypeValueVO.class);
        return userTypeValueServiceResults.assignment(userTypeValueVO);
    }


    /**
     * 分页条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:04
     * @param   page:
     * @param   userTypeValueQuery:
     * @return  com.baomidou.mybatisplus.core.metadata.IPage
     */
    @Override
    public Page<UserTypeValueVO> pageEnhance(Page page, UserTypeValueQuery userTypeValueQuery) {
        UserTypeValue userTypeValue = GeneralConvertor.convertor(userTypeValueQuery, UserTypeValue.class);
        QueryWrapper<UserTypeValue> queryWrapper = new QueryWrapper<>(userTypeValue);
        //TODO 自动生成查询，禁止手动写语句
        UserTypeValueServiceQuery.query(userTypeValueQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeValueQuery, queryWrapper);
        //DO数据
        Page<UserTypeValue> pageDO = userTypeValueMapper.selectPage(page, queryWrapper);
        //VO数据
        Page<UserTypeValueVO> pageVO = userTypeValueServiceResults.toPageVO(pageDO);
        return userTypeValueServiceResults.assignment(pageVO);
    }


    /**
     * 单条条件查询
     * @author  孙凯伦
     * @since   2021-10-21 01:49:04
     * @param   userTypeValueQuery:
     * @return  java.util.List<com.entity.UserTypeValueVO>
     */
    @Override
    public UserTypeValueVO getOneEnhance(UserTypeValueQuery userTypeValueQuery) {
        UserTypeValue userTypeValue = GeneralConvertor.convertor(userTypeValueQuery, UserTypeValue.class);
        QueryWrapper<UserTypeValue> queryWrapper = new QueryWrapper<>(userTypeValue);
        //TODO 自动生成查询，禁止手动写语句
        UserTypeValueServiceQuery.query(userTypeValueQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeValueQuery, queryWrapper);
        //DO数据
        UserTypeValue userTypeValueDO = userTypeValueMapper.selectOne(queryWrapper);
        //VO数据
        UserTypeValueVO userTypeValueVO = GeneralConvertor.convertor(userTypeValueDO, UserTypeValueVO.class);
        return userTypeValueServiceResults.assignment(userTypeValueVO);
    }


    /**
     * 总数
     * @author  孙凯伦
     * @since   2021-10-21 01:49:04
     * @param   userTypeValueQuery:
     * @return  java.lang.Integer
     */
    @Override
    public Long countEnhance(UserTypeValueQuery userTypeValueQuery) {
        UserTypeValue userTypeValue = GeneralConvertor.convertor(userTypeValueQuery, UserTypeValue.class);
        QueryWrapper<UserTypeValue> queryWrapper = new QueryWrapper<>(userTypeValue);
        //TODO 自动生成查询，禁止手动写语句
        UserTypeValueServiceQuery.query(userTypeValueQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeValueQuery, queryWrapper);
        return userTypeValueMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     * @author  孙凯伦
     * @since   2021-10-21 01:49:04
     * @param   userTypeValueBO:
     * @return  java.lang.String
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(UserTypeValueBO userTypeValueBO) {
        //判断是否为空
        if (StringUtils.isNotBlank(userTypeValueBO.getCode())) {
            //限制
            Long x = countEnhance(new UserTypeValueQuery() {{
                setCode(userTypeValueBO.getCode());
            }});
            if (x > 0) {
                throw new BusinessException("标签值编码重复");
            }
        }
        UserTypeValue userTypeValue = GeneralConvertor.convertor(userTypeValueBO, UserTypeValue.class);
        userTypeValueMapper.insert(userTypeValue);
        return userTypeValue.getId();
    }


    /**
     * 修改
     * @author  孙凯伦
     * @since   2021-10-21 01:49:04
     * @param   userTypeValueBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(UserTypeValueBO userTypeValueBO) {
        //默认code不修改
        userTypeValueBO.setCode(null);
        UserTypeValue userTypeValue = GeneralConvertor.convertor(userTypeValueBO, UserTypeValue.class);
        UpdateWrapper<UserTypeValue > updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userTypeValueBO.getId());
        Integer i = userTypeValueMapper.update(userTypeValue, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     * @author  孙凯伦
     * @since   2021-10-21 01:49:04
     * @param   userTypeValueBO:
     * @return  java.lang.Boolean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserTypeValueBO userTypeValueBO) {
        UserTypeValue userTypeValue = GeneralConvertor.convertor(userTypeValueBO, UserTypeValue.class);
        QueryWrapper<UserTypeValue> queryWrapper = new QueryWrapper<>(userTypeValue);
        Integer i = userTypeValueMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 查询人工查询条件
     *
     * @author     	孙凯伦
     * @since   	2021-10-21 01:49:04
     * @param       userTypeValueQuery 用户类型值表
     * @return      QueryWrapper
     */
     private QueryWrapper queryArtificial(UserTypeValueQuery userTypeValueQuery, QueryWrapper<UserTypeValue> queryWrapper) {
         if(StringUtils.isNotBlank(userTypeValueQuery.getUserTypeCode())){
            queryWrapper.inSql("`user_type_id`", "select `id` from `user_type` where `code` in (" + userTypeValueQuery.getUserTypeCode() + ")");
         }
         if(StringUtils.isNotBlank(userTypeValueQuery.getIdList())) {
             queryWrapper.inSql("`id`", userTypeValueQuery.getIdList());
         }
        return queryWrapper;
    }
}