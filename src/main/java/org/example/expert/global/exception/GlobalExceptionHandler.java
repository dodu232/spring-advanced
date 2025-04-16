package org.example.expert.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String> handleApiException(ApiException e) {
        log.error("ApiException occurred. type={} message={} className={}", e.getErrorType(),
            e.getMessage(), e.getClass().getName());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}

