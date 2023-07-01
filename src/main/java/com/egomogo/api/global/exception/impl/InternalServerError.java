package com.egomogo.api.global.exception.impl;

import com.egomogo.api.global.exception.base.ApiException;
import com.egomogo.api.global.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public final class InternalServerError extends ApiException {
    public InternalServerError(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public InternalServerError(ErrorCode errorCode) {
        super(errorCode);
    }

    public InternalServerError(String message) {
        super(message);
    }

    public InternalServerError() {
        super();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    protected ErrorCode getDefaultErrorCode() {
        return ErrorCode.INTERNAL_SERVER_ERROR;
    }
}
