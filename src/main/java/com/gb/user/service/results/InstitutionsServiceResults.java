package com.gb.user.service.results;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.user.entity.Institutions;
import com.gb.user.entity.query.InstitutionsExecutivesQuery;
import com.gb.user.entity.vo.InstitutionsExecutivesVO;
import com.gb.user.entity.vo.InstitutionsVO;
import com.gb.user.service.InstitutionsExecutivesService;
import com.gb.utils.GeneralConvertor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * TODO 机构,Service返回实现
 * 代码生成器
 *
 * @author sunxin
 * @className InstitutionsServiceResults
 * @time 2022-07-04 10:48:36
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class InstitutionsServiceResults {

    private InstitutionsExecutivesService institutionsExecutivesService;


    /**
     * TODO 单条，增强返回参数追加
     *
     * @param institutionsVO 机构
     * @return InstitutionsVO
     * @author sunxin
     * @methodName assignment
     * @time 2022-07-04 10:48:36
     */
    public InstitutionsVO assignment(InstitutionsVO institutionsVO) {
        if(Objects.isNull(institutionsVO) || StringUtils.isBlank(institutionsVO.getId())) {
            return institutionsVO;
        }
        List<InstitutionsExecutivesVO> institutionsExecutivesVOList = institutionsExecutivesService.listEnhance(new InstitutionsExecutivesQuery(){{
            setInstitutionsId(institutionsVO.getId());
        }});
        institutionsVO.setInstitutionsExecutivesBOList(institutionsExecutivesVOList);
        return institutionsVO;
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param institutionsVOList 机构
     * @return Page<InstitutionsVO>
     * @author sunxin
     * @methodName assignment
     * @time 2022-07-04 10:48:36
     */
    public Page<InstitutionsVO> assignment(Page<InstitutionsVO> institutionsVOList) {
        institutionsVOList.getRecords().forEach(institutionsVO -> {
        });
        return institutionsVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param institutionsVOList 机构
     * @return List<InstitutionsVO>
     * @author sunxin
     * @methodName assignment
     * @time 2022-07-04 10:48:36
     */
    public List<InstitutionsVO> assignment(List<InstitutionsVO> institutionsVOList) {
        institutionsVOList.forEach(institutionsVO -> {
        });
        return institutionsVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 机构
     * @return Page<InstitutionsVO>
     * @author sunxin
     * @methodName toPageVO
     * @time 2022-07-04 10:48:36
     */
    public Page<InstitutionsVO> toPageVO(Page<Institutions> pageDO) {
        Page<InstitutionsVO> pageVO = new Page<InstitutionsVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), InstitutionsVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}