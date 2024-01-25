package com.example.fitconnect.repository.review;

import com.example.fitconnect.domain.review.Review;
import org.springframework.data.domain.Page;

public interface CustomReviewRepository {
    Page<Review> findReviews(int page, int size,Long exerciseEventId, String sortBy);
}
