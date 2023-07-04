package com.egomogo.api.global.exception.model;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // Not Unique to use
    NOT_FOUND,
    BAD_REQUEST,
    FORBIDDEN,
    INTERNAL_SERVER_ERROR,
    NOT_IMPLEMENTED,
    UNAUTHORIZED,

    // Unique for each APIs
    REQUIRED_PARAMETERS("요청에 필요한 모든 정보를 입력해주세요."),
    SOMETHING_BAD("Write a default message if it is required"); // this is a sample

    private final String message;

    ErrorCode() {
        this.message = "";
    }
    ErrorCode(String message) {
        this.message = message;
    }

}
