package com.egomogo.api.global.exception.impl;

import com.egomogo.api.global.exception.base.ApiException;
import com.egomogo.api.global.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public final class Forbidden extends ApiException {
    public Forbidden(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public Forbidden(ErrorCode errorCode) {
        super(errorCode);
    }

    public Forbidden(String message) {
        super(message);
    }

    public Forbidden() {
        super();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    protected ErrorCode getDefaultErrorCode() {
        return ErrorCode.FORBIDDEN;
    }
}
