package com.example.fitconnect.controller.registration;

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
    public ResponseEntity<Void> approveRegistration(@PathVariable Long registrationId,
            @RequestParam Long eventId) {

        approvalService.approveRegistration(registrationId, eventId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{registrationId}/deny")
    public ResponseEntity<Void> denyRegistration(@PathVariable Long registrationId) {
        approvalService.denyRegistration(registrationId);
        return ResponseEntity.ok().build();
    }

}