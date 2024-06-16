package shop.dreamtree.jlog.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;

public enum JLogErrorCode {

    INVALID_EXPENSE_FORMAT(BAD_REQUEST, "Expense can't be less than zero"),
    UNAUTHORIZED_USERNAME(UNAUTHORIZED, "Unauthorized to join this room."),
    LOG_NOT_EXISTS(NOT_FOUND, "No such log exists"),
    ROOM_NOT_EXISTS(NOT_FOUND, "No such room exists."),
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
