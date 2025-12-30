package com.gb.user.service.results;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.user.entity.Team;
import com.gb.user.entity.vo.TeamVO;
import com.gb.user.mapper.TeamUserMapper;
import com.gb.user.service.TeamService;
import com.gb.utils.GeneralConvertor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * TODO 团队,Service返回实现
 * 代码生成器
 *
 * @author sunx
 * @className TeamServiceResults
 * @time 2022-08-30 04:44:17
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamServiceResults {

    /**
     * 团队人员
     */
    private TeamUserMapper teamUserMapper;

    /**
     * 团队人员
     */
    private TeamService teamService;

    /**
     * TODO 单条，增强返回参数追加
     *
     * @param teamVO 团队
     * @return TeamVO
     * @author sunx
     * @methodName assignment
     * @time 2022-08-30 04:44:17
     */
    public TeamVO assignment(TeamVO teamVO) {
        if (teamVO != null) {
            return teamVO;
        } else {
            return teamVO;
        }
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param teamVOList 团队
     * @return Page<TeamVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-08-30 04:44:17
     */
    public Page<TeamVO> assignment(Page<TeamVO> teamVOList) {
        if(Objects.isNull(teamVOList) || CollectionUtils.isEmpty(teamVOList.getRecords())) {
            return teamVOList;
        }
        teamVOList.getRecords().forEach(t -> {
            t.setChildren(teamService.teamTree(false, t.getId()));
        });
        return teamVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param teamVOList 团队
     * @return List<TeamVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-08-30 04:44:17
     */
    public List<TeamVO> assignment(List<TeamVO> teamVOList) {
        if(CollectionUtils.isEmpty(teamVOList)) {
            return teamVOList;
        }
        //计算每个团队下的人员总数【包括子团队】
        teamVOList.forEach(teamVO -> {
        });
        return teamVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 团队
     * @return Page<TeamVO>
     * @author sunx
     * @methodName toPageVO
     * @time 2022-08-30 04:44:17
     */
    public Page<TeamVO> toPageVO(Page<Team> pageDO) {
        Page<TeamVO> pageVO = new Page<TeamVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), TeamVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}