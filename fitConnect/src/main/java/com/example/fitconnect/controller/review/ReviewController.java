package com.example.fitconnect.controller.review;

import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.domain.review.dto.ReviewRegistrationDto;
import com.example.fitconnect.domain.review.dto.ReviewUpdateDto;
import com.example.fitconnect.service.review.ReviewCreationService;
import com.example.fitconnect.service.review.ReviewUpdateService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/reviews")
public class ReviewController {

    private final ReviewCreationService reviewCreationService;
    private final ReviewUpdateService reviewUpdateService;
    private final CommonService commonService;
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewRegistrationDto reviewRegistrationDto,
           HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        Review review = reviewCreationService.createReview(reviewRegistrationDto, userId);
        return ResponseEntity.ok(review);

    }
    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId,
            @RequestBody ReviewUpdateDto reviewUpdateDto,
            HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        Review updatedReview = reviewUpdateService.updateReview(reviewId, reviewUpdateDto, userId);
        return ResponseEntity.ok(updatedReview);
    }
}
