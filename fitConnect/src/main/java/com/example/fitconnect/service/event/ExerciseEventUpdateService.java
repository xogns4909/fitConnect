package com.example.fitconnect.service.event;

import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.dto.event.request.ExerciseEventUpdateDto;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExerciseEventUpdateService {

    private final ExerciseEventRepository exerciseEventRepository;
    @Transactional
    public ExerciseEvent updateEvent(Long eventId, ExerciseEventUpdateDto updateDto,Long userId) {
        ExerciseEvent event = exerciseEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.EVENT_NOT_FOUND));

        event.update(updateDto, userId);

        return event;
    }
}
