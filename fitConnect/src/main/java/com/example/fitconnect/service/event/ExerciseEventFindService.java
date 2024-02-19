package com.example.fitconnect.service.event;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.dto.event.response.EventDetailResponseDto;
import com.example.fitconnect.dto.event.response.EventResponseDto;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseEventFindService {

    private final ExerciseEventRepository exerciseEventRepository;


    public Page<EventResponseDto> findEvents(Category category, City city, String searchBy,
            String description, int page) {
        Page<ExerciseEvent> eventsWithConditions = exerciseEventRepository.findEventsWithConditions(
                category, city, searchBy,
                description, page);
        return eventsWithConditions.map(EventResponseDto::toDto);
    }


    public EventDetailResponseDto findEventDetail(Long eventId) {

        ExerciseEvent exerciseEvent = findEventByEventId(eventId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessages.EVENT_NOT_FOUND));
        return new EventDetailResponseDto().toDto(exerciseEvent);
    }

    public Optional<ExerciseEvent> findEventByEventId(Long eventId) {
        return exerciseEventRepository.findById(eventId);
    }


    public Page<EventResponseDto> findEventByUserId(Long userId, Pageable pageable) {
        Page<ExerciseEvent> eventsByOrganizerId = exerciseEventRepository.findEventsByOrganizerId(
                userId, pageable);
        return eventsByOrganizerId.map(EventResponseDto::toDto);
    }
}


