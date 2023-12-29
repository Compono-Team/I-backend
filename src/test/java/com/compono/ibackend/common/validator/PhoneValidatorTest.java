package com.compono.ibackend.common.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import jakarta.validation.ConstraintValidatorContext;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("[Validator] 전화번호 유효성 검사")
class PhoneValidatorTest {

    private PhoneValidator phoneValidator;

    @BeforeEach
    void setup() {
        phoneValidator = new PhoneValidator();
    }

    @DisplayName("정상적인 전화번호를 검사하면 true 반환합니다.")
    @ParameterizedTest
    @MethodSource("validPhoneNumberValuesAndContext")
    void givenValidPhoneNumber_whenValidatePhoneNumber_thenTrue(
            String value, ConstraintValidatorContext context) {
        assertTrue(phoneValidator.isValid(value, context));
    }

    @DisplayName("비정상적인 전화번호를 검사하면 false 반환합니다.")
    @ParameterizedTest
    @MethodSource("inValidPhoneNumberValuesAndContext")
    void givenInvalidPhoneNumber_whenValidatePhoneNumber_thenFalse(
            String value, ConstraintValidatorContext context) {
        assertFalse(phoneValidator.isValid(value, context));
    }

    private static Stream<Arguments> validPhoneNumberValuesAndContext() {
        return Stream.of(
                arguments("010-1234-5678", null),
                arguments("011-123-5678", null),
                arguments("016-1234-5678", null));
    }

    private static Stream<Arguments> inValidPhoneNumberValuesAndContext() {
        return Stream.of(
                arguments("012-1234-5678", null),
                arguments("01012345678", null),
                arguments("010-12345-678", null),
                arguments("", null),
                arguments(null, null));
    }
}
