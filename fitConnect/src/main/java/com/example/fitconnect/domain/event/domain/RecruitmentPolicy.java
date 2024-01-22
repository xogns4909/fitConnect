package com.example.fitconnect.domain.event.domain;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.Getter;

@Embeddable
@Getter
public class RecruitmentPolicy {

    private static final int MAX_PARTICIPANTS = 100;

    private Integer maxParticipants;
    private LocalDateTime registrationStart;
    private LocalDateTime registrationEnd;

    public RecruitmentPolicy(Integer maxParticipants, LocalDateTime registrationStart,
            LocalDateTime registrationEnd) {
        validateMaxParticipants(maxParticipants);
        validateRegistrationPeriod(registrationStart, registrationEnd);
        this.maxParticipants = maxParticipants;
        this.registrationStart = registrationStart;
        this.registrationEnd = registrationEnd;
    }

    public RecruitmentPolicy() {

    }

    private void validateMaxParticipants(Integer maxParticipants) {
        if (maxParticipants <= 0 || maxParticipants > MAX_PARTICIPANTS) {
            throw new BusinessException(ErrorMessages.PARTICIPANTS_NUMBER_INVALID);
        }
    }

    private void validateRegistrationPeriod(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new BusinessException(ErrorMessages.TIME_NULL);
        }

        if (start.isAfter(end)) {
            throw new BusinessException(ErrorMessages.REGISTRATION_DATE_INVALID);
        }
    }
}