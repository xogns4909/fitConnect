package com.example.fitconnect.dto.event.response;

import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import java.time.LocalDateTime;
import lombok.Getter;
@Getter
public class EventResponseDto {

    private Long id;
    private String title;
    private String category;
    private String organizerNickname;

    private LocalDateTime startTime;


    private LocalDateTime endTime;

    private LocalDateTime writeTime;

    private City city;

    private String address;

    protected EventResponseDto() {
    }

    public EventResponseDto(Long id, String title, String category, String organizerNickname,
            LocalDateTime startTime, LocalDateTime endTime,LocalDateTime writeTime,City city,String address) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.organizerNickname = organizerNickname;
        this.startTime = startTime;
        this.endTime = endTime;
        this.writeTime = writeTime;
        this.city = city;
        this.address = address;
    }

    public static EventResponseDto toDto(ExerciseEvent exerciseEvent) {
        return new EventResponseDto(
                exerciseEvent.getId(),
                exerciseEvent.getEventDetail().getTitle(),
                exerciseEvent.getCategory().getName(),
                exerciseEvent.getOrganizer().getUserBaseInfo().getNickname(),
                exerciseEvent.getEventDetail().getStartDate(),
                exerciseEvent.getEventDetail().getEndDate(),
                exerciseEvent.getCreatedAt(),
                exerciseEvent.getLocation().getCity(),
                exerciseEvent.getLocation().getAddress()
        );
    }
}