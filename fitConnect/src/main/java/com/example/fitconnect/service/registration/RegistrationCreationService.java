package com.example.fitconnect.service.registration;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.dto.registration.response.RegistrationResponseDto;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import com.example.fitconnect.repository.registration.RegistrationRepository;
import com.example.fitconnect.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationCreationService {

    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final ExerciseEventRepository exerciseEventRepository;

    @Transactional
    public RegistrationResponseDto createRegistration(Long userId, Long eventId) {
        User user = findUser(userId);
        ExerciseEvent event = findEvent(eventId);
        checkUser(user.getId(), event.getOrganizer().getId());
        checkAlreadyRegistered(userId, eventId);
        Registration registration = new Registration(user,event);
        Registration savedRegistration = registrationRepository.save(registration);
        return new RegistrationResponseDto().toDto(savedRegistration);
    }
    private void checkAlreadyRegistered(Long userId, Long eventId) {
        registrationRepository.findRegistrationByUserAndEvent(userId, eventId)
                .ifPresent(r -> {
                    throw new BusinessException(ErrorMessages.ALREADY_REGISTERED);
                });
    }
    private void checkUser(Long createUserId, Long organizerId) {
        if(createUserId.equals(organizerId)){
            throw new BusinessException(ErrorMessages.ORGANIZER_CANNOT_REGISTER);
        }
    }



    private ExerciseEvent findEvent(Long eventId) {
        ExerciseEvent event = exerciseEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.EVENT_NOT_FOUND));
        return event;
    }

    private User findUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));
        return user;
    }

}
