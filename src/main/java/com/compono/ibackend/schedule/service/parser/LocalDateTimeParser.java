package com.compono.ibackend.schedule.service.parser;

import static com.compono.ibackend.common.enumType.ErrorCode.INVALID_DATE_TIME_FORMAT;

import com.compono.ibackend.common.exception.DateFormatException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.springframework.stereotype.Component;

@Component
public class LocalDateTimeParser {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    public LocalDateTime parse(String dateString) {
        try {
            return LocalDateTime.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            throw new DateFormatException(INVALID_DATE_TIME_FORMAT);
        }
    }
}
