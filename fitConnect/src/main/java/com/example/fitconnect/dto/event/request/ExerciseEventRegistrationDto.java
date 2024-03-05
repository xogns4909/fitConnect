package com.example.fitconnect.dto.event.request;

import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.domain.user.domain.User;
import java.util.List;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
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

    public ExerciseEvent toEntity(User organizer, List<Image> images) {
        return new ExerciseEvent(
                organizer,
                eventDetail.toEntity(),
                recruitmentPolicy.toEntity(),
                location.toEntity(),
                category,
                images
        );
    }
}