package com.jlog.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import jakarta.validation.ConstraintViolation;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import com.jlog.domain.room.RoomCreateRequest;

class RoomCreateRequestTest extends ValidationTest {

    private RoomCreateRequest sut;

    @DisplayName("성공")
    @Test
    void username() {
        sut = new RoomCreateRequest("username");

        var violations = validator.validate(sut);

        assertThat(violations).isEmpty();
    }

    @DisplayName("실패: username null or empty")
    @ParameterizedTest
    @NullAndEmptySource
    void username_blank(String username) {
        sut = new RoomCreateRequest(username);

        var violations = validator.validate(sut);

        assertThat(violations).isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .containsAnyOf("must not be blank", "size must be between 2 and 16");
    }

    @DisplayName("실패: 2글자 미만 또는 16글자 초과")
    @ParameterizedTest
    @MethodSource
    void invalid_username_length(String username) {
        sut = new RoomCreateRequest(username);

        var violations = validator.validate(sut);

        assertThat(violations).isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .containsOnly("size must be between 2 and 16");
    }

    static Stream<String> invalid_username_length() {
        var length1 = RandomStringUtils.secure().nextAlphanumeric(1);
        var length17 = RandomStringUtils.secure().nextAlphanumeric(17);
        return Stream.of(length1, length17);
    }
}
