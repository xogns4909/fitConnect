package com.example.fitconnect.domain.event.dto;

import com.example.fitconnect.domain.event.domain.RecruitmentPolicy;
import java.time.LocalDateTime;

public class RecruitmentPolicyDto {


    private int maxParticipants;
    private LocalDateTime registrationStart;
    private LocalDateTime registrationEnd;

    public RecruitmentPolicyDto(int maxParticipants, LocalDateTime registrationStart,
            LocalDateTime registrationEnd) {
        this.maxParticipants = maxParticipants;
        this.registrationStart = registrationStart;
        this.registrationEnd = registrationEnd;
    }

    public RecruitmentPolicy toEntity() {
        return new RecruitmentPolicy(maxParticipants, registrationStart, registrationEnd);
    }
}