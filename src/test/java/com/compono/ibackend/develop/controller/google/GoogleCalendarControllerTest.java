// package com.compono.ibackend.develop.controller.google;
//
// import static org.junit.jupiter.api.Assertions.assertSame;
//
// import com.compono.ibackend.develop.service.google.GoogleCalendarService;
// import com.google.api.services.calendar.model.Events;
// import java.util.Objects;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
//
// @SpringBootTest
// class GoogleCalendarControllerTest {
//
//    private final GoogleCalendarService googleCalendarService;
//
//    private final GoogleCalendarController googleCalendarController;
//
//    @Autowired
//    public GoogleCalendarControllerTest(
//            GoogleCalendarService googleCalendarService,
//            GoogleCalendarController googleCalendarController) {
//        this.googleCalendarService = googleCalendarService;
//        this.googleCalendarController = googleCalendarController;
//    }
//
//    @Test
//    void testGetHoliday() {
//        // Given
//        Events mockEvents = googleCalendarService.findHolidayFromGoogleCalendar();
//
//        // Act
//        ResponseEntity<Object> responseEntity = googleCalendarController.getHoliday();
//
//        // Assert
//        assertSame(HttpStatus.OK, responseEntity.getStatusCode());
//        assertSame(
//                mockEvents.getClass(),
// Objects.requireNonNull(responseEntity.getBody()).getClass());
//    }
// }
