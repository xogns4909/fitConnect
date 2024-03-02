package com.example.fitconnect.service.review;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.dto.review.request.ReviewUpdateDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.repository.review.ReviewRepository;
import com.example.fitconnect.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

class ReviewUpdateServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewUpdateService reviewUpdateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateReview_Success() {
        Long reviewId = 1L;
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Review review = new Review("content", 3.0, user, new ExerciseEvent());
        ReviewUpdateDto reviewUpdateDto = new ReviewUpdateDto("Updated Content", 5);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));


        reviewUpdateService.updateReview(reviewId, reviewUpdateDto, userId);


        assertThat(review.getContent()).isEqualTo("Updated Content");
        assertThat(review.getRating()).isEqualTo(5);

    }


    @Test
    void UserNotFound_Test() {
        Long reviewId = 1L;
        Long userId = 1L;
        ReviewUpdateDto reviewUpdateDto = new ReviewUpdateDto("Content", 4);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(new Review()));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewUpdateService.updateReview(reviewId, reviewUpdateDto,
                userId)).isInstanceOf(EntityNotFoundException.class);
    }

}
