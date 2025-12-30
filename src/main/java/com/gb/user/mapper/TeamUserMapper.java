package com.gb.user.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.user.entity.TeamUser;
import com.gb.user.entity.enums.TeamUserTypeEnum;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * TODO 团队人员，mapper数据处理层
 * 代码生成器
 *
 * @author sunx
 * @className TeamUserMapper
 * @time 2022-08-31 11:01:59
 */
public interface TeamUserMapper extends BaseMapper<TeamUser> {
    /**
     * 根据团队ID查询本团队和子团队下的团队人员【去重】
     * @author sunx
     * @param teamId 团队ID
     * @return List<TeamUser>
     */
    @Select("select distinct `user_id` from `team_user` where `is_delete` = 0 and `team_id` in ("
            + " select t3.`id` from ("
            + " select t1.`id`, t1.`team_id`, "
            + " if(find_in_set(`team_id`, @pids) > 0, @pids := concat(@pids, ',', `id`), if(`id` = @pids, 0 , -1)) as `isChild` "
            + " from (select `id`,`team_id` from `team` t order by `sorting` desc) t1, (select @pids := #{teamId}) t2 ) t3 where `isChild` != -1 "
            + ")")
    List<TeamUser> queryTeamUserByTeamId(@Param("teamId") String teamId);

    /**
     * 团队权限经纪人分页查询
     *
     * @param page: 分页
     * @param teamId: 团队ID
     * @param authUserId: 权限团队成员ID
     * @param userId: 团队成员ID
     * @param queryWrapper:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-10-21 01:50:40
     */
    @Select("<script>" +
            "select * from (" +
            "<if test=\"(userId != null and userId !='') or (teamId != null and teamId !='')\">" +
            "select * from (" +
            "</if>"+
            " select `id`, `user_id`, `team_id`, `type`, `team_group_id`, `team_group_value_id`," +
            " (select `name` from `team` where `team`.`id` = `team_user`.`team_id`) as teamName, " +
            " (select `name` from `user_extends` where `user_extends`.`user_id` = `team_user`.`user_id`) as userName, " +
            " (select `name` from `team_group_value` where `team_group_value`.`id` = `team_user`.`team_group_value_id`) as teamGroupValueName, " +
            " (select `name` from `team_group` where `team_group`.`id` = `team_user`.`team_group_id`) as teamGroupName " +
            " from `team_user` where is_delete = 0 " +
            "<if test=\"authUserId != null and authUserId !=''\">" +
            " and `team_id` in ( select `team_id` from `team_user_data_permissions` where `is_delete` = 0 and `team_user_id` = '${authUserId}')" +
            " or `user_id` = '${authUserId}' " +
            "</if>" +
            "<if test=\"(userId != null and userId !='') or (teamId != null and teamId !='')\">" +
            ") a where 1=1 " +
            "<if test=\"userId != null and userId !=''\">" +
            " and `user_id` = '${userId}' " +
            "</if>"+
            "<if test=\"teamId != null and teamId !=''\">" +
            " and `team_id` in ('${teamId}') " +
            "</if>"+
            "</if>"+
            " ) q ${ew.customSqlSegment}" +
            "</script>")
    IPage<TeamUser> teamAuthBrokerSelect(Page page, @Param("teamId") String teamId, @Param("authUserId") String authUserId, @Param("userId") String userId, @Param("ew") QueryWrapper<TeamUser> queryWrapper);

    /**
     * 团队权限经纪人统计查询
     *
     * @param teamUserNameQuery: 团队成员名称模糊查询
     * @param teamId: 团队ID
     * @param authUserId: 权限团队成员ID
     * @param userId: 团队成员ID
     * @return List<TeamUser>
     * @author sunx
     * @since 2021-10-21 01:50:40
     */
    @Select("<script>" +
            "<if test=\"(userId != null and userId !='') or (teamId != null and teamId !='') or (teamUserNameQuery != null and teamUserNameQuery !='') or (typeEnum != null)\">" +
            "select * from (" +
            "</if>"+
            " select `user_id`, `team_id`, " +
            " (select `name` from `team` where `team`.`id` = `team_user`.`team_id`) as teamName, " +
            " (select `name` from `user_extends` where `user_extends`.`user_id` = `team_user`.`user_id`) as userName, " +
            " type " +
            " from `team_user` where is_delete = 0 " +
            "<if test=\"authUserId != null and authUserId !=''\">" +
            " and `team_id` in ( select `team_id` from `team_user_data_permissions` where `is_delete` = 0 and `team_user_id` = '${authUserId}')" +
            " or `user_id` = '${authUserId}' " +
            "</if>" +
            "<if test=\"(userId != null and userId !='') or (teamId != null and teamId !='') or (teamUserNameQuery != null and teamUserNameQuery !='') or (typeEnum != null)\">" +
            ") a where 1=1" +
            "<if test=\"userId != null and userId !=''\">" +
            " and `user_id` = '${userId}' " +
            "</if>"+
            "<if test=\"teamId != null and teamId !=''\">" +
            " and `team_id` in (${teamId}) " +
            "</if>"+
            "<if test=\"teamUserNameQuery != null and teamUserNameQuery !=''\">" +
            " and `userName` like '${teamUserNameQuery}%' " +
            "</if>"+
            "<if test=\"typeEnum != null\">" +
            " and `type` = ${typeEnum.value} " +
            "</if>"+
            "</if>"+
            "</script>")
    List<TeamUser> queryAuthBrokerCount(@Param("teamUserNameQuery") String teamUserNameQuery, @Param("teamId") String teamId, @Param("authUserId") String authUserId, @Param("userId") String userId, @Param("typeEnum") TeamUserTypeEnum typeEnum);
}