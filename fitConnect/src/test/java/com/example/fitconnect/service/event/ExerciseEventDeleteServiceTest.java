package com.example.fitconnect.service.event;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.dto.event.request.EventDetailDto;
import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
import com.example.fitconnect.dto.event.request.LocationDto;
import com.example.fitconnect.dto.event.request.RecruitmentPolicyDto;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomDeleteService;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomFindService;
import com.example.fitconnect.service.registration.RegistrationFindService;
import com.example.fitconnect.service.registration.RegistrationFindServiceTest;
import com.example.fitconnect.service.review.ReviewDeletionService;
import com.example.fitconnect.service.review.ReviewFindService;
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
public class ExerciseEventDeleteServiceTest {

    @Mock
    private ExerciseEventRepository exerciseEventRepository;

    @Mock
    private ChatRoomFindService chatRoomFindService;

    @Mock
    private RegistrationFindService registrationFindService;

    @Mock
    private ReviewFindService reviewFindService;

    @InjectMocks
    private ExerciseEventDeleteService exerciseEventDeleteService;

    @Mock
    private ReviewDeletionService reviewDeletionService;
    private User user;
    private ExerciseEvent event;
    private Long eventId = 1L;
    private Long userId = 1L;

    @BeforeEach
    void setUp() {
        user = new User(new UserBaseInfo("user@example.com", "nickname", "url"), Role.MEMBER);
        user.setId(userId);
        event = createExerciseEvent(user);
    }

    @Test
    void deleteEvent_Success() {

        List<ChatRoom> chatRooms = new ArrayList<>();
        List<Registration> registrations = new ArrayList<>();
        given(chatRoomFindService.findChatRoomsByEventId(eventId)).willReturn(chatRooms);
        given(registrationFindService.findByEventId(eventId)).willReturn(registrations);
        given(exerciseEventRepository.findById(eventId)).willReturn(Optional.of(event));
        given(reviewFindService.findReviewsByEventId(eventId)).willReturn(Optional.empty());
        doNothing().when(reviewDeletionService).deleteReviews(anyList());
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
