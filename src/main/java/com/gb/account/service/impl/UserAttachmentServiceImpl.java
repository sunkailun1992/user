package com.gb.account.service.impl;

import com.gb.account.entity.query.UserAttachmentQuery;
import com.gb.account.entity.vo.UserAttachmentVO;
import com.gb.account.entity.bo.UserAttachmentBO;
import com.gb.account.entity.UserAttachment;
import com.gb.account.mapper.UserAttachmentMapper;
import com.gb.account.service.UserAttachmentService;
import com.gb.account.service.query.UserAttachmentServiceQuery;
import com.gb.account.service.results.UserAttachmentServiceResults;
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
 * TODO 用户附件，Service服务实现层
 * 代码生成器
 *
 * @author lijh
 * @className UserAttachmentServiceImpl
 * @time 2022-04-14 10:04:04
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserAttachmentServiceImpl extends ServiceImpl<UserAttachmentMapper, UserAttachment> implements UserAttachmentService {


    /**
     * 用户附件
     */
    private UserAttachmentMapper userAttachmentMapper;


    /**
     * 用户附件
     */
    private UserAttachmentServiceResults userAttachmentServiceResults;


    /**
     * 用户附件增强条件
     */
    private UserAttachmentServiceQuery userAttachmentServiceQuery;


    /**
     * TODO 集合
     *
     * @param userAttachmentQuery 用户附件
     * @return List<UserAttachmentVO>
     * @author lijh
     * @methodName listEnhance
     * @time 2022-04-14 10:04:04
     */
    @Override
    public List<UserAttachmentVO> listEnhance(UserAttachmentQuery userAttachmentQuery) {
        UserAttachment userAttachment = GeneralConvertor.convertor(userAttachmentQuery, UserAttachment.class);
        QueryWrapper<UserAttachment> queryWrapper = new QueryWrapper<>(userAttachment);
        // TODO 自动生成查询，禁止手动写语句
        userAttachmentServiceQuery.query(userAttachmentQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userAttachmentQuery, queryWrapper);
        // DO数据
        List<UserAttachment> userAttachmentDO = userAttachmentMapper.selectList(queryWrapper);
        // VO数据
        List<UserAttachmentVO> userAttachmentVO = GeneralConvertor.convertor(userAttachmentDO, UserAttachmentVO.class);
        return userAttachmentServiceResults.assignment(userAttachmentVO);
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param userAttachmentQuery 用户附件
     * @return Page<UserAttachmentVO>
     * @author lijh
     * @methodName pageEnhance
     * @time 2022-04-14 10:04:04
     */
    @Override
    public Page<UserAttachmentVO> pageEnhance(Page page, UserAttachmentQuery userAttachmentQuery) {
        UserAttachment userAttachment = GeneralConvertor.convertor(userAttachmentQuery, UserAttachment.class);
        QueryWrapper<UserAttachment> queryWrapper = new QueryWrapper<>(userAttachment);
        // TODO 自动生成查询，禁止手动写语句
        userAttachmentServiceQuery.query(userAttachmentQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userAttachmentQuery, queryWrapper);
        // DO数据
        Page<UserAttachment> pageDO = userAttachmentMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<UserAttachmentVO> pageVO = userAttachmentServiceResults.toPageVO(pageDO);
        return userAttachmentServiceResults.assignment(pageVO);
    }


    /**
     * TODO 单条
     *
     * @param userAttachmentQuery 用户附件
     * @return UserAttachmentVO
     * @author lijh
     * @methodName getOneEnhance
     * @time 2022-04-14 10:04:04
     */
    @Override
    public UserAttachmentVO getOneEnhance(UserAttachmentQuery userAttachmentQuery) {
        UserAttachment userAttachment = GeneralConvertor.convertor(userAttachmentQuery, UserAttachment.class);
        QueryWrapper<UserAttachment> queryWrapper = new QueryWrapper<>(userAttachment);
        // TODO 自动生成查询，禁止手动写语句
        userAttachmentServiceQuery.query(userAttachmentQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userAttachmentQuery, queryWrapper);
        // DO数据
        UserAttachment userAttachmentDO = userAttachmentMapper.selectOne(queryWrapper);
        // VO数据
        UserAttachmentVO userAttachmentVO = GeneralConvertor.convertor(userAttachmentDO, UserAttachmentVO.class);
        return userAttachmentServiceResults.assignment(userAttachmentVO);
    }


    /**
     * TODO 总数
     *
     * @param userAttachmentQuery 用户附件
     * @return Integer
     * @author lijh
     * @methodName countEnhance
     * @time 2022-04-14 10:04:04
     */
    @Override
    public Long countEnhance(UserAttachmentQuery userAttachmentQuery) {
        UserAttachment userAttachment = GeneralConvertor.convertor(userAttachmentQuery, UserAttachment.class);
        QueryWrapper<UserAttachment> queryWrapper = new QueryWrapper<>(userAttachment);
        // TODO 自动生成查询，禁止手动写语句
        userAttachmentServiceQuery.query(userAttachmentQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userAttachmentQuery, queryWrapper);
        return userAttachmentMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param userAttachmentBO 用户附件
     * @return String
     * @author lijh
     * @methodName saveEnhance
     * @time 2022-04-14 10:04:04
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(UserAttachmentBO userAttachmentBO) {
        UserAttachment userAttachment = GeneralConvertor.convertor(userAttachmentBO, UserAttachment.class);
        userAttachmentMapper.insert(userAttachment);
        return userAttachment.getId();
    }


    /**
     * TODO 修改
     *
     * @param userAttachmentBO 用户附件
     * @return Boolean
     * @author lijh
     * @methodName updateEnhance
     * @time 2022-04-14 10:04:04
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(UserAttachmentBO userAttachmentBO) {
        UserAttachment userAttachment = GeneralConvertor.convertor(userAttachmentBO, UserAttachment.class);
        UpdateWrapper<UserAttachment> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userAttachmentBO.getId());
        int i = userAttachmentMapper.update(userAttachment, updateWrapper);
        return i > 0;
    }


    /**
     * TODO 删除
     *
     * @param userAttachmentBO 用户附件
     * @return Boolean
     * @author lijh
     * @methodName removeEnhance
     * @time 2022-04-14 10:04:04
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserAttachmentBO userAttachmentBO) {
        UserAttachment userAttachment = GeneralConvertor.convertor(userAttachmentBO, UserAttachment.class);
        QueryWrapper<UserAttachment> queryWrapper = new QueryWrapper<>(userAttachment);
        int i = userAttachmentMapper.delete(queryWrapper);
        return i > 0;
    }


    /**
     * TODO 人工查询条件
     *
     * @param userAttachmentQuery 用户附件
     * @return QueryWrapper
     * @author lijh
     * @methodName queryArtificial
     * @time 2022-04-14 10:04:04
     */
    private QueryWrapper queryArtificial(UserAttachmentQuery userAttachmentQuery, QueryWrapper<UserAttachment> queryWrapper) {

        if (StringUtils.isNotBlank(userAttachmentQuery.getId())) {
            queryWrapper.eq("id", userAttachmentQuery.getId());
        }

        if (StringUtils.isNotBlank(userAttachmentQuery.getUserId())) {
            queryWrapper.eq("user_id", userAttachmentQuery.getUserId());
        }

        /**
         * 文件名称模糊查询
         */
        if (StringUtils.isNotBlank(userAttachmentQuery.getQuery())) {
            queryWrapper.like("name", userAttachmentQuery.getQuery());
        }
        return queryWrapper;
    }
}