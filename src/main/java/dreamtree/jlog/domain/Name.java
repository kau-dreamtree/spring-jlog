package dreamtree.jlog.domain;

import static dreamtree.jlog.exception.JLogErrorCode.INVALID_NAME_FORMAT;
import static dreamtree.jlog.exception.JLogErrorCode.NAME_MUST_NOT_BE_NULL;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import dreamtree.jlog.exception.JLogException;

@Embeddable
public class Name {

    private static final String PATTERN = "^(?!\\d)[^\\W_]{1,16}$";

    @Column(name = "name", length = 16)
    private String value;

    protected Name() {
    }

    public Name(String value) {
        this.value = validate(value);
    }

    private String validate(String value) {
        if (requireNonNull(value).matches(PATTERN)) {
            return value;
        }
        throw new JLogException(INVALID_NAME_FORMAT);
    }

    private String requireNonNull(String value) {
        try {
            return Objects.requireNonNull(value);
        } catch (NullPointerException e) {
            throw new JLogException(NAME_MUST_NOT_BE_NULL);
        }
    }

    public String value() {
        return value;
    }
}
