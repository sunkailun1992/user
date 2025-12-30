package com.gb.user.service.results;

import com.gb.user.entity.TeamGroupValue;
import com.gb.user.entity.vo.TeamGroupValueVO;
import com.gb.user.entity.bo.TeamGroupValueBO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.gb.utils.GeneralConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * TODO 团队组别值,Service返回实现
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValueServiceResults
 * @time 2022-08-31 10:59:01
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamGroupValueServiceResults {


    /**
     * TODO 单条，增强返回参数追加
     *
     * @param teamGroupValueVO 团队组别值
     * @return TeamGroupValueVO
     * @author sunx
     * @methodName assignment
     * @time 2022-08-31 10:59:01
     */
    public TeamGroupValueVO assignment(TeamGroupValueVO teamGroupValueVO) {
        if (teamGroupValueVO != null) {
            return teamGroupValueVO;
        } else {
            return teamGroupValueVO;
        }
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param teamGroupValueVOList 团队组别值
     * @return Page<TeamGroupValueVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-08-31 10:59:01
     */
    public Page<TeamGroupValueVO> assignment(Page<TeamGroupValueVO> teamGroupValueVOList) {
        teamGroupValueVOList.getRecords().forEach(teamGroupValueVO -> {
        });
        return teamGroupValueVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param teamGroupValueVOList 团队组别值
     * @return List<TeamGroupValueVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-08-31 10:59:01
     */
    public List<TeamGroupValueVO> assignment(List<TeamGroupValueVO> teamGroupValueVOList) {
        teamGroupValueVOList.forEach(teamGroupValueVO -> {
        });
        return teamGroupValueVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 团队组别值
     * @return Page<TeamGroupValueVO>
     * @author sunx
     * @methodName toPageVO
     * @time 2022-08-31 10:59:01
     */
    public Page<TeamGroupValueVO> toPageVO(Page<TeamGroupValue> pageDO) {
        Page<TeamGroupValueVO> pageVO = new Page<TeamGroupValueVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), TeamGroupValueVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}