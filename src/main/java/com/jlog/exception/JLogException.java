package com.jlog.exception;

import org.springframework.http.HttpStatusCode;

public class JLogException extends RuntimeException {

    private final JLogErrorCode errorCode;

    public JLogException(String message) {
        super(message);
        errorCode = JLogErrorCode.SERVER_ERROR;
    }

    public JLogException(JLogErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

    public HttpStatusCode httpStatus() {
        return errorCode.httpStatus();
    }

    public JLogErrorCode errorCode() {
        return errorCode;
    }
}
