package com.example.fitconnect.controller.mypage;

import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import com.example.fitconnect.service.registration.RegistrationFindService;
import com.example.fitconnect.service.review.ReviewFindService;
import jakarta.servlet.http.HttpSession;
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
    private final CommonService commonService;

    @GetMapping("/reviews")
    public ResponseEntity<Page<Review>> getUserReviews(Pageable pageable, HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        Page<Review> reviews = reviewFindService.findReviewsByUserId(userId, pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/registrations")
    public ResponseEntity<Page<Registration>> getUserRegistrations(Pageable pageable,
            HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        Page<Registration> registrations = registrationFindService.findRegistrationByUserId(userId,
                pageable);
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/events")
    public ResponseEntity<Page<ExerciseEvent>> getUserEvents(Pageable pageable,
            HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        Page<ExerciseEvent> events = exerciseEventFindService.findEventByUserId(userId, pageable);
        return ResponseEntity.ok(events);
    }
}