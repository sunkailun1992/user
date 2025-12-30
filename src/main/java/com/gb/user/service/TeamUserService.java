package com.gb.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.user.entity.TeamUser;
import com.gb.user.entity.bo.TeamUserBO;
import com.gb.user.entity.query.TeamAuthBrokerQuery;
import com.gb.user.entity.query.TeamUserQuery;
import com.gb.user.entity.vo.TeamUserPolicyVO;
import com.gb.user.entity.vo.TeamUserVO;

import java.util.Map;


/**
 * TODO 团队人员，Service服务接口层
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserService
 * @time 2022-08-31 11:01:59
 */
public interface TeamUserService extends IService<TeamUser> {


    /**
     * TODO 分页
     *
     * @param page
     * @param teamUserQuery 团队人员
     * @return Page<TeamUserVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-08-31 11:01:59
     */
    Page<TeamUserVO> pageEnhance(Page page, TeamUserQuery teamUserQuery);


    /**
     * TODO 集合
     *
     * @param teamUserQuery 团队人员
     * @return Object
     * @author sunx
     * @methodName listEnhance
     * @time 2022-08-31 11:01:59
     */
    Object listEnhance(TeamUserQuery teamUserQuery);


    /**
     * TODO 单条
     *
     * @param teamUserQuery 团队人员
     * @return TeamUserVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-08-31 11:01:59
     */
    TeamUserVO getOneEnhance(TeamUserQuery teamUserQuery);


    /**
     * TODO 总数
     *
     * @param teamUserQuery 团队人员
     * @return Long
     * @author sunx
     * @methodName countEnhance
     * @time 2022-08-31 11:01:59
     */
    Long countEnhance(TeamUserQuery teamUserQuery);


    /**
     * TODO 新增
     *
     * @param teamUserBO 团队人员
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-08-31 11:01:59
     */
    String saveEnhance(TeamUserBO teamUserBO);


    /**
     * TODO 修改
     *
     * @param teamUserBO 团队人员
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-08-31 11:01:59
     */
    void updateEnhance(TeamUserBO teamUserBO);


    /**
     * TODO 删除
     *
     * @param teamUserBO 团队人员
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-08-31 11:01:59
     */
    Boolean removeEnhance(TeamUserBO teamUserBO);


    /**
     * TODO 团队权限经纪人分页查询
     *
     * @param page 分页参数
     * @param teamAuthBrokerQuery 团队权限经纪人查询请求参数
     * @return Page<TeamUserPolicyVO>
     * @author sunx
     * @methodName policyPageSelect
     * @time 2022-08-31 11:01:59
     */
    Page<TeamUserPolicyVO> teamAuthBrokerSelect(Page page, TeamAuthBrokerQuery teamAuthBrokerQuery);


    /**
     * TODO 团队权限经纪人列表查询
     *
     * @param teamAuthBrokerQuery 团队权限经纪人查询请求参数
     * @return Object
     * @author sunx
     * @methodName Object
     * @time 2022-08-31 11:01:59
     */
    Object teamAuthBrokerSelectList(TeamAuthBrokerQuery teamAuthBrokerQuery);


    /**
     * 团队权限经纪人统计查询
     * @param teamAuthBrokerQuery 团队权限经纪人统计查询请求参数
     * @return Map<String, Object>
     * @author sunx
     * @methodName queryAuthBrokerCount
     * @time 2022-08-31 11:01:59
     */
    Map<String, Object> queryAuthBrokerCount(TeamAuthBrokerQuery teamAuthBrokerQuery);
}
