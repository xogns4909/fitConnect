package com.example.fitconnect.repository.review;

import com.example.fitconnect.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long>,CustomReviewRepository {

}
