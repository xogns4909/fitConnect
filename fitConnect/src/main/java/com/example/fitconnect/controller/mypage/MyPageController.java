package com.example.fitconnect.controller.mypage;

import com.example.fitconnect.config.annotation.CurrentUserId;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.dto.registration.response.RegistrationResponseDto;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import com.example.fitconnect.service.registration.RegistrationFindService;
import com.example.fitconnect.service.review.ReviewFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final ReviewFindService reviewFindService;
    private final RegistrationFindService registrationFindService;
    private final ExerciseEventFindService exerciseEventFindService;

    @GetMapping("/reviews")
    public ResponseEntity<Page<Review>> getUserReviews(Pageable pageable,
            @CurrentUserId Long userId) {
        Page<Review> reviews = reviewFindService.findReviewsByUserId(userId, pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/registrations")
    public ResponseEntity<Page<RegistrationResponseDto>> getUserRegistrations(Pageable pageable,
            @CurrentUserId Long userId) {
        Page<RegistrationResponseDto> registrations = registrationFindService.findRegistrationByUserId(
                userId, pageable);
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/events")
    public ResponseEntity<Page<ExerciseEvent>> getUserEvents(Pageable pageable,
            @CurrentUserId Long userId) {
        Page<ExerciseEvent> events = exerciseEventFindService.findEventByUserId(userId, pageable);
        return ResponseEntity.ok(events);
    }
}