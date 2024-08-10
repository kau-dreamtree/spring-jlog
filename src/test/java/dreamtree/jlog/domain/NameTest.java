package dreamtree.jlog.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static dreamtree.jlog.exception.JLogErrorCode.INVALID_NAME_FORMAT;
import static dreamtree.jlog.exception.JLogErrorCode.NAME_MUST_NOT_BE_NULL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import dreamtree.jlog.exception.JLogException;

class NameTest {

    @Test
    void nullName() {
        assertThatThrownBy(() -> new Name(null))
                .isInstanceOf(JLogException.class)
                .hasMessage(NAME_MUST_NOT_BE_NULL.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "*", "_", "1", "1a"})
    void invalidNames(String name) {
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(JLogException.class)
                .hasMessage(INVALID_NAME_FORMAT.message());
    }
}
