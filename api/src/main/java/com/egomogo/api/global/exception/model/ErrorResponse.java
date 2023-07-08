package com.egomogo.api.global.exception.model;

import com.egomogo.api.global.exception.base.ApiException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final String errorCode;
    private final String errorMessage;
    private final String path;

    public ErrorResponse(ApiException ex, String requestUri) {
        this.timestamp = LocalDateTime.now();
        this.errorCode = ex.getErrorCode().name();
        this.errorMessage = ex.getMessage();
        this.path = requestUri;
    }
}
