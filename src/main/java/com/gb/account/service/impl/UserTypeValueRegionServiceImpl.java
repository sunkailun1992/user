package com.gb.account.service.impl;

import cn.hutool.core.convert.Convert;
import com.gb.account.entity.query.UserTypeValueRegionQuery;
import com.gb.account.entity.vo.UserTypeValueRegionVO;
import com.gb.account.entity.bo.UserTypeValueRegionBO;
import com.gb.account.entity.UserTypeValueRegion;
import com.gb.account.mapper.UserTypeValueRegionMapper;
import com.gb.account.service.UserTypeValueRegionService;
import com.gb.account.service.query.UserTypeValueRegionServiceQuery;
import com.gb.account.service.results.UserTypeValueRegionServiceResults;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.rpc.component.RpcComponent;
import com.gb.utils.constants.UniversalConstant;
import com.gb.utils.exception.BusinessException;
import org.apache.commons.collections.MapUtils;
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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.gb.rpc.enums.RpcTypeEnum.*;


/**
 * TODO 用户类型值地区，Service服务实现层
 * 代码生成器
 *
 * @author lijh
 * @className UserTypeValueRegionServiceImpl
 * @time 2022-07-12 11:45:19
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class UserTypeValueRegionServiceImpl extends ServiceImpl<UserTypeValueRegionMapper, UserTypeValueRegion> implements UserTypeValueRegionService {


    /**
     * 用户类型值地区
     */
    private UserTypeValueRegionMapper userTypeValueRegionMapper;


    /**
     * 用户类型值地区
     */
    private UserTypeValueRegionServiceResults userTypeValueRegionServiceResults;


    /**
     * 用户类型值地区增强条件
     */
    private UserTypeValueRegionServiceQuery userTypeValueRegionServiceQuery;

    private RpcComponent rpcComponent;


    /**
     * TODO 集合
     *
     * @param userTypeValueRegionQuery 用户类型值地区
     * @return List<UserTypeValueRegionVO>
     * @author lijh
     * @methodName listEnhance
     * @time 2022-07-12 11:45:19
     */
    @Override
    public List<UserTypeValueRegionVO> listEnhance(UserTypeValueRegionQuery userTypeValueRegionQuery) {
        UserTypeValueRegion userTypeValueRegion = GeneralConvertor.convertor(userTypeValueRegionQuery, UserTypeValueRegion.class);
        QueryWrapper<UserTypeValueRegion> queryWrapper = new QueryWrapper<>(userTypeValueRegion);
        // TODO 自动生成查询，禁止手动写语句
        userTypeValueRegionServiceQuery.query(userTypeValueRegionQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeValueRegionQuery, queryWrapper);
        // DO数据
        List<UserTypeValueRegion> userTypeValueRegionDO = userTypeValueRegionMapper.selectList(queryWrapper);
        // VO数据
        List<UserTypeValueRegionVO> userTypeValueRegionVO = GeneralConvertor.convertor(userTypeValueRegionDO, UserTypeValueRegionVO.class);
        // 判断是否增强
        if (userTypeValueRegionQuery.getAssignment() == null) {
            return userTypeValueRegionServiceResults.assignment(userTypeValueRegionVO);
        } else {
            return userTypeValueRegionQuery.getAssignment() ? userTypeValueRegionServiceResults.assignment(userTypeValueRegionVO) : userTypeValueRegionVO;
        }
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param userTypeValueRegionQuery 用户类型值地区
     * @return Page<UserTypeValueRegionVO>
     * @author lijh
     * @methodName pageEnhance
     * @time 2022-07-12 11:45:19
     */
    @Override
    public Page<UserTypeValueRegionVO> pageEnhance(Page page, UserTypeValueRegionQuery userTypeValueRegionQuery) {
        UserTypeValueRegion userTypeValueRegion = GeneralConvertor.convertor(userTypeValueRegionQuery, UserTypeValueRegion.class);
        QueryWrapper<UserTypeValueRegion> queryWrapper = new QueryWrapper<>(userTypeValueRegion);
        // TODO 自动生成查询，禁止手动写语句
        userTypeValueRegionServiceQuery.query(userTypeValueRegionQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeValueRegionQuery, queryWrapper);
        // DO数据
        Page<UserTypeValueRegion> pageDO = userTypeValueRegionMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<UserTypeValueRegionVO> pageVO = userTypeValueRegionServiceResults.toPageVO(pageDO);
        // 判断是否增强
        if (userTypeValueRegionQuery.getAssignment() == null) {
            return userTypeValueRegionServiceResults.assignment(pageVO);
        } else {
            return userTypeValueRegionQuery.getAssignment() ? userTypeValueRegionServiceResults.assignment(pageVO) : pageVO;
        }
    }


    /**
     * TODO 单条
     *
     * @param userTypeValueRegionQuery 用户类型值地区
     * @return UserTypeValueRegionVO
     * @author lijh
     * @methodName getOneEnhance
     * @time 2022-07-12 11:45:19
     */
    @Override
    public UserTypeValueRegionVO getOneEnhance(UserTypeValueRegionQuery userTypeValueRegionQuery) {
        UserTypeValueRegion userTypeValueRegion = GeneralConvertor.convertor(userTypeValueRegionQuery, UserTypeValueRegion.class);
        QueryWrapper<UserTypeValueRegion> queryWrapper = new QueryWrapper<>(userTypeValueRegion);
        // TODO 自动生成查询，禁止手动写语句
        userTypeValueRegionServiceQuery.query(userTypeValueRegionQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeValueRegionQuery, queryWrapper);
        // DO数据
        UserTypeValueRegion userTypeValueRegionDO = userTypeValueRegionMapper.selectOne(queryWrapper);
        // VO数据
        UserTypeValueRegionVO userTypeValueRegionVO = GeneralConvertor.convertor(userTypeValueRegionDO, UserTypeValueRegionVO.class);
        // 判断是否增强
        if (userTypeValueRegionQuery.getAssignment() == null) {
            return userTypeValueRegionServiceResults.assignment(userTypeValueRegionVO);
        } else {
            return userTypeValueRegionQuery.getAssignment() ? userTypeValueRegionServiceResults.assignment(userTypeValueRegionVO) : userTypeValueRegionVO;
        }
    }


    /**
     * TODO 总数
     *
     * @param userTypeValueRegionQuery 用户类型值地区
     * @return Integer
     * @author lijh
     * @methodName countEnhance
     * @time 2022-07-12 11:45:19
     */
    @Override
    public Long countEnhance(UserTypeValueRegionQuery userTypeValueRegionQuery) {
        UserTypeValueRegion userTypeValueRegion = GeneralConvertor.convertor(userTypeValueRegionQuery, UserTypeValueRegion.class);
        QueryWrapper<UserTypeValueRegion> queryWrapper = new QueryWrapper<>(userTypeValueRegion);
        // TODO 自动生成查询，禁止手动写语句
        userTypeValueRegionServiceQuery.query(userTypeValueRegionQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(userTypeValueRegionQuery, queryWrapper);
        return userTypeValueRegionMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param userTypeValueRegionBO 用户类型值地区
     * @return String
     * @author lijh
     * @methodName saveEnhance
     * @time 2022-07-12 11:45:19
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(UserTypeValueRegionBO userTypeValueRegionBO) {
        //赋值省信息
        if (StringUtils.isNotBlank(userTypeValueRegionBO.getProvinceName())) {
            HashMap<String, String> provinceMap = new HashMap<>(1);
            provinceMap.put("name", userTypeValueRegionBO.getProvinceName());
            Map<String, Object> objMap = rpcComponent.rpcQuery(provinceMap, PROVINCE_GET, Map.class);
            if (MapUtils.isEmpty(objMap)) {
                throw new BusinessException("缺少对应省配置！");
            }
            userTypeValueRegionBO.setProvinceName(Convert.toStr(objMap.get("name")));
            userTypeValueRegionBO.setProvinceCode(Convert.toStr(objMap.get("provinceCode")));
        }
        //赋值市信息
        HashMap<String, String> cityMap = new HashMap<>(1);
        cityMap.put("provinceCode", userTypeValueRegionBO.getProvinceCode());
        if (StringUtils.isNotBlank(userTypeValueRegionBO.getCityName())) {
            cityMap.put("name", userTypeValueRegionBO.getCityName());
        } else {
            cityMap.put("name", "全部市");
        }
        Map<String, Object> objMap = rpcComponent.rpcQuery(cityMap, CITY_GET_ONE, Map.class);
        if (MapUtils.isEmpty(objMap)) {
            throw new BusinessException("缺少对应市配置！");
        }
        userTypeValueRegionBO.setCityName(Convert.toStr(objMap.get("name")));
        userTypeValueRegionBO.setCityCode(Convert.toStr(objMap.get("cityCode")));

        //赋值区信息
        HashMap<String, String> areaMap = new HashMap<>(1);
        areaMap.put("cityCode", userTypeValueRegionBO.getCityCode());
        if (StringUtils.isNotBlank(userTypeValueRegionBO.getAreaName())) {
            areaMap.put("name", userTypeValueRegionBO.getAreaName());
        } else {
            areaMap.put("name", "全部区");
        }
        Map<String, Object> creaObjMap = rpcComponent.rpcQuery(areaMap, AREA_GET_ONE, Map.class);
        if (MapUtils.isEmpty(objMap)) {
            throw new BusinessException("缺少对应市配置！");
        }
        userTypeValueRegionBO.setAreaName(Convert.toStr(creaObjMap.get("name")));
        userTypeValueRegionBO.setAreaCode(Convert.toStr(creaObjMap.get("areaCode")));


        UserTypeValueRegion userTypeValueRegion = GeneralConvertor.convertor(userTypeValueRegionBO, UserTypeValueRegion.class);
        userTypeValueRegionMapper.insert(userTypeValueRegion);
        return userTypeValueRegion.getId();
    }


    /**
     * TODO 修改
     *
     * @param userTypeValueRegionBO 用户类型值地区
     * @return Boolean
     * @author lijh
     * @methodName updateEnhance
     * @time 2022-07-12 11:45:19
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(UserTypeValueRegionBO userTypeValueRegionBO) {
        UserTypeValueRegion userTypeValueRegion = GeneralConvertor.convertor(userTypeValueRegionBO, UserTypeValueRegion.class);
        UpdateWrapper<UserTypeValueRegion> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userTypeValueRegionBO.getId());
        Integer i = userTypeValueRegionMapper.update(userTypeValueRegion, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 删除
     *
     * @param userTypeValueRegionBO 用户类型值地区
     * @return Boolean
     * @author lijh
     * @methodName removeEnhance
     * @time 2022-07-12 11:45:19
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(UserTypeValueRegionBO userTypeValueRegionBO) {
        UserTypeValueRegion userTypeValueRegion = GeneralConvertor.convertor(userTypeValueRegionBO, UserTypeValueRegion.class);
        QueryWrapper<UserTypeValueRegion> queryWrapper = new QueryWrapper<>(userTypeValueRegion);
        Integer i = userTypeValueRegionMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 人工查询条件
     *
     * @param userTypeValueRegionQuery 用户类型值地区
     * @return QueryWrapper
     * @author lijh
     * @methodName queryArtificial
     * @time 2022-07-12 11:45:19
     */
    private QueryWrapper queryArtificial(UserTypeValueRegionQuery userTypeValueRegionQuery, QueryWrapper<UserTypeValueRegion> queryWrapper) {
        //省code
        if (StringUtils.isNotBlank(userTypeValueRegionQuery.getProvinceCode())) {
            queryWrapper.eq("province_code", userTypeValueRegionQuery.getProvinceCode());
        }
        //市code
        if (StringUtils.isNotBlank(userTypeValueRegionQuery.getCityCode())) {
            queryWrapper.eq("city_code", userTypeValueRegionQuery.getCityCode());
        }
        //区code
        if (StringUtils.isNotBlank(userTypeValueRegionQuery.getAreaCode())) {
            queryWrapper.eq("area_code", userTypeValueRegionQuery.getAreaCode());
        }
        return queryWrapper;
    }
}