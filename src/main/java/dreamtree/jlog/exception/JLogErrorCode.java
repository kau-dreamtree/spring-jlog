package dreamtree.jlog.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;

public enum JLogErrorCode {

    ID_MUST_NOT_BE_NULL(BAD_REQUEST, "The given id must not be null"),
    INVALID_EXPENSE_FORMAT(BAD_REQUEST, "An expense must be a positive integer."),
    UNAUTHORIZED_MEMBER_NAME(UNAUTHORIZED, "Unauthorized to join this room."),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "Unauthorized to join this room."),
    MEMBER_NOT_EXISTS(NOT_FOUND, "No such member exists."),
    LOG_NOT_EXISTS(NOT_FOUND, "No such log exists"),
    ROOM_NOT_EXISTS(NOT_FOUND, "No such room exists."),
    OUTPAY_NOT_EXISTS(NOT_FOUND, "No such outpay exists"),
    ROOM_FULL(BAD_REQUEST, "The room has no room."),
    SERVER_ERROR(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getReasonPhrase())
    ;

    private final HttpStatus httpStatus;
    private final String message;

    JLogErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus httpStatus() {
        return httpStatus;
    }

    public String message() {
        return message;
    }
}
