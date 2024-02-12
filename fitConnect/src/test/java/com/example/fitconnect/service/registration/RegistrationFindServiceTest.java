package com.example.fitconnect.service.registration;

import static org.junit.jupiter.api.Assertions.*;

import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.event.dto.EventDetailDto;
import com.example.fitconnect.domain.event.dto.ExerciseEventRegistrationDto;
import com.example.fitconnect.domain.event.dto.LocationDto;
import com.example.fitconnect.domain.event.dto.RecruitmentPolicyDto;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.dto.registration.response.RegistrationResponseDto;
import com.example.fitconnect.repository.registration.RegistrationRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RegistrationFindServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationFindService registrationFindService;

    @Test
    public void testFindRegistrationsByEventId() {
        User user = new User();
        Long eventId = 1L;
        user.setId(1L);
        ExerciseEvent exerciseEvent = createEvent(user);
        Registration registration = new Registration(user, exerciseEvent);
        Page<Registration> page = new PageImpl<>(List.of(registration));
        RegistrationResponseDto registrationResponseDto = new RegistrationResponseDto().toDto(
                registration);

        when(registrationRepository.findByExerciseEventId(eq(eventId), any())).thenReturn(page);
        Page<RegistrationResponseDto> result = registrationFindService.findByEventId(eventId,
                PageRequest.of(0, 10));

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);

        verify(registrationRepository).findByExerciseEventId(eq(eventId), any());
    }

    private ExerciseEvent createEvent(User user) {
        return new ExerciseEventRegistrationDto(
                new EventDetailDto("title", "Description", LocalDateTime.now(),
                        LocalDateTime.now().plusHours(2)),
                new RecruitmentPolicyDto(30, LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                new LocationDto(City.SEOUL, "서울시 강남구"),
                Category.SOCCER
        ).toEntity(user);
    }

}
