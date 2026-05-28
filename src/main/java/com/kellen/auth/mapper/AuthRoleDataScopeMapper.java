package com.kellen.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kellen.auth.entity.AuthRoleDataScope;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * 角色自定义数据范围，mapper数据处理层。
 *
 * @author sunkailun
 * @className AuthRoleDataScopeMapper
 * @time 2026/05/27
 */
public interface AuthRoleDataScopeMapper extends BaseMapper<AuthRoleDataScope> {

    /**
     * 物理删除角色下的全部自定义数据范围关系。
     *
     * @param tenantId 租户ID
     * @param roleId   角色ID
     * @return 删除数量
     */
    @Delete("DELETE FROM auth_role_data_scope WHERE tenant_id = #{tenantId} AND role_id = #{roleId}")
    int deleteByRoleId(@Param("tenantId") String tenantId, @Param("roleId") String roleId);
}
