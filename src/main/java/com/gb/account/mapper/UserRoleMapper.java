package com.gb.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gb.account.entity.UserRole;
import feign.Param;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:37
 * @description:	TODO  用户角色表，mapper数据处理层
 * @source:  	    代码生成器
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 批量插入用户角色关联表
     * @param list:
     * @return Integer
     * @author sunx
     * @DateTime 2021/3/8  2:11 下午
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @Insert("<script>" +
            "insert into `user_role` " +
            "(`id`,`user_id`,`role_id`,`description`,`create_date_time`,`create_name`,`modify_date_time`,`modify_name`) " +
            "values " +
            "<foreach collection='list' item='u' index='index' separator=','>" +
            "(#{u.id},#{u.userId},#{u.roleId},#{u.description},#{u.createDateTime},#{u.createName},#{u.modifyDateTime},#{u.modifyName})" +
            "</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertBatch(@Param("list")List<UserRole> list);
}
