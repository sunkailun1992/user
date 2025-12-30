package com.gb.user.service.impl;

import com.gb.user.entity.query.UserNotSpuQuery;
import com.gb.user.entity.vo.UserNotSpuVO;
import com.gb.user.entity.bo.UserNotSpuBO;
import com.gb.user.entity.UserNotSpu;
import com.gb.user.mapper.UserNotSpuMapper;
import com.gb.user.service.UserNotSpuService;
import com.gb.user.service.query.UserNotSpuServiceQuery;
import com.gb.user.service.results.UserNotSpuServiceResults;
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
 * TODO 企业渠道用户排除产品，Service服务实现层
 * 代码生成器
 *
 * @author 孙凯伦
 * @className UserNotSpuServiceImpl
 * @time 2023-07-07 04:36:59
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserNotSpuServiceImpl extends ServiceImpl<UserNotSpuMapper, UserNotSpu> implements UserNotSpuService {


    /**
     * 企业渠道用户排除产品
     */
    private UserNotSpuMapper userNotSpuMapper;


    /**
     * 企业渠道用户排除产品
     */
    private UserNotSpuServiceResults userNotSpuServiceResults;


    /**
     * 企业渠道用户排除产品增强条件
     */
    private UserNotSpuServiceQuery userNotSpuServiceQuery;


    /**
     * TODO 集合
     *
     * @param userNotSpuQuery 企业渠道用户排除产品
     * @return List<UserNotSpuVO>
     * @author 孙凯伦
     * @methodName listEnhance
     * @time 2023-07-07 04:36:59
     */
    @Override
    public List<UserNotSpuVO> listEnhance(UserNotSpuQuery userNotSpuQuery) {
        UserNotSpu userNotSpu = GeneralConvertor.convertor(userNotSpuQuery, UserNotSpu.class);
        QueryWrapper<UserNotSpu> queryWrapper = new QueryWrapper<>(userNotSpu);
        // TODO 自动生成查询，禁止手动写语句
        userNotSpuServiceQuery.query(userNotSpuQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userNotSpuQuery, queryWrapper);
        // DO数据
        List<UserNotSpu> userNotSpuDO = userNotSpuMapper.selectList(queryWrapper);
        // VO数据
        List<UserNotSpuVO> userNotSpuVO = GeneralConvertor.convertor(userNotSpuDO, UserNotSpuVO.class);
        // 判断是否增强
        if (userNotSpuQuery.getAssignment() == null) {
            return userNotSpuServiceResults.assignment(userNotSpuVO);
        } else {
            return userNotSpuQuery.getAssignment() ? userNotSpuServiceResults.assignment(userNotSpuVO) : userNotSpuVO;
        }
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param userNotSpuQuery 企业渠道用户排除产品
     * @return Page<UserNotSpuVO>
     * @author 孙凯伦
     * @methodName pageEnhance
     * @time 2023-07-07 04:36:59
     */
    @Override
    public Page<UserNotSpuVO> pageEnhance(Page page, UserNotSpuQuery userNotSpuQuery) {
        UserNotSpu userNotSpu = GeneralConvertor.convertor(userNotSpuQuery, UserNotSpu.class);
        QueryWrapper<UserNotSpu> queryWrapper = new QueryWrapper<>(userNotSpu);
        // TODO 自动生成查询，禁止手动写语句
        userNotSpuServiceQuery.query(userNotSpuQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userNotSpuQuery, queryWrapper);
        // DO数据
        Page<UserNotSpu> pageDO = userNotSpuMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<UserNotSpuVO> pageVO = userNotSpuServiceResults.toPageVO(pageDO);
        // 判断是否增强
        if (userNotSpuQuery.getAssignment() == null) {
            return userNotSpuServiceResults.assignment(pageVO);
        } else {
            return userNotSpuQuery.getAssignment() ? userNotSpuServiceResults.assignment(pageVO) : pageVO;
        }
    }


    /**
     * TODO 单条
     *
     * @param userNotSpuQuery 企业渠道用户排除产品
     * @return UserNotSpuVO
     * @author 孙凯伦
     * @methodName getOneEnhance
     * @time 2023-07-07 04:36:59
     */
    @Override
    public UserNotSpuVO getOneEnhance(UserNotSpuQuery userNotSpuQuery) {
        UserNotSpu userNotSpu = GeneralConvertor.convertor(userNotSpuQuery, UserNotSpu.class);
        QueryWrapper<UserNotSpu> queryWrapper = new QueryWrapper<>(userNotSpu);
        // TODO 自动生成查询，禁止手动写语句
        userNotSpuServiceQuery.query(userNotSpuQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userNotSpuQuery, queryWrapper);
        // DO数据
        UserNotSpu userNotSpuDO = userNotSpuMapper.selectOne(queryWrapper);
        // VO数据
        UserNotSpuVO userNotSpuVO = GeneralConvertor.convertor(userNotSpuDO, UserNotSpuVO.class);
        // 判断是否增强
        if (userNotSpuQuery.getAssignment() == null) {
            return userNotSpuServiceResults.assignment(userNotSpuVO);
        } else {
            return userNotSpuQuery.getAssignment() ? userNotSpuServiceResults.assignment(userNotSpuVO) : userNotSpuVO;
        }
    }


    /**
     * TODO 总数
     *
     * @param userNotSpuQuery 企业渠道用户排除产品
     * @return Integer
     * @author 孙凯伦
     * @methodName countEnhance
     * @time 2023-07-07 04:36:59
     */
    @Override
    public Long countEnhance(UserNotSpuQuery userNotSpuQuery) {
        UserNotSpu userNotSpu = GeneralConvertor.convertor(userNotSpuQuery, UserNotSpu.class);
        QueryWrapper<UserNotSpu> queryWrapper = new QueryWrapper<>(userNotSpu);
        // TODO 自动生成查询，禁止手动写语句
        userNotSpuServiceQuery.query(userNotSpuQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userNotSpuQuery, queryWrapper);
        return userNotSpuMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param userNotSpuBO 企业渠道用户排除产品
     * @return String
     * @author 孙凯伦
     * @methodName saveEnhance
     * @time 2023-07-07 04:36:59
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(UserNotSpuBO userNotSpuBO) {
        UserNotSpu userNotSpu = GeneralConvertor.convertor(userNotSpuBO, UserNotSpu.class);
        userNotSpuMapper.insert(userNotSpu);
        return userNotSpu.getId();
    }


    /**
     * TODO 修改
     *
     * @param userNotSpuBO 企业渠道用户排除产品
     * @return Boolean
     * @author 孙凯伦
     * @methodName updateEnhance
     * @time 2023-07-07 04:36:59
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(UserNotSpuBO userNotSpuBO) {
        UserNotSpu userNotSpu = GeneralConvertor.convertor(userNotSpuBO, UserNotSpu.class);
        UpdateWrapper<UserNotSpu> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userNotSpuBO.getId());
        Integer i = userNotSpuMapper.update(userNotSpu, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 删除
     *
     * @param userNotSpuBO 企业渠道用户排除产品
     * @return Boolean
     * @author 孙凯伦
     * @methodName removeEnhance
     * @time 2023-07-07 04:36:59
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserNotSpuBO userNotSpuBO) {
        UserNotSpu userNotSpu = GeneralConvertor.convertor(userNotSpuBO, UserNotSpu.class);
        QueryWrapper<UserNotSpu> queryWrapper = new QueryWrapper<>(userNotSpu);
        Integer i = userNotSpuMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 人工查询条件
     *
     * @param userNotSpuQuery 企业渠道用户排除产品
     * @return QueryWrapper
     * @author 孙凯伦
     * @methodName queryArtificial
     * @time 2023-07-07 04:36:59
     */
    private QueryWrapper queryArtificial(UserNotSpuQuery userNotSpuQuery, QueryWrapper<UserNotSpu> queryWrapper) {
        return queryWrapper;
    }
}