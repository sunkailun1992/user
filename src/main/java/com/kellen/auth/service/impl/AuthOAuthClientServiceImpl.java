package com.kellen.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthOAuthClient;
import com.kellen.auth.entity.bo.AuthOAuthClientBO;
import com.kellen.auth.entity.enums.AuthStateEnum;
import com.kellen.auth.entity.query.AuthOAuthClientQuery;
import com.kellen.auth.entity.vo.AuthOAuthClientVO;
import com.kellen.auth.mapper.AuthOAuthClientMapper;
import com.kellen.auth.service.AuthOAuthClientService;
import com.kellen.auth.service.query.AuthOAuthClientServiceQuery;
import com.kellen.auth.service.results.AuthOAuthClientServiceResults;
import com.kellen.utils.context.TenantContextHolder;
import com.kellen.utils.convert.GeneralConvertor;
import com.kellen.utils.enumeration.ReturnCode;
import com.kellen.utils.exception.UserException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * OAuth 客户端业务服务实现。
 */
@Service
public class AuthOAuthClientServiceImpl implements AuthOAuthClientService {

    private static final String CLIENT_TYPE_PUBLIC = "public";
    private static final String CLIENT_TYPE_CONFIDENTIAL = "confidential";
    private static final String TOKEN_AUTH_METHOD_NONE = "none";
    private static final String TOKEN_AUTH_METHOD_CLIENT_SECRET_BASIC = "client_secret_basic";
    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    private static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
    private static final long DEFAULT_ACCESS_TOKEN_TTL_SECONDS = 900L;
    private static final long MAX_ACCESS_TOKEN_TTL_SECONDS = 30L * 24L * 60L * 60L;

    private final AuthOAuthClientMapper authOAuthClientMapper;
    private final AuthOAuthClientServiceQuery authOAuthClientServiceQuery;
    private final AuthOAuthClientServiceResults authOAuthClientServiceResults;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthOAuthClientServiceImpl(AuthOAuthClientMapper authOAuthClientMapper,
                                      AuthOAuthClientServiceQuery authOAuthClientServiceQuery,
                                      AuthOAuthClientServiceResults authOAuthClientServiceResults) {
        this.authOAuthClientMapper = authOAuthClientMapper;
        this.authOAuthClientServiceQuery = authOAuthClientServiceQuery;
        this.authOAuthClientServiceResults = authOAuthClientServiceResults;
    }

    @Override
    public Page<AuthOAuthClientVO> page(Page<AuthOAuthClient> page, AuthOAuthClientQuery query) {
        try {
            TenantContextHolder.setTenantId(query.getTenantId());
            QueryWrapper<AuthOAuthClient> queryWrapper = buildQueryWrapper(query);
            Page<AuthOAuthClient> pageDO = authOAuthClientMapper.selectPage(page, queryWrapper);
            Page<AuthOAuthClientVO> pageVO = authOAuthClientServiceResults.toPageVO(pageDO);
            return needAssignment(query) ? authOAuthClientServiceResults.assignment(pageVO) : pageVO;
        } finally {
            TenantContextHolder.clear();
        }
    }

