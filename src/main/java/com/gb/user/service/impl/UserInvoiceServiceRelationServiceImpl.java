package com.gb.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.bean.GongBaoConfig;
import com.gb.user.entity.UserInvoiceServiceRelation;
import com.gb.user.mapper.UserInvoiceServiceRelationMapper;
import com.gb.user.service.UserInvoiceServiceRelationService;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.ParameterNullException;
import com.gb.utils.exception.PreventRepeatException;
import com.google.common.collect.Lists;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户发票与发票服务关联关系表 服务实现类
 * </p>
 *
 * @author sunx
 * @since 2021-05-27
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserInvoiceServiceRelationServiceImpl extends ServiceImpl<UserInvoiceServiceRelationMapper, UserInvoiceServiceRelation> implements UserInvoiceServiceRelationService {


    /**
     * 用户发票与发票服务关联关系表
     */
    private UserInvoiceServiceRelationMapper userInvoiceServiceRelationMapper;


    /**
     * 集合条件查询
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelation:
     * @return      java.util.List<com.entity.UserInvoiceServiceRelation>
     */
    @Override
    public List<UserInvoiceServiceRelation> listEnhance(UserInvoiceServiceRelation userInvoiceServiceRelation) {
        QueryWrapper<UserInvoiceServiceRelation> queryWrapper = new QueryWrapper<>(userInvoiceServiceRelation);
        query(userInvoiceServiceRelation, queryWrapper);
        return assignment(userInvoiceServiceRelationMapper.selectList(queryWrapper));
    }


    /**
     * 分页条件查询
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelation:
     * @param page:
     * @return      java.util.List<com.entity.UserInvoiceServiceRelation>
     */
    @Override
    public IPage pageEnhance(Page page, UserInvoiceServiceRelation userInvoiceServiceRelation) {
        QueryWrapper<UserInvoiceServiceRelation> queryWrapper = new QueryWrapper<>(userInvoiceServiceRelation);
        query(userInvoiceServiceRelation, queryWrapper);
        return assignment(userInvoiceServiceRelationMapper.selectPage(page, queryWrapper));
    }


    /**
     * 单条条件查询
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelation:
     * @return      java.util.List<com.entity.UserInvoiceServiceRelation>
     */
    @Override
    public UserInvoiceServiceRelation getOneEnhance(UserInvoiceServiceRelation userInvoiceServiceRelation) {
        QueryWrapper<UserInvoiceServiceRelation> queryWrapper = new QueryWrapper<>(userInvoiceServiceRelation);
        query(userInvoiceServiceRelation, queryWrapper);
        return assignment(userInvoiceServiceRelationMapper.selectOne(queryWrapper));
    }


    /**
     * 总数
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelation:
     * @return      java.util.List<com.entity.UserInvoiceServiceRelation>
     */
    @Override
    public Long countEnhance(UserInvoiceServiceRelation userInvoiceServiceRelation) {
        QueryWrapper<UserInvoiceServiceRelation> queryWrapper = new QueryWrapper<>(userInvoiceServiceRelation);
        query(userInvoiceServiceRelation, queryWrapper);
        return userInvoiceServiceRelationMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelation:
     * @return      java.util.List<com.entity.UserInvoiceServiceRelation>
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean saveEnhance(boolean userFlag, UserInvoiceServiceRelation userInvoiceServiceRelation) throws Exception {
        Integer i = 0;
        if(userFlag){
            List<String> invoiceServiceIdList = Lists.newArrayList();
            //专票
            if((1 == userInvoiceServiceRelation.getType()) && StringUtils.isNotBlank(GongBaoConfig.specialInvoiceIds)){
                invoiceServiceIdList = Arrays.asList(GongBaoConfig.specialInvoiceIds.split(","));
            }
            //普票
            if((0 == userInvoiceServiceRelation.getType()) && StringUtils.isNotBlank(GongBaoConfig.commonInvoiceIds)){
                invoiceServiceIdList = Arrays.asList(GongBaoConfig.commonInvoiceIds.split(","));
            }
            if(CollectionUtils.isEmpty(invoiceServiceIdList)){
                throw new ParameterNullException("发票服务未配置！");
            }
            String invoiceServiceIdsJson = StringUtils.join(invoiceServiceIdList,",");
            //1、判断发票服务是否已经关联
            Long num = countEnhance(new UserInvoiceServiceRelation(){{
                setUserInvoiceId(userInvoiceServiceRelation.getUserInvoiceId());
                setInvoiceServiceIdsJson(invoiceServiceIdsJson);
            }});
            if(num > 0){
                throw new PreventRepeatException("用户发票与发票服务已经关联！");
            }
            //2、开始做发票与服务关联
            List<UserInvoiceServiceRelation> relationList = Lists.newArrayList();
            for(String serviceId : invoiceServiceIdList){
                UserInvoiceServiceRelation relation = new UserInvoiceServiceRelation();
                BeanUtils.copyProperties(userInvoiceServiceRelation, relation);
                relation.setInvoiceServiceId(serviceId);
                relation.setType(null);
                relationList.add(relation);
            }
            i = userInvoiceServiceRelationMapper.insertBatch(relationList);
        }else{
            i = userInvoiceServiceRelationMapper.insert(userInvoiceServiceRelation);
        }
        if(i== 0){
            throw new BusinessException("发票关联新增失败！");
        }
        return true;
    }


    /**
     * 修改
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelation:
     * @return      java.util.List<com.entity.UserInvoiceServiceRelation>
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(UserInvoiceServiceRelation userInvoiceServiceRelation) {
        UpdateWrapper<UserInvoiceServiceRelation > updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userInvoiceServiceRelation.getId());
        Integer i = userInvoiceServiceRelationMapper.update(userInvoiceServiceRelation, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelation:
     * @return      java.util.List<com.entity.UserInvoiceServiceRelation>
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserInvoiceServiceRelation userInvoiceServiceRelation) {
        QueryWrapper<UserInvoiceServiceRelation> queryWrapper = new QueryWrapper<>(userInvoiceServiceRelation);
        Integer i = userInvoiceServiceRelationMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 增强查询条件
     *
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelation:
     * @param queryWrapper:
     * @return      void
     */
    private void query(UserInvoiceServiceRelation userInvoiceServiceRelation, QueryWrapper<UserInvoiceServiceRelation> queryWrapper) {
        /**
         * 排序
         */
        if(userInvoiceServiceRelation.getCollation() != null && StringUtils.isNotBlank(userInvoiceServiceRelation.getCollationFields())){
            if(userInvoiceServiceRelation.getCollation()){
                queryWrapper.orderByAsc(userInvoiceServiceRelation.getCollationFields());
            }else{
                queryWrapper.orderByDesc(userInvoiceServiceRelation.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc(userInvoiceServiceRelation.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(userInvoiceServiceRelation.getFields())){
            queryWrapper.select(userInvoiceServiceRelation.getFields());
        }

        /**
        * sql查询服务ID列表JSON
        */
        if(StringUtils.isNotBlank(userInvoiceServiceRelation.getInvoiceServiceIdsJson())){
            queryWrapper.inSql("invoice_service_id", userInvoiceServiceRelation.getInvoiceServiceIdsJson());
        }
    }


    /**
     * 单条，增强返回参数追加
     *
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelation:
     *
     * @return      UserInvoiceServiceRelation
     */
    private UserInvoiceServiceRelation assignment(UserInvoiceServiceRelation userInvoiceServiceRelation) {
        return userInvoiceServiceRelation;
    }

    /**
     * 分页,增强返回参数追加
     *
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelationList:
     *
     * @return      UserInvoiceServiceRelation
     */
    private IPage assignment(IPage<UserInvoiceServiceRelation> userInvoiceServiceRelationList) {
        userInvoiceServiceRelationList.getRecords().forEach(userInvoiceServiceRelation -> {
        });
        return userInvoiceServiceRelationList;
    }


    /**
     * 集合,增强返回参数追加
     *
     * @author sunx
     * @since 2021-05-27
     * @param userInvoiceServiceRelationList:
     *
     * @return      UserInvoiceServiceRelation
     */
    private List<UserInvoiceServiceRelation> assignment(List<UserInvoiceServiceRelation> userInvoiceServiceRelationList) {
        userInvoiceServiceRelationList.forEach(userInvoiceServiceRelation -> {
        });
        return userInvoiceServiceRelationList;
    }
}
