package com.example.fitconnect.domain.event.dto;

import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.user.domain.User;

public class ExerciseEventRegistrationDto {
    private EventDetailDto eventDetail;
    private RecruitmentPolicyDto recruitmentPolicy;
    private LocationDto location;
    private Category category;

    public ExerciseEventRegistrationDto(EventDetailDto eventDetail,
            RecruitmentPolicyDto recruitmentPolicy, LocationDto location, Category category) {
        this.eventDetail = eventDetail;
        this.recruitmentPolicy = recruitmentPolicy;
        this.location = location;
        this.category = category;
    }

    public ExerciseEventRegistrationDto() {

    }

    public ExerciseEvent toEntity(User organizer) {
        return new ExerciseEvent(
                organizer,
                eventDetail.toEntity(),
                recruitmentPolicy.toEntity(),
                location.toEntity(),
                category
        );
    }
}