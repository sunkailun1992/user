package com.gb.permissions.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gb.permissions.entity.Resource;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:59:45
 * @description:	TODO  资源表，mapper数据处理层
 * @source:  	    代码生成器
 */
public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * 用户权限查询
     * @author      sunkailun
     * @DateTime    2020/1/2  2:41 下午
     * @email       376253703@qq.com
     * @phone       13777579028
     * @param userId:
     * @param api:
     * @param appCode:
     * @param superiorsId:
     * @return      java.util.List<com.entity.Resource>
     */
    @Select("<script>" +
            "select * from `resource` where id in (select `resource_id` from `role_resource` where " +
            "(" +
            "`role_id` in (select `id` from `role` where `id` in (select `role_id` from `user_role` where `user_id` = #{userId} and `is_delete` = false) and `system_id` in (select `id` from `system` where `code` = #{appCode})  and `is_delete` = false) and `is_delete` = false ) " +
            "or" +
            "`role_id` in (select `role_id` from `group_role` where `group_id` in (select `id` from `group` where id in(select `group_id` from `user_group` where `user_id` = #{userId} and `is_delete` = false) and `system_id` in (select `id` from `system` where `code` =  #{appCode})  and `is_delete` = false) and `is_delete` = false)" +
            ")"
            +
            "<if test='superiorsId != null '>" +
            "and `superiors_id` = #{superiorsId} " +
            "</if>"
            +
            "<if test='api != null '>" +
            "and `api` = #{api} " +
            "</if>"
            +
            "and `is_delete` = false order by `sorting` desc" +
            "</script>")
    List<Resource> userResource(@Param("userId") String userId, @Param("api") Boolean api, @Param("appCode") String appCode, @Param("superiorsId") String superiorsId);


    /**
     * 根据角色码值查询对应的权限
     * @author      sunkailun
     * @DateTime    2020/1/2  2:41 下午
     * @email       376253703@qq.com
     * @phone       13777579028
     * @param roleCode: 角色码值
     * @param api:
     * @return      java.util.List<com.entity.Resource>
     */
    @Select("<script>" +
            "select * from `resource` where id in (select `resource_id` from `role_resource` where " +
            "`role_id` in (select `id` from `role` where `value` = #{roleCode}  and `is_delete` = false)  and `is_delete` = false " +
            ")"
            +
            "<if test='api != null '>" +
            " and `api` = #{api} " +
            "</if>"
            +
            "and `is_delete` = false order by `sorting` desc" +
            "</script>")
    List<Resource> getRoleResource(@Param("roleCode") String roleCode, @Param("api") Boolean api);
}
