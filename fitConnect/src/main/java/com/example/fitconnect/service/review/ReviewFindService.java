package com.example.fitconnect.service.review;


import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.dto.review.response.ReviewResponseDto;
import com.example.fitconnect.repository.review.ReviewRepository;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewFindService {

    private final ReviewRepository reviewRepository;

    private final ExerciseEventFindService exerciseEventFindService;

    public Page<ReviewResponseDto> findReviewsByExerciseEvent(Long exerciseEventId, int page, int size,
            String sortBy) {
        exerciseEventFindService.findEventByEventId(exerciseEventId).orElseThrow(() -> new BusinessException(
                        ErrorMessages.EVENT_NOT_FOUND));
        Page<Review> reviews = reviewRepository.findReviews(page, size, exerciseEventId, sortBy);
        return reviews.map(ReviewResponseDto::toDto);
    }

    public Page<ReviewResponseDto> findReviewsByUserId(Long userId, Pageable pageable){
        return  reviewRepository.findReviewsByUserId(userId,pageable).map(ReviewResponseDto::toDto);
    }
}
