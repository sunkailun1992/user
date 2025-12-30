package com.gb.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.rpc.component.RpcComponent;
import com.gb.rpc.enums.RpcTypeEnum;
import com.gb.user.entity.UserShippingAddress;
import com.gb.user.entity.bo.AreaBO;
import com.gb.user.mapper.UserShippingAddressMapper;
import com.gb.user.service.UserShippingAddressService;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.ParameterNullException;
import com.gb.utils.exception.PreventRepeatException;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户收货地址 服务实现类
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserShippingAddressServiceImpl extends ServiceImpl<UserShippingAddressMapper, UserShippingAddress> implements UserShippingAddressService {

    /**
     * 用户收货地址
     */
    private UserShippingAddressMapper userShippingAddressMapper;

    private RpcComponent rpcComponent;

    /**
     * 集合条件查询
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddress:
     * @return      java.util.List<com.entity.UserShippingAddress>
     */
    @Override
    public List<UserShippingAddress> listEnhance(UserShippingAddress userShippingAddress) {
        QueryWrapper<UserShippingAddress> queryWrapper = new QueryWrapper<>(userShippingAddress);
        query(userShippingAddress, queryWrapper);
        return assignment(userShippingAddressMapper.selectList(queryWrapper));
    }


    /**
     * 分页条件查询
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddress:
     * @param page:
     * @return      java.util.List<com.entity.UserShippingAddress>
     */
    @Override
    public IPage pageEnhance(Page page, UserShippingAddress userShippingAddress) {
        QueryWrapper<UserShippingAddress> queryWrapper = new QueryWrapper<>(userShippingAddress);
        query(userShippingAddress, queryWrapper);
        return assignment(userShippingAddressMapper.selectPage(page, queryWrapper));
    }


    /**
     * 单条条件查询
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddress:
     * @return      java.util.List<com.entity.UserShippingAddress>
     */
    @Override
    public UserShippingAddress getOneEnhance(UserShippingAddress userShippingAddress) {
        QueryWrapper<UserShippingAddress> queryWrapper = new QueryWrapper<>(userShippingAddress);
        query(userShippingAddress, queryWrapper);
        return assignment(userShippingAddressMapper.selectOne(queryWrapper));
    }


    /**
     * 总数
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddress:
     * @return      java.util.List<com.entity.UserShippingAddress>
     */
    @Override
    public Long countEnhance(UserShippingAddress userShippingAddress) {
        QueryWrapper<UserShippingAddress> queryWrapper = new QueryWrapper<>(userShippingAddress);
        query(userShippingAddress, queryWrapper);
        return userShippingAddressMapper.selectCount(queryWrapper);
    }


    /**
     * 新增
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddress:
     * @return      java.util.List<com.entity.UserShippingAddress>
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean saveEnhance(UserShippingAddress userShippingAddress) throws Exception {
        Long num = countEnhance(new UserShippingAddress(){{
            setUserId(userShippingAddress.getUserId());
            setProvinceCode(userShippingAddress.getProvinceCode());
            setCityCode(userShippingAddress.getCityCode());
            setAreaCode(userShippingAddress.getAreaCode());
            setReceiverAddress(userShippingAddress.getReceiverAddress());
            setReceiverMobile(userShippingAddress.getReceiverMobile());
            setReceiverName(userShippingAddress.getReceiverName());
        }});
        if(num > 0){
            throw new PreventRepeatException("该收件地址已存在！");
        }
        Integer i = userShippingAddressMapper.insert(userShippingAddress);
        if(i == 0){
            throw new BusinessException("收件地址新增失败！");
        }
        return true;
    }


    /**
     * 修改
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddress:
     * @return      java.util.List<com.entity.UserShippingAddress>
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(boolean stateUpdate, UserShippingAddress userShippingAddress) throws Exception {
        //1、判断当前的收件地址序列是否存在
        UserShippingAddress shippingAddress = getOneEnhance(new UserShippingAddress(){{
            setId(userShippingAddress.getId());
        }});
        if(null == shippingAddress){
            log.error("用户对应的该收件地址【id：{}】不存在！", userShippingAddress.getId());
            throw new ParameterNullException("用户对应的该收件地址不存在！");
        }
        UpdateWrapper<UserShippingAddress > updateWrapper = new UpdateWrapper<>();
        UserShippingAddress updateAddress = new UserShippingAddress();
        if(stateUpdate){
            if(userShippingAddress.getState().equals(shippingAddress.getState())){
                log.debug("收件地址状态与被修改的收件地址状态【{}】一致，无须更新！", shippingAddress.getState());
                return true;
            }
            int fxState = (userShippingAddress.getState().equals(1)) ? 0 : 1;
            //2、其他地址全部设置为非默认值-默认值
            updateWrapper.eq("user_id", shippingAddress.getUserId());
            updateAddress.setState(fxState);
            userShippingAddressMapper.update(updateAddress, updateWrapper);
            //3、当前参数设置为默认值
            updateAddress = userShippingAddress;
        }else{
            //2、判断当前用户更新的地址在库里面是否已经存在！
            updateAddress = new UserShippingAddress(){{
                setUserId(shippingAddress.getUserId());
                setProvinceCode(userShippingAddress.getProvinceCode());
                setCityCode(userShippingAddress.getCityCode());
                setAreaCode(userShippingAddress.getAreaCode());
                setReceiverAddress(userShippingAddress.getReceiverAddress());
                setReceiverMobile(userShippingAddress.getReceiverMobile());
                setReceiverName(userShippingAddress.getReceiverName());
            }};
            UserShippingAddress address = getOneEnhance(updateAddress);
            updateAddress.setZipCode(userShippingAddress.getZipCode());
            if(null != address){
                if(!StringUtils.equals(shippingAddress.getId(), address.getId())){
                    throw new PreventRepeatException("该收件地址相关信息已存在！");
                } else {
                    if(StringUtils.equals(address.getZipCode(), updateAddress.getZipCode())){
                        log.debug("收件地址信息【{}】与被修改的收件地址信息【{}】一致，无须更新！", JSON.toJSONString(address), JSON.toJSONString(userShippingAddress));
                        return true;
                    }
                }
            }
            updateAddress.setUserId(null);
            updateAddress.setId(userShippingAddress.getId());
        }

        updateWrapper.eq("id", updateAddress.getId());
        Integer i = userShippingAddressMapper.update(updateAddress, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 删除
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddress:
     * @return      java.util.List<com.entity.UserShippingAddress>
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserShippingAddress userShippingAddress) {
        Long num = countEnhance(userShippingAddress);
        if(num == 0){
            log.debug("收件地址序列：{}，库里面不存在！",userShippingAddress.getId());
            return true;
        }
        QueryWrapper<UserShippingAddress> queryWrapper = new QueryWrapper<>(userShippingAddress);
        Integer i = userShippingAddressMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * 增强查询条件
     *
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddress:
     * @param queryWrapper:
     * @return      void
     */
    private void query(UserShippingAddress userShippingAddress, QueryWrapper<UserShippingAddress> queryWrapper) {
        /**
         * 排序
         */
        if(userShippingAddress.getCollation() != null && StringUtils.isNotBlank(userShippingAddress.getCollationFields())){
            if(userShippingAddress.getCollation()){
                queryWrapper.orderByAsc(userShippingAddress.getCollationFields());
            }else{
                queryWrapper.orderByDesc("state").orderByDesc(userShippingAddress.getCollationFields());
            }
        }else{
            queryWrapper.orderByDesc("state").orderByDesc(userShippingAddress.getCollationFields());
        }

        /**
        * 显示字段
        */
        if(StringUtils.isNotBlank(userShippingAddress.getFields())){
            queryWrapper.select(userShippingAddress.getFields());
        }

        /**
         * 收件地址模糊查询
         */
        if(StringUtils.isNotBlank(userShippingAddress.getQuery())){
            queryWrapper.likeRight("`receiver_address`",userShippingAddress.getQuery());
        }
    }


    /**
     * 单条，增强返回参数追加
     *
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddress:
     *
     * @return      UserShippingAddress
     */
    private UserShippingAddress assignment(UserShippingAddress userShippingAddress) {
        if(Objects.isNull(userShippingAddress)) {
            return userShippingAddress;
        }
        List<UserShippingAddress> userShippingAddressList = assignment(new ArrayList<UserShippingAddress>() {{
            add(userShippingAddress);
        }});
        if(CollectionUtils.isNotEmpty(userShippingAddressList)) {
            return userShippingAddressList.get(0);
        }
        return userShippingAddress;
    }

    /**
     * 分页,增强返回参数追加
     *
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddressList:
     *
     * @return      UserShippingAddress
     */
    private IPage assignment(IPage<UserShippingAddress> userShippingAddressList) {
        if(Objects.isNull(userShippingAddressList)) {
            return userShippingAddressList;
        }
        List<UserShippingAddress>  shippingAddressList = assignment(userShippingAddressList.getRecords());
        userShippingAddressList.setRecords(shippingAddressList);
        return userShippingAddressList;
    }


    /**
     * 集合,增强返回参数追加
     *
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddressList:
     *
     * @return      UserShippingAddress
     */
    private List<UserShippingAddress> assignment(List<UserShippingAddress> userShippingAddressList) {
        if(CollectionUtils.isEmpty(userShippingAddressList)) {
            return userShippingAddressList;
        }
        Map<String, AreaBO> groupAreaMap = queryByAreaMap(userShippingAddressList);
        if(MapUtils.isEmpty(groupAreaMap)) {
            return userShippingAddressList;
        }
        for (UserShippingAddress shipAddress: userShippingAddressList) {
           String key  = shipAddress.getProvinceCode() + "," + shipAddress.getCityCode() + "," + shipAddress.getAreaCode();
           if(groupAreaMap.containsKey(key)) {
               AreaBO bo = groupAreaMap.get(key);
               shipAddress.setAreaName(bo.getAreaName());
               shipAddress.setCityName(bo.getCityName());
               shipAddress.setProvinceName(bo.getProvinceName());
           }
        }
        return userShippingAddressList;
    }

    /**
     * 根据地区列表获取地区中文信息
     *
     * @author sunx
     * @since 2021-05-25
     * @param userShippingAddressList:
     *
     * @return   Map<String, AreaBO>
     */
    private Map<String, AreaBO> queryByAreaMap(List<UserShippingAddress> userShippingAddressList) {
        Map<String,  AreaBO> groupAreaMap = Maps.newHashMap();
        //1、获取地区列表，rpc获取地区对应的中文名称
        List<String> areaList = userShippingAddressList.stream().map(p -> p.getAreaCode()).filter( u -> StringUtils.isNotBlank(u)).distinct().collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(areaList)) {
            List<Map<String, Object>> objList  = rpcComponent.rpcQuery(StringUtils.join(areaList,","), RpcTypeEnum.AREA_GET, List.class);
            //2、对rpc返回结果按照【省市区代码】进行分组
            if(CollectionUtils.isNotEmpty(objList)) {
                List<AreaBO> boList = JSON.parseArray(JSON.toJSONString(objList), AreaBO.class);
                groupAreaMap = boList.stream().collect(Collectors.toMap(area -> area.getProvinceCode() + "," + area.getCityCode() + "," + area.getAreaCode(), data -> data));
            }
        }
        return groupAreaMap;
    }
}
