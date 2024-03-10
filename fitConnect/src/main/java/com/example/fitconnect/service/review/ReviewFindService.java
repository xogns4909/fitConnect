package com.example.fitconnect.service.review;


import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.dto.review.response.ReviewResponseDto;
import com.example.fitconnect.repository.review.ReviewRepository;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewFindService {

    private final ReviewRepository reviewRepository;

    private final ExerciseEventFindService exerciseEventFindService;

    public Page<ReviewResponseDto> findReviewsByExerciseEventIdPageAble(Long exerciseEventId, int page, int size,
            String sortBy) {
        exerciseEventFindService.findEventByEventId(exerciseEventId).orElseThrow(() -> new BusinessException(
                        ErrorMessages.EVENT_NOT_FOUND));
        Page<Review> reviews = reviewRepository.findReviews(page, size, exerciseEventId, sortBy);
        return reviews.map(ReviewResponseDto::toDto);
    }

    public Page<ReviewResponseDto> findReviewsByUserId(Long userId, Pageable pageable){
        return  reviewRepository.findReviewsByUserId(userId,pageable).map(ReviewResponseDto::toDto);
    }

    public Optional<Review> findReviewByUserIdAndEventId(Long userId,Long eventId){
        return reviewRepository.findByUserIdAndExerciseEventId(userId,eventId);
    }

    public List<Review> findReviewsByEventId(Long eventId) {
        return reviewRepository.findByExerciseEventId(eventId);
    }
}
