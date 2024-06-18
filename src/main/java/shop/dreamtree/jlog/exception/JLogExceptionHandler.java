package shop.dreamtree.jlog.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JLogExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(JLogException.class)
    public ResponseEntity<String> handleJLogException(JLogException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(e.httpStatus()).body(e.getMessage());
    }
}
