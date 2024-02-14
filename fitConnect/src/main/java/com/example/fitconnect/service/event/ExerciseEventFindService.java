package com.example.fitconnect.service.event;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.event.domain.Location;
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


    public Page<ExerciseEvent> findEvents(Category category, City city, String searchBy,
            String description, int page) {
        return exerciseEventRepository.findEventsWithConditions(category, city, searchBy,
                description, page);
    }

    public Optional<ExerciseEvent> findEventByEventId(Long eventId) {
        return exerciseEventRepository.findById(eventId);
    }

    public ExerciseEvent findEventDetail(Long eventId) {
        return findEventByEventId(eventId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessages.EVENT_NOT_FOUND));
    }

    public Page<ExerciseEvent> findEventByUserId(Long userId, Pageable pageable) {
        return exerciseEventRepository.findEventsByOrganizerId(userId, pageable);
    }
}


