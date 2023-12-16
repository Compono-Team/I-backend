package com.compono.ibackend.develop.service.Google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarRequestInitializer;
import com.google.api.services.calendar.model.Events;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoogleCalendarService {
    @Value("${spring.api-key.google.google-calendar-api.key}")
    private String GOOGLE_CALENDAR_API_KEY;

    private final static String GOOGLE_KOREA_HOLIDAY_CALENDAR_ID = "en.south_korea#holiday@group.v.calendar.google.com";
    private final static JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();


    /**
     * Google calendar API를 사용하기 위한 Calendar 서비스를 생성해서 반환하는 함수
     *
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    private Calendar createGoogleCalendarService() throws GeneralSecurityException, IOException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        return new Calendar.Builder(httpTransport, JSON_FACTORY, null)
                .setCalendarRequestInitializer(new CalendarRequestInitializer(GOOGLE_CALENDAR_API_KEY))
                .build();
    }

    /**
     * 공휴일 데이터를 가져오는 API
     *
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public Events findHolidayFromGoogleCalendar() throws IOException, GeneralSecurityException {
        Calendar service = createGoogleCalendarService();

        return service.events().list(GOOGLE_KOREA_HOLIDAY_CALENDAR_ID).execute();
    }
}
