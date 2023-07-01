package com.egomogo.api.global.exception.base;

import com.egomogo.api.global.exception.model.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ApiException extends RuntimeException{
    private final ErrorCode errorCode;

    public ApiException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
        this.errorCode = getDefaultErrorCode();
    }

    public ApiException() {
        this.errorCode = getDefaultErrorCode();
    }

    public abstract HttpStatus getHttpStatus();
    protected abstract ErrorCode getDefaultErrorCode();

}
