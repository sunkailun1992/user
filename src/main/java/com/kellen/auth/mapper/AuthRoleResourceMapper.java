package com.kellen.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kellen.auth.entity.AuthRoleResource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色资源关系，mapper数据处理层
 *
 * @author sunkailun
 * @className AuthRoleResourceMapper
 * @time 2026/05/25
 */
public interface AuthRoleResourceMapper extends BaseMapper<AuthRoleResource> {

    /**
     * 查询角色当前有效资源ID列表。
     *
     * @param tenantId 租户ID
     * @param roleId   角色ID
     * @return 资源ID列表
     */
    @Select("SELECT resource_id FROM auth_role_resource WHERE tenant_id = #{tenantId} AND role_id = #{roleId} AND is_delete = b'0'")
    List<String> selectResourceIdsByRoleId(@Param("tenantId") String tenantId, @Param("roleId") String roleId);

    /**
     * 物理删除角色下指定资源以外的关系。
     *
     * @param tenantId    租户ID
     * @param roleId      角色ID
     * @param resourceIds 需要保留的资源ID列表
     * @return 删除数量
     */
    @Delete("""
            <script>
            DELETE FROM auth_role_resource
            WHERE tenant_id = #{tenantId}
              AND role_id = #{roleId}
            <if test="resourceIds != null and resourceIds.size > 0">
              AND resource_id NOT IN
              <foreach collection="resourceIds" item="resourceId" open="(" separator="," close=")">
                #{resourceId}
              </foreach>
            </if>
            </script>
            """)
    int deleteByRoleIdAndResourceIdNotIn(@Param("tenantId") String tenantId,
                                         @Param("roleId") String roleId,
                                         @Param("resourceIds") List<String> resourceIds);

    /**
     * 物理删除指定角色资源关系。
     *
     * @param tenantId   租户ID
     * @param roleId     角色ID
     * @param resourceId 资源ID
     * @return 删除数量
     */
    @Delete("DELETE FROM auth_role_resource WHERE tenant_id = #{tenantId} AND role_id = #{roleId} AND resource_id = #{resourceId}")
    int deleteByRoleIdAndResourceId(@Param("tenantId") String tenantId,
                                    @Param("roleId") String roleId,
                                    @Param("resourceId") String resourceId);
}
