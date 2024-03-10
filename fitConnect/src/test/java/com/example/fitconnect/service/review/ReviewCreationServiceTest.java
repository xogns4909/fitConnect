package com.example.fitconnect.service.review;

import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.dto.event.request.EventDetailDto;
import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
import com.example.fitconnect.dto.event.request.LocationDto;
import com.example.fitconnect.dto.event.request.RecruitmentPolicyDto;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.dto.review.request.ReviewRegistrationDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.dto.review.response.ReviewResponseDto;
import com.example.fitconnect.repository.review.ReviewRepository;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import com.example.fitconnect.service.user.UserFindService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private ReviewFindService reviewFindService;


    @ParameterizedTest
    @CsvSource({"Great review, 4.5, 2",
            "Invalid user, 3.0, 1",
            "Another review, 2.0, 3"})
    public void createReview_Success(String content, double rating, Long exerciseEventId) {
        Long userId = 1L;
        ReviewRegistrationDto registrationDto = new ReviewRegistrationDto(content, rating,
                exerciseEventId);
        User user = new User(new UserBaseInfo("test@naver.com","hoon","url"),Role.MEMBER);
        ExerciseEvent exerciseEvent = createExerciseEvent(user);

        when(userFindService.findUserByUserId(userId)).thenReturn(Optional.of(user));
        when(exerciseEventFindService.findEventByEventId(exerciseEventId)).thenReturn(
                Optional.of(exerciseEvent));
        when(reviewRepository.save(any(Review.class))).thenReturn(new Review(content,rating,user,exerciseEvent));
        ReviewResponseDto createdReview = reviewCreationService.createReview(registrationDto, userId);

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

    private static ExerciseEvent createExerciseEvent(User user) {
        List<Image> images = new ArrayList<>();
        EventDetailDto eventDetailDto = new EventDetailDto("title","Description", LocalDateTime.now(),
                LocalDateTime.now().plusHours(2));
        RecruitmentPolicyDto recruitmentPolicyDto = new RecruitmentPolicyDto(30,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        LocationDto locationDto = new LocationDto(City.SEOUL, "서울시 강남구");
        Category category = Category.SOCCER;
        ExerciseEvent exerciseEvent = new ExerciseEventRegistrationDto(eventDetailDto,
                recruitmentPolicyDto, locationDto, category).toEntity(user,images);
        return exerciseEvent;
    }
    @Test
    public void createReview_ReviewAlreadyExists() {
        Long userId = 1L;
        Long exerciseEventId = 1L;
        ReviewRegistrationDto registrationDto = new ReviewRegistrationDto("Great review", 5.0, exerciseEventId);
        User user = new User(new UserBaseInfo("user@example.com", "User", "userPic.jpg"), Role.MEMBER);

        ExerciseEvent exerciseEvent = createExerciseEvent(user);

        Review existingReview = new Review("Existing review", 4.0, user, exerciseEvent);

        when(userFindService.findUserByUserId(anyLong())).thenReturn(Optional.of(user));
        when(exerciseEventFindService.findEventByEventId(anyLong())).thenReturn(Optional.of(exerciseEvent));
        when(reviewFindService.findReviewByUserIdAndEventId(any(), any())).thenReturn(Optional.of(existingReview));


        assertThatThrownBy(() -> reviewCreationService.createReview(registrationDto, userId))
                .isInstanceOf(BusinessException.class);
    }

}

