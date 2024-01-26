package com.example.fitconnect.service.review;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.domain.review.dto.ReviewUpdateDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.repository.review.ReviewRepository;
import com.example.fitconnect.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewUpdateService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Review updateReview(Long reviewId, ReviewUpdateDto reviewUpdateDto, Long userId) {
        Review review = findReview(reviewId);

        User currentUser = findUser(userId);

        review.updateReview(reviewUpdateDto, currentUser);

        return reviewRepository.save(review);
    }

    private User findUser(Long currentUserId) {
        return userRepository.findById(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }

    private Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.REVIEW_NOT_FOUND));
    }
}
