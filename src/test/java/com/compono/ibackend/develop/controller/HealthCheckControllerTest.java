package com.compono.ibackend.develop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(HealthCheckController.class)
public class HealthCheckControllerTest {

    @Autowired private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void healthCheckTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/develop/v1/health"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("OK"));
    }
}
