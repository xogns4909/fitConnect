package com.example.fitconnect.service.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.*;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.dto.event.request.EventDetailDto;
import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
import com.example.fitconnect.dto.event.request.ExerciseEventUpdateDto;
import com.example.fitconnect.dto.event.request.LocationDto;
import com.example.fitconnect.dto.event.request.RecruitmentPolicyDto;
import com.example.fitconnect.repository.event.ExerciseEventRepository;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class ExerciseEventUpdateServiceTest {

    @Mock
    private ExerciseEventRepository repository;

    @InjectMocks
    private ExerciseEventUpdateService service;

    private User user;
    private ExerciseEvent existingEvent;
    private ExerciseEventUpdateDto updateDto;

    @BeforeEach
    void setUp() {
        user = new User(new UserBaseInfo("test123@naver.com", "hi", ".com"), Role.MEMBER);
        user.setId(1L);
        existingEvent = createExerciseEvent(user);
        updateDto = createUpdateDto();
    }

    @Test
    void updateValidEvent() {
        given(repository.findById(1L)).willReturn(Optional.of(existingEvent));

        ExerciseEvent updatedEvent = service.updateEvent(1L, updateDto, 1L);

        assertThat(updatedEvent).isNotNull();
        assertThat(updatedEvent.getEventDetail().getDescription())
                .isEqualTo(updateDto.getEventDetail().getDescription());
        assertThat(updatedEvent.getCategory()).isEqualTo(updateDto.getCategory());
    }

    @Test
    void updateInvalidEvent() {
        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateEvent(1L, updateDto, 1L))
                .isInstanceOf(EntityNotFoundException.class);
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


    private ExerciseEventUpdateDto createUpdateDto() {
        return new ExerciseEventUpdateDto(
                new EventDetailDto("title","Updated Description", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2)),
                new RecruitmentPolicyDto(50, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)),
                new LocationDto(City.SEOUL, "서울시 송파구"),
                Category.BASKETBALL
        );
    }
}
