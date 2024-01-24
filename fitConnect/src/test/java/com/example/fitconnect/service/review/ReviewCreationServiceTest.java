package com.example.fitconnect.service.review;

import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.domain.review.dto.ReviewRegistrationDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import com.example.fitconnect.repository.review.ReviewRepository;
import com.example.fitconnect.repository.user.UserRepository;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import com.example.fitconnect.service.user.UserFindService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewCreationServiceTest {

    @InjectMocks
    private ReviewCreationService reviewCreationService;

    @Mock
    private UserFindService userFindService;

    @Mock
    private ExerciseEventFindService exerciseEventFindService;

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private ExerciseEventRepository exerciseEventRepository;


    @ParameterizedTest
    @CsvSource({"Great review, 4.5, 2",
            "Invalid user, 3.0, 1",
            "Another review, 2.0, 3"})
    public void createReview_Success(String content, double rating, Long exerciseEventId) {
        Long userId = 1L;
        ReviewRegistrationDto registrationDto = new ReviewRegistrationDto(content, rating,
                exerciseEventId);
        User user = new User();
        ExerciseEvent exerciseEvent = new ExerciseEvent();

        when(userFindService.findUserByUserId(userId)).thenReturn(Optional.of(user));
        when(exerciseEventFindService.findEventByEventId(exerciseEventId)).thenReturn(
                Optional.of(exerciseEvent));
        when(reviewRepository.save(any(Review.class))).thenReturn(new Review());

        Review createdReview = reviewCreationService.createReview(registrationDto, userId);

        assertThat(createdReview).isNotNull();
    }

    @Test
    public void User_Not_Found() {
        Long invalidUserId = 999L;
        ReviewRegistrationDto registrationDto = new ReviewRegistrationDto("Invalid user", 3.0, 1L);

        when(userFindService.findUserByUserId(invalidUserId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewCreationService.createReview(registrationDto, invalidUserId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void Event_Not_Found() {
        Long userId = 1L;
        Long invalidEventId = 999L;
        ReviewRegistrationDto registrationDto = new ReviewRegistrationDto("Invalid Event", 3.0,
                invalidEventId);

        when(userFindService.findUserByUserId(userId)).thenReturn(Optional.of(new User()));
        when(exerciseEventFindService.findEventByEventId(invalidEventId)).thenReturn(
                Optional.empty());

        assertThatThrownBy(() -> reviewCreationService.createReview(registrationDto, userId))
                .isInstanceOf(EntityNotFoundException.class);
    }

}

