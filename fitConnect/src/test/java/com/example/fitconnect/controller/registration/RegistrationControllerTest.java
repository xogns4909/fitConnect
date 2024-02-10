package com.example.fitconnect.controller.registration;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.fitconnect.auth.service.JwtService;
import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.dto.registration.response.RegistrationResponseDto;
import com.example.fitconnect.service.registration.RegistrationCancellationService;
import com.example.fitconnect.service.registration.RegistrationCreationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private RegistrationCreationService registrationService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    RegistrationCancellationService cancellationService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new RegistrationController(registrationService,cancellationService)).build();
    }

    @Test
    void createRegistration_Success() throws Exception {
        Long userId = 1L;
        Long eventId = 1L;
        RegistrationResponseDto registration = new RegistrationResponseDto();
        given(registrationService.createRegistration(userId, eventId)).willReturn(registration);

        mockMvc.perform(post("/api/registrations")
                        .param("eventId", eventId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void cancelRegistration_Success() throws Exception {
        Long registrationId = 1L;

        doNothing().when(cancellationService).cancelRegistration(any(), any());

        mockMvc.perform(delete("/api/registrations/" + registrationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
