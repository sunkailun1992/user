package com.gb.user.service.results;

import com.gb.user.entity.TeamGroupValueLimit;
import com.gb.user.entity.vo.TeamGroupValueLimitVO;
import com.gb.user.entity.bo.TeamGroupValueLimitBO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.gb.utils.GeneralConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * TODO 团队组别限制,Service返回实现
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValueLimitServiceResults
 * @time 2022-08-31 10:59:01
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamGroupValueLimitServiceResults {


    /**
     * TODO 单条，增强返回参数追加
     *
     * @param teamGroupValueLimitVO 团队组别限制
     * @return TeamGroupValueLimitVO
     * @author sunx
     * @methodName assignment
     * @time 2022-08-31 10:59:01
     */
    public TeamGroupValueLimitVO assignment(TeamGroupValueLimitVO teamGroupValueLimitVO) {
        if (teamGroupValueLimitVO != null) {
            return teamGroupValueLimitVO;
        } else {
            return teamGroupValueLimitVO;
        }
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param teamGroupValueLimitVOList 团队组别限制
     * @return Page<TeamGroupValueLimitVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-08-31 10:59:01
     */
    public Page<TeamGroupValueLimitVO> assignment(Page<TeamGroupValueLimitVO> teamGroupValueLimitVOList) {
        teamGroupValueLimitVOList.getRecords().forEach(teamGroupValueLimitVO -> {
        });
        return teamGroupValueLimitVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param teamGroupValueLimitVOList 团队组别限制
     * @return List<TeamGroupValueLimitVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-08-31 10:59:01
     */
    public List<TeamGroupValueLimitVO> assignment(List<TeamGroupValueLimitVO> teamGroupValueLimitVOList) {
        teamGroupValueLimitVOList.forEach(teamGroupValueLimitVO -> {
        });
        return teamGroupValueLimitVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 团队组别限制
     * @return Page<TeamGroupValueLimitVO>
     * @author sunx
     * @methodName toPageVO
     * @time 2022-08-31 10:59:01
     */
    public Page<TeamGroupValueLimitVO> toPageVO(Page<TeamGroupValueLimit> pageDO) {
        Page<TeamGroupValueLimitVO> pageVO = new Page<TeamGroupValueLimitVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), TeamGroupValueLimitVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}