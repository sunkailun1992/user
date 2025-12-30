package com.gb.user.service;

import com.gb.user.entity.query.TeamGroupQuery;
import com.gb.user.entity.vo.TeamGroupVO;
import com.gb.user.entity.bo.TeamGroupBO;
import com.gb.user.entity.TeamGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * TODO 团队组别，Service服务接口层
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupService
 * @time 2022-08-31 10:59:01
 */
public interface TeamGroupService extends IService<TeamGroup> {


    /**
     * TODO 分页
     *
     * @param page
     * @param teamGroupQuery 团队组别
     * @return Page<TeamGroupVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-08-31 10:59:01
     */
    Page<TeamGroupVO> pageEnhance(Page page, TeamGroupQuery teamGroupQuery);


    /**
     * TODO 集合
     *
     * @param teamGroupQuery 团队组别
     * @return List<TeamGroupVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-08-31 10:59:01
     */
    List<TeamGroupVO> listEnhance(TeamGroupQuery teamGroupQuery);


    /**
     * TODO 单条
     *
     * @param teamGroupQuery 团队组别
     * @return TeamGroupVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-08-31 10:59:01
     */
    TeamGroupVO getOneEnhance(TeamGroupQuery teamGroupQuery);


    /**
     * TODO 总数
     *
     * @param teamGroupQuery 团队组别
     * @return Long
     * @author sunx
     * @methodName countEnhance
     * @time 2022-08-31 10:59:01
     */
    Long countEnhance(TeamGroupQuery teamGroupQuery);


    /**
     * TODO 新增
     *
     * @param teamGroupBO 团队组别
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-08-31 10:59:01
     */
    String saveEnhance(TeamGroupBO teamGroupBO);


    /**
     * TODO 修改
     *
     * @param teamGroupBO 团队组别
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-08-31 10:59:01
     */
    Boolean updateEnhance(TeamGroupBO teamGroupBO);


    /**
     * TODO 删除
     *
     * @param teamGroupBO 团队组别
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-08-31 10:59:01
     */
    Boolean removeEnhance(TeamGroupBO teamGroupBO);
}
