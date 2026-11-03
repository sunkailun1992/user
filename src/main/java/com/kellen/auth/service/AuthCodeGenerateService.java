package com.kellen.auth.service;

import com.kellen.auth.entity.query.AuthCodeGenerateQuery;

/**
 * 认证编码生成服务。
 *
 * @author sunkailun
 * @className AuthCodeGenerateService
 * @time 2026/05/27
 */
public interface AuthCodeGenerateService {

    /**
     * 生成业务编码。
     *
     * @param query 编码生成查询参数
     * @return 业务编码
     * @author sunkailun
     */
    String generate(AuthCodeGenerateQuery query);
}
