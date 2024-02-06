package com.example.fitconnect.service.event;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.dto.EventDetailDto;
import com.example.fitconnect.domain.event.dto.ExerciseEventRegistrationDto;
import com.example.fitconnect.domain.event.dto.RecruitmentPolicyDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import com.example.fitconnect.service.user.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseEventRegistrationService {

    private final ExerciseEventRepository exerciseEventRepository;
    private final UserFindService userFindService;

    @Transactional
    public ExerciseEvent registerEvent(Long userId, ExerciseEventRegistrationDto registrationDto) {
        User organizer = userFindService.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));

        ExerciseEvent exerciseEvent = registrationDto.toEntity(organizer);

        return exerciseEventRepository.save(exerciseEvent);
    }
}
