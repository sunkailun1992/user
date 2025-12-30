package com.gb.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gb.user.entity.query.UserTokenQuery;
import com.gb.user.entity.vo.UserTokenVO;
import com.gb.user.entity.bo.UserTokenBO;
import com.gb.user.entity.UserToken;
import com.gb.user.mapper.UserTokenMapper;
import com.gb.user.service.UserTokenService;
import com.gb.user.service.query.UserTokenServiceQuery;
import com.gb.user.service.results.UserTokenServiceResults;
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
 * TODO 用户设备信息表，Service服务实现层
 * 代码生成器
 *
 * @author wgs
 * @className UserTokenServiceImpl
 * @time 2022-01-20 03:40:09
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, UserToken> implements UserTokenService {


    /**
     * 用户设备信息表
     */
    private UserTokenMapper userTokenMapper;


    /**
     * 用户设备信息表
     */
    private UserTokenServiceResults userTokenServiceResults;


    /**
     * 用户设备信息表增强条件
     */
    private UserTokenServiceQuery userTokenServiceQuery;


    /**
     * TODO 集合
     *
     * @param userTokenQuery 用户设备信息表
     * @return List<UserTokenVO>
     * @author wgs
     * @methodName listEnhance
     * @time 2022-01-20 03:40:09
     */
    @Override
    public List<UserTokenVO> listEnhance(UserTokenQuery userTokenQuery) {
        UserToken userToken = GeneralConvertor.convertor(userTokenQuery, UserToken.class);
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<>(userToken);
        // TODO 自动生成查询，禁止手动写语句
        userTokenServiceQuery.query(userTokenQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTokenQuery, queryWrapper);
        // DO数据
        List<UserToken> userTokenDO = userTokenMapper.selectList(queryWrapper);
        // VO数据
        List<UserTokenVO> userTokenVO = GeneralConvertor.convertor(userTokenDO, UserTokenVO.class);
        return userTokenServiceResults.assignment(userTokenVO);
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param userTokenQuery 用户设备信息表
     * @return Page<UserTokenVO>
     * @author wgs
     * @methodName pageEnhance
     * @time 2022-01-20 03:40:09
     */
    @Override
    public Page<UserTokenVO> pageEnhance(Page page, UserTokenQuery userTokenQuery) {
        UserToken userToken = GeneralConvertor.convertor(userTokenQuery, UserToken.class);
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<>(userToken);
        // TODO 自动生成查询，禁止手动写语句
        userTokenServiceQuery.query(userTokenQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTokenQuery, queryWrapper);
        // DO数据
        Page<UserToken> pageDO = userTokenMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<UserTokenVO> pageVO = userTokenServiceResults.toPageVO(pageDO);
        return userTokenServiceResults.assignment(pageVO);
    }


    /**
     * TODO 单条
     *
     * @param userTokenQuery 用户设备信息表
     * @return UserTokenVO
     * @author wgs
     * @methodName getOneEnhance
     * @time 2022-01-20 03:40:09
     */
    @Override
    public UserTokenVO getOneEnhance(UserTokenQuery userTokenQuery) {
        UserToken userToken = GeneralConvertor.convertor(userTokenQuery, UserToken.class);
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<>(userToken);
        // TODO 自动生成查询，禁止手动写语句
        userTokenServiceQuery.query(userTokenQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTokenQuery, queryWrapper);
        // DO数据
        UserToken userTokenDO = userTokenMapper.selectOne(queryWrapper);
        // VO数据
        UserTokenVO userTokenVO = GeneralConvertor.convertor(userTokenDO, UserTokenVO.class);
        return userTokenServiceResults.assignment(userTokenVO);
    }


    /**
     * TODO 总数
     *
     * @param userTokenQuery 用户设备信息表
     * @return Integer
     * @author wgs
     * @methodName countEnhance
     * @time 2022-01-20 03:40:09
     */
    @Override
    public Long countEnhance(UserTokenQuery userTokenQuery) {
        UserToken userToken = GeneralConvertor.convertor(userTokenQuery, UserToken.class);
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<>(userToken);
        // TODO 自动生成查询，禁止手动写语句
        userTokenServiceQuery.query(userTokenQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTokenQuery, queryWrapper);
        return userTokenMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param userTokenBO 用户设备信息表
     * @return String
     * @author wgs
     * @methodName saveEnhance
     * @time 2022-01-20 03:40:09
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(UserTokenBO userTokenBO) {
        UserToken userToken = GeneralConvertor.convertor(userTokenBO, UserToken.class);
        userTokenMapper.insert(userToken);
        return userToken.getId();
    }


    /**
     * TODO 修改
     *
     * @param userTokenBO 用户设备信息表
     * @return Boolean
     * @author wgs
     * @methodName updateEnhance
     * @time 2022-01-20 03:40:09
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(UserTokenBO userTokenBO) {
        UserToken userToken = GeneralConvertor.convertor(userTokenBO, UserToken.class);
        UpdateWrapper<UserToken> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userTokenBO.getId());
        Integer i = userTokenMapper.update(userToken, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 删除
     *
     * @param userTokenBO 用户设备信息表
     * @return Boolean
     * @author wgs
     * @methodName removeEnhance
     * @time 2022-01-20 03:40:09
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserTokenBO userTokenBO) {
        UserToken userToken = GeneralConvertor.convertor(userTokenBO, UserToken.class);
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<>(userToken);
        Integer i = userTokenMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }

    @Override
    public String bindToken(UserTokenBO userTokenBO) {
        UserToken userToken = GeneralConvertor.convertor(userTokenBO, UserToken.class);
        LambdaQueryWrapper<UserToken> lambda = new LambdaQueryWrapper<>();
        lambda.eq(UserToken::getUserId, userToken.getUserId());
        lambda.eq(UserToken::getTokenType, userToken.getTokenType());
        UserToken userTokenDO = userTokenMapper.selectOne(lambda);
        String id;
        if (userTokenDO == null) {
            id = this.saveEnhance(userTokenBO);
        } else {
            userTokenBO.setId(userTokenDO.getId());
            this.updateEnhance(userTokenBO);
            id = userTokenDO.getId();
        }
        return id;
    }


    /**
     * TODO 人工查询条件
     *
     * @param userTokenQuery 用户设备信息表
     * @return QueryWrapper
     * @author wgs
     * @methodName queryArtificial
     * @time 2022-01-20 03:40:09
     */
    private QueryWrapper queryArtificial(UserTokenQuery userTokenQuery, QueryWrapper<UserToken> queryWrapper) {
        return queryWrapper;
    }
}