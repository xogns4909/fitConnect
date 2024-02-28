package com.example.fitconnect.config.convertor;

import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.dto.registration.response.RegistrationResponseDto;

public class RegistrationConvertor {

    public static RegistrationResponseDto toDto(Registration registration) {
        return new RegistrationResponseDto(
                registration.getId(),
                registration.getUser().getId(),
                registration.getExerciseEvent().getId(),
                registration.getStatus().toString()
        );
    }

}
