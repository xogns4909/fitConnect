package com.example.fitconnect.controller.event;

import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.event.dto.ExerciseEventRegistrationDto;
import com.example.fitconnect.service.event.ExerciseEventRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class ExerciseEventController {

    private final ExerciseEventRegistrationService registrationService;
    private final CommonService commonService;

    @PostMapping("/register")
    public ResponseEntity<?> registerEvent(
            @RequestBody ExerciseEventRegistrationDto registrationDto,
            HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        ExerciseEvent registeredEvent = registrationService.registerEvent(userId, registrationDto);
        return ResponseEntity.ok(registeredEvent);
    }
}
