package com.example.fitconnect.service.registration;


import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.repository.registration.RegistrationRepository;
import com.example.fitconnect.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationCancellationService {

    private final RegistrationRepository registrationRepository;

    private final UserRepository userRepository;

    public void cancelRegistration(Long registrationId, Long userId) {

        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.REGISTRATION_NOT_FOUND));

        registration.cancel(userId);

    }
}

