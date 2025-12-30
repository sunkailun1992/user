package com.gb.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.user.entity.Team;
import com.gb.user.entity.TeamTreeNode;
import com.gb.user.entity.bo.TeamBO;
import com.gb.user.entity.query.TeamQuery;
import com.gb.user.entity.vo.TeamVO;

import java.util.List;


/**
 * TODO 团队，Service服务接口层
 * 代码生成器
 *
 * @author sunx
 * @className TeamService
 * @time 2022-08-30 04:44:17
 */
public interface TeamService extends IService<Team> {


    /**
     * TODO 分页
     *
     * @param page
     * @param teamQuery 团队
     * @return Page<TeamVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-08-30 04:44:17
     */
    Page<TeamVO> pageEnhance(Page page, TeamQuery teamQuery);


    /**
     * TODO 集合
     *
     * @param teamQuery 团队
     * @return List<TeamVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-08-30 04:44:17
     */
    List<TeamVO> listEnhance(TeamQuery teamQuery);


    /**
     * TODO 权限团队集合查询
     *
     * @param authUserId 权限用户Id
     * @param nameQuery 团队名称模糊查询
     * @param teamLevelQuery 团队本级及下级查询
     * @return List<TeamVO>
     * @author sunx
     * @methodName teamAuthSelect
     * @time 2022-08-30 04:44:17
     */
    List<TeamVO> teamAuthSelect(String authUserId, String nameQuery, Boolean teamLevelQuery);


    /**
     * TODO 单条
     *
     * @param teamQuery 团队
     * @return TeamVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-08-30 04:44:17
     */
    TeamVO getOneEnhance(TeamQuery teamQuery);


    /**
     * TODO 总数
     *
     * @param teamQuery 团队
     * @return Long
     * @author sunx
     * @methodName countEnhance
     * @time 2022-08-30 04:44:17
     */
    Long countEnhance(TeamQuery teamQuery);


    /**
     * TODO 新增
     *
     * @param teamBO 团队
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-08-30 04:44:17
     */
    String saveEnhance(TeamBO teamBO);


    /**
     * TODO 修改
     *
     * @param teamBO 团队
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-08-30 04:44:17
     */
    Boolean updateEnhance(TeamBO teamBO);


    /**
     * TODO 删除
     *
     * @param teamBO 团队
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-08-30 04:44:17
     */
    Boolean removeEnhance(TeamBO teamBO);

    /**
     * TODO 团队树
     *
     * @param advanceSelected 团队树查询
     * @param teamId 团队ID
     * @return List<TreeNode>
     * @author sunx
     * @methodName teamTree
     * @time 2022-08-30 04:44:17
     */
    List<TeamTreeNode> teamTree(Boolean advanceSelected, String teamId);

    /**
     * TODO 父级团队查询
     *
     * @param teamId 团队ID
     * @return TreeNode
     * @author sunx
     * @methodName queryParentTeam
     * @time 2022-08-30 04:44:17
     */
    TeamTreeNode queryParentTeam(String teamId);



    /**
     * TODO 团队本级及下级查询
     *
     * @param levelQuery 是否下级查询
     * @param teamId 团队ID
     * @param teamNameQuery 团队名称模糊查询
     * @param teamIdList 父级团队ID列表
     * @return List<TeamVO>
     * @author sunx
     * @methodName teamLevelQuery
     * @time 2022-08-30 04:44:17
     */
    List<TeamVO> teamLevelQuery(Boolean levelQuery, String teamId, String teamNameQuery, List<String> teamIdList);
}
