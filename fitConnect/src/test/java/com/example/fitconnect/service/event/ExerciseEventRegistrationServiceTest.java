package com.example.fitconnect.service.event;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.dto.event.request.EventDetailDto;
import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
import com.example.fitconnect.dto.event.request.LocationDto;
import com.example.fitconnect.dto.event.request.RecruitmentPolicyDto;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import com.example.fitconnect.service.user.UserFindService;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExerciseEventRegistrationServiceTest {

    @Mock
    private ExerciseEventRepository exerciseEventRepository;

    @Mock
    private UserFindService userFindService;

    @InjectMocks
    private ExerciseEventRegistrationService exerciseEventRegistrationService;

    @ParameterizedTest
    @MethodSource("provideValidRegistrationData")
    void registerEvent_Success(Long userId, ExerciseEventRegistrationDto dto, User user,
            ExerciseEvent expectedEvent) {
        given(userFindService.findUserByUserId(userId)).willReturn(Optional.of(user));
        given(exerciseEventRepository.save(any(ExerciseEvent.class))).willReturn(expectedEvent);

        ExerciseEvent result = exerciseEventRegistrationService.registerEvent(userId, dto);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedEvent);
    }

    private static Stream<Arguments> provideValidRegistrationData() {
        User user = new User(new UserBaseInfo("user@example.com", "User Name", "profilePic.jpg"),
                Role.MEMBER);
        user.setId(1L);

        ExerciseEventRegistrationDto eventRegistrationDto = createEventRegistrationDto();
        ExerciseEvent exerciseEvent = eventRegistrationDto.toEntity(user);

        return Stream.of(
                Arguments.of(1L, eventRegistrationDto, user, exerciseEvent)
        );
    }

    private static ExerciseEventRegistrationDto createEventRegistrationDto() {
        EventDetailDto eventDetailDto = new EventDetailDto("title","Description", LocalDateTime.now(),
                LocalDateTime.now().plusHours(2));
        RecruitmentPolicyDto recruitmentPolicyDto = new RecruitmentPolicyDto(30,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        LocationDto locationDto = new LocationDto(City.SEOUL, "서울시 강남구");
        Category category = Category.SOCCER;

        return new ExerciseEventRegistrationDto(eventDetailDto,
                recruitmentPolicyDto, locationDto, category);
    }

    @ParameterizedTest
    @ValueSource(longs = {999L, 1000L})
    void registerEvent_Failure(Long userId) {
        ExerciseEventRegistrationDto dto = new ExerciseEventRegistrationDto();

        given(userFindService.findUserByUserId(userId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> exerciseEventRegistrationService.registerEvent(userId, dto))
                .isInstanceOf(EntityNotFoundException.class);
    }
}