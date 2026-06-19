package com.kellen.auth.service.query;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * 认证查询字段白名单工具。
 * <p>
 * 统一约束前端传入的 select 和 order 字段，避免动态字段直接进入 SQL 造成注入风险。
 *
 * @author sunkailun
 * @className AuthQueryFieldWhitelist
 * @time 2026/06/08
 */
public final class AuthQueryFieldWhitelist {

    /**
     * 私有构造方法。
     *
     * @return void
     * @author sunkailun
     */
    private AuthQueryFieldWhitelist() {
        // 工具类禁止实例化，所有能力通过静态方法提供。
    }

    /**
     * 解析安全排序字段。
     *
     * @param requestedField 前端请求排序字段
     * @param defaultField   默认排序字段
     * @param allowedFields  允许查询字段映射
     * @return 安全排序字段
     * @author sunkailun
     */
    public static String resolveSortField(String requestedField, String defaultField, Map<String, String> allowedFields) {
        // 取出前端传入字段对应的数据库列名，未命中表示字段不在白名单。
        String requestedColumn = allowedFields.get(StringUtils.trimToEmpty(requestedField));
        // 前端字段合法时直接返回白名单内数据库列名。
        if (StringUtils.isNotBlank(requestedColumn)) {
            // 返回白名单解析后的排序列，避免原始字符串进入 SQL。
            return requestedColumn;
        }
        // 取出默认字段对应的数据库列名，确保兜底排序字段也经过白名单解析。
        String defaultColumn = allowedFields.get(StringUtils.trimToEmpty(defaultField));
        // 默认字段合法时返回默认排序列。
        if (StringUtils.isNotBlank(defaultColumn)) {
            // 返回白名单解析后的默认排序列。
            return defaultColumn;
        }
        // 默认字段未配置到白名单时返回主键列，保证查询仍然有稳定排序字段。
        return allowedFields.get("id");
    }

    /**
     * 解析安全查询字段集合。
     *
     * @param requestedFields 前端请求查询字段
     * @param allowedFields   允许查询字段映射
     * @return 安全查询字段数组
     * @author sunkailun
     */
    public static String[] resolveSelectFields(String requestedFields, Map<String, String> allowedFields) {
        // 未指定查询字段时返回空数组，由调用方保持默认 select 行为。
        if (StringUtils.isBlank(requestedFields)) {
            // 返回空数组表示不拼接自定义 select。
            return new String[0];
        }
        // 按英文逗号拆分字段，并且只保留白名单内字段。
        return Arrays.stream(requestedFields.split(","))
                // 去掉每个字段两侧空白，避免空格影响白名单匹配。
                .map(StringUtils::trimToEmpty)
                // 将前端字段名转换为白名单内数据库列名。
                .map(allowedFields::get)
                // 过滤未命中白名单的非法字段。
                .filter(StringUtils::isNotBlank)
                // 去重避免重复 select 同一列。
                .distinct()
                // 转换为 MyBatis-Plus QueryWrapper 支持的字段数组。
                .toArray(String[]::new);
    }
}
