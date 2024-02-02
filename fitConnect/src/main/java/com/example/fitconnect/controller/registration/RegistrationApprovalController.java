package com.example.fitconnect.controller.registration;

import com.example.fitconnect.config.annotation.CurrentUserId;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.service.registration.RegistrationApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/registrations")
public class RegistrationApprovalController {

    private final RegistrationApprovalService approvalService;

    @PostMapping("/{registrationId}/approve")
    public ResponseEntity<Registration> approveRegistration(@PathVariable Long registrationId,
            @CurrentUserId Long userId) {

        approvalService.approveRegistration(registrationId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{registrationId}/deny")
    public ResponseEntity<Registration> denyRegistration(@PathVariable Long registrationId,
            @CurrentUserId Long userId) {
        approvalService.denyRegistration(registrationId, userId);
        return ResponseEntity.ok().build();
    }
}