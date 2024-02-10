package com.example.fitconnect.controller.registration;

import com.example.fitconnect.config.annotation.CurrentUserId;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.dto.registration.response.RegistrationResponseDto;
import com.example.fitconnect.service.registration.RegistrationCancellationService;
import com.example.fitconnect.service.registration.RegistrationCreationService;
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

    @PostMapping
    public ResponseEntity<RegistrationResponseDto> createRegistration(@RequestParam Long eventId,
            @CurrentUserId Long userId) {
        RegistrationResponseDto registrationResponseDto = registrationService.createRegistration(
                userId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponseDto);
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Registration> cancelRegistration(@PathVariable Long registrationId,
            @CurrentUserId Long userId) {
        cancellationService.cancelRegistration(registrationId, userId);
        return ResponseEntity.ok().build();
    }


}
