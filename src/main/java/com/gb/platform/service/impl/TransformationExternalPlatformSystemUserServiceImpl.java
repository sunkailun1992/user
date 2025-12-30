package com.gb.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.account.entity.bo.UserRoleBO;
import com.gb.account.service.UserRoleService;
import com.gb.platform.entity.TransformationExternalPlatformSystemUser;
import com.gb.platform.entity.bo.BatchPlatformSystemUserBO;
import com.gb.platform.entity.bo.ExternalPlatformSystemBO;
import com.gb.platform.entity.bo.TransformationExternalPlatformSystemUserBO;
import com.gb.platform.entity.query.TransformationExternalPlatformSystemQuery;
import com.gb.platform.entity.query.TransformationExternalPlatformSystemUserQuery;
import com.gb.platform.entity.vo.TransformationExternalPlatformSystemUserVO;
import com.gb.platform.entity.vo.TransformationExternalPlatformSystemVO;
import com.gb.platform.mapper.TransformationExternalPlatformSystemUserMapper;
import com.gb.platform.service.TransformationExternalPlatformSystemService;
import com.gb.platform.service.TransformationExternalPlatformSystemUserService;
import com.gb.platform.service.query.TransformationExternalPlatformSystemUserServiceQuery;
import com.gb.platform.service.results.TransformationExternalPlatformSystemUserServiceResults;
import com.gb.user.enums.RoleUserTypeRelatedEnum;
import com.gb.utils.GeneralConvertor;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.ParameterNullException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * TODO 转化外部系统平台用户关联，Service服务实现层
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemUserServiceImpl
 * @time 2022-12-16 03:10:09
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TransformationExternalPlatformSystemUserServiceImpl extends ServiceImpl<TransformationExternalPlatformSystemUserMapper, TransformationExternalPlatformSystemUser> implements TransformationExternalPlatformSystemUserService {


    /**
     * 转化外部系统平台用户关联
     */
    private TransformationExternalPlatformSystemUserMapper transformationExternalPlatformSystemUserMapper;


    /**
     * 转化外部系统平台用户关联
     */
    private TransformationExternalPlatformSystemUserServiceResults transformationExternalPlatformSystemUserServiceResults;


    /**
     * 转化外部系统平台用户关联增强条件
     */
    private TransformationExternalPlatformSystemUserServiceQuery transformationExternalPlatformSystemUserServiceQuery;


    /**
     * 转化外部系统平台
     */
    private TransformationExternalPlatformSystemService transformationExternalPlatformSystemService;


    /**
     * 用户角色表
     */
    private UserRoleService userRoleService;


    /**
     * TODO 集合
     *
     * @param transformationExternalPlatformSystemUserQuery 转化外部系统平台用户关联
     * @return List<TransformationExternalPlatformSystemUserVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-12-16 03:10:09
     */
    @Override
    public List<TransformationExternalPlatformSystemUserVO> listEnhance(TransformationExternalPlatformSystemUserQuery transformationExternalPlatformSystemUserQuery) {
        TransformationExternalPlatformSystemUser transformationExternalPlatformSystemUser = GeneralConvertor.convertor(transformationExternalPlatformSystemUserQuery, TransformationExternalPlatformSystemUser.class);
        QueryWrapper<TransformationExternalPlatformSystemUser> queryWrapper = new QueryWrapper<>(transformationExternalPlatformSystemUser);
        // TODO 自动生成查询，禁止手动写语句
        transformationExternalPlatformSystemUserServiceQuery.query(transformationExternalPlatformSystemUserQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(transformationExternalPlatformSystemUserQuery, queryWrapper);
        // DO数据
        List<TransformationExternalPlatformSystemUser> transformationExternalPlatformSystemUserDO = transformationExternalPlatformSystemUserMapper.selectList(queryWrapper);
        // VO数据
        List<TransformationExternalPlatformSystemUserVO> transformationExternalPlatformSystemUserVO = GeneralConvertor.convertor(transformationExternalPlatformSystemUserDO, TransformationExternalPlatformSystemUserVO.class);
        // 判断是否增强
        if (Objects.nonNull(transformationExternalPlatformSystemUserQuery.getAssignment()) && transformationExternalPlatformSystemUserQuery.getAssignment()) {
            return transformationExternalPlatformSystemUserServiceResults.assignment(transformationExternalPlatformSystemUserVO);
        }
        return transformationExternalPlatformSystemUserVO;
    }


    /**
     * TODO 分页
     *
     * @param page
     * @param transformationExternalPlatformSystemUserQuery 转化外部系统平台用户关联
     * @return Page<TransformationExternalPlatformSystemUserVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-12-16 03:10:09
     */
    @Override
    public Page<TransformationExternalPlatformSystemUserVO> pageEnhance(Page page, TransformationExternalPlatformSystemUserQuery transformationExternalPlatformSystemUserQuery) {
        TransformationExternalPlatformSystemUser transformationExternalPlatformSystemUser = GeneralConvertor.convertor(transformationExternalPlatformSystemUserQuery, TransformationExternalPlatformSystemUser.class);
        QueryWrapper<TransformationExternalPlatformSystemUser> queryWrapper = new QueryWrapper<>(transformationExternalPlatformSystemUser);
        // TODO 自动生成查询，禁止手动写语句
        transformationExternalPlatformSystemUserServiceQuery.query(transformationExternalPlatformSystemUserQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(transformationExternalPlatformSystemUserQuery, queryWrapper);
        // DO数据
        Page<TransformationExternalPlatformSystemUser> pageDO = transformationExternalPlatformSystemUserMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<TransformationExternalPlatformSystemUserVO> pageVO = transformationExternalPlatformSystemUserServiceResults.toPageVO(pageDO);
        // 判断是否增强
        if (Objects.nonNull(transformationExternalPlatformSystemUserQuery.getAssignment()) && transformationExternalPlatformSystemUserQuery.getAssignment()) {
            return transformationExternalPlatformSystemUserServiceResults.assignment(pageVO);
        }
        return pageVO;
    }


    /**
     * TODO 单条
     *
     * @param transformationExternalPlatformSystemUserQuery 转化外部系统平台用户关联
     * @return TransformationExternalPlatformSystemUserVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-12-16 03:10:09
     */
    @Override
    public TransformationExternalPlatformSystemUserVO getOneEnhance(TransformationExternalPlatformSystemUserQuery transformationExternalPlatformSystemUserQuery) {
        TransformationExternalPlatformSystemUser transformationExternalPlatformSystemUser = GeneralConvertor.convertor(transformationExternalPlatformSystemUserQuery, TransformationExternalPlatformSystemUser.class);
        QueryWrapper<TransformationExternalPlatformSystemUser> queryWrapper = new QueryWrapper<>(transformationExternalPlatformSystemUser);
        // TODO 自动生成查询，禁止手动写语句
        transformationExternalPlatformSystemUserServiceQuery.query(transformationExternalPlatformSystemUserQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(transformationExternalPlatformSystemUserQuery, queryWrapper);
        // DO数据
        TransformationExternalPlatformSystemUser transformationExternalPlatformSystemUserDO = transformationExternalPlatformSystemUserMapper.selectOne(queryWrapper);
        // VO数据
        TransformationExternalPlatformSystemUserVO transformationExternalPlatformSystemUserVO = GeneralConvertor.convertor(transformationExternalPlatformSystemUserDO, TransformationExternalPlatformSystemUserVO.class);
        // 判断是否增强
        if (Objects.nonNull(transformationExternalPlatformSystemUserQuery.getAssignment()) && transformationExternalPlatformSystemUserQuery.getAssignment()) {
            return transformationExternalPlatformSystemUserServiceResults.assignment(transformationExternalPlatformSystemUserVO);
        }
        return transformationExternalPlatformSystemUserVO;
    }


    /**
     * TODO 总数
     *
     * @param transformationExternalPlatformSystemUserQuery 转化外部系统平台用户关联
     * @return Integer
     * @author sunx
     * @methodName countEnhance
     * @time 2022-12-16 03:10:09
     */
    @Override
    public Long countEnhance(TransformationExternalPlatformSystemUserQuery transformationExternalPlatformSystemUserQuery) {
        TransformationExternalPlatformSystemUser transformationExternalPlatformSystemUser = GeneralConvertor.convertor(transformationExternalPlatformSystemUserQuery, TransformationExternalPlatformSystemUser.class);
        QueryWrapper<TransformationExternalPlatformSystemUser> queryWrapper = new QueryWrapper<>(transformationExternalPlatformSystemUser);
        // TODO 自动生成查询，禁止手动写语句
        transformationExternalPlatformSystemUserServiceQuery.query(transformationExternalPlatformSystemUserQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(transformationExternalPlatformSystemUserQuery, queryWrapper);
        return transformationExternalPlatformSystemUserMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param transformationExternalPlatformSystemUserBO 转化外部系统平台用户关联
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-12-16 03:10:09
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(TransformationExternalPlatformSystemUserBO transformationExternalPlatformSystemUserBO) {
        TransformationExternalPlatformSystemUser transformationExternalPlatformSystemUser = GeneralConvertor.convertor(transformationExternalPlatformSystemUserBO, TransformationExternalPlatformSystemUser.class);
        transformationExternalPlatformSystemUserMapper.insert(transformationExternalPlatformSystemUser);
        return transformationExternalPlatformSystemUser.getId();
    }


    /**
     * TODO 批量外部系统用户新增
     *
     * @param bo
     * @return void
     * @author 孙凯伦
     * @methodName saveBatchPlateform
     * @time 2023/9/14 11:36
     */
    @Override
    public void saveBatchPlateform(BatchPlatformSystemUserBO bo) {
        log.debug("saveBatchPlateform求参数：{}", JSON.toJSONString(bo));
        //批量关联系统平台
        if (CollectionUtils.isNotEmpty(bo.getExternalPlatformSystemList())) {
            List<ExternalPlatformSystemBO> platformSystemList = bo.getExternalPlatformSystemList().stream().filter(s -> StringUtils.isNotBlank(s.getExternalPlatformCode()) && StringUtils.isNotBlank(s.getExternalSystemCode())).collect(Collectors.collectingAndThen(
                    Collectors.toCollection(() -> new TreeSet<>(
                            Comparator.comparing(p -> String.join("-", p.getExternalSystemCode(), p.getExternalPlatformCode())))), ArrayList::new));
            if (platformSystemList.size() != bo.getExternalPlatformSystemList().size()) {
                throw new BusinessException("存在重复的G端平台系统！");
            }

            //重新关联该用户的外部平台系统
            List<TransformationExternalPlatformSystemUser> list = Lists.newArrayList();
            for (ExternalPlatformSystemBO externalPlatformSystemBO : bo.getExternalPlatformSystemList()) {
                TransformationExternalPlatformSystemVO transformationExternalPlatformSystemVO = transformationExternalPlatformSystemService.getOneEnhance(new TransformationExternalPlatformSystemQuery() {{
                    setExternalSystemCode(externalPlatformSystemBO.getExternalSystemCode());
                    setExternalPlatformCode(externalPlatformSystemBO.getExternalPlatformCode());
                }});
                if (Objects.isNull(transformationExternalPlatformSystemVO)) {
                    log.error("存在未关联G端平台的外部平台系统！externalSystemCode：{}，externalPlatformCode：{}", externalPlatformSystemBO.getExternalSystemCode(), externalPlatformSystemBO.getExternalPlatformCode());
                    throw new ParameterNullException("存在未关联G端平台的外部平台系统！");
                }
                Long i = countEnhance(new TransformationExternalPlatformSystemUserQuery() {{
                    setUserId(bo.getUserId());
                    setTransformationExternalPlatformSystemId(transformationExternalPlatformSystemVO.getId());
                }});
                if (i > 0) {
                    throw new BusinessException("存在重复的G端平台系统！");
                }
                TransformationExternalPlatformSystemUser platformSystemUser = new TransformationExternalPlatformSystemUser();
                platformSystemUser.setUserId(bo.getUserId());
                platformSystemUser.setCreateName(bo.getModifyName());
                platformSystemUser.setModifyName(bo.getModifyName());
                platformSystemUser.setClue(externalPlatformSystemBO.getClue());
                platformSystemUser.setTransformationExternalPlatformSystemId(transformationExternalPlatformSystemVO.getId());
                list.add(platformSystemUser);
            }
            if (CollectionUtils.isNotEmpty(list)) {
                saveBatch(list);
                //关联角色信息【G端平台数据】
                userRoleService.saveEnhance(new UserRoleBO() {{
                    setUserId(bo.getUserId());
                    setRoleCode(RoleUserTypeRelatedEnum.G端平台数据.getRoleCode());
                    setCreateName(bo.getModifyName());
                }});
            }
        } else {
            userRoleService.removeEnhance(new UserRoleBO() {{
                setUserId(bo.getUserId());
                setRoleCode(RoleUserTypeRelatedEnum.G端平台数据.getRoleCode());
            }});
        }
    }


    /**
     * TODO 修改
     *
     * @param transformationExternalPlatformSystemUserBO 转化外部系统平台用户关联
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-12-16 03:10:09
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(TransformationExternalPlatformSystemUserBO transformationExternalPlatformSystemUserBO) {
        if (countEnhance(new TransformationExternalPlatformSystemUserQuery() {{
            setId(transformationExternalPlatformSystemUserBO.getId());
        }}) < 1) {
            throw new ParameterNullException("未找到外部平台系统信息！");
        }
        TransformationExternalPlatformSystemUser transformationExternalPlatformSystemUser = GeneralConvertor.convertor(transformationExternalPlatformSystemUserBO, TransformationExternalPlatformSystemUser.class);
        UpdateWrapper<TransformationExternalPlatformSystemUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", transformationExternalPlatformSystemUserBO.getId());
        Integer i = transformationExternalPlatformSystemUserMapper.update(transformationExternalPlatformSystemUser, updateWrapper);
        return i > 0;
    }


    /**
     * TODO 修改
     *
     * @param transformationExternalPlatformSystemUserBO 转化外部系统平台用户关联
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-12-16 03:10:09
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateUserIdEnhance(TransformationExternalPlatformSystemUserBO transformationExternalPlatformSystemUserBO) {
        TransformationExternalPlatformSystemUser transformationExternalPlatformSystemUser = GeneralConvertor.convertor(transformationExternalPlatformSystemUserBO, TransformationExternalPlatformSystemUser.class);
        UpdateWrapper<TransformationExternalPlatformSystemUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", transformationExternalPlatformSystemUserBO.getUserId());
        Integer i = transformationExternalPlatformSystemUserMapper.update(transformationExternalPlatformSystemUser, updateWrapper);
        return i > 0;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void updateBatchPlateform(BatchPlatformSystemUserBO bo) {
        log.debug("updateBatchPlateform请求参数：{}", JSON.toJSONString(bo));
        //删除该用户下面所有的外部平台系统的关联记录
        removeEnhance(new TransformationExternalPlatformSystemUserBO() {{
            setUserId(bo.getUserId());
        }});
        //批量关联系统平台
        if (CollectionUtils.isNotEmpty(bo.getExternalPlatformSystemList())) {
            List<ExternalPlatformSystemBO> platformSystemList = bo.getExternalPlatformSystemList().stream().filter(s -> StringUtils.isNotBlank(s.getExternalPlatformCode()) && StringUtils.isNotBlank(s.getExternalSystemCode())).collect(Collectors.collectingAndThen(
                    Collectors.toCollection(() -> new TreeSet<>(
                            Comparator.comparing(p -> String.join("-", p.getExternalSystemCode(), p.getExternalPlatformCode())))), ArrayList::new));
            if (platformSystemList.size() != bo.getExternalPlatformSystemList().size()) {
                throw new BusinessException("存在重复的G端平台系统！");
            }

            //重新关联该用户的外部平台系统
            List<TransformationExternalPlatformSystemUser> list = Lists.newArrayList();
            for (ExternalPlatformSystemBO externalPlatformSystemBO : bo.getExternalPlatformSystemList()) {
                TransformationExternalPlatformSystemVO transformationExternalPlatformSystemVO = transformationExternalPlatformSystemService.getOneEnhance(new TransformationExternalPlatformSystemQuery() {{
                    setExternalSystemCode(externalPlatformSystemBO.getExternalSystemCode());
                    setExternalPlatformCode(externalPlatformSystemBO.getExternalPlatformCode());
                }});
                if (Objects.isNull(transformationExternalPlatformSystemVO)) {
                    log.error("存在未关联G端平台的外部平台系统！externalSystemCode：{}，externalPlatformCode：{}", externalPlatformSystemBO.getExternalSystemCode(), externalPlatformSystemBO.getExternalPlatformCode());
                    throw new ParameterNullException("存在未关联G端平台的外部平台系统！");
                }
                TransformationExternalPlatformSystemUser platformSystemUser = new TransformationExternalPlatformSystemUser();
                platformSystemUser.setUserId(bo.getUserId());
                platformSystemUser.setCreateName(bo.getModifyName());
                platformSystemUser.setModifyName(bo.getModifyName());
                platformSystemUser.setClue(externalPlatformSystemBO.getClue());
                platformSystemUser.setTransformationExternalPlatformSystemId(transformationExternalPlatformSystemVO.getId());
                list.add(platformSystemUser);
            }
            if (CollectionUtils.isNotEmpty(list)) {
                saveBatch(list);
                //关联角色信息【G端平台数据】
                userRoleService.saveEnhance(new UserRoleBO() {{
                    setUserId(bo.getUserId());
                    setRoleCode(RoleUserTypeRelatedEnum.G端平台数据.getRoleCode());
                    setCreateName(bo.getModifyName());
                }});
            }
        } else {
            userRoleService.removeEnhance(new UserRoleBO() {{
                setUserId(bo.getUserId());
                setRoleCode(RoleUserTypeRelatedEnum.G端平台数据.getRoleCode());
            }});
        }
    }


    /**
     * TODO 删除
     *
     * @param transformationExternalPlatformSystemUserBO 转化外部系统平台用户关联
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-12-16 03:10:09
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(TransformationExternalPlatformSystemUserBO transformationExternalPlatformSystemUserBO) {
        TransformationExternalPlatformSystemUser transformationExternalPlatformSystemUser = GeneralConvertor.convertor(transformationExternalPlatformSystemUserBO, TransformationExternalPlatformSystemUser.class);
        QueryWrapper<TransformationExternalPlatformSystemUser> queryWrapper = new QueryWrapper<>(transformationExternalPlatformSystemUser);
        Integer i = transformationExternalPlatformSystemUserMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 人工查询条件
     *
     * @param transformationExternalPlatformSystemUserQuery 转化外部系统平台用户关联
     * @return QueryWrapper
     * @author sunx
     * @methodName queryArtificial
     * @time 2022-12-16 03:10:09
     */
    private QueryWrapper queryArtificial(TransformationExternalPlatformSystemUserQuery transformationExternalPlatformSystemUserQuery, QueryWrapper<TransformationExternalPlatformSystemUser> queryWrapper) {
        //外部系统名称左模糊查询
        if (org.apache.commons.lang3.StringUtils.isNotBlank(transformationExternalPlatformSystemUserQuery.getExternalSystemNameQuery())) {
            queryWrapper.inSql("transformation_external_platform_system_id", "select `id` from `transformation_external_platform_system` where `external_system_id` in (select `id` from `external_system` where `name` like '" + transformationExternalPlatformSystemUserQuery.getExternalSystemNameQuery() + "%')");
        }
        //省查询
        if (org.apache.commons.lang3.StringUtils.isNotBlank(transformationExternalPlatformSystemUserQuery.getProvinceCode())) {
            queryWrapper.inSql("transformation_external_platform_system_id", "select `id` from `transformation_external_platform_system` where `province_code` in(" + com.gb.utils.StringUtils.in(transformationExternalPlatformSystemUserQuery.getProvinceCode()) + ")");
        }
        //市查询
        if (org.apache.commons.lang3.StringUtils.isNotBlank(transformationExternalPlatformSystemUserQuery.getCityCode())) {
            queryWrapper.inSql("transformation_external_platform_system_id", "select `id` from `transformation_external_platform_system` where `city_code` in(" + com.gb.utils.StringUtils.in(transformationExternalPlatformSystemUserQuery.getCityCode()) + ")");
        }
        //区查询
        if (org.apache.commons.lang3.StringUtils.isNotBlank(transformationExternalPlatformSystemUserQuery.getAreaCode())) {
            queryWrapper.inSql("transformation_external_platform_system_id", "select `id` from `transformation_external_platform_system` where `area_code` in(" + com.gb.utils.StringUtils.in(transformationExternalPlatformSystemUserQuery.getAreaCode()) + ")");
        }
        return queryWrapper;
    }
}