package com.jlog.exception;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

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

    @Override
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        if (request instanceof ServletWebRequest servletWebRequest) {
            HttpServletResponse response = servletWebRequest.getResponse();
            if (response != null && response.isCommitted()) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Response already committed. Ignoring: " + ex);
                }
                return null;
            }
        }

        if (body == null && ex instanceof ErrorResponse errorResponse) {
            body = errorResponse.updateAndGetBody(getMessageSource(), LocaleContextHolder.getLocale());
        }

        if (statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR) && body == null) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        log.error(ex.getMessage(), ex);

        return createResponseEntity(body, headers, statusCode, request);
    }
}
