package com.kellen.auth.service.impl;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * OAuth token endpoint 标准错误。
 */
@Getter
public class AuthOAuthTokenException extends RuntimeException {

    private final String error;
    private final HttpStatus status;

    public AuthOAuthTokenException(String error, String description, HttpStatus status) {
        super(description);
        this.error = error;
        this.status = status;
    }
}
