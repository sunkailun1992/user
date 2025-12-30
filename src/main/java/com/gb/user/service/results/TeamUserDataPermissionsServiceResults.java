package com.gb.user.service.results;

import com.gb.user.entity.TeamUserDataPermissions;
import com.gb.user.entity.vo.TeamUserDataPermissionsVO;
import com.gb.user.entity.bo.TeamUserDataPermissionsBO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.gb.utils.GeneralConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * TODO 团队人员数据权限,Service返回实现
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserDataPermissionsServiceResults
 * @time 2022-08-30 04:44:18
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class TeamUserDataPermissionsServiceResults {


    /**
     * TODO 单条，增强返回参数追加
     *
     * @param teamUserDataPermissionsVO 团队人员数据权限
     * @return TeamUserDataPermissionsVO
     * @author sunx
     * @methodName assignment
     * @time 2022-08-30 04:44:18
     */
    public TeamUserDataPermissionsVO assignment(TeamUserDataPermissionsVO teamUserDataPermissionsVO) {
        if (teamUserDataPermissionsVO != null) {
            return teamUserDataPermissionsVO;
        } else {
            return teamUserDataPermissionsVO;
        }
    }


    /**
     * TODO 分页，增强返回参数追加
     *
     * @param teamUserDataPermissionsVOList 团队人员数据权限
     * @return Page<TeamUserDataPermissionsVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-08-30 04:44:18
     */
    public Page<TeamUserDataPermissionsVO> assignment(Page<TeamUserDataPermissionsVO> teamUserDataPermissionsVOList) {
        teamUserDataPermissionsVOList.getRecords().forEach(teamUserDataPermissionsVO -> {
        });
        return teamUserDataPermissionsVOList;
    }


    /**
     * TODO 集合，增强返回参数追加
     *
     * @param teamUserDataPermissionsVOList 团队人员数据权限
     * @return List<TeamUserDataPermissionsVO>
     * @author sunx
     * @methodName assignment
     * @time 2022-08-30 04:44:18
     */
    public List<TeamUserDataPermissionsVO> assignment(List<TeamUserDataPermissionsVO> teamUserDataPermissionsVOList) {
        teamUserDataPermissionsVOList.forEach(teamUserDataPermissionsVO -> {
        });
        return teamUserDataPermissionsVOList;
    }


    /**
     * TODO DO转化VO
     *
     * @param pageDO 团队人员数据权限
     * @return Page<TeamUserDataPermissionsVO>
     * @author sunx
     * @methodName toPageVO
     * @time 2022-08-30 04:44:18
     */
    public Page<TeamUserDataPermissionsVO> toPageVO(Page<TeamUserDataPermissions> pageDO) {
        Page<TeamUserDataPermissionsVO> pageVO = new Page<TeamUserDataPermissionsVO>();
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), TeamUserDataPermissionsVO.class));
        pageVO.setCurrent(pageDO.getCurrent());
        pageVO.setPages(pageDO.getPages());
        pageVO.setSize(pageDO.getSize());
        pageVO.setTotal(pageDO.getTotal());
        return pageVO;
    }
}