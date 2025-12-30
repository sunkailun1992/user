package com.gb.user.service.results;

import com.gb.user.entity.TeamGroup;
import com.gb.user.entity.vo.TeamGroupVO;
import com.gb.user.entity.bo.TeamGroupBO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.gb.utils.GeneralConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * TODO 团队组别,Service返回实现
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupServiceResults
 * @time 2022-08-31 10:59:01
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamGroupServiceResults {


    /**
     * TODO 单条，增强返回参数追加
     *
     * @param teamGroupVO 团队组别
     * @return TeamGroupVO
     * @author sunx
     * @methodName assignment
     * @time 2022-08-31 10:59:01
     */
    public TeamGroupVO assignment(TeamGroupVO teamGroupVO) {
        if (teamGroupVO != null) {
            return teamGroupVO;
        } else {
            return teamGroupVO;
        }
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param teamGroupVOList 团队组别
     * @return Page<TeamGroupVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-08-31 10:59:01
     */
    public Page<TeamGroupVO> assignment(Page<TeamGroupVO> teamGroupVOList) {
        teamGroupVOList.getRecords().forEach(teamGroupVO -> {
        });
        return teamGroupVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param teamGroupVOList 团队组别
     * @return List<TeamGroupVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-08-31 10:59:01
     */
    public List<TeamGroupVO> assignment(List<TeamGroupVO> teamGroupVOList) {
        teamGroupVOList.forEach(teamGroupVO -> {
        });
        return teamGroupVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 团队组别
     * @return Page<TeamGroupVO>
     * @author sunx
     * @methodName toPageVO
     * @time 2022-08-31 10:59:01
     */
    public Page<TeamGroupVO> toPageVO(Page<TeamGroup> pageDO) {
        Page<TeamGroupVO> pageVO = new Page<TeamGroupVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), TeamGroupVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}