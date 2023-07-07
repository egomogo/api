package com.egomogo.api.global.exception.impl;

import com.egomogo.api.global.exception.base.ApiException;
import com.egomogo.api.global.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public final class BadRequest extends ApiException {
    public BadRequest(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public BadRequest(ErrorCode errorCode) {
        super(errorCode);
    }

    public BadRequest(String message) {
        super(message);
    }

    public BadRequest() {
        super();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    protected ErrorCode getDefaultErrorCode() {
        return ErrorCode.BAD_REQUEST;
    }
}
