package com.compono.ibackend.common.utils;

import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.CustomException;
import com.google.api.services.calendar.model.EventDateTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class DateUtils {

    /**
     * EventDateTime 에서 LocalDateTime으로 변환하는 함수
     *
     * @param eventDateTime
     * @return
     */
    public static LocalDateTime convertEventDateTimeToLocalDateTime(EventDateTime eventDateTime) {
        if (eventDateTime.getDateTime() != null) {
            return LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(eventDateTime.getDateTime().getValue()),
                    ZoneId.systemDefault()
            );
        } else if (eventDateTime.getDate() != null) {
            return LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(eventDateTime.getDate().getValue()),
                    ZoneId.systemDefault()
            ).withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_EVENTDATETIME);
        }
    }
}
