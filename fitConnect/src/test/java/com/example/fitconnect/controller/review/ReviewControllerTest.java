package com.example.fitconnect.controller.review;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.fitconnect.auth.service.JwtService;
import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.domain.review.dto.ReviewRegistrationDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.service.review.ReviewCreationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
class ReviewControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ReviewCreationService reviewCreationService;

    @Mock
    private CommonService commonService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController, commonService).build();
    }

    @Test
    void createReviewTest() throws Exception {
        ReviewRegistrationDto dto = createTestReviewRegistrationDto();
        Long userId = 1L;
        Review expectedReview = createTestReview(dto);

        given(reviewCreationService.createReview(any(ReviewRegistrationDto.class), anyLong()))
                .willReturn(expectedReview);

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(dto))
                        .param("userId", userId.toString()))
                .andExpect(status().isOk());

        verify(reviewCreationService, times(1)).createReview(any(ReviewRegistrationDto.class),
                anyLong());
    }

    private ReviewRegistrationDto createTestReviewRegistrationDto() {
        return new ReviewRegistrationDto("content", 3.0, 1L);
    }

    private Review createTestReview(ReviewRegistrationDto dto) {
        return new Review(dto.getContent(), dto.getRating(), new User(), new ExerciseEvent());
    }

    private String convertToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    private <T> T convertFromJson(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }
}
