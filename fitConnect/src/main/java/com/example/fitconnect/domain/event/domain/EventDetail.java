package com.example.fitconnect.domain.event.domain;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.domain.registration.Registration;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Embeddable
@Getter
public class EventDetail {

    private static final int MAX_PARTICIPANTS = 100;

    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "exerciseEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Registration> registrations = new ArrayList<>();

    public EventDetail(String description, LocalDateTime startDate, LocalDateTime endDate) {
        validateDates(startDate, endDate);;
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