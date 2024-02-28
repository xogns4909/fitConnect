package com.example.fitconnect.service.event;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExerciseEventDeleteServiceTest {

    @Mock
    private ExerciseEventRepository exerciseEventRepository;

    @InjectMocks
    private ExerciseEventDeleteService exerciseEventDeleteService;

    private User user;
    private ExerciseEvent event;
    private Long eventId = 1L;
    private Long userId = 1L;

    @BeforeEach
    void setUp() {
        user = new User(new UserBaseInfo("user@example.com", "nickname", "url"), Role.MEMBER);
        user.setId(userId);
        event = new ExerciseEvent();
        event.setOrganizer(user);
    }

    @Test
    void deleteEvent_Success() {
        given(exerciseEventRepository.findById(eventId)).willReturn(Optional.of(event));

        exerciseEventDeleteService.deleteEvent(eventId, userId);

    }

    @Test
    void deleteEvent_NotFoundEntity() {
        given(exerciseEventRepository.findById(eventId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> exerciseEventDeleteService.deleteEvent(eventId, userId))
                .isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    void deleteEvent_InvalidUser() {
        Long anotherUserId = 2L;
        given(exerciseEventRepository.findById(eventId)).willReturn(Optional.of(event));

        assertThatThrownBy(() -> exerciseEventDeleteService.deleteEvent(eventId, anotherUserId))
                .isInstanceOf(BusinessException.class);

    }
}
