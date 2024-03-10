package com.example.fitconnect.repository.review;

import com.example.fitconnect.domain.review.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomReviewRepository {
    Page<Review> findReviews(int page, int size,Long exerciseEventId, String sortBy);

    Page<Review> findReviewsByUserId(Long userId, Pageable pageable);

    Optional<Review> findByUserIdAndExerciseEventId(Long userId, Long eventId);

    List<Review> findByExerciseEventId(Long eventId);
}
