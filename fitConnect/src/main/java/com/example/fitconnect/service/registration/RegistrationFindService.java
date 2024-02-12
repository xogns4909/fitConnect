package com.example.fitconnect.service.registration;

import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.dto.registration.response.RegistrationResponseDto;
import com.example.fitconnect.repository.registration.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationFindService {

    private final RegistrationRepository registrationRepository;

    public Page<RegistrationResponseDto> findRegistrationByUserId(Long userId, Pageable pageable){
        Page<Registration> registrations = registrationRepository.findRegistrationsByUserId(userId, pageable);
        return registrations.map(new RegistrationResponseDto()::toDto);
    }
    public Page<RegistrationResponseDto> findByEventId(Long eventId, Pageable pageable){
        Page<Registration> registrations = registrationRepository.findByExerciseEventId(eventId, pageable);
        return registrations.map(new RegistrationResponseDto()::toDto);
    }

}
