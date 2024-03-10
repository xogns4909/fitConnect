package com.example.fitconnect.service.review;

import static org.junit.jupiter.api.Assertions.*;

import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.dto.review.response.ReviewResponseDto;
import com.example.fitconnect.repository.review.ReviewRepository;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReviewFindServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ExerciseEventFindService exerciseEventFindService;

    private ReviewFindService reviewFindService;

    @BeforeEach
    public void setUp() {
        reviewFindService = new ReviewFindService(reviewRepository, exerciseEventFindService);
    }

    @Test
    public void findReview_Success() {
        Long exerciseEventId = 1L;
        int page = 1;
        int size = 10;
        String sortBy = "rating";
        Page<Review> expectedPage = new PageImpl<>(Collections.singletonList(new Review()),
                PageRequest.of(page - 1, size), 1);

        when(exerciseEventFindService.findEventByEventId(exerciseEventId)).thenReturn(
                Optional.of(new ExerciseEvent()));
        when(reviewRepository.findReviews(page, size, exerciseEventId, sortBy)).thenReturn(
                expectedPage);

        Page<ReviewResponseDto> result = reviewFindService.findReviewsByExerciseEventIdPageAble(exerciseEventId, page,
                size, sortBy);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
    }
    
    @Test
    public void findReviewByUserId_WithData() {
        Long userId = 1L;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Review mockReview = new Review();
        List<Review> reviews = Collections.singletonList(mockReview);
        Page<Review> expectedPage = new PageImpl<>(reviews, pageable, reviews.size());

        when(reviewRepository.findReviewsByUserId(userId, pageable)).thenReturn(expectedPage);

        Page<ReviewResponseDto> result = reviewFindService.findReviewsByUserId(userId, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
    }



    @Test
    public void event_Not_Found() {
        Long exerciseEventId = 2L;

        when(exerciseEventFindService.findEventByEventId(exerciseEventId)).thenReturn(
                Optional.empty());

        assertThrows(BusinessException.class,
                () -> reviewFindService.findReviewsByExerciseEventIdPageAble(exerciseEventId, 1, 10,
                        "rating"));
    }

    @Test
    public void findReviewByUserIdAndEventId_Exists() {
        Long userId = 1L;
        Long eventId = 1L;
        Review mockReview = new Review();

        when(reviewRepository.findByUserIdAndExerciseEventId(userId, eventId)).thenReturn(Optional.of(mockReview));

        Optional<Review> result = reviewFindService.findReviewByUserIdAndEventId(userId, eventId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(mockReview);
    }

    @Test
    public void findReviewByUserIdAndEventId_NotExist() {
        Long userId = 1L;
        Long eventId = 1L;

        when(reviewRepository.findByUserIdAndExerciseEventId(userId, eventId)).thenReturn(Optional.empty());

        Optional<Review> result = reviewFindService.findReviewByUserIdAndEventId(userId, eventId);

        assertThat(result).isNotPresent();
    }

}
