package com.example.fitconnect.controller.event;

import com.example.fitconnect.auth.service.JwtService;
import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.dto.EventDetailDto;
import com.example.fitconnect.domain.event.dto.ExerciseEventRegistrationDto;
import com.example.fitconnect.domain.event.dto.LocationDto;
import com.example.fitconnect.domain.event.dto.RecruitmentPolicyDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.service.event.ExerciseEventRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExerciseEventController.class)
public class ExerciseEventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;
    @MockBean
    private ExerciseEventRegistrationService registrationService;

    @MockBean
    private CommonService commonService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ExerciseEventController(registrationService, commonService)).build();
    }

    @Test
    public void registerEvent_Success() throws Exception {
        Long userId = 1L;
        ExerciseEventRegistrationDto registrationDto = createEventRegistrationDto();

        given(commonService.extractUserIdFromSession(any())).willReturn(userId);
        given(registrationService.registerEvent(eq(userId), eq(registrationDto)))
                .willReturn(registrationDto.toEntity(new User()));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/events/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registrationDto)))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static ExerciseEventRegistrationDto createEventRegistrationDto() {
        EventDetailDto eventDetailDto = new EventDetailDto("Description", LocalDateTime.now(),
                LocalDateTime.now().plusHours(2));
        RecruitmentPolicyDto recruitmentPolicyDto = new RecruitmentPolicyDto(30,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        LocationDto locationDto = new LocationDto(City.SEOUL, "서울시 강남구");
        Category category = Category.SOCCER;

        return new ExerciseEventRegistrationDto(eventDetailDto,
                recruitmentPolicyDto, locationDto, category);
    }
}