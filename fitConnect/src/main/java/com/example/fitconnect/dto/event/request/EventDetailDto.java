package com.example.fitconnect.dto.event.request;

import com.example.fitconnect.domain.event.domain.EventDetail;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class EventDetailDto {
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public EventDetailDto(String title,String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EventDetail toEntity() {
        return new EventDetail(title,description, startDate, endDate);
    }
}