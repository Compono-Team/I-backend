package com.compono.ibackend.develop.service.google;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.api.services.calendar.model.Events;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GoogleCalendarServiceTest {

    private final GoogleCalendarService googleCalendarService;

    @Autowired
    public GoogleCalendarServiceTest(GoogleCalendarService googleCalendarService) {
        this.googleCalendarService = googleCalendarService;
    }

    @Test
    void testFindHolidayFromGoogleCalendar() {
        // Given
        Events events = googleCalendarService.findHolidayFromGoogleCalendar();
        String googleCalendarKoreaSummary = "Holidays in South Korea";

        // Act
        String eventSummary = events.getSummary();
        System.out.println(eventSummary);
        // Assert
        assertEquals(googleCalendarKoreaSummary, eventSummary);
    }
}
