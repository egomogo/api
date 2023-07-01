package com.egomogo.api.global.exception.impl;

import com.egomogo.api.global.exception.base.ApiException;
import com.egomogo.api.global.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public final class NotImplemented extends ApiException {

    public NotImplemented(ErrorCode errorCode, String message) {
        super(HttpStatus.NOT_IMPLEMENTED, errorCode, message);
    }

    public NotImplemented(ErrorCode errorCode) {
        super(HttpStatus.NOT_IMPLEMENTED, errorCode);
    }
}
