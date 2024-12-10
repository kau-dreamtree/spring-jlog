package com.jlog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class JLogExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(JLogException.class)
    public ResponseEntity<String> handleJLogException(JLogException exception) {
        var status = exception.httpStatus();
        var message = exception.getMessage();
        if (status.is4xxClientError()) {
            log.warn(message);
        }
        if (status.is5xxServerError()) {
            log.error(message, exception);
        }
        return ResponseEntity.status(status).body(message);
    }
}
