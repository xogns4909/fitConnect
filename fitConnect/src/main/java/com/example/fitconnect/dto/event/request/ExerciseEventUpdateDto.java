package com.example.fitconnect.dto.event.request;

import com.example.fitconnect.domain.event.domain.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseEventUpdateDto {
    private EventDetailDto eventDetail;
    private RecruitmentPolicyDto recruitmentPolicy;
    private LocationDto location;
    private Category category;

    public ExerciseEventUpdateDto(EventDetailDto eventDetail,
            RecruitmentPolicyDto recruitmentPolicy,
            LocationDto location, Category category) {
        this.eventDetail = eventDetail;
        this.recruitmentPolicy = recruitmentPolicy;
        this.location = location;
        this.category = category;
    }
}