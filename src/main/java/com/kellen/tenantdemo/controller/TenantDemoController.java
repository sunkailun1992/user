package com.kellen.tenantdemo.controller;

import com.kellen.tenantdemo.entity.TenantDemo;
import com.kellen.tenantdemo.mapper.TenantDemoMapper;
import com.kellen.utils.TenantContextHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tenant-demo")
public class TenantDemoController {

    private final JdbcTemplate jdbcTemplate;
    private final TenantDemoMapper tenantDemoMapper;

    public TenantDemoController(JdbcTemplate jdbcTemplate, TenantDemoMapper tenantDemoMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tenantDemoMapper = tenantDemoMapper;
    }

    @PostMapping("/init")
    public Map<String, Object> init() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS tenant_demo (
                    id VARCHAR(64) PRIMARY KEY,
                    tenant_id VARCHAR(64) NOT NULL,
                    name VARCHAR(100) NOT NULL,
                    KEY idx_tenant_demo_tenant_id (tenant_id)
                )
                """);
        return result("tenant_demo table ready", null);
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestParam String name) {
        TenantDemo tenantDemo = new TenantDemo();
        tenantDemo.setName(name);
        tenantDemoMapper.insert(tenantDemo);
        return result("saved", tenantDemo);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user:tenant-demo:list')")
    public Map<String, Object> list() {
        return result("current tenant data", tenantDemoMapper.selectList(null));
    }

    @GetMapping("/list-all")
    public Map<String, Object> listAll() {
        TenantContextHolder.ignore();
        try {
            return result("all tenant data", tenantDemoMapper.selectList(null));
        } finally {
            TenantContextHolder.clearIgnore();
        }
    }

    private Map<String, Object> result(String message, Object data) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("message", message);
        result.put("currentTenantId", TenantContextHolder.getTenantId());
        result.put("data", data);
        if (data instanceof List<?> list) {
            result.put("size", list.size());
        }
        return result;
    }
}
