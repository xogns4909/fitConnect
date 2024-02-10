package com.example.fitconnect.dto.registration.response;

import com.example.fitconnect.domain.registration.Registration;
import lombok.Getter;

@Getter
public class RegistrationResponseDto {
    private Long registrationId;
    private Long userId;
    private Long eventId;
    private String status;


    public RegistrationResponseDto(Long registrationId, Long userId, Long eventId, String status) {
        this.registrationId = registrationId;
        this.userId = userId;
        this.eventId = eventId;
        this.status = status;
    }
    
    public RegistrationResponseDto(){}

    public  RegistrationResponseDto toDto(Registration registration) {
        return new RegistrationResponseDto(
                registration.getId(),
                registration.getUser().getId(),
                registration.getExerciseEvent().getId(),
                registration.getStatus().toString()
        );
    }
}