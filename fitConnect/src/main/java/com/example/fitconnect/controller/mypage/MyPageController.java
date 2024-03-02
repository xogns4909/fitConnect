package com.example.fitconnect.controller.mypage;

import com.example.fitconnect.global.annotation.CurrentUserId;
import com.example.fitconnect.dto.event.response.EventResponseDto;
import com.example.fitconnect.dto.registration.response.RegistrationResponseDto;
import com.example.fitconnect.dto.review.response.ReviewResponseDto;
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
    public ResponseEntity<Page<ReviewResponseDto>> getUserReviews(Pageable pageable,
            @CurrentUserId Long userId) {
        Page<ReviewResponseDto> reviews = reviewFindService.findReviewsByUserId(userId, pageable);
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
    public ResponseEntity<Page<EventResponseDto>> getUserEvents(Pageable pageable,
            @CurrentUserId Long userId) {
        Page<EventResponseDto> events = exerciseEventFindService.findEventByUserId(userId, pageable);
        return ResponseEntity.ok(events);
    }
}