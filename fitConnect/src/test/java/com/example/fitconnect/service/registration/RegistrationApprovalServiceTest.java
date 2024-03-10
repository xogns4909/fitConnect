package com.example.fitconnect.service.registration;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.global.exception.EntityNotFoundException;
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
import java.util.ArrayList;
import java.util.List;
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
        ExerciseEvent event = createExerciseEvent(new User());
        registration.setStatus(RegistrationStatus.APPROVED);
        when(registrationRepository.findById(registrationId)).thenReturn(Optional.of(registration));
        when(exerciseEventFindService.findEventByEventId(eventId)).thenReturn(
                Optional.of(event));

        service.approveRegistration(registrationId, eventId);

        assertThat(registration.getStatus()).isEqualTo(RegistrationStatus.APPROVED);
    }

    @Test
    public void approve_NotFound() {
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.approveRegistration(registrationId, eventId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void deny_Success() {
        registration.setStatus(RegistrationStatus.REJECTED);
        when(registrationRepository.findById(registrationId)).thenReturn(Optional.of(registration));

        service.denyRegistration(registrationId);

        assertThat(registration.getStatus()).isEqualTo(RegistrationStatus.REJECTED);
    }

    @Test
    public void deny_NotFound() {
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.denyRegistration(registrationId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void approve_ExceedMaxParticipants() {
        ExerciseEvent event = createExerciseEvent(new User());
        when(registrationRepository.findById(registrationId)).thenReturn(Optional.of(registration));
        when(exerciseEventFindService.findEventByEventId(eventId)).thenReturn(Optional.of(event));
        when(registrationRepository.countByExerciseEventIdAndStatus(eventId,
                RegistrationStatus.APPROVED)).thenReturn(31L);

        assertThatThrownBy(() -> service.approveRegistration(registrationId, eventId))
                .isInstanceOf(BusinessException.class);
    }

    private static ExerciseEvent createExerciseEvent(User user) {
        List<Image> images = new ArrayList<>();
        EventDetailDto eventDetailDto = new EventDetailDto("title","Description", LocalDateTime.now(),
                LocalDateTime.now().plusHours(2));
        RecruitmentPolicyDto recruitmentPolicyDto = new RecruitmentPolicyDto(30,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        LocationDto locationDto = new LocationDto(City.SEOUL, "서울시 강남구");
        Category category = Category.SOCCER;
        ExerciseEvent exerciseEvent = new ExerciseEventRegistrationDto(eventDetailDto,
                recruitmentPolicyDto, locationDto, category).toEntity(user,images);
        return exerciseEvent;
    }
}
