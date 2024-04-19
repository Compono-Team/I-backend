package com.compono.ibackend.schedule.service;

import static com.compono.ibackend.common.enumType.ErrorCode.INVALID_DATE_TIME_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.compono.ibackend.common.exception.DateFormatException;
import com.compono.ibackend.schedule.service.parser.LocalDateTimeParser;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("LocalDateTime으로 파싱할 시")
class LocalDateParserTest {

    private final LocalDateTimeParser localDateTimeParser = new LocalDateTimeParser();

    @Test
    @DisplayName("LocalDateTime 파싱을 성공한다")
    void success() {
        // given
        String input = "202404171200";

        // when
        LocalDateTime actual = localDateTimeParser.parse(input);

        // then
        assertThat(actual).isEqualTo(LocalDateTime.of(2024, 4, 17, 12, 00));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2024-04-17 12:00", "20240417"})
    @DisplayName("yyyyMMddHHmm 형식이 아닌 경우 예외가 발생한다.")
    void failWithWrongFormat(String input) {
        // given

        // when
        // then
        assertThatThrownBy(() -> localDateTimeParser.parse(input))
                .isInstanceOf(DateFormatException.class)
                .hasMessage(INVALID_DATE_TIME_FORMAT.getMsg());
    }
}
