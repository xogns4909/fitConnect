package com.example.fitconnect.service.review;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.dto.review.request.ReviewRegistrationDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.dto.review.response.ReviewResponseDto;
import com.example.fitconnect.repository.review.ReviewRepository;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import com.example.fitconnect.service.user.UserFindService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewCreationService {

    private final ReviewRepository reviewRepository;
    private final UserFindService userFindService;
    private final ExerciseEventFindService exerciseEventFindService;

    private final ReviewFindService reviewFindService;

    @Transactional
    public ReviewResponseDto createReview(ReviewRegistrationDto reviewRegistrationDto,
            Long userId) {
        User user = findUser(userId);
        ExerciseEvent exerciseEvent = findExercise(reviewRegistrationDto);

        validateDuplicationReview(user, exerciseEvent);

        Review review = new Review(reviewRegistrationDto.getContent(),
                reviewRegistrationDto.getRating(), user, exerciseEvent);
        Review savedReview = reviewRepository.save(review);
        return ReviewResponseDto.toDto(savedReview);
    }

    private void validateDuplicationReview(User user, ExerciseEvent exerciseEvent) {
        reviewFindService.findReviewByUserIdAndEventId(user.getId(), exerciseEvent.getId())
                .ifPresent(review -> {
                    throw new BusinessException(ErrorMessages.Review_AlREADY_WRITTEN);
                });
    }
    private User findUser(Long userId) {
        return userFindService.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }

    private ExerciseEvent findExercise(ReviewRegistrationDto reviewRegistrationDto) {
        return exerciseEventFindService.findEventByEventId(
                        reviewRegistrationDto.getExerciseEventId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.EVENT_NOT_FOUND));
    }
}
