package com.example.fitconnect.controller.registration;
import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.service.registration.RegistrationApprovalService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/registrations")
public class RegistrationApprovalController {

    private final RegistrationApprovalService approvalService;

    private final CommonService commonService;
    @PostMapping("/{registrationId}/approve")
    public ResponseEntity<Registration> approveRegistration(@PathVariable Long registrationId,
            HttpSession session) {

        Long userId = commonService.extractUserIdFromSession(session);
        approvalService.approveRegistration(registrationId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{registrationId}/deny")
    public ResponseEntity<Registration> denyRegistration(@PathVariable Long registrationId,
           HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        approvalService.denyRegistration(registrationId, userId);
        return ResponseEntity.ok().build();
    }
}