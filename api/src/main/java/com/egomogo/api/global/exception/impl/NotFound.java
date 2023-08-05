package com.egomogo.api.global.exception.impl;

import com.egomogo.api.global.exception.base.ApiException;
import com.egomogo.api.global.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public final class NotFound extends ApiException {
    public NotFound(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public NotFound(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFound(String message) {
        super(message);
    }

    public NotFound() {
        super();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    protected ErrorCode getDefaultErrorCode() {
        return ErrorCode.NOT_FOUND;
    }


}
