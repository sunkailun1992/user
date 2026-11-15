package com.kellen.auth.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kellen.auth.entity.AuthOAuthClient;
import com.kellen.auth.entity.query.AuthOAuthClientQuery;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * OAuth 客户端查询条件增强。
 */
@Component
public class AuthOAuthClientServiceQuery {

    private static final String DEFAULT_SORT_FIELD = "update_time";

    private static final Map<String, String> ALLOWED_FIELDS = Map.ofEntries(
            Map.entry("id", "id"),
            Map.entry("clientId", "client_id"),
            Map.entry("client_id", "client_id"),
            Map.entry("name", "name"),
            Map.entry("clientType", "client_type"),
            Map.entry("client_type", "client_type"),
            Map.entry("tokenEndpointAuthMethod", "token_endpoint_auth_method"),
            Map.entry("token_endpoint_auth_method", "token_endpoint_auth_method"),
            Map.entry("grantTypes", "grant_types"),
            Map.entry("grant_types", "grant_types"),
            Map.entry("state", "state"),
            Map.entry("tenantId", "tenant_id"),
            Map.entry("tenant_id", "tenant_id"),
            Map.entry("version", "version"),
            Map.entry("createTime", "create_time"),
            Map.entry("create_time", "create_time"),
            Map.entry("updateTime", "update_time"),
            Map.entry("update_time", "update_time")
    );

    public QueryWrapper<AuthOAuthClient> query(AuthOAuthClientQuery query, QueryWrapper<AuthOAuthClient> queryWrapper) {
        if (query == null) {
            return queryWrapper;
        }
        String sortField = AuthQueryFieldWhitelist.resolveSortField(query.getCollationFields(), DEFAULT_SORT_FIELD, ALLOWED_FIELDS);
        if (Boolean.TRUE.equals(query.getCollation())) {
            queryWrapper.orderByAsc(sortField);
        } else {
            queryWrapper.orderByDesc(sortField);
        }
        String[] selectFields = AuthQueryFieldWhitelist.resolveSelectFields(query.getFields(), ALLOWED_FIELDS);
        if (selectFields.length > 0) {
            queryWrapper.select(selectFields);
        }
        return queryWrapper;
    }
}
