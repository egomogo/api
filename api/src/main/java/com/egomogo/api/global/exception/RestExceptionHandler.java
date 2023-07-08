package com.egomogo.api.global.exception;

import com.egomogo.api.global.exception.base.ApiException;
import com.egomogo.api.global.exception.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleRestException(ApiException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorResponse(ex, request.getRequestURI()));
    }
}
