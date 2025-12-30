package com.gb.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.platform.entity.ExternalPlatform;
import com.gb.platform.entity.bo.ExternalPlatformBO;
import com.gb.platform.entity.query.ExternalPlatformQuery;
import com.gb.platform.entity.vo.ExternalPlatformGroupVO;
import com.gb.platform.entity.vo.ExternalPlatformVO;
import com.gb.platform.mapper.ExternalPlatformMapper;
import com.gb.platform.service.ExternalPlatformService;
import com.gb.platform.service.query.ExternalPlatformServiceQuery;
import com.gb.platform.service.results.ExternalPlatformServiceResults;
import com.gb.utils.GeneralConvertor;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * TODO 外部平台，Service服务实现层
 * 代码生成器
 *
 * @author sunx
 * @className ExternalPlatformServiceImpl
 * @time 2022-12-16 03:10:07
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class ExternalPlatformServiceImpl extends ServiceImpl<ExternalPlatformMapper, ExternalPlatform> implements ExternalPlatformService {


    /**
     * 外部平台
     */
    private ExternalPlatformMapper externalPlatformMapper;


    /**
     * 外部平台
     */
    private ExternalPlatformServiceResults externalPlatformServiceResults;


    /**
     * 外部平台增强条件
     */
    private ExternalPlatformServiceQuery externalPlatformServiceQuery;


    /**
     * TODO 集合
     *
     * @param externalPlatformQuery 外部平台
     * @return Object
     * @author sunx
     * @methodName listEnhance
     * @time 2022-12-16 03:10:07
     */
    @Override
    public Object listEnhance(ExternalPlatformQuery externalPlatformQuery) {
        ExternalPlatform externalPlatform = GeneralConvertor.convertor(externalPlatformQuery, ExternalPlatform.class);
        QueryWrapper<ExternalPlatform> queryWrapper = new QueryWrapper<>(externalPlatform);
        // TODO 自动生成查询，禁止手动写语句
        externalPlatformServiceQuery.query(externalPlatformQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(externalPlatformQuery, queryWrapper);
        // DO数据
        List<ExternalPlatform> externalPlatformDO = externalPlatformMapper.selectList(queryWrapper);
        // VO数据
        List<ExternalPlatformVO> externalPlatformVO = GeneralConvertor.convertor(externalPlatformDO, ExternalPlatformVO.class);
        //查询结果按照平台进行分组
        if(Objects.nonNull(externalPlatformQuery.getBackResultGroup()) && externalPlatformQuery.getBackResultGroup()) {
            List<ExternalPlatformGroupVO> resultList = Lists.newArrayList();
            if(CollectionUtils.isNotEmpty(externalPlatformVO)) {
                Map<String, List<ExternalPlatformVO>> platformGroupMap = externalPlatformVO.stream().collect(Collectors.groupingBy(ExternalPlatformVO :: getCode));
                for (Map.Entry<String, List<ExternalPlatformVO>> map : platformGroupMap.entrySet()) {
                    List<ExternalPlatformVO> list = map.getValue();
                    ExternalPlatformGroupVO externalPlatformGroupVO = GeneralConvertor.convertor(list.get(0), ExternalPlatformGroupVO.class);
                    externalPlatformGroupVO.setExternalSystemList(Lists.newArrayList());
                    for(ExternalPlatformVO vo : list) {
                        if(StringUtils.isBlank(vo.getExternalSystemCode()) && StringUtils.isBlank(vo.getExternalSystemName())) {
                            continue;
                        }
                        Map<String, String> externalSystemMap = Maps.newHashMap();
                        externalSystemMap.put("externalSystemCode", vo.getExternalSystemCode());
                        externalSystemMap.put("externalSystemName", vo.getExternalSystemName());
                        externalPlatformGroupVO.getExternalSystemList().add(externalSystemMap);
                    }
                    resultList.add(externalPlatformGroupVO);
                }
            }
            return resultList;
        }
                    // 判断是否增强
        if (Objects.nonNull(externalPlatformQuery.getAssignment()) && externalPlatformQuery.getAssignment()) {
            return externalPlatformServiceResults.assignment(externalPlatformVO);
        }
        return externalPlatformVO;

    }


    /**
     * TODO 分页
     *
     * @param page
     * @param externalPlatformQuery 外部平台
     * @return Page<ExternalPlatformVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-12-16 03:10:07
     */
    @Override
    public Page<ExternalPlatformVO> pageEnhance(Page page, ExternalPlatformQuery externalPlatformQuery) {
        ExternalPlatform externalPlatform = GeneralConvertor.convertor(externalPlatformQuery, ExternalPlatform.class);
        QueryWrapper<ExternalPlatform> queryWrapper = new QueryWrapper<>(externalPlatform);
        // TODO 自动生成查询，禁止手动写语句
        externalPlatformServiceQuery.query(externalPlatformQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(externalPlatformQuery, queryWrapper);
        // DO数据
        Page<ExternalPlatform> pageDO = externalPlatformMapper.selectPage(page, queryWrapper);
        // VO数据
        Page<ExternalPlatformVO> pageVO = externalPlatformServiceResults.toPageVO(pageDO);
        // 判断是否增强
        if (externalPlatformQuery.getAssignment() == null) {
            return externalPlatformServiceResults.assignment(pageVO);
        } else {
            return externalPlatformQuery.getAssignment() ? externalPlatformServiceResults.assignment(pageVO) : pageVO;
        }
    }


    /**
     * TODO 单条
     *
     * @param externalPlatformQuery 外部平台
     * @return ExternalPlatformVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-12-16 03:10:07
     */
    @Override
    public ExternalPlatformVO getOneEnhance(ExternalPlatformQuery externalPlatformQuery) {
        ExternalPlatform externalPlatform = GeneralConvertor.convertor(externalPlatformQuery, ExternalPlatform.class);
        QueryWrapper<ExternalPlatform> queryWrapper = new QueryWrapper<>(externalPlatform);
        // TODO 自动生成查询，禁止手动写语句
        externalPlatformServiceQuery.query(externalPlatformQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(externalPlatformQuery, queryWrapper);
        // DO数据
        ExternalPlatform externalPlatformDO = externalPlatformMapper.selectOne(queryWrapper);
        // VO数据
        ExternalPlatformVO externalPlatformVO = GeneralConvertor.convertor(externalPlatformDO, ExternalPlatformVO.class);
        // 判断是否增强
        if (externalPlatformQuery.getAssignment() == null) {
            return externalPlatformServiceResults.assignment(externalPlatformVO);
        } else {
            return externalPlatformQuery.getAssignment() ? externalPlatformServiceResults.assignment(externalPlatformVO) : externalPlatformVO;
        }
    }


    /**
     * TODO 总数
     *
     * @param externalPlatformQuery 外部平台
     * @return Integer
     * @author sunx
     * @methodName countEnhance
     * @time 2022-12-16 03:10:07
     */
    @Override
    public Long countEnhance(ExternalPlatformQuery externalPlatformQuery) {
        ExternalPlatform externalPlatform = GeneralConvertor.convertor(externalPlatformQuery, ExternalPlatform.class);
        QueryWrapper<ExternalPlatform> queryWrapper = new QueryWrapper<>(externalPlatform);
        // TODO 自动生成查询，禁止手动写语句
        externalPlatformServiceQuery.query(externalPlatformQuery, queryWrapper);
        // TODO 人工查询条件
        queryArtificial(externalPlatformQuery, queryWrapper);
        return externalPlatformMapper.selectCount(queryWrapper);
    }


    /**
     * TODO 新增
     *
     * @param externalPlatformBO 外部平台
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-12-16 03:10:07
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public String saveEnhance(ExternalPlatformBO externalPlatformBO) {
        ExternalPlatform externalPlatform = GeneralConvertor.convertor(externalPlatformBO, ExternalPlatform.class);
        externalPlatformMapper.insert(externalPlatform);
        return externalPlatform.getId();
    }


    /**
     * TODO 修改
     *
     * @param externalPlatformBO 外部平台
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-12-16 03:10:07
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean updateEnhance(ExternalPlatformBO externalPlatformBO) {
        ExternalPlatform externalPlatform = GeneralConvertor.convertor(externalPlatformBO, ExternalPlatform.class);
        UpdateWrapper<ExternalPlatform> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", externalPlatformBO.getId());
        Integer i = externalPlatformMapper.update(externalPlatform, updateWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 删除
     *
     * @param externalPlatformBO 外部平台
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-12-16 03:10:07
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Boolean removeEnhance(ExternalPlatformBO externalPlatformBO) {
        ExternalPlatform externalPlatform = GeneralConvertor.convertor(externalPlatformBO, ExternalPlatform.class);
        QueryWrapper<ExternalPlatform> queryWrapper = new QueryWrapper<>(externalPlatform);
        Integer i = externalPlatformMapper.delete(queryWrapper);
        return i > 0 ? true : false;
    }


    /**
     * TODO 人工查询条件
     *
     * @param externalPlatformQuery 外部平台
     * @return QueryWrapper
     * @author sunx
     * @methodName queryArtificial
     * @time 2022-12-16 03:10:07
     */
    private QueryWrapper queryArtificial(ExternalPlatformQuery externalPlatformQuery, QueryWrapper<ExternalPlatform> queryWrapper) {
        if(StringUtils.isNotBlank(externalPlatformQuery.getNameQuery())) {
            queryWrapper.likeRight("name", externalPlatformQuery.getNameQuery());
        }
        return queryWrapper;
    }
}