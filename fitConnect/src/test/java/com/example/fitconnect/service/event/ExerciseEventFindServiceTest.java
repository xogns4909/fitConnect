package com.example.fitconnect.service.event;

import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.dto.event.request.EventDetailDto;
import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
import com.example.fitconnect.dto.event.request.LocationDto;
import com.example.fitconnect.dto.event.request.RecruitmentPolicyDto;
import com.example.fitconnect.dto.event.response.EventResponseDto;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ExerciseEventFindServiceTest {

    @Mock
    private ExerciseEventRepository exerciseEventRepository;

    @InjectMocks
    private ExerciseEventFindService exerciseEventFindService;


    @Test
    void FindEvents_thenReturnsPageOfEvents() {
        User user = new User(new UserBaseInfo("test@Bave.com","김태훈","url"), Role.MEMBER);

        Category category = Category.SOCCER;
        String description = "Soccer match in Seoul";
        City city = City.BUSAN;
        String searchBy = "title";
        int page = 0;
        ExerciseEvent event1 = createExerciseEvent(user);
        ExerciseEvent event2 = createExerciseEvent(user);

        List<ExerciseEvent> events = Arrays.asList(event1, event2);
        Page<ExerciseEvent> expectedPage = new PageImpl<>(events, PageRequest.of(page, 10),
                events.size());

        given(exerciseEventRepository.findEventsWithConditions(category, city, searchBy,
                description, page))
                .willReturn(expectedPage);

        Page<EventResponseDto> result = exerciseEventFindService.findEvents(category, city, searchBy,
                description,
                page);

        assertThat(result.getSize()).isEqualTo(expectedPage.getSize());
    }

    @Test
    void findEventByEventId_NotNull() {
        Long eventId = 1L;
        ExerciseEvent expectedEvent = new ExerciseEvent();
        given(exerciseEventRepository.findById(eventId)).willReturn(Optional.of(expectedEvent));

        Optional<ExerciseEvent> result = exerciseEventFindService.findEventByEventId(eventId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedEvent);
    }

    @Test
    void findEventByEventId_Empty() {
        Long eventId = 2L;
        given(exerciseEventRepository.findById(eventId)).willReturn(Optional.empty());

        Optional<ExerciseEvent> result = exerciseEventFindService.findEventByEventId(eventId);

        assertThat(result).isNotPresent();
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