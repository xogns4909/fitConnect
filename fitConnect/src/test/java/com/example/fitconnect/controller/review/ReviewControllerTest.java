package com.example.fitconnect.controller.review;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.dto.review.request.ReviewRegistrationDto;
import com.example.fitconnect.dto.review.request.ReviewUpdateDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.dto.review.response.ReviewResponseDto;
import com.example.fitconnect.repository.review.ReviewRepository;
import com.example.fitconnect.service.review.ReviewCreationService;
import com.example.fitconnect.service.review.ReviewDeletionService;
import com.example.fitconnect.service.review.ReviewFindService;
import com.example.fitconnect.service.review.ReviewUpdateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    private ReviewUpdateService reviewUpdateService;

    @Mock
    private ReviewDeletionService reviewDeletionService;

    @Mock
    private ReviewFindService reviewFindService;

    @Mock
    private ReviewRepository reviewRepository;
    @InjectMocks
    private ReviewController reviewController;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    void createReviewTest() throws Exception {
        ReviewRegistrationDto dto = createTestReviewRegistrationDto();
        Long userId = 1L;
        Review expectedReview = createTestReview(dto);
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto();

        given(reviewCreationService.createReview(any(ReviewRegistrationDto.class), anyLong()))
                .willReturn(reviewResponseDto);

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(dto))
                        .param("userId", userId.toString()))
                .andExpect(status().isOk());

        verify(reviewCreationService, times(1)).createReview(any(ReviewRegistrationDto.class),
                anyLong());
    }

    @Test
    void updateReviewTest() throws Exception {
        Long reviewId = 1L;
        Long userId = 2L;
        ReviewUpdateDto reviewUpdateDto = new ReviewUpdateDto("Updated Content", 4.0);
        Review updatedReview = createUpdatedTestReview(reviewUpdateDto);
        doNothing().when(reviewUpdateService)
                .updateReview(anyLong(), any(ReviewUpdateDto.class), anyLong());

        mockMvc.perform(patch("/api/reviews/" + reviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(reviewUpdateDto))
                        .sessionAttr("userId", userId))
                .andExpect(status().isNoContent());

    }

    @Test
    void deleteReviewTest() throws Exception {
        Long reviewId = 1L;
        Long userId = 1L;

        ReviewRegistrationDto testReviewRegistrationDto = createTestReviewRegistrationDto();
        Review review = createTestReview(testReviewRegistrationDto);
        given(reviewRepository.findById(reviewId)).willReturn(Optional.of(review));

        mockMvc.perform(delete("/api/reviews/" + reviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr("userId", userId))
                .andExpect(status().isNoContent());

    }

    @Test
    void getReviewsByEventTest() throws Exception {
        Long exerciseEventId = 1L;
        int page = 1;
        int size = 10;
        String sortBy = "rating";
        Page<ReviewResponseDto> expectedPage = new PageImpl<>(
                Collections.singletonList(new ReviewResponseDto()), PageRequest.of(page - 1, size),
                1);

        given(reviewFindService.findReviewsByExerciseEventIdPageAble(exerciseEventId, page, size,
                sortBy)).willReturn(expectedPage);

        mockMvc.perform(get("/api/reviews/events/" + exerciseEventId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sortBy", sortBy))
                .andExpect(status().isOk());

        verify(reviewFindService, times(1)).findReviewsByExerciseEventIdPageAble(exerciseEventId, page, size,
                sortBy);
    }


    private Review createUpdatedTestReview(ReviewUpdateDto dto) {
        return new Review(dto.getContent(), dto.getRating(), new User(), new ExerciseEvent());
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
}
