package com.egomogo.api.global.exception.model;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SAMPLE("Write a default message if it is required");

    private final String message;

    ErrorCode() {
        this.message = "";
    }
    ErrorCode(String message) {
        this.message = message;
    }

}
