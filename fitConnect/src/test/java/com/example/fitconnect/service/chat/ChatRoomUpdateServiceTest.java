package com.example.fitconnect.service.chat;


import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.chat.dto.ChatRoomUpdateDto;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomFindService;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomUpdateService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
public class ChatRoomUpdateServiceTest {

    @InjectMocks
    private ChatRoomUpdateService chatRoomUpdateService;

    @Mock
    private ChatRoomFindService chatRoomFindService;

    @Mock
    private ChatRoom chatRoom;

    private ChatRoomUpdateDto chatRoomUpdateDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        chatRoomUpdateDto = new ChatRoomUpdateDto("update title", 1L);
    }

    @Test
    public void updateTitle_Success() {
        when(chatRoomFindService.findChatRoomByChatRoomId(any(Long.class))).thenReturn(
                Optional.of(chatRoom));

        chatRoomUpdateService.updateTitle(chatRoomUpdateDto, 1L);

        verify(chatRoomFindService, times(1)).findChatRoomByChatRoomId(any(Long.class));
        verify(chatRoom, times(1)).update(any(String.class));
    }

    @Test
    public void updateTitle_Failure() {
        when(chatRoomFindService.findChatRoomByChatRoomId(any(Long.class))).thenReturn(
                Optional.empty());

        Assertions.assertThatThrownBy(
                        () -> chatRoomUpdateService.updateTitle(chatRoomUpdateDto, anyLong()))
                .isInstanceOf(EntityNotFoundException.class);

        verify(chatRoomFindService, times(1)).findChatRoomByChatRoomId(any(Long.class));
        verify(chatRoom, never()).update(any(String.class));
    }
}
