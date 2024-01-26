package com.example.fitconnect.service.event;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseEventDeleteService {

    private final ExerciseEventRepository exerciseEventRepository;

    public void deleteEvent(Long eventId, Long userId) {
        ExerciseEvent event = exerciseEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.EVENT_NOT_FOUND));

        if (!event.getOrganizer().getId().equals(userId)) {
            throw new BusinessException(ErrorMessages.UNAUTHORIZED_USER);
        }

        exerciseEventRepository.delete(event);
    }
}