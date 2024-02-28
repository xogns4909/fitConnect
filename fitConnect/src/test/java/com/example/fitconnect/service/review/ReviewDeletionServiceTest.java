package com.example.fitconnect.service.review;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;;

import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.repository.review.ReviewRepository;
import com.example.fitconnect.domain.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Optional;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewDeletionServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewDeletionService reviewDeletionService;

    @Test
    void deleteReview_Success() {
        Long reviewId = 1L;
        Long userId = 1L;
        Review review = createTestReviewWithUser(userId);

        given(reviewRepository.findById(reviewId)).willReturn(Optional.of(review));

        reviewDeletionService.deleteReview(reviewId, userId);

        then(reviewRepository).should().deleteById(reviewId);
    }

    @Test
    void ReviewNotFound() {
        Long reviewId = 1L;
        Long userId = 1L;

        given(reviewRepository.findById(reviewId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> reviewDeletionService.deleteReview(reviewId, userId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void UnauthorizedUser() {
        Long reviewId = 1L;
        Long userId = 1L;
        Long anotherUserId = 2L;
        Review review = createTestReviewWithUser(anotherUserId);

        given(reviewRepository.findById(reviewId)).willReturn(Optional.of(review));

        assertThatThrownBy(() -> reviewDeletionService.deleteReview(reviewId, userId))
                .isInstanceOf(BusinessException.class);
    }

    private Review createTestReviewWithUser(Long userId) {
        User user = new User();
        user.setId(userId);
        return new Review("review",3.0,user,new ExerciseEvent());
    }
}