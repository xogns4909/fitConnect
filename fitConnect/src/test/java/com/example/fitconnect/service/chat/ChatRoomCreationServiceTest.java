package com.example.fitconnect.service.chat;

import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.dto.chat.request.ChatRoomRegistrationDto;
import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.dto.event.request.EventDetailDto;
import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
import com.example.fitconnect.dto.event.request.LocationDto;
import com.example.fitconnect.dto.event.request.RecruitmentPolicyDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.repository.chat.chatRoom.ChatRoomRepository;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomCreationService;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import com.example.fitconnect.service.user.UserFindService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ChatRoomCreationServiceTest {

    @Mock
    private UserFindService userFindService;

    @Mock
    private ExerciseEventFindService exerciseEventFindService;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @InjectMocks
    private ChatRoomCreationService chatRoomCreationService;

    @Test
    public void createChatRoom_Success() {
        User mockUser = mock(User.class);
        mockUser.setId(1L);
        ExerciseEvent mockEvent = createExerciseEvent(mockUser);
        when(userFindService.findUserByUserId(anyLong())).thenReturn(Optional.of(mockUser));
        when(exerciseEventFindService.findEventByEventId(anyLong())).thenReturn(
                Optional.of(mockEvent));
        when(chatRoomRepository.save(any(ChatRoom.class))).thenAnswer(
                invocation -> invocation.getArgument(0));

        ChatRoom result = chatRoomCreationService.createChatRoom(
                new ChatRoomRegistrationDto("title", 1L), 1L);

        assertThat(result).isNotNull();
        assertThat(result.getCreator()).isEqualTo(mockUser);
        assertThat(result.getExerciseEvent()).isEqualTo(mockEvent);
    }

    @Test
    public void createChatRoom_UserNotFound() {

        when(userFindService.findUserByUserId(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> chatRoomCreationService.createChatRoom(
                new ChatRoomRegistrationDto("title", 1L), 1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void createChatRoom_EventNotFound() {
        User mockUser = mock(User.class);
        when(userFindService.findUserByUserId(anyLong())).thenReturn(Optional.of(mockUser));
        when(exerciseEventFindService.findEventByEventId(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> chatRoomCreationService.createChatRoom(
                new ChatRoomRegistrationDto("title", 1L), 1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void createChatRoom_UnauthorizedCreator() {
        User mockUser = new User();
        mockUser.setId(1L);
        ExerciseEvent mockEvent = createExerciseEvent(mockUser);
        mockEvent.setOrganizer(mockUser);

        when(userFindService.findUserByUserId(mockUser.getId())).thenReturn(Optional.of(mockUser));
        when(exerciseEventFindService.findEventByEventId(mockEvent.getId())).thenReturn(
                Optional.of(mockEvent));

        assertThatThrownBy(() -> chatRoomCreationService.createChatRoom(
                new ChatRoomRegistrationDto("title", mockEvent.getId()), mockUser.getId()))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    public void createChatRoom_DuplicateChatRoom() {
        User mockUser = new User();
        mockUser.setId(1L);
        ExerciseEvent mockEvent = createExerciseEvent(mockUser);

        ChatRoom existingChatRoom = new ChatRoom();
        when(userFindService.findUserByUserId(mockUser.getId())).thenReturn(Optional.of(mockUser));
        when(exerciseEventFindService.findEventByEventId(mockEvent.getId())).thenReturn(
                Optional.of(mockEvent));
        when(chatRoomRepository.findByUserIdAndExerciseEventId(mockUser.getId(), mockEvent.getId()))
                .thenReturn(Optional.of(existingChatRoom));

        assertThatThrownBy(() -> chatRoomCreationService.createChatRoom(
                new ChatRoomRegistrationDto("title", mockEvent.getId()), mockUser.getId()))
                .isInstanceOf(BusinessException.class);
    }


    private static ExerciseEvent createExerciseEvent(User user) {
        EventDetailDto eventDetailDto = new EventDetailDto("title", "Description",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2));
        RecruitmentPolicyDto recruitmentPolicyDto = new RecruitmentPolicyDto(30,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        LocationDto locationDto = new LocationDto(City.SEOUL, "서울시 강남구");
        Category category = Category.SOCCER;
        ExerciseEvent exerciseEvent = new ExerciseEventRegistrationDto(eventDetailDto,
                recruitmentPolicyDto, locationDto, category).toEntity(user);
        return exerciseEvent;
    }
}
