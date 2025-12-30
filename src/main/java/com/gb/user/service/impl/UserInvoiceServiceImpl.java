package com.gb.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.user.entity.UserInvoice;
import com.gb.user.entity.UserInvoiceServiceRelation;
import com.gb.user.mapper.UserInvoiceMapper;
import com.gb.user.service.UserInvoiceService;
import com.gb.user.service.UserInvoiceServiceRelationService;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.ParameterNullException;
import com.gb.utils.exception.PreventRepeatException;
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
 * <p>
 * 用户发票表 服务实现类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserInvoiceServiceImpl extends ServiceImpl<UserInvoiceMapper, UserInvoice> implements UserInvoiceService {


    /**
     * 用户发票表
     */
    private UserInvoiceMapper userInvoiceMapper;

    private UserInvoiceServiceRelationService  relationService;


    /**
     * 集合条件查询
     * @author sunx
     * @since 2021-05-25
     * @param userInvoice:
     * @return      java.util.List<com.entity.UserInvoice>
     */
    @Override
    public List<UserInvoice> listEnhance(UserInvoice userInvoice) {
        QueryWrapper<UserInvoice> queryWrapper = new QueryWrapper<>(userInvoice);
        query(userInvoice, queryWrapper);
        if(StringUtils.isNotBlank(userInvoice.getInvoiceServiceId())){
            return assignment(userInvoiceMapper.selectListByInvoiceServiceId(queryWrapper));
        } else {
            return assignment(userInvoiceMapper.selectList(queryWrapper));
        }
    }


    /**
     * 分页条件查询
     * @author sunx
     * @since 2021-05-25
     * @param userInvoice:
     * @param page:
     * @return      java.util.List<com.entity.UserInvoice>
     */
    @Override
    public IPage pageEnhance(Page page, UserInvoice userInvoice) {
        QueryWrapper<UserInvoice> queryWrapper = new QueryWrapper<>(userInvoice);
        query(userInvoice, queryWrapper);
        if(StringUtils.isNotBlank(userInvoice.getInvoiceServiceId())){
            return assignment(userInvoiceMapper.selectPageByInvoiceServiceId(page, queryWrapper));
        } else {
            return assignment(userInvoiceMapper.selectPage(page, queryWrapper));
        }
    }


    /**
     * 单条条件查询
     * @author sunx
     * @since 2021-05-25
     * @param userInvoice:
     * @return      java.util.List<com.entity.UserInvoice>
     */
    @Override
    public UserInvoice getOneEnhance(UserInvoice userInvoice) {
        QueryWrapper<UserInvoice> queryWrapper = new QueryWrapper<>(userInvoice);
        query(userInvoice, queryWrapper);
        return assignment(userInvoiceMapper.selectOne(queryWrapper));
    }


    /**
     * 总数
     * @author sunx
     * @since 2021-05-25
     * @param userInvoice:
     * @return      java.util.List<com.entity.UserInvoice>
     */
    @Override
    public Long countEnhance(UserInvoice userInvoice) {
        QueryWrapper<UserInvoice> queryWrapper = new QueryWrapper<>(userInvoice);
        query(userInvoice, queryWrapper);
        return userInvoiceMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     * @author sunx
     * @since 2021-05-25
     * @param userInvoice:
     * @return      java.util.List<com.entity.UserInvoice>
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean saveEnhance(UserInvoice userInvoice) throws Exception {
        Long num = countEnhance(new UserInvoice(){{
            setUserId(userInvoice.getUserId());
            setType(userInvoice.getType());
            setInvoiceLookedUp(userInvoice.getInvoiceLookedUp());
        }});
        if(num > 0){
            throw new PreventRepeatException("该发票抬头已存在！");
        }
        //发票税号全部大写
        String tacCoding = userInvoice.getInvoiceTaxCoding();
        if(StringUtils.isNotBlank(tacCoding)){
            userInvoice.setInvoiceTaxCoding(tacCoding.toUpperCase());
        }
        Integer i = userInvoiceMapper.insert(userInvoice);
        if(i== 0){
            throw new BusinessException("发票抬头新增失败！");
        }
        return relationService.saveEnhance(true, new UserInvoiceServiceRelation(){{
            setUserInvoiceId(userInvoice.getId());
            setType(userInvoice.getType());
        }});
    }


    /**
     * 修改
     * @author sunx
     * @since 2021-05-25
     * @param userInvoice:
     * @return      java.util.List<com.entity.UserInvoice>
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(boolean stateUpdate, UserInvoice userInvoice) throws Exception {
        //1、判断当前的发票抬头是否存在
        UserInvoice innerVoice = getOneEnhance(new UserInvoice(){{
            setId(userInvoice.getId());
        }});
        if(null == innerVoice){
            log.error("用户对应的该发票抬头【id：{}】不存在！", userInvoice.getId());
            throw new ParameterNullException("用户对应的该发票抬头不存在！");
        }
        UpdateWrapper<UserInvoice> updateWrapper = new UpdateWrapper<>();
        UserInvoice updateAddress = new UserInvoice();
        //1、判断是否是设置默认状态的操作
        if(stateUpdate){
            if(userInvoice.getState().equals(innerVoice.getState())){
                log.debug("发票抬头状态与被修改的发票抬头状态【{}】一致，无须更新！", innerVoice.getState());
                return true;
            }
            int fxState = (userInvoice.getState().equals(1)) ? 0 : 1;
            //2、其他地址全部设置为非默认值-默认值
            updateWrapper.eq("user_id", innerVoice.getUserId());
            updateAddress.setState(fxState);
            userInvoiceMapper.update(updateAddress, updateWrapper);
            //3、当前参数设置为默认值
            updateAddress = userInvoice;
        }else{
            UserInvoice comInvoice = getOneEnhance(new UserInvoice(){{
                setUserId(innerVoice.getUserId());
                setType(userInvoice.getType());
                setInvoiceLookedUp(userInvoice.getInvoiceLookedUp());
            }});
            if(null != comInvoice){
                boolean invoiceTitleSame = false;
                if(StringUtils.equals(comInvoice.getId(), innerVoice.getId())) {
                    if(StringUtils.equals(innerVoice.getInvoiceEmail(), userInvoice.getInvoiceEmail()) &&  (innerVoice.getType() == 0)) {
                        invoiceTitleSame = true;
                    }
                } else {
                    invoiceTitleSame =  true;
                }
                if(invoiceTitleSame) {
                    log.debug("发票抬头信息【{}】与原发票抬头信息【{}】一致，无须更新！", JSON.toJSONString(userInvoice), JSON.toJSONString(innerVoice));
                    throw new PreventRepeatException("发票抬头信息与原发票抬头信息一致！");
                }
                //发票税号全部大写
                String tacCoding = userInvoice.getInvoiceTaxCoding();
                if(StringUtils.isNotBlank(tacCoding)){
                    userInvoice.setInvoiceTaxCoding(tacCoding.toUpperCase());
                }
            }
            updateAddress = userInvoice;
        }
        updateWrapper.eq("id", userInvoice.getId());
        Integer i = userInvoiceMapper.update(updateAddress, updateWrapper);
        return true;
    }


    /**
     * 删除
     * @author sunx
     * @since 2021-05-25
     * @param userInvoice:
     * @return      java.util.List<com.entity.UserInvoice>
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserInvoice userInvoice) {
        UserInvoice deleteInvoice = getOneEnhance(userInvoice);
        if(null == deleteInvoice){
            log.debug("发票抬头序列：{}，库里面不存在！",userInvoice.getId());
            return true;
        }
        //判断发票抬头与发票服务关联是否存在关联关系
        UserInvoiceServiceRelation relation = new UserInvoiceServiceRelation();
        relation.setUserInvoiceId(deleteInvoice.getId());
        if(relationService.countEnhance(relation) > 0){
            boolean dealResult = relationService.removeEnhance(relation);
            if(!dealResult){
                throw new BusinessException("发票抬头与发票服务关联关系解除失败！");
            }
        }
        QueryWrapper<UserInvoice> queryWrapper = new QueryWrapper<>(userInvoice);
        Integer i = userInvoiceMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 增强查询条件
     *
     * @author sunx
     * @since 2021-05-25
     * @param userInvoice:
     * @param queryWrapper:
     * @return      void
     */
    private void query(UserInvoice userInvoice, QueryWrapper<UserInvoice> queryWrapper) {
        /**
         * 排序
         */
        if(userInvoice.getCollation() != null && StringUtils.isNotBlank(userInvoice.getCollationFields())){
            if(userInvoice.getCollation()){
                queryWrapper.orderByAsc(userInvoice.getCollationFields());
            }else{
                queryWrapper.orderByDesc("state").orderByDesc(userInvoice.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc("state").orderByDesc(userInvoice.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(userInvoice.getFields())){
            queryWrapper.select(userInvoice.getFields());
        }

        /**
         * 发票抬头模糊查询
         */
        if(StringUtils.isNotBlank(userInvoice.getQuery())){
            queryWrapper.likeRight("`invoice_looked_up`",userInvoice.getQuery());
        }
    }


    /**
     * 单条，增强返回参数追加
     *
     * @author sunx
     * @since 2021-05-25
     * @param userInvoice:
     *
     * @return      UserInvoice
     */
    private UserInvoice assignment(UserInvoice userInvoice) {
        return userInvoice;
    }

    /**
     * 分页,增强返回参数追加
     *
     * @author sunx
     * @since 2021-05-25
     * @param userInvoiceList:
     *
     * @return      UserInvoice
     */
    private IPage assignment(IPage<UserInvoice> userInvoiceList) {
        userInvoiceList.getRecords().forEach(userInvoice -> {
        });
        return userInvoiceList;
    }


    /**
     * 集合,增强返回参数追加
     *
     * @author sunx
     * @since 2021-05-25
     * @param userInvoiceList:
     *
     * @return      UserInvoice
     */
    private List<UserInvoice> assignment(List<UserInvoice> userInvoiceList) {
        userInvoiceList.forEach(userInvoice -> {
        });
        return userInvoiceList;
    }
}
