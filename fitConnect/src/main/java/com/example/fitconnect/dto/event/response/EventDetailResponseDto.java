package com.example.fitconnect.dto.event.response;

import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;;
import com.example.fitconnect.dto.review.response.ReviewResponseDto;
import com.example.fitconnect.dto.user.response.UserResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class EventDetailResponseDto {

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private City city;

    private Category category;
    private String address;

    private int maxParticipants;

    private LocalDateTime registrationStart;

    private LocalDateTime registrationEnd;

    private List<ReviewResponseDto> reviewResponseDtoList;

    private UserResponseDto userResponseDto;

    public EventDetailResponseDto() {
    }

    public EventDetailResponseDto toDto(ExerciseEvent exerciseEvent) {
        this.title = exerciseEvent.getEventDetail().getTitle();
        this.description = exerciseEvent.getEventDetail().getDescription();
        this.startTime = exerciseEvent.getEventDetail().getStartDate();
        this.endTime = exerciseEvent.getEventDetail().getEndDate();
        this.city = exerciseEvent.getLocation().getCity();
        this.address = exerciseEvent.getLocation().getAddress();
        this.category = exerciseEvent.getCategory();
        this.maxParticipants = exerciseEvent.getRegistrationPolicy().getMaxParticipants();
        this.registrationStart = exerciseEvent.getRegistrationPolicy().getRegistrationStart();
        this.registrationEnd = exerciseEvent.getRegistrationPolicy().getRegistrationEnd();
        this.userResponseDto = new UserResponseDto().toDto(exerciseEvent.getOrganizer());
        return this;
    }
}
