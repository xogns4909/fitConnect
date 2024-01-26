package com.example.fitconnect.service.registration;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.repository.registration.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationApprovalService {

    private final RegistrationRepository registrationRepository;

    @Transactional
    public void approveRegistration(Long registrationId, Long adminId) {
        Registration registration = findRegistration(registrationId);
        registration.approve(adminId);
    }

    @Transactional
    public void denyRegistration(Long registrationId, Long adminId) {
        Registration registration = findRegistration(registrationId);
        registration.deny(adminId);
    }

    private Registration findRegistration(Long registrationId) {
        return registrationRepository.findById(registrationId)
                .orElseThrow(
                        () -> new EntityNotFoundException(ErrorMessages.REGISTRATION_NOT_FOUND));
    }

}