package com.compono.ibackend.develop.controller.google;

import com.compono.ibackend.develop.service.google.GoogleCalendarService;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/develop/")
public class GoogleCalendarController {

    private final GoogleCalendarService googleCalendarService;

    @GetMapping("v1/holiday")
    public ResponseEntity<Object> getHoliday() {
        Events events = googleCalendarService.findHolidayFromGoogleCalendar();

        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}
