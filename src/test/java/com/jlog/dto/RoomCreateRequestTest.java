package com.jlog.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class RoomCreateRequestTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    private RoomCreateRequest sut;

    @BeforeAll
    static void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        validatorFactory.close();
    }

    @DisplayName("성공")
    @Test
    void username() {
        sut = new RoomCreateRequest("username");

        var violations = validator.validate(sut);

        assertThat(violations).isEmpty();
    }

    @DisplayName("실패: null")
    @ParameterizedTest
    @NullAndEmptySource
    void username_blank(String username) {
        sut = new RoomCreateRequest(username);

        var violations = validator.validate(sut);

        assertThat(violations).isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .containsAnyOf("must not be blank", "size must be between 2 and 16");
    }

    @DisplayName("실패: 16글자 초과")
    @Test
    void username_too_short() {
        var username = RandomStringUtils.secure().nextAlphanumeric(17);
        sut = new RoomCreateRequest(username);

        var violations = validator.validate(sut);

        assertThat(violations).isNotEmpty()
                .first()
                .extracting(ConstraintViolation::getMessage)
                .isEqualTo("size must be between 2 and 16");
    }
}
