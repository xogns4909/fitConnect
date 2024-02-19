package com.example.fitconnect.service.registration;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.dto.event.request.EventDetailDto;
import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
import com.example.fitconnect.dto.event.request.LocationDto;
import com.example.fitconnect.dto.event.request.RecruitmentPolicyDto;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.registration.RegistrationStatus;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.repository.registration.RegistrationRepository;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RegistrationApprovalServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private ExerciseEventFindService exerciseEventFindService;

    @InjectMocks
    private RegistrationApprovalService service;

    private Registration registration;
    private Long registrationId = 1L;
    private Long userId = 2L;
    private Long eventId = 1L;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        User user = new User();
        user.setId(userId);
        ExerciseEvent exerciseEvent = new ExerciseEvent();
        registration = new Registration(user, exerciseEvent);

    }

    @Test
    public void approve_Success() {
        ExerciseEvent event = createEvent();
        registration.setStatus(RegistrationStatus.APPROVED);
        when(registrationRepository.findById(registrationId)).thenReturn(Optional.of(registration));
        when(exerciseEventFindService.findEventByEventId(eventId)).thenReturn(
                Optional.of(event));

        service.approveRegistration(registrationId, userId, eventId);

        assertThat(registration.getStatus()).isEqualTo(RegistrationStatus.APPROVED);
    }

    @Test
    public void approve_NotFound() {
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.approveRegistration(registrationId, eventId, userId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void deny_Success() {
        registration.setStatus(RegistrationStatus.REJECTED);
        when(registrationRepository.findById(registrationId)).thenReturn(Optional.of(registration));

        service.denyRegistration(registrationId, userId);

        assertThat(registration.getStatus()).isEqualTo(RegistrationStatus.REJECTED);
    }

    @Test
    public void deny_NotFound() {
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.denyRegistration(registrationId, userId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void approve_ExceedMaxParticipants() {
        ExerciseEvent event = createEvent();
        when(registrationRepository.findById(registrationId)).thenReturn(Optional.of(registration));
        when(exerciseEventFindService.findEventByEventId(eventId)).thenReturn(Optional.of(event));
        when(registrationRepository.countByExerciseEventIdAndStatus(eventId,
                RegistrationStatus.APPROVED)).thenReturn(31L);

        assertThatThrownBy(() -> service.approveRegistration(registrationId, userId, eventId))
                .isInstanceOf(BusinessException.class);
    }

    private ExerciseEvent createEvent() {
        return new ExerciseEventRegistrationDto(
                new EventDetailDto("title", "Description", LocalDateTime.now(),
                        LocalDateTime.now().plusHours(2)),
                new RecruitmentPolicyDto(30, LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                new LocationDto(City.SEOUL, "서울시 강남구"),
                Category.SOCCER
        ).toEntity(new User());
    }
}
