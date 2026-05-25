package com.kellen.securitydemo.controller;

import com.kellen.security.SecurityUser;
import com.kellen.security.UserContextHolder;
import com.kellen.utils.TenantContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/security-demo")
public class SecurityDemoController {

    @GetMapping("/public")
    public Map<String, Object> publicApi() {
        return result("public api");
    }

    @GetMapping("/me")
    public Map<String, Object> me() {
        return result("authenticated api");
    }

    @GetMapping("/permission")
    @PreAuthorize("hasAuthority('user:tenant-demo:list')")
    public Map<String, Object> permission() {
        return result("permission api");
    }

    private Map<String, Object> result(String message) {
        SecurityUser user = UserContextHolder.get();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("message", message);
        result.put("userId", user == null ? null : user.getUserId());
        result.put("username", user == null ? null : user.getUsername());
        result.put("tenantId", user == null ? null : user.getTenantId());
        result.put("tenantContext", TenantContextHolder.getTenantId());
        result.put("authorities", user == null ? null : user.getAuthorities());
        return result;
    }
}
