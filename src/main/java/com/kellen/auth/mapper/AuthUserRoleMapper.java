package com.kellen.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kellen.auth.entity.AuthUserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * 用户角色关系，mapper数据处理层
 *
 * @author sunkailun
 * @className AuthUserRoleMapper
 * @time 2026/05/25
 */
public interface AuthUserRoleMapper extends BaseMapper<AuthUserRole> {

    /**
     * 物理删除用户下的全部角色关系。
     *
     * @param tenantId 租户ID
     * @param userId   用户ID
     * @return 删除数量
     */
    @Delete("DELETE FROM auth_user_role WHERE tenant_id = #{tenantId} AND user_id = #{userId}")
    int deleteByUserId(@Param("tenantId") String tenantId, @Param("userId") String userId);
}
