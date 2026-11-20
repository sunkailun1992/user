package com.kellen.auth.service;

import com.kellen.datapermission.DataPermissionContextHolder;
import com.kellen.utils.context.TenantContextHolder;

import java.util.function.Supplier;

/**
 * 认证横切查询的上下文范围：在跨租户或指定租户下执行查询，结束后把调用方原有的租户、租户忽略与数据权限忽略标记原样恢复。
 *
 * <p>业务上下文按请求加载（Dubbo 调用的线程里可能带着上游透传的租户），不能在查询后简单清空上下文。</p>
 *
 * @author 孙凯伦
 */
public final class AuthQueryScope {

    private AuthQueryScope() {
    }

    /**
     * 跨租户、忽略数据权限执行查询（认证配置是全局主数据）。
     *
     * @param query 查询
     * @param <T>   结果类型
     * @return 查询结果
     */
    public static <T> T crossTenant(Supplier<T> query) {
        Snapshot snapshot = Snapshot.take();
        try {
            TenantContextHolder.ignore();
            DataPermissionContextHolder.ignore();
            return query.get();
        } finally {
            snapshot.restore();
        }
    }

    /**
     * 在指定租户下、忽略数据权限执行查询（租户插件按该租户拼条件）。
     *
     * @param tenantId 租户 ID
     * @param query    查询
     * @param <T>      结果类型
     * @return 查询结果
     */
    public static <T> T inTenant(String tenantId, Supplier<T> query) {
        Snapshot snapshot = Snapshot.take();
        try {
            TenantContextHolder.clearIgnore();
            TenantContextHolder.setTenantId(tenantId);
            DataPermissionContextHolder.ignore();
            return query.get();
        } finally {
            snapshot.restore();
        }
    }

    /**
     * 调用前的上下文。
     *
     * @param tenantId       租户 ID
     * @param tenantIgnored  是否忽略租户
     * @param dataIgnored    是否忽略数据权限
     */
    private record Snapshot(String tenantId, boolean tenantIgnored, boolean dataIgnored) {

        static Snapshot take() {
            return new Snapshot(TenantContextHolder.getTenantId(), TenantContextHolder.isIgnore(), DataPermissionContextHolder.isIgnore());
        }

        void restore() {
            TenantContextHolder.setTenantId(tenantId);
            if (tenantIgnored) {
                TenantContextHolder.ignore();
            } else {
                TenantContextHolder.clearIgnore();
            }
            if (dataIgnored) {
                DataPermissionContextHolder.ignore();
            } else {
                DataPermissionContextHolder.clear();
            }
        }
    }
}
