package com.example.fitconnect.service.registration;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;

import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.dto.event.request.EventDetailDto;
import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
import com.example.fitconnect.dto.event.request.LocationDto;
import com.example.fitconnect.dto.event.request.RecruitmentPolicyDto;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.dto.registration.response.RegistrationResponseDto;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import com.example.fitconnect.repository.registration.RegistrationRepository;
import com.example.fitconnect.repository.user.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        testEvent = createExerciseEvent(testUser);
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


        given(userRepository.findById(validUserId)).willReturn(Optional.of(testUser));
        given(exerciseEventRepository.findById(validEventId)).willReturn(Optional.of(testEvent));

        assertThatThrownBy(() -> registrationService.createRegistration(validUserId, validEventId))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void createRegistration_AlreadyRegistered() {

        lenient().when(userRepository.findById(validUserId)).thenReturn(Optional.of(testUser));
        lenient().when(exerciseEventRepository.findById(validEventId))
                .thenReturn(Optional.of(testEvent));
        lenient().when(
                        registrationRepository.findRegistrationByUserAndEvent(validUserId, validEventId))
                .thenReturn(Optional.of(new Registration()));

        assertThatThrownBy(() -> registrationService.createRegistration(validUserId, validEventId))
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
