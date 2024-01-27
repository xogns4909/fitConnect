package com.example.fitconnect.service.registration;

import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.repository.registration.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationFindService {

    private final RegistrationRepository registrationRepository;

    public Page<Registration> findRegistrationByUserId(Long userId, Pageable pageable){
        return registrationRepository.findRegistrationsByUserId(userId,pageable);
    }
}
