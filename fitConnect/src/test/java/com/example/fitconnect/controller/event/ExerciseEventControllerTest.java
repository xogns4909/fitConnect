package com.example.fitconnect.controller.event;

import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.dto.event.request.EventDetailDto;
import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
import com.example.fitconnect.dto.event.request.ExerciseEventUpdateDto;
import com.example.fitconnect.dto.event.request.LocationDto;
import com.example.fitconnect.dto.event.request.RecruitmentPolicyDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.dto.event.response.EventDetailResponseDto;
import com.example.fitconnect.dto.event.response.EventResponseDto;
import com.example.fitconnect.service.event.ExerciseEventDeleteService;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import com.example.fitconnect.service.event.ExerciseEventRegistrationService;
import com.example.fitconnect.service.event.ExerciseEventUpdateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExerciseEventController.class)
public class ExerciseEventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExerciseEventRegistrationService registrationService;
    @MockBean
    private ExerciseEventFindService exerciseEventFindService;
    @MockBean
    private ExerciseEventUpdateService exerciseEventUpdateService;

    @MockBean
    private ExerciseEventDeleteService exerciseEventDeleteService;

    private final Long userId = 1L;
    private final Long eventId = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new ExerciseEventController(registrationService, exerciseEventFindService,
                        exerciseEventUpdateService, exerciseEventDeleteService)).build();

    }

    @Test
    public void registerEventShouldReturnStatusOk() throws Exception {
        ExerciseEventRegistrationDto registrationDto = createEventRegistrationDto();
        MockMultipartFile file = new MockMultipartFile("multipartFileList", "filename.txt",
                "text/plain", "some xml".getBytes());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String eventDtoJson = objectMapper.writeValueAsString(registrationDto);
        MockMultipartFile eventDto = new MockMultipartFile("registrationDto", "",
                "application/json", eventDtoJson.getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/events/register")
                        .file(file)
                        .file(eventDto)
                        .param("userId", userId.toString()))
                .andExpect(status().isOk());
    }


    @Test
    public void findEvent_Success() throws Exception {
        setupFindService();

        performGet("/api/events", "0", "SOCCER", "Soccer match")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].category").value("축구"));
    }

    @Test
    public void updateEvent_Success() throws Exception {
        ExerciseEventUpdateDto updateDto = createUpdateDto();
        Long eventId = 1L;
        setupUpdateService(updateDto);

        mockMvc.perform(patch("/api/events/" + eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteEvent_Success() throws Exception {
        Long eventId = 1L;
        setupDeleteService(eventId);

        performDelete("/api/events/" + eventId)
                .andExpect(status().isNoContent());
    }

    @Test
    public void getEventDetail_Success() throws Exception {
        User user = new User(new UserBaseInfo("test@naver.com", "test", "url"), Role.MEMBER);
        List<Image> images = new ArrayList<>();
        ExerciseEvent event = createEventRegistrationDto().toEntity(user, images);
        EventDetailResponseDto eventDetailResponseDto = new EventDetailResponseDto().toDto(event);
        given(exerciseEventFindService.findEventDetail(eventId)).willReturn(eventDetailResponseDto);
        mockMvc.perform(get("/api/events/" + eventId + "/detail"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value(event.getCategory().toString()));
    }


    private void setupRegistrationService(ExerciseEventRegistrationDto dto) {
        given(registrationService.registerEvent(eq(userId), eq(dto), any()))
                .willReturn(dto.toEntity(new User(), new ArrayList<>()));
    }

    private void setupFindService() {
        User user = new User(new UserBaseInfo("test@naver.com", "test", "url"), Role.MEMBER);
        ExerciseEvent event = createEventRegistrationDto().toEntity(user, new ArrayList<>());
        Page<EventResponseDto> expectedPage = new PageImpl<>(Collections.singletonList(event),
                PageRequest.of(0, 10), 1).map(EventResponseDto::toDto);
        given(exerciseEventFindService.findEvents(any(), any(), any(), any(), anyInt()))
                .willReturn(expectedPage);
    }

    private void setupUpdateService(ExerciseEventUpdateDto dto) {
        given(exerciseEventUpdateService.updateEvent(eq(eventId), eq(dto), eq(userId)))
                .willReturn(new ExerciseEvent());
    }

    private void setupDeleteService(Long eventId) {
        doNothing().when(exerciseEventDeleteService).deleteEvent(eq(eventId), eq(userId));
    }

    private ResultActions performPost(String url, Object dto) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)));
    }

    private ResultActions performGet(String url, String page, String category, String description)
            throws Exception {
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

    private ResultActions performDelete(String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(url));
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
        EventDetailDto eventDetailDto = new EventDetailDto("title", "Description",
                LocalDateTime.now(),
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
                new EventDetailDto("title", "Updated Description", LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(1).plusHours(2)),
                new RecruitmentPolicyDto(50, LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(2)),
                new LocationDto(City.SEOUL, "서울시 송파구"),
                Category.BASKETBALL
        );
    }
}