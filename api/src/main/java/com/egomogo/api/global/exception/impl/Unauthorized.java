package com.egomogo.api.global.exception.impl;

import com.egomogo.api.global.exception.base.ApiException;
import com.egomogo.api.global.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public final class Unauthorized extends ApiException {
    public Unauthorized(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public Unauthorized(ErrorCode errorCode) {
        super(errorCode);
    }

    public Unauthorized(String message) {
        super(message);
    }

    public Unauthorized() {
        super();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    protected ErrorCode getDefaultErrorCode() {
        return ErrorCode.UNAUTHORIZED;
    }
}
