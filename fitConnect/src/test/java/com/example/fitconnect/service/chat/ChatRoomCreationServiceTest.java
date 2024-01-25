package com.example.fitconnect.service.chat;

import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.chat.dto.ChatRoomRegistrationDto;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.repository.chat.ChatRoomRepository;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import com.example.fitconnect.service.user.UserFindService;
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
        ExerciseEvent mockEvent = mock(ExerciseEvent.class);
        when(userFindService.findUserByUserId(anyLong())).thenReturn(Optional.of(mockUser));
        when(exerciseEventFindService.findEventByEventId(anyLong())).thenReturn(
                Optional.of(mockEvent));
        when(chatRoomRepository.save(any(ChatRoom.class))).thenAnswer(
                invocation -> invocation.getArgument(0));

        ChatRoom result = chatRoomCreationService.createChatRoom(
                new ChatRoomRegistrationDto("title", 1L),1L);

        assertThat(result).isNotNull();
        assertThat(result.getCreator()).isEqualTo(mockUser);
        assertThat(result.getExerciseEvent()).isEqualTo(mockEvent);
    }

    @Test
    public void createChatRoom_UserNotFound() {

        when(userFindService.findUserByUserId(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> chatRoomCreationService.createChatRoom(
                        new ChatRoomRegistrationDto("title", 1L),1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void createChatRoom_EventNotFound() {
        User mockUser = mock(User.class);
        when(userFindService.findUserByUserId(anyLong())).thenReturn(Optional.of(mockUser));
        when(exerciseEventFindService.findEventByEventId(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> chatRoomCreationService.createChatRoom(
                new ChatRoomRegistrationDto("title", 1L),1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

}
