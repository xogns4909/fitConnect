package com.example.fitconnect.controller.review;

import com.example.fitconnect.config.annotation.CurrentUserId;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.domain.review.dto.ReviewRegistrationDto;
import com.example.fitconnect.domain.review.dto.ReviewUpdateDto;
import com.example.fitconnect.dto.review.response.ReviewResponseDto;
import com.example.fitconnect.service.review.ReviewCreationService;
import com.example.fitconnect.service.review.ReviewDeletionService;
import com.example.fitconnect.service.review.ReviewFindService;
import com.example.fitconnect.service.review.ReviewUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/reviews")
public class ReviewController {

    private final ReviewCreationService reviewCreationService;
    private final ReviewUpdateService reviewUpdateService;
    private final ReviewDeletionService reviewDeletionService;
    private final ReviewFindService reviewFindService;
    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewRegistrationDto reviewRegistrationDto,
           @CurrentUserId Long userId) {
        ReviewResponseDto review = reviewCreationService.createReview(reviewRegistrationDto, userId);
        return ResponseEntity.ok(review);

    }
    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable Long reviewId,
            @RequestBody ReviewUpdateDto reviewUpdateDto,
            @CurrentUserId Long userId) {
        reviewUpdateService.updateReview(reviewId, reviewUpdateDto, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId, @CurrentUserId Long userId) {
        reviewDeletionService.deleteReview(reviewId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<Page<Review>> getReviewsByEvent(@PathVariable Long eventId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "default") String sortBy) {
        Page<Review> reviews = reviewFindService.findReviewsByExerciseEvent(eventId, page, size, sortBy);
        return ResponseEntity.ok(reviews);
    }
}
