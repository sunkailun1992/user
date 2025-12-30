package com.gb.user.service;

import com.gb.user.entity.query.TeamGroupValueLimitQuery;
import com.gb.user.entity.vo.TeamGroupValueLimitVO;
import com.gb.user.entity.bo.TeamGroupValueLimitBO;
import com.gb.user.entity.TeamGroupValueLimit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * TODO 团队组别限制，Service服务接口层
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValueLimitService
 * @time 2022-08-31 10:59:01
 */
public interface TeamGroupValueLimitService extends IService<TeamGroupValueLimit> {


    /**
     * TODO 分页
     *
     * @param page
     * @param teamGroupValueLimitQuery 团队组别限制
     * @return Page<TeamGroupValueLimitVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-08-31 10:59:01
     */
    Page<TeamGroupValueLimitVO> pageEnhance(Page page, TeamGroupValueLimitQuery teamGroupValueLimitQuery);


    /**
     * TODO 集合
     *
     * @param teamGroupValueLimitQuery 团队组别限制
     * @return List<TeamGroupValueLimitVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-08-31 10:59:01
     */
    List<TeamGroupValueLimitVO> listEnhance(TeamGroupValueLimitQuery teamGroupValueLimitQuery);


    /**
     * TODO 单条
     *
     * @param teamGroupValueLimitQuery 团队组别限制
     * @return TeamGroupValueLimitVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-08-31 10:59:01
     */
    TeamGroupValueLimitVO getOneEnhance(TeamGroupValueLimitQuery teamGroupValueLimitQuery);


    /**
     * TODO 总数
     *
     * @param teamGroupValueLimitQuery 团队组别限制
     * @return Long
     * @author sunx
     * @methodName countEnhance
     * @time 2022-08-31 10:59:01
     */
    Long countEnhance(TeamGroupValueLimitQuery teamGroupValueLimitQuery);


    /**
     * TODO 新增
     *
     * @param teamGroupValueLimitBO 团队组别限制
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-08-31 10:59:01
     */
    String saveEnhance(TeamGroupValueLimitBO teamGroupValueLimitBO);


    /**
     * TODO 修改
     *
     * @param teamGroupValueLimitBO 团队组别限制
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-08-31 10:59:01
     */
    Boolean updateEnhance(TeamGroupValueLimitBO teamGroupValueLimitBO);


    /**
     * TODO 删除
     *
     * @param teamGroupValueLimitBO 团队组别限制
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-08-31 10:59:01
     */
    Boolean removeEnhance(TeamGroupValueLimitBO teamGroupValueLimitBO);
}
