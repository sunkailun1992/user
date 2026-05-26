package com.kellen.auth.service;

import java.util.Map;

/**
 * 认证体系初始化服务。
 *
 * @author sunkailun
 * @className AuthBootstrapService
 * @time 2026/05/26
 */
public interface AuthBootstrapService {

    /**
     * 初始化真实认证授权基础数据。
     *
     * @return 初始化结果
     */
    Map<String, Object> init();
}
