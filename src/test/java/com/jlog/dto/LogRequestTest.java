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
import org.junit.jupiter.params.provider.ValueSource;

import com.jlog.domain.log.LogRequest;

class LogRequestTest extends ValidationTest {

    private static final Long id = 1L;
    private static final String roomCode = "roomCode";
    private static final String username = "username";
    private static final Long expense = 1_000L;
    private static final String memo = "memo";

    private LogRequest sut;

    @DisplayName("success")
    @Test
    void success() {
        sut = new LogRequest(id, roomCode, username, expense, memo);

        var violations = validator.validate(sut);

        assertThat(violations).isEmpty();
    }

    @DisplayName("failure: id null")
    @Test
    void id() {
        sut = new LogRequest(null, roomCode, username, expense, memo);

        var violations = validator.validate(sut);

        assertThat(violations).isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .containsOnly("must not be null");
    }

    @DisplayName("failure: null or empty")
    @ParameterizedTest
    @NullAndEmptySource
    void code_null(String roomCode) {
        sut = new LogRequest(id, roomCode, username, expense, memo);

        var violations = validator.validate(sut);

        assertThat(violations).isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .containsAnyOf("must not be blank", "size must be between 8 and 8");
    }

    @DisplayName("failure: invalid length")
    @ParameterizedTest
    @ValueSource(strings = {"length7", "length009"})
    void invalid_code_length(String roomCode) {
        sut = new LogRequest(id, roomCode, username, expense, memo);

        var violations = validator.validate(sut);

        assertThat(violations).isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .containsOnly("size must be between 8 and 8");
    }

    @DisplayName("failure: null or empty")
    @ParameterizedTest
    @NullAndEmptySource
    void username_blank(String username) {
        sut = new LogRequest(id, roomCode, username, expense, memo);

        var violations = validator.validate(sut);

        assertThat(violations).isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .containsAnyOf("must not be blank", "size must be between 2 and 16");
    }

    @DisplayName("failure: 2글자 미만 또는 16글자 초과")
    @ParameterizedTest
    @MethodSource
    void invalid_username_length(String username) {
        sut = new LogRequest(id, roomCode, username, expense, memo);

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

    @DisplayName("failure: null")
    @Test
    void expense() {
        sut = new LogRequest(id, roomCode, username, null, memo);

        var violations = validator.validate(sut);

        assertThat(violations).isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .containsOnly("must not be null");
    }

    @DisplayName("failure: invalid length exceeding 255")
    @Test
    void memo() {
        String memo = RandomStringUtils.secure().nextAlphanumeric(256);
        sut = new LogRequest(id, roomCode, username, expense, memo);

        var violations = validator.validate(sut);

        assertThat(violations).isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .containsOnly("size must be between 0 and 255");
    }
}
