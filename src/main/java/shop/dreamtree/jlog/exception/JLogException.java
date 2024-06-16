package shop.dreamtree.jlog.exception;

import static shop.dreamtree.jlog.exception.JLogErrorCode.SERVER_ERROR;

import java.util.function.Supplier;

import org.springframework.http.HttpStatusCode;

public class JLogException extends RuntimeException {

    private final JLogErrorCode errorCode;

    public JLogException(String message) {
        super(message);
        errorCode = SERVER_ERROR;
    }

    public JLogException(JLogErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

    public static Supplier<JLogException> getFrom(JLogErrorCode errorCode) {
        return () -> new JLogException(errorCode);
    }

    public HttpStatusCode getHttpStatus() {
        return errorCode.httpStatus();
    }
}
