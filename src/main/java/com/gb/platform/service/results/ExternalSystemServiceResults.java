package com.gb.platform.service.results;

import com.gb.platform.entity.ExternalSystem;
import com.gb.platform.entity.vo.ExternalSystemVO;
import com.gb.platform.entity.bo.ExternalSystemBO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.gb.utils.GeneralConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * TODO 外部系统,Service返回实现
 * 代码生成器
 *
 * @author sunx
 * @className ExternalSystemServiceResults
 * @time 2022-12-16 03:10:08
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class ExternalSystemServiceResults {


    /**
     * TODO 单条，增强返回参数追加
     *
     * @param externalSystemVO 外部系统
     * @return ExternalSystemVO
     * @author sunx
     * @methodName assignment
     * @time 2022-12-16 03:10:08
     */
    public ExternalSystemVO assignment(ExternalSystemVO externalSystemVO) {
        if (externalSystemVO != null) {
            return externalSystemVO;
        } else {
            return externalSystemVO;
        }
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param externalSystemVOList 外部系统
     * @return Page<ExternalSystemVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-12-16 03:10:08
     */
    public Page<ExternalSystemVO> assignment(Page<ExternalSystemVO> externalSystemVOList) {
        externalSystemVOList.getRecords().forEach(externalSystemVO -> {
        });
        return externalSystemVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param externalSystemVOList 外部系统
     * @return List<ExternalSystemVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-12-16 03:10:08
     */
    public List<ExternalSystemVO> assignment(List<ExternalSystemVO> externalSystemVOList) {
        externalSystemVOList.forEach(externalSystemVO -> {
        });
        return externalSystemVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 外部系统
     * @return Page<ExternalSystemVO>
     * @author sunx
     * @methodName toPageVO
     * @time 2022-12-16 03:10:08
     */
    public Page<ExternalSystemVO> toPageVO(Page<ExternalSystem> pageDO) {
        Page<ExternalSystemVO> pageVO = new Page<ExternalSystemVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), ExternalSystemVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}