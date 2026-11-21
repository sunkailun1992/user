package com.kellen.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kellen.auth.entity.AuthUserTenant;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * 用户租户关联Mapper。
 *
 * @author sunkailun
 * @className AuthUserTenantMapper
 * @time 2026/05/29
 */
public interface AuthUserTenantMapper extends BaseMapper<AuthUserTenant> {

    /**
     * 按用户删除旧租户关联。
     *
     * @param userId 用户ID
     * @return 删除数量
     */
    @Delete("DELETE FROM auth_user_tenant WHERE user_id = #{userId}")
    int deleteByUserId(@Param("userId") String userId);

    /**
     * 删除用户在某个租户里的关联（物理删除，避免逻辑删除的旧行占住唯一索引）。
     *
     * @param userId           用户ID
     * @param relationTenantId 关联租户ID
     * @return 删除数量
     */
    @Delete("DELETE FROM auth_user_tenant WHERE user_id = #{userId} AND relation_tenant_id = #{relationTenantId}")
    int deleteByUserIdAndRelationTenantId(@Param("userId") String userId, @Param("relationTenantId") String relationTenantId);
}
