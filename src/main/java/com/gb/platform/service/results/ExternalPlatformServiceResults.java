package com.gb.platform.service.results;

import com.gb.platform.entity.ExternalPlatform;
import com.gb.platform.entity.vo.ExternalPlatformVO;
import com.gb.platform.entity.bo.ExternalPlatformBO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.gb.utils.GeneralConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * TODO 外部平台,Service返回实现
 * 代码生成器
 *
 * @author sunx
 * @className ExternalPlatformServiceResults
 * @time 2022-12-16 03:10:07
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class ExternalPlatformServiceResults {


    /**
     * TODO 单条，增强返回参数追加
     *
     * @param externalPlatformVO 外部平台
     * @return ExternalPlatformVO
     * @author sunx
     * @methodName assignment
     * @time 2022-12-16 03:10:07
     */
    public ExternalPlatformVO assignment(ExternalPlatformVO externalPlatformVO) {
        if (externalPlatformVO != null) {
            return externalPlatformVO;
        } else {
            return externalPlatformVO;
        }
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param externalPlatformVOList 外部平台
     * @return Page<ExternalPlatformVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-12-16 03:10:07
     */
    public Page<ExternalPlatformVO> assignment(Page<ExternalPlatformVO> externalPlatformVOList) {
        externalPlatformVOList.getRecords().forEach(externalPlatformVO -> {
        });
        return externalPlatformVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param externalPlatformVOList 外部平台
     * @return List<ExternalPlatformVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-12-16 03:10:07
     */
    public List<ExternalPlatformVO> assignment(List<ExternalPlatformVO> externalPlatformVOList) {
        externalPlatformVOList.forEach(externalPlatformVO -> {
        });
        return externalPlatformVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 外部平台
     * @return Page<ExternalPlatformVO>
     * @author sunx
     * @methodName toPageVO
     * @time 2022-12-16 03:10:07
     */
    public Page<ExternalPlatformVO> toPageVO(Page<ExternalPlatform> pageDO) {
        Page<ExternalPlatformVO> pageVO = new Page<ExternalPlatformVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), ExternalPlatformVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}