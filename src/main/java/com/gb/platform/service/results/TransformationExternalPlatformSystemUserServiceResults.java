package com.gb.platform.service.results;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.platform.entity.TransformationExternalPlatformSystemUser;
import com.gb.platform.entity.query.TransformationExternalPlatformSystemQuery;
import com.gb.platform.entity.vo.TransformationExternalPlatformSystemUserVO;
import com.gb.platform.entity.vo.TransformationExternalPlatformSystemVO;
import com.gb.platform.service.TransformationExternalPlatformSystemService;
import com.gb.utils.GeneralConvertor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * TODO 转化外部系统平台用户关联,Service返回实现
 * 代码生成器
 *
 * @author sunx
 * @className TransformationExternalPlatformSystemUserServiceResults
 * @time 2022-12-16 03:10:09
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TransformationExternalPlatformSystemUserServiceResults {

    private TransformationExternalPlatformSystemService transformationExternalPlatformSystemService;

    /**
     * TODO 单条，增强返回参数追加
     *
     * @param transformationExternalPlatformSystemUserVO 转化外部系统平台用户关联
     * @return TransformationExternalPlatformSystemUserVO
     * @author sunx
     * @methodName assignment
     * @time 2022-12-16 03:10:09
     */
    public TransformationExternalPlatformSystemUserVO assignment(TransformationExternalPlatformSystemUserVO transformationExternalPlatformSystemUserVO) {
        if (Objects.isNull(transformationExternalPlatformSystemUserVO)) {
            return transformationExternalPlatformSystemUserVO;
        }
        TransformationExternalPlatformSystemVO transformationExternalPlatformSystemVO = transformationExternalPlatformSystemService.getOneEnhance(new TransformationExternalPlatformSystemQuery(){{
            setId(transformationExternalPlatformSystemUserVO.getTransformationExternalPlatformSystemId());
        }});
        if(Objects.nonNull(transformationExternalPlatformSystemVO)) {
            transformationExternalPlatformSystemUserVO.setExternalPlatformId(transformationExternalPlatformSystemVO.getExternalPlatformId());
            transformationExternalPlatformSystemUserVO.setExternalPlatformCode(transformationExternalPlatformSystemVO.getExternalPlatformCode());
            transformationExternalPlatformSystemUserVO.setExternalPlatformName(transformationExternalPlatformSystemVO.getExternalPlatformName());
            transformationExternalPlatformSystemUserVO.setExternalSystemName(transformationExternalPlatformSystemVO.getExternalSystemName());
            transformationExternalPlatformSystemUserVO.setExternalSystemId(transformationExternalPlatformSystemVO.getExternalSystemId());
            transformationExternalPlatformSystemUserVO.setExternalSystemCode(transformationExternalPlatformSystemVO.getExternalSystemCode());
            transformationExternalPlatformSystemUserVO.setProvinceCode(transformationExternalPlatformSystemVO.getProvinceCode());
            transformationExternalPlatformSystemUserVO.setProvinceName(transformationExternalPlatformSystemVO.getProvinceName());
            transformationExternalPlatformSystemUserVO.setCityCode(transformationExternalPlatformSystemVO.getCityCode());
            transformationExternalPlatformSystemUserVO.setCityName(transformationExternalPlatformSystemVO.getCityName());
            transformationExternalPlatformSystemUserVO.setAreaCode(transformationExternalPlatformSystemVO.getAreaCode());
            transformationExternalPlatformSystemUserVO.setAreaName(transformationExternalPlatformSystemVO.getAreaName());
        }
        return transformationExternalPlatformSystemUserVO;
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param transformationExternalPlatformSystemUserVOList 转化外部系统平台用户关联
     * @return Page<TransformationExternalPlatformSystemUserVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-12-16 03:10:09
     */
    public Page<TransformationExternalPlatformSystemUserVO> assignment(Page<TransformationExternalPlatformSystemUserVO> transformationExternalPlatformSystemUserVOList) {
        if(Objects.isNull(transformationExternalPlatformSystemUserVOList)) {
            return transformationExternalPlatformSystemUserVOList;
        }
        List<TransformationExternalPlatformSystemUserVO> platformSystemUserVOList = assignment(transformationExternalPlatformSystemUserVOList.getRecords());
        transformationExternalPlatformSystemUserVOList.setRecords(platformSystemUserVOList);
        return transformationExternalPlatformSystemUserVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param transformationExternalPlatformSystemUserVOList 转化外部系统平台用户关联
     * @return List<TransformationExternalPlatformSystemUserVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-12-16 03:10:09
     */
    public List<TransformationExternalPlatformSystemUserVO> assignment(List<TransformationExternalPlatformSystemUserVO> transformationExternalPlatformSystemUserVOList) {
        if(CollectionUtils.isEmpty(transformationExternalPlatformSystemUserVOList)) {
            return transformationExternalPlatformSystemUserVOList;
        }
        transformationExternalPlatformSystemUserVOList.forEach(transformationExternalPlatformSystemUserVO -> {
            assignment(transformationExternalPlatformSystemUserVO);
        });
        return transformationExternalPlatformSystemUserVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 转化外部系统平台用户关联
     * @return Page<TransformationExternalPlatformSystemUserVO>
     * @author sunx
     * @methodName toPageVO
     * @time 2022-12-16 03:10:09
     */
    public Page<TransformationExternalPlatformSystemUserVO> toPageVO(Page<TransformationExternalPlatformSystemUser> pageDO) {
        Page<TransformationExternalPlatformSystemUserVO> pageVO = new Page<TransformationExternalPlatformSystemUserVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), TransformationExternalPlatformSystemUserVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}