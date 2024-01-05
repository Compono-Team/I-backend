package com.compono.ibackend.develop.service.google;

import com.compono.ibackend.common.exception.CustomException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarRequestInitializer;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoogleCalendarService {

    private static final String GOOGLE_KOREA_HOLIDAY_CALENDAR_ID =
            "en.south_korea#holiday@group.v.calendar.google.com";
    private static final JsonFactory JSONFACTORY = GsonFactory.getDefaultInstance();

    @Value("${spring.api-key.google.google-calendar-api.key}")
    private String googleCalendarApiKey;

    /**
     * Google calendar API를 사용하기 위한 Calendar 서비스를 생성해서 반환하는 함수
     *
     * @return
     */
    private Calendar createGoogleCalendarService() {
        try {
            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            return new Calendar.Builder(httpTransport, JSONFACTORY, null)
                    .setCalendarRequestInitializer(
                            new CalendarRequestInitializer(googleCalendarApiKey))
                    .build();
        } catch (Exception ex) {
            throw new CustomException(ex);
        }
    }

    /**
     * 공휴일 데이터를 가져오는 API
     *
     * @return
     */
    public Events findHolidayFromGoogleCalendar() {
        try {
            Calendar service = createGoogleCalendarService();

            return service.events().list(GOOGLE_KOREA_HOLIDAY_CALENDAR_ID).execute();
        } catch (Exception ex) {
            throw new CustomException(ex);
        }
    }
}
