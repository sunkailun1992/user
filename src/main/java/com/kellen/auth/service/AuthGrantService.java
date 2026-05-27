package com.kellen.auth.service;

import com.kellen.auth.entity.AuthResource;
import com.kellen.auth.entity.bo.AuthRoleResourceBO;
import com.kellen.auth.entity.bo.AuthRoleResourceSyncBO;
import com.kellen.auth.entity.bo.AuthRoleDataScopeSyncBO;
import com.kellen.auth.entity.bo.AuthUserRoleBO;
import com.kellen.auth.entity.enums.AuthResourceCategoryEnum;
import com.kellen.auth.entity.vo.AuthResourceVO;

import java.util.List;

/**
 * 授权关系业务服务。
 *
 * @author sunkailun
 * @className AuthGrantService
 * @time 2026/05/26
 */
public interface AuthGrantService {

    /**
     * 绑定用户角色。
     *
     * @param bo 用户角色授权参数
     * @return 是否成功
     */
    Boolean bindUserRole(AuthUserRoleBO bo);

    /**
     * 绑定角色资源。
     *
     * @param bo 角色资源授权参数
     * @return 是否成功
     */
    Boolean bindRoleResource(AuthRoleResourceBO bo);

    /**
     * 查询角色已绑定资源ID列表。
     *
     * @param tenantId 租户ID
     * @param roleId   角色ID
     * @return 资源ID列表
     */
    List<String> listRoleResourceIds(String tenantId, String roleId);

    /**
     * 按完整资源ID列表同步角色资源关系。
     *
     * @param bo 角色资源同步授权参数
     * @return 是否成功
     */
    Boolean syncRoleResources(AuthRoleResourceSyncBO bo);

    /**
     * 查询角色自定义数据范围部门ID列表。
     *
     * @param tenantId 租户ID
     * @param roleId   角色ID
     * @return 部门ID列表
     */
    List<String> listRoleDataScopeDeptIds(String tenantId, String roleId);

    /**
     * 按完整部门ID列表同步角色自定义数据范围。
     *
     * @param bo 角色数据范围同步参数
     * @return 是否成功
     */
    Boolean syncRoleDataScopes(AuthRoleDataScopeSyncBO bo);

    /**
     * 查询用户拥有的资源。
     *
     * @param userId 用户ID
     * @return 资源列表
     */
    List<AuthResource> findResourcesByUserId(String userId);

    /**
     * 提取后端权限码。
     *
     * @param resources 资源列表
     * @return 权限码列表
     */
    List<String> toPermissionCodes(List<AuthResource> resources);

    /**
     * 转换指定分类的资源展示结果。
     *
     * @param resources 资源列表
     * @param category  资源分类
     * @return 资源展示结果
     */
    List<AuthResourceVO> toResourceViews(List<AuthResource> resources, AuthResourceCategoryEnum category);
}
