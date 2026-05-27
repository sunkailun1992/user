package com.kellen.auth.service.impl;

import com.kellen.auth.entity.enums.AuthResourceCategoryEnum;
import com.kellen.auth.entity.query.AuthCodeGenerateQuery;
import com.kellen.auth.service.AuthCodeGenerateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 认证编码生成服务实现。
 *
 * @author sunkailun
 * @className AuthCodeGenerateServiceImpl
 * @time 2026/05/27
 */
@Service
public class AuthCodeGenerateServiceImpl implements AuthCodeGenerateService {

    /**
     * 时间后缀格式，保证同一业务目标下编码按生成时间可读。
     */
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 随机数生成器，用于降低同秒生成编码的碰撞概率。
     */
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成业务编码。
     *
     * @param query 编码生成查询参数
     * @return 业务编码
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    @Override
    public String generate(AuthCodeGenerateQuery query) {
        String target = StringUtils.upperCase(StringUtils.trimToEmpty(query.getTarget()), Locale.ROOT); // 标准化目标，避免前端大小写差异影响生成规则。
        String readableName = toReadableName(query.getName()); // 将业务名称转换为可读片段，便于人工识别编码来源。
        String uniqueSuffix = LocalDateTime.now().format(TIME_FORMATTER) + "_" + randomSuffix(); // 使用时间和随机数共同组成唯一后缀。

        if ("TENANT".equals(target)) {
            return "tenant_" + readableName + "_" + uniqueSuffix; // 租户编码使用 tenant 前缀。
        }
        if ("ROLE".equals(target)) {
            return "role_" + readableName + "_" + uniqueSuffix; // 角色编码使用 role 前缀。
        }
        if ("RESOURCE".equals(target)) {
            AuthResourceCategoryEnum category = query.getResourceCategory() == null ? AuthResourceCategoryEnum.FRONTEND : query.getResourceCategory(); // 资源默认按前端菜单生成。
            String prefix = AuthResourceCategoryEnum.BACKEND == category ? "api:" : "menu:"; // 前端资源和后端权限使用不同命名空间。
            return prefix + readableName + ":" + uniqueSuffix; // 权限资源编码保留冒号分段，匹配权限码阅读习惯。
        }
        return "code_" + readableName + "_" + uniqueSuffix; // 未知目标兜底生成通用编码，保证接口可用。
    }

    /**
     * 转换可读名称片段。
     *
     * @param name 业务名称
     * @return 可读名称片段
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    private String toReadableName(String name) {
        String normalized = StringUtils.lowerCase(StringUtils.trimToEmpty(name), Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "_")
                .replaceAll("_+", "_")
                .replaceAll("^_|_$", ""); // 只保留小写字母、数字和下划线，避免权限码出现中文、空格或特殊符号。
        return StringUtils.defaultIfBlank(normalized, "auto"); // 名称为空时使用 auto，保证编码结构稳定。
    }

    /**
     * 生成四位随机后缀。
     *
     * @return 随机后缀
     * @author sunkailun
     * @DateTime 2026/05/27
     * @email 376253703@qq.com
     */
    private String randomSuffix() {
        return String.format("%04d", RANDOM.nextInt(10000)); // 固定四位数字，便于排序和阅读。
    }
}
