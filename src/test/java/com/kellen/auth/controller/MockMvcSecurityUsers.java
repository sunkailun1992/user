package com.kellen.auth.controller;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

/**
 * MockMvc 安全测试用户工厂。
 *
 * @author sunkailun
 */
final class MockMvcSecurityUsers {

    private MockMvcSecurityUsers() {
    }

    static UserRequestPostProcessor authority(String authority) {
        return user("test-user").authorities(() -> authority);
    }
}
