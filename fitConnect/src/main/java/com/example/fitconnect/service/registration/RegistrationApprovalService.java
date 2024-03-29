package com.example.fitconnect.service.registration;

import static com.example.fitconnect.global.error.ErrorMessages.EVENT_MAX_PARTICIPANTS_REACHED;
import static com.example.fitconnect.global.error.ErrorMessages.EVENT_NOT_FOUND;

import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.registration.RegistrationStatus;
import com.example.fitconnect.repository.registration.RegistrationRepository;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationApprovalService {

    private final RegistrationRepository registrationRepository;

    private final ExerciseEventFindService exerciseEventFindService;

    public long countApprovedRegistrations(Long eventId) {
        return registrationRepository.countByExerciseEventIdAndStatus(eventId,
                RegistrationStatus.APPROVED);
    }

    @Transactional
    public void approveRegistration(Long registrationId, Long eventId) {
        Registration registration = findRegistration(registrationId);
        ExerciseEvent event = findEvent(eventId);
        long approvedCount = countApprovedRegistrations(eventId);

        validationMaxParticipants(approvedCount, event);
        validationRegistrationTime(event);
        registration.approve();

    }



    @Transactional
    public void denyRegistration(Long registrationId) {
        Registration registration = findRegistration(registrationId);
        registration.deny();
    }


    private void validationRegistrationTime(ExerciseEvent event) {
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(event.getRegistrationPolicy().getRegistrationStart())
                || now.isAfter(event.getRegistrationPolicy().getRegistrationEnd())) {
            throw new BusinessException(ErrorMessages.REGISTRATION_PERIOD_CLOSED);
        }
    }

    private void validationMaxParticipants(long approvedCount, ExerciseEvent event) {
        if (approvedCount >= event.getRegistrationPolicy().getMaxParticipants()) {
            throw new BusinessException(EVENT_MAX_PARTICIPANTS_REACHED);
        }
    }

    private Registration findRegistration(Long registrationId) {
        return registrationRepository.findById(registrationId)
                .orElseThrow(
                        () -> new EntityNotFoundException(ErrorMessages.REGISTRATION_NOT_FOUND));
    }


    private ExerciseEvent findEvent(Long eventId) {
        return exerciseEventFindService.findEventByEventId(eventId)
                .orElseThrow(() -> new BusinessException(EVENT_NOT_FOUND));
    }

}