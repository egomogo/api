package com.egomogo.api.global.exception.impl;

import com.egomogo.api.global.exception.base.ApiException;
import com.egomogo.api.global.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public final class Unauthorized extends ApiException {

    public Unauthorized(ErrorCode errorCode, String message) {
        super(HttpStatus.UNAUTHORIZED, errorCode, message);
    }

    public Unauthorized(ErrorCode errorCode) {
        super(HttpStatus.UNAUTHORIZED, errorCode);
    }
}
