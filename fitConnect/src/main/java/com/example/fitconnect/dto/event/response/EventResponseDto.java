package com.example.fitconnect.dto.event.response;

import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class EventResponseDto {

    private Long id;
    private String title;
    private String category;
    private String organizerNickname;

    private LocalDateTime startTime;


    private LocalDateTime endTime;

    protected EventResponseDto() {
    }

    public EventResponseDto(Long id, String title, String category, String organizerNickname,
            LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.organizerNickname = organizerNickname;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static EventResponseDto toDto(ExerciseEvent exerciseEvent) {
        return new EventResponseDto(
                exerciseEvent.getId(),
                exerciseEvent.getEventDetail().getTitle(),
                exerciseEvent.getCategory().getName(),
                exerciseEvent.getOrganizer().getUserBaseInfo().getNickname(),
                exerciseEvent.getEventDetail().getStartDate(),
                exerciseEvent.getEventDetail().getEndDate()
        );
    }
}