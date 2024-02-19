package com.example.fitconnect.controller.registration;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.fitconnect.auth.service.JwtService;
import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.dto.registration.response.RegistrationResponseDto;
import com.example.fitconnect.service.registration.RegistrationCancellationService;
import com.example.fitconnect.service.registration.RegistrationCreationService;
import com.example.fitconnect.service.registration.RegistrationFindService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
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

    @MockBean
    RegistrationFindService registrationFindService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                        new RegistrationController(registrationService, cancellationService,
                                registrationFindService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void createRegistration_Success() throws Exception {
        Long userId = 1L;
        Long eventId = 1L;
        RegistrationResponseDto registration = new RegistrationResponseDto();
        given(registrationService.createRegistration(userId, eventId)).willReturn(registration);

        mockMvc.perform(post("/api/registrations/" + eventId)
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

    @Test
    public void getRegistrationsByEventId_Success() throws Exception {

        Long eventId = 1L;
        RegistrationResponseDto mockRegistrationResponseDto = new RegistrationResponseDto();
        Page<RegistrationResponseDto> mockPage = new PageImpl<>(
                List.of(mockRegistrationResponseDto));

        when(registrationFindService.findByEventId(eq(eventId), any(Pageable.class))).thenReturn(
                mockPage);
        mockMvc.perform(get("/api/registrations/1?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON));
//                .andExpect(status().isOk());
    }
}
