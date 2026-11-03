package com.kellen.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthOAuthClient;
import com.kellen.auth.entity.bo.AuthOAuthClientBO;
import com.kellen.auth.entity.query.AuthOAuthClientQuery;
import com.kellen.auth.entity.vo.AuthOAuthClientVO;

import java.util.List;

/**
 * OAuth 客户端业务服务。
 */
public interface AuthOAuthClientService {

    Page<AuthOAuthClientVO> page(Page<AuthOAuthClient> page, AuthOAuthClientQuery query);

    List<AuthOAuthClientVO> list(AuthOAuthClientQuery query);

    String save(AuthOAuthClientBO bo);

    Boolean update(AuthOAuthClientBO bo);

    Boolean remove(String tenantId, String id);
}
