package com.egomogo.api.global.exception.impl;

import com.egomogo.api.global.exception.base.ApiException;
import com.egomogo.api.global.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public final class Forbidden extends ApiException {

    public Forbidden(ErrorCode errorCode, String message) {
        super(HttpStatus.FORBIDDEN, errorCode, message);
    }

    public Forbidden(ErrorCode errorCode) {
        super(HttpStatus.FORBIDDEN, errorCode);
    }
}
