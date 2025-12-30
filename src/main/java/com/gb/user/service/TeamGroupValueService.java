package com.gb.user.service;

import com.gb.user.entity.query.TeamGroupValueQuery;
import com.gb.user.entity.vo.TeamGroupValueVO;
import com.gb.user.entity.bo.TeamGroupValueBO;
import com.gb.user.entity.TeamGroupValue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;


/**
 * TODO 团队组别值，Service服务接口层
 * 代码生成器
 *
 * @author sunx
 * @className TeamGroupValueService
 * @time 2022-08-31 10:59:01
 */
public interface TeamGroupValueService extends IService<TeamGroupValue> {


    /**
     * TODO 分页
     *
     * @param page
     * @param teamGroupValueQuery 团队组别值
     * @return Page<TeamGroupValueVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-08-31 10:59:01
     */
    Page<TeamGroupValueVO> pageEnhance(Page page, TeamGroupValueQuery teamGroupValueQuery);


    /**
     * TODO 集合
     *
     * @param teamGroupValueQuery 团队组别值
     * @return List<TeamGroupValueVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-08-31 10:59:01
     */
    List<TeamGroupValueVO> listEnhance(TeamGroupValueQuery teamGroupValueQuery);


    /**
     * TODO 单条
     *
     * @param teamGroupValueQuery 团队组别值
     * @return TeamGroupValueVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-08-31 10:59:01
     */
    TeamGroupValueVO getOneEnhance(TeamGroupValueQuery teamGroupValueQuery);


    /**
     * TODO 总数
     *
     * @param teamGroupValueQuery 团队组别值
     * @return Long
     * @author sunx
     * @methodName countEnhance
     * @time 2022-08-31 10:59:01
     */
    Long countEnhance(TeamGroupValueQuery teamGroupValueQuery);


    /**
     * TODO 新增
     *
     * @param teamGroupValueBO 团队组别值
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-08-31 10:59:01
     */
    String saveEnhance(TeamGroupValueBO teamGroupValueBO);


    /**
     * TODO 修改
     *
     * @param teamGroupValueBO 团队组别值
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-08-31 10:59:01
     */
    Boolean updateEnhance(TeamGroupValueBO teamGroupValueBO);


    /**
     * TODO 删除
     *
     * @param teamGroupValueBO 团队组别值
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-08-31 10:59:01
     */
    Boolean removeEnhance(TeamGroupValueBO teamGroupValueBO);
}
