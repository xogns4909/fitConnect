package com.example.fitconnect.controller.registration;

import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.service.registration.RegistrationCancellationService;
import com.example.fitconnect.service.registration.RegistrationCreationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationCreationService registrationService;
    private final RegistrationCancellationService cancellationService;
    private final CommonService commonService;

    @PostMapping
    public ResponseEntity<Registration> createRegistration(@RequestParam Long eventId, HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        Registration registration = registrationService.createRegistration(userId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(registration);
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Registration> cancelRegistration(@PathVariable Long registrationId,HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        cancellationService.cancelRegistration(registrationId, userId);
        return ResponseEntity.ok().build();
    }


}
