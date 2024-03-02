package com.example.fitconnect.domain.event.domain;

import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.Getter;

@Embeddable
@Getter
public class EventDetail {

    private static final int MAX_PARTICIPANTS = 100;

    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public EventDetail(String title,String description, LocalDateTime startDate, LocalDateTime endDate) {
        validateDates(startDate, endDate);;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EventDetail() {

    }

    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        if(startDate == null || endDate == null){
            throw new BusinessException(ErrorMessages.TIME_NULL);
        }

        if (startDate.isAfter(endDate)) {
            throw new BusinessException(ErrorMessages.REGISTRATION_DATE_INVALID);
        }
    }
}