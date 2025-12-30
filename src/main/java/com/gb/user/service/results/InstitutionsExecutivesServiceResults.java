package com.gb.user.service.results;

import com.gb.user.entity.InstitutionsExecutives;
import com.gb.user.entity.vo.InstitutionsExecutivesVO;
import com.gb.user.entity.bo.InstitutionsExecutivesBO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.gb.utils.GeneralConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * TODO 机构高管,Service返回实现
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsExecutivesServiceResults
 * @time 2022-07-04 10:48:36
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class InstitutionsExecutivesServiceResults {


    /**
     * TODO 单条，增强返回参数追加
     *
     * @param institutionsExecutivesVO 机构高管
     * @return InstitutionsExecutivesVO
     * @author sunxin
     * @methodName assignment
     * @time 2022-07-04 10:48:36
     */
    public InstitutionsExecutivesVO assignment(InstitutionsExecutivesVO institutionsExecutivesVO) {
        if (institutionsExecutivesVO != null) {
            return institutionsExecutivesVO;
        } else {
            return institutionsExecutivesVO;
        }
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param institutionsExecutivesVOList 机构高管
     * @return Page<InstitutionsExecutivesVO>
     * @author sunxin
     * @methodName assignment
     * @time 2022-07-04 10:48:36
     */
    public Page<InstitutionsExecutivesVO> assignment(Page<InstitutionsExecutivesVO> institutionsExecutivesVOList) {
        institutionsExecutivesVOList.getRecords().forEach(institutionsExecutivesVO -> {
        });
        return institutionsExecutivesVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param institutionsExecutivesVOList 机构高管
     * @return List<InstitutionsExecutivesVO>
     * @author sunxin
     * @methodName assignment
     * @time 2022-07-04 10:48:36
     */
    public List<InstitutionsExecutivesVO> assignment(List<InstitutionsExecutivesVO> institutionsExecutivesVOList) {
        institutionsExecutivesVOList.forEach(institutionsExecutivesVO -> {
        });
        return institutionsExecutivesVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 机构高管
     * @return Page<InstitutionsExecutivesVO>
     * @author sunxin
     * @methodName toPageVO
     * @time 2022-07-04 10:48:36
     */
    public Page<InstitutionsExecutivesVO> toPageVO(Page<InstitutionsExecutives> pageDO) {
        Page<InstitutionsExecutivesVO> pageVO = new Page<InstitutionsExecutivesVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), InstitutionsExecutivesVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}