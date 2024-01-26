package com.example.fitconnect.service.event;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.event.dto.ExerciseEventUpdateDto;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseEventUpdateService {

    private final ExerciseEventRepository exerciseEventRepository;

    public ExerciseEvent updateEvent(Long eventId, ExerciseEventUpdateDto updateDto,Long userId) {
        ExerciseEvent event = exerciseEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.EVENT_NOT_FOUND));

        event.update(updateDto, userId);

        return event;
    }
}
