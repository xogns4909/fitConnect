package com.example.fitconnect.domain.event.dto;

import com.example.fitconnect.domain.event.domain.EventDetail;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class EventDetailDto {
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public EventDetailDto(String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EventDetail toEntity() {
        return new EventDetail(description, startDate, endDate);
    }
}