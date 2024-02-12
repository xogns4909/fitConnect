package com.example.fitconnect.controller.mypage;

import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.dto.registration.response.RegistrationResponseDto;
import com.example.fitconnect.dto.review.response.ReviewResponseDto;
import com.example.fitconnect.service.review.ReviewFindService;
import com.example.fitconnect.service.registration.RegistrationFindService;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import com.example.fitconnect.config.service.CommonService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class MyPageControllerTest {

    @Mock
    private ReviewFindService reviewFindService;
    @Mock
    private RegistrationFindService registrationFindService;
    @Mock
    private ExerciseEventFindService exerciseEventFindService;
    @Mock
    private HttpSession session;

    @InjectMocks
    private MyPageController myPageController;

    private MockMvc mockMvc;
    private Long userId;
    private Pageable pageable;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(myPageController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        pageable = PageRequest.of(0, 20);
        userId = 1L;
    }


    @Test
    public void getUserReviewsTest() throws Exception {
        Page<ReviewResponseDto> mockPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(reviewFindService.findReviewsByUserId(userId, pageable)).thenReturn(mockPage);

        mockMvc.perform(get("/mypage/reviews")
                        .param("page", "0")
                        .param("size", "20")
                        .param("userId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserRegistrationsTest() throws Exception {
        Page<RegistrationResponseDto> mockPage = new PageImpl<>(Collections.emptyList(), pageable,
                0);
        when(registrationFindService.findRegistrationByUserId(userId, pageable)).thenReturn(
                mockPage);

        mockMvc.perform(get("/mypage/registrations")
                        .sessionAttr("session", new MockHttpSession())
                        .param("page", "0")
                        .param("size", "20")
                        .param("userId", "1"))

                .andExpect(status().isOk());
    }

    @Test
    public void getUserEventsTest() throws Exception {
        Page<ExerciseEvent> mockPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(exerciseEventFindService.findEventByUserId(userId, pageable)).thenReturn(mockPage);

        mockMvc.perform(get("/mypage/events")
                        .sessionAttr("session", new MockHttpSession())
                        .param("page", "0")
                        .param("size", "20")
                        .param("userId", "1"))
                .andExpect(status().isOk());
    }
}
