package com.example.fitconnect.service.review;


import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.repository.review.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewDeletionService {

    private final ReviewRepository reviewRepository;


    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.REVIEW_NOT_FOUND));

        if (!review.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorMessages.UNAUTHORIZED_USER);
        }

        reviewRepository.deleteById(reviewId);
    }

    @Transactional
    public void deleteReviews(List<Review> reviews) {
        if(reviews != null || !reviews.isEmpty()) {
            reviewRepository.deleteAll(reviews);
        }
    }
}

