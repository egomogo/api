package com.egomogo.api.global.exception.model;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // Basic Code
    NOT_FOUND,
    BAD_REQUEST,
    FORBIDDEN,
    INTERNAL_SERVER_ERROR,
    NOT_IMPLEMENTED,
    UNAUTHORIZED,

    // Unique for each APIs
    SOMETHING_BAD("Write a default message if it is required"); // this is a sample

    private final String message;

    ErrorCode() {
        this.message = "";
    }
    ErrorCode(String message) {
        this.message = message;
    }

}
