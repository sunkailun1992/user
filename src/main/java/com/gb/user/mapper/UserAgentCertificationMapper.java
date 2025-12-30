package com.gb.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gb.user.entity.UserAgentCertification;
import feign.Param;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import java.util.List;

/**
 * <p>
 * 用户经纪人认证 Mapper 接口
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
public interface UserAgentCertificationMapper extends BaseMapper<UserAgentCertification> {

    /**
     * 批量插入经纪人认证
     * @param list:
     * @return int
     * @author sunx
     * @DateTime 2021/3/8  2:11 下午
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @Insert("<script>" +
            "insert into user_agent_certification " +
            "(`id`,`user_id`,`create_date_time`,`create_name`,`modify_date_time`,`modify_name`) " +
            "values " +
            "<foreach collection='list' item='u' index='index' separator=','>" +
            "(#{u.id},#{u.userId},#{u.createDateTime},#{u.createName},#{u.modifyDateTime},#{u.modifyName})" +
            "</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertBatch(@Param("list")List<UserAgentCertification> list);
}
