package com.egomogo.api.global.exception.impl;

import com.egomogo.api.global.exception.base.ApiException;
import com.egomogo.api.global.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public final class NotImplemented extends ApiException {
    public NotImplemented(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public NotImplemented(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotImplemented(String message) {
        super(message);
    }

    public NotImplemented() {
        super();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_IMPLEMENTED;
    }

    @Override
    protected ErrorCode getDefaultErrorCode() {
        return ErrorCode.NOT_IMPLEMENTED;
    }
}
