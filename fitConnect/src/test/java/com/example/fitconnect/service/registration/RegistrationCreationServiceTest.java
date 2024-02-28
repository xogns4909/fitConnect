package com.example.fitconnect.service.registration;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;

import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.dto.registration.response.RegistrationResponseDto;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import com.example.fitconnect.repository.registration.RegistrationRepository;
import com.example.fitconnect.repository.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegistrationCreationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ExerciseEventRepository exerciseEventRepository;

    @InjectMocks
    private RegistrationCreationService registrationService;

    private User testUser;
    private ExerciseEvent testEvent;
    private Long validUserId = 1L;
    private Long validEventId = 1L;
    private Long invalidUserId = 99L;
    private Long invalidEventId = 99L;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testEvent = new ExerciseEvent();
    }

    @Test
    void createRegistration_Success() {
        given(userRepository.findById(validUserId)).willReturn(Optional.of(testUser));
        given(exerciseEventRepository.findById(validEventId)).willReturn(Optional.of(testEvent));
        given(registrationService.createRegistration(anyLong(), anyLong())).willReturn(
                new RegistrationResponseDto());

        assertThatCode(() -> registrationService.createRegistration(validUserId, validEventId))
                .doesNotThrowAnyException();
    }

    @Test
    void createRegistration_UserNotFound() {
        given(userRepository.findById(invalidUserId)).willReturn(Optional.empty());

        assertThatThrownBy(
                () -> registrationService.createRegistration(invalidUserId, validEventId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void createRegistration_EventNotFound() {
        given(userRepository.findById(validUserId)).willReturn(Optional.of(testUser));
        given(exerciseEventRepository.findById(invalidEventId)).willReturn(Optional.empty());

        assertThatThrownBy(
                () -> registrationService.createRegistration(validUserId, invalidEventId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void createRegistration_OrganizerCannotRegister() {

        testEvent.setOrganizer(testUser);

        given(userRepository.findById(validUserId)).willReturn(Optional.of(testUser));
        given(exerciseEventRepository.findById(validEventId)).willReturn(Optional.of(testEvent));

        assertThatThrownBy(() -> registrationService.createRegistration(validUserId, validEventId))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void createRegistration_AlreadyRegistered() {

        testEvent.setOrganizer(testUser);

        lenient().when(userRepository.findById(validUserId)).thenReturn(Optional.of(testUser));
        lenient().when(exerciseEventRepository.findById(validEventId))
                .thenReturn(Optional.of(testEvent));
        lenient().when(
                        registrationRepository.findRegistrationByUserAndEvent(validUserId, validEventId))
                .thenReturn(Optional.of(new Registration()));

        assertThatThrownBy(() -> registrationService.createRegistration(validUserId, validEventId))
                .isInstanceOf(BusinessException.class);
    }

}
