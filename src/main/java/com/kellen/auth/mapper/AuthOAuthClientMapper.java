package com.kellen.auth.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kellen.auth.entity.AuthOAuthClient;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * OAuth 客户端 Mapper。
 */
public interface AuthOAuthClientMapper extends BaseMapper<AuthOAuthClient> {

    /**
     * 公开 OAuth 入口按全局唯一 client_id 定位可信租户，仅返回主键和租户，不读取密钥。
     *
     * <p>此自定义 SQL 不经过 BaseMapper 的逻辑删除拼装，故显式限定启用、未删除；
     * 最多取两条让服务拒绝异常重复数据，不以 LIMIT 1 随机选择租户。只有此方法忽略租户插件。</p>
     *
     * @param clientId 标准 OAuth 客户端标识，始终使用参数绑定
     * @return 零到两个身份投影，完整客户端仍须在所属租户下读取
     */
    @InterceptorIgnore(tenantLine = "true")
    @Select("SELECT id, tenant_id FROM auth_oauth_client WHERE client_id = #{clientId} AND state = 1 AND is_delete = false LIMIT 2")
    List<AuthOAuthClient> selectEnabledOAuthIdentities(@Param("clientId") String clientId);
}
