package com.example.fitconnect.controller.event;

import com.example.fitconnect.auth.service.JwtService;
import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.event.dto.EventDetailDto;
import com.example.fitconnect.domain.event.dto.ExerciseEventRegistrationDto;
import com.example.fitconnect.domain.event.dto.ExerciseEventUpdateDto;
import com.example.fitconnect.domain.event.dto.LocationDto;
import com.example.fitconnect.domain.event.dto.RecruitmentPolicyDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import com.example.fitconnect.service.event.ExerciseEventRegistrationService;
import com.example.fitconnect.service.event.ExerciseEventUpdateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    @MockBean
    private ExerciseEventFindService exerciseEventFindService;
    @MockBean
    private ExerciseEventUpdateService exerciseEventUpdateService;

    private final Long userId = 1L;
    private final Long eventId = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new ExerciseEventController(registrationService, exerciseEventFindService,
                        exerciseEventUpdateService, commonService)).build();
        given(commonService.extractUserIdFromSession(any())).willReturn(userId);
    }

    @Test
    public void registerEventShouldReturnStatusOk() throws Exception {
        ExerciseEventRegistrationDto registrationDto = createEventRegistrationDto();
        setupRegistrationService(registrationDto);

        performPost("/api/events/register", registrationDto)
                .andExpect(status().isOk());
    }

    @Test
    public void findEventShouldReturnStatusOk() throws Exception {
        setupFindService();

        performGet("/api/events", "0", "SOCCER", "Soccer match")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].category").value("SOCCER"));
    }

    @Test
    public void updateEventShouldReturnStatusOk() throws Exception {
        ExerciseEventUpdateDto updateDto = createUpdateDto();
        setupUpdateService(updateDto);

        performPut("/api/events/" + eventId, updateDto)
                .andExpect(status().isOk());
    }

    private void setupRegistrationService(ExerciseEventRegistrationDto dto) {
        given(registrationService.registerEvent(eq(userId), eq(dto)))
                .willReturn(dto.toEntity(new User()));
    }

    private void setupFindService() {
        ExerciseEvent event = createEventRegistrationDto().toEntity(new User());
        Page<ExerciseEvent> expectedPage = new PageImpl<>(Collections.singletonList(event),
                PageRequest.of(0, 10), 1);
        given(exerciseEventFindService.findEvents(any(), any(), anyInt()))
                .willReturn(expectedPage);
    }

    private void setupUpdateService(ExerciseEventUpdateDto dto) {
        given(exerciseEventUpdateService.updateEvent(eq(eventId), eq(dto), eq(userId)))
                .willReturn(new ExerciseEvent());
    }

    private ResultActions performPost(String url, Object dto) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)));
    }

    private ResultActions performGet(String url, String page, String category, String description) throws Exception {
        return mockMvc.perform(get(url)
                .param("page", page)
                .param("category", category)
                .param("description", description));
    }

    private ResultActions performPut(String url, Object dto) throws Exception {
        return mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)));
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

    private ExerciseEventUpdateDto createUpdateDto() {
        return new ExerciseEventUpdateDto(
                new EventDetailDto("Updated Description", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2)),
                new RecruitmentPolicyDto(50, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)),
                new LocationDto(City.SEOUL, "서울시 송파구"),
                Category.BASKETBALL
        );
    }
}