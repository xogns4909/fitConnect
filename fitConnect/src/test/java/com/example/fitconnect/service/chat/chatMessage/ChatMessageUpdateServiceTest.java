package com.example.fitconnect.service.chat.chatMessage;

import static org.junit.jupiter.api.Assertions.*;

import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.chat.dto.ChatMessageUpdateDto;
import com.example.fitconnect.domain.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ChatMessageUpdateServiceTest {

    @InjectMocks
    private ChatMessageUpdateService chatMessageUpdateService;

    @Mock
    private ChatMessageFindService chatMessageFindService;

    @Test
    void updateMessage_Success() {
        Long chatMessageId = 1L;
        Long userId = 1L;
        ChatMessageUpdateDto updateDto = new ChatMessageUpdateDto("Updated content", chatMessageId);
        User sender = new User();
        sender.setId(1L);
        ChatMessage message = new ChatMessage("Original content", new ChatRoom(), sender);
        message.setCreatedAt(LocalDateTime.now().minusMinutes(1));
        when(chatMessageFindService.findChatMessageByChatMessageId(chatMessageId))
                .thenReturn(Optional.of(message));

        assertThatCode(() -> chatMessageUpdateService.updateMessage(updateDto, userId))
                .doesNotThrowAnyException();
    }

    @Test
    void updateMessage_Failure_UnauthorizedUser() {
        Long chatMessageId = 1L;
        Long userId = 2L; // 다른 사용자 ID
        ChatMessageUpdateDto updateDto = new ChatMessageUpdateDto("Updated content", chatMessageId);
        User sender = new User();
        sender.setId(1L);
        ChatMessage message = new ChatMessage("Original content", new ChatRoom(), sender);

        when(chatMessageFindService.findChatMessageByChatMessageId(chatMessageId))
                .thenReturn(Optional.of(message));

        assertThatThrownBy(() -> chatMessageUpdateService.updateMessage(updateDto, userId))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void updateMessage_Failure_TimeExpired() {
        Long chatMessageId = 1L;
        Long userId = 1L;
        ChatMessageUpdateDto updateDto = new ChatMessageUpdateDto("Updated content", chatMessageId);
        User sender = new User();
        sender.setId(1L);
        ChatMessage message = new ChatMessage("Original content", new ChatRoom(), sender);
        message.setCreatedAt(LocalDateTime.now().minusMinutes(6)); 

        when(chatMessageFindService.findChatMessageByChatMessageId(chatMessageId))
                .thenReturn(Optional.of(message));

        assertThatThrownBy(() -> chatMessageUpdateService.updateMessage(updateDto, userId))
                .isInstanceOf(BusinessException.class);
    }
}
