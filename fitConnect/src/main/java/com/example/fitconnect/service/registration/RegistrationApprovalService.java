package com.example.fitconnect.service.registration;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.repository.registration.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationApprovalService {

    private final RegistrationRepository registrationRepository;


    public void approveRegistration(Long registrationId, Long adminId) {
        Registration registration = findRegistration(registrationId);
        registration.approve(adminId);
    }


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