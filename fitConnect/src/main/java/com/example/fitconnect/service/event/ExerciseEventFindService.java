package com.example.fitconnect.service.event;

import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseEventFindService {

    private final ExerciseEventRepository exerciseEventRepository;


    public Page<ExerciseEvent> findEvents(Category category, String description, int page) {
        return exerciseEventRepository.findEventsWithConditions(category, description, page);
    }
    public Optional<ExerciseEvent> findEventByEventId(Long eventId){
        return exerciseEventRepository.findById(eventId);
    }
}


