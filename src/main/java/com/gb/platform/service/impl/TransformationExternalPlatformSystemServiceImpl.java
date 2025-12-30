package com.gb.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.platform.entity.TransformationExternalPlatformSystem;
import com.gb.platform.entity.bo.TransformationExternalPlatformSystemBO;
import com.gb.platform.entity.query.ExternalPlatformQuery;
import com.gb.platform.entity.query.ExternalSystemQuery;
import com.gb.platform.entity.query.TransformationExternalPlatformSystemQuery;
import com.gb.platform.entity.vo.ExternalSystemVO;
import com.gb.platform.entity.vo.TransformationExternalPlatformSystemVO;
import com.gb.platform.mapper.TransformationExternalPlatformSystemMapper;
import com.gb.platform.service.ExternalPlatformService;
import com.gb.platform.service.ExternalSystemService;
import com.gb.platform.service.TransformationExternalPlatformSystemService;
import com.gb.platform.service.query.TransformationExternalPlatformSystemServiceQuery;
import com.gb.platform.service.results.TransformationExternalPlatformSystemServiceResults;
import com.gb.user.constant.RedisConstant;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.constants.UniversalConstant;
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
import java.util.Objects;


/**
 * TODO 转化外部系统平台，Service服务实现层
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemServiceImpl
 * @time 2022-12-16 03:10:08
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TransformationExternalPlatformSystemServiceImpl extends ServiceImpl<TransformationExternalPlatformSystemMapper, TransformationExternalPlatformSystem> implements TransformationExternalPlatformSystemService {


    /**
     * 转化外部系统平台
     */
    private TransformationExternalPlatformSystemMapper transformationExternalPlatformSystemMapper;


    /**
     * 转化外部系统平台
     */
    private TransformationExternalPlatformSystemServiceResults transformationExternalPlatformSystemServiceResults;


    /**
     * 转化外部系统平台增强条件
     */
    private TransformationExternalPlatformSystemServiceQuery transformationExternalPlatformSystemServiceQuery;

    /**
     * 外部系统服务
     */
    private ExternalSystemService externalSystemService;

    /**
     * 外部平台服务
     */
    private ExternalPlatformService externalPlatformService;


    /**
     * TODO 集合
     *
     * @param transformationExternalPlatformSystemQuery 转化外部系统平台
     * @return List<TransformationExternalPlatformSystemVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-12-16 03:10:08
     */
    @Override
    public List<TransformationExternalPlatformSystemVO> listEnhance(TransformationExternalPlatformSystemQuery transformationExternalPlatformSystemQuery) {
        TransformationExternalPlatformSystem transformationExternalPlatformSystem = GeneralConvertor.convertor(transformationExternalPlatformSystemQuery, TransformationExternalPlatformSystem.class);
        QueryWrapper<TransformationExternalPlatformSystem> queryWrapper = new QueryWrapper<>(transformationExternalPlatformSystem);
        // TODO 自动生成查询，禁止手动写语句
        transformationExternalPlatformSystemServiceQuery.query(transformationExternalPlatformSystemQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(transformationExternalPlatformSystemQuery, queryWrapper);
        // DO数据
        List<TransformationExternalPlatformSystem> transformationExternalPlatformSystemDO = transformationExternalPlatformSystemMapper.selectList(queryWrapper);
        // VO数据
        List<TransformationExternalPlatformSystemVO> transformationExternalPlatformSystemVO = GeneralConvertor.convertor(transformationExternalPlatformSystemDO, TransformationExternalPlatformSystemVO.class);
        // 判断是否增强
        if(Objects.nonNull(transformationExternalPlatformSystemQuery.getAssignment()) && transformationExternalPlatformSystemQuery.getAssignment()) {
            return transformationExternalPlatformSystemServiceResults.assignment(transformationExternalPlatformSystemVO);
        }
        return transformationExternalPlatformSystemVO;
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param transformationExternalPlatformSystemQuery 转化外部系统平台
     * @return Page<TransformationExternalPlatformSystemVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-12-16 03:10:08
     */
    @Override
    public Page<TransformationExternalPlatformSystemVO> pageEnhance(Page page, TransformationExternalPlatformSystemQuery transformationExternalPlatformSystemQuery) {
        TransformationExternalPlatformSystem transformationExternalPlatformSystem = GeneralConvertor.convertor(transformationExternalPlatformSystemQuery, TransformationExternalPlatformSystem.class);
        QueryWrapper<TransformationExternalPlatformSystem> queryWrapper = new QueryWrapper<>(transformationExternalPlatformSystem);
        // TODO 自动生成查询，禁止手动写语句
        transformationExternalPlatformSystemServiceQuery.query(transformationExternalPlatformSystemQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(transformationExternalPlatformSystemQuery, queryWrapper);
        // DO数据
        Page<TransformationExternalPlatformSystem> pageDO = transformationExternalPlatformSystemMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<TransformationExternalPlatformSystemVO> pageVO = transformationExternalPlatformSystemServiceResults.toPageVO(pageDO);
        // 判断是否增强
        if(Objects.nonNull(transformationExternalPlatformSystemQuery.getAssignment()) && transformationExternalPlatformSystemQuery.getAssignment()) {
            return transformationExternalPlatformSystemServiceResults.assignment(pageVO);
        }
        return pageVO;
    }


    /**
     * TODO 单条
     *
     * @param transformationExternalPlatformSystemQuery 转化外部系统平台
     * @return TransformationExternalPlatformSystemVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-12-16 03:10:08
     */
    @Override
    public TransformationExternalPlatformSystemVO getOneEnhance(TransformationExternalPlatformSystemQuery transformationExternalPlatformSystemQuery) {
        TransformationExternalPlatformSystem transformationExternalPlatformSystem = GeneralConvertor.convertor(transformationExternalPlatformSystemQuery, TransformationExternalPlatformSystem.class);
        QueryWrapper<TransformationExternalPlatformSystem> queryWrapper = new QueryWrapper<>(transformationExternalPlatformSystem);
        // TODO 自动生成查询，禁止手动写语句
        transformationExternalPlatformSystemServiceQuery.query(transformationExternalPlatformSystemQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(transformationExternalPlatformSystemQuery, queryWrapper);
        // DO数据
        TransformationExternalPlatformSystem transformationExternalPlatformSystemDO = transformationExternalPlatformSystemMapper.selectOne(queryWrapper);
        // VO数据
        TransformationExternalPlatformSystemVO transformationExternalPlatformSystemVO = GeneralConvertor.convertor(transformationExternalPlatformSystemDO, TransformationExternalPlatformSystemVO.class);
        // 判断是否增强
        if (Objects.nonNull(transformationExternalPlatformSystemQuery.getAssignment()) && transformationExternalPlatformSystemQuery.getAssignment()) {
            return transformationExternalPlatformSystemServiceResults.assignment(transformationExternalPlatformSystemVO);
        }
        return transformationExternalPlatformSystemVO;
    }


    /**
     * TODO 总数
     *
     * @param transformationExternalPlatformSystemQuery 转化外部系统平台
     * @return Integer
     * @author sunx
     * @methodName countEnhance
     * @time 2022-12-16 03:10:08
     */
    @Override
    public Long countEnhance(TransformationExternalPlatformSystemQuery transformationExternalPlatformSystemQuery) {
        TransformationExternalPlatformSystem transformationExternalPlatformSystem = GeneralConvertor.convertor(transformationExternalPlatformSystemQuery, TransformationExternalPlatformSystem.class);
        QueryWrapper<TransformationExternalPlatformSystem> queryWrapper = new QueryWrapper<>(transformationExternalPlatformSystem);
        // TODO 自动生成查询，禁止手动写语句
        transformationExternalPlatformSystemServiceQuery.query(transformationExternalPlatformSystemQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(transformationExternalPlatformSystemQuery, queryWrapper);
        return transformationExternalPlatformSystemMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param transformationExternalPlatformSystemBO 转化外部系统平台
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-12-16 03:10:08
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(TransformationExternalPlatformSystemBO transformationExternalPlatformSystemBO) {
        validateParams(transformationExternalPlatformSystemBO);
        TransformationExternalPlatformSystem transformationExternalPlatformSystem = GeneralConvertor.convertor(transformationExternalPlatformSystemBO, TransformationExternalPlatformSystem.class);
        transformationExternalPlatformSystemMapper.insert(transformationExternalPlatformSystem);
        return transformationExternalPlatformSystem.getId();
    }


    /**
     * TODO 修改
     *
     * @param transformationExternalPlatformSystemBO 转化外部系统平台
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-12-16 03:10:08
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(TransformationExternalPlatformSystemBO transformationExternalPlatformSystemBO) {
        validateParams(transformationExternalPlatformSystemBO);
        TransformationExternalPlatformSystem transformationExternalPlatformSystem = GeneralConvertor.convertor(transformationExternalPlatformSystemBO, TransformationExternalPlatformSystem.class);
        UpdateWrapper<TransformationExternalPlatformSystem> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", transformationExternalPlatformSystemBO.getId());
        Integer i = transformationExternalPlatformSystemMapper.update(transformationExternalPlatformSystem, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 删除
     *
     * @param transformationExternalPlatformSystemBO 转化外部系统平台
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-12-16 03:10:08
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(TransformationExternalPlatformSystemBO transformationExternalPlatformSystemBO) {
        TransformationExternalPlatformSystem transformationExternalPlatformSystem = GeneralConvertor.convertor(transformationExternalPlatformSystemBO, TransformationExternalPlatformSystem.class);
        QueryWrapper<TransformationExternalPlatformSystem> queryWrapper = new QueryWrapper<>(transformationExternalPlatformSystem);
        Integer i = transformationExternalPlatformSystemMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 人工查询条件
     *
     * @param transformationExternalPlatformSystemQuery 转化外部系统平台
     * @return QueryWrapper
     * @author sunx
     * @methodName queryArtificial
     * @time 2022-12-16 03:10:08
     */
    private QueryWrapper queryArtificial(TransformationExternalPlatformSystemQuery transformationExternalPlatformSystemQuery, QueryWrapper<TransformationExternalPlatformSystem> queryWrapper) {
        //外部平台名称左模糊查询
        if(StringUtils.isNotBlank(transformationExternalPlatformSystemQuery.getExternalPlatformNameQuery())) {
            queryWrapper.inSql("external_platform_code", "select `code` from `external_platform` where `name` like '" + transformationExternalPlatformSystemQuery.getExternalPlatformNameQuery() + "%'" );
        }
        //外部系统名称左模糊查询
        if(StringUtils.isNotBlank(transformationExternalPlatformSystemQuery.getExternalSystemNameQuery())) {
            queryWrapper.inSql("external_system_id", "select `id` from `external_system` where `name` like '" + transformationExternalPlatformSystemQuery.getExternalSystemNameQuery() + "%'" );
        }
        //crm权限用户id
        if(StringUtils.isNotBlank(transformationExternalPlatformSystemQuery.getAuthUserId())) {
            queryWrapper.inSql("id", "select `transformation_external_platform_system`.`id` from `transformation_external_platform_system_user`,`transformation_external_platform_system` where `transformation_external_platform_system`.`id` = `transformation_external_platform_system_user`.`transformation_external_platform_system_id` and `transformation_external_platform_system_user`.`is_delete` = 0 and `user_id` = '" + transformationExternalPlatformSystemQuery.getAuthUserId() + "'" );
        }
        return queryWrapper;
    }

    /**
     * 参数校验
     * @param transformationExternalPlatformSystemBO 请求参数
     * @author sunx
     */
    private void validateParams(TransformationExternalPlatformSystemBO transformationExternalPlatformSystemBO) {
        if(StringUtils.isNotBlank(transformationExternalPlatformSystemBO.getDescription()) && transformationExternalPlatformSystemBO.getDescription().length() > UniversalConstant.TWO_HUNDRED) {
            throw new BusinessException("备注长度超出范围！");
        }
        if(StringUtils.isNotBlank(transformationExternalPlatformSystemBO.getLinkAddress()) && transformationExternalPlatformSystemBO.getLinkAddress().length() > RedisConstant.ONE_HUNDRED) {
            throw new BusinessException("平台链接长度超出范围！");
        }
        if(StringUtils.isNotBlank(transformationExternalPlatformSystemBO.getExternalPlatformCode())) {
            long num = externalPlatformService.countEnhance(new ExternalPlatformQuery(){{
                setCode(transformationExternalPlatformSystemBO.getExternalPlatformCode());
            }});
            if(num < 1) {
                throw new ParameterNullException("未找到该外部平台信息！");
            }
        }
        if(StringUtils.isNotBlank(transformationExternalPlatformSystemBO.getExternalSystemId()) || StringUtils.isNotBlank(transformationExternalPlatformSystemBO.getExternalSystemCode())) {
            ExternalSystemVO externalSystemVO = externalSystemService.getOneEnhance(new ExternalSystemQuery(){{
                setId(transformationExternalPlatformSystemBO.getExternalSystemId());
                setCode(transformationExternalPlatformSystemBO.getExternalSystemCode());
            }});
            if(Objects.isNull(externalSystemVO)) {
                throw new ParameterNullException("未找到该外部系统信息！");
            }
            transformationExternalPlatformSystemBO.setExternalSystemCode(externalSystemVO.getCode());
            transformationExternalPlatformSystemBO.setExternalSystemId(externalSystemVO.getId());
            TransformationExternalPlatformSystemVO transformationExternalPlatformSystemVO  = getOneEnhance(new TransformationExternalPlatformSystemQuery(){{
                setExternalSystemId(externalSystemVO.getId());
                setExternalPlatformCode(transformationExternalPlatformSystemBO.getExternalPlatformCode());
            }});
            if(Objects.nonNull(transformationExternalPlatformSystemVO)) {
                if(StringUtils.isNotBlank(transformationExternalPlatformSystemBO.getId()) && StringUtils.equals(transformationExternalPlatformSystemVO.getId(), transformationExternalPlatformSystemBO.getId())) {
                    return;
                }
                throw new PreventRepeatException("该平台系统已关联！");
            }
        }
    }
}