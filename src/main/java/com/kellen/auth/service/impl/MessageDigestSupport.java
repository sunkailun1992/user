package com.kellen.auth.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 简单摘要比较工具。
 */
final class MessageDigestSupport {

    private MessageDigestSupport() {
    }

    static boolean constantTimeEquals(String first, String second) {
        return MessageDigest.isEqual(first.getBytes(StandardCharsets.UTF_8), second.getBytes(StandardCharsets.UTF_8));
    }
}