    @Override
    public List<AuthOAuthClientVO> list(AuthOAuthClientQuery query) {
        try {
            TenantContextHolder.setTenantId(query.getTenantId());
            QueryWrapper<AuthOAuthClient> queryWrapper = buildQueryWrapper(query);
            List<AuthOAuthClient> records = authOAuthClientMapper.selectList(queryWrapper);
            List<AuthOAuthClientVO> voRecords = authOAuthClientServiceResults.toListVO(records);
            return needAssignment(query) ? authOAuthClientServiceResults.assignment(voRecords) : voRecords;
        } finally {
            TenantContextHolder.clear();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(AuthOAuthClientBO bo) {
        normalize(bo, true);
        validate(bo, true);
        try {
            TenantContextHolder.setTenantId(bo.getTenantId());
            AuthOAuthClient exists = authOAuthClientMapper.selectOne(new LambdaQueryWrapper<AuthOAuthClient>()
                    .eq(AuthOAuthClient::getClientId, bo.getClientId())
                    .last("LIMIT 1"));
            if (exists != null) {
                return exists.getId();
            }
            AuthOAuthClient client = GeneralConvertor.convertor(bo, AuthOAuthClient.class);
            client.setId(StringUtils.trimToNull(bo.getId()));
            client.setState(bo.getState() == null ? AuthStateEnum.启用 : bo.getState());
            if (StringUtils.isNotBlank(bo.getClientSecret())) {
                client.setClientSecret(passwordEncoder.encode(bo.getClientSecret()));
            }
            authOAuthClientMapper.insert(client);
            return client.getId();
        } finally {
            TenantContextHolder.clear();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(AuthOAuthClientBO bo) {
        normalize(bo, false);
        validate(bo, false);
        try {
            TenantContextHolder.setTenantId(bo.getTenantId());
            AuthOAuthClient client = GeneralConvertor.convertor(bo, AuthOAuthClient.class);
            client.setTenantId(null);
            client.setClientId(null);
            if (StringUtils.isNotBlank(bo.getClientSecret())) {
                client.setClientSecret(passwordEncoder.encode(bo.getClientSecret()));
            } else {
                client.setClientSecret(null);
            }
            return authOAuthClientMapper.updateById(client) > 0;
        } finally {
            TenantContextHolder.clear();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean remove(String tenantId, String id) {
        try {
            TenantContextHolder.setTenantId(tenantId);
            return authOAuthClientMapper.deleteById(id) > 0;
        } finally {
            TenantContextHolder.clear();
        }
    }

    private QueryWrapper<AuthOAuthClient> buildQueryWrapper(AuthOAuthClientQuery query) {
        AuthOAuthClient entity = GeneralConvertor.convertor(query, AuthOAuthClient.class);
        if (entity != null) {
            entity.setTenantId(null);
        }
        QueryWrapper<AuthOAuthClient> queryWrapper = entity == null ? new QueryWrapper<>() : new QueryWrapper<>(entity);
        authOAuthClientServiceQuery.query(query, queryWrapper);
        if (query != null && StringUtils.isNotBlank(query.getQuery())) {
            queryWrapper.and(wrapper -> wrapper.like("client_id", query.getQuery()).or().like("name", query.getQuery()));
        }
        return queryWrapper;
    }

    private void normalize(AuthOAuthClientBO bo, boolean create) {
        bo.setClientType(StringUtils.defaultIfBlank(bo.getClientType(), CLIENT_TYPE_CONFIDENTIAL));
        bo.setTokenEndpointAuthMethod(StringUtils.defaultIfBlank(
                bo.getTokenEndpointAuthMethod(),
                CLIENT_TYPE_PUBLIC.equalsIgnoreCase(bo.getClientType()) ? TOKEN_AUTH_METHOD_NONE : TOKEN_AUTH_METHOD_CLIENT_SECRET_BASIC
        ));
        bo.setAccessTokenTtlSeconds(normalizeTtl(bo.getAccessTokenTtlSeconds()));
        if (bo.getState() == null && create) {
            bo.setState(AuthStateEnum.启用);
        }
    }

    private void validate(AuthOAuthClientBO bo, boolean create) {
        boolean publicClient = CLIENT_TYPE_PUBLIC.equalsIgnoreCase(bo.getClientType());
        boolean noTokenAuth = TOKEN_AUTH_METHOD_NONE.equalsIgnoreCase(bo.getTokenEndpointAuthMethod());
        if (noTokenAuth && !publicClient) {
            throw new UserException(ReturnCode.用户请求参数错误, "tokenEndpointAuthMethod=none 只能用于 public 客户端");
        }
        if (!noTokenAuth && create && StringUtils.isBlank(bo.getClientSecret())) {
            throw new UserException(ReturnCode.请求必填参数为空, "保密客户端必须配置 clientSecret");
        }
        if (containsToken(bo.getGrantTypes(), GRANT_TYPE_AUTHORIZATION_CODE) && StringUtils.isBlank(bo.getRedirectUris())) {
            throw new UserException(ReturnCode.请求必填参数为空, "authorization_code 客户端必须配置 redirectUris");
        }
        if (containsToken(bo.getGrantTypes(), GRANT_TYPE_CLIENT_CREDENTIALS) && noTokenAuth) {
            throw new UserException(ReturnCode.用户请求参数错误, "client_credentials 不能使用 tokenEndpointAuthMethod=none");
        }
    }

    private boolean containsToken(String raw, String token) {
        if (StringUtils.isBlank(raw)) {
            return false;
        }
        for (String value : raw.split("[,\\s]+")) {
            if (token.equals(value)) {
                return true;
            }
        }
        return false;
    }

    private long normalizeTtl(Long configuredTtlSeconds) {
        if (configuredTtlSeconds == null || configuredTtlSeconds <= 0) {
            return DEFAULT_ACCESS_TOKEN_TTL_SECONDS;
        }
        return Math.min(configuredTtlSeconds, MAX_ACCESS_TOKEN_TTL_SECONDS);
    }

    private boolean needAssignment(AuthOAuthClientQuery query) {
        return query == null || !Boolean.FALSE.equals(query.getAssignment());
    }
}
