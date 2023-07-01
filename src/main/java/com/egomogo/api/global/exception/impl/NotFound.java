package com.egomogo.api.global.exception.impl;

import com.egomogo.api.global.exception.base.ApiException;
import com.egomogo.api.global.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public final class NotFound extends ApiException {

    public NotFound(ErrorCode errorCode, String message) {
        super(HttpStatus.NOT_FOUND, errorCode, message);
    }

    public NotFound(ErrorCode errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }
}
