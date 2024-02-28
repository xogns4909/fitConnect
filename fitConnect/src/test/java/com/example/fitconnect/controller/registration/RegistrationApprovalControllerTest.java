package com.example.fitconnect.controller.registration;

import static org.junit.jupiter.api.Assertions.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.fitconnect.service.registration.RegistrationApprovalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(RegistrationApprovalController.class)
class RegistrationApprovalControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private RegistrationApprovalService approvalService;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new RegistrationApprovalController(approvalService)).build();
    }

    @Test
    void approveRegistration_Success() throws Exception {
        Long registrationId = 1L;
        Long eventId = 2L;

        mockMvc.perform(post("/api/registrations/" + registrationId + "/approve")
                        .param("eventId", eventId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void denyRegistration_Success() throws Exception {
        Long registrationId = 1L;
        Long adminId = 2L;

        mockMvc.perform(post("/api/registrations/" + registrationId + "/deny")
                        .param("adminId", adminId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
