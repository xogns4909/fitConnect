package com.example.fitconnect.service.registration;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.registration.RegistrationStatus;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.repository.registration.RegistrationRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RegistrationApprovalServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationApprovalService service;

    private Registration registration;
    private Long registrationId = 1L;
    private Long userId = 2L;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        User user = new User();
        user.setId(userId);
        ExerciseEvent exerciseEvent = new ExerciseEvent();
        registration = new Registration(user,exerciseEvent);

    }

    @Test
    public void approve_Success() {
        registration.setStatus(RegistrationStatus.APPROVED);
        when(registrationRepository.findById(registrationId)).thenReturn(Optional.of(registration));

        service.approveRegistration(registrationId, userId);

        assertThat(registration.getStatus()).isEqualTo(RegistrationStatus.APPROVED);
    }

    @Test
    public void approve_NotFound() {
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.approveRegistration(registrationId, userId))
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
}
