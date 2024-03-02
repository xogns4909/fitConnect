package com.example.fitconnect.service.registration;

import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.registration.RegistrationStatus;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.repository.registration.RegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegistrationCancellationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationCancellationService registrationCancellationService;

    private User user;
    private Registration registration;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        registration = new Registration(user, new ExerciseEvent());
    }

    @Test
    void CancellationUpdates_Success() {
        given(registrationRepository.findById(registration.getId())).willReturn(Optional.of(registration));

        registrationCancellationService.cancelRegistration(registration.getId(), user.getId());

        assertThat(registration.getStatus()).isEqualTo(RegistrationStatus.CANCELED);
        verify(registrationRepository).findById(registration.getId());
    }

    @Test
    void notFoundRegistration_Test() {
        Long wrongId = 2L;
        given(registrationRepository.findById(wrongId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> registrationCancellationService.cancelRegistration(wrongId, user.getId()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void unauthorizedUserThrowsException_Test() {
        Long differentUserId = 2L;
        given(registrationRepository.findById(registration.getId())).willReturn(Optional.of(registration));

        assertThatThrownBy(() -> registrationCancellationService.cancelRegistration(registration.getId(), differentUserId))
                .isInstanceOf(BusinessException.class);
    }
}
