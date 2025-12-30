package com.gb.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.user.entity.TeamUserDataPermissions;
import com.gb.user.entity.bo.TeamUserDataPermissionsBO;
import com.gb.user.entity.query.TeamUserDataPermissionsQuery;
import com.gb.user.entity.vo.TeamUserDataPermissionsVO;

import java.util.List;


/**
 * TODO 团队人员数据权限，Service服务接口层
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserDataPermissionsService
 * @time 2022-08-30 04:44:18
 */
public interface TeamUserDataPermissionsService extends IService<TeamUserDataPermissions> {


    /**
     * TODO 分页
     *
     * @param page
     * @param teamUserDataPermissionsQuery 团队人员数据权限
     * @return Page<TeamUserDataPermissionsVO>
     * @author sunx
     * @methodName pageEnhance
     * @time 2022-08-30 04:44:18
     */
    Page<TeamUserDataPermissionsVO> pageEnhance(Page page, TeamUserDataPermissionsQuery teamUserDataPermissionsQuery);


    /**
     * TODO 集合
     *
     * @param teamUserDataPermissionsQuery 团队人员数据权限
     * @return List<TeamUserDataPermissionsVO>
     * @author sunx
     * @methodName listEnhance
     * @time 2022-08-30 04:44:18
     */
    List<TeamUserDataPermissionsVO> listEnhance(TeamUserDataPermissionsQuery teamUserDataPermissionsQuery);


    /**
     * TODO 单条
     *
     * @param teamUserDataPermissionsQuery 团队人员数据权限
     * @return TeamUserDataPermissionsVO
     * @author sunx
     * @methodName getOneEnhance
     * @time 2022-08-30 04:44:18
     */
    TeamUserDataPermissionsVO getOneEnhance(TeamUserDataPermissionsQuery teamUserDataPermissionsQuery);


    /**
     * TODO 总数
     *
     * @param teamUserDataPermissionsQuery 团队人员数据权限
     * @return Long
     * @author sunx
     * @methodName countEnhance
     * @time 2022-08-30 04:44:18
     */
    Long countEnhance(TeamUserDataPermissionsQuery teamUserDataPermissionsQuery);


    /**
     * TODO 新增
     *
     * @param teamUserDataPermissionsBO 团队人员数据权限
     * @return String
     * @author sunx
     * @methodName saveEnhance
     * @time 2022-08-30 04:44:18
     */
    String saveEnhance(TeamUserDataPermissionsBO teamUserDataPermissionsBO);


    /**
     * TODO 修改
     *
     * @param teamUserDataPermissionsBO 团队人员数据权限
     * @return Boolean
     * @author sunx
     * @methodName updateEnhance
     * @time 2022-08-30 04:44:18
     */
    Boolean updateEnhance(TeamUserDataPermissionsBO teamUserDataPermissionsBO);


    /**
     * TODO 删除
     *
     * @param teamUserDataPermissionsBO 团队人员数据权限
     * @return Boolean
     * @author sunx
     * @methodName removeEnhance
     * @time 2022-08-30 04:44:18
     */
    Boolean removeEnhance(TeamUserDataPermissionsBO teamUserDataPermissionsBO);
}
