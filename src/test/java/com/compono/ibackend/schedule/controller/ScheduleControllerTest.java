package com.compono.ibackend.schedule.controller;

import com.compono.ibackend.user.controller.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("[컨트롤러] - 스케줄")
@WebMvcTest(UserController.class)
@AutoConfigureRestDocs
public class ScheduleControllerTest {

    @Autowired private MockMvc mvc;

    @Test
    void addSchedule() {}
}
